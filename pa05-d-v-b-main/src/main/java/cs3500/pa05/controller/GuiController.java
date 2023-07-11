package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Json.BujoJson;
import cs3500.pa05.model.Json.DayJson;
import cs3500.pa05.model.Json.EventJson;
import cs3500.pa05.model.Json.TaskJson;
import cs3500.pa05.model.Theme;
import cs3500.pa05.model.ThemeType;
import cs3500.pa05.model.Time;
import cs3500.pa05.view.PopupView;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Represents the controller for GUI elements.
 */
public class GuiController {
  @FXML
  private Button addTask;
  @FXML
  private Button addEvent;
  @FXML
  private Button setLimit;
  @FXML
  private Button save;
  @FXML
  private Button changeTheme;
  @FXML
  private Button sortByNameTask;
  @FXML
  private Button sortByDurationTask;
  @FXML
  private Button sortByNameEvent;
  @FXML
  private Button sortByDurationEvent;
  @FXML
  private VBox taskQueueBox;
  @FXML
  private VBox sunPane;
  @FXML
  private VBox monPane;
  @FXML
  private VBox tuePane;
  @FXML
  private VBox wedPane;
  @FXML
  private VBox thuPane;
  @FXML
  private VBox friPane;
  @FXML
  private VBox satPane;
  @FXML
  private Rectangle headerRect;
  @FXML
  private Rectangle changeThemeRect;
  @FXML
  private Label sunLabel;
  @FXML
  private Label monLabel;
  @FXML
  private Label tueLabel;
  @FXML
  private Label wedLabel;
  @FXML
  private Label thuLabel;
  @FXML
  private Label friLabel;
  @FXML
  private Label satLabel;
  @FXML
  private Rectangle addTaskRect;
  @FXML
  private Rectangle addEventRect;
  @FXML
  private Rectangle setLimitRect;
  @FXML
  private Rectangle saveRect;
  @FXML
  private Rectangle editRect;
  @FXML
  private Label quotesLabel;
  @FXML
  private Label sortTasksLabel;
  @FXML
  private Label sortEventsLabel;
  @FXML
  private Rectangle tasksNameRect;
  @FXML
  private Rectangle tasksDurRect;
  @FXML
  private Rectangle eventNameRect;
  @FXML
  private Rectangle eventDurRect;
  @FXML
  private Label taskQueueLabel;
  @FXML
  private Label headerLabel;
  @FXML
  private TextField weekNameText;
  @FXML
  private TextArea quotesArea;
  @FXML
  private Button edit;
  private Button cancel;
  private final Stage stage;
  private final Popup taskPopup;
  private final Popup eventPopup;
  private final Popup limitPopup;
  private final Popup changeThemePopup;
  private final Popup fileTitlePopup;
  private final Popup warnPopup;
  private final Label dayLabel = new Label("day: ");
  private final HashMap<EventJson, VBox> events;
  private final HashMap<TaskJson, VBox> tasks;
  private RadioButton mon;
  private RadioButton tue;
  private RadioButton wed;
  private RadioButton thu;
  private RadioButton fri;
  private RadioButton sat;
  private RadioButton sun;
  private final PopupView popupView;
  private final Theme theme;
  private final UserController userController;

  /**
   * Constructs a GUIController.
   *
   * @param stage the stage of the GUI
   */
  public GuiController(Stage stage) {
    this.stage = stage;
    events = new HashMap<>();
    tasks = new HashMap<>();
    this.taskPopup = new Popup();
    this.eventPopup = new Popup();
    this.limitPopup = new Popup();
    this.changeThemePopup = new Popup();
    this.fileTitlePopup = new Popup();
    this.warnPopup = new Popup();
    popupView = new PopupView();
    monPane = new VBox(3);
    tuePane = new VBox(3);
    wedPane = new VBox(3);
    thuPane = new VBox(3);
    friPane = new VBox(3);
    satPane = new VBox(3);
    sunPane = new VBox(3);
    theme = new Theme();
    userController = new UserController();
  }

