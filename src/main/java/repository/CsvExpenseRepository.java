package repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import domain.Expense;
import helper.FileHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvExpenseRepository implements ExpenseRepository {

    private static final CsvMapper CSV_MAPPER = new CsvMapper();

    private final File file;

    public CsvExpenseRepository(Path filePath) throws IOException {
        this.file = FileHelper.getFile(filePath.toString());
    }

    @Override
    public List<Expense> findAll() {
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
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Error reading CSV file " + file.getAbsolutePath() + ": " + e);
        }
    }

    @Override
    public void save(Expense expense) {
        try {
            boolean fileExists = file.exists() && file.length() > 0;
            int nextID = generateNextId();

            try (FileWriter writer = new FileWriter(file, true)) {

                if (!fileExists) {
                    writer.write("id,date,description,amount\n");
                }

                writer.write(String.format("%d,%s,%s,%s%n",
                        nextID,
                        expense.getDate(),
                        escape(expense.getDescription()),
                        expense.getAmount()));
            }

            System.out.println("Added Expense with id: " + nextID);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        List<Expense> all = findAll();

        boolean removed = all.removeIf(e -> e.getId() == id);

        if (!removed) {
            System.out.println("Expense with id " + id + " not found");
            return;
        }

        try {
            saveAll(all);
            System.out.println("Deleted expense with id: " + id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int generateNextId() {
        return findAll().stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1;
    }

    private String escape(String value) {
        return value.replace(",", " ");
    }

    private void saveAll(List<Expense> expenses) throws IOException {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("id,date,description,amount\n");

            for (Expense expense : expenses) {
                writer.write(String.format("%d,%s,%s,%s%n",
                        expense.getId(),
                        expense.getDate(),
                        escape(expense.getDescription()),
                        expense.getAmount()));
            }
        }
    }
}
