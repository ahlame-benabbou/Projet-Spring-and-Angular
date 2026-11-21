package com.mydefi.mydefi_backend.service;

import com.mydefi.mydefi_backend.dto.PharmacieResultat;
import com.mydefi.mydefi_backend.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RechercheService {

    private final StockRepository stockRepository;

    public List<PharmacieResultat> rechercher(String medicament, double lat, double lng) {
        return stockRepository.rechercherProches(medicament, lat, lng, 20).stream()
                .map(p -> new PharmacieResultat(p.getId(), p.getNom(), p.getAdresse(),
                        p.getTelephone(), p.getLatitude(), p.getLongitude(),
                        p.getStatut(), p.getUpdatedAt(), p.getDistanceKm()))
                .toList();
    }
}