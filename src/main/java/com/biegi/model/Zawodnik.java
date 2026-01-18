package com.biegi.model;
public class Zawodnik {
    private String imie;
    private String nazwisko;
    public Zawodnik(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
}