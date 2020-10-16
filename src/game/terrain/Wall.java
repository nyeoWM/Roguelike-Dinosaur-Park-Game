package game.terrain;

		import edu.monash.fit2099.engine.Actor;
		import edu.monash.fit2099.engine.Ground;
		import game.skills.MasterGroundType;

public class Wall extends Ground {

	public Wall() {
		super('#');
		addSkill(MasterGroundType.LAND);

	}

	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

}
