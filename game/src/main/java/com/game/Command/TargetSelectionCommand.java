package com.game.Command;

import com.game.entities.Entity;

import java.util.List;
import java.util.Optional;

public class TargetSelectionCommand implements ICommand {
    private final List<Entity> enemies;
    private final int option;
    private Optional<Entity> target;

    public TargetSelectionCommand(int providedOption, List<Entity> enemies) {
        this.option = providedOption;
        this.enemies = enemies;
    }

    @Override
    public void execute() {
        if(option >= enemies.size() || option < 0) {
            target = Optional.empty();
        }
        else {
            target = Optional.of(enemies.get(option));
        }
    }

    public Optional<Entity> getSelectedTarget() {
        return target;
    }
}
