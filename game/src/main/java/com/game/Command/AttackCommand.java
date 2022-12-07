package com.game.Command;

public class AttackCommand implements Command{
    private Object opponent;
    private int attackRate;

    AttackCommand(Object opponent, int attackRate) {
        this.opponent = opponent;
        this.attackRate = attackRate;
    }

    @Override
    public void execute() {
        //attack logic
    }
}
