package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

// to convert baby dino or grown dino into corpse after dying


public class ConvertItem extends Action {
    protected Item item;

    /**
     * Constructor.
     *
     * @param item the item to convert.
     */
    public ConvertItem(Item item) {

        this.item = item;
    }

    /**
     * Change the dinosaur after dying into an item corpse.
     *
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return a description of the action suitable for feedback in the UI
     */

    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).addItem(item);
        return menuDescription(actor);
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player drops the potato"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drops the " + item;
    }


}
