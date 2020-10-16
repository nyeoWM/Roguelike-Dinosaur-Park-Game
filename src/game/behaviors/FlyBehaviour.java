package game.behaviors;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that figures out a MoveAction that will move the actor two steps at time
 * closer to a target Actor. Can be used only by Pteranodons to represent the flying feature of the Pteranodons.
 */
public class FlyBehaviour implements Behaviour {
    private Location actorLocation;

    private ArrayList<Location> possibleLocations;
    private Random random = new Random();

    /**
     * Initialises an instance of the behaviour
     */

    public FlyBehaviour() {

    }

    /**
     * Based on the actor calling it, iterates through all locations of the map and finds
     * the closest food source for the actor. Then, computes the exit that would take the
     * actor closer to that item and returns a move actor action.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return
     */

    @Override
    public Action getAction(Actor actor, GameMap map) {
        actorLocation = map.locationOf(actor);

        possibleLocations = new ArrayList<Location>();

        for (Exit exit : actorLocation.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                if (!possibleLocations.contains(destination)) ;
                possibleLocations.add(destination);
            }
            for (Exit exit2 : actorLocation.getExits()) {
                Location furthestDestination = exit2.getDestination();
                if (furthestDestination.canActorEnter(actor)) {
                    if (!possibleLocations.contains(furthestDestination)) ;
                    possibleLocations.add(furthestDestination);
                }
            }
        }

        if (!possibleLocations.isEmpty()) {
            Location locationChoice =  possibleLocations.get(random.nextInt(possibleLocations.size()));
            return new MoveActorAction(locationChoice, "FlyLocation");
        }
        else {
            return null;
        }

    }


    /**
     * Compute the Manhattan distance between two locations.
     *  it was inspired from the mars code.
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance (Location a, Location b){
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }


}
