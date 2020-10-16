package game.dinos;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.behaviors.WanderBehaviour;
import game.behaviors.HungerBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import game.skills.HabitatClassification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public abstract class Trex extends Actor {

    protected Behaviour wanderBehaviour;
    protected Behaviour hungerBehaviour;
    protected Integer food_level_trex = 500;
    private boolean t = false;
    private Action dinoAction;

    /**
     * Constructor.
     * All Trex are represented by a 'T' and have 100 hit points.
     *
     * @param name the name of this Trex
     */
    public Trex (String name, Character character, DinoType someDino) {
        super(name, character, 100);
        addSkill(someDino);
        addSkill(HabitatClassification.LAND);
        wanderBehaviour = new WanderBehaviour();
        hungerBehaviour = new HungerBehaviour();
    }

    /**
     * return the name of the Trex.
     * @return      the name of the Trex.
     */
    public String getName(){
        return name;
    }

    /**
     * performing attack action on the actor (Trex) if possible by another actor.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return           attack action on the targeted Trex.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur is allegeable for breeding or not, if so an egg will be
     * laid and will appear on the map.
     * 'x' represent babay velociraptor's corpse.
     * 'X' represent adult velociraptor dinosaur corpse.
     * 'o' represent velocirapto's egg.
     * 'e' represent protoceratop's egg.
     * 'C' represent adult protoceratop corpse.
     * 'F' represent food source bought from the shop.
     * '[' represent pteranodons corpse.
     * 'b' represent Plesiosaur's egg.
     * 'k' represent Pteranodon's egg.
     * each of these food sources ('e','C','F','D','d','['), will increase the food level index of the dinosaur by different value.
     *
     * FIXME: Trex wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public abstract Action playTurn(Actions actions, Action lastAction, GameMap map, Display display);
    /**
     * return the name and location of the hungry dinosaur
     */

    public void trexEat(Location myLocation, GameMap map) {
        // Returns a list of all exits (inclusive of current location) containing edible items/ actors)
        List<Location> locationsWithFood = new ArrayList<>();
        List<Location> locations = myLocation.getExits().stream()
                .map(Exit::getDestination)
                .collect(Collectors.toList());
        locations.add(myLocation);
        for (Location L : locations) {
            if (L.getActor()!= null) {
                if (L.getActor().hasSkill(ConsumableBy.CARNIVORE)||L.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)) {
                    locationsWithFood.add(L);
                }
            }else {
                if (L.getItems().stream().anyMatch(item -> (item.hasSkill(ConsumableBy.CARNIVORE))||item.hasSkill(ConsumableBy.WATERCARNIVORE))) {
                    locationsWithFood.add(L);
                }
            }
        }
        for(Location LL : locationsWithFood){
            System.out.println(LL.toString());
        }
        //random function to pick a location to eat from, then code to add to trex food level depending on items eaten
        if (locationsWithFood.size() > 0) {
            Random r = new Random();
            Location targetLocation = locationsWithFood.get(r.nextInt(locationsWithFood.size()));
            if (targetLocation.getActor() != null){
                if (targetLocation.getActor().hasSkill(ConsumableBy.CARNIVORE)||targetLocation.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)) {
                    food_level_trex += 60;
                    map.removeActor(map.getActorAt(targetLocation));
                }
            }
            else {
                List<Item> items = targetLocation.getItems().stream()
                        .filter(item -> (item.hasSkill(ConsumableBy.CARNIVORE)||item.hasSkill(ConsumableBy.WATERCARNIVORE)))
                        .collect(Collectors.toList());
                if (items.size() > 0) {
                    Item targetItem = items.get(0);
                    char targetChar = targetItem.getDisplayChar();
                    switch (targetChar) {
                        case 'F':
                            food_level_trex += 200;
                        case 'e':
                            food_level_trex += 10;
                        case 'C':
                            food_level_trex += 50;
                        case 'X':
                            food_level_trex += 60;
                        case 'x' :
                            food_level_trex += 30;
                        case '[':
                            food_level_trex += 60;
                        case 'k':
                            food_level_trex +=10;
                        case 'm':
                            return;
                    }
                    targetLocation.removeItem(targetItem);
                }
            }
        }
    }



    public boolean isHungry() {
        return food_level_trex < 150;
    }




}
