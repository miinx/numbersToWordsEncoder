package org.karen.numtowords.util;

public class TestUtils {

    public TestUtils() {
    }

    public String getTestClassesDirectory() {
        String testClassesDirectory = "";
        try {
            testClassesDirectory = getClass().getClassLoader().getResource("").getPath();
        } catch (NullPointerException e) {
            System.out.println("Unable to determine test classes directory.");
        }
        return testClassesDirectory;
    }

}
