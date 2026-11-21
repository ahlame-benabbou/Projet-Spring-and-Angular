package com.mydefi.mydefi_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"pharmacie_id", "medicament_id"}))
@Getter @Setter @NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pharmacie pharmacie;

    @ManyToOne(optional = false)
    private Medicament medicament;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Enumerated(EnumType.STRING)
    private Source source;

    private LocalDateTime updatedAt;
}