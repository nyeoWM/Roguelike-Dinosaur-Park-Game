package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.EndingType;

/**
 * class EndGameAction
 */

public class EndGameAction extends Action {
    protected Item item;
    private EndingType endType;

    /**
     * constructor for the EndGameAction.
     * @param endingType       the way the game is going to end.
     */
    public EndGameAction(EndingType endingType) {
        endType = endingType;
    }


    /**
     *
     */

    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return endingDescription(actor);
    }

    /**
     * method to end the game if the user chooses quit from the menu.
     * @param actor The actor performing the action.
     * @return      a string to indicate the ending of the game.
     */
    @Override
    public String menuDescription(Actor actor) {
        String menuString = "";
        switch (this.endType) {
            case QUITGAME:
                menuString = "Quiting the Game";
        }
        return menuString;
    }


    @Override
    public String hotkey() {
        return "z";
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player drops the potato"
     */
    public String endingDescription(Actor actor) {
        String endString = "";
        switch (this.endType) {
            case QUITGAME:
                endString = "You Quit the Game... \n== See you, space dino-farmer";
                break;
            case PLAYERLOSES:
                endString = "You Lost the Game... \n== Unlucky, you will get better in time";
                break;
            case PLAYERWINS:
                endString = "You Won the Game... \n== Harry, you're a MASTER DINO FARMER";
                break;
        }

    return endString;
    }

}
