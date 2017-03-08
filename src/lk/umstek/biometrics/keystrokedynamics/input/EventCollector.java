/*
 * EventCollector.java
 * Keeps key events recorded. 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.input;

import java.util.LinkedList;

/**
 * Collect information about key presses or releases.
 *
 * @author wickramaranga
 */
public class EventCollector {

    private final LinkedList<Keystroke> keystrokes;

    /**
     * Creates a new EventCollector.
     */
    public EventCollector() {
        keystrokes = new LinkedList<>();
    }

    /**
     * Notifies that a key has been pressed.
     *
     * @param symbol
     */
    public void press(String symbol) {
        keystrokes.push(new Keystroke(EventType.KeyDown, symbol));
    }

    /**
     * Notifies that a key has been released.
     *
     * @param symbol
     */
    public void release(String symbol) {
        keystrokes.push(new Keystroke(EventType.KeyUp, symbol));
    }
}
