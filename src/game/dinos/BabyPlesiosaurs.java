package game.dinos;


import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.actions.AttackAction;
import game.items.Corpse;
import game.skills.DinoType;

/**
 * class BabyPlesiosaurs.
 */
public class BabyPlesiosaurs extends Plesiosaurs {

    private int i = 0;
    private Action dinoAction;



    /**
     * constructor.
     * for the BabyPlesiosaurs class.
     * All BabyVelociraptors are represented by a 'p' and have 50 hit points.
     * @param name          name of the BabyPlesiosaurs.
     */

    public BabyPlesiosaurs(String name){

        super(name,'p',50);
    }


    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        // attack player and dino
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
            System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        food_level_ple -= 1;
        if (food_level_ple == 0){
            Corpse c = new Corpse("dino", '{', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);

        }
        i += 1;
        if (i > 30){
            Location t = map.locationOf(this);
            map.removeActor(this);
            AdultPlesiosaurs v = new AdultPlesiosaurs("Plesiosaurs");
            map.addActor(v,t);
            return new DoNothingAction();
        }

        if (food_level_ple > 150) {
            food_level_ple = 150;
        }
        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }


}
