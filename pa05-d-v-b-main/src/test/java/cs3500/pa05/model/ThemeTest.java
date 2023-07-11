package cs3500.pa05.model;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThemeTest {
  Theme pgTheme;
  Theme ywTheme;
  Theme blTheme;
  Theme ppTheme;

  @BeforeEach
  public void setup() {
    pgTheme = new Theme();
    pgTheme.setThemeType(ThemeType.PINKGREEN);

    ywTheme = new Theme();
    ywTheme.setThemeType(ThemeType.YELLOW);

    blTheme = new Theme();
    blTheme.setThemeType(ThemeType.BLUE);

    ppTheme = new Theme();
    ppTheme.setThemeType(ThemeType.PURPLE);
  }

  @Test
  public void getThemeTypeTest() {
    assertEquals(ThemeType.PINKGREEN, pgTheme.getThemeType());
    assertEquals(ThemeType.YELLOW, ywTheme.getThemeType());
    assertEquals(ThemeType.BLUE, blTheme.getThemeType());
    assertEquals(ThemeType.PURPLE, ppTheme.getThemeType());
  }

  @Test
  public void getColorOneTest() {
    assertEquals("#a9bc89", pgTheme.getColorOne());
    assertEquals("#f7dba1", ywTheme.getColorOne());
    assertEquals("#e6f1fc", blTheme.getColorOne());
    assertEquals("#bdb5d0", ppTheme.getColorOne());
  }

  @Test
  public void getColorTwoTest() {
    assertEquals("#555e3a", pgTheme.getColorTwo());
    assertEquals("#a18570", ywTheme.getColorTwo());
    assertEquals("#484e54", blTheme.getColorTwo());
    assertEquals("#323236", ppTheme.getColorTwo());
  }

  @Test
  public void getFontTest() {
    assertEquals("-fx-font-family: 'BM JUA OTF'", pgTheme.getFont());
    assertEquals("-fx-font-family: 'Avenir Next'", ywTheme.getFont());
    assertEquals("-fx-font-family: 'Apple Symbols'", blTheme.getFont());
    assertEquals("-fx-font-family: 'BM DoHyeon OTF'", ppTheme.getFont());
  }

  @Test
  public void getFaceTest() {
    assertEquals("(O_O)", pgTheme.getFace());
    assertEquals("(-_- )", ywTheme.getFace());
    assertEquals("(^ - ^ )", blTheme.getFace());
    assertEquals("(; - ; )", ppTheme.getFace());
  }
}