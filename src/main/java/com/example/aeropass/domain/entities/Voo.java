package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusVoo;
import com.example.aeropass.application.DTOs.VooRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private EnumStatusVoo status = EnumStatusVoo.ATIVO;

    public Voo(VooRequest voo) {
        this.setCodigoVoo(voo.codigoVoo());
        this.setCapacidade(voo.capacidade());
        this.setAssentosDisponiveis(voo.assentosDisponiveis());
        this.setCidadeOrigem(voo.cidadeOrigem());
        this.setCidadeDestino(voo.cidadeDestino());
        this.setDataHoraSaida(voo.dataHoraSaida());
        this.setDataHoraChegada(voo.dataHoraChegada());
    }

    public void setAviao(Aviao aviao) {
        if (aviao == null) {
            throw new IllegalArgumentException("O vôo deve estar associado a um avião!");
        }
        this.aviao = aviao;
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



}
