package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusVoo;
import com.example.aeropass.application.DTOs.VooRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Voo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aviao_id",referencedColumnName = "id")
    public Aviao aviao;

    private String codigoVoo;

    private Long capacidade;

    private Long assentosDisponiveis;

    private String cidadeOrigem;

    private String cidadeDestino;

    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegadaPrevista;

    private LocalDateTime dataHoraChegada;

//    @OneToMany(mappedBy = "voo")
//    private List<Passagem> passagens;

    @ManyToMany
    @JoinTable(
            name = "tb_piloto_voo",
            joinColumns = @JoinColumn(name = "voo_id"),
            inverseJoinColumns = @JoinColumn(name = "piloto_id")
    )
    private Set<Piloto> pilotos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    private EnumStatusVoo status = EnumStatusVoo.ATIVO;

    public Voo(VooRequest voo, Aviao aviao, Set<Piloto> pilotos, Empresa empresa) {
        this.setEmpresa(empresa);
        this.setCodigoVoo(voo.codigoVoo());
        this.setCapacidade(voo.capacidade());
        this.setAssentosDisponiveis(voo.assentosDisponiveis());
        this.setCidadeOrigem(voo.cidadeOrigem());
        this.setCidadeDestino(voo.cidadeDestino());
        this.setDataHoraSaida(voo.dataHoraSaida());
        this.setDataHoraChegadaPrevista(voo.dataHoraChegadaPrevista());
        this.setAviao(aviao);
        this.setPilotos(pilotos);
    }

    public void setEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("O vôo deve estar associado a uma empresa!");
        }
        this.empresa = empresa;
    }

    public void setAviao(Aviao aviao) {
        if (aviao == null) {
            throw new IllegalArgumentException("O vôo deve estar associado a um avião!");
        }
        if (empresa != null && !empresa.getId().equals(aviao.getEmpresa().getId())) {
            throw new IllegalArgumentException("O avião deve pertencer à mesma empresa do vôo!");
        }
        this.aviao = aviao;
    }

    public void setPilotos(Set<Piloto> pilotos) {
        if (pilotos == null || pilotos.isEmpty()) {
            throw new IllegalArgumentException("O vôo deve ter pelo menos um piloto!");
        }
        if (empresa != null && pilotos.stream().anyMatch(p -> !empresa.getId().equals(p.getEmpresa().getId()))) {
            throw new IllegalArgumentException("Todos os pilotos devem pertencer à mesma empresa do vôo!");
        }
        this.pilotos = new HashSet<>(pilotos);
    }

    public void setCapacidade(Long capacidade) {
        if (capacidade == null || capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade do voo deve ser maior que zero!");
        }
        if (assentosDisponiveis != null && assentosDisponiveis > capacidade) {
            throw new IllegalArgumentException("A capacidade não pode ser menor que os assentos disponíveis!");
        }
        this.capacidade = capacidade;
    }

    public void setAssentosDisponiveis(Long assentosDisponiveis) {
        if (assentosDisponiveis == null || assentosDisponiveis < 0) {
            throw new IllegalArgumentException("Assentos disponíveis não podem ser negativos!");
        }
        if (capacidade != null && assentosDisponiveis > capacidade) {
            throw new IllegalArgumentException("Assentos disponíveis não podem ser maiores que a capacidade do voo!");
        }
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public void reservarAssento() {
        if (assentosDisponiveis == null || assentosDisponiveis <= 0) {
            throw new IllegalArgumentException("Não há assentos disponíveis neste voo!");
        }
        this.assentosDisponiveis--;
    }

    public void liberarAssento() {
        if (capacidade != null && assentosDisponiveis != null && assentosDisponiveis >= capacidade) {
            throw new IllegalArgumentException("Todos os assentos deste voo já estão disponíveis!");
        }
        this.assentosDisponiveis++;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        if (dataHoraSaida == null) {
            throw new IllegalArgumentException("A data e hora de saída são obrigatórias!");
        }
        if (dataHoraChegada != null && dataHoraChegada.isBefore(dataHoraSaida)) {
            throw new IllegalArgumentException("A data de saída não pode ser posterior à data de chegada!");
        }
        if (dataHoraChegadaPrevista != null && dataHoraChegadaPrevista.isBefore(dataHoraSaida)) {
            throw new IllegalArgumentException("A data de saída não pode ser posterior à data de chegada prevista!");
        }
        this.dataHoraSaida = dataHoraSaida;
    }


    public void setDataHoraChegada(LocalDateTime dataHoraChegada) {
        if (dataHoraChegada != null && dataHoraSaida != null && dataHoraChegada.isBefore(dataHoraSaida)) {
            throw new IllegalArgumentException("A data de chegada não pode ser anterior à data de saída!");
        }
        this.dataHoraChegada = dataHoraChegada;
    }

    public void setDataHoraChegadaPrevista(LocalDateTime dataHoraChegadaPrevista) {
        if (dataHoraChegadaPrevista == null) {
            throw new IllegalArgumentException("A data e hora de chegada prevista são obrigatórias!");
        }
        if (dataHoraSaida != null && dataHoraChegadaPrevista.isBefore(dataHoraSaida)) {
            throw new IllegalArgumentException("A data de chegada prevista não pode ser anterior à data de saída!");
        }
        this.dataHoraChegadaPrevista = dataHoraChegadaPrevista;
    }
    public void validarCriacaoDePassagem(Long empresaIdUsuario) {
        if (empresaIdUsuario != null) {
            if (this.empresa == null || !this.empresa.getId().equals(empresaIdUsuario)) {
                throw new IllegalArgumentException("O vôo não pertence à sua empresa!");
            }
            return;
        }

    }

}
