package cs3500.pa05.controller;

import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.FileReader;
import cs3500.pa05.model.FileWriter;
import cs3500.pa05.model.Json.BujoJson;
import cs3500.pa05.model.Json.EventJson;
import cs3500.pa05.model.Json.TaskJson;
import cs3500.pa05.model.ThemeType;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents the controller for the user.
 */
public class UserController {

  private BulletJournal bujo;

  /**
   * Handles when the user enters the specified file they want to have the bullet journal on.
   *
   * @param file the file that the user enters.
   * @return the loaded bujo information.
   */
  protected BujoJson handlePath(String file) {
    File f = Path.of(file).toFile();
    this.bujo = new BulletJournal(f.toPath(), new FileReader(), new FileWriter(f));
    return bujo.getWeek();
  }

  /**
   * Handles when the user enters a new event.
   *
   * @param event the event that the user entered.
   * @return true if the user has exceeded their limits, false otherwise
   */
  protected boolean handleEvent(EventJson event) {
    bujo.addEvent(event);
    return bujo.checkLimitViolation(false);
  }

  /**
   * Handles when the user enters a new task.
   *
   * @param task the new task that the user has entered
   * @return true if the user has exceeded their limits, false otherwise
   */
  protected boolean handleTask(TaskJson task) {
    bujo.addTask(task);
    return bujo.checkLimitViolation(true);
  }

  /**
   * Handles when the user enters a new limit.
   */
  protected void handleLimit(String taskLimit, String eventLimit) {
    try {
      bujo.setTaskLimit(Integer.parseInt(taskLimit));
    } catch (NumberFormatException e) {
      System.err.println("Invalid limit input.");
    }

    try {
      bujo.setEventLimit(Integer.parseInt(eventLimit));
    } catch (NumberFormatException e) {
      System.err.println("Invalid limit input.");
    }
  }

  /**
   * Handles when the user wants to remove a task.
   *
   * @param task the task to be removed
   */
  protected void handleRemoveTask(TaskJson task) {
    bujo.removeTask(task);
  }

  /**
   * Handles when the user wants to remove an event.
   *
   * @param event the event to be removed
   */
  protected void handleRemoveEvent(EventJson event) {
    bujo.removeEvent(event);
  }

  /**
   * Handles when the user wants to change the theme.
   *
   * @param theme the new theme for the bullet journal
   */
  protected void handleTheme(ThemeType theme) {
    bujo.chooseTheme(theme);
  }

  /**
   * Handles when the user wants to save the bullet journal to a file.
   */
  protected void handleSave() {
    bujo.saveBulletJournal();
  }

  /**
   * Handles when the sidebar of tasks needs to be created.
   *
   * @return a sorted list of tasks sorted by urgency
   */
  protected List<TaskJson> getTaskQueue() {
    return bujo.sortTasksPriority();
  }

  /**
   * Replaces an event with another event.
   *
   * @param oldEvent    the event to be replaced
   * @param editedEvent the new event
   */
  protected void editEvent(EventJson oldEvent, EventJson editedEvent) {
    bujo.removeEvent(oldEvent);
    bujo.addEvent(editedEvent);
  }

  /**
   * Replaces a task with another task.
   *
   * @param oldTask    the task to be replaced
   * @param editedTask the new task
   */
  protected void editTask(TaskJson oldTask, TaskJson editedTask) {
    bujo.removeTask(oldTask);
    bujo.addTask(editedTask);
  }

  /**
   * Checks if the task/event limit will be violated.
   *
   * @param isTask true if checking task limit, else checking event limit
   * @return true if the limit is violated
   */
  protected boolean checkLimit(boolean isTask) {
    return bujo.checkLimitViolation(isTask);
  }

  /**
   * Sorts tasks either by name or completion.
   *
   * @param name true if sorting by name, else sorting by completion
   * @return the sorted list of tasks
   */
  protected List<TaskJson> sortTasks(boolean name) {
    return bujo.sortTasksNameCompletion(name);
  }

  /**
   * Sorts events by either name or duration.
   *
   * @param name true if sorting by name, else sorting by duration
   * @return the sorted list of events
   */
  protected List<EventJson> sortEvents(boolean name) {
    return bujo.sortEventsNameDuration(name);
  }

  /**
   * Handles saving a note to the bullet journal.
   *
   * @param n the note to be saved
   */
  protected void handleNote(String n) {
    bujo.saveNote(n);
  }
}
