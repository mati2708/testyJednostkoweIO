package com.biegi;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.biegi") // ZMIANA: Szukamy w głównym pakiecie, gdzie są Twoje testy
public class SuiteWarstwaKontroli {
}