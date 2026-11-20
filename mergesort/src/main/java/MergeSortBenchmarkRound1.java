import java.io.IOException;
import java.nio.file.Path;

public class MergeSortBenchmarkRound1 {
    // Cutoff 0 tắt Insertion Sort và dùng làm mốc đối chứng.
    private static final int[] CUTOFFS = {0, 7, 10, 24, 36, 48, 60};

    public static void main(String[] args) throws IOException {
        MergeSortBenchmark.run(1, Path.of("results", "timings.csv"), CUTOFFS);
    }
}
