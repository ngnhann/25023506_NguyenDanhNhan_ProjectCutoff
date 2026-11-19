import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class GenerateInput {
    public static void main(String[] args) throws IOException {
        int[] sizes = {10, 100, 1_000, 10_000, 100_000};
        Random rng = new Random(42);

        for (int n : sizes) {
            int[] random = new int[n];
            int[] positive = new int[n];
            int[] negative = new int[n];
            int[] descending = new int[n];
            int[] ascending = new int[n];
            int[] narrow = new int[n];

            for (int i = 0; i < n; i++) {
                random[i] = rng.nextInt(2_000_001) - 1_000_000;
                positive[i] = rng.nextInt(1_000_000) + 1;
                negative[i] = -(rng.nextInt(1_000_000) + 1);
                descending[i] = n - i;
                ascending[i] = i + 1;
                narrow[i] = rng.nextInt(7) - 3;
            }

            Path file = Paths.get("inputArrayLength_" + n + ".csv");

            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write("type,n,values");
                writer.newLine();

                writeRow(writer, "random", random);
                writeRow(writer, "positive", positive);
                writeRow(writer, "negative", negative);
                writeRow(writer, "descending", descending);
                writeRow(writer, "ascending", ascending);
                writeRow(writer, "narrow", narrow);
            }

            System.out.println("Da tao " + file.toAbsolutePath());
        }
    }

    private static void writeRow(BufferedWriter writer, String type, int[] values)
            throws IOException {
        StringBuilder row = new StringBuilder();
        row.append(type).append(',').append(values.length).append(',');

        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                row.append(' ');
            }
            row.append(values[i]);
        }

        writer.write(row.toString());
        writer.newLine();
    }
}