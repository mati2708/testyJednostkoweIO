package com.biegi;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.biegi") // ZMIANA: Szukamy w głównym pakiecie
@IncludeTags("mocki")
public class SuiteTylkoMocki {
}