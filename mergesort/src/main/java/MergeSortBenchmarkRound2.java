import java.io.IOException;
import java.nio.file.Path;

public class MergeSortBenchmarkRound2 {
    private static final int[] CUTOFFS = {0, 24, 36, 48, 60, 72, 84};

    public static void main(String[] args) throws IOException {
        // Hai vòng dùng chung thư mục results, mỗi vòng ghi vào một file riêng.
        MergeSortBenchmark.run(2, Path.of("results", "timming2.csv"), CUTOFFS);
    }
}
