package com.phonecompany.billing;

import java.math.BigDecimal;

public class PhoneStats {

    private String phone;
    private BigDecimal priceSum;
    private int count;

    public PhoneStats(String phone, BigDecimal priceSum, int count) {
        this.phone = phone;
        this.priceSum = priceSum;
        this.count = count;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
