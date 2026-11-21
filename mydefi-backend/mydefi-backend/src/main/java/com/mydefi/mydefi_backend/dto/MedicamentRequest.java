package com.mydefi.mydefi_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicamentRequest(@NotBlank String nom,
                                @NotBlank String dosage,
                                String forme,
                                String laboratoire) {}