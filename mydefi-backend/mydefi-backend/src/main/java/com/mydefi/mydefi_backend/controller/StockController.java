package com.mydefi.mydefi_backend.controller;

import com.mydefi.mydefi_backend.dto.StockRequest;
import com.mydefi.mydefi_backend.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    private final StockService stockService;

    // PUT /api/stocks (sera réservé aux pharmaciens avec l'authentification)
    @PutMapping
    public ResponseEntity<Void> mettreAJour(@Valid @RequestBody StockRequest req) {
        stockService.mettreAJour(req);
        return ResponseEntity.noContent().build();
    }
}