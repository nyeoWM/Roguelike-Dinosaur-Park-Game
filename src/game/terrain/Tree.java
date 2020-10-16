package game.terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.skills.ConsumableBy;
import game.skills.GroundType;
import game.skills.HabitatClassification;
import game.skills.MasterGroundType;

public class Tree extends Ground {
	private int age = 0;

	public Tree() {
		super('+');
		addSkill(GroundType.TREE);
		addSkill(ConsumableBy.HERBIVORE);
		addSkill(MasterGroundType.LAND);


	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
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
