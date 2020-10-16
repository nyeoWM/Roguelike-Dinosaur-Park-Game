package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.skills.GroundType;
import game.skills.HabitatClassification;
import game.skills.MasterGroundType;


/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
		addSkill(GroundType.DIRT);
		addSkill(MasterGroundType.LAND);
	}

	/**
	 * Called once per turn, so that Locations that has the tree or dirt on them will experience the passage time.
	 * using random function to randomise the growing of trees/grass after number of turns.
	 * @param location The location of the ground on which we lie.
	 */
	@Override
	public void tick(Location location) {
		if (treeChecker(location) == true) {
			if (Math.random() <= 0.020) {
				location.setGround(new Tree());
				return;
			}
		}
		else {
			if (this.hasSkill(GroundType.DIRT) == true) {
				if (Math.random() <= 0.020) {
					location.setGround(new Grass());
					return;
				}
			}
		}


	}

	//inspired by conwayslife
	/**
	 * to check if the location beside it has tree on it or not.
	 * @param location		random location on the map.
	 * @return				true if it has tree on it, false otherwise.
	 */
	private boolean treeChecker(Location location) {
		long treeNum = location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasSkill(GroundType.TREE)).count();
		if (treeNum >= 1) {
			return true;
		}
		else {
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
	//location.getExits().stream().map(exit -> exit.getDestination().getActor()
}
