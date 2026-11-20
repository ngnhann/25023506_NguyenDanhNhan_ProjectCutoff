import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// Đọc các mảng từ CSV và dùng cùng quy trình đo cho cả hai vòng.
public class MergeSortBenchmark {
    private static final int[] SIZES = {10, 100, 1_000, 10_000, 100_000, 1_000_000};
    private static final int WARMUP_TRIALS = 5;
    private static final int MEASURED_TRIALS = 10;
    private static final int TARGET_ELEMENTS_PER_BATCH = 10_000;
    private static final String INPUT_HEADER = "type,n,values";
    private static final Set<String> ARRAY_TYPES = Set.of(
            "random", "positive", "negative", "descending", "ascending", "narrow");

    static void run(int round, Path outputFile, int[] cutoffs) throws IOException {
        // Kiểm tra thư mục chạy và đủ file đầu vào trước khi tạo file kết quả.
        Path projectDirectory = Path.of("").toAbsolutePath();
        if (!Files.isRegularFile(projectDirectory.resolve("pom.xml"))
                || !Files.isDirectory(projectDirectory.resolve("data"))) {
            throw new IOException("Hãy chạy benchmark từ thư mục gốc CUTOFF_MERGESORT. "
                    + "Thư mục hiện tại: " + projectDirectory);
        }
        for (int size : SIZES) {
            Path input = inputFile(size);
            if (!Files.isRegularFile(input)) {
                throw new IOException("Thiếu file đầu vào: " + input.toAbsolutePath());
            }
        }

        System.out.println("Vòng " + round + " - Cutoff " + Arrays.toString(cutoffs));
        System.out.println("File kết quả: " + outputFile.toAbsolutePath());

        Path resultsDirectory = outputFile.getParent();
        Files.createDirectories(resultsDirectory);

        // Ghi vào file tạm. Chỉ thay file kết quả sau khi đo xong tất cả các mảng.
        Path temporaryOutput = Files.createTempFile(resultsDirectory, "timings-", ".csv");
        try {
            writeBenchmarkResults(temporaryOutput, cutoffs);
            Files.move(temporaryOutput, outputFile, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            // Nếu có lỗi khi đo, xóa file tạm còn sót lại.
            Files.deleteIfExists(temporaryOutput);
        }

        System.out.println("Saved " + outputFile.toAbsolutePath());
    }

    private static Path inputFile(int size) {
        return Path.of("data", "inputArrayLength_" + size + ".csv");
    }

    private static void writeBenchmarkResults(Path outputFile, int[] cutoffs) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            writer.write("n,array_type,cutoff,trial,batch_size,time_ns");
            writer.newLine();

            // Mỗi kích thước có một file đầu vào. Kết quả của tất cả kích thước ghi chung.
            for (int size : SIZES) {
                benchmarkInputFile(writer, size, cutoffs);
            }
        }
    }

    private static void benchmarkInputFile(BufferedWriter writer, int size, int[] cutoffs)
            throws IOException {
        Path input = inputFile(size);
        // Ghi nhớ các kiểu mảng đã đọc để phát hiện kiểu bị trùng hoặc bị thiếu.
        Set<String> seenTypes = new HashSet<>();
        // Đọc từng dòng để chỉ cần giữ một mảng dữ liệu lớn tại một thời điểm.
        try (BufferedReader reader = Files.newBufferedReader(input)) {
            String header = reader.readLine();
            if (!INPUT_HEADER.equals(header)) {
                throw new IOException("Invalid CSV header: " + input);
            }

            int lineNumber = 1;
            String row = reader.readLine();
            while (row != null) {
                lineNumber++;
                // Mỗi dòng có ba cột. Cột cuối chứa các số cách nhau bằng dấu cách.
                String[] fields = row.split(",", 3);
                if (fields.length != 3) {
                    throw new IOException("Invalid row " + lineNumber + ": " + input);
                }

                String arrayType = fields[0];
                if (!ARRAY_TYPES.contains(arrayType)) {
                    throw new IOException("Unknown array type " + arrayType + " in " + input);
                }
                if (seenTypes.contains(arrayType)) {
                    throw new IOException("Repeated array type " + arrayType + " in " + input);
                }
                seenTypes.add(arrayType);

                int[] original = parseValues(fields, size, input);
                benchmarkArray(writer, arrayType, original, cutoffs);
                row = reader.readLine();
            }
        }

        for (String arrayType : ARRAY_TYPES) {
            if (!seenTypes.contains(arrayType)) {
                throw new IOException("Missing array type " + arrayType + " in " + input);
            }
        }
    }

    private static int[] parseValues(String[] fields, int size, Path input) throws IOException {
        // Kiểm tra cột n và số phần tử thực tế với kích thước ghi trong tên file.
        int declaredSize = Integer.parseInt(fields[1]);
        String[] values = fields[2].trim().split("\\s+");
        if (declaredSize != size || values.length != size) {
            throw new IOException("Wrong array length for " + fields[0] + " in " + input);
        }

        int[] original = new int[size];
        for (int index = 0; index < size; index++) {
            original[index] = Integer.parseInt(values[index]);
        }
        return original;
    }

    private static void benchmarkArray(BufferedWriter writer, String arrayType, int[] original,
                                       int[] cutoffs) throws IOException {
        // Dùng Arrays.sort trên một bản sao để có kết quả chuẩn cho việc đối chiếu.
        int[] expected = original.clone();
        Arrays.sort(expected);

        // Với mảng nhỏ, một lần sort có thể quá ngắn để đo ổn định.
        // Mảng dài 10 được sort 1.000 lần trong một lượt. Mảng dài 100.000 được sort một lần.
        int batchSize = TARGET_ELEMENTS_PER_BATCH / original.length;
        if (batchSize < 1) {
            batchSize = 1;
        }
        int totalTrials = WARMUP_TRIALS + MEASURED_TRIALS;

        int firstCutoffIndex = 0;
        for (int trial = 1; trial <= totalTrials; trial++) {
            // Luân phiên thứ tự để mỗi cutoff có cơ hội chạy ở các vị trí khác nhau.
            // Mỗi lượt chuyển cutoff chạy đầu tiên của lượt trước xuống cuối.
            for (int offset = 0; offset < cutoffs.length; offset++) {
                // Phép chia lấy dư (%) đưa chỉ số về 0 khi đi qua cuối danh sách cutoff.
                int cutoffIndex = (firstCutoffIndex + offset) % cutoffs.length;
                int cutoff = cutoffs[cutoffIndex];
                long elapsedNanoseconds = measureSortBatch(
                        original, expected, arrayType, cutoff, batchSize);

                // Lượt làm nóng cho JVM chạy thuật toán trước khi lấy số liệu.
                if (trial > WARMUP_TRIALS) {
                    int measuredTrial = trial - WARMUP_TRIALS;
                    // Chia thời gian cả nhóm cho số lần sort để lấy nano giây mỗi lần sort.
                    double nanosecondsPerSort = (double) elapsedNanoseconds / batchSize;
                    String result = original.length + "," + arrayType + "," + cutoff + ","
                            + measuredTrial + "," + batchSize + "," + nanosecondsPerSort;
                    writer.write(result);
                    writer.newLine();
                }
            }
            firstCutoffIndex = (firstCutoffIndex + 1) % cutoffs.length;
        }
    }

    private static long measureSortBatch(int[] original, int[] expected, String arrayType,
                                         int cutoff, int batchSize) {
        // Mỗi lần sắp xếp bắt đầu bằng một bản sao mới của cùng mảng gốc.
        int[][] copies = new int[batchSize][];
        for (int index = 0; index < batchSize; index++) {
            copies[index] = original.clone();
        }

        // Bấm giờ sau khi sao chép xong và dừng ngay khi đã sắp xếp hết các bản sao.
        long startTime = System.nanoTime();
        for (int[] copy : copies) {
            MergeSort.mergeSort(copy, cutoff);
        }
        long elapsedNanoseconds = System.nanoTime() - startTime;

        // Sau khi dừng đồng hồ, đối chiếu từng kết quả với Arrays.sort.
        for (int[] copy : copies) {
            if (!Arrays.equals(expected, copy)) {
                throw new IllegalStateException(
                        "Wrong result for n=" + original.length
                                + ", type=" + arrayType + ", cutoff=" + cutoff);
            }
        }
        return elapsedNanoseconds;
    }
}
