package com.biegi;

import com.biegi.dao.Dao;
import com.biegi.external.SystemPlatnosci;
import com.biegi.external.SystemPowiadomien;
import com.biegi.facade.ManagerDanych;
import com.biegi.model.Zawodnik;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("mocki")
@ExtendWith(MockitoExtension.class)
@DisplayName("Zadanie 2: Testy Rejestracji z Płatnością i Emailem")
class ManagerDanychMockTest {

    @Mock
    private Dao daoMock;

    @Mock
    private SystemPlatnosci platnosciMock; // Mock banku

    @Mock
    private SystemPowiadomien powiadomieniaMock; // Mock emaila

    @InjectMocks
    private ManagerDanych manager; // Mockito wstrzyknie wszystkie 3 mocki tutaj!

    @Test
    @DisplayName("Scenariusz Pozytywny: Płatność OK -> Zapis -> Email")
    void shouldCompleteRegistrationWhenPaymentSucceeds() {
        // GIVEN
        // Uczymy bank: "Jak zapytam, powiedz że masz kasę (true)"
        when(platnosciMock.przetworzPlatnosc(anyDouble())).thenReturn(true);

        // WHEN
        manager.zarejestrujZawodnika("Jan", "Kowalski", 1);

        // THEN
        // 1. Sprawdzamy czy pobrano opłatę
        verify(platnosciMock).przetworzPlatnosc(anyDouble());
        // 2. Sprawdzamy czy zapisano w bazie
        verify(daoMock).zapiszZgloszenie(eq(1), any(Zawodnik.class));
        // 3. Sprawdzamy czy wysłano maila
        verify(powiadomieniaMock).wyslijEmail(contains("Jan"), anyString());
    }

    @Test
    @DisplayName("Scenariusz Negatywny: Brak środków -> Brak zapisu -> Brak maila")
    void shouldAbortRegistrationWhenPaymentFails() {
        // GIVEN
        // Uczymy bank: "Odrzuć płatność (false)"
        when(platnosciMock.przetworzPlatnosc(anyDouble())).thenReturn(false);

        // WHEN & THEN
        // Oczekujemy błędu IllegalStateException (zdefiniowanego w Managerze)
        assertThrows(IllegalStateException.class, () -> {
            manager.zarejestrujZawodnika("Biedny", "Student", 1);
        });

        // WERYFIKACJA SKUTKÓW:
        // 1. Bank był pytany
        verify(platnosciMock).przetworzPlatnosc(anyDouble());

        // 2. ALE do bazy nic nie powinno trafić! (never)
        verify(daoMock, never()).zapiszZgloszenie(anyInt(), any(Zawodnik.class));

        // 3. I mail NIE powinien wyjść! (never)
        verify(powiadomieniaMock, never()).wyslijEmail(anyString(), anyString());
    }
}