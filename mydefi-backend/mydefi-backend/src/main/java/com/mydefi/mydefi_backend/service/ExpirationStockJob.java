package com.mydefi.mydefi_backend.service;

import com.mydefi.mydefi_backend.model.Statut;
import com.mydefi.mydefi_backend.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ExpirationStockJob {

    private final StockRepository stockRepository;

    // Toutes les heures : stock non mis à jour depuis 24 h -> NON_CONFIRME
    @Scheduled(fixedRate = 3_600_000)
    @Transactional
    public void expirer() {
        stockRepository.expirerAnciens(LocalDateTime.now().minusHours(24), Statut.NON_CONFIRME);
    }
}