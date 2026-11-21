package com.mydefi.mydefi_backend.dto;

import com.mydefi.mydefi_backend.model.Statut;
import jakarta.validation.constraints.NotNull;

public record StockRequest(@NotNull Long pharmacieId,
                           @NotNull Long medicamentId,
                           @NotNull Statut statut) {}