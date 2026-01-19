package com.biegi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy warstwy modelu (Encje)")
class ZawodnikTest {

    @Test
    @DisplayName("Sprawdzenie poprawności tworzenia obiektu")
    void testKonstruktoraIGetterow() {
        // GIVEN - tworzymy obiekt
        String imie = "Adam";
        String nazwisko = "Małysz";

        // WHEN
        Zawodnik z = new Zawodnik(imie, nazwisko);

        // THEN - sprawdzamy czy dane się zgadzają
        assertAll("Weryfikacja pól",
                () -> assertEquals("Adam", z.getImie()),
                () -> assertEquals("Małysz", z.getNazwisko())
        );
    }
}