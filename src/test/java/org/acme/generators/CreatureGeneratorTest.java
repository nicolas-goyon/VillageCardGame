package org.acme.generators;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.creatures.Creature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class CreatureGeneratorTest {

    @BeforeEach
    void setUp(){
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextGaussian()).thenReturn(1.0);
        NumbersGenerator.setRandom(random);
    }

    @Test
    void testGenerateCreatures(){
        CreatureGenerator creatureGenerator = new CreatureGenerator();
        creatureGenerator.setDifficulty(1);
        assertEquals(4, creatureGenerator.generateCreatures().size());
        creatureGenerator.setDifficulty(1);
        assertEquals(4, creatureGenerator.generateCreatures().size());
        creatureGenerator.setDifficulty(1);


        // Test to have 2 goblins, 1 orc and 1 snake
        int goblins = 0;
        int orcs = 0;
        int snakes = 0;

        for (Creature creature : creatureGenerator.generateCreatures()){
            if (creature.getName().contains("Goblin")){
                goblins++;
            }
            if (creature.getName().contains("Orc")){
                orcs++;
            }
            if (creature.getName().contains("Snake")){
                snakes++;
            }
        }

        assertTrue(goblins > 0);
        assertTrue(orcs > 0);

        assertEquals(3, goblins);
        assertEquals(1, orcs);
        assertEquals(0, snakes);
    }

    @Test
    void testGenerateCreatures2() {
        CreatureGenerator creatureGenerator = new CreatureGenerator();
        creatureGenerator.setDifficulty(10);
        assertEquals(33, creatureGenerator.generateCreatures().size());
        creatureGenerator.setDifficulty(10);


        // Test to have 2 goblins, 1 orc and 1 snake
        int goblins = 0;
        int orcs = 0;
        int snakes = 0;

        for (Creature creature : creatureGenerator.generateCreatures()) {
            if (creature.getName().contains("Goblin")) {
                goblins++;
            }
            if (creature.getName().contains("Orc")) {
                orcs++;
            }
            if (creature.getName().contains("Snake")) {
                snakes++;
            }
        }


        assertEquals(14, goblins);
        assertEquals(16, orcs);
        assertEquals(3, snakes);
    }
}
