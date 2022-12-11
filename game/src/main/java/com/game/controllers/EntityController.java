package com.game.controllers;

import com.game.entities.Entity;

import java.util.List;

public interface EntityController {
    //null to skip turn
    Entity getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies);
}
