package game.behaviors;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import sun.lwawt.macosx.CSystemTray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor. Can be used by all dinos and thus is a good
 * example of the dont repeat yourself principle.
 */
public class HungerBehaviour implements Behaviour {

	private Actor target;

	/**
	 * Initialises an instance of the behaviour
	 */

	public HungerBehaviour() {
	}

	/**
	 * Based on the actor calling it, iterates through all locations of the map and finds
	 * the closest food source for the actor. Then, computes the exit that would take the
	 * actor closer to that item and returns a move actor action. Code modified from follow
	 * item action
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return
	 */

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// check the type of the caller, and set the kind of consumable to look for
		ConsumableBy targetSkill;
		if (actor.hasSkill(DinoType.PROTOCERATOPS)) {
			targetSkill = ConsumableBy.HERBIVORE;
		} else {
			if (actor.hasSkill(DinoType.PLESIOSAURS)) {
				targetSkill = ConsumableBy.WATERCARNIVORE;
			} else {
				if (actor.hasSkill(DinoType.PTERANODONS)) {
					targetSkill = ConsumableBy.CARNIVORE;
				}else { if (actor.hasSkill(DinoType.TREX)||actor.hasSkill(DinoType.REAREDTREX))
					targetSkill = ConsumableBy.CARNIVORE;
				// final else for velociraptors
				else{
					targetSkill = ConsumableBy.CARNIVORE;
				}
				}
			}
		}
		// iterates through all the locations on the map, and returns the location
		// of the closest food sourcd and the distance to get there
		Location myLocation = map.locationOf(actor);
		Location closestLocation = null;
		int closestDistance = Integer.MAX_VALUE;
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		for (int x : xRange) {
			for (int y : yRange){
				Location newLocation = map.at(x, y);

				if (newLocation.getGround().hasSkill(targetSkill)) {
					int newDistance = distance(myLocation, newLocation);
					if (newDistance < closestDistance) {
						closestDistance = newDistance;
						closestLocation = newLocation;
					}
				}



				for (Item i : newLocation.getItems()) {
					if (i.hasSkill(targetSkill)) {
						int newDistance = distance(myLocation, newLocation);
						if (newDistance < closestDistance) {
							closestDistance = newDistance;
							closestLocation = newLocation;
						}
					}
				}


				Actor someActor = newLocation.getActor();
				if (someActor != null) {
					if (newLocation.getActor().hasSkill(targetSkill)) {
						int newDistance = distance(myLocation, newLocation);
						if (newDistance < closestDistance) {
							closestDistance = newDistance;
							closestLocation = newLocation;
						}
					}
				}

			}
		}
		// iterate through the (max 8) possible exits and returns the action moving to
		// the exit that brings the dino closer to the food source
		//
		if (closestLocation != null) {
			for (Exit exit : myLocation.getExits()) {
				Location destination = exit.getDestination();
				if (destination.canActorEnter(actor)) {

					int finalDistance = distance(closestLocation, destination);
					if (finalDistance < closestDistance) {
						return new MoveActorAction(destination, exit.getName());
					}
				}
			} return null;
		} return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 *  from the mars demo
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}

	}