  /**
   * Loads an existing bujo file's data onto the GUI.
   *
   * @param bujo a bujo path
   */
  public void makeFileGui(BujoJson bujo) {
    for (DayJson dayJson : bujo.week()) {
      if (dayJson == null) {
        continue;
      }
      for (TaskJson task : dayJson.tasks()) {
        if (task != null) {
          CheckBox complete = new CheckBox();

          if (task.complete()) {
            complete.fire();
          }
          VBox taskGui = createTaskBox(task.name(), task.description(), complete);
          tasks.put(task, taskGui);
          addToGridPane(taskGui, task.day());
          CheckBox invisCheck = new CheckBox();
          invisCheck.setVisible(false);
          Label completionLabel = new Label();
          if (complete.isSelected()) {
            completionLabel.setText("Completed!");
          } else {
            completionLabel.setText("Incomplete!");
          }
          VBox taskQueueGui = createTaskBox(task.name(), task.description(), invisCheck);
          taskQueueGui.getChildren().add(completionLabel);
          taskQueueBox.getChildren().add(taskQueueGui);
          complete.setOnAction(event -> {
            if (complete.isSelected()) {
              tasks.put(new TaskJson(task.name(), task.description(), task.day(), true), taskGui);
              tasks.remove(task);
              taskQueueGui.getChildren().remove(completionLabel);
              taskQueueGui.getChildren().add(new Label("Completed!"));
            } else {
              tasks.put(new TaskJson(task.name(), task.description(), task.day(), false), taskGui);
              tasks.remove(task);
              taskQueueGui.getChildren().remove(completionLabel);
              taskQueueGui.getChildren().add(new Label("Incomplete!"));
            }
          });
        }
      }
      for (EventJson event : dayJson.events()) {
        if (event != null) {
          VBox eventGui =
              createEventBox(event.name(), event.description(), event.translateStartTime(true),
                  event.translateStartTime(false));
          events.put(event, eventGui);
          addToGridPane(eventGui, event.day());
        }
      }
    }
    theme.setThemeType(bujo.theme());
    changeTheme(theme.getColorOne(), theme.getColorTwo(), theme.getFont(), theme.getFace());
    quotesArea.setText(bujo.note());
  }

  /**
   * Creates a popup for getting the user's specified file path, or lets them name a new one.
   */
  public void makeFileNamePopup() {
    VBox content = new VBox(8);
    content.setAlignment(Pos.CENTER);
    content.setPrefWidth(1290);
    content.setPrefHeight(907);
    HBox inputRow = new HBox(6);
    inputRow.setAlignment(Pos.CENTER);
    Text welcome = new Text("Welcome! (^-^)" + System.lineSeparator()
        + "Please enter an existing bujo file path, or the name of a new one!");
    welcome.setTextAlignment(TextAlignment.CENTER);
    welcome.setStyle("-fx-font-family: 'BM Jua'");
    welcome.setFill(Color.valueOf("#555e3a"));
    TextField fileName = new TextField();
    Button goButton = new Button("go!");
    goButton.setOnAction(event -> {
      if (fileName.getText().endsWith(".bujo")) {
        makeFileGui(userController.handlePath(fileName.getText()));
        fileTitlePopup.hide();
      } else {
        fileName.clear();
        welcome.setText("Your file must end in \".bujo\" to be valid! (-o- )");
      }
    });
    content.getChildren().add(welcome);
    inputRow.getChildren().add(fileName);
    inputRow.getChildren().add(goButton);
    content.getChildren().add(inputRow);
    Rectangle background = popupView.createPopupBackground(907, 1290);
    fileTitlePopup.getContent().add(background);
    fileTitlePopup.getContent().add(content);
  }

