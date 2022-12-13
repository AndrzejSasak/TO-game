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
        if (currentCharacter.equals(newCharacter))
            return;
        currentCharacter = newCharacter;
    }
}
