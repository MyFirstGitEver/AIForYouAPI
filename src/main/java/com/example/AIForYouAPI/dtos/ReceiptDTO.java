package com.example.AIForYouAPI.dtos;

import java.util.Date;

public class ReceiptDTO {
    private String name;
    private Date buyDate;
    private boolean expired, periodic;

    public ReceiptDTO() {

    }

    public ReceiptDTO(String name, Date buyDate, boolean expired, boolean periodic) {
        this.name = name;
        this.buyDate = buyDate;
        this.expired = expired;
        this.periodic = periodic;
    }

    public String getName() {
        return name;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isPeriodic() {
        return periodic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setPeriodic(boolean periodic) {
        this.periodic = periodic;
    }
}
