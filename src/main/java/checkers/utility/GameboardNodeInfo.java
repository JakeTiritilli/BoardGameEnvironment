package checkers.utility;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * This class is used for Nodes on the gameboard (Cells, CheckerPieces). This object will
 * be attached to the Node using Node.setUserData(GameboardNodeInfo). The position and the
 * event handlers will be saved here in order to derive information for later use.
 */

public class GameboardNodeInfo {
    public EventHandler<MouseEvent> clickEventHandler;
    public PosTuple boardPosition;

    public GameboardNodeInfo(PosTuple boardPosition) {
        this.boardPosition = boardPosition;
    }

    public GameboardNodeInfo(PosTuple boardPosition, EventHandler<MouseEvent> clickEventHandler) {
        this.boardPosition = boardPosition;
        this.clickEventHandler = clickEventHandler;
    }

    /**
     * Clears the stored click event handler.
     * @return The reference to the event handler so that it can be used
     * to clear the event handler in the controller.
     */
    public EventHandler<MouseEvent> clearClickEventHanlder() {
        EventHandler<MouseEvent> temp = this.clickEventHandler;
        this.clickEventHandler = null;
        return temp;
    }
}
