package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import com.biegi.model.Zawodnik;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

// Użyj tagu "mocki", aby SuiteTylkoMocki.java go wykrył
@Tag("mocki")
@ExtendWith(MockitoExtension.class)
@DisplayName("Zadanie 2: Testy Fasady z użyciem MOCKÓW")
class ManagerDanychMockTest {

    @Mock
    private Dao daoMock;

    @InjectMocks
    private ManagerDanych manager;

    @Test
    @DisplayName("Powinien wywołać zapis w Dao dokładnie raz")
    void shouldCallDaoSaveOnce() {
        // GIVEN
        String imie = "Adam";
        String nazwisko = "Malysz";
        int id = 500;

        // WHEN
        manager.zarejestrujZawodnika(imie, nazwisko, id);

        // THEN
        // Weryfikacja: czy metoda zapiszZgloszenie została wywołana raz z odpowiednim ID?
        verify(daoMock, times(1)).zapiszZgloszenie(eq(id), any(Zawodnik.class));
    }

    @Test
    @DisplayName("Nie powinien ruszać Dao, gdy dane są błędne (puste imię)")
    void shouldNotTouchDaoWhenValidationFails() {
        // GIVEN
        String imie = "";

        // WHEN
        try {
            manager.zarejestrujZawodnika(imie, "Kowalski", 500);
        } catch (IllegalArgumentException e) {
            // Ignorujemy wyjątek, bo testujemy zachowanie Dao, a nie sam wyjątek
        }

        // THEN
        // Upewniamy się, że Dao NIE zostało wywołane
        verify(daoMock, never()).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));
    }

    @Test
    @DisplayName("Symulacja błędu bazy danych (doThrow)")
    void shouldHandleDatabaseError() {
        // GIVEN
        // Konfiguracja stuba dla metody void: rzuć wyjątek przy próbie zapisu
        doThrow(new RuntimeException("Błąd połączenia z bazą"))
                .when(daoMock).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));

        // WHEN
        try {
            manager.zarejestrujZawodnika("Test", "Testowy", 999);
        } catch (RuntimeException e) {
            // Oczekujemy, że błąd poleci wyżej (bo manager go nie łapie)
        }

        // THEN
        // Weryfikujemy, że Manager mimo wszystko spróbował wywołać Dao
        verify(daoMock).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));
    }
}