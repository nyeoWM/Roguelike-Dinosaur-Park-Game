package game.dinos;


import edu.monash.fit2099.engine.*;
import game.items.Corpse;
import game.items.Egg;
import game.skills.DinoType;

/**
 * A herbivorous dinosaur.
 *
 */
public class AdultProtoceratops extends Protoceraptops {

	private boolean t = false;
	private Action dinoAction;


	/** 
	 * Constructor.
	 * All Protoceratops are represented by a 'D' and have 100 hit points.
	 * 
	 * @param name the name of this Protoceratops
	 */
	public AdultProtoceratops(String name) {
		super(name, 'D', 30);
	}



	/**
	 * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
	 * die and turn to corpse, and to check if the the dinosaur aligiable for breeding or not, if so an egg will be
	 * laid and will appear on the map.
	 * 'c' represent the corpse of dead Protoceratop.
	 * 'e' represent the egg of the Protoceratop.
	 * 'f' represent the food purchased from the shop.
	 *we
	 *
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Location location = map.locationOf(this);
		proEat(location);
		if (isHungry()) {
			dinoAction = hungerBehaviour.getAction(this, map);
			System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );

		} else {
			dinoAction = wanderBehaviour.getAction(this, map);
		}

		//checkHunger();
		Location n = null;
		Egg egg = null;
		food_level_pro -= 1;
		if (food_level_pro == 0){
			Corpse c = new Corpse("Protoceratops_Corpse", 'C', true);
			c.convertItem().execute(this, map);
			map.removeActor(this);
			return new DoNothingAction();
		}

		if (food_level_pro > 20 && Math.random() >= 0.6) {
			egg = new Egg("Protoceratops_Egg",'e',true, DinoType.PROTOCERATOPS);
			n = new Location(map, map.locationOf(this).x(), map.locationOf(this).y());
			egg.convertItem().execute(this,map);
			System.out.println(egg.getEggType());
			food_level_pro = 20;
			t = true;
		}

		if (dinoAction != null)
			return dinoAction;

		return new DoNothingAction();
	}


}
