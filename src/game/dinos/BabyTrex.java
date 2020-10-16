package game.dinos;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.items.Corpse;
import game.skills.DinoType;

/**
 * class babyTrex.
 */

public class BabyTrex extends Trex {

    private int i = 0;
    private Action dinoAction;
    private DinoType dinoType;


    /**
     * constructor.
     * for the BabyTrex class.
     * All BabyTrex are represented by a 't'.
     * @param name          name of the BabyTrex.
     */

    public BabyTrex(String name, DinoType someDino) {

        super(name, 't', someDino);
        dinoType = someDino;

    }

    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse. also the method check if their is any player around the Trex the Trex will deliver
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
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        this.heal(10);
        Location location = map.locationOf(this);
        // code to attack player
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
            System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
        } else {
            dinoAction = wanderBehaviour.getAction(this, map);
        }
        food_level_trex -= 1;
        if (food_level_trex == 0){
            Corpse c = new Corpse("dino", '?', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);

        }
        i += 1;
        if (i > 50){
            Location t = map.locationOf(this);
            map.removeActor(this);
            switch (dinoType){
                case TREX:
                    map.addActor(new AdultTrex("Trex", DinoType.TREX),t);
                    break;
                case REAREDTREX:
                    map.addActor(new AdultTrex("Reared TRex", DinoType.REAREDTREX),t);
                    break;

            }

            return new DoNothingAction();
        }

        if (food_level_trex > 250) {
            food_level_trex = 250;
        }
        if (dinoAction != null)
            return dinoAction;

        return new DoNothingAction();
    }















}
