package com.game.controllers;

import com.game.entities.Entity;

import java.util.List;
import java.util.Optional;

public interface AbstractEntityController {

    Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies);
}
