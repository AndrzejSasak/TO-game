package com.game;

import com.game.entities.Entity;
import com.game.sharedUserInterface.Login;
import com.game.sharedUserInterface.Menu;

public class Main {
   public static void main(String[] args) {
      Entity entity = new Login().createPlayer();
      Menu menu = new Menu(entity);
      menu.run();
   }
}