  /**
   * Creates a popup for the user to enter details of a task.
   */
  private void makeTaskPopUp() {
    VBox content = new VBox(8);
    Rectangle padding = new Rectangle(180, 10);
    padding.setFill(Color.valueOf("#ffffff"));
    content.getChildren().add(padding);
    Label nameLabel = new Label("name: ");
    TextField taskName = new TextField();
    Label descripLabel = new Label("description");
    TextField taskDescription = new TextField();
    HBox dayRow = createWeekRadios();
    content.getChildren().addAll(
        nameLabel, taskName, descripLabel, taskDescription, dayLabel, dayRow);
    Button finalizeTask = new Button("add task");
    finalizeTask.setOnAction(event -> {
      CheckBox complete = new CheckBox();
      VBox taskBox =
          createTaskBox(taskName.getText(), taskDescription.getText(), complete);
      addToGridPane(taskBox, translateToDay());
      tasks.put(new TaskJson(taskName.getText(), taskDescription.getText(), translateToDay(),
          complete.isSelected()), taskBox);
      CheckBox invisCheck = new CheckBox();
      invisCheck.setVisible(false);
      Label completionLabel = new Label();
      if (complete.isSelected()) {
        completionLabel.setText("Completed!");
      } else {
        completionLabel.setText("Incomplete!");
      }
      VBox taskQueueGui = createTaskBox(taskName.getText(), taskDescription.getText(), invisCheck);
      taskQueueGui.getChildren().add(completionLabel);
      taskQueueBox.getChildren().add(taskQueueGui);
      complete.setOnAction(actionEvent -> {
        if (complete.isSelected()) {
          taskBox.getChildren().add(new Label("Completed!"));
          tasks.put(new TaskJson(
              taskName.getText(), taskDescription.getText(), translateToDay(), true), taskBox);
          tasks.remove(new TaskJson(
              taskName.getText(), taskDescription.getText(), translateToDay(), false));
          completionLabel.setText("Completed!");
          userController.handleRemoveTask(new TaskJson(
              taskName.getText(), taskDescription.getText(), translateToDay(), false));
          userController.handleTask(new TaskJson(
              taskName.getText(), taskDescription.getText(), translateToDay(), true));
        }
      });
      userController.handleTask(
          new TaskJson(taskName.getText(), taskDescription.getText(), translateToDay(),
              complete.isSelected()));
    });
    cancel = new Button("cancel");
    cancel.setOnAction(event -> taskPopup.hide());
    HBox buttonRow = new HBox(5);
    buttonRow.getChildren().add(finalizeTask);
    buttonRow.getChildren().add(cancel);
    content.getChildren().add(buttonRow);
    Rectangle background = popupView.createPopupBackground(240, 360);
    taskPopup.getContent().add(background);
    taskPopup.getContent().add(content);
  }

  /**
   * Returns the Day corresponding to which button is being pressed.
   *
   * @return the Day that is selected
   */
  private Day translateToDay() {
    if (mon.isSelected()) {
      return Day.MONDAY;
    } else if (tue.isSelected()) {
      return Day.TUESDAY;
    } else if (wed.isSelected()) {
      return Day.WEDNESDAY;
    } else if (thu.isSelected()) {
      return Day.THURSDAY;
    } else if (fri.isSelected()) {
      return Day.FRIDAY;
    } else if (sat.isSelected()) {
      return Day.SATURDAY;
    } else {
      return Day.SUNDAY;
    }
  }

  /**
   * Creates a formatted representation of a task to display in the week view.
   *
   * @param name        the name of the task
   * @param description the description of the task
   * @return taskBox a box-like representaion of a task
   */
  private VBox createTaskBox(String name, String description, CheckBox complete) {
    VBox taskBox = new VBox();
    Button delete = new Button("X");
    delete.setStyle("-fx-background-color: transparent");
    Text taskName = new Text(name);
    Text taskDescription = new Text(description);
    taskBox.getChildren()
        .addAll(new Text("Task:"), taskName, taskDescription,
            complete, delete);
    TaskJson createdTask = new TaskJson(taskName.getText(), taskDescription.getText(),
        translateToDay(),
        complete.isSelected());
    delete.setOnAction(event -> {
      taskBox.getChildren().clear();
      userController.handleRemoveTask(createdTask);
    });
    return taskBox;
  }

