package game.shop;

import edu.monash.fit2099.engine.*;
import game.dinos.AdultProtoceratops;
import game.items.Egg;
import game.items.Tag;
import game.skills.ConsumableBy;
import game.items.Food;
import game.skills.DinoType;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * shop class
 * It extends ground and returns an action to player, enterShop when the
 * player is adjacent to the shop on the map. When the player enters the
 * shop, the game enters an internal loop that freezes time until the player
 * is done with shopping.
 */
public class Shop extends Ground {

    private HashMap<String,Double>  itemsOnSale = new HashMap<String, Double>();
    private HashMap<String,Double>  itemsToBuy = new HashMap<String, Double>();
    private HashMap<AdultProtoceratops, Integer> live_dino_sold = new HashMap<AdultProtoceratops, Integer>();
    private boolean inShop;
    private boolean validInput;
    private Location shopLocation;
    private Actor player;
    private Bank bank;
    /**
     * constructor for the shop class.
     */
    public Shop() {
        super('S');
        itemsToBuy.put("Protoceratops_Corpse",10.0);
        itemsToBuy.put("Protoceratops_Egg",15.0);
        itemsToBuy.put("Protoceratops", 100.0);
        itemsToBuy.put("Velociraptor", 100.0);
        itemsToBuy.put("Plesiosaurs",1000.0);
        itemsToBuy.put("Pteranodons",1000.0);



        itemsOnSale.put("Herbivore_Food",20.0);
        itemsOnSale.put("Protoceratops_Egg",50.0);
        itemsOnSale.put("Velociraptor_Egg",1000.0);
        itemsOnSale.put("Carnivore_Food",100.0);
        itemsOnSale.put("Plesiosaurs_Egg",3000.0);
        itemsOnSale.put("Pteranodons_Egg",3000.0);
        itemsOnSale.put("Marine_Food",300.0);
        itemsOnSale.put("Trex_Egg",30000.0);
        itemsOnSale.put("Tag",0.0);
        this.bank = new Bank(1500);

    }

    /**
     * method used to sell live Dinosaurs to the shop.
     * @param   dinoName string representing the dino name.
     */
    public void sellDino(String dinoName) {
        if (dinoName.equals("Plesiosaurs")){
            bank.deposit(5000);
            return;
        }
        if (dinoName.equals("Pteranodons") ){
            bank.deposit(5000);
            return;
        }
        if (dinoName.equals("TREX")||dinoName.equals(("REAREDTREX"))){
            bank.deposit(30000);
            return;
        }
        bank.deposit(100.0);

    }


    /**
     * return the menu of the shop.
     * Selecting an item will cause the shop to first process if the player is allowed
     * to do the action. ie does the player have enough money to purchase said item.
     * or whether the player has the item in the bank, then calling bank to deduct or
     * add the money to the players account.
     */

    private void printMenu() {
        System.out.println("####################################");
        System.out.println("######## Welcome to the Shop #########");
        System.out.println("####################################");
        System.out.println("0. Exit the Shop");
        System.out.println("1. Check Your Inventory");
        System.out.println("2. Display Account Balance");
        System.out.println("3. Buy Herbivore Food for $20");
        System.out.println("4. Buy Carnivore Food for $100");
        System.out.println("5. Buy Marine Food for $300");
        System.out.println("6. Buy Velociraptor Egg for $1000");
        System.out.println("7. Buy Dino Tag");
        System.out.println("8. Buy Protoceratops Egg for $50");
        System.out.println("9. Buy Plesiosaurs Egg for $3000");
        System.out.println("a. Buy Pteranodons Egg for $3000");
        System.out.println("b. Buy Trex Egg for $30000");
        System.out.println("c. Sell Protoceratops Egg for $10");
        System.out.println("d. Sell Protoceratops Corpse for $15");
        System.out.println("######################################");
        //getting the input char also implement error checking
        char inputChar = this.readString("What would you like to do?\n");
        // if statements to check what choice the player made
        if (inputChar == '0') {
            exitShop();
        } else if (inputChar == '1') {
            System.out.println("This is your inventory:");
            List<Item> inventory =  this.player.getInventory();
            for (int i = 0; i  < inventory.size(); i++) {
                System.out.println(i);
                System.out.println(inventory.get(i).toString() + "\n") ;
            }
        } else if (inputChar == '2') {
            System.out.println("Your balance is: " + bank.getBalance());

        } else if (inputChar == '3') {
            buyItemFromShop("Herbivore_Food");
        } else if (inputChar == '4') {
            buyItemFromShop("Carnivore_Food");
        } else if (inputChar == '5') {
            buyItemFromShop("Marine_Food");
        } else if (inputChar == '6') {
            buyItemFromShop("Velociraptor_Egg");
        } else if (inputChar == '7') {
            buyItemFromShop("Tag");
        }else if (inputChar == '8') {
            buyItemFromShop("Protoceratops Egg");
        }else if (inputChar == '9') {
            buyItemFromShop("Plesiosaurs Egg");
        } else if (inputChar == 'a') {
            buyItemFromShop("Pteranodons Egg");
        } else if (inputChar == 'b'){
            buyItemFromShop("Trex Egg");
        } else if (inputChar == 'c'){
            sellItemToShop("Protoceratops Egg");
        } else if(inputChar == 'd') {
            sellItemToShop("Protoceratops Corpse");
        }else {
            System.out.println("Invalid Character, please try again.");
        }
    }

