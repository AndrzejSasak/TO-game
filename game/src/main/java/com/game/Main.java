package com.game;

import com.game.entities.Entity;
import com.game.sharedUserInterface.Login;
import com.game.menu.MainMenu;

public class Main {
   public static void main(String[] args) {
      Entity entity = new Login().createPlayer();
      MainMenu menu = new MainMenu(entity);
      menu.run();
   }
}
