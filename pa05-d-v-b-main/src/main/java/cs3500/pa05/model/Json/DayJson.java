package cs3500.pa05.model.Json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents each day recorded in the bullet journal.
 *
 * @param tasks the tasks for the day
 * @param events the events for the day
 */
public record DayJson(@JsonProperty("tasks") TaskJson[] tasks,
                      @JsonProperty("events") EventJson[] events) {
}