    /**
     * method to handle the buying action from the shop and deduct the money (item price) from the bank-->account.
     * @param item      item player's wants to buy.
     * the item bought will be dropped at a random location in front of the shop.
     */
    private void buyItemFromShop(String item) {
        if (!itemsOnSale.containsKey(item)){
            System.out.println("Item not On Sale Anymore");
            return;
        }
        double price = itemsOnSale.get(item);
        try {
            bank.deduct(price);
        } catch (Exception o) {
            System.out.println(o.getMessage());
            return;
        }
        System.out.println("The item will be dropped on the floor at a random location. Please exit the shop to pick up your item.");
        //price is obtained from a hashmap, meaning its extensible in the future
        switch (item){
            case("Herbivore_Food"):
                Food foodH = new Food(item, 'f', true, ConsumableBy.HERBIVORE);
                getRandomExitLocation().addItem(foodH);
                break;
            case("Carnivore_Food"):
                Food foodC = new Food(item, 'F', true, ConsumableBy.CARNIVORE);
                getRandomExitLocation().addItem(foodC);
                break;
            case ("Marine_Food"):
                Food foodPle = new Food(item, 'M', true, ConsumableBy.WATERCARNIVORE);
                getRandomExitLocation().addItem(foodPle);
            case("Velociraptor_Egg"):
                Egg eggV = new Egg(item, 'o', true, DinoType.VELOCIRAPTOR);
                getRandomExitLocation().addItem(eggV);
                break;
            case("Protoceratops_Egg"):
                Egg eggP = new Egg(item, 'e', true, DinoType.PROTOCERATOPS);
                getRandomExitLocation().addItem(eggP);
                break;
            case ("Plesiosaurs_Egg"):
                Egg eggPle = new Egg(item, 'b', true, DinoType.PLESIOSAURS);
                getRandomExitLocation().addItem(eggPle);
            case ("Pteranodons_Egg"):
                Egg eggPte = new Egg(item, 'k', true, DinoType.PTERANODONS);
                getRandomExitLocation().addItem(eggPte);
            case ("Trex_Egg"):
                Egg eggT = new Egg(item, 'm', true, DinoType.TREX);
                getRandomExitLocation().addItem(eggT);
            case("Tag"):
                Tag tag = new Tag(item, '/',true, this);
                getRandomExitLocation().addItem(tag);
                break;
        }
    }

    /**
     * method to handle the selling action from the shop and removing the item from player inventory and depositing money
     * to player account.
     * @param item          item player wants to sell.
     */
    private void sellItemToShop(String item){
        if (!itemsToBuy.containsKey(item)) {
            System.out.println("Not Interested In Buying Item");
            return;
        }
        List<Item> existenceOfItem = player.getInventory().stream()
                .filter(someItem -> someItem.toString() == item)
                .collect(Collectors.toList());

        if ( existenceOfItem.size() <= 0) {
            System.out.println("You do not have this Item in your Inventory");
        } else {
            player.removeItemFromInventory(existenceOfItem.get(0));
            bank.deposit(itemsToBuy.get(item));
        }
    }

    /**
     * used to drop the purchased item at random location near the player.
     * @return          the item at random location.
     */
    private Location getRandomExitLocation() {
        List<Location> locations = shopLocation.getExits().stream()
                            .map(exit -> exit.getDestination())
                            .filter(location -> location.getGround().canActorEnter(player))
                            .collect(Collectors.toList());
        Random r = new Random();
        return locations.get(r.nextInt(locations.size()));
    }

    /**
     * when the player is in the shop menu will be displayed to choose action.
     * @param Player        player in the shop.
     */
    public void enterShop(Actor Player, Location shopLocation) {
        this.shopLocation = shopLocation;
        this.inShop = true;
        this.player = Player;
        // infinite loop until player exits shop
        while (inShop) {
            //this.createSellList();
            //this.createBuyList()
            //create shop action list

            this.printMenu();
        }
    }

    /**
     * reads input from user to decide what action player will make while inside the shop.
     * @param prompt        prompt entered by user.
     * @return              the line
     */
    private char readString(String prompt) {
        System.out.print(prompt);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        char c;
        try {
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s.length() < 1){
            c = 'f';
        } else {
            c = s.charAt(0);
        }
        return c;
    }

    /**
     * returns position of player --> outside the shop.
     */
    private void exitShop() {
        this.inShop = false;
    }


    /**
     *  Returns an Action list.
     *
     *  @param actor the Actor acting.
     *  @param location the current Location.
     *  @param direction the direction of the Ground from the Actor.
     *  @return a new collection of Actions.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        return new Actions(new EnterShopAction(actor, this, location));
    }

    /**
     * Returns false if an Actor can not enter this location.
     * Actors can enter a location if the terrain is passable and there isn't an Actor there already.
     * Game rule. One actor per location.
     * @param actor the Actor who might be moving
     * @return false if the Actor can enter this location
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }


    /**
     * implement terrain.
     * @return      returns false
     */
    @Override
    public boolean blocksThrownObjects() {
        return super.blocksThrownObjects();
    }
}
