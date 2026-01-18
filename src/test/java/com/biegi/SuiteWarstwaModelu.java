package com.biegi;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.biegi.model") // Wskazuje na warstwę encji
public class SuiteWarstwaModelu {
}