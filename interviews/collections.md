# Hiểu về cách hoạt động nội bộ của **HashMap** trong Java

**HashMap** là một trong những cấu trúc dữ liệu quan trọng và được sử dụng phổ biến nhất trong Java để lưu trữ dữ liệu dưới dạng cặp **key-value**. Hãy cùng tìm hiểu cách **HashMap** hoạt động bên trong.

---

### 1. **Cấu trúc nội bộ**
- **HashMap** sử dụng một mảng của các **Bucket**.
- Mỗi **Bucket** trong HashMap được thể hiện như một **LinkedList** hoặc **Red-Black Tree** để xử lý các trường hợp **hash collision** (xung đột băm).

**Cấu trúc chính:**
- **Node<K, V>:** Là phần tử lưu trữ trong mỗi Bucket. Nó chứa:
    - `key`: Khóa.
    - `value`: Giá trị.
    - `hash`: Giá trị băm của khóa.
    - `next`: Con trỏ trỏ đến phần tử tiếp theo (trong trường hợp có xung đột).

---

### 2. **Quá trình thêm phần tử (`put` method)**

Khi bạn gọi phương thức `put(key, value)` để thêm phần tử vào HashMap:

1. **Tính toán giá trị băm (hash) của khóa:**
    - Hàm băm được sử dụng để chuyển đổi khóa thành một giá trị số nguyên.
    - **Công thức băm:**
      ```java
      hash = key.hashCode() ^ (key.hashCode() >>> 16);
      ```
      Hàm này giúp phân phối các giá trị băm đồng đều hơn.

2. **Xác định chỉ mục trong mảng Bucket:**
    - **Công thức chỉ mục:**
      ```java
      index = (n - 1) & hash;
      ```
      Trong đó `n` là kích thước của mảng Bucket.

