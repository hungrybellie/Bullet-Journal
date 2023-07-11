package cs3500.pa05.model.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.ThemeType;

/**
 * Represents a Bujo as a Json.
 *
 * @param week   an array of the days of the week
 * @param weekName name of the week
 * @param limits the limits for events and tasks per day
 * @param theme  the theme for the bullet journal
 * @param note the note on the bullet journal
 */
public record BujoJson(
    @JsonProperty("week") DayJson[] week,
    @JsonProperty("name") String weekName,
    @JsonProperty("limits") LimitJson limits,
    @JsonProperty("theme") ThemeType theme,
    @JsonProperty("note") String note
) {

}
