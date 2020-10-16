package game.actions;

import edu.monash.fit2099.engine.*;
import game.items.Tag;
import game.shop.Shop;

import java.util.Optional;

/**
 * Action to tag and sell the dino to the shop immediately
 */
public class TagDinoAction extends Action {
    private Actor targetDino;
    private String locationString;
    private Shop shop;
    private Tag theTag;

    /**
     * Initialise an instance of the class with all the parameters needed to sell
     * the dino later. Saved the parameters as attributes
     * @param targetDino the dino to be tagged/sold
     * @param targetLocation the location of the dino to be tagged
     * @param shop the shop in the map. Needed for selling the dino later
     * @param tag this tag calling the item
     */
    public TagDinoAction(Actor targetDino, Location targetLocation, Shop shop, Tag tag) {
        this.targetDino = targetDino;
        this.locationString = Integer.toString(targetLocation.x()) + "," + Integer.toString(targetLocation.y());
        this.shop = shop;
        this.theTag = tag;
    }

    /**
     * Removes the dino from and map and calls a method in shop to deposite money to players account. Also removes
     * tag from the actor's inventory
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(targetDino);
        shop.sellDino(targetDino.toString());
        actor.removeItemFromInventory(this.theTag);
        return "Dinosaur sold.";
    }

    /**
     * Menu description of the action
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " tags the " + this.targetDino + " at " + this.locationString;
    }
}
