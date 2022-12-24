package com.game.controllers;

import com.game.entities.Entity;
import com.game.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractEntityController extends Serializable {

    Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies);

    Optional<User> getRealPlayerEntityOwner();
}
