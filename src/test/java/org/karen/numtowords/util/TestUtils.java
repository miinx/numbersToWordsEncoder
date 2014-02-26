package org.karen.numtowords.util;

import java.io.*;

public class TestUtils {

    public static File createTempFileWithProvidedLines(String fileName, String... lines) throws IOException {

        File tempFile = File.createTempFile(fileName, ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(tempFile));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.format("Error creating temp file '%s'. Error was: %s", fileName, e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return tempFile;

    }

}
