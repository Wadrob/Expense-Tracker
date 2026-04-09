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
    private final static String EXPENSE_FILE_CSV = "expenseFile.csv";
    private static final CsvMapper CSV_MAPPER = new CsvMapper();

    public static File getFile() throws IOException {
        File file = new File(EXPENSE_FILE_CSV);

        if (file.createNewFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("id,description,amount\n");
            }
        }

        return file;
    }

    public static List<Expense> getExpensesFromFile(File file) throws IOException {
        if (file.length() == 0) {
            return List.of();
        }

        CsvSchema schema = CSV_MAPPER
                .schemaFor(Expense.class)
                .withHeader();

        try {
            MappingIterator<Expense> iterator =
                    CSV_MAPPER.readerFor(Expense.class)
                            .with(schema)
                            .readValues(file);

            return iterator.readAll();
        } catch (IOException e) {
            throw new IOException("Error reading CSV file " + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }
}
