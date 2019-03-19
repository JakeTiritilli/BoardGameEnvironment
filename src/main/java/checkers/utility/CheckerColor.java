package checkers.utility;

/**
 * Represents a Checkers player as either the "BLACK" player
 * or the "RED" player. This enum was declared to make
 * the code more strongly typed instead of "stringly"
 * typed :). - Jacob
 *
 * @author Tyler Vu
 */

public enum CheckerColor {
    BLACK {
        public String toString() {
            return "Black";
        }
    },
    RED {
        public String toString() {
            return "Red";
        }
    }
}
