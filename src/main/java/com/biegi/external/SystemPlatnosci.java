package com.biegi.external;

public class SystemPlatnosci {
    public boolean przetworzPlatnosc(double kwota) {
        // Symulacja: w prawdziwym świecie łączyłby się z bankiem
        System.out.println("BANK: Przetwarzanie płatności na kwotę: " + kwota);
        return true; // Domyślnie sukces
    }
}