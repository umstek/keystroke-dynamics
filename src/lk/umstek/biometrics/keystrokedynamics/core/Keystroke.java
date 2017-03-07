/*
 * Keystroke.java
 * Holds data describing a single key event, either a press or a release. 
 * Immutable. 
 */
package lk.umstek.biometrics.keystrokedynamics.core;

import java.util.Date;

/**
 * Store type of the event (press/release), which key key emitted the event, and
 * the time at which the event happened.
 *
 * @author wickramaranga
 */
public class Keystroke {

    private final EventType type;
    private final Date date;
    private final String symbol;

    /**
     * Creates a new key event representation.
     *
     * @param type Type of the event - pressed/released.
     * @param symbol The symbol to recognize the key which emitted the event.
     */
    public Keystroke(EventType type, String symbol) {
        this.type = type;
        this.date = new Date();
        this.symbol = symbol;
    }

    /**
     * A press or a release?
     *
     * @return the type
     */
    public EventType getType() {
        return type;
    }

    /**
     * Event timestamp.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Subject which emitted the event.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

}
