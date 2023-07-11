package cs3500.pa05.model;

import cs3500.pa05.model.Json.BujoJson;
import cs3500.pa05.model.Json.EventJson;
import cs3500.pa05.model.Json.TaskJson;
import java.util.List;

/**
 * Represents the functionality needed for a Bujo file reader.
 */
public interface BujoReader {
  /**
   * Gets the bullet journal stored in the file.
   *
   * @return the week
   */
  BujoJson getBujo();

  /**
   * Gets the events stored in the file.
   *
   * @return the events
   */
  List<EventJson> getEvents();

  /**
   * Gets the tasks stored in the file.
   *
   * @return the tasks
   */
  List<TaskJson> getTasks();
}