  /**
   * Creates an HBox row of radio buttons that represent the days of the week.
   *
   * @return dayRow a row of radio buttons for each day of the week.
   */
  public HBox createWeekRadios() {
    ToggleGroup chosenDay = new ToggleGroup();
    sun = new RadioButton("Sun");
    sun.setToggleGroup(chosenDay);
    sun.setSelected(true);
    mon = new RadioButton("Mon");
    mon.setToggleGroup(chosenDay);
    tue = new RadioButton("Tue");
    tue.setToggleGroup(chosenDay);
    wed = new RadioButton("Wed");
    wed.setToggleGroup(chosenDay);
    thu = new RadioButton("Thu");
    thu.setToggleGroup(chosenDay);
    fri = new RadioButton("Fri");
    fri.setToggleGroup(chosenDay);
    sat = new RadioButton("Sat");
    sat.setToggleGroup(chosenDay);
    HBox dayRow = new HBox(5);
    dayRow.getChildren().addAll(sun, mon, tue, wed, thu, fri, sat);
    return dayRow;
  }

  /**
   * Creates a popup for a user to enter the details of an event.
   */
  private void makeEventPopUp() {
    Rectangle padding = new Rectangle(180, 10);
    padding.setFill(Color.valueOf("#ffffff"));
    TextField hourDigit = new TextField();
    Label colon = new Label(":");
    colon.setPrefWidth(10);
    hourDigit.setPrefWidth(30);
    TextField minDigit = new TextField();
    minDigit.setPrefWidth(30);
    ToggleGroup amOrPm = new ToggleGroup();
    RadioButton am = new RadioButton("AM");
    RadioButton pm = new RadioButton("PM");
    am.setToggleGroup(amOrPm);
    am.setSelected(true);
    pm.setToggleGroup(amOrPm);
    HBox startTimeRow = new HBox(5);
    startTimeRow.getChildren().addAll(hourDigit, colon, minDigit, am, pm);
    HBox timeRow = new HBox(5);
    TextField hoursDigit = new TextField();
    hoursDigit.setPrefWidth(30);
    Label hoursLabel = new Label("H");
    TextField minutesDigit = new TextField();
    minutesDigit.setPrefWidth(30);
    Label minutesLabel = new Label("M");
    timeRow.getChildren().addAll(hoursDigit, hoursLabel, minutesDigit, minutesLabel);
    VBox content = new VBox(8);
    TextField eventName = new TextField();
    TextField eventDescription = new TextField();
    HBox dayRow = createWeekRadios();
    Label nameLabel = new Label("name: ");
    Label descripLabel = new Label("description: ");
    Label startTime = new Label("start time...");
    Label eventDuration = new Label("duration: ");
    content.getChildren()
        .addAll(padding, nameLabel, eventName, descripLabel, eventDescription, dayLabel, dayRow,
            startTime, startTimeRow,
            eventDuration, timeRow);
    Button finalizeEvent = new Button("add event");
    finalizeEvent.setOnAction(event -> {
      Time time;
      try {
        time =
            new Time(Integer.parseInt(hourDigit.getText()), Integer.parseInt(minDigit.getText()));
      } catch (NumberFormatException e) {
        time = null;
      }
      Time duration;
      try {
        duration = new Time(Integer.parseInt(hoursDigit.getText()),
            Integer.parseInt(minutesDigit.getText()));
      } catch (NumberFormatException e) {
        duration = null;
      }

      VBox eventBox =
          createEventBox(eventName.getText(), eventDescription.getText(), time, duration);
      addToGridPane(eventBox, translateToDay());
    });
    cancel = new Button("cancel");
    cancel.setOnAction(event -> eventPopup.hide());
    HBox buttonRow = new HBox(5);
    buttonRow.getChildren().add(finalizeEvent);
    buttonRow.getChildren().add(cancel);
    content.getChildren().add(buttonRow);
    Rectangle background = popupView.createPopupBackground(320, 360);
    eventPopup.getContent().add(background);
    eventPopup.getContent().add(content);
  }

