import com.game.Messages;
import com.game.controllers.NPCEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    void WarriorShouldAttackArcher(){
        Entity player = new Warrior("Warrior", 1, new NPCEntityController());
        List<Entity> friends = List.of(player);

        Entity archer = new Archer("a", 1, new NPCEntityController());
        List<Entity> enemies = List.of(
            new Warrior("w", 1, new NPCEntityController()),
            new Wizard("wi", 1, new NPCEntityController()),
            archer
        );

        assertTrue(player.getPreferredTargets(enemies).contains(archer));
    }

    @Test
    void ArcherShouldAttackWizard(){
        Entity player = new Archer("Archer", 1, new NPCEntityController());
        List<Entity> friends = List.of(player);

        Entity wizard = new Wizard("wi", 1, new NPCEntityController());
        List<Entity> enemies = List.of(
                new Warrior("w", 1, new NPCEntityController()),
                new Archer("a", 1, new NPCEntityController()),
                wizard
        );

        assertTrue(player.getPreferredTargets(enemies).contains(wizard));
    }

    @Test
    void WizardShouldAttackWarrior(){
        Entity player = new Wizard("Wizard", 1, new NPCEntityController());
        List<Entity> friends = List.of(player);

        Entity warrior = new Warrior("w", 1, new NPCEntityController());

        List<Entity> enemies = List.of(
                new Wizard("wi", 1, new NPCEntityController()),
                new Archer("a", 1, new NPCEntityController()),
                warrior
        );

        assertTrue(player.getPreferredTargets(enemies).contains(warrior));
    }

    @Test
    void WizardShouldAttackAllAtOnce(){
        Entity player = new Wizard("Wizard", 1, new NPCEntityController()){
            @Override public void update(List<Entity> allFriends, List<Entity> allEnemies){
                attack(allEnemies.get(0), allFriends, allEnemies);
            }
        };
        List<Entity> friends = List.of(player);


        List<Entity> enemies = List.of(
                new Wizard("wi", 1, new NPCEntityController()) {
                    @Override public boolean dodge(Entity attacker){return false;}
                },
                new Archer("a", 1, new NPCEntityController()){
                    @Override public boolean dodge(Entity attacker){return false;}
                },
                new Warrior("w", 1, new NPCEntityController()){
                    @Override public boolean dodge(Entity attacker){return false;}
                }
        );

        player.update(friends, enemies);

        assertTrue(enemies.stream().allMatch(entity -> entity.getHp() != entity.getMaxHp()));
    }

    @Test
    void EntityShouldNotTakeDamageWhenDead(){
        Entity player = new Wizard("Wizard", 1, new NPCEntityController()){
            @Override public void update(List<Entity> allFriends, List<Entity> allEnemies){
                attack(allEnemies.get(0), allFriends, allEnemies);
            }
            @Override public boolean dodge(Entity attacker){return false;}
        };
        player.setAttackPoints(9999);
        List<Entity> friends = List.of(player);

        Entity enemy = new Archer("w", 1, new NPCEntityController()){
            @Override public boolean dodge(Entity attacker){return false;}
        };
        List<Entity> enemies = List.of(enemy);

        while(!enemy.isDead())
            player.update(friends, enemies);

        int hp = enemy.getHp();

        player.update(friends, enemies);

        assertEquals(enemy.getHp(), hp);
    }


    @Before
    public void setUpStreams() {

    }
    @Test
    void ShouldParryAttack(){
        System.setOut(new PrintStream(outContent));
        Entity player = new Wizard("Wizard", 1, new NPCEntityController()){
            @Override public void update(List<Entity> allFriends, List<Entity> allEnemies){
                attack(allEnemies.get(0), allFriends, allEnemies);
            }
        };
        player.setAttackPoints(9999);
        List<Entity> friends = List.of(player);

        Entity enemy = new Archer("Archer", 1, new NPCEntityController()){
            @Override public boolean dodge(Entity attacker){return true;}
        };
        List<Entity> enemies = List.of(enemy);

        player.update(friends, enemies);

        System.setOut(originalOut);
        System.out.println(outContent);

        assertTrue(outContent.toString().contains("Archer parried attack!"));
    }
}


