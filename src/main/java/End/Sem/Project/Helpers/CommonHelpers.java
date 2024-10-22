package End.Sem.Project.Helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import java.util.function.Consumer;

public class CommonHelpers {

    protected boolean isNullString(String s) {
        return s == null;
    }

    protected void updateIfNotNullString(String newValue, Consumer<String> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    protected void updateIfNotNullTime(LocalTime newValue, Consumer<LocalTime> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    protected void updateIfNotNullDate(LocalDate newValue, Consumer<LocalDate> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    protected void updateIfNotNullBool(Boolean newValue, Consumer<Boolean> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    protected void updateIfNotNullUUID(UUID uuid, Consumer<UUID> setter) {
        if (uuid != null) {
            setter.accept(uuid);
        }
    }

    protected void updateIfNotNullInt(int eventAvailability, Consumer<Integer> setter) {
        if (eventAvailability > 0) {
            setter.accept(eventAvailability);
        } else {
            System.out.println("Event availability is zero");
        }
    }

}
