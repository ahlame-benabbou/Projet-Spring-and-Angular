package com.mydefi.mydefi_backend.repository;

import com.mydefi.mydefi_backend.model.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacieRepository extends JpaRepository<Pharmacie, Long> {
}