package mineopoly_three;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.ItemType;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.strategy.step.ChargingStep;
import mineopoly_three.strategy.step.MiningStep;
import mineopoly_three.strategy.step.SellingStep;
import mineopoly_three.strategy.step.StepHelper;
import mineopoly_three.tiles.TileType;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.Assert.*;

public class MineopolyTest {

    @Test
    public void testStepHelperGetLocationOfNearestTileType() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.RED_MARKET }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(0, 0), null, 0);
        assertEquals(new Point(1, 0), StepHelper.getLocationOfNearestTileType(boardView, TileType.RED_MARKET));
    }

    @Test
    public void testStepHelperGetLocationOfNearestTileTypeNonExisting() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.RED_MARKET }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(0, 0), null, 0);
        assertNull(StepHelper.getLocationOfNearestTileType(boardView, TileType.BLUE_MARKET));
    }

    @Test
    public void testStepHelperGetTurnActionToLocation() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.RED_MARKET }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(0, 0), null, 0);
        assertEquals(TurnAction.MOVE_UP, StepHelper.getTurnActionToLocation(boardView, new Point(0, 1), new Random()));
    }

    @Test
    public void testMiningStepGetTurnActionMove() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RESOURCE_DIAMOND } },
                null, new Point(0, 0), null, 0);
        Economy economy = new Economy(new ItemType[] { ItemType.DIAMOND });
        MiningStep step = new MiningStep(10, new Random(), boardView, economy);
        assertEquals(TurnAction.MOVE_RIGHT, step.getTurnAction(boardView, null));
    }

    @Test
    public void testMiningStepGetTurnActionAtResource() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RESOURCE_DIAMOND } },
                null, new Point(1, 0), null, 0);
        Economy economy = new Economy(new ItemType[] { ItemType.DIAMOND });
        MiningStep step = new MiningStep(10, new Random(), boardView, economy);
        assertEquals(TurnAction.MINE, step.getTurnAction(boardView, null));
    }

    @Test
    public void testMiningStepShouldEndStepInventoryIsFull() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RESOURCE_DIAMOND } },
                null, new Point(0, 0), null, 0);
        Economy economy = new Economy(new ItemType[] { ItemType.DIAMOND });
        MiningStep step = new MiningStep(10, new Random(), boardView, economy);
        assertTrue(step.shouldEndStep(boardView, 0, 10));
    }

    @Test
    public void testMiningStepShouldEndStepInventoryIsNotFull() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RESOURCE_DIAMOND } },
                null, new Point(0, 0), null, 0);
        Economy economy = new Economy(new ItemType[] { ItemType.DIAMOND });
        MiningStep step = new MiningStep(10, new Random(), boardView, economy);
        assertFalse(step.shouldEndStep(boardView, 0, 9));
    }

    @Test
    public void testSellingStepGetTurnActionMove() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(0, 0), null, 0);
        SellingStep step = new SellingStep(true, new Random(), boardView);
        assertEquals(TurnAction.MOVE_RIGHT, step.getTurnAction(boardView, null));
    }

    @Test
    public void testSellingStepGetTurnActionAtMarket() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(1, 0), null, 0);
        SellingStep step = new SellingStep(true, new Random(), boardView);
        assertNull(step.getTurnAction(boardView, null));
    }

    @Test
    public void testSellingStepShouldEndStepNotAtMarket() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(0, 0), null, 0);
        SellingStep step = new SellingStep(true, new Random(), boardView);
        assertFalse(step.shouldEndStep(boardView, 0, 0));
    }

    @Test
    public void testSellingStepShouldEndStepAtMarket() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RED_MARKET } },
                null, new Point(1, 0), null, 0);
        SellingStep step = new SellingStep(true, new Random(), boardView);
        assertTrue(step.shouldEndStep(boardView, 0, 0));
    }

    @Test
    public void testChargingStepGetTurnActionMove() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RECHARGE } },
                null, new Point(0, 0), null, 0);
        ChargingStep step = new ChargingStep(10, new Random(), boardView);
        assertEquals(TurnAction.MOVE_RIGHT, step.getTurnAction(boardView, null));
    }

    @Test
    public void testChargingStepGetTurnActionCharge() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RECHARGE } },
                null, new Point(1, 0), null, 0);
        ChargingStep step = new ChargingStep(10, new Random(), boardView);
        assertNull(step.getTurnAction(boardView, null));
    }

    @Test
    public void testChargingStepShouldEndStepFullyCharged() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RECHARGE } },
                null, new Point(0, 0), null, 0);
        ChargingStep step = new ChargingStep(10, new Random(), boardView);
        assertTrue(step.shouldEndStep(boardView, 10, 0));
    }

    @Test
    public void testChargingStepShouldEndStepNotFullyCharged() {
        PlayerBoardView boardView = new PlayerBoardView(
                new TileType[][] { { TileType.EMPTY, TileType.EMPTY }, { TileType.EMPTY, TileType.RECHARGE } },
                null, new Point(0, 0), null, 0);
        ChargingStep step = new ChargingStep(10, new Random(), boardView);
        assertFalse(step.shouldEndStep(boardView, 9, 0));
    }
}
