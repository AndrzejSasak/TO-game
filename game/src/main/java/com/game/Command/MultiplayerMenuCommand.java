package com.game.Command;

import com.game.Command.multiplayerMenuCommand.IMenuCommand;

public class MultiplayerMenuCommand implements ICommand {

    IMenuCommand menuCommand;

    MultiplayerMenuCommand(IMenuCommand menuCommand) {
        this.menuCommand = menuCommand;
    }

    @Override
    public void execute() {
        menuCommand.execute();
    }
}
