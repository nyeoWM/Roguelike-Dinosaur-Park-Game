package game.shop;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.shop.Shop;

/**
 * Action class that is passed to player everythime they approach the shop. Allows the to enter the shop.
 */
public class EnterShopAction extends Action {
    private Actor actor;
    private Shop shop;
    private Location shopLocation;


    /**
     * constructor of the EnterShopAction.
     * @param actor     actor ( player ) that enters the shop.
     * @param shop      the shop.
     */
    public EnterShopAction(Actor actor, Shop shop, Location shopLocation) {
        this.actor = actor;
        this.shop = shop;
        this.shopLocation = shopLocation;
    }

    /**
     * Perform the Action (enter shop)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        shop.enterShop(this.actor, this.shopLocation);
        return "GoodBye";
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Enter the Shop?";
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     * @return  The key used for this Action in the menu, which in this case 'e' to enter the shop.
     */
    @Override
    public String hotkey() {
        return "e";
    }
}
