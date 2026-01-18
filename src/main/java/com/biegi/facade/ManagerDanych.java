package com.biegi.facade;

import com.biegi.dao.Dao;
import com.biegi.model.Zawodnik;

public class ManagerDanych {

    private final Dao dao;

    // Konstruktor używany przez aplikację
    public ManagerDanych() {
        this.dao = new Dao();
    }

    // Konstruktor używany przez testy (do wstrzykiwania mocków)
    public ManagerDanych(Dao dao) {
        this.dao = dao;
    }

    public void zarejestrujZawodnika(String imie, String nazwisko, int idWydarzenia) {
        // Walidacja
        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("Imię nie może być puste");
        }

        // Utworzenie obiektu
        Zawodnik nowyZawodnik = new Zawodnik(imie, nazwisko);

        // Zapis w DAO
        dao.zapiszZgloszenie(idWydarzenia, nowyZawodnik);
    }
}