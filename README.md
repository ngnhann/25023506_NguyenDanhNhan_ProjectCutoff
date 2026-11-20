# Project CUTOFF MergeSort

- Sinh viên thực hiện: Nguyễn Danh Nhân
- MSV: 25023506
- Lớp: 2627I_UET.CS1058_8

## 1. Mục đích thực nghiệm

Mergesort và Quicksort có chung một cải tiến là CUTOFF dùng Insertion sort đối với mảng độ dài nhỏ. Tuy vậy, với mỗi cấu hình máy sẽ có một giá trị CUTOFF tương ứng phù hợp với cấu hình đó. Vì thế nên tôi thực hiện so sánh, đo thời gian sắp xếp khi cải tiến CUTOFF với các giá trị khác nhau của MergeSort và chọn ra ngưỡng phù hợp nhất với cấu hình máy cá nhân và bộ dữ liệu dùng để thực nghiệm.

Với mỗi giá trị CUTOFF dương K, cương trình sẽ thực hiện InsertionSort cho mảng có độ dài từ 2 đến K. CUTOFF 0 sẽ chạy MergeSort thuần và dùng đấy làm mốc đối chứng.

## 2. Thiết lập thực nghiệm

- **Cấu hình máy tính thực hiện thực nghiệm:**
  + CPU: 13th Gen Intel(R) Core(TM) i7-13650HX (20 CPUs), ~2.6GHz
  + Hệ điều hành: Windows 11 Home Single Language 64-bit (10.0, Build 26300)
  + RAM: 32GB

**Môi trường chạy:**

Oracle Open JDK 25.0.2

**Dữ liệu đầu vào:**

Các mảng có độ dài lần lượt là 10, 100, 1000, 10000, 100000, 1000000. Mỗi độ dài có 6 mảng lần lượt là random, số dương, số âm, tăng dần, giảm dần, sinh trong khoảng hẹp. Được chứa trong 6 file CSV đầu vào nằm trong thư mục data.

Ghi thêm cách sinh dữ liệu và seed tại đây …

**Phương pháp đo thời gian:**

Mỗi kiểu mảng, độ dài mảng và CUTOFFF có 5 lượt làm nóng JVM và 10 lượt đo để lấy số liệu. BenchMark đọc dữ liệu và tạo bản sao trước khi bấm giờ bằng System.nanoTime(). Sau khi dừng bấm giờ, chương trình sẽ kiểm tra lại mảng kết quả

**Tổng hợp và xử lý số liệu:**

`time_ns` là thời gian trung bình cho một lần sắp xếp trong lượt đo đó. Mỗi ô trong bảng đại diện cho trung vị của 10 giá trị time_ns của cùng 1 kiểu độ dài mảng, kiểu mảng và CUTOFF. Xác định giá trị thấp nhất của 1 hàng bằng số trung vị chưa làm tròn của mỗi ô, sau đó được in đậm.

## 3. Vòng 1 — So sánh các CUTOFF ban đầu

Đo CUTOFF `0`, `7`, `10`, `24`, `36`, `48` và `60`. Mỗi bảng ứng với một độ dài mảng, mỗi hàng ứng với một kiểu đầu vào, mỗi cột tương ứng với 1 CUTOFF

**Số liệu đo kết quả** [Kết quả đo](results/timings.csv)

### Mảng độ dài 10 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 0.31205 | 0.05575 | 0.02285 | **0.02255** | 0.02285 | 0.023 | 0.02335 |
| Toàn số dương | 0.2968 | 0.05915 | 0.0217 | 0.02145 | **0.02095** | 0.02115 | 0.0214 |
| Toàn số âm | 0.2343 | 0.05035 | 0.02685 | 0.0268 | 0.02665 | 0.02655 | **0.0265** |
| Giảm dần | 0.1957 | 0.0422 | 0.02955 | 0.0295 | **0.0294** | **0.0294** | 0.02955 |
| Tăng dần | 0.17445 | 0.0311 | **0.0094** | 0.00975 | **0.0094** | **0.0094** | 0.00955 |
| Khoảng hẹp `[-3, 3]` | 0.1908 | 0.0512 | 0.0262 | **0.01645** | 0.017 | 0.02655 | 0.01675 |