  private void makeEventEditPopup(EventJson eventEdit) {
    Rectangle padding = new Rectangle(180, 10);
    padding.setFill(Color.valueOf("#ffffff"));
    HBox startTimeRow = new HBox(5);
    TextField hourDigit = new TextField(
        String.valueOf(eventEdit.translateStartTime(true).getHour()));
    Label colon = new Label(":");
    colon.setPrefWidth(10);
    hourDigit.setPrefWidth(30);
    TextField minDigit = new TextField(
        String.valueOf(eventEdit.translateStartTime(true).getMinute()));
    minDigit.setPrefWidth(30);
    ToggleGroup amOrPm = new ToggleGroup();
    RadioButton am = new RadioButton("AM");
    RadioButton pm = new RadioButton("PM");
    am.setToggleGroup(amOrPm);
    am.setSelected(true);
    pm.setToggleGroup(amOrPm);
    startTimeRow.getChildren().addAll(hourDigit, colon, minDigit, am, pm);
    HBox timeRow = new HBox(5);
    TextField hoursDigit = new TextField(
        String.valueOf(eventEdit.translateStartTime(false).getHour()));
    hoursDigit.setPrefWidth(30);
    Label hoursLabel = new Label("H");
    TextField minutesDigit = new TextField(
        String.valueOf(eventEdit.translateStartTime(false).getMinute()));
    minutesDigit.setPrefWidth(30);
    Label minutesLabel = new Label("M");
    timeRow.getChildren().addAll(hoursDigit, hoursLabel, minutesDigit, minutesLabel);
    VBox content = new VBox(8);
    TextField eventName = new TextField(eventEdit.name());
    TextField eventDescription = new TextField(eventEdit.description());
    HBox dayRow = createWeekRadios();
    Label nameLabel = new Label("name: ");
    Label descripLabel = new Label("description: ");
    Label startTime = new Label("start time...");
    Label eventDuration = new Label("duration: ");
    content.getChildren()
        .addAll(padding, nameLabel, eventName, descripLabel, eventDescription, dayLabel, dayRow,
            startTime, startTimeRow,
            eventDuration, timeRow);
    Button finalizeEvent = new Button("add event");
    finalizeEvent.setOnAction(event -> {
      Time time;
      try {
        time =
            new Time(Integer.parseInt(hourDigit.getText()), Integer.parseInt(minDigit.getText()));
      } catch (NumberFormatException e) {
        time = null;
      }
      Time duration;
      try {
        duration = new Time(Integer.parseInt(hoursDigit.getText()),
            Integer.parseInt(minutesDigit.getText()));
      } catch (NumberFormatException e) {
        duration = null;
      }

      VBox eventBox =
          createEventBox(eventName.getText(), eventDescription.getText(), time, duration);
      if (translateToDay() != null) {
        //TODO: get valid input from the user
        addToGridPane(eventBox, translateToDay());
      }
    });
    cancel = new Button("cancel");
    cancel.setOnAction(event -> eventPopup.hide());
    HBox buttonRow = new HBox(5);
    buttonRow.getChildren().add(finalizeEvent);
    buttonRow.getChildren().add(cancel);
    content.getChildren().add(buttonRow);
    Rectangle background = popupView.createPopupBackground(320, 360);
    eventPopup.getContent().add(background);
    eventPopup.getContent().add(content);
  }

