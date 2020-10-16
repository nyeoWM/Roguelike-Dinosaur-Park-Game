package game.dinos;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.items.Corpse;

/**
 * class BabyPteranodons.
 */

public class BabyPteranodons extends Pteranodons {

    private int i = 0;
    private Action dinoAction;

    /**
     * constructor.
     * for the BabyPteranodons class.
     * All BabyPteranodons are represented by a 'r' and have 50 hit points
     * @param name          name of the BabyPteranodons.
     */

    public BabyPteranodons(String name){

        super(name, 'r',50);
    }


    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        // implementation of priority of feeding
        pteEat1(location, map);
        pteEat2(location, map);

        dinoAction = flyBehaviour.getAction(this, map);
        food_level_pte -= 1;
        if (food_level_pte == 0){
            Corpse c = new Corpse("dino", '[', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);

        }
        i += 1;
        if (i > 30){
            Location t = map.locationOf(this);
            map.removeActor(this);
            AdultPteranodons v = new AdultPteranodons("Pteranodons");
            map.addActor(v,t);
            return new DoNothingAction();
        }

        if (food_level_pte > 150) {
            food_level_pte = 150;
        }
        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }


}
