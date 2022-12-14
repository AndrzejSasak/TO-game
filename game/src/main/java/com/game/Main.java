package com.game;

import com.game.entities.Entity;
import com.game.menu.IMenu;
import com.game.menu.MainMenuCreator;
import com.game.sharedUserInterface.Login;

public class Main {
   public static void main(String[] args) {
      Entity entity = new Login().createPlayer();
      IMenu menu = new MainMenuCreator().createMenu(entity);
      menu.run();
   }
}
