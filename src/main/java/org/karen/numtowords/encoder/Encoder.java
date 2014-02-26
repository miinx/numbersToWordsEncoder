package org.karen.numtowords.encoder;

import java.io.FileNotFoundException;
import java.util.List;

public interface Encoder {

    List<String> encode(String number) throws FileNotFoundException;

}
