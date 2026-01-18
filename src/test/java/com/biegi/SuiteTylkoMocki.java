package com.biegi;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Szybki Raport: Tylko testy jednostkowe (Mocki)")
@SelectPackages("com.biegi") // Gdzie szukać?
@IncludeTags("jednostkowe")  // Co włączyć? Tylko te z etykietą "jednostkowe"
public class SuiteTylkoMocki {
    // Pusta klasa
}