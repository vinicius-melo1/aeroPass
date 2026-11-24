package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.DTOs.EnumStatusUsuario;
import com.example.aeropass.domain.domainServices.UsuarioDomainService;
import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.repository.EmpresaRepository;
import com.example.aeropass.domain.repository.PassageiroRepository;
import com.example.aeropass.domain.repository.UsuarioRepository;
import com.example.aeropass.domain.valueObject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioDomainService usuarioDomainService;

    @Value("${spring.secret}")
    private String secret;



    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request){
        if(usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())) {
            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Empresa getEmpresaUsuarioLogado() {
        Long empresaId = getUsuarioLogado().empresa_id();
        if (empresaId == null) {
            return null;
        }
        return empresaRepository.getReferenceById(empresaId);
    }


    public List<UsuarioResponse> listarTodosUsuariosTable(){

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() != null) {
            return usuarioRepository.getUsuariosByEmpresa_Id(usuarioLogado.empresa_id())
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }

        if (usuarioLogado.authorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return usuarioRepository.findById(usuarioLogado.id())
                    .map(usuario -> List.of(new UsuarioResponse(usuario)))
                    .orElse(List.of());
        }

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)) {
            return new CriarAdminResponse(0L, "Usuário salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioDomainService.validarUnicidade(usuarioAdminSalvar);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuário salvo com sucesso");
    }

    public UsuarioResponse criarPrimeiroUsuarioEmpresa(UsuarioRequest usuario, Long empresaId) {

        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (empresa == null) {
            return null;
        }

        Usuario usuarioBanco = new Usuario(usuario, empresa);
        usuarioDomainService.validarUnicidade(usuarioBanco);
        usuarioRepository.save(usuarioBanco);
        return new UsuarioResponse(usuarioBanco);
    }

    @Transactional
    public UsuarioResponse cadastrar(CadastroRequest request) {
        Usuario usuario = new Usuario(request);
        usuarioDomainService.validarUnicidade(usuario); // email + CPF

        usuarioRepository.save(usuario);

        Passageiro passageiro = new Passageiro(request, usuario);
        passageiroRepository.save(passageiro);

        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        if (id == 0L) {
            Empresa empresa = getEmpresaUsuarioLogado();
            Usuario usuarioBanco = new Usuario(usuario, empresa);
            usuarioDomainService.validarUnicidade(usuarioBanco);

            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        } else {
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if(usuarioBanco != null) {
                CPF cpf = new CPF(usuario.cpf());
                usuarioDomainService.validarUnicidade(id, usuario.email(), cpf);

                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(new CPF(usuario.cpf()));
                usuarioBanco.setEmail(usuario.email());
                if (usuario.senha() != null && !usuario.senha().isBlank()) {
                    usuarioBanco.setSenha(usuario.senha());
                }
                usuarioRepository.save(usuarioBanco);
                return new UsuarioResponse(usuarioBanco);
            }
        }
        return null;

    }

    public UsuarioResponse buscarPorId(Long id) {
        var usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario == null) {
            return null;
        }
        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;

    }

    public UsuarioResponse BuscarUsuarioLogado() {
        try{
            var usuarioLogado = getUsuarioLogado();
            return usuarioRepository.findById(usuarioLogado.id())
                    .stream()
                    .map(UsuarioResponse::new)
                    .findFirst().orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário logado");
        }
    }
}
