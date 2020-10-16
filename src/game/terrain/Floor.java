package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.skills.MasterGroundType;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
		addSkill(MasterGroundType.LAND);
	}

	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor.toString() != "Player") {
			return false;
		} else {
			return true;
		}

	}



}
