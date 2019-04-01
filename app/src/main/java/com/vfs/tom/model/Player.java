package com.vfs.tom.model;

public class Player {
    private String userName;
    private double damageLevel;
    private String weapon;
    private double score;
    //private static Player player;
    //TODO weapon damage modifier

    public Player(){}
    public Player(String userName, double damageLevel, String weapon, double score){
        this.userName = userName;
        this.damageLevel =damageLevel;
        this.weapon = weapon;
        this.score=score;
    }

    /*public static Player getPlayerInstance(String userName, double damageLevel, String weapon, double score){
        if(player==null){
            player = new Player(userName,damageLevel,weapon, score);
        }
        return player;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(double damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

