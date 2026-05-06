package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    private static final String HEADERS = "id,date,description,amount\n";

    public static File getFile(String path) throws IOException {
        File file = new File(path);

        if (file.createNewFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(HEADERS);
            }
        }

        return file;
    }
}
