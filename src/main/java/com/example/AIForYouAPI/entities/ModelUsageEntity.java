package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "model_usage")
public class ModelUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private int userId, modelId;
    private Date usageTime;

    public ModelUsageEntity() {

    }

    public ModelUsageEntity(int userId, int modelId, Date usageTime) {
        this.userId = userId;
        this.modelId = modelId;
        this.usageTime = usageTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getModelId() {
        return modelId;
    }

    public Date getUsageTime() {
        return usageTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setUsageTime(Date usageTime) {
        this.usageTime = usageTime;
    }
}
