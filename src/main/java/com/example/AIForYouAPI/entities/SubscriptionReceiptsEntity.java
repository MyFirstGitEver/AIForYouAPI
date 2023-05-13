package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "subscription_receipts")
public class SubscriptionReceiptsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;

    private int idUser, idSubs;
    private Date receiptDate, endDate;

    public SubscriptionReceiptsEntity() {
    }

    public SubscriptionReceiptsEntity(SubscriptionEntity subscription, int userId) {
        idUser = userId;
        idSubs = subscription.getId();
        receiptDate = new Date();

        long mils = TimeUnit.DAYS.toMillis(subscription.getExpireIntervalInWeeks() * 7L) + receiptDate.getTime();
        endDate = new Date(mils);
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdSubs() {
        return idSubs;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdSubs(int idSubs) {
        this.idSubs = idSubs;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
