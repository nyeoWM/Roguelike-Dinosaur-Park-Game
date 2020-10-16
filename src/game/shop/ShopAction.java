package game.shop;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
/**
 * Base class for Shop Actions.
 */
public abstract class ShopAction {
        Shop shop;

        /**
         * constructor for ShopAction class
         * @param shop          the shop.
         */
        public ShopAction(Shop shop) {

                this.shop = shop;
        }

        /**
         * Perform the Action.
         */
        public abstract String execute();

        /**
         * @param actor The actor performing the action.
         */
        public abstract String menuDescription(Actor actor);
}
