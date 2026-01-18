package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import com.biegi.model.Zawodnik;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("mocki")
@ExtendWith(MockitoExtension.class)
class ManagerDanychMockTest {

    @Mock
    private Dao daoMock;

    @InjectMocks
    private ManagerDanych manager;

    @Test
    void shouldCallDaoSaveOnce() {
        // GIVEN
        String imie = "Adam";
        String nazwisko = "Małysz";
        int id = 500;

        // WHEN
        manager.zarejestrujZawodnika(imie, nazwisko, id);

        // THEN
        verify(daoMock, times(1)).zapiszZgloszenie(eq(id), any(Zawodnik.class));
    }

    @Test
    void shouldNotTouchDaoWhenValidationFails() {
        // GIVEN
        String imie = "";

        // WHEN
        try {
            manager.zarejestrujZawodnika(imie, "Kowalski", 500);
        } catch (IllegalArgumentException e) {
            // Ignorujemy błąd walidacji
        }

        // THEN
        verify(daoMock, never()).zapiszZgloszenie(anyInt(), any(Zawodnik.class));
    }

    @Test
    void shouldHandleDatabaseError() {
        // GIVEN
        doThrow(new RuntimeException("Błąd DB"))
                .when(daoMock).zapiszZgloszenie(anyInt(), any(Zawodnik.class));

        // WHEN & THEN
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            manager.zarejestrujZawodnika("Test", "Testowy", 999);
        });

        verify(daoMock).zapiszZgloszenie(anyInt(), any(Zawodnik.class));
    }
}