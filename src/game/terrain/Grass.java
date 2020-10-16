package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.skills.ConsumableBy;
import game.skills.GroundType;
import game.skills.HabitatClassification;
import game.skills.MasterGroundType;

public class Grass extends Ground {
	private int age = 0;

	/**
	 * constructor for the grass class.
	 */
	public Grass() {
		super(',');
		addSkill(GroundType.GRASS);
		addSkill(MasterGroundType.LAND);

		addSkill(ConsumableBy.HERBIVORE);

	}

	/**
	 * Called once per turn, so that Locations that has the grass in it will experience the passage time.
	 * using random function to randomise the growing of trees after number of turns.
	 * @param location The location of the ground on which we lie.
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		age++;
		if (treeChecker(location) == true) {
			if (Math.random() <= 0.020) {
				location.setGround(new Tree());
				return;
			}
		}
	}


	//inspired by conwayslife
	/**
	 * to check if the location has tree on it or not.
	 * @param location		random location on the map.
	 * @return				true if it has tree on it, false otherwise.
	 */
	private boolean treeChecker(Location location) {
		long treeNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasSkill(GroundType.TREE)).count();
		if (treeNum >= 1) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor.hasSkill(HabitatClassification.LAND)) {
			return true;
		} else {
			return false;
		}

	}

}

