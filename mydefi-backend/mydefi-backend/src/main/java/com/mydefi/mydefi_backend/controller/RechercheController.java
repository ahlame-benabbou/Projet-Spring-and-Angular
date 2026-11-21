package com.mydefi.mydefi_backend.controller;
import com.mydefi.mydefi_backend.dto.MedicamentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mydefi.mydefi_backend.dto.PharmacieResultat;
import com.mydefi.mydefi_backend.model.Medicament;
import com.mydefi.mydefi_backend.repository.MedicamentRepository;
import com.mydefi.mydefi_backend.service.RechercheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RechercheController {

    private final RechercheService rechercheService;
    private final MedicamentRepository medicamentRepository;
    // POST /api/medicaments : ajoute un médicament absent du catalogue
    @PostMapping("/medicaments")
    public ResponseEntity<Medicament> ajouter(@Valid @RequestBody MedicamentRequest req) {
        String nom = req.nom().trim();
        String dosage = req.dosage().trim();

        if (medicamentRepository.existsByNomIgnoreCaseAndDosageIgnoreCase(nom, dosage)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();   // 409 : existe déjà
        }
        Medicament m = new Medicament(null, nom, dosage,
                req.forme() == null ? "" : req.forme().trim(),
                req.laboratoire() == null ? "" : req.laboratoire().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentRepository.save(m));
    }
    // GET /api/pharmacies?medicament=doliprane&lat=34.03&lng=-5.0
    @GetMapping("/pharmacies")
    public List<PharmacieResultat> rechercher(@RequestParam String medicament,
                                              @RequestParam double lat,
                                              @RequestParam double lng) {
        return rechercheService.rechercher(medicament, lat, lng);
    }

    // GET /api/medicaments?q=dol (autocomplétion)
    @GetMapping("/medicaments")
    public List<Medicament> suggestions(@RequestParam String q) {
        return medicamentRepository.findTop10ByNomContainingIgnoreCase(q);
    }
}