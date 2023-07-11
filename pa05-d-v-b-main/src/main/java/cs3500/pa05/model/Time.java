package cs3500.pa05.model;

/**
 * Represents a timestamp
 */
public class Time implements Comparable<Time> {
  private static final int MAX_MILITARY_HOURS = 24;
  private static final int MAX_MINUTES = 59;
  private static final int MAX_REGULAR_HOURS = 12;
  private final int hour;
  private final int minute;
  private TimeMarkers marker = null;

  /**
   * Instantiates a new timestamp in military time.
   *
   * @param hour   the hour of the timestamp
   * @param minute the minute of the timestamp
   */
  public Time(int hour, int minute) {
    if (hour > MAX_MILITARY_HOURS || hour < 0) {
      throw new IllegalArgumentException("Hour given is out of bounds.");
    }
    if (minute > MAX_MINUTES || minute < 0) {
      throw new IllegalArgumentException("Minutes given is out of bounds.");
    }
    this.hour = hour;
    this.minute = minute;
  }

  /**
   * Instantiates a new timestamp in regular time.
   *
   * @param hour   the hour of the timestamp
   * @param minute the minute of the timestamp
   * @param marker the marker for the timestamp
   */
  public Time(int hour, int minute, TimeMarkers marker) {
    this(hour, minute);
    if (hour > MAX_REGULAR_HOURS || hour <= 0) {
      throw new IllegalArgumentException("Hour given is out of bounds.");
    }
    this.marker = marker;
  }

  @Override
  public String toString() {
    String minTensPlace = "";
    String hourTensPlace = "";
    if (minute < 10) {
      minTensPlace = "0";
    }
    if (hour < 10) {
      hourTensPlace = "0";
    }
    if (marker != null) {
      return String.format("%d:%s%d %s", hour, minTensPlace, minute,
          marker.toString().toLowerCase());
    }
    return String.format("%s%d:%s%d", hourTensPlace, hour, minTensPlace, minute);
  }

  public int getHour() {
    return hour;
  }

  public int getMinute() {
    return minute;
  }

  @Override
  public int compareTo(Time o) {
    if (hour > o.hour) {
      return 1;
    } else if (hour < o.hour) {
      return -1;
    } else if (minute > o.minute) {
      return 1;
    } else if (minute < o.minute) {
      return -1;
    }
    return 0;
  }
}
