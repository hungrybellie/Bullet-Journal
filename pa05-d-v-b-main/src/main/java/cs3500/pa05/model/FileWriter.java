package cs3500.pa05.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Represents a file writer.
 */
public class FileWriter {
  private final File outputFile;

  /**
   * Instantiates a file writer that writes to the given file.
   *
   * @param outputFile the file to write to.
   */
  public FileWriter(File outputFile) {
    if (!outputFile.getName().endsWith(".bujo")) {
      throw new IllegalArgumentException("Notes file must be a .bujo file.");
    }
    this.outputFile = outputFile;
  }

  /**
   * Writes the serialized bullet journal into a file.
   *
   * @param serializedNode the serialized Json node of the bullet journal
   * @throws IOException if the file does not exist.
   */
  public void writeToFile(String serializedNode) throws IOException {
    BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(outputFile));
    writer.write(serializedNode);
    writer.close();
  }

}