### Mảng độ dài 100 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 2.773 | 0.996 | 0.9855 | 0.7505 | 0.7065 | **0.68** | 0.814 |
| Toàn số dương | 2.8445 | 1.011 | 1.0085 | 0.779 | 0.72 | **0.708** | 0.8005 |
| Toàn số âm | 2.856 | 0.998 | 0.9845 | 0.7595 | 0.6745 | **0.674** | 0.6965 |
| Giảm dần | 3.1655 | 1.106 | 1.0695 | **0.904** | 1.0065 | 1.0335 | 1.6915 |
| Tăng dần | 2.626 | 0.735 | 0.7445 | 0.454 | 0.304 | 0.302 | **0.1675** |
| Khoảng hẹp `[-3, 3]` | 2.9185 | 1.0255 | 1.0115 | 0.7755 | **0.663** | 0.6775 | 0.671 |

### Mảng độ dài 1.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 39.935 | 21.675 | 17.19 | 15.085 | 15.305 | 14 | **13.715** |
| Toàn số dương | 31.885 | 15.93 | 13.395 | 12.06 | 13.51 | 11.445 | **11.17** |
| Toàn số âm | 32.015 | 16.005 | 13.48 | 12.03 | 13.005 | 11.155 | **10.82** |
| Giảm dần | 27.62 | 12.645 | 10.495 | **10.305** | 11.64 | 11.845 | 11.76 |
| Tăng dần | 23.955 | 9.4 | 6.42 | 5.38 | 4.13 | **4.02** | **4.02** |
| Khoảng hẹp `[-3, 3]` | 29.53 | 15 | 12.055 | 10.54 | 10.835 | 9.93 | **9.59** |

### Mảng độ dài 10.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 730.4 | 569.9 | 526.25 | 490.2 | 490 | 473.7 | **460.05** |
| Toàn số dương | 798.05 | 587 | 553.2 | 507.3 | 500.8 | 477.4 | **474.45** |
| Toàn số âm | 719.05 | 545.2 | 511.35 | 484.25 | 479.2 | **456.9** | 459.35 |
| Giảm dần | 296 | 144.3 | 135.15 | **132.35** | 132.95 | 168.6 | 169.95 |
| Tăng dần | 245.55 | 109 | 89.75 | 72.9 | 70.45 | **67.05** | 67.2 |
| Khoảng hẹp `[-3, 3]` | 465.85 | 303.45 | 257.5 | 230.1 | 233.8 | **224.45** | 225.05 |

### Mảng độ dài 100.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 8459.55 | 6811.25 | 7055.15 | 6253.6 | 6115.55 | 6228.75 | **5996.65** |
| Toàn số dương | 9045.2 | 7257.75 | 7169.45 | 6698.3 | 6557.5 | 6461.2 | **6368.6** |
| Toàn số âm | 8837.15 | 7139.85 | 7128.8 | 6490.9 | 6353.85 | 6368.1 | **6290.8** |
| Giảm dần | 3398.1 | 1862.9 | 1829.25 | **1747.3** | 1859.2 | 2029.65 | 2541.95 |
| Tăng dần | 2735.4 | 1294.3 | 1263.7 | 973.1 | 943.3 | 897.9 | **828.05** |
| Khoảng hẹp `[-3, 3]` | 5109.4 | 3425.35 | 3355.95 | 2840.15 | 2679.1 | 2653.5 | **2644.45** |

### Mảng độ dài 1.000.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 7 (µs) | Cut 10 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 103599.35 | 86344.15 | 82840.9 | 80374.75 | 77037.1 | **76262.1** | 76535.7 |
| Toàn số dương | 112345.45 | 98051.1 | 90592.95 | 85861.85 | 82964.1 | **81553.2** | 82867.5 |
| Toàn số âm | 106771.3 | 89597.5 | 87798.35 | 83793.05 | **78432.85** | 79172.15 | 79283.75 |
| Giảm dần | 43889.1 | 24509.15 | 21914.9 | **21401.5** | 23907.65 | 25229.85 | 24550.3 |
| Tăng dần | 31879.85 | 17942.95 | 14888.45 | 13573.05 | **11952.4** | 11959 | 12521.7 |
| Khoảng hẹp `[-3, 3]` | 63002.5 | 41531.9 | 39116.35 | 32013.45 | **29959.6** | 30326.9 | 30652.85 |

