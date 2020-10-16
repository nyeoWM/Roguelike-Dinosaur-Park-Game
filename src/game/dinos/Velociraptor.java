package game.dinos;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.behaviors.WanderBehaviour;
import game.behaviors.HungerBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Velociraptor extends Actor{

    protected Behaviour wanderBehaviour;
    protected Behaviour hungerBehaviour;
    protected Integer food_level_velo = 15;
    private boolean t = false;
    private Action dinoAction;


    /**
     * Constructor.
     * All Velociraptors are represented by a 'V' and have 100 hit points.
     *
     * @param name the name of this Velociraptors
     */
    public Velociraptor(String name, Character character,Integer food_level_velo) {
        super(name, character, 100);
        addSkill(DinoType.VELOCIRAPTOR);
        wanderBehaviour = new WanderBehaviour();
        hungerBehaviour = new HungerBehaviour();
        this.food_level_velo = food_level_velo;
    }

    /**
     * return the name of the Velociraptor.
     * @return      the name of the Velociraptor.
     */
    public String getName(){
        return name;
    }


    /**
     * performing attack action on the actor (Velociraptor) if possible by another actor.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return           attack action on the targeted Velociraptor.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
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
    public abstract Action playTurn(Actions actions, Action lastAction, GameMap map, Display display);
    /**
     * return the name and location of the hungry dinosaur
     */

    public void veloEat(Location myLocation, GameMap map) {
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
            }else {
                if (L.getItems().stream().anyMatch(item -> item.hasSkill(ConsumableBy.CARNIVORE))) {
                    locationsWithFood.add(L);
                }
            }
        }
        for(Location LL : locationsWithFood){
            System.out.println(LL.toString());
        }
        //random function to pick a location to eat from, then code to add to velo food level depeding on items eaten
        if (locationsWithFood.size() > 0) {
            Random r = new Random();
            Location targetLocation = locationsWithFood.get(r.nextInt(locationsWithFood.size()));
            if (targetLocation.getActor() != null){
                if (targetLocation.getActor().hasSkill(ConsumableBy.CARNIVORE)) {
                    food_level_velo += 60;
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
                        case 'F':
                            food_level_velo = 100;
                        case 'e':
                            food_level_velo += 10;
                        case 'C':
                            food_level_velo += 50;
                    }
                    targetLocation.removeItem(targetItem);
                }
            }
        }
    }



    public boolean isHungry() {
        if (food_level_velo < 20) {
            return true;
        } else {
            return false;
        }
    }




}
