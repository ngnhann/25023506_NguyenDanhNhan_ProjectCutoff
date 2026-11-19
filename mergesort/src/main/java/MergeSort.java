import java.util.Arrays;
public class MergeSort {
    public static void mergeSort(int[] nums,int cutOFF) {
        if (nums == null || nums.length <= 1) { //nếu mảng null hoặc mảng có độ dài nhỏ hơn hoặc bằng 1 thì trả về mảng đó (TH Gốc)
            return;
        }
        else if (nums.length <= cutOFF){
            InsertionSort.insertionSort(nums,0,nums.length -1);
            return;
        }
        int mid = nums.length / 2; //lấy phần tử chính giữa làm gốc
        int[] left = Arrays.copyOfRange(nums, 0, mid); //coppy mảng trái
        int[] right = Arrays.copyOfRange(nums, mid, nums.length); //coppy mảng phải
        mergeSort(left,cutOFF); //gọi đệ quy trái
        mergeSort(right,cutOFF); //gọi đệ quy phải
        merge(nums, left, right); //hàm merge để nối lại với nhau
    }
    public static void merge(int[] nums, int[] left, int[] right) { //phương pháp gộp 2 mảng vào thành 1 mảng được sắp xếp
        int i = 0; //biến i cho mảng trái, j cho mảng phải, k cho mảng ban đầu
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) { //đầu tiên sắp xếp lại các phần tử của 2 mảng trái, phải vào mảng ban đầu
            if (left[i] <= right[j]) {
                nums[k] = left[i];
                i++;
            } else {
                nums[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < left.length) { //nếu i còn thì thêm các phần tử mảng trái vào nốt
            nums[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length) { //nếu j còn thì thêm các phần tử mảng phải vào nốt
            nums[k] = right[j];
            j++;
            k++;
        }
    }
}
