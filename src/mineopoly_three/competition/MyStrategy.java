package mineopoly_three.competition;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.InventoryItem;
import mineopoly_three.strategy.MinePlayerStrategy;
import mineopoly_three.strategy.PlayerBoardView;

import java.awt.*;
import java.util.Random;

/**
 * This strategy will repeatly execute the following three steps:
 * 1. Mining (defined in MiningStep): Mine resources until inventory is full
 * 2. Selling (defined in SellingStep): Sell inventory to the market
 * 3. Charging (defined in ChargingStep): Charging until it reaches max charge
 * Also see the description of each class
 */
public class MyStrategy implements MinePlayerStrategy {
    private int maxInventorySize;
    private int maxCharge;
    private boolean isRedPlayer;
    private Random random;

    private Step currentStep;
    private int currentInventorySize;

    @Override
    public void initialize(int boardSize, int maxInventorySize, int maxCharge, int winningScore,
                           PlayerBoardView startingBoard, Point startTileLocation, boolean isRedPlayer, Random random) {
        this.maxInventorySize = maxInventorySize;
        this.maxCharge = maxCharge;
        this.isRedPlayer = isRedPlayer;
        this.random = random;

        currentStep = null;
        currentInventorySize = 0;
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy, int currentCharge, boolean isRedTurn) {
        if (currentStep == null) {
            currentStep = new MiningStep(maxInventorySize, random, boardView, economy);
        }

        // If should end, moves to the next step in the sequence
        if (currentStep.shouldEndStep(boardView, currentCharge, currentInventorySize)) {
            if (currentStep instanceof MiningStep) {
                currentStep = new SellingStep(isRedPlayer, random, boardView);
            } else if (currentStep instanceof SellingStep) {
                currentStep = new ChargingStep(maxCharge, random, boardView);
            } else {
                currentStep = new MiningStep(maxInventorySize, random, boardView, economy);
            }
        }
        return currentStep.getTurnAction(boardView, economy);
    }

    @Override
    public void onReceiveItem(InventoryItem itemReceived) {
        currentInventorySize++;
    }

    @Override
    public void onSoldInventory(int totalSellPrice) {
        currentInventorySize = 0;
    }

    @Override
    public String getName() {
        return "MyStrategy";
    }

    @Override
    public void endRound(int totalRedPoints, int totalBluePoints) {

    }
}
