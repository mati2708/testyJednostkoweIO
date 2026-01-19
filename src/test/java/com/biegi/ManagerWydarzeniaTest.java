package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.external.SystemPlatnosci; // Dodany import
import com.biegi.external.SystemPowiadomien; // Dodany import
import com.biegi.facade.ManagerDanych;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("logika")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Zadanie 1: Testy tworzenia wydarzeń (Integracyjne)")
class ManagerWydarzenTest {

    private ManagerDanych manager;
    private Dao dao;

    @BeforeEach
    void setUp() {
        // GIVEN
        dao = new Dao();
        dao.wyczyscWszystko(); // Czyścimy listę wydarzeń przed testem

        // Tworzymy atrapy systemów zewnętrznych (są wymagane przez konstruktor)
        SystemPlatnosci platnosci = new SystemPlatnosci();
        SystemPowiadomien powiadomienia = new SystemPowiadomien();

        // POPRAWKA: Przekazujemy 3 argumenty zamiast 1
        manager = new ManagerDanych(dao, platnosci, powiadomienia);
    }

    @Test
    @Order(1)
    @DisplayName("Scenariusz Główny: Poprawne utworzenie wydarzenia")
    void testUtworzWydarzenie_Sukces() {
        // GIVEN
        String nazwa = "Maraton Warszawski";
        String data = "2025-09-30";

        // WHEN & THEN
        assertDoesNotThrow(() -> {
            manager.utworzWydarzenie(nazwa, data, "Warszawa", 1000);
        });
    }

    @Test
    @Order(2)
    @DisplayName("Scenariusz Alternatywny: Termin zajęty")
    void testUtworzWydarzenie_TerminZajety() {
        // GIVEN - Mamy już jedno wydarzenie
        manager.utworzWydarzenie("Bieg Niepodległości", "2025-11-11", "Wroclaw", 500);

        // WHEN & THEN - Próba dodania drugiego w tej samej dacie
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            manager.utworzWydarzenie("Inny Bieg", "2025-11-11", "Poznan", 200);
        });

        assertEquals("Termin 2025-11-11 jest już zajęty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @Order(3)
    @DisplayName("Walidacja: Błędny limit uczestników")
    void testUtworzWydarzenie_BlednyLimit(int blednyLimit) {
        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> {
            manager.utworzWydarzenie("Test", "2025-01-01", "Dom", blednyLimit);
        });
    }
}