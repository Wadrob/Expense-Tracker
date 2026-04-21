package helper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import domain.Expense;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHelper {
    private static final CsvMapper CSV_MAPPER = new CsvMapper();
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

    public static List<Expense> getExpensesFromFile(File file) throws IOException {
        CsvSchema schema = CsvSchema.builder()
                .addColumn("id")
                .addColumn("date")
                .addColumn("description")
                .addColumn("amount")
                .setUseHeader(true)
                .build();

        try (MappingIterator<Expense> iterator =
                     CSV_MAPPER.readerFor(Expense.class)
                             .with(schema)
                             .readValues(file)) {
            return iterator.readAll();
        } catch (IOException e) {
            throw new IOException("Error reading CSV file " + file.getAbsolutePath() + ": " + e);
        }
    }
}
