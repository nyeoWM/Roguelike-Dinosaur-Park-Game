package game.dinos;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.behaviors.FlyBehaviour;
import game.behaviors.HungerBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import game.skills.HabitatClassification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * class Pteranodons.
 */

public abstract class Pteranodons extends Actor {
    protected Behaviour flyBehaviour;
    protected Behaviour hungerBehaviour;
    protected Integer food_level_pte = 30;
    private boolean t = false;
    private Action dinoAction;

    /**
     * constructor for Pteranodons class
     * all Pteranodons on the map will represented by 'R' for adult and 'r' for baby.
     * @param name              name of the Pteranodons.
     * @param character         character representing Pteranodons adult or baby.
     * @param food_level_pte    food level of the Pteranodons.
     */
    public Pteranodons(String name,Character character, Integer food_level_pte){
        super(name, character,200);
        // because it can travel on both water and land, it can be consumed by both carnivores and water carnivores
        addSkill(DinoType.PTERANODONS);
        addSkill(ConsumableBy.CARNIVORE);
        addSkill(ConsumableBy.WATERCARNIVORE);
        addSkill(HabitatClassification.WATER);
        addSkill(HabitatClassification.LAND);
        flyBehaviour = new FlyBehaviour();
        hungerBehaviour = new HungerBehaviour();
        this.food_level_pte = food_level_pte;

    }

    /**
     * return the name of the Pteranodons.
     * @return				the Pteranodons name.
     */
    public String getName(){
        return name;
    }

    /**
     * performing attack action on the actor (Pteranodons) if possible by another actor.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return			 attack action on the targeted Pteranodons.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }


    /**
     * Figure out what to do next. and check if the food level of dinosaur to check if its still alive, if not dinosaur will
     * die and turn to corpse, and to check if the the dinosaur aligiable for breeding or not, if so an egg will be
     * laid and will appear on the map.
     * 'c' represent the corpse of dead Protoceratop.
     * 'e' represent the egg of the Protoceratop.
     * 'M' represent the Marine food purchased from the shop.
     * 'F' represent the carnivore food purchased form the shop.
     * 'X' represent the corpse of a Velociraptor.
     * 'x' represent the corpse of a baby velociraptor.
     * '^' represent a fish in the water.
     * '?' represent the corpse of a Trex.
     * '{' represent the corpse of Plesiosaurs.
     * '[' represent the corpse of Pterandodons.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public abstract Action playTurn(Actions actions, Action lastAction, GameMap map, Display display);

    /**
     * eat method for Pteranodons in which fish corpses, marine and carnivore feed been given priority to be eaten first.
     * @param myLocation        location of the Pteranodons.
     * @param map               map that the Pteranodons is on.
     */
    public void pteEat1(Location myLocation, GameMap map) {
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
                if (L.getItems().stream().anyMatch(item -> item.hasSkill(ConsumableBy.CARNIVORE))) {
                    locationsWithFood.add(L);
                }
            }
        }
        for(Location LL : locationsWithFood){
            System.out.println(LL.toString());
        }
        //random function to pick a location to eat from, then code to add to pte food level depending on items eaten
        if (locationsWithFood.size() > 0) {
            Random r = new Random();
            Location targetLocation = locationsWithFood.get(r.nextInt(locationsWithFood.size()));
            if (targetLocation.getActor() != null){
                if (targetLocation.getActor().hasSkill(ConsumableBy.CARNIVORE) || targetLocation.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)){
                    food_level_pte += 60;
                    map.removeActor(map.getActorAt(targetLocation));
                }
            }
            else {
                List<Item> items = targetLocation.getItems().stream()
                        .filter(item -> item.hasSkill(ConsumableBy.CARNIVORE))
                        .collect(Collectors.toList());
                if (items.size() > 0) {
                    Item targetItem = items.get(0);
                    char targetChar = targetItem.getDisplayChar();
                    switch (targetChar) {
                        case 'M':
                            food_level_pte = 200;
                        case 'e':
                            food_level_pte += 10;
                        case 'C':
                            food_level_pte += 50;
                        case '^':
                            food_level_pte += 30;
                        case '?':
                            food_level_pte += 60;
                        case 'X' :
                            food_level_pte += 60;
                        case 'x' :
                            food_level_pte += 30;
                        case '{':
                            food_level_pte += 60;
                        case '[':
                            food_level_pte +=60;
                        case 'F':
                            food_level_pte += 200;
                        case 'k':
                            return;
                    }
                    targetLocation.removeItem(targetItem);
                }
            }
        }
    }

    /**
     * PteEat2 method which will handle eating behaviour of the Pteranodons if and only if his priority food can not be found around
     * him so he will attack any dinosaur in his range.
     * @param myLocation        location of the Pteranodons.
     * @param map               map that the Pteranodons is on.
     */
    public void pteEat2(Location myLocation, GameMap map) {
        // Returns a list of all exits (inclusive of current location) containing edible items/ actors)
        List<Location> locationsWithFood = new ArrayList<>();
        List<Location> locations = myLocation.getExits().stream()
                .map(Exit::getDestination)
                .collect(Collectors.toList());
        locations.add(myLocation);
        for (Location L : locations) {
            if (L.getActor()!= null) {
                if (L.getActor().hasSkill(ConsumableBy.CARNIVORE)) {
                    locationsWithFood.add(L);
                }
        if (locationsWithFood.size() > 0) {
            Random r = new Random();
            Location targetLocation = locationsWithFood.get(r.nextInt(locationsWithFood.size()));
            if (targetLocation.getActor() != null){
                if (targetLocation.getActor().hasSkill(ConsumableBy.CARNIVORE) || targetLocation.getActor().hasSkill(ConsumableBy.WATERCARNIVORE)){
                    food_level_pte += 60;
                    map.removeActor(map.getActorAt(targetLocation));
                }
            }
                }
            }
        }
    }


    /**
     * checks if the Pteranodons hungry or not.
     * @return      returns true if the Pteranodons is hungry false otherwise.
     */
    public boolean isHungry(){
        return food_level_pte < 50;
    }




}
