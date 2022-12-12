package com.game;

import com.game.levels.Level;
import com.game.levels.Training;

public class Main {
   public static void main(String[] args) {
      Level game = new Training("Adrian");
      while (!game.run());
   }
}
