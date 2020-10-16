package game.dinos;


import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.behaviors.WanderBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import game.skills.HabitatClassification;

/**
 * A Fish, it uses splash, it is not very effective.
 *
 */
public class Fish extends Actor {

    protected Behaviour wanderBehaviour;
    private Action wanderFish;
    private Integer numTurns;


    /**
     * Constructor.
     * All fish are represented by a 'f'.
     */
    public Fish(String name) {
        super(name, '^', 100);
        addSkill(DinoType.FISH);
        addSkill(HabitatClassification.WATER);
        addSkill(ConsumableBy.WATERCARNIVORE);
        wanderBehaviour = new WanderBehaviour();
        numTurns = 0;
    }

    /**
     * return the name of the fish.
     * @return      the name of the fishr.
     */
    public String getName(){
        return name;
    }


    /**
     * Fish wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        numTurns += 1;
        wanderFish = wanderBehaviour.getAction(this, map);
        if (numTurns > 20) {
            map.removeActor(this);
            return new DoNothingAction();
        }

        if (wanderFish != null) {
            return wanderFish;
        }
        return new DoNothingAction();
    }

    public boolean isHungry() {
        return false;
    }








}