3. **Thêm phần tử vào Bucket:**
    - Nếu Bucket tại `index` chưa có phần tử, Node mới được tạo và lưu vào.
    - Nếu Bucket đã có phần tử, kiểm tra **xung đột băm** (:
        - Nếu khóa đã tồn tại, giá trị mới sẽ ghi đè lên giá trị cũ.
        - Nếu khóa khác nhau nhưng trùng chỉ mục (collision), phần tử sẽ được thêm vào danh sách liên kết.

4. **Chuyển đổi thành cây đỏ-đen:**
    - Nếu số lượng phần tử trong danh sách liên kết vượt quá ngưỡng (`TREEIFY_THRESHOLD` = 8), danh sách sẽ được chuyển thành cây đỏ-đen để tối ưu hiệu suất.

---

### 3. **Quá trình truy vấn phần tử (`get` method)**

Khi bạn gọi `get(key)` để lấy giá trị tương ứng với khóa:

1. **Tính giá trị băm (hash) của khóa.**
2. **Xác định chỉ mục Bucket.**
3. **Tìm kiếm trong Bucket:**
    - So sánh giá trị băm và khóa:
        - Nếu trùng, trả về giá trị.
        - Nếu không, tiếp tục kiểm tra các phần tử khác trong danh sách liên kết hoặc cây đỏ-đen.

---

### 4. **Cơ chế Resize**

- Khi số lượng phần tử trong HashMap vượt quá `load factor` (mặc định là `0.75`), HashMap sẽ tự động tăng kích thước mảng Bucket gấp đôi.
- **Quá trình Resize:**
    - Tạo một mảng mới có kích thước lớn hơn.
    - Rehash (tính toán lại hash) và phân phối lại các phần tử.

---

### 5. **Hiệu suất**

| **Tình huống**        | **Trung bình** | **Tệ nhất** (collision) |
|------------------------|----------------|--------------------------|
| **Truy vấn (`get`)**   | O(1)           | O(log n) (với cây)       |
| **Thêm (`put`)**       | O(1)           | O(log n) (với cây)       |

---

### 6. **Ưu điểm và nhược điểm**

**Ưu điểm:**
- Tốc độ truy vấn và thêm dữ liệu nhanh (O(1) trung bình).
- Hỗ trợ lưu trữ dữ liệu không đồng nhất (null keys và values).

**Nhược điểm:**
- Tốn bộ nhớ do sử dụng mảng và xử lý xung đột.
- Hiệu suất phụ thuộc vào hàm băm và khả năng phân phối đều các giá trị băm.


### 7. **So sánh `Collections.synchronizedMap()` và `ConcurrentHashMap`**

| **Tiêu chí**                | **SynchronizedMap**                             | **ConcurrentHashMap**                          |
|-----------------------------|-----------------------------------------------|-----------------------------------------------|
| **Đồng bộ hóa**             | Toàn bộ Map bị khóa khi thao tác.             | Chỉ khóa bucket cụ thể khi ghi. Đọc không cần khóa. |
| **Hiệu suất**               | Thấp hơn do khóa toàn bộ.                     | Cao hơn do hỗ trợ đồng thời nhiều thao tác.   |
| **Đọc/Ghi đồng thời**       | Không hỗ trợ.                                 | Hỗ trợ nhiều luồng đọc/ghi đồng thời.         |
| **Phương thức nâng cao**    | Không hỗ trợ.                                 | Hỗ trợ (e.g., `compute()`, `merge()`).        |
| **Khi nào nên dùng**         | Ứng dụng nhỏ, ít luồng.                      | Ứng dụng lớn, nhiều luồng, yêu cầu hiệu năng. |

---

### **Kết luận**

- **Nếu ứng dụng nhỏ hoặc không yêu cầu cao về hiệu suất**, bạn có thể sử dụng `Collections.synchronizedMap()` vì nó dễ áp dụng và ít phức tạp.
- **Nếu ứng dụng lớn với nhiều luồng truy cập đồng thời**, ưu tiên sử dụng `ConcurrentHashMap` vì nó tối ưu hơn rất nhiều về hiệu năng và hỗ trợ các thao tác nâng cao cho môi trường đa luồng.

# 2. **Cách hoạt động nội bộ của LinkedList trong Java**

`LinkedList` trong Java là một cấu trúc dữ liệu được triển khai dựa trên **danh sách liên kết kép (Doubly Linked List)**. Đây là một cấu trúc mà mỗi phần tử được lưu trong một **nút (Node)**, và các nút này được liên kết với nhau thông qua các con trỏ **next** (đến nút kế tiếp) và **prev** (đến nút trước đó). Dưới đây là chi tiết cách hoạt động của `LinkedList` bên trong.

---

### **1. Cấu trúc của LinkedList**

#### **Node (Nút)**
- Mỗi phần tử trong `LinkedList` được đại diện bởi một **Node**, và Node bao gồm 3 thành phần:
    - **Dữ liệu (item):** Giá trị thực tế được lưu trữ.
    - **Con trỏ kế tiếp (next):** Trỏ đến nút tiếp theo trong danh sách.
    - **Con trỏ trước đó (prev):** Trỏ đến nút trước đó trong danh sách.

#### **Các thuộc tính chính của LinkedList**
- **first:** Trỏ đến nút đầu tiên (head) của danh sách.
- **last:** Trỏ đến nút cuối cùng (tail) của danh sách.
- **size:** Số lượng phần tử hiện tại trong danh sách.

---

### **2. Cách hoạt động của các phương thức chính**

#### **a. Thêm phần tử (add)**

- **Thêm vào cuối danh sách:**
    - Tạo một nút mới để lưu trữ dữ liệu.
    - Liên kết nút cuối hiện tại (`last`) với nút mới qua con trỏ `next`.
    - Cập nhật con trỏ `prev` của nút mới trỏ về nút cuối cũ.
    - Đặt con trỏ `last` của danh sách trỏ đến nút mới.

- **Thêm vào đầu danh sách:**
    - Tạo một nút mới.
    - Liên kết nút đầu hiện tại (`first`) với nút mới qua con trỏ `prev`.
    - Liên kết con trỏ `next` của nút mới đến nút đầu cũ.
    - Cập nhật con trỏ `first` trỏ đến nút mới.

- **Thêm vào giữa danh sách:**
    - Duyệt đến vị trí cần thêm.
    - Cập nhật các con trỏ của nút trước và nút sau để liên kết chúng với nút mới.

---

#### **b. Truy xuất phần tử (get)**

- Khi muốn truy xuất một phần tử tại vị trí cụ thể:
    - Nếu vị trí cần truy xuất nằm trong nửa đầu danh sách, bắt đầu duyệt từ **first**.
    - Nếu vị trí nằm trong nửa sau danh sách, bắt đầu duyệt từ **last**.
    - Lần lượt theo con trỏ `next` (hoặc `prev`) để tìm đến nút mong muốn.

Cách này đảm bảo cân bằng giữa hiệu suất và tính hiệu quả khi truy xuất dữ liệu.

---

#### **c. Xóa phần tử (remove)**

- **Xóa ở đầu danh sách:**
    - Cập nhật con trỏ `first` trỏ đến nút thứ hai.
    - Hủy tham chiếu từ nút đầu cũ đến danh sách.

- **Xóa ở cuối danh sách:**
    - Cập nhật con trỏ `last` trỏ đến nút trước nút cuối.
    - Hủy tham chiếu từ nút cuối cũ.

- **Xóa ở giữa danh sách:**
    - Duyệt đến vị trí cần xóa.
    - Cập nhật con trỏ `next` của nút trước đó trỏ đến nút sau đó, và con trỏ `prev` của nút sau đó trỏ đến nút trước đó.
    - Hủy tham chiếu của nút cần xóa.

---

### **3. Ưu và nhược điểm của LinkedList**

#### **Ưu điểm**
1. **Thêm/Xóa nhanh:**
    - Do không cần di chuyển các phần tử khác như trong `ArrayList`, việc thêm/xóa chỉ cần thay đổi các con trỏ `next` và `prev`.
2. **Kích thước linh hoạt:**
    - Không cần xác định trước kích thước, `LinkedList` có thể mở rộng hoặc thu nhỏ một cách động.

#### **Nhược điểm**
1. **Truy xuất chậm:**
    - Không hỗ trợ truy xuất ngẫu nhiên trực tiếp bằng chỉ số. Mỗi lần truy xuất cần duyệt từ đầu hoặc cuối danh sách.
2. **Tốn bộ nhớ hơn:**
    - Mỗi nút trong danh sách lưu thêm hai con trỏ `next` và `prev`, dẫn đến sử dụng nhiều bộ nhớ hơn so với `ArrayList`.

---

### **4. Khi nào nên sử dụng LinkedList?**

- Sử dụng `LinkedList` khi:
    1. Cần thực hiện nhiều thao tác **thêm/xóa** phần tử, đặc biệt là ở đầu hoặc cuối danh sách.
    2. Cần triển khai các cấu trúc dữ liệu như **Queue** hoặc **Deque** (hàng đợi, ngăn xếp).
    3. Không cần truy xuất dữ liệu ngẫu nhiên thường xuyên.

- Không sử dụng `LinkedList` khi:
    1. Ứng dụng cần truy cập phần tử bằng chỉ số thường xuyên (lúc này `ArrayList` là lựa chọn tốt hơn).

---

### **5. So sánh giữa LinkedList và ArrayList**

| **Tiêu chí**              | **LinkedList**                                  | **ArrayList**                                  |
|---------------------------|------------------------------------------------|-----------------------------------------------|
| **Truy xuất (get)**        | Chậm (O(n), duyệt tuần tự qua các nút)          | Nhanh (O(1), truy xuất trực tiếp bằng chỉ số) |
| **Thêm/Xóa**              | Nhanh ở đầu/cuối danh sách (O(1))               | Chậm nếu ở giữa (do phải di chuyển phần tử)   |
| **Sử dụng bộ nhớ**         | Tốn bộ nhớ hơn (do cần lưu thêm con trỏ)        | Tiết kiệm bộ nhớ hơn                          |
| **Khi nào phù hợp?**       | Thao tác thêm/xóa nhiều, cần linh hoạt          | Truy xuất dữ liệu ngẫu nhiên thường xuyên     |

---

### **Kết luận**

`LinkedList` là cấu trúc dữ liệu phù hợp cho các bài toán yêu cầu nhiều thao tác thêm/xóa phần tử và ít thao tác truy xuất ngẫu nhiên. Tuy nhiên, nếu bạn cần hiệu suất cao khi truy cập theo chỉ số, `ArrayList` là lựa chọn tốt hơn.