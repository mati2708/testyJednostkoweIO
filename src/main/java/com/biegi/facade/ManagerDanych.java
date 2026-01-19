package com.biegi.facade;

import com.biegi.dao.Dao;
import com.biegi.external.SystemPlatnosci;
import com.biegi.external.SystemPowiadomien;
import com.biegi.model.Wydarzenie; // Upewnij się, że masz ten import!
import com.biegi.model.Zawodnik;

public class ManagerDanych {

    private final Dao dao;
    private final SystemPlatnosci platnosci;
    private final SystemPowiadomien powiadomienia;

    // Konstruktor bez parametrów (Dla aplikacji produkcyjnej)
    public ManagerDanych() {
        this.dao = new Dao();
        this.platnosci = new SystemPlatnosci();
        this.powiadomienia = new SystemPowiadomien();
    }

    // Konstruktor pełny (Dla testów - wstrzykiwanie zależności)
    public ManagerDanych(Dao dao, SystemPlatnosci platnosci, SystemPowiadomien powiadomienia) {
        this.dao = dao;
        this.platnosci = platnosci;
        this.powiadomienia = powiadomienia;
    }

    // --- FUNKCJONALNOŚĆ 1: Rejestracja Zawodnika (z Płatnościami) ---

    public void zarejestrujZawodnika(String imie, String nazwisko, int idWydarzenia) {
        // 1. Walidacja
        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("Imię nie może być puste");
        }

        // 2. Płatność
        boolean oplacono = platnosci.przetworzPlatnosc(99.00);
        if (!oplacono) {
            throw new IllegalStateException("Płatność odrzucona. Rejestracja anulowana.");
        }

        // 3. Zapis
        Zawodnik nowyZawodnik = new Zawodnik(imie, nazwisko);
        dao.zapiszZgloszenie(idWydarzenia, nowyZawodnik);

        // 4. Powiadomienie
        powiadomienia.wyslijEmail(imie + "@example.com", "Witaj na zawodach!");
    }

    // --- FUNKCJONALNOŚĆ 2: Tworzenie Wydarzenia (Tego Ci brakowało!) ---

    public void utworzWydarzenie(String nazwa, String data, String lokalizacja, int limit) {
        // 1. Walidacja danych
        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException("Nazwa wydarzenia nie może być pusta");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit musi być dodatni");
        }

        // 2. Sprawdzenie dostępności terminu w DAO
        if (dao.czyTerminZajety(data)) {
            throw new IllegalStateException("Termin " + data + " jest już zajęty");
        }

        // 3. Zapis wydarzenia
        Wydarzenie noweWydarzenie = new Wydarzenie(nazwa, data, lokalizacja, limit);
        dao.zapiszWydarzenie(noweWydarzenie);

        System.out.println("Utworzono wydarzenie: " + nazwa);
    }
}