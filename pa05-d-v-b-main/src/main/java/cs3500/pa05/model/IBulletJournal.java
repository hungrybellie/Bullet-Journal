package cs3500.pa05.model;

import cs3500.pa05.model.Json.EventJson;
import cs3500.pa05.model.Json.TaskJson;
import cs3500.pa05.model.Json.BujoJson;
import java.util.List;

/**
 * Represents the functionality needed for a bullet journal.
 */
public interface IBulletJournal {
  /**
   * Adds an event to the bullet journal.
   *
   * @param event the event to be added
   */
  void addEvent(EventJson event);

  /**
   * Adds a task to the bullet journal.
   *
   * @param task the task to be added
   */
  void addTask(TaskJson task);

  /**
   * Removes an event from the bullet journal.
   *
   * @param event the event to be removed
   */
  void removeEvent(EventJson event);

  /**
   * Removes a task from the bullet journal.
   *
   * @param task the task to be removed
   */
  void removeTask(TaskJson task);

  /**
   * Sorts all tasks by their priority deadline.
   *
   * @return a list of all tasks sorted from the closest upcoming date to the furthest date
   */
  List<TaskJson> sortTasksPriority();

  /**
   * Sorts all tasks by their name or duration.
   *
   * @param byName if wanting to sort by name, otherwise will be sorted by duration from shortest
   *               to longest duration
   * @return a list of TaskJsons sorted by the given flag
   */
  List<TaskJson> sortTasksNameCompletion(boolean byName);

  /**
   * Sorts all events by their name or duration.
   *
   * @param byName if wanting to sort by name, otherwise will be sorted by duration from shortest
   *               to longest duration
   * @return a list of EventJsons sorted by the given flag
   */
  List<EventJson> sortEventsNameDuration(boolean byName);

  /**
   * Returns whether the task or event limit will be exceeded after adding another task or event.
   *
   * @param isTask if the task limit is being checked, otherwise the event limit will be checked
   * @return if the limit will be violated
   */
  boolean checkLimitViolation(boolean isTask);

  /**
   * Sets the limit to the number of tasks per day.
   *
   * @param limit the limit
   */
  void setTaskLimit(int limit);

  /**
   * Sets the limit to the number of events per day.
   *
   * @param limit the limit
   */
  void setEventLimit(int limit);

  /**
   * Sets the name of the week.
   *
   * @param name the name for the week
   */
  void setWeekName(String name);

  /**
   * Changes the theme of the bullet journal.
   *
   * @param theme the specified theme
   */
  void chooseTheme(ThemeType theme);

  /**
   * Saves the bullet journal to a .bujo file.
   */
  void saveBulletJournal();

  /**
   * Saves the note to the bullet journal.
   *
   * @param note the note
   */
  void saveNote(String note);

  /**
   * Retrieves the week.
   *
   * @return the week
   */
  BujoJson getWeek();
}
