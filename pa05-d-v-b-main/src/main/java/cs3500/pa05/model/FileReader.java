package cs3500.pa05.model;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Json.BujoJson;
import cs3500.pa05.model.Json.DayJson;
import cs3500.pa05.model.Json.EventJson;
import cs3500.pa05.model.Json.TaskJson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents a file reader.
 */
public class FileReader implements BujoReader {
  private final ObjectMapper mapper = new ObjectMapper();
  private BujoJson week;

  /**
   * Reads the file and parses the week from the file.
   *
   * @param file file to be read.
   * @return true if the file can be read, false otherwise
   */
  public boolean readFile(Path file) {
    if (Files.exists(file)) {
      try {
        JsonParser parser = this.mapper.getFactory().createParser(file.toFile());
        week = parser.readValueAs(BujoJson.class);
        return true;
      } catch (IOException e) {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public BujoJson getBujo() {
    return week;
  }

  @Override
  public ArrayList<EventJson> getEvents() {
    ArrayList<EventJson> events = new ArrayList<>();
    for (DayJson day : week.week()) {
      for (EventJson event : day.events()) {
        if (event != null) {
          events.add(event);
        }
      }
    }
    return events;
  }

  @Override
  public ArrayList<TaskJson> getTasks() {
    ArrayList<TaskJson> tasks = new ArrayList<>();
    for (DayJson day : week.week()) {
      for (TaskJson task : day.tasks()) {
        if (task != null) {
          tasks.add(task);
        }
      }
    }
    return tasks;
  }
}
