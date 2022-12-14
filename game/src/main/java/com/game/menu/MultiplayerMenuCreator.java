package com.game.menu;

import com.game.entities.Entity;

public class MultiplayerMenuCreator implements IMenuCreator{
    @Override
    public IMenu createMenu(Entity player) {
        return new MultiplayerMenu(player);
    }
}
