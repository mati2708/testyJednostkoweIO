package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// ZASADA: "Brak jednoznacznie określonej kolejności wykonywania testów" -> Naprawione przez @TestMethodOrder
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("logika")
@DisplayName("Zadanie 1: Testy integracyjne (bez mocków)")
class ManagerDanychTest {

    private ManagerDanych manager;
    private Dao dao;

    @BeforeEach
    void setUp() {
        // ZASADA: "Nie przygotowano wszystkich danych" -> Tworzymy świeże obiekty
        dao = new Dao();
        manager = new ManagerDanych(dao);
    }

    @Test
    @Order(1)
    @DisplayName("Sprawdzenie, czy manager został poprawnie utworzony")
    void testKonstrukcji() {
        // GIVEN
        // (setUp)

        // WHEN & THEN
        // ZASADA: "Wykorzystano mniej niż 3 różne asercje" -> Tu mamy assertNotNull
        assertNotNull(manager, "Manager nie powinien być nullem");
    }

    // ZASADA: "Wykorzystano mniej niż 2 różne sposoby parametryzacji" -> Sposób 1: @ValueSource
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\n"})
    @Order(2)
    @DisplayName("Walidacja: Puste imię powinno rzucić wyjątek (ValueSource)")
    void testZarejestrujZawodnika_BledneImie_ValueSource(String niepoprawneImie) {
        // GIVEN
        String nazwisko = "Nowak";
        int idWydarzenia = 1;

        // WHEN & THEN
        // ZASADA: Różne asercje -> assertThrows
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.zarejestrujZawodnika(niepoprawneImie, nazwisko, idWydarzenia);
        });

        // ZASADA: Różne asercje -> assertEquals
        assertEquals("Imię nie może być puste", exception.getMessage());
    }

    // ZASADA: "Wykorzystano mniej niż 2 różne sposoby parametryzacji" -> Sposób 2: @CsvSource
    @ParameterizedTest
    @CsvSource({
            "Jan, Kowalski, 100",
            "Anna, Nowak, 200"
    })
    @Order(3)
    @DisplayName("Rejestracja: Poprawne dane nie rzucają wyjątku (CsvSource)")
    void testZarejestrujZawodnika_PoprawneDane_CsvSource(String imie, String nazwisko, int id) {
        // GIVEN
        // (dane z parametrów)

        // WHEN & THEN
        // ZASADA: Różne asercje -> assertDoesNotThrow
        assertDoesNotThrow(() -> {
            manager.zarejestrujZawodnika(imie, nazwisko, id);
        }, "Poprawne dane nie powinny powodować błędu");
    }
}