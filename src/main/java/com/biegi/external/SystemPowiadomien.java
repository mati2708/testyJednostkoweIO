package com.biegi.external;

public class SystemPowiadomien {
    public void wyslijEmail(String adresat, String temat) {
        // Symulacja: w prawdziwym świecie łączyłby się z serwerem SMTP
        System.out.println("EMAIL: Wysłano wiadomość do " + adresat + " o treści: " + temat);
    }
}