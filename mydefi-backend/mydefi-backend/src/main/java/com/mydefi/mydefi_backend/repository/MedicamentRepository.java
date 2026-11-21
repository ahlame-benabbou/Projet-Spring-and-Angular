package com.mydefi.mydefi_backend.repository;

import com.mydefi.mydefi_backend.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    boolean existsByNomIgnoreCaseAndDosageIgnoreCase(String nom, String dosage);
    // Autocomplétion : médicaments dont le nom contient le texte saisi
    List<Medicament> findTop10ByNomContainingIgnoreCase(String nom);
}