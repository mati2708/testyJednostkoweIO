package com.biegi.dao;

import com.biegi.model.Zawodnik;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    private static final List<Zawodnik> bazaDanych = new ArrayList<>();

    public void zapiszZgloszenie(int idWydarzenia, Zawodnik z) {
        System.out.println("DAO: Zapisuję " + z.getImie());
        bazaDanych.add(z);
    }

    public void wyczyscBaze() {
        bazaDanych.clear();
    }
}