### Nhận xét vòng 1

CUTOFF `60` có thời gian trung vị thấp nhất ở 13/36 trường hợp và đồng hạng ở 1 trường hợp. Với mảng ngẫu nhiên dài 100.000 phần tử, thời gian giảm từ 8.459,55 µs ở CUTOFF `0` xuống 5.996,65 µs ở CUTOFF `60`, tương đương giảm khoảng 29,1%.

**Ngưỡng phù hợp thay đổi theo kiểu mảng và độ dài:**

CUTOFF `24` nhanh nhất cho mảng giảm dần ở cả năm kích thước từ 100 đến 1.000.000 phần tử.

Với mảng ngẫu nhiên dài 1.000.000 phần tử, CUTOFF `48` có trung vị thấp nhất, còn CUTOFF `36` nhanh nhất trên mảng toàn số âm, mảng tăng dần và mảng khoảng hẹp ở cùng kích thước.

CUTOFF `7` xếp sau ngưỡng nhanh nhất ở cả 36 trường hợp. CUTOFF `10` chỉ đồng hạng nhanh nhất trên mảng tăng dần dài 10 phần tử. Vì vậy, loại `7` và `10` ở vòng 2, giữ `0` làm đối chứng và giữ `24`, `36`, `48`, `60` để so sánh với hai ngưỡng lớn hơn là `72` và `84`.

## 4. Vòng 2 — Đo thêm với các CUTOFF 72 và 84

Vòng 2 dùng cùng bộ dữ liệu và quy trình đo của vòng 1. Danh sách CUTOFF là `0`, `24`, `36`, `48`, `60`, `72` và `84`.

Chạy `MergeSortBenchmarkRound2` từ thư mục gốc dự án. Kết quả vòng này ghi vào [Kết quả đo vòng 2](results/timming2.csv). Mỗi ô trong bảng lấy trung vị từ 10 lượt đo của vòng 2 và đổi sang µs.

### Mảng dài 10 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 0.3064 | **0.02295** | 0.0231 | 0.0235 | 0.0238 | 0.02425 | 0.02315 |
| Toàn số dương | 0.304 | 0.02185 | 0.02165 | 0.02315 | 0.0218 | 0.022 | **0.0215** |
| Toàn số âm | 0.31555 | 0.0293 | 0.0289 | **0.0285** | 0.02925 | 0.0289 | **0.0285** |
| Giảm dần | 0.27635 | 0.0307 | 0.0312 | 0.03105 | 0.03075 | **0.03055** | 0.03105 |
| Tăng dần | 0.1763 | 0.00905 | 0.009 | 0.009 | **0.0089** | **0.0089** | 0.009 |
| Khoảng hẹp `[-3, 3]` | 0.182 | **0.0148** | **0.0148** | **0.0148** | **0.0148** | **0.0148** | 0.015 |

### Mảng dài 100 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 2.679 | 0.732 | 0.708 | **0.671** | 0.8475 | 0.8485 | 0.832 |
| Toàn số dương | 3.047 | 0.792 | **0.7235** | 0.7245 | 0.8355 | 0.842 | 0.8595 |
| Toàn số âm | 2.9545 | 0.758 | **0.68** | 0.6935 | 0.758 | 0.753 | 0.7295 |
| Giảm dần | 3.007 | **0.84** | 0.91 | 0.892 | 1.4825 | 1.491 | 1.505 |
| Tăng dần | 2.6195 | 0.4455 | 0.294 | 0.313 | **0.1935** | 0.2 | 0.1965 |
| Khoảng hẹp `[-3, 3]` | 2.995 | 0.7335 | **0.634** | 0.656 | 0.685 | 0.6865 | 0.674 |

