package mineopoly_three.competition;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;

import java.awt.*;
import java.util.Random;

/**
 * Charging step of MyStrategy. This step will move to the nearest recharge tile and standing there until reach max
 * charge
 */
public class ChargingStep implements Step  {
    private int maxCharge;
    private Random random;

    private Point targetLocation;

    /**
     * Initialize a charging step.
     *
     * @param maxCharge The amount of charge your robot starts with (number of tile moves before needing to recharge)
     * @param random A random number generator
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     */
    public ChargingStep(int maxCharge, Random random, PlayerBoardView boardView) {
        this.maxCharge = maxCharge;
        this.random = random;

        targetLocation = StepHelper.getLocationOfNearestTileType(boardView, TileType.RECHARGE);
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy) {
        if (boardView.getYourLocation().equals(targetLocation)) {
            return null;
        }
        return StepHelper.getTurnActionToLocation(boardView, targetLocation, random);
    }

    @Override
    public boolean shouldEndStep(PlayerBoardView boardView, int currentCharge, int currentInventorySize) {
        return targetLocation == null || currentCharge == maxCharge;
    }
}
