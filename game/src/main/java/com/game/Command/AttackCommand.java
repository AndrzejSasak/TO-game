package com.game.Command;

import com.game.entities.Entity;

public class AttackCommand implements ICommand{

    Entity entity;

    public AttackCommand(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void execute() {
        entity.bWantsToWait = false;
        entity.bWantsToAttack = true;
    }
}
