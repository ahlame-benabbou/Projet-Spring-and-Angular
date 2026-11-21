package com.mydefi.mydefi_backend.service;

import com.mydefi.mydefi_backend.dto.StockRequest;
import com.mydefi.mydefi_backend.model.Source;
import com.mydefi.mydefi_backend.model.Stock;
import com.mydefi.mydefi_backend.repository.MedicamentRepository;
import com.mydefi.mydefi_backend.repository.PharmacieRepository;
import com.mydefi.mydefi_backend.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final PharmacieRepository pharmacieRepository;
    private final MedicamentRepository medicamentRepository;

    // Crée le stock s'il n'existe pas, sinon le met à jour
    public Stock mettreAJour(StockRequest req) {
        Stock stock = stockRepository
                .findByPharmacieIdAndMedicamentId(req.pharmacieId(), req.medicamentId())
                .orElseGet(() -> {
                    Stock s = new Stock();
                    s.setPharmacie(pharmacieRepository.findById(req.pharmacieId())
                            .orElseThrow(() -> new IllegalArgumentException("Pharmacie introuvable")));
                    s.setMedicament(medicamentRepository.findById(req.medicamentId())
                            .orElseThrow(() -> new IllegalArgumentException("Médicament introuvable")));
                    return s;
                });
        stock.setStatut(req.statut());
        stock.setSource(Source.MANUEL);
        stock.setUpdatedAt(LocalDateTime.now());
        return stockRepository.save(stock);
    }
}