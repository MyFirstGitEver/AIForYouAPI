package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String tenSubscription;
    private Float price;
    private int expireIntervalInWeeks;

    public SubscriptionEntity() {
    }

    public Integer getId() {
        return id;
    }

    public String getTenSubscription() {
        return tenSubscription;
    }

    public Float getPrice() {
        return price;
    }

    public int getExpireIntervalInWeeks() {
        return expireIntervalInWeeks;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTenSubscription(String tenSubscription) {
        this.tenSubscription = tenSubscription;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setExpireIntervalInWeeks(int expireIntervalInWeeks) {
        this.expireIntervalInWeeks = expireIntervalInWeeks;
    }
}