  /**
   * Creates a formatted representation of an event to display in a week view.
   *
   * @param name        the name of the event
   * @param description the description of the event
   * @return eventBox a box-like representation of an event
   */
  private VBox createEventBox(String name, String description, Time startTime, Time duration) {
    Button delete = new Button("X");
    delete.setStyle("-fx-background-color: transparent");
    Text textName = new Text(name);
    Text textDescription = new Text(description);
    Text textStartTime;
    if (startTime == null) {
      textStartTime = new Text("No start time");
    } else {
      textStartTime = new Text(startTime.toString());
    }
    Text textDuration;
    if (duration == null) {
      textDuration = new Text("No duration specified");
    } else {
      textDuration =
          new Text(String.format("%dhr and %dmin", duration.getHour(), duration.getMinute()));
    }
    VBox eventBox = new VBox(8);
    events.put(new EventJson(textName.getText(), textDescription.getText(), translateToDay(),
        textStartTime.getText(), textDuration.getText()), eventBox);
    EventJson createdEvent = new EventJson(textName.getText(), textDescription.getText(),
        translateToDay(),
        textStartTime.getText(), textDuration.getText());
    Button edit = new Button("E");
    edit.setStyle("-fx-background-color: transparent");
    edit.setOnAction(event -> makeEventEditPopup(createdEvent));
    HBox buttonRow = new HBox(5);
    buttonRow.getChildren().addAll(edit, delete);
    eventBox.getChildren()
        .addAll(new Text("Event:"), textName, textDescription,
            textStartTime, textDuration, buttonRow);
    userController.handleEvent(createdEvent);
    delete.setOnAction(event -> {
      eventBox.getChildren().clear();
      userController.handleRemoveEvent(createdEvent);
    });
    return eventBox;
  }

  /**
   * Creates a popup for the user to set a limit of tasks and/or events for a day.
   */
  private void makeLimitPopup() {
    VBox content = new VBox(8);
    Rectangle padding = new Rectangle(180, 10);
    padding.setFill(Color.valueOf("ffffff"));
    content.getChildren().add(padding);
    Label limitPrompt = new Label("Please enter the limits you would like to set:");
    TextField taskLimit = new TextField("task limit...");
    TextField eventLimit = new TextField("event limit...");
    Button saveLimit = new Button("save");
    saveLimit.setOnAction(
        event -> userController.handleLimit(taskLimit.getText(), eventLimit.getText()));
    Button cancelLimit = new Button("cancel");
    cancelLimit.setOnAction(event -> limitPopup.hide());
    content.getChildren().add(limitPrompt);
    content.getChildren().add(taskLimit);
    content.getChildren().add(eventLimit);
    HBox buttonRow = new HBox();
    buttonRow.getChildren().add(saveLimit);
    buttonRow.getChildren().add(cancelLimit);
    content.getChildren().add(buttonRow);
    Rectangle background = popupView.createPopupBackground(180, 260);
    limitPopup.getContent().add(background);
    limitPopup.getContent().add(content);
  }

  /**
   * Creates a popup that warns the user when they are going to pass their task/event limit.
   */
  private void makeLimitWarning() {
    HBox content = new HBox();
    content.setAlignment(Pos.CENTER);
    Text warning =
        new Text("You cannot add anymore tasks or events or else you will go over your limit!");
    Button okButton = new Button("ok");
    okButton.setOnAction(event -> warnPopup.hide());
    content.getChildren().addAll(warning, okButton);
    Rectangle background = popupView.createPopupBackground(50, 340);
    warnPopup.getContent().add(background);
    warnPopup.getContent().add(content);
  }

  /**
   * Adds a task or event to the week view's grid pane.
   *
   * @param event the event to display
   * @param day   the day to display the event
   */
  public void addToGridPane(VBox event, Day day) {
    switch (day) {
      case FRIDAY -> friPane.getChildren().add(event);
      case MONDAY -> monPane.getChildren().add(event);
      case SUNDAY -> sunPane.getChildren().add(event);
      case TUESDAY -> tuePane.getChildren().add(event);
      case SATURDAY -> satPane.getChildren().add(event);
      case THURSDAY -> thuPane.getChildren().add(event);
      case WEDNESDAY -> wedPane.getChildren().add(event);
      default -> System.out.println("The day input is incorrect");
    }
  }

