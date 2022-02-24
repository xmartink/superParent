package com.phonecompany.billing;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    private String delimiter;
    private String dateTimePattern;
    private LocalTime intervalStart;
    private LocalTime intervalEnd;
    private BigDecimal intervalPrice;
    private BigDecimal noIntervalPrice;
    private BigDecimal moreFivePrice;
    private int moreCall;

    public TelephoneBillCalculatorImpl() {
        this(",", "dd-MM-yyyy HH:mm:ss",
                LocalTime.of(8, 0, 0),
                LocalTime.of(15, 59, 59),
                new BigDecimal(1),
                new BigDecimal("0.50"),
                new BigDecimal("0.20"),
                5
                );
    }

    public TelephoneBillCalculatorImpl(String delimiter, String dateTimePattern, LocalTime intervalStart, LocalTime intervalEnd, BigDecimal intervalPrice, BigDecimal noIntervalPrice, BigDecimal moreFivePrice, int moreCall) {
        this.delimiter = delimiter;
        this.dateTimePattern = dateTimePattern;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.intervalPrice = intervalPrice;
        this.noIntervalPrice = noIntervalPrice;
        this.moreFivePrice = moreFivePrice;
        this.moreCall = moreCall;
    }

    @Override
    public BigDecimal calculate(String phoneLog) {
        Map<String, PhoneStats> calculateMap = new HashMap<>();

        for(String line: phoneLog.replaceAll("\r", "").split("\n")) {
            String[] split = line.split(delimiter);
            String telephone = split[0];
            LocalDateTime dateTime1 = LocalDateTime.parse(split[1], DateTimeFormatter.ofPattern(dateTimePattern));
            LocalDateTime dateTime2 = LocalDateTime.parse(split[2], DateTimeFormatter.ofPattern(dateTimePattern));

            BigDecimal sum = getSumFromLine(dateTime1, dateTime2);
            if(calculateMap.containsKey(telephone)) {
                PhoneStats stats = calculateMap.get(telephone);
                stats.setCount(stats.getCount() + 1);
                stats.setPriceSum(stats.getPriceSum().add(sum));
            } else {
                calculateMap.put(telephone, new PhoneStats(telephone, sum, 1));
            }
        }

        int mostCall = calculateMap.values().stream()
                .map(PhoneStats::getCount)
                .max(Integer::compareTo)
                .orElseThrow(NoSuchElementException::new);

        Long mostCallTelephone = calculateMap.values().stream()
                .filter(phoneStats -> phoneStats.getCount() == mostCall)
                .map(phoneStats -> Long.valueOf(phoneStats.getPhone()))
                .sorted(Collections.reverseOrder())
                .toList()
                .get(0);

        calculateMap.get(String.valueOf(mostCallTelephone)).setPriceSum(BigDecimal.ZERO);

        return calculateMap.values().stream().map(PhoneStats::getPriceSum).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getSumFromLine(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        int between = (int) Math.ceil((float)ChronoUnit.SECONDS.between(dateTime1, dateTime2) / 60);//bez float to nezaokrouhli nahoru
        BigDecimal price;
        BigDecimal sum = BigDecimal.ZERO;
        LocalTime startTime = dateTime1.toLocalTime();
        LocalTime testTime;
        for(int l = 0; l < between; l++) {
            testTime = startTime.plusMinutes(l);
            if(l >= moreCall) {
                price = moreFivePrice;
            }
            else if(testTime.isBefore(intervalStart) || testTime.isAfter(intervalEnd)) {
                price = noIntervalPrice;
            } else {
                price = intervalPrice;
            }
            sum = sum.add(price);
        }
        return sum;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public LocalTime getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(LocalTime intervalStart) {
        this.intervalStart = intervalStart;
    }

    public LocalTime getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(LocalTime intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public BigDecimal getIntervalPrice() {
        return intervalPrice;
    }

    public void setIntervalPrice(BigDecimal intervalPrice) {
        this.intervalPrice = intervalPrice;
    }

    public BigDecimal getNoIntervalPrice() {
        return noIntervalPrice;
    }

    public void setNoIntervalPrice(BigDecimal noIntervalPrice) {
        this.noIntervalPrice = noIntervalPrice;
    }

    public BigDecimal getMoreFivePrice() {
        return moreFivePrice;
    }

    public void setMoreFivePrice(BigDecimal moreFivePrice) {
        this.moreFivePrice = moreFivePrice;
    }

    public int getMoreCall() {
        return moreCall;
    }

    public void setMoreCall(int moreCall) {
        this.moreCall = moreCall;
    }

}
