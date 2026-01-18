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


@Tag("jednostkowe") // <--- DODAJ TĘ LINIJKĘ@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class) // <--- TERAZ JEST W NOWEJ LINII I ZADZIAŁA!
@DisplayName("Zadanie 2: Testy Fasady z użyciem MOCKÓW")
class ManagerDanychMockTest {

    // @Mock - tworzy atrapę (fałszywe Dao). Ono nie ma w środku prawdziwej mapy!
    // Udaje, że działa, ale nic nie zapisuje.
    @Mock
    private Dao daoMock;

    // @InjectMocks - tworzy prawdziwego Managera, ale wstrzykuje mu do środka
    // naszą atrapę (daoMock) zamiast prawdziwego Dao.
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

        // THEN - weryfikacja interakcji
        // Sprawdzamy: "Hej, atrapą Dao, czy ktoś wywołał na tobie metodę zapiszZgloszenie?"
        // any(Zawodnik.class) oznacza: "z jakimkolwiek obiektem zawodnika"
        verify(daoMock, times(1)).zapiszZgloszenie(eq(id), any(Zawodnik.class));
    }

    @Test
    @DisplayName("Nie powinien ruszać Dao, gdy dane są błędne")
    void shouldNotTouchDaoWhenValidationFails() {
        // GIVEN - błędne imię
        String imie = ""; 
        
        // WHEN - próbujemy (wiemy, że poleci wyjątek, więc łapiemy go w try-catch lub ignorujemy w tym teście)
        try {
            manager.zarejestrujZawodnika(imie, "Kowalski", 500);
        } catch (IllegalArgumentException e) {
            // Ignorujemy błąd, bo interesuje nas czy Dao zostało dotknięte
        }

        // THEN
        // Sprawdzamy: "Czy Dao było używane?". Oczekujemy, że NIE (never).
        verify(daoMock, never()).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));
    }

    @Test
    @DisplayName("Symulacja błędu bazy danych")
    void shouldHandleDatabaseError() {
        // GIVEN
        // Uczymy atrapę: "Jak ktoś zawoła zapiszZgloszenie, to rzuć błędem RuntimeException"
        // To realizuje wymóg instrukcji: doThrow().when() dla metod void [cite: 8162]
        doThrow(new RuntimeException("Błąd połączenia z bazą"))
            .when(daoMock).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));

        // WHEN
        // Wywołujemy managera. On spróbuje zapisać, Dao rzuci błąd.
        // Manager nie obsługuje błędów (nie ma try-catch w kodzie), więc błąd powinien wylecieć wyżej.
        try {
            manager.zarejestrujZawodnika("Test", "Testowy", 999);
        } catch (RuntimeException e) {
            System.out.println("Złapano oczekiwany błąd z atrapy: " + e.getMessage());
        }

        // THEN
        // Upewniamy się, że Manager mimo błędu próbował zapisać (czyli doszedł do linijki z dao)
        verify(daoMock).zapiszZgloszenie(any(Integer.class), any(Zawodnik.class));
    }
}