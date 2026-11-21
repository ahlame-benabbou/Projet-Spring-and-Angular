package com.mydefi.mydefi_backend.config;

import com.mydefi.mydefi_backend.model.*;
import com.mydefi.mydefi_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PharmacieRepository pharmacieRepo;
    private final MedicamentRepository medicamentRepo;
    private final StockRepository stockRepo;

    @Override
    public void run(String... args) throws Exception {
        if (medicamentRepo.count() == 0) importerMedicaments();
        if (pharmacieRepo.count() == 0) creerPharmacies();
        if (stockRepo.count() == 0) genererStocks();
    }

    // Lit src/main/resources/medicaments.csv et remplit la table medicament
    private void importerMedicaments() throws Exception {
        List<Medicament> liste = new ArrayList<>();
        Set<String> vus = new HashSet<>();   // évite les doublons nom + dosage

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("medicaments.csv").getInputStream(), StandardCharsets.UTF_8))) {
            br.readLine();   // saute la ligne d'en-tête
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.isBlank()) continue;
                String[] c = ligne.split(";", -1);
                if (c.length < 4 || c[0].isBlank()) continue;
                String cle = (c[0].trim() + "|" + c[1].trim()).toLowerCase();
                if (!vus.add(cle)) continue;
                liste.add(new Medicament(null, c[0].trim(), c[1].trim(), c[2].trim(), c[3].trim()));
            }
        }
        medicamentRepo.saveAll(liste);
        System.out.println(">>> " + liste.size() + " médicaments importés");
    }

    private void creerPharmacies() {
        pharmacieRepo.saveAll(List.of(
                new Pharmacie(null, "Pharmacie Centrale", "Av. Hassan II", "0535000001", 34.0331, -5.0003),
                new Pharmacie(null, "Pharmacie Al Amal", "Rue Sebou", "0535000002", 34.0450, -4.9800),
                new Pharmacie(null, "Pharmacie Atlas", "Bd Mohammed V", "0535000003", 34.0200, -5.0100),
                new Pharmacie(null, "Pharmacie Ibn Sina", "Av. des FAR", "0535000004", 34.0380, -4.9950),
                new Pharmacie(null, "Pharmacie Andalous", "Rue Ahmed Chawki", "0535000005", 34.0270, -5.0200)
        ));
    }

    // Chaque pharmacie a environ 70 % des médicaments, avec un statut aléatoire
    private void genererStocks() {
        Statut[] statuts = {Statut.DISPONIBLE, Statut.FAIBLE, Statut.INDISPONIBLE};
        Random random = new Random();
        List<Stock> stocks = new ArrayList<>();
        for (Pharmacie p : pharmacieRepo.findAll()) {
            for (Medicament m : medicamentRepo.findAll()) {
                if (random.nextInt(100) >= 70) continue;
                Stock s = new Stock();
                s.setPharmacie(p);
                s.setMedicament(m);
                s.setStatut(statuts[random.nextInt(statuts.length)]);
                s.setSource(Source.MANUEL);
                s.setUpdatedAt(LocalDateTime.now().minusHours(random.nextInt(30)));
                stocks.add(s);
            }
        }
        stockRepo.saveAll(stocks);
    }
}