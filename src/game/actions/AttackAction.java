package game.actions;

import edu.monash.fit2099.engine.*;
import game.items.Tag;
import game.shop.Shop;

import java.util.Optional;

/**
 * Action to attack an actor.
 */
public class AttackAction extends Action {
    private Actor target;
    private String locationString;
    private Shop shop;
    private Tag theTag;
    private int damage;

    /**
     * constructor for the AttackAction class.
     * @param target            target of the attack action by an actor.
     * @param targetLocation    the target location.
     * @param damage            the damage point delivered to the target.
     */
    public AttackAction(Actor target, Location targetLocation, int damage) {
        this.target = target;
        this.locationString = Integer.toString(targetLocation.x()) + "," + Integer.toString(targetLocation.y());
        this.damage = damage;

    }

    /**
     * method execute to deliver the damage point to te target.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return      damage delivered.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        target.hurt(damage);
        return menuDescription(actor);
    }

    /**
     * returns description of the attack action performed by an actor with the damage points displayed.
     * @param actor The actor performing the attack action.
     * @return      string description of the attack action performed by an actor with the damage points displayed.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks the " + this.target + " at " + this.locationString + " for " + Integer.toString(damage) + " damage";
    }
}
