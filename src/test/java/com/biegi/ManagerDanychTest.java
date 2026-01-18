package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("logika")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagerDanychTest {

    private ManagerDanych manager;
    private Dao dao;

    @BeforeEach
    void setUp() {
        // GIVEN
        dao = new Dao();
        manager = new ManagerDanych(dao);
    }

    @Test
    @Order(1)
    void testKonstrukcji() {
        // WHEN & THEN
        assertNotNull(manager, "Manager nie powinien być nullem");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\n"})
    @Order(2)
    void testZarejestrujZawodnika_BledneImie(String niepoprawneImie) {
        // GIVEN
        String nazwisko = "Nowak";
        int idWydarzenia = 1;

        // WHEN & THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.zarejestrujZawodnika(niepoprawneImie, nazwisko, idWydarzenia);
        });

        assertEquals("Imię nie może być puste", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Jan, Kowalski, 100",
            "Anna, Nowak, 200",
            "Piotr, O'Connor, 300"
    })
    @Order(3)
    void testZarejestrujZawodnika_PoprawneDane(String imie, String nazwisko, int id) {
        // WHEN & THEN
        assertDoesNotThrow(() -> {
            manager.zarejestrujZawodnika(imie, nazwisko, id);
        });
    }
}