package com.game.Command;

import com.game.entities.Entity;

import java.util.List;
import java.util.Optional;

public class TargetSelectionCommand implements ICommand {
    private final List<Entity> enemies;
    private final int option;
    private Target target;

    public TargetSelectionCommand(int providedOption, List<Entity> enemies) {
        this.option = providedOption;
        this.enemies = enemies;
    }

    @Override
    public void execute() {
        if(option >= enemies.size() + 1 || option < 0) {
            target = new Target(null, false);
        }
        else if(option == enemies.size()) {
            target = new Target(null, true);
        }
        else {
            target = new Target(enemies.get(option), false);
        }

    }

    public Target getSelectedTarget() {
        return target;
    }
}
