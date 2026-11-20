# Nhật ký tóm tắt quá trình sử dụng AI (AI Usage Log)

- **Sinh viên thực hiện:** Nguyễn Danh Nhân
- **MSSV:** 25023506
- **Lớp:** 2627I_UET.CS1058_8
- **Công cụ AI sử dụng:** OpenAI Codex
- **Dự án:** Thực nghiệm CUTOFF để kết hợp Mergesort với Insertion Sort bằng Java trên cấu hình máy cá nhân (OpenJDK 25.0.2)
- **Thời gian trao đổi:** Từ 19 đến 20/11/2026

## 1. Phạm vi sử dụng AI

Tôi dùng Codex để phân tích đề, thảo luận cách thiết kế thí nghiệm, rà soát mã, hỗ trợ sửa cấu hình Maven và IntelliJ, viết chương trình benchmark, kiểm tra CSV và trình bày kết quả trong README. Tôi tự viết bản Mergesort gốc theo bài giảng, viết bộ test ban đầu và chương trình sinh dữ liệu, sau đó bổ sung Insertion Sort và tham số CUTOFF. Tôi quyết định cách tổ chức dữ liệu, chọn các ngưỡng cần thử và trực tiếp chạy các lần đo dùng trong báo cáo. Codex thực hiện các lần chạy kiểm tra riêng để xác nhận mã biên dịch được, sắp xếp đúng và ghi CSV theo định dạng đã thống nhất.

## 2. Tóm tắt các giai đoạn trao đổi thực tế

### 2.1. Ngày 19/11 — Phân tích đề và kiểm tra Mergesort gốc

Tôi hỏi Codex cách hiểu đề bài và cách tổ chức dự án. Hai bên thống nhất dùng CUTOFF `0` làm mốc Mergesort thuần, thử các ngưỡng dương trên cùng dữ liệu và trình bày kết quả theo từng độ dài và kiểu mảng. Tôi viết `MergeSort.java` và `MergeSortTest.java` trước, rồi nhờ Codex rà soát. Cả 8 ca JUnit ban đầu chạy thành công, gồm mảng rỗng, mảng một phần tử, mảng tăng dần, mảng giảm dần, số trùng, số âm, mảng thông thường và `null`.

### 2.2. Ngày 19/11 — Thiết kế đầu vào và xử lý lỗi chạy dự án

Tôi chọn sáu kiểu mảng cho mỗi độ dài, gồm ngẫu nhiên, toàn số dương, toàn số âm, giảm dần, tăng dần và khoảng hẹp `[-3, 3]`. Sau khi xem cách tổ chức dữ liệu ban đầu do Codex tạo, tôi yêu cầu hoàn tác và chỉ hướng dẫn để tự viết. Tôi viết `GenerateInput.java` với seed `42`, mỗi file CSV ứng với một độ dài và mỗi dòng dữ liệu chứa một mảng riêng.

Khi thêm module `sinh_test`, Maven chuyển project gốc thành POM cha và bỏ qua bộ test còn nằm ở `src` gốc. Codex xác định nguyên nhân, thêm module `mergesort` và chuyển hai file Java sang đúng module. Sau đó, chúng tôi xử lý lỗi IntelliJ trên đường dẫn có dấu tiếng Việt. Codex tái hiện `InvalidPathException` và `ClassNotFoundException`, rồi kiểm tra cách chạy bằng PowerShell với đường dẫn tương đối. [Chương trình sinh dữ liệu](../sinh_test/src/main/java/GenerateInput.java)

### 2.3. Ngày 19/11 — Rà soát Hybrid Sort và xây dựng benchmark

Sau khi tôi thêm Insertion Sort và tham số CUTOFF, Codex rà soát phần trộn hai nửa mảng và phát hiện bộ JUnit vẫn gọi hàm theo chữ ký cũ. Codex kiểm tra riêng kết quả sắp xếp bằng `Arrays.sort` trên các ca nhỏ và dữ liệu CSV. Sau khi tôi duyệt phương án, Codex cập nhật JUnit để thử nhiều CUTOFF và kiểm tra ba độ dài quanh mỗi ngưỡng dương. Theo yêu cầu của tôi, Codex viết lại các đoạn khó đọc bằng vòng lặp thường, tên biến rõ ràng và chú thích tiếng Việt. [Mã Mergesort](../mergesort/src/main/java/MergeSort.java) · [Bộ test JUnit](../mergesort/src/test/java/MergeSortTest.java)

Chúng tôi dùng `System.nanoTime()` để đo, với 5 lượt làm nóng JVM và 10 lượt lấy số liệu cho mỗi tổ hợp. Chương trình sao chép mảng trước khi bấm giờ, luân phiên thứ tự CUTOFF và kiểm tra kết quả sau khi dừng đồng hồ. Với mảng ngắn, `batch_size` là số lần sắp xếp trong một lượt bấm giờ. Tổng thời gian của lượt đó được chia cho `batch_size` để ghi `time_ns` cho một lần sắp xếp.

