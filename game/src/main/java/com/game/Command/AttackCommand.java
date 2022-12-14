package com.game.Command;

import com.game.entities.Entity;

public class AttackCommand implements ICommand {
    private Entity opponent;
    private int attackRate;

    AttackCommand(Entity opponent, int attackRate) {
        this.opponent = opponent;
        this.attackRate = attackRate;
    }

    @Override
    public void execute() {
        opponent.getHit(attackRate);
    }
}
