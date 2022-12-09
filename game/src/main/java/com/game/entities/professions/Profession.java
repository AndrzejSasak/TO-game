package com.game.entities.professions;

public interface Profession {
    int getMaxHp();
    int getAttack();
    float getAttackBonus(Profession enemy);
    float getProtectionBonus(Profession enemy);
    float getDodgeBonus(Profession enemy);
    String getProfessionName();
}