### 2.4. Ngày 19–20/11 — Mở rộng dữ liệu và thực hiện vòng 1

Tôi bắt đầu với các ngưỡng `0`, `7`, `10`, `24`, rồi mở rộng vòng 1 lên `36`, `48` và `60`. Theo yêu cầu của tôi, Codex bổ sung độ dài `1.000.000` vào chương trình sinh dữ liệu và benchmark, chạy lại phần sinh dữ liệu và kiểm tra file mới có đủ sáu mảng. Benchmark được chỉnh để đọc từng dòng CSV khi xử lý file lớn. Khi IntelliJ chỉ hiển thị hàng `random` trong phần xem trước, Codex đọc file trực tiếp và xác nhận năm kiểu còn lại ở các dòng kế tiếp.

Tôi chạy benchmark chính thức. Codex kiểm tra CSV có đủ 2.520 dòng đo, với `trial` từ 1 đến 10 cho mỗi tổ hợp. Các bảng trong README dùng trung vị của 10 giá trị `time_ns`, đổi sang µs. Nhận xét vòng 1 ghi nhận CUTOFF `60` có trung vị thấp nhất ở 13/36 trường hợp và đồng hạng thêm một trường hợp. Với mảng giảm dần từ 100 đến 1.000.000 phần tử, CUTOFF `24` nhanh nhất ở cả năm kích thước. [Số liệu vòng 1](../results/timings.csv)

### 2.5. Ngày 20/11 — Thực hiện vòng 2 và tổng hợp kết quả

Tôi quyết định bỏ `7` và `10`, giữ `0`, `24`, `36`, `48`, `60` và thử thêm `72`, `84`. Codex tạo lớp chạy riêng cho vòng 2 và dùng chung quy trình đo với vòng 1. Đường dẫn đầu ra ban đầu bị đặt nhầm. Sau khi tôi chỉ ra, Codex chuyển file đã đo vào `results/timming2.csv` và cập nhật cách chạy để mỗi vòng in rõ tên vòng, danh sách CUTOFF và đường dẫn kết quả. Sau khi tách hai lớp chạy, cả 9 phương thức JUnit đều pass. [Chương trình benchmark](../mergesort/src/main/java/MergeSortBenchmark.java) · [Số liệu vòng 2](../results/timming2.csv)

Tôi gửi bảng trung vị để điền vào README. Theo phản hồi của tôi, Codex hoàn tác lần điền bảng vòng 2 đầu tiên, sau đó đọc lại file tôi gửi và đối chiếu từng ô theo độ dài, kiểu mảng và CUTOFF. Tôi viết nhận xét vòng 2 theo bố cục vòng 1 và thảo luận phần kết luận. Hai ngưỡng mới `72` và `84` cải thiện trung vị ở 14/36 trường hợp so với ngưỡng nhanh nhất trong nhóm giữ lại, khi so sánh trong cùng vòng. Tôi chọn `48`, `60`, `72` làm các ngưỡng đề xuất để thử trước trên bộ dữ liệu này. Với mảng giảm dần từ 100 phần tử trở lên, kết quả vòng 2 nghiêng về `24` hoặc `36`. [Bảng trung vị vòng 2](../results/bangTrungViKetQua2.csv) · [Báo cáo thực nghiệm](../README.md)

## 3. Phân định phần việc thực hiện

| Hạng mục | Sinh viên thực hiện | AI hỗ trợ |
|---|---|---|
| **Cấu trúc dự án và cài đặt Mergesort** | Tạo dự án, tự viết `MergeSort.java` theo bài giảng và bộ JUnit ban đầu, sau đó bổ sung Insertion Sort và tham số CUTOFF. | Rà soát logic chia và trộn mảng, chẩn đoán lỗi Maven và IntelliJ, hỗ trợ sắp xếp lại các module và mở rộng JUnit để kiểm tra nhiều CUTOFF. |
| **Dữ liệu và chạy thực nghiệm** | Chọn kiểu mảng, độ dài và các CUTOFF cho từng vòng, viết `GenerateInput.java` ban đầu và trực tiếp chạy hai vòng đo trên máy cá nhân. | Hướng dẫn định dạng CSV, bổ sung kích thước 1.000.000 phần tử vào chương trình sinh dữ liệu, viết benchmark dùng chung cho hai vòng và kiểm tra số lượt đo cùng đường dẫn kết quả. |
| **Phân tích và báo cáo** | Cung cấp thông tin cấu hình máy và số liệu đo, chuẩn bị bảng trung vị, rà soát README và chọn các CUTOFF đề xuất, viết nhận xét. | Tạo khung báo cáo, điền bảng Markdown từ số liệu đã đối chiếu. |
