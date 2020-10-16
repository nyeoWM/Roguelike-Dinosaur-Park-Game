package game.items;


import edu.monash.fit2099.engine.Item;
import game.actions.ConvertItem;
import game.skills.ConsumableBy;
/**
 * Generic corpse class for both Herbivore or Carnivore food. Will have different attributes
 * depending on parameters passed to it during creation. A good example of the dont repeat
 * yourself principle.
 */
public class Corpse extends Item {
    /**
     * constructor of the corpse class.
     * @param name              name of the dinosaur corpse.
     * @param displayChar       the character to use to represent the corpse if it is on the ground
     * @param portable          true if and only if the Item can be picked up
     */
        public Corpse(String name, char displayChar, boolean portable) {
            super(name, displayChar, portable);
            addSkill(ConsumableBy.CARNIVORE);

        }



    /**
     * to convert an item (dinosaur) to a corpse.
     * @return       convert dinosaur to corpse.
     */
    public ConvertItem convertItem(){
            return new ConvertItem(this);
        }


}

