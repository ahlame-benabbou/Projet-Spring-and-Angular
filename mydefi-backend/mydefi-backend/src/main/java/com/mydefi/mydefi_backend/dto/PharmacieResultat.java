package com.mydefi.mydefi_backend.dto;

import java.time.LocalDateTime;

public record PharmacieResultat(Long id, String nom, String adresse, String telephone,
                                double latitude, double longitude, String statut,
                                LocalDateTime updatedAt, double distanceKm) {}