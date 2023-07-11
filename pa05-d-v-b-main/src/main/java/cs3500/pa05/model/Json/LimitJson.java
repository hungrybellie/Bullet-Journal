package cs3500.pa05.model.Json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the limits that the user can set for the maximum number of items per day.
 *
 * @param maxEvents the maximum number of events allowed per day
 * @param maxTasks the maximum number of tasks allowed per day
 */
public record LimitJson(@JsonProperty("max_num_events") int maxEvents,
                        @JsonProperty("max_num_tasks") int maxTasks) {
}
