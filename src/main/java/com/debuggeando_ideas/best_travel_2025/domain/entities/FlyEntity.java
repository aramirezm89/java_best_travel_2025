package com.debuggeando_ideas.best_travel_2025.domain.entities;

import com.debuggeando_ideas.best_travel_2025.util.Aeroline;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "fly")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;

    @Column(length = 20)
    private String originName;

    @Column(length = 20)
    private String destinyName;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Aeroline aeroLine;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fly", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TicketEntity> tickets;
}
