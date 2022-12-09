package com.game;

import com.game.entities.NPC;
import com.game.entities.professions.Profession;
import com.game.entities.professions.Warrior;

import javax.swing.text.html.parser.Entity;

public class Main {

   NPC npc = new NPC(new Warrior());

   Entity npc2 = npc;
}
