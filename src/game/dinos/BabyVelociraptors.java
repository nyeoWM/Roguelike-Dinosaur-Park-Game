package game.dinos;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.items.Corpse;

/**
 * baby Velociraptors dinosaur.
 */
public class BabyVelociraptors extends Velociraptor {

    private int i = 0;

    private Action dinoAction;

    /**
     * constructor.
     * for the BabyVelociraptors class.
     * All BabyVelociraptors are represented by a 'v' and have 50 hit points
     * @param name          name of the BabyVelociraptors.
     */

    public BabyVelociraptors(String name){

        super(name, 'v',20);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        veloEat(location, map);
        if (isHungry()) {
            dinoAction = hungerBehaviour.getAction(this, map);
            System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        food_level_velo -= 1;
        if (food_level_velo == 0){
            Corpse c = new Corpse("dino", 'x', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);

        }
        i += 1;
        if (i > 30){
            Location t = map.locationOf(this);
            map.removeActor(this);
            AdultVelociraptor v = new AdultVelociraptor("Velociraptor");
            map.addActor(v,t);
            return new DoNothingAction();
        }

        if (food_level_velo > 100) {
            food_level_velo = 100;
        }
        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }

}






