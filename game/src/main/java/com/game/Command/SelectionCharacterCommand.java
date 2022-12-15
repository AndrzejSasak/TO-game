package com.game.Command;

import com.game.entities.Entity;

public class SelectionCharacterCommand implements ICommand{
    Entity currentCharacter;
    Entity newCharacter;

    public SelectionCharacterCommand(Entity currentCharacter, Entity newCharacter) {
        this.currentCharacter = currentCharacter;
        this.newCharacter = newCharacter;
    }

    @Override
    public void execute() {
        currentCharacter = newCharacter;
    }

    public Entity getEntity() {
        return this.currentCharacter;
    }
}
