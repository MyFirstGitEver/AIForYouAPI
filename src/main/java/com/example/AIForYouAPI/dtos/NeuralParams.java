package com.example.AIForYouAPI.dtos;

import com.example.AIForYouAPI.tools.Vector;

public class NeuralParams implements TrainParams{
    private Vector[][] w;
    private Vector[] b;

    private double cost;

    public NeuralParams() {

    }

    public NeuralParams(Vector[][] w, Vector[] b, double cost) {
        this.w = w;
        this.b = b;
    }

    public Vector[][] getW() {
        return w;
    }

    public Vector[] getB() {
        return b;
    }

    public double getCost() {
        return cost;
    }

    public void setW(Vector[][] w) {
        this.w = w;
    }

    public void setB(Vector[] b) {
        this.b = b;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}