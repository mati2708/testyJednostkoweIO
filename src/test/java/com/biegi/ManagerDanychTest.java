package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.external.SystemPlatnosci;
import com.biegi.external.SystemPowiadomien;
import com.biegi.facade.ManagerDanych;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("logika")
@DisplayName("Zadanie 1: Testy integracyjne (bez mocków)")
class ManagerDanychTest {

    private ManagerDanych manager;
    private Dao dao;

    @BeforeEach
    void setUp() {
        // GIVEN - tworzymy wszystkie zależności
        dao = new Dao();

        // Tworzymy prawdziwe instancje systemów zewnętrznych (symulacje)
        SystemPlatnosci platnosci = new SystemPlatnosci();
        SystemPowiadomien powiadomienia = new SystemPowiadomien();

        // Konstruktor teraz przyjmuje 3 argumenty - to naprawia Twój błąd!
        manager = new ManagerDanych(dao, platnosci, powiadomienia);
    }

    @Test
    @Order(1)
    @DisplayName("Sprawdzenie, czy manager został poprawnie utworzony")
    void testKonstrukcji() {
        assertNotNull(manager, "Manager nie powinien być nullem");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\n"})
    @Order(2)
    @DisplayName("Walidacja: Puste imię powinno rzucić wyjątek (ValueSource)")
    void testZarejestrujZawodnika_BledneImie_ValueSource(String niepoprawneImie) {
        String nazwisko = "Nowak";
        int idWydarzenia = 1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.zarejestrujZawodnika(niepoprawneImie, nazwisko, idWydarzenia);
        });

        assertEquals("Imię nie może być puste", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Jan, Kowalski, 100",
            "Anna, Nowak, 200"
    })
    @Order(3)
    @DisplayName("Rejestracja: Poprawne dane nie rzucają wyjątku (CsvSource)")
    void testZarejestrujZawodnika_PoprawneDane_CsvSource(String imie, String nazwisko, int id) {
        assertDoesNotThrow(() -> {
            manager.zarejestrujZawodnika(imie, nazwisko, id);
        }, "Poprawne dane nie powinny powodować błędu");
    }
}