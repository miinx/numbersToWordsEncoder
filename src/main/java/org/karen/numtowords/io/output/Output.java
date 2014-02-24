package org.karen.numtowords.io.output;

import java.util.List;

public interface Output {

    void write(String message);

    void writeEncodingResults(String number, List<String> wordMatches);
}
