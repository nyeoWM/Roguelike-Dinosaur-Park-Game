package game.items;

import edu.monash.fit2099.engine.Item;
import game.skills.ConsumableBy;

/**
 * Generic food class for both Herbivore or Carnivore food. Will have different attributes
 * depending on parameters passed to it during creation. A good example of the dont repeat
 * yourself principle.
 */
public class Food extends Item {
    /**
     * constructor for the food class.
     * @param name          name (type) of the food.
     * @param displayChar   the character to use to represent the food if it is on the ground.
     * @param portable      true if and only if the Item can be picked up.
     */

    public Food(String name, char displayChar, boolean portable, ConsumableBy consumableBy) {

        super(name, displayChar, portable);
        addSkill(consumableBy);

    }

    /**
     * returns a different display char depending on if its Herbivore food or Carnivore food.
     * @return display Char
     */
    @Override
    public char getDisplayChar(){
        return displayChar;
    }

}
