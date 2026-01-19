package com.biegi.dao;

import com.biegi.model.Wydarzenie;
import com.biegi.model.Zawodnik;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    // Stara lista (zmieniona na listę obiektów dla zgodności z diagramem)
    private static final List<Zawodnik> bazaDanych = new ArrayList<>();

    // NOWA LISTA: Wydarzenia
    private static final List<Wydarzenie> bazaWydarzen = new ArrayList<>();

    // --- Metody dla ZAWODNIKÓW ---

    public void zapiszZgloszenie(int idWydarzenia, Zawodnik z) {
        System.out.println("DAO: Zapisuję zawodnika " + z.getImie());
        bazaDanych.add(z);
    }

    // --- Metody dla WYDARZEŃ (Potrzebne do nowych testów) ---

    public boolean czyTerminZajety(String data) {
        for (Wydarzenie w : bazaWydarzen) {
            if (w.getData().equals(data)) {
                return true;
            }
        }
        return false;
    }

    public void zapiszWydarzenie(Wydarzenie w) {
        System.out.println("DAO: Tworzę wydarzenie: " + w.getNazwa() + " w dniu " + w.getData());
        bazaWydarzen.add(w);
    }

    // --- Czyszczenie (Wspólne) ---
    public void wyczyscWszystko() {
        bazaDanych.clear();
        bazaWydarzen.clear();
    }

    // Stara nazwa dla kompatybilności wstecznej (jeśli gdzieś została użyta)
    public void wyczyscBaze() {
        wyczyscWszystko();
    }
}