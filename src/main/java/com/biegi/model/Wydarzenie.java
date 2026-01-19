package com.biegi.model;

public class Wydarzenie {
    private String nazwa;
    private String data; // Uproszczenie: String zamiast LocalDate dla łatwiejszych testów
    private String lokalizacja;
    private int limitUczestnikow;

    public Wydarzenie(String nazwa, String data, String lokalizacja, int limitUczestnikow) {
        this.nazwa = nazwa;
        this.data = data;
        this.lokalizacja = lokalizacja;
        this.limitUczestnikow = limitUczestnikow;
    }

    public String getNazwa() { return nazwa; }
    public String getData() { return data; }
    public String getLokalizacja() { return lokalizacja; }
    public int getLimitUczestnikow() { return limitUczestnikow; }
}