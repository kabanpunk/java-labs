package ru.nsu.lab3.controller;

public class PlayerStat {
    private String name;
    private double stat;

    public PlayerStat(String name, double stat) {
        this.name = name;
        this.stat = stat;
    }

    public String getName() {
        return name;
    }

    public double getStat() {
        return stat;
    }
}
