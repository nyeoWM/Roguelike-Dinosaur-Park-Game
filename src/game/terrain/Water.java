package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.skills.GroundType;
import game.skills.HabitatClassification;
import game.skills.MasterGroundType;

import static game.skills.GroundType.REED;

/**
 * class water.
 */

public class Water extends Ground {


    /**
     * constructor for the class water.
     * water on the the map wil be represented by '~'.
     */
    public Water() {
        super('~');
        addSkill(MasterGroundType.WATER);

    }
    /**
     * Called once per turn, so that Locations that has the grass in it will experience the passage time.
     * using random function to randomise the appearing of water after number of turns.
     * @param location The location of the ground on which we lie.
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (LandChecker(location) == true) {
            if (Math.random() <= 0.1) {
                location.setGround(new Reed());
                return;
            }
        }

        if (ReedChecker(location) == true) {
            if (Math.random() <= 0.05) {
                location.setGround(new Reed());
                return;
            }
        }


    }

    /**
     * check the number surrounding land location.
     * @param location      location to be checked.
     * @return              true if the location land is more than 1 , false otherwise.
     */
    private boolean LandChecker(Location location) {
        long landNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasSkill(MasterGroundType.LAND)).count();
        if (landNum >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks the number of reeds at specific location.
     * @param location      location to be checked.
     * @return              true if the number of reeds is >= 1, false otherwise.
     */
    private boolean ReedChecker(Location location) {
        long ReedNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasSkill(GroundType.REED)).count();
        if (ReedNum >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method canActorEnter to prevent LAND dinosaurs and the player from entering the water.
     * it will allow dinosaurs with classification WATER.
     * @param actor the Actor to check
     * @return      true if the actor is allowed to enter , false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasSkill(HabitatClassification.WATER)) {
            return true;
        } else {
            return false;
        }

    }

}

