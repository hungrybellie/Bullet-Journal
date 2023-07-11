package cs3500.pa05.model.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.TimeMarkers;
import java.util.Arrays;

/**
 * Represents an event created for a certain day.
 *
 * @param name        name of the event
 * @param description description of what the event is
 * @param day         the day of the week the event is on
 * @param time        the start time for the event
 * @param duration    how long the event will last
 */
public record EventJson(@JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("day") Day day,
                        @JsonProperty("start_time") String time,
                        @JsonProperty("duration") String duration) {
  // Temporary Note: duration format will be "-h-m", where the - are digits
  //TODO: description is optional, everything else is mandatory

  /**
   * Translates the time strings into time.
   *
   * @param isStartTime chooses whether to translate the start time or the duration property.
   * @return a time
   */
  public Time translateStartTime(boolean isStartTime) {
    String toTranslate;
    if (isStartTime) {
      toTranslate = time;
    } else {
      toTranslate = duration;
    }
    String[] arrayTime = toTranslate.split(":");
    System.out.println(Arrays.toString(arrayTime));
    String[] minuteAndMarker = arrayTime[1].split(" ");

    if (minuteAndMarker.length == 2) {
      TimeMarkers marker;
      if (minuteAndMarker[1].equals("am")) {
        marker = TimeMarkers.AM;
      } else {
        marker = TimeMarkers.PM;
      }
      return new Time(Integer.parseInt(arrayTime[0]), Integer.parseInt(minuteAndMarker[0]), marker);
    }
    return new Time(Integer.parseInt(arrayTime[0]), Integer.parseInt(minuteAndMarker[0]));
  }

  /**
   * Checks if two EventJsons are equal.
   *
   * @param other   the reference object with which to compare
   * @return true if the EventJsons are equal, false if not
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof EventJson)) {
      return false;
    } else {
      return this.name().equals(((EventJson) other).name())
          && this.description().equals(((EventJson) other).description())
          && this.day().equals(((EventJson) other).day())
          && this.time().equals(((EventJson) other).time())
          && this.duration().equals(((EventJson) other).duration());
    }
  }
}
