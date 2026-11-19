public class InsertionSort {
    public static void insertionSort(int[] nums, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = nums[i]; //lấy phần tử thứ hiện tại làm key bắt đầu từ phần tử thứ 2
            int j = i - 1;
            while (j >= left && nums[j] > key) { //nếu phần tử phía trc lớn hơn thì dịch qua phải
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = key;//chèn vào vị trí thích hợp
        }
    }
}
