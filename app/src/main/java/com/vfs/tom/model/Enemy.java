package com.vfs.tom.model;

import java.io.Serializable;


public class Enemy implements Serializable {
    private String enemyType;
    private double energy;

    public Enemy() {
    }
    public Enemy(String enemyType, double energy) {
        this.enemyType = enemyType;
        this.energy = energy;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "enemyType='" + enemyType + '\'' +
                ", energy=" + energy +
                '}';
    }

    public double onDamageTaken(double damage){
        this.energy-=damage;
        return energy;
    }
}
