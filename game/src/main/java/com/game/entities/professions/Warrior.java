package com.game.entities.professions;


public class Warrior implements Profession{
    private int maxHp = 350;
    private int attack = 20;
    private String professionName = "Warrior";



    @Override
    public float getAttackBonus(Profession enemy) {
        return 0;
    }

    @Override
    public float getProtectionBonus(Profession enemy) {
        return 0;
    }

    @Override
    public float getDodgeBonus(Profession enemy) {
        return 0;
    }

    @Override
    public String getProfessionName() {
        return professionName;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getAttack() {
        return attack;
    }
}
