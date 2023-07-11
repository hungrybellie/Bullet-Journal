package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class FileWriterTest {
  @Test
  public void writeToFileTest() {
    File testFile = Path.of("fileWriterTestFile.bujo").toFile();
    FileWriter writer = new FileWriter(testFile);
    try {
      writer.writeToFile("testNodehehe");
      Scanner scanner = new Scanner(testFile);
      assertEquals("testNodehehe", scanner.nextLine());
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void invalidInputTest() {
    assertThrows(IllegalArgumentException.class,
        () -> new FileWriter(Path.of("fileWriterTestFile.md").toFile()));
  }
}