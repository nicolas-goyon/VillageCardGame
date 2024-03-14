package org.acme.generators;

import org.acme.creatures.Creature;
import org.acme.creatures.CreatureType;

import java.util.ArrayList;
import java.util.List;

public class CreatureGenerator {

    private int difficulty;

    public CreatureGenerator(){
        this.difficulty = 1;
    }

    public List<Creature> generateCreatures(){
        List<Creature> creatures = generateCreatureGroup();
        while (creatures.isEmpty()){
            creatures = generateCreatureGroup();
        }
        difficulty++;
        return creatures;
    }

    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }


    public List<Creature> generateCreatureGroup(){
        int numberOfCreatures = NumbersGenerator.generateNormalDistributedNumber(difficulty * 3.0, difficulty).intValue();
        // proportion of each creature
        int orc = (int) (numberOfCreatures * 0.3);
        int snake = (int) (numberOfCreatures * 0.1);

        // The proportion between orc and goblin need to change based on the difficulty
        // The higher the difficulty, the more orcs
        if (difficulty > 3){
            orc = (int) (numberOfCreatures * 0.5);
            snake = (int) (numberOfCreatures * 0.1);
        }

        int goblin = numberOfCreatures - orc - snake;


        // Generate the creatures
        List<Creature> creatures = new ArrayList<>();
        for (int i = 0; i < goblin; i++){
            creatures.add(CreatureType.GOBLIN.create());
        }
        for (int i = 0; i < orc; i++){
            creatures.add(CreatureType.ORC.create());
        }
        for (int i = 0; i < snake; i++){
            creatures.add(CreatureType.SNAKE.create());
        }

        // Name the creatures
        for (Creature creature : creatures){
            creature.setName(creature.getName() + "-" + (creatures.indexOf(creature)+1));
        }

        return creatures;
    }


}
