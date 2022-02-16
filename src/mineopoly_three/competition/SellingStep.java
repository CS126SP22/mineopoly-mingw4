package mineopoly_three.competition;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;

import java.awt.*;
import java.util.Random;

/**
 * Selling step of MyStrategy. This step will move to the nearest market tile.
 */
public class SellingStep implements Step  {
    private Random random;

    private Point targetLocation;

    /**
     * Initialize a selling step.
     *
     * @param isRedPlayer True if this strategy is the red player, false otherwise
     * @param random A random number generator
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     */
    public SellingStep(boolean isRedPlayer, Random random, PlayerBoardView boardView) {
        this.random = random;

        targetLocation = StepHelper.getLocationOfNearestTileType(boardView,
                isRedPlayer ? TileType.RED_MARKET : TileType.BLUE_MARKET);
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy) {
        return StepHelper.getTurnActionToLocation(boardView, targetLocation, random);
    }

    @Override
    public boolean shouldEndStep(PlayerBoardView boardView, int currentCharge, int currentInventorySize) {
        return targetLocation == null || boardView.getYourLocation().equals(targetLocation);
    }
}
