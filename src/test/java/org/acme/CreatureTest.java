package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.aesh.readline.editing.Vi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CreatureTest {

    private Creature creature;
    private Villager villager;

    @BeforeEach
    void setUp() {
        creature = new Creature.Builder()
                .name("Goblin")
                .health(10)
                .damage(3)
                .build();

        villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job("Farmer")
                .characteristic(List.of("Strong", "Hardworking"))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .build();
    }

    @Test
    void testCreature() {
        assertEquals("Goblin", creature.getName());
        assertEquals(10, creature.getHealth());
        assertEquals(3, creature.getDamage());
    }

    @Test
    void testTakeDamage() {
        assertEquals(0, creature.takeDamage(0));
        assertEquals(0, creature.takeDamage(2));
        assertEquals(8, creature.getHealth());
        assertEquals(0, creature.takeDamage(8));
        assertEquals(0, creature.getHealth());
        assertEquals(10, creature.takeDamage(10));

        assertThrows(IllegalArgumentException.class, () -> creature.takeDamage(-1));
    }

    @Test
    void testAttack() {
        creature.attack(List.of(villager));
        assertEquals(97, villager.getHealth());

        Creature orc = new Creature.Builder()
                .name("Orc")
                .health(50)
                .damage(20)
                .build();

        Villager john = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job("Farmer")
                .characteristic(List.of("Strong", "Hardworking"))
                .stomachSize(10)
                .health(10)
                .baseHealth(10)
                .damage(10)
                .magic(10)
                .build();

        Villager jane = new Villager.Builder()
                .name("Jane")
                .surname("Doe")
                .age(25)
                .job("Farmer")
                .characteristic(List.of("Strong", "Hardworking"))
                .stomachSize(10)
                .health(10)
                .baseHealth(10)
                .damage(10)
                .magic(10)
                .build();
        orc.attack(List.of(john, jane, villager));
        assertEquals(0, john.getHealth());
        assertEquals(0, jane.getHealth());
        assertEquals(97, villager.getHealth());

        Creature littleWorm = new Creature.Builder()
                .name("Little Worm")
                .health(1)
                .damage(0)
                .build();

        littleWorm.attack(List.of(villager));
        assertEquals(97, villager.getHealth());
    }

    @Test
    void builderTest() {
        testBuilderException(builder -> { });
        testBuilderException(builder -> builder.name("Goblin"));
        testBuilderException(builder -> builder.name("Goblin").health(10));
    }

    private void testBuilderException(Consumer<Creature.Builder> builderConsumer) {
        Creature.Builder builder = new Creature.Builder();
        builderConsumer.accept(builder);
        assertThrows(IllegalStateException.class, builder::build);
    }
}
