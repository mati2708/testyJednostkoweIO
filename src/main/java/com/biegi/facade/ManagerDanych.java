package com.biegi.facade;

import com.biegi.dao.Dao;
import com.biegi.model.Zawodnik;

public class ManagerDanych {
    private Dao dao;

    public ManagerDanych(Dao dao) {
        this.dao = dao;
    }

    public void zarejestrujZawodnika(String imie, String nazwisko, int idWydarzenia) {
        // ZMIANA TUTAJ: używamy isBlank() zamiast isEmpty()
        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("Imię nie może być puste");
        }
        Zawodnik nowyZawodnik = new Zawodnik(imie, nazwisko);
        dao.zapiszZgloszenie(idWydarzenia, nowyZawodnik);
    }
}