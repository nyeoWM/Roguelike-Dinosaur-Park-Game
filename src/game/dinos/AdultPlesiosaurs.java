package game.dinos;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.TagDinoAction;
import game.items.Corpse;
import game.items.Egg;
import game.skills.DinoType;

import java.util.List;
import java.util.stream.Collectors;

public class AdultPlesiosaurs extends Plesiosaurs {

    private boolean t = false;
    private Action dinoAction;

    /**
     * Constructor.
     * All Adult Plesiosaurs are represented by a 'P' and have 100 hit points.
     *
     * @param name the name of this Plesiosaurs
     */
    public AdultPlesiosaurs(String name) {
        super(name, 'P', 30);
    }



    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur is allegeable for breeding or not, if so an egg will be
     * laid and will appear on the map.
     * '{' represent Plesiosaur dinosaur corpse.
     * 'b' represent Plesiosaur's egg.
     *
     * FIXME:  Plesiosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public  Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);

        // implementation of attack action on player and Trex
        Actor targetActor = location.getExits().stream().map(exit -> exit.getDestination().getActor())
                .filter(actor -> actor != null )
                .filter(actor -> (actor.toString() == "Player" || actor.hasSkill(DinoType.TREX) || actor.hasSkill(DinoType.REAREDTREX)) )
                .findFirst().orElse(null);
        if (targetActor != null) {
            return new AttackAction(targetActor, map.locationOf(targetActor), 10);
        }


        pleEat(location, map);
        if (isHungry()) {
            dinoAction = hungerBehaviour.getAction(this, map);
            System.out.println(getName()+" Plesiosaur at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        //checkHunger();
        Location n = null;
        Egg egg = null;
        food_level_ple -= 1;
        if (food_level_ple == 0){
            Corpse c = new Corpse("Plesiosaurs_Corpse", '{', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);
            return new DoNothingAction();

        }
        // implementation of plesiosaurs egg

        if (food_level_ple > 100 && Math.random() >= 0.7) {
            egg = new Egg("Plesiosaurs_Egg",'b',true, DinoType.PLESIOSAURS);
            n = new Location(map, map.locationOf(this).x(), map.locationOf(this).y());
            egg.convertItem().execute(this,map);
            System.out.println(egg.getEggType());
            t = true;
        }


        if (food_level_ple > 200) {
            food_level_ple = 200;
        }
        System.out.println("Plesiosaurs food level is " + food_level_ple);

        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }

}