  /**
   * Shows the welcome screen where the user enters their bujo file name.
   */
  public void showFileTitlePopUp() {
    this.fileTitlePopup.show(this.stage);
  }

  /**
   * Clears the week panes of any tasks and or events.
   */
  private void clearWeekPanes() {
    List<VBox> panes = List.of(friPane, monPane, satPane, sunPane, thuPane, tuePane, wedPane);
    for (VBox pane : panes) {
      pane.getChildren().clear();
    }
  }

  /**
   * Handles button that sorts tasks and events by name.
   */
  private void handleSortTasksByName() {
    List<TaskJson> fileTasks = userController.sortTasks(true);
    clearWeekPanes();
    for (TaskJson task : fileTasks) {
      for (TaskJson hashTask : tasks.keySet()) {
        if (task.equals(hashTask)) {
          addToGridPane(tasks.get(task), task.day());
        }
      }
    }
    for (EventJson event : events.keySet()) {
      addToGridPane(events.get(event), event.day());
    }
  }

  /**
   * Handles button that sorts tasks and events by duration.
   */
  private void handleSortTasksByCompletion() {
    List<TaskJson> fileTasks = userController.sortTasks(false);
    clearWeekPanes();
    for (TaskJson task : fileTasks) {
      for (TaskJson hashTask : tasks.keySet()) {
        if (task.equals(hashTask)) {
          addToGridPane(tasks.get(task), task.day());
        }
      }
    }
    for (EventJson event : events.keySet()) {
      addToGridPane(events.get(event), event.day());
    }
  }

  /**
   * Handles button that sorts tasks and events by name.
   */
  private void handleSortEventsByName() {
    List<EventJson> fileEvents = userController.sortEvents(true);
    clearWeekPanes();
    for (EventJson event : fileEvents) {
      for (EventJson hashEvent : events.keySet()) {
        if (event.equals(hashEvent)) {
          addToGridPane(events.get(event), event.day());
        }
      }
    }
    for (TaskJson taskJson : tasks.keySet()) {
      addToGridPane(tasks.get(taskJson), taskJson.day());
    }
  }

  /**
   * Handles button that sorts tasks and events by duration.
   */
  private void handleSortEventsByDuration() {
    List<EventJson> fileEvents = userController.sortEvents(false);
    clearWeekPanes();
    for (EventJson event : fileEvents) {
      for (EventJson hashEvent : events.keySet()) {
        if (event.equals(hashEvent)) {
          addToGridPane(events.get(event), event.day());
        }
      }
    }
    for (TaskJson taskJson : tasks.keySet()) {
      addToGridPane(tasks.get(taskJson), taskJson.day());
    }
  }

  private void changeLabelTheme(Label label, String textFill, String font) {
    label.setTextFill(Color.valueOf(textFill));
    label.setStyle(font);
  }


  /**
   * Changes the bullet journal's GUI to the specified colors and fonts.
   *
   * @param colorOne color to use for buttons or headers
   * @param colorTwo color to use for text
   * @param font     font to use for text
   */
  private void changeTheme(String colorOne, String colorTwo, String font, String face) {
    headerRect.setFill(Color.valueOf(colorOne));
    headerLabel.setText(face);
    changeLabelTheme(headerLabel, colorTwo, font);
    changeThemeRect.setFill(Color.valueOf(colorOne));
    weekNameText.setBackground(Background.fill(Color.valueOf("#ffffff")));
    weekNameText.setStyle("-fx-text-fill: " + colorTwo);
    weekNameText.setStyle(font);
    changeLabelTheme(quotesLabel, colorTwo, font);
    quotesArea.setStyle(font);
    List<Label> weekLabels =
        List.of(sunLabel, monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel);
    for (Label day : weekLabels) {
      changeLabelTheme(day, colorTwo, font);
    }
    changeLabelTheme(sortTasksLabel, colorTwo, font);
    changeLabelTheme(sortEventsLabel, colorTwo, font);
    changeLabelTheme(taskQueueLabel, colorTwo, font);
    addEventRect.setFill(Color.valueOf(colorOne));
    addTaskRect.setFill(Color.valueOf(colorOne));
    setLimitRect.setFill(Color.valueOf(colorOne));
    saveRect.setFill(Color.valueOf(colorOne));
    tasksNameRect.setFill(Color.valueOf(colorOne));
    tasksDurRect.setFill(Color.valueOf(colorOne));
    eventDurRect.setFill(Color.valueOf(colorOne));
    eventNameRect.setFill(Color.valueOf(colorOne));
    editRect.setFill(Color.valueOf(colorOne));
  }