### Mảng dài 1.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 40.95 | 15.155 | 16.915 | 14.405 | **13.335** | 19.035 | 17.855 |
| Toàn số dương | 39.28 | 15.225 | 15.395 | 13.065 | **12.845** | 18.24 | 17.525 |
| Toàn số âm | 31.205 | 12.295 | 12.74 | **10.845** | 11.115 | 16.93 | 15.94 |
| Giảm dần | 27.335 | **10.655** | 12.18 | 11.925 | 11.825 | 20.555 | 20.465 |
| Tăng dần | 22.975 | 5.04 | 4.055 | 3.93 | 3.91 | 3.255 | **3.24** |
| Khoảng hẹp `[-3, 3]` | 29.35 | 10.47 | 10.33 | **9.465** | 9.515 | 14.675 | 14.13 |

### Mảng dài 10.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 725.1 | 469.4 | 476.25 | 455.45 | 458.75 | 444.9 | **442.0** |
| Toàn số dương | 696.45 | 457.45 | 463.15 | 442.9 | 448.75 | **438.6** | 439.8 |
| Toàn số âm | 786.7 | 508.7 | 503.15 | **476.1** | 484.55 | 484.65 | 476.35 |
| Giảm dần | 391.0 | 173.2 | **171.7** | 205.2 | 206.5 | 207.3 | 306.4 |
| Tăng dần | 245.55 | 68.95 | 65.0 | 59.75 | 59.05 | 59.7 | **50.75** |
| Khoảng hẹp `[-3, 3]` | 456.75 | 222.45 | 220.35 | 211.8 | **211.7** | 213.5 | 228.7 |

### Mảng dài 100.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 8762.35 | 6633.0 | 6513.35 | 6471.2 | 6215.75 | **6180.95** | 6262.5 |
| Toàn số dương | 9436.8 | 6752.35 | 6595.45 | 6712.15 | 6396.2 | **6325.1** | 6400.25 |
| Toàn số âm | 9005.15 | 6601.55 | 6367.75 | 6389.45 | 6286.5 | **6204.15** | 6246.3 |
| Giảm dần | 4732.55 | 2117.6 | **2053.65** | 2291.95 | 2839.1 | 2545.0 | 2577.35 |
| Tăng dần | 3766.65 | 1205.6 | 1639.4 | 1304.15 | 908.1 | **899.3** | 935.95 |
| Khoảng hẹp `[-3, 3]` | 8115.45 | 4602.15 | 4515.0 | 4510.1 | 4012.6 | 3975.65 | **3730.85** |

### Mảng dài 1.000.000 phần tử

| Kiểu mảng | Cut 0 (µs) | Cut 24 (µs) | Cut 36 (µs) | Cut 48 (µs) | Cut 60 (µs) | Cut 72 (µs) | Cut 84 (µs) |
|---|---:|---:|---:|---:|---:|---:|---:|
| Ngẫu nhiên | 152537.35 | 108298.85 | 102625.25 | 102676.35 | 109186.9 | 104962.75 | **97492.7** |
| Toàn số dương | 105170.1 | 80260.45 | 76946.7 | 78446.15 | 78944.8 | **75799.8** | 76631.65 |
| Toàn số âm | 105344.8 | 81929.3 | 78920.35 | 77973.4 | 77802.25 | 77823.85 | **76507.9** |
| Giảm dần | 56069.8 | **29358.3** | 39707.45 | 32926 | 32517.45 | 47169.75 | 49380.15 |
| Tăng dần | 42236.1 | 17370.7 | 14657.4 | **13558.25** | 14386.1 | 13854.6 | 14997.2 |
| Khoảng hẹp `[-3, 3]` | 64978.15 | 42507.25 | **37075.3** | 40305.3 | 42960 | 46122.4 | 39919.5 |

### Nhận xét vòng 2:

CUTOFF `72` và `84` mỗi ngưỡng có thời gian trung vị thấp nhất ở 7/36 trường hợp. Ngoài ra, `72` đồng hạng ở 2 trường hợp và `84` đồng hạng ở 1 trường hợp. Với mảng ngẫu nhiên dài 1.000.000 phần tử, thời gian giảm từ 152.537,35 µs ở CUTOFF `0` xuống 97.492,7 µs ở CUTOFF `84`, tương đương giảm khoảng 36,1%.

