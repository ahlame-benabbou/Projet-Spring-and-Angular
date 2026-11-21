package com.mydefi.mydefi_backend.repository;

import java.time.LocalDateTime;

public interface PharmacieProche {
    Long getId();
    String getNom();
    String getAdresse();
    String getTelephone();
    Double getLatitude();
    Double getLongitude();
    String getStatut();
    LocalDateTime getUpdatedAt();
    Double getDistanceKm();
}