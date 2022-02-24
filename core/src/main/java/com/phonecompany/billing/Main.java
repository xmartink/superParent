package com.phonecompany.billing;

import com.phonecompany.billing.TelephoneBillCalculator;
import com.phonecompany.billing.TelephoneBillCalculatorImpl;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        TelephoneBillCalculator telephoneBillCalculator = new TelephoneBillCalculatorImpl();
        Path path = Paths.get(FilenameUtils.separatorsToSystem("TelephoneBillCalculator/testdata/test10.csv"));
        try {
            System.out.println(telephoneBillCalculator.calculate(Files.readString(path, StandardCharsets.US_ASCII)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