  /**
   * Makes a popup for the user to change the theme of the bullet journal spread.
   */
  private void makeThemePopup() {
    Button green = new Button("Green");
    green.setOnAction(event -> {
      theme.setThemeType(ThemeType.PINKGREEN);
      changeTheme(theme.getColorOne(), theme.getColorTwo(), theme.getFont(), theme.getFace());
      userController.handleTheme(ThemeType.PINKGREEN);
    });
    Button yellow = new Button("Yellow");
    yellow.setOnAction(event -> {
      theme.setThemeType(ThemeType.YELLOW);
      changeTheme(theme.getColorOne(), theme.getColorOne(), theme.getFont(), theme.getFace());
      userController.handleTheme(ThemeType.YELLOW);
    });
    Button blue = new Button("Blue");
    blue.setOnAction(event -> {
      theme.setThemeType(ThemeType.BLUE);
      changeTheme(theme.getColorOne(), theme.getColorTwo(), theme.getFont(), theme.getFace());
      userController.handleTheme(ThemeType.BLUE);
    });
    Button purple = new Button("Purple");
    purple.setOnAction(event -> {
      theme.setThemeType(ThemeType.PURPLE);
      changeTheme(theme.getColorOne(), theme.getColorTwo(), theme.getFont(), theme.getFace());
      userController.handleTheme(ThemeType.PURPLE);
    });
    Button cancelChange = new Button("cancel");
    cancelChange.setOnAction(event -> changeThemePopup.hide());
    Rectangle background = popupView.createPopupBackground(180, 100);
    VBox vbox = new VBox(8);
    vbox.getChildren().addAll(green, yellow, purple, blue, cancelChange);
    changeThemePopup.getContent().addAll(background, vbox);
  }

  /**
   * Initializes controls of the GUI.
   */
  public void run() {
    addTask.setOnAction(event -> {
      if (userController.checkLimit(true)) {
        this.warnPopup.show(this.stage);
        makeLimitWarning();
      } else {
        this.taskPopup.show(this.stage);
      }
    });
    makeTaskPopUp();
    addEvent.setOnAction(event -> {
      if (userController.checkLimit(false)) {
        this.warnPopup.show(this.stage);
        makeLimitWarning();
      } else {
        this.eventPopup.show(this.stage);
      }
    });
    makeEventPopUp();
    setLimit.setOnAction(event -> this.limitPopup.show(this.stage));
    makeLimitPopup();
    changeTheme.setOnAction(event -> this.changeThemePopup.show(this.stage));
    makeThemePopup();
    save.setOnAction(event -> {
      userController.handleNote(quotesArea.getText());
      userController.handleSave();
    });
    sortByNameTask.setOnAction(event -> handleSortTasksByName());
    sortByDurationTask.setOnAction(event -> handleSortTasksByCompletion());
    sortByNameEvent.setOnAction(event -> handleSortEventsByName());
    sortByDurationEvent.setOnAction(event -> handleSortEventsByDuration());
    makeFileNamePopup();
    showFileTitlePopUp();
  }
}
