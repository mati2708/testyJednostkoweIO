package com.biegi.dao;

import com.biegi.model.Zawodnik;
import java.util.HashMap;
import java.util.Map;

public class Dao {
    // To jest nasza "udawana" baza danych w pamięci
    private Map<Integer, String> tabelaZgloszen = new HashMap<>();

    // TEJ METODY BRAKOWAŁO: Zapisuje zawodnika
    public void zapiszZgloszenie(int idWydarzenia, Zawodnik zawodnik) {
        String rekord = zawodnik.getImie() + " " + zawodnik.getNazwisko();
        tabelaZgloszen.put(idWydarzenia, rekord);
    }

    // TEJ METODY BRAKOWAŁO: Pobiera dane do sprawdzenia w teście
    public String pobierzZgloszenie(int idWydarzenia) {
        return tabelaZgloszen.get(idWydarzenia);
    }
    
    // TEJ METODY BRAKOWAŁO: Czyści bazę po teście
    public void wyczyscBaze() {
        tabelaZgloszen.clear();
    }
}