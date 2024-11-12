
# Trạng Thái Vòng Đời của Entity trong JPA

Khi làm việc với Java Persistence API (JPA) và các triển khai phổ biến như Hibernate hoặc EclipseLink, điều quan trọng là phải hiểu các trạng thái vòng đời khác nhau của các entity trong JPA. Mỗi trạng thái có hành vi riêng biệt và xác định cách một entity tương tác với context của nó. Dưới đây là tổng quan và ví dụ cho từng trạng thái.

### 1. Các Trạng Thái Chính của Entity

Các entity trong JPA có thể tồn tại ở bốn trạng thái chính, mỗi trạng thái ảnh hưởng đến cách chúng tương tác với context và khi nào chúng cần các phương thức gọi rõ ràng để tương tác với cơ sở dữ liệu.

- **New**: Trạng thái này áp dụng khi entity lần đầu tiên được tạo (ví dụ: với `new User()`). Entity này chưa được liên kết với context, vì vậy cần gọi `save()` rõ ràng để lưu vào cơ sở dữ liệu.
  ```java
  public User createUser(String name) {
      User user = new User();
      user.setName(name);
      return userRepository.save(user); // Lưu entity mới vào cơ sở dữ liệu.
  }
  ```

- **Managed**: Entity ở trạng thái managed được liên kết với context. Bất kỳ thay đổi nào được thực hiện sẽ tự động được đồng bộ hóa với cơ sở dữ liệu khi `transaction` hoàn tất — không cần gọi `save()` sau khi cập nhật.
  ```java
  public User updateUser(Long id, String newName) {
      User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
      user.setName(newName); // Thay đổi tự động lưu khi `transaction` kết thúc.
      return user;
  }
  ```

- **Detached**: Khi một entity bị tách khỏi context, nó không còn được theo dõi nữa. Điều này có thể xảy ra sau khi `transaction` kết thúc, khi `EntityManager` đóng, hoặc khi gọi rõ ràng `detach()`. Các entity Detached cần được liên kết lại (ví dụ: bằng cách gọi `save()` hoặc `merge()`) để lưu các thay đổi.
  ```java
  @Transactional(readOnly = true)
  public User loadUser(Long id) {
      return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public void demonstrateDetachedLifecycle(Long userId) {
      User user = loadUser(userId); // Trạng thái Managed trong `transaction`.

      // Sau khi `transaction` kết thúc, `user` trở thành Detached.

      saveEditedUser(user); // Liên kết lại `user` với context.
  }
  ```

- **Removed**: Trạng thái này cho biết entity sẽ bị xóa, và sẽ bị xóa khỏi cơ sở dữ liệu sau khi `transaction` hoàn tất.
  ```java
  public void deleteUser(Long id) {
      User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
      userRepository.delete(user); // Xóa entity khi `transaction` kết thúc.
  }
  ```

### 2. Các Quy Tắc Sử Dụng `save()` và `delete()`

Hiểu khi nào sử dụng các phương thức như `save()` và `delete()` giúp tránh các vấn đề phổ biến:

- **Khi nào cần gọi `save()`**:
    - Với các entity mới (trạng thái New) để lưu vào cơ sở dữ liệu.
    - Với các entity Detached để liên kết lại và lưu các thay đổi.

- **Khi nào không cần gọi `save()`**:
    - Với các entity đang ở trạng thái Managed vì chúng tự động đồng bộ với cơ sở dữ liệu.

- **Rủi ro khi gọi `save()` không cần thiết**:
    - **Hiệu suất**: Các lệnh `save` không cần thiết có thể làm tăng thêm các thao tác với cơ sở dữ liệu.
    - **Cập nhật không mong muốn**: Lưu một entity chưa được thay đổi có thể ghi đè các trường không thay đổi.
    - **Xung đột phiên bản**: Gọi `save` quá mức có thể gây ra xung đột nếu có sử dụng cơ chế phiên bản (ví dụ: `@Version`).

Hiểu các trạng thái này và sử dụng `save()` hợp lý giúp đảm bảo tương tác mượt mà với cơ sở dữ liệu và tối ưu hóa hiệu suất của ứng dụng.
