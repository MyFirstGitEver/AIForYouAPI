package com.example.AIForYouAPI.dtos;


import com.example.AIForYouAPI.tools.Vector;

public class RegressionParams implements TrainParams{
    private Vector w;
    private float b;
    private float accuracy;

    public RegressionParams() {

    }

    public RegressionParams(Vector w, float b, float accuracy) {
        this.w = w;
        this.b = b;
        this.accuracy = accuracy;
    }

    public Vector getW() {
        return w;
    }

    public float getB() {
        return b;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setW(Vector w) {
        this.w = w;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}