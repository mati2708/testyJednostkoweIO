package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.facade.ManagerDanych;
import com.biegi.model.Wydarzenie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("mocki")
@ExtendWith(MockitoExtension.class)
@DisplayName("Zadanie 2: Testy tworzenia wydarzeń (Mocki)")
class ManagerWydarzenMockTest {

    @Mock
    private Dao daoMock;

    @InjectMocks
    private ManagerDanych manager;

    @Test
    @DisplayName("Powinien zapisać wydarzenie, gdy termin jest wolny")
    void shouldSaveEventWhenDateIsFree() {
        // GIVEN
        String data = "2026-05-01";
        // Uczymy atrapę: "Gdy zapytam o datę, powiedz że jest wolna (false)"
        when(daoMock.czyTerminZajety(data)).thenReturn(false);

        // WHEN
        manager.utworzWydarzenie("Majówka", data, "Las", 50);

        // THEN
        // 1. Sprawdzamy, czy manager zapytał o dostępność
        verify(daoMock).czyTerminZajety(data);
        // 2. Sprawdzamy, czy manager zlecił zapis
        verify(daoMock).zapiszWydarzenie(any(Wydarzenie.class));
    }

    @Test
    @DisplayName("Powinien rzucić błąd i NIE zapisywać, gdy termin zajęty")
    void shouldThrowExceptionWhenDateIsOccupied() {
        // GIVEN
        String zajetaData = "2026-12-24";
        // Uczymy atrapę: "Ten termin jest zajęty! (true)"
        when(daoMock.czyTerminZajety(zajetaData)).thenReturn(true);

        // WHEN & THEN
        assertThrows(IllegalStateException.class, () -> {
            manager.utworzWydarzenie("Wigilia", zajetaData, "Dom", 10);
        });

        // Weryfikacja
        verify(daoMock).czyTerminZajety(zajetaData); // Zapytał? Tak.
        verify(daoMock, never()).zapiszWydarzenie(any(Wydarzenie.class)); // Zapisał? NIE! (Sukces)
    }
}