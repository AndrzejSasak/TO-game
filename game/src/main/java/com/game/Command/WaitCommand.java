package com.game.Command;

import com.game.controllers.RemotePlayerEntityController;
import com.game.entities.Entity;

public class WaitCommand implements ICommand{

    Entity entity;

    public WaitCommand(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void execute() {
        entity.bWantsToWait = true;
        entity.bWantsToAttack = false;
        entity.setCritical(true);
    }
}
