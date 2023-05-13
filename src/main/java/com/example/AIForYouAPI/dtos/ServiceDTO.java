package com.example.AIForYouAPI.dtos;

import com.example.AIForYouAPI.entities.AIModelEntity;
import com.example.AIForYouAPI.entities.SubscriptionEntity;

public class ServiceDTO {
    private int id;
    private String tenService, imageUrl;
    private float price;

    public ServiceDTO() {
    }

    public ServiceDTO(AIModelEntity model) {
        id = - (model.getId() + 1);
        tenService = model.getTenModel();
        imageUrl = model.getAiImageUrl();
        price = model.getPrice();
    }

    public ServiceDTO(SubscriptionEntity subscription) {
        id = subscription.getId() + 1;
        tenService = subscription.getTenSubscription();
        imageUrl = "https://res.cloudinary.com/doa0jsihz/image/upload/v1683560535/rent_ihfpfo.png";
        price = subscription.getPrice();
    }

    public int getId() {
        return id;
    }

    public String getTenService() {
        return tenService;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenService(String tenService) {
        this.tenService = tenService;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}