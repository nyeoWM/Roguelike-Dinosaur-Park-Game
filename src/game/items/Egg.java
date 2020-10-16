package game.items;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.actions.ConvertItem;
import game.dinos.*;
import game.skills.DinoType;
import game.skills.ConsumableBy;
import game.skills.MasterGroundType;

import java.util.Optional;


/**
 * Generic egg class for both Herbivore or Carnivore food. Will have different attributes
 * and will produce differnt dinos based on parameters passed to it during creation.
 * A good example of the dont repeat yourself principle.
 */
public class Egg extends Item {

    private int counter = 0;
    private DinoType dinoType;

    /**
     * constructor for the egg class.
     *
     * @param name        name of the egg created.
     * @param displayChar the character to use to represent the egg if it is on the ground.
     * @param portable    true if and only if the Item can be picked up.
     */
    public Egg(String name, char displayChar, boolean portable, DinoType dino) {
        super(name, displayChar, portable);
        this.dinoType = dino;
        addSkill(dino);
        if (this.hasSkill(DinoType.PROTOCERATOPS)) {
            addSkill(ConsumableBy.CARNIVORE);
            addSkill(ConsumableBy.WATERCARNIVORE);
        }
        if (this.hasSkill(DinoType.PLESIOSAURS)) {
            addSkill(ConsumableBy.CARNIVORE);
        }

    }


    /**
     * to convert an item (egg) to a baby dinosaur.
     *
     * @return convert egg to baby dinosaur.
     */
    public ConvertItem convertItem() {
        return new ConvertItem(this);
    }


    /**
     * return the name of the egg.
     *
     * @return the nam eof the egg.
     */
    public String getEggType() {

        return name;
    }

    /**
     * Called once per turn, so that Locations that the egg is on can experience the passage time.
     * after 10 turns egg will turn to baby dinosaur.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        // implementation of having the egg beside the water before it can hatch
        if (dinoType == DinoType.PLESIOSAURS) {
            if (!this.besideWater(currentLocation)) {
                return;
            }
        }
        counter += 1;
        //System.out.println("Counter"+counter);
        if (counter > 10) {
            if (!currentLocation.containsAnActor()) {
                switch (dinoType) {
                    case PROTOCERATOPS:
                        currentLocation.removeItem(this);
                        currentLocation.addActor(new BabyProtoceratops("Baby_Protoceratops"));
                        break;

                    case VELOCIRAPTOR:
                        currentLocation.removeItem(this);
                        currentLocation.addActor(new BabyVelociraptors("Baby_Velociraptors"));
                        break;

                    case REAREDTREX:
                        currentLocation.removeItem(this);
                        currentLocation.addActor(new BabyTrex("Baby_TRex", DinoType.REAREDTREX));
                        break;

                    case TREX:
                        currentLocation.removeItem(this);
                        currentLocation.addActor(new BabyTrex("Baby_TRex", DinoType.TREX));
                        break;

                    case PLESIOSAURS:
                        currentLocation.removeItem(this);
                        getWaterLocation(currentLocation)
                                .addActor(new BabyPlesiosaurs("Baby_Plesiosaurs"));
                        break;

                    case PTERANODONS:
                        currentLocation.removeItem(this);
                        currentLocation.addActor(new BabyPteranodons("Baby_Pteranodons"));
                        break;

                }

            }

        }
    }

    /**
     * check if the the egg was put next to the water and will enable it to hatch.
     * @param location      location of the egg.
     * @return              true if it is near the water , false otherwise.
     */
    private Boolean besideWater(Location location) {
        long waterNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasSkill(MasterGroundType.WATER)).count();
        if (waterNum >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check where is the water location on the map.
     * @param location      location of the water.
     * @return              the location if the water.
     */
    private Location getWaterLocation(Location location) {
        Location waterLocation = location.getExits().stream().map(exit -> exit.getDestination())
                .filter(destination -> destination
                        .getGround()
                        .hasSkill(MasterGroundType.WATER))
                .findFirst().orElse(null);;
        if (waterLocation != null){
            Location finalLocation = waterLocation;
            System.out.println(finalLocation.toString());
            return finalLocation;
        }
        return location;
    }
}
