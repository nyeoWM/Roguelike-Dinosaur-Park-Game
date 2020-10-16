package game.dinos;


import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.items.Corpse;
import game.items.Egg;
import game.skills.DinoType;


/**
 * A Carnivores dinosaur.
 *
 */
public class AdultVelociraptor extends Velociraptor {
	// Will need to change this to a collection if Protoceratops gets additional Behaviours.

//	private Integer food_level_velo = 15;
    private boolean t = false;
	private Action dinoAction;



	/**
	 * Constructor.
	 * All Velociraptors are represented by a 'V' and have 100 hit points.
	 *
	 * @param name the name of this Velociraptors
	 */
	public AdultVelociraptor(String name) {
		super(name, 'V',50);
	}


	/**
	 * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur is allegeable for breeding or not, if so an egg will be
     * laid and will appear on the map.
	 * 'x' represent velocirapto dinosaur corpse.
     * 'o' represent velocirapto's egg.
     * 'e' represent protoceratop's egg.
     * 'C' represent adult protoceratop corpse.
     * 'F' represent food source bought from the shop.
	 * 'D' represent adult protoceratop.
	 * 'd' represent baby protocerratop.
     * each of these food sources ('e','C','F','D','d'), will increase the food level index of the dinosaur by different value.
     *
	 * FIXME: Velociraptors wanders around at random, or if no suitable MoveActions are available, it
	 * just stands there.  That's boring.
	 *
	 * @see Actor#playTurn(Actions, Action, GameMap, Display)
	 */

	@Override
	public  Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Location location = map.locationOf(this);
		veloEat(location, map);
		if (isHungry()) {
			dinoAction = hungerBehaviour.getAction(this, map);
			System.out.println(getName()+" at location: " + "(" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ")" + "is hungry..." );
		} else {
			dinoAction = wanderBehaviour.getAction(this, map);
		}
		//checkHunger();
        Location n = null;
        Egg egg = null;
		food_level_velo -= 1;
		if (food_level_velo == 0){
			Corpse c = new Corpse("Velociraptor_Corpse", 'X', true);
            c.convertItem().execute(this, map);
            map.removeActor(this);
			return new DoNothingAction();

		}

		if (food_level_velo > 60 && Math.random() >= 0.7) {
            egg = new Egg("Velociraptor_Egg",'o',true, DinoType.VELOCIRAPTOR);
            n = new Location(map, map.locationOf(this).x(), map.locationOf(this).y());
            egg.convertItem().execute(this,map);
            System.out.println(egg.getEggType());
            t = true;
		}


		if (food_level_velo > 100) {
			food_level_velo = 100;
		}
		System.out.println("velo food level is " + food_level_velo);

		if (dinoAction != null)
			return dinoAction;

		return new DoNothingAction();
	}


}
