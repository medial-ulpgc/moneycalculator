package moneycalculator.persistence.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import moneycalculator.persistence.file.FileExchangeRateLoader;

public class FileSystemHandler {

    public static <T> Stream<T> loadTextFile(String filename, Function<Stream<String>, Stream<T>> lineTransformation, boolean ignoreFirstLine) {
        Stream<T> response = Stream.empty();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            Stream<String> lines = ignoreFirstLine?reader.lines().skip(1):reader.lines();
            response = lineTransformation.apply(lines);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}

