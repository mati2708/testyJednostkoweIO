package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integracyjne") // <--- DODAJ TĘ LINIJKĘ
@DisplayName("Zadanie 1: Testy Fasady bez mockow")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagerDanychTest {

    private static Dao dao;
    private static ManagerDanych manager;

    @BeforeAll
    static void setUpBeforeClass() {
        dao = new Dao();
        manager = new ManagerDanych(dao);
    }

    @AfterEach
    void tearDown() {
        dao.wyczyscBaze();
    }

    @Test
    @Order(1)
    @DisplayName("Rejestracja poprawnego zawodnika")
    void shouldRegisterCompetitor() {
        manager.zarejestrujZawodnika("Jan", "Kowalski", 100);
        String wynik = dao.pobierzZgloszenie(100);
        assertEquals("Jan Kowalski", wynik);
    }

    @ParameterizedTest
    @Order(2)
    @DisplayName("Rejestracja wielu zawodników")
    @CsvSource({"Anna, Nowak, 101", "Piotr, Zielinski, 102"})
    void shouldRegisterMultiple(String imie, String nazwisko, int id) {
        manager.zarejestrujZawodnika(imie, nazwisko, id);
        assertNotNull(dao.pobierzZgloszenie(id));
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Blad przy pustym imieniu")
    @ValueSource(strings = {"", "  "})
    void shouldThrowException(String zleImie) {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.zarejestrujZawodnika(zleImie, "Nowak", 200);
        });
    }
}