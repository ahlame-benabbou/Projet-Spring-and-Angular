package com.mydefi.mydefi_backend.repository;

import com.mydefi.mydefi_backend.model.Statut;
import com.mydefi.mydefi_backend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByPharmacieIdAndMedicamentId(Long pharmacieId, Long medicamentId);

    // Formule de Haversine : distance en km entre l'utilisateur et chaque pharmacie
    @Query(value = """
        SELECT p.id AS "id", p.nom AS "nom", p.adresse AS "adresse",
               p.telephone AS "telephone", p.latitude AS "latitude",
               p.longitude AS "longitude", s.statut AS "statut",
               s.updated_at AS "updatedAt",
               (6371 * acos(least(1.0,
                   cos(radians(:lat)) * cos(radians(p.latitude))
                   * cos(radians(p.longitude) - radians(:lng))
                   + sin(radians(:lat)) * sin(radians(p.latitude))))) AS "distanceKm"
        FROM stock s
        JOIN pharmacie p ON p.id = s.pharmacie_id
        JOIN medicament m ON m.id = s.medicament_id
        WHERE LOWER(m.nom) LIKE LOWER(CONCAT('%', :nom, '%'))
          AND s.statut IN ('DISPONIBLE', 'FAIBLE')
        ORDER BY "distanceKm"
        LIMIT :limite
        """, nativeQuery = true)
    List<PharmacieProche> rechercherProches(@Param("nom") String nom,
                                            @Param("lat") double lat,
                                            @Param("lng") double lng,
                                            @Param("limite") int limite);

    // Pour l'expiration automatique (étape 7)
    @Modifying
    @Query("UPDATE Stock s SET s.statut = :nouveau WHERE s.updatedAt < :limite AND s.statut <> :nouveau")
    int expirerAnciens(@Param("limite") LocalDateTime limite, @Param("nouveau") Statut nouveau);
}