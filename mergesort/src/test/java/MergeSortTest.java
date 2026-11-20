import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// Các test này kiểm tra kết quả sắp xếp của từng cutoff.
public class MergeSortTest {
    private static final int[] CUTOFFS = {0, 7, 10, 24, 36, 48, 60};

    @Test
    void testNormalArray() {
        int[] input = {5, 2, 8, 1, 3};
        // expected là thứ tự đúng mà mảng đầu vào phải có sau khi sắp xếp.
        int[] expected = {1, 2, 3, 5, 8};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testSingleElement() {
        int[] input = {5};
        int[] expected = {5};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testReverseSorted() {
        int[] input = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testDuplicateElements() {
        int[] input = {4, 2, 4, 1, 2};
        // Mảng kết quả phải giữ đủ hai số 2 và hai số 4.
        int[] expected = {1, 2, 2, 4, 4};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testNegativeNumbers() {
        int[] input = {-5, 3, -1, 0, 2};
        int[] expected = {-5, -1, 0, 2, 3};
        checkAllCutoffs(input, expected);
    }

    @Test
    void testNullArray() {
        // Khi đầu vào là null, hàm cần kết thúc bình thường với mọi cutoff.
        for (int cutoff : CUTOFFS) {
            assertDoesNotThrow(() -> MergeSort.mergeSort(null, cutoff), "cutoff = " + cutoff);
        }
    }

    @Test
    void testLengthsAroundCutoff() {
        for (int cutoff : CUTOFFS) {
            // Cutoff 0 tắt Insertion Sort nên không có độ dài quanh ngưỡng cần thử.
            if (cutoff == 0) {
                continue;
            }

            // Ví dụ, cutoff 7 được thử với mảng dài 6, 7 và 8.
            // Hai mảng đầu vào thẳng Insertion Sort. Mảng dài 8 phải chia trước.
            for (int length = cutoff - 1; length <= cutoff + 1; length++) {
                int[] input = new int[length];
                int[] expected = new int[length];
                // Tạo đầu vào giảm dần và ghi sẵn kết quả kỳ vọng theo thứ tự tăng dần.
                for (int index = 0; index < length; index++) {
                    input[index] = length - index;
                    expected[index] = index + 1;
                }

                MergeSort.mergeSort(input, cutoff);
                String testCase = "cutoff = " + cutoff + ", length = " + length;
                assertArrayEquals(expected, input, testCase);
            }
        }
    }

    private void checkAllCutoffs(int[] input, int[] expected) {
        for (int cutoff : CUTOFFS) {
            // MergeSort sửa trực tiếp mảng được truyền vào. Mỗi cutoff dùng một bản sao mới.
            int[] actual = input.clone();
            MergeSort.mergeSort(actual, cutoff);
            // So sánh cả mảng để kiểm tra đúng thứ tự và đủ phần tử.
            assertArrayEquals(expected, actual, "cutoff = " + cutoff);
        }
    }
}
