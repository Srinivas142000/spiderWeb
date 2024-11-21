package End.Sem.Project.Helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * A Helper class with methods for various operations,
 * including null checks and updating values.
 */
public class CommonHelpers {

    /**
     * Checks if a given string is null.
     *
     * @param s the string to check
     * @return true if the string is null, false otherwise
     */
    protected boolean isNullString(String s) {
        return s == null;
    }

    /**
     * Updates a value using a function if the new value is not null.
     *
     * @param newValue the new value to set
     * @param setter   the setter function to apply the new value
     */
    protected void updateIfNotNullString(String newValue, Consumer<String> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    /**
     * Updates a time value using a function if the new value is not null.
     *
     * @param newValue the new LocalTime value to set
     * @param setter   the setter function to apply the new value
     */
    protected void updateIfNotNullTime(LocalTime newValue, Consumer<LocalTime> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    /**
     * Updates a date value using a function if the new value is not null.
     *
     * @param newValue the new LocalDate value to set
     * @param setter   the setter function to apply the new value
     */
    protected void updateIfNotNullDate(LocalDate newValue, Consumer<LocalDate> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    /**
     * Updates a boolean value using a function if the new value is not null.
     *
     * @param newValue the new Boolean value to set
     * @param setter   the setter function to apply the new value
     */
    protected void updateIfNotNullBool(Boolean newValue, Consumer<Boolean> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    /**
     * Updates a UUID value using a function if the new value is not null.
     *
     * @param uuid   the new UUID value to set
     * @param setter the setter function to apply the new value
     */
    protected void updateIfNotNullUUID(UUID uuid, Consumer<UUID> setter) {
        if (uuid != null) {
            setter.accept(uuid);
        }
    }

    /**
     * Increases ticket count for each event if zero no registrations allowed.
     *
     * @param eventAvailability ticket count
     * @param setter           int function to modify the value
     */
    protected void updateIfNotNullInt(int eventAvailability, Consumer<Integer> setter) {
        if (eventAvailability > 0) {
            setter.accept(eventAvailability);
        } else {
            System.out.println("Event availability is zero");
        }
    }
}
