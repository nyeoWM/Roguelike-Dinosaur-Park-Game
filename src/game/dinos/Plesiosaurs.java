package game.dinos;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
//import game.Dirt;
//import game.Interfaces.DinoInterface;
import game.behaviors.WanderBehaviour;
import game.behaviors.HungerBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import game.skills.HabitatClassification;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * class Plesiosaurs
 */

public abstract class Plesiosaurs extends Actor {

    protected Behaviour wanderBehaviour;
    protected Behaviour hungerBehaviour;
    protected Integer food_level_ple;
    private boolean t = false;
    private Action dinoAction;

    /**
     * constructor for the class Plesiosaurs.
     * all Plesiosaurs on the map will represented by P for adult and p for baby.
     * @param name              name of the Plesiosaurs.
     * @param character         character that represent the Plesiosaurs adult or baby.
     * @param food_level_ple    food level of the Plesiosaurs.
     */

    public Plesiosaurs(String name, Character character,Integer food_level_ple) {
        super(name, character, 200);
        addSkill(DinoType.PLESIOSAURS);
        addSkill(ConsumableBy.CARNIVORE);
        // to ensure that it can only swim
        addSkill((HabitatClassification.WATER));
        wanderBehaviour = new WanderBehaviour();
        hungerBehaviour = new HungerBehaviour();
        this.food_level_ple = food_level_ple;

    }

    /**
     * returns the name of the Plesiosaurs
     * @return      name of Plesiosaurs.
     */
    public String getName() {
        return name;
    }

    /**
     * performing attack action on the actor (Plesiosaurs) if possible by another actor.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return attack action on the targeted Plesiosaurs.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur aligiable for breeding or not, if so an egg will be
     * laid and will appear on the map. also will check if there is any dinosaur near the water where the Plesiosaurs can attack
     * if yes, the Plesiosaurs will attack that dinosaur.
     * '{' represent the corpse of dead Plesiosaurs.
     * 'e' represent the egg of the Protoceratop.
     * 'M' represent the Marine food purchased from the shop.
     * '^' represent a fish in the water.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public abstract Action playTurn(Actions actions, Action lastAction, GameMap map, Display display);

    public void pleEat(Location myLocation, GameMap map) {
        // Returns a list of all exits (inclusive of current location) containing edible items/ actors)
        List<Location> locationsWithFood = new ArrayList<>();
        List<Location> locations = myLocation.getExits().stream()
                .map(Exit::getDestination)
                .collect(Collectors.toList());
        locations.add(myLocation);
        for (Location L : locations) {
            if (L.getActor()!= null) {
                if (L.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)) {
                    locationsWithFood.add(L);
                }
            }else {
                if (L.getItems().stream().anyMatch(item -> item.hasSkill(ConsumableBy.WATERCARNIVORE))) {
                    locationsWithFood.add(L);
                }
            }
        }

        //random function to pick a location to eat from, then code to add to ple food level depending on items eaten
        if (locationsWithFood.size() > 0) {
            Random r = new Random();
            Location targetLocation = locationsWithFood.get(r.nextInt(locationsWithFood.size()));
            if (targetLocation.getActor() != null){
                if (targetLocation.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)) {
                    food_level_ple += 60;
                    map.removeActor(map.getActorAt(targetLocation));
                }
            }
            else {
                List<Item> items = targetLocation.getItems().stream()
                        .filter(item -> item.hasSkill(ConsumableBy.WATERCARNIVORE))
                        .collect(Collectors.toList());
                if (items.size() > 0) {
                    Item targetItem = items.get(0);
                    char targetChar = targetItem.getDisplayChar();
                    switch (targetChar) {
                        case '^':
                            food_level_ple += 30;
                        case 'M':
                            food_level_ple = 200;
                        case 'e':
                            food_level_ple += 10;
                        case '}':
                            food_level_ple += 50;

                    }
                    targetLocation.removeItem(targetItem);
                }
            }
        }
    }

    /**
     * checks if the Plesiosaurs hungry or not.
     * @return      true if the Plesiosaurs is hungry, false otherwise.
     */
    public boolean isHungry() {
        return food_level_ple < 40;
    }



}
