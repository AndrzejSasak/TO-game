package com.game.Command;

public class SelectionCharacterCommand implements ICommand{
    Object currentCharacter;
    Object newCharacter;

    SelectionCharacterCommand(Object currentCharacter, Object newCharacter) {
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
