package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ai_model")
public class AIModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String tenModel, aiImageUrl;
    private Float price;

    public AIModelEntity() {

    }

    public Integer getId() {
        return id;
    }

    public String getTenModel() {
        return tenModel;
    }

    public String getAiImageUrl() {
        return aiImageUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTenModel(String tenModel) {
        this.tenModel = tenModel;
    }

    public void setAiImageUrl(String aiImageUrl) {
        this.aiImageUrl = aiImageUrl;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
