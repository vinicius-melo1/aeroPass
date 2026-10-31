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
}
