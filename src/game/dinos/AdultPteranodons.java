package game.dinos;

import edu.monash.fit2099.engine.*;
import game.behaviors.FlyBehaviour;
import game.items.Corpse;
import game.items.Egg;
import game.skills.DinoType;


public class AdultPteranodons extends Pteranodons{

    private boolean t = false;
    private Action dinoAction;

    /**
     * Constructor.
     * All Pteranodons are represented by a 'R' and have 100 hit points.
     *
     * @param name the name of this Pteranodons
     */
    public AdultPteranodons(String name) {
        super(name, 'R',200);
    }

    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur is allegeable for breeding or not, if so an egg will be
     * laid and will appear on the map.
     * '[' represent the corpse of Pteranodons.
     * 'k' represent the egg of Pteranodons.
     *
     * FIXME:  Pteranodons wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public  Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        pteEat1(location, map);
        pteEat2(location, map);
        dinoAction = flyBehaviour.getAction(this, map);
        Location n = null;
        Egg egg = null;
        food_level_pte -= 1;
        if (food_level_pte == 0){
            Corpse c = new Corpse("Pteranodons_Corpse", '[', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);
            return new DoNothingAction();

        }

        if (food_level_pte > 60 && Math.random() >= 0.7) {
            egg = new Egg("Pteranodons_Egg",'k',true, DinoType.PTERANODONS);
            n = new Location(map, map.locationOf(this).x(), map.locationOf(this).y());
            egg.convertItem().execute(this,map);
            System.out.println(egg.getEggType());
            t = true;
        }


        if (food_level_pte > 200) {
            food_level_pte = 200;
        }
        System.out.println("Pteranodons food level is " + food_level_pte);

        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }













}
