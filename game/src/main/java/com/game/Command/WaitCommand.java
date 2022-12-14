package com.game.Command;

import com.game.entities.Entity;

public class WaitCommand implements ICommand {
    private Entity entity;

    WaitCommand(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void execute() {

    }
}
