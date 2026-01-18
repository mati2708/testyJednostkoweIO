package com.biegi;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite // To oznacza, że to jest Zestaw
@SuiteDisplayName("Wielki Raport: Uruchomienie wszystkich testów w projekcie")
@SelectPackages("com.biegi") // Wybiera wszystkie testy z tego folderu/pakietu
public class SuiteWszystkieTesty {
    // Ta klasa musi być pusta w środku!
}