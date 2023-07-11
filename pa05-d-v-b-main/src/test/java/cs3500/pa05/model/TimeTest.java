package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TimeTest {
  @Test
  public void militaryTimeTest() {
    assertThrows(IllegalArgumentException.class, () -> new Time(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> new Time(25, 0));
    assertThrows(IllegalArgumentException.class, () -> new Time(23, -1));
    assertThrows(IllegalArgumentException.class, () -> new Time(23, 60));
  }

  @Test
  public void regularTimeTest() {
    assertThrows(IllegalArgumentException.class, () -> new Time(13, 0, TimeMarkers.PM));
    assertThrows(IllegalArgumentException.class, () -> new Time(0, 0, TimeMarkers.AM));
  }

  @Test
  public void toStringTest() {
    assertEquals("00:00", new Time(0, 0).toString());
    assertEquals("10:10", new Time(10, 10).toString());
    assertEquals("1:00 am", new Time(1, 0, TimeMarkers.AM).toString());
    assertEquals("2:35 pm", new Time(2, 35, TimeMarkers.PM).toString());
  }

  @Test
  public void compareToTest() {
    assertEquals(0, new Time(1, 1).compareTo(new Time(1, 1)));
    assertEquals(1, new Time(2, 1).compareTo(new Time(1, 1)));
    assertEquals(-1, new Time(1, 1).compareTo(new Time(2, 1)));
    assertEquals(1, new Time(1, 2).compareTo(new Time(1, 1)));
    assertEquals(-1, new Time(1, 1).compareTo(new Time(1, 2)));

  }
}