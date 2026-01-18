package com.biegi;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.biegi.facade") // Wskazuje na warstwę kontroli/fasady
public class SuiteWarstwaKontroli {
}