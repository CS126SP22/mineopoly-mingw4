package mineopoly_three.competition;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.strategy.PlayerBoardView;

/**
 * A step that is part of a strategy
 */
public interface Step {

    /**
     * Returns what action your player should do on this turn
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param economy The GameEngine's economy object which holds current prices for resources
     * @return The TurnAction enum for the action that this step wants to perform on this game turn
     */
    TurnAction getTurnAction(PlayerBoardView boardView, Economy economy);

    /**
     * Whether the step should end. If true, the strategy should move the the next step
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param currentCharge The amount of charge your robot has (number of tile moves before needing to recharge)
     * @param currentInventorySize The size of the inventory
     * @return true if the step should end, and false otherwise
     */
    boolean shouldEndStep(PlayerBoardView boardView, int currentCharge, int currentInventorySize);
}
