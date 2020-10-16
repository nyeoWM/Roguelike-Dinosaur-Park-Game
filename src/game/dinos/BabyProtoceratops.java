package game.dinos;

import edu.monash.fit2099.engine.*;
import game.items.Corpse;

public class BabyProtoceratops extends Protoceraptops {

    private int i = 0;

    private Action dinoAction;

    /**
     * constructor.
     * for the BabyProtoceratops class.
     * All BabyProtoceratops are represented by a 'd' and have 50 hit points.
     * @param name          name of the BabyProtoceratops.
     */
    public BabyProtoceratops(String name) {
        super(name, 'd', 10);
    }



    /**
     * Figure out what to do next. and check if the food level of baby dinosaur and to check if its still alive, if not the baby
     * dinosaur will die and turn to corpse, and to check if the the dinosaur have reached maturity or not by keep tracking
     * of the turns, after 30 turns baby dinosaur will grow up to an adult one and will represented by "D".
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     *
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        Location location = map.locationOf(this);
        proEat(location);

        if (isHungry()) {
            dinoAction = hungerBehaviour.getAction(this, map);
            System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")"+ "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        food_level_pro -= 1;
        if (food_level_pro == 0) {
            Corpse c = new Corpse("dino", 'c', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);
        }
        i += 1;
        if (i > 30) {
            Location n = map.locationOf(this);
            map.removeActor(this);
            AdultProtoceratops p = new AdultProtoceratops("Protoceratops");
            map.addActor(p, n);
            return new DoNothingAction();
        }
        if (dinoAction != null)
            return dinoAction;


        return new DoNothingAction();
    }


}
