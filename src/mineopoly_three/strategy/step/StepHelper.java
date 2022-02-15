package mineopoly_three.strategy.step;

import mineopoly_three.action.TurnAction;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * A utility class that includes helper methods for Strategy steps
 */
public class StepHelper {

    /**
     * Get the location of the nearest tile from the player with the specified tile type
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param tileType The specified tile type
     * @return The location of the nearest tile from the player with the specified tile type, or null if there are no
     *         tiles with the specified tile type.
     */
    public static Point getLocationOfNearestTileType(PlayerBoardView boardView, TileType tileType) {
        Set<Point> visited = new HashSet<>();
        Deque<Point> deque = new ArrayDeque<>();
        for (deque.offer(boardView.getYourLocation()); !deque.isEmpty();) {
            Point point = deque.poll();
            if (boardView.getTileTypeAtLocation(point.x, point.y) == tileType) {
                return point;
            } else if (boardView.getTileTypeAtLocation(point.x, point.y) != null && !visited.contains(point)) {
                visited.add(point);
                deque.offer(new Point(point.x, point.y + 1));
                deque.offer(new Point(point.x, point.y - 1));
                deque.offer(new Point(point.x + 1, point.y));
                deque.offer(new Point(point.x - 1, point.y));
            }
        }
        return null;
    }

    /**
     * Get a turn action that will move the player to the specified target location
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your strategy is allowed to access
     * @param targetLocation The location that the player should move to
     * @param random A random number generator
     * @return The TurnAction enum for the action that moves toward the specified target location, or null if the player
     *         is already at the specified location
     */
    public static TurnAction getTurnActionToLocation(PlayerBoardView boardView, Point targetLocation, Random random) {
        if (boardView.getYourLocation().equals(targetLocation)) {
            return null;
        }
        List<TurnAction> turnActions = new ArrayList<>();
        if (boardView.getYourLocation().y < targetLocation.y) {
            turnActions.add(TurnAction.MOVE_UP);
        }
        if (boardView.getYourLocation().y > targetLocation.y) {
            turnActions.add(TurnAction.MOVE_DOWN);
        }
        if (boardView.getYourLocation().x < targetLocation.x) {
            turnActions.add(TurnAction.MOVE_RIGHT);
        }
        if (boardView.getYourLocation().x > targetLocation.x) {
            turnActions.add(TurnAction.MOVE_LEFT);
        }
        return turnActions.get(random.nextInt(turnActions.size()));
    }
}
