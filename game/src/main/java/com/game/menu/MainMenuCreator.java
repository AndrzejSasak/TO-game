package com.game.menu;

import com.game.entities.Entity;

public class MainMenuCreator implements IMenuCreator {
    @Override
    public IMenu createMenu(Entity player) {
        return new MainMenu(player);
    }
}
