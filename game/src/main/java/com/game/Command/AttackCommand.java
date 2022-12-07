package com.game.Command;

public class AttackCommand implements ICommand {
    private Object opponent;
    private int attackRate;

    AttackCommand(Object opponent, int attackRate) {
        this.opponent = opponent;
        this.attackRate = attackRate;
    }

    @Override
    public void execute() {
        ////TODO: add attack logic
    }
}
