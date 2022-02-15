package mineopoly_three.strategy.step;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.ItemType;
import mineopoly_three.strategy.PlayerBoardView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Mining step of MyStrategy. This step will move to the nearest resource tile that sells the highest price in the
 * market and mine the resource. It will repeat this process until the invenotry is full.
 */
public class MiningStep implements Step {
    private int maxInventorySize;
    private Random random;

    private ItemType itemType;
    private Point targetLocation;
    private int currentTurn;

    /**
     * Initialize a mining step.
     *
     * @param maxInventorySize The maximum number of items that your player can carry at one time
     * @param random A random number generator
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param economy The GameEngine's economy object which holds current prices for resources
     */
    public MiningStep(int maxInventorySize, Random random, PlayerBoardView boardView, Economy economy) {
        this.maxInventorySize = maxInventorySize;
        this.random = random;

        updateTargetLocation(boardView, economy);
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy) {
        if (!boardView.getYourLocation().equals(targetLocation)) {
            return StepHelper.getTurnActionToLocation(boardView, targetLocation, random);
        }
        if (currentTurn == itemType.getTurnsToMine()) {
            currentTurn = 0;
            updateTargetLocation(boardView, economy);
            return TurnAction.PICK_UP_RESOURCE;
        }
        currentTurn++;
        return TurnAction.MINE;
    }

    @Override
    public boolean shouldEndStep(PlayerBoardView boardView, int currentCharge, int currentInventorySize) {
        return targetLocation == null || currentInventorySize  == maxInventorySize;
    }

    /**
     * Update targetLocation to be the location of the nearest resource tile that will sell the highest price in the
     * market
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param economy The GameEngine's economy object which holds current prices for resources
     */
    private void updateTargetLocation(PlayerBoardView boardView, Economy economy) {
        List<Entry<ItemType, Integer>> entries = new ArrayList<>(economy.getCurrentPrices().entrySet());
        entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        for (Entry<ItemType, Integer> entry : entries) {
            itemType = entry.getKey();
            targetLocation = StepHelper.getLocationOfNearestTileType(boardView, entry.getKey().getResourceTileType());
            if (targetLocation != null) {
                break;
            }
        }
    }
}