**Ngưỡng phù hợp thay đổi theo kiểu mảng và độ dài**

Với mảng giảm dần từ 100 đến 1.000.000 phần tử, CUTOFF nhanh nhất là `24` hoặc `36` tùy kích thước. Ở độ dài 1.000.000 phần tử, CUTOFF `84` nhanh nhất với mảng ngẫu nhiên và mảng toàn số âm, `72` nhanh nhất với mảng toàn số dương, còn `36` nhanh nhất với mảng có giá trị trong khoảng hẹp.

So với ngưỡng tốt nhất trong nhóm giữ lại (`0`, `24`, `36`, `48`, `60`) đo trong cùng vòng, ít nhất một trong hai ngưỡng mới cho thời gian thấp hơn ở 14/36 trường hợp và đồng hạng ở 3/36 trường hợp. Kết quả cho thấy `72` và `84` phù hợp với một số kiểu đầu vào, trong khi các ngưỡng nhỏ hơn vẫn cho kết quả tốt nhất ở nhiều trường hợp, đặc biệt với mảng giảm dần.

## 5. Kết luận

**Nhận xét chung:**

Trong các ngưỡng đã thử, CUTOFF 24 hoặc 36 phù hợp nhất với mảng giảm dần từ 100 phần tử. Với mảng độ dài trung bình, 48 và 60 là các ứng viên đáng cân nhắc. Với mảng lớn, 72 và 84 thường cho thời gian thấp nhất, nhưng lựa chọn cụ thể còn phụ thuộc vào kiểu đầu vào.

**Đề xuất CUTOFF cho cấu hình máy thực nghiệm:** 48-72

**Số liệu dùng để chọn:**

Dựa vào thời gian trung vị của 10 lượt đo cho mỗi tổ hợp độ dài mảng, kiểu mảng và CUTOFF ở vòng 2. Trong 36 trường hợp, nhóm CUTOFF từ 48-72 có 16 trường hợp đứng đầu riêng và 3 trường hợp đồng hạng nhất. CUTOFF 48 nhanh nhất với mảng toàn số âm dài 10.000 phần tử (476,10 µs), CUTOFF 60 nhanh nhất với mảng ngẫu nhiên dài 1.000 phần tử (13,335 µs), còn CUTOFF 72 nhanh nhất với mảng ngẫu nhiên dài 100.000 phần tử (6.180,95 µs). Đây là cơ sở để chọn 48, 60 và 72 làm các ngưỡng mặc định có thể thử. Riêng với mảng giảm dần từ 100 phần tử trở lên, số liệu đang nghiêng về nhóm CUTOFF từ 24-36

**Phạm vi áp dụng**:

- Cấu hình máy như ở phần thiết lập thực nghiệm, chạy full hiệu năng
- Merge dùng Arrays.copyOfRange để copy mảng kết hợp với InsertionSort
- Bộ dữ liệu ngẫu nhiên, tăng dần, giảm dần, số âm, số dương và nằm trong 1 khoảng hẹp

## 6. Cách chạy lại và tệp nộp

Mở PowerShell ở thư mục gốc dự án, nơi có `pom.xml` và thư mục `data`. Dùng JDK 25 để biên dịch và chạy JUnit.

```powershell
mvn -pl mergesort test
```

Chọn lệnh tương ứng với vòng bạn muốn đo. Trước khi đo, chương trình in số vòng, các CUTOFF và đường dẫn file CSV.

| Vòng | Lệnh chạy | File kết quả |
|---|---|---|
| 1 | `java -cp .\mergesort\target\classes MergeSortBenchmarkRound1` | [`results/timings.csv`](results/timings.csv) |
| 2 | `java -cp .\mergesort\target\classes MergeSortBenchmarkRound2` | [`results/timming2.csv`](results/timming2.csv) |

Mỗi lần đo thành công sẽ thay nội dung CSV của vòng đã chọn.
