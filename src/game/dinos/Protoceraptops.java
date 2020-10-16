package game.dinos;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.behaviors.WanderBehaviour;
import game.behaviors.HungerBehaviour;
import game.skills.ConsumableBy;
import game.skills.DinoType;
import game.skills.GroundType;
import game.terrain.Dirt;

public abstract class Protoceraptops extends Actor{
    protected Behaviour wanderBehaviour;
    protected Behaviour hungerBehaviour;
    protected Integer food_level_pro;
    private boolean t = false;
    private Action dinoAction;


    /**
     * Constructor.
     * All Protoceratops are represented by a 'D' and have 100 hit points.
     *
     * @param name the name of this Protoceratops
     */
    public Protoceraptops(String name, Character character, Integer food_level_pro) {
        super(name, character, 100);
        addSkill(DinoType.PROTOCERATOPS);
        addSkill(ConsumableBy.CARNIVORE);
        addSkill(ConsumableBy.WATERCARNIVORE);
        wanderBehaviour = new WanderBehaviour();
        hungerBehaviour = new HungerBehaviour();
        this.food_level_pro = food_level_pro;

    }

    /**
     * return the name of the dinosaur.
     * @return				the dinosaur name.
     */
    public String getName(){
        return name;
    }

    /**
     * performing attack action on the actor (Protoceratops) if possible by another actor.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return			 attack action on the targeted Protoceratop.
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
     * 'f' represent the food purchased from the shop.
     *we
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public abstract Action playTurn(Actions actions, Action lastAction, GameMap map, Display display);


    public void proEat(Location location){
        if ((location.getGround().hasSkill(GroundType.TREE)) && (food_level_pro < 50)){
            food_level_pro +=10;
            location.setGround(new Dirt());
        }

        if ((location.getGround().hasSkill(GroundType.GRASS)) && (food_level_pro < 50)){
            food_level_pro += 5;
            location.setGround(new Dirt());
        }
        if ((location.getGround().getDisplayChar() == 'f')&& (food_level_pro < 50)) {
            food_level_pro += 20;
            location.setGround(new Dirt());
        }
        if (food_level_pro > 50){
            food_level_pro = 50;
        }
    }

    public boolean isHungry(){
        return food_level_pro < 10;
    }



}
