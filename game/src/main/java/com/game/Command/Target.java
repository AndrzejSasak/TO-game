package com.game.Command;

import com.game.entities.Entity;

public class Target {
    private boolean skipCommand;
    private Entity entity;

    Target(Entity entity, boolean skipCommand) {
        this.skipCommand = skipCommand;
        this.entity = entity;
    }

    public boolean isSkipCommand() {
        return skipCommand;
    }

    public Entity getEntity() {
        return entity;
    }
}
