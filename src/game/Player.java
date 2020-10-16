package game;

import edu.monash.fit2099.engine.*;

import game.actions.EndGameAction;
import game.skills.DinoType;
import game.skills.HabitatClassification;


/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();


//	private ArrayList<String>  Inventory = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		addSkill(HabitatClassification.LAND);
	}

	/**
	 * figuer out what to do next, and heal the player by 10 points after every new turn after he has been attacked.
	 * and will check the way have ended, wining, losing or quiting the game.
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return				in what way the game has ended.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		this.heal(10);
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null) {
			return lastAction.getNextAction();
		}

		if (!isConscious()) {
			return new EndGameAction(EndingType.PLAYERLOSES);
		}

		if (findRearedCaptiveTrex(map.locationOf(this), map)){
			return new EndGameAction(EndingType.PLAYERWINS);
		}



		actions.add(new EndGameAction(EndingType.QUITGAME));
		return menu.showMenu(this, actions, display);
	}

	/**
	 * adds the item bought from shop to player inventory.
	 * @param item		item bought.
	 */
	public void addToInvetroy(Item item) {
		addItemToInventory(item);
	}

	/**
	 * removes item sold to the shop from player inventory or dropped.
	 * @param item		item sold or dropped.
	 */
	public void removeFromInventory(Item item) {
		removeFromInventory(item);
		return;
	}


	public boolean isHungry() {
		return false;
	}


	/**
	 * checks the maps to find reared trex.
	 * @param myLocation		location of the reared trex.
	 * @param map				map that the player on
	 * @return					tru if the rear trex exists on the map, false otherwise.
	 */
	public Boolean findRearedCaptiveTrex(Location myLocation, GameMap map) {

		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		for (int x : xRange) {
			for (int y : yRange) {
				Location newLocation = map.at(x, y);
				Actor someActor = newLocation.getActor();
				if (someActor != null) {
					if (newLocation.getActor().toString() == "Reared TRex") {
						return true;
					}
				}

			}
		}
		return false;
	}
}

