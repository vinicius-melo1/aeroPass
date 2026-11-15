    package com.example.aeropass.domain.entities;

    import com.example.aeropass.application.DTOs.*;
    import com.example.aeropass.domain.valueObject.CPF;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.NoArgsConstructor;
    import org.jspecify.annotations.Nullable;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public class Usuario implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;

        @Embedded
        private CPF cpf;

        private String senha;

        private String email;

        private String role = "ROLE_USER";

        // FetchTypeEager = carrega a próxima tabela junto no select
        // FetchTypeLazy = carrega as tabelas no select conforme a necessidade
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "empresa_id",referencedColumnName = "id")
        private Empresa empresa;

        private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

        public void setEmail(String email) {
            if(!email.isEmpty() && !email.contains("@")) {
                throw new IllegalArgumentException("Email inválido!");
            }
            this.email = email;
        }
        public Usuario(CriarAdminRequest criarAdminRequest) {
            this.setCpf(new CPF(criarAdminRequest.cpf()));
            this.setNome(criarAdminRequest.nome());
            this.setSenha(criarAdminRequest.senha());
            this.setEmail(criarAdminRequest.email());
            this.setRole("ROLE_ADMIN");
        }

        public Usuario(UsuarioRequest usuario) {
            this.setCpf(new CPF(usuario.cpf()));
            this.setNome(usuario.nome());
            this.setSenha(usuario.senha());
            this.setEmail(usuario.email());
            this.setRole("ROLE_USER");
        }
        public Usuario(CadastroRequest request) {
            this.setCpf(new CPF(request.cpf()));
            this.setNome(request.nome());
            this.setSenha(request.senha());
            this.setEmail(request.email());
            this.setRole("ROLE_CLIENTE");
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(this.role));
        }

        @Override
        public @Nullable String getPassword() {
            return "";
        }

        @Override
        public String getUsername() {
            return "";
        }
    }
