package game.dinos;
import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.actions.AttackAction;
import game.items.Corpse;
import game.items.Egg;
import game.skills.DinoType;

/**
 * class AdultTrex.
 */

public class AdultTrex extends Trex {

    private boolean t = false;
    private Action dinoAction;

    /**
     * Constructor.
     * All Trex are represented by a 'T'.
     *
     * @param name the name of this Trex.
     */
    public AdultTrex(String name, DinoType someDino) {
        super(name, 'T', someDino);
    }

    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur is allegeable for breeding or not, if so an egg will be
     * laid and will appear on the map. also the method check if their is any player around the Trex the Trex will deliver
     * damage by 10 points to the player. also if the Trex get damage at any turn of the game during every new turn he will
     * heal by 10 points
     * 'm' represent the egg of the Trex.
     * '?' represent the corpse of a Trex.
     * FIXME: Trex wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public  Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        this.heal(10);
        Location location = map.locationOf(this);
        // implement eating action for trex
        Actor targetActor = location.getExits().stream().map(exit -> exit.getDestination().getActor())
                .filter(actor -> actor != null )
                .filter(actor -> (actor.toString() == "Player") )
                .findFirst().orElse(null);
        if (targetActor != null) {
            return new AttackAction(targetActor, map.locationOf(targetActor), 10);
        }
        trexEat(location, map);
        if (isHungry()) {
            dinoAction = hungerBehaviour.getAction(this, map);
            System.out.println(getName()+" Trex at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        //checkHunger();
        Location n = null;
        Egg egg = null;
        food_level_trex -= 1;
        if (food_level_trex == 0){
            Corpse c = new Corpse("Trex_Corpse", '?', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);
            return new DoNothingAction();

        }

        if (food_level_trex > 200 && Math.random() >= 0.7) {
            egg = new Egg("Trex_Egg",'m',true, DinoType.REAREDTREX);
            n = new Location(map, map.locationOf(this).x(), map.locationOf(this).y());
            egg.convertItem().execute(this,map);
            System.out.println(egg.getEggType());
            t = true;
        }


        if (food_level_trex > 500) {
            food_level_trex = 500;
        }
        System.out.println("Trex food level is " + food_level_trex);

        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }




}
