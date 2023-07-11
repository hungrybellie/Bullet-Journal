package cs3500.pa05.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Represents the JavaFx for a popup.
 */
public class PopupView {

  /**
   * Sets up a GUI background for popups.
   *
   * @param height popup height
   * @param width  popup width
   */
  public Rectangle createPopupBackground(int height, int width) {
    Rectangle background = new Rectangle();
    background.setHeight(height);
    background.setWidth(width);
    background.setFill(Color.valueOf("#ffffff"));
    background.setArcHeight(20);
    background.setArcWidth(20);
    return background;
  }



}
