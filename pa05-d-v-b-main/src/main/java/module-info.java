module cs3500.pa05 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;

  opens cs3500.pa05 to javafx.fxml;
    exports cs3500.pa05;
    exports cs3500.pa05.controller;
    exports cs3500.pa05.model;
    exports cs3500.pa05.view;
    opens cs3500.pa05.controller to javafx.fxml;
  exports cs3500.pa05.model.Json;
}