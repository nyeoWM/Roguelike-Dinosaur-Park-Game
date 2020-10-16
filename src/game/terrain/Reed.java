package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.dinos.BabyProtoceratops;
import game.dinos.Fish;
import game.skills.ConsumableBy;
import game.skills.GroundType;
import game.skills.HabitatClassification;
import game.skills.MasterGroundType;

/**
 * class Reed
 */
public class Reed extends Ground {
    private int age = 0;

    /**
     * constructor for the Reed class.
     * where the Reed is represented by '|' on the map
     */
    public Reed() {
        super('|');
        addSkill(GroundType.REED);
        addSkill(MasterGroundType.WATER);
        addSkill(ConsumableBy.WATERHERBIVORE);

    }

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Reed(char displayChar) {
        super(displayChar);
    }

    /**
     * Called once per turn, so that Locations that has the grass in it will experience the passage time.
     * using random function to randomise the growing of reed after number of turns.
     * @param location The location of the ground on which we lie.
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (ReedCounter(location) > 6) {
            location.setGround(new Water());
        }
        if (Math.random() <= 0.1) {
            if (location.containsAnActor() == false) {
                location.addActor(new Fish("Fish"));
            }
        }
    }


    //inspired by conwayslife
    /**
     * to check if the location has Reed on it or not.
     * @param location		random location on the map.
     * @return				true if it has Reed on it, false otherwise.
     */
    private long ReedCounter(Location location) {
        long ReedNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasSkill(GroundType.REED)).count();
        return ReedNum;
    }

    /**
     * method canActorEnter to prevent LAND dinosaurs and the player from entering the water.
     * it will allow dinosars with classification WATER.
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

