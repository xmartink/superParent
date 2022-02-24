package com.phonecompany.billing;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TestTelephoneBillCalculatorImpl {

    private final TelephoneBillCalculatorImpl telephoneBillCalculator;

    public TestTelephoneBillCalculatorImpl() {
        this.telephoneBillCalculator = new TelephoneBillCalculatorImpl();
    }

    @Test
    void calculate() {
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test.csv"));
        try {
            System.out.println(telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate2() {//pulnoc
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test2.csv"));
        try {
            assertEquals(new BigDecimal("1.50"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate3() {//mimo interval bez 5 minut
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test3.csv"));
        try {
            assertEquals(new BigDecimal("1.50"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate4() {//mimo interval s 5 minutama
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test4.csv"));
        try {
            assertEquals(new BigDecimal("3.10"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate5() {//v intervalu bez 5 minut
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test5.csv"));
        try {
            assertEquals(new BigDecimal(1), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate6() {//v intervalu s 5 minutama
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test6.csv"));
        try {
            assertEquals(new BigDecimal("6.20"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate7() {//mix bez 5 minut
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test7.csv"));
        try {
            assertEquals(new BigDecimal("2.50"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate8() {//mix s 5 minutama
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test8.csv"));
        try {
            assertEquals(new BigDecimal("6.10"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate9() {//mix bez 5 minut
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test9.csv"));
        try {
            assertEquals(new BigDecimal("1.50"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate10() {//mix s 5 minutama
        Path path = Paths.get(FilenameUtils.separatorsToSystem("testdata/test10.csv"));
        try {
            assertEquals(new BigDecimal("4.20"), telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}