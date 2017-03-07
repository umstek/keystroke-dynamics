/*
 * Keystroke.java
 * Holds data describing a single key event, either a press or a release. 
 * Immutable. 
 */
package lk.umstek.biometrics.keystrokedynamics.core;

import java.util.Date;

/**
 *
 * @author wickramaranga
 */
public class Keystroke {

    private final EventType type;
    private final Date date;
    private final char symbol;

    public Keystroke(EventType type, char symbol) {
        this.type = type;
        this.date = new Date();
        this.symbol = symbol;
    }

    /**
     * @return the type
     */
    public EventType getType() {
        return type;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

}
