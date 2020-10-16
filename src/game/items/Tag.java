package game.items;

import edu.monash.fit2099.engine.*;
import game.actions.TagDinoAction;
import game.shop.Shop;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Class for tag item. If held by the player constantly scans all adjacent squares for taggable dinos
 * and returns tag dino action to the player followed by the location of the dino
 */
public class Tag extends Item {
    private Shop shop;


    /**
     * constructor for the Tag class.
     * @param name              name.
     * @param displayChar       the character to use to represent the tag if it is on the ground
     * @param portable          true if and only if the Item can be picked up.
     * @param shop              the shop.
     */
    public Tag(String name, char displayChar, boolean portable, Shop shop) {
        super(name, displayChar, portable);
        this.shop = shop;
    }

    /**
     * finds all the dino actors adjacent to the player. Returns an action specifying the locatiom
     * for the dinos
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (actor.toString().equals("Player")) {
            List <Location> targetLocations = currentLocation.getExits().stream().map(Exit::getDestination)
                .filter(thisLocation -> thisLocation.getActor() != null )
                    .filter(thisLocation -> !thisLocation.getActor().isHungry())
                      .collect(Collectors.toList());
            if (targetLocations.size() > 0) {
                targetLocations.stream().forEach(c -> addAction(new TagDinoAction(c.getActor(), c, shop, this)));
            }
        }
    }

    /**
     * adds action to the allowable actions array.
     * @param action        action added.
     */
    public void addAction(Action action) {
        this.allowableActions.add(action);
    }

    /**
     * let the location experience the passage of time.
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
    }

    /**
     * return the list of allowable actions.
     * @return          the list of allowable actions.
     */
    @Override
    public List<Action> getAllowableActions() {
        return super.getAllowableActions();
    }

}