# 1. Trạng Thái Vòng Đời của Entity trong JPA

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


# 2. Cơ chế Dirty Checking trong Hibernate

**Dirty Checking** là một cơ chế mà Hibernate sử dụng để xác định xem giá trị của một entity có thay đổi kể từ khi được lấy từ database hay không. Điều này giúp Hibernate tối ưu hóa các truy vấn database để chỉ cập nhật các trường đã thay đổi.

## Cách thức hoạt động:

1. **Lấy entity**: Khi một entity được lấy từ database, Hibernate lưu trạng thái ban đầu của entity này trong cache cấp một (session).
2. **Chỉnh sửa entity**: Sau khi lấy về, người dùng có thể thay đổi entity này.
3. **Đồng bộ trạng thái**: Trước khi thực hiện bất kỳ thao tác nào (như trước khi commit một `transaction` hoặc gọi rõ ràng `flush()`), Hibernate thực hiện quy trình "dirty checking" bằng cách so sánh trạng thái hiện tại của entity với trạng thái ban đầu được lưu trữ trong cache.
4. **Cập nhật database**: Nếu Hibernate phát hiện bất kỳ thay đổi nào, nó sẽ tạo và thực thi truy vấn SQL update tương ứng để cập nhật chỉ những trường đã thay đổi.

### Ưu điểm của “dirty checking”:
- **Tối ưu hóa**: Chỉ các trường đã chỉnh sửa được cập nhật trong database, điều này có thể cải thiện hiệu suất khi dữ liệu ít hơn được truyền giữa ứng dụng và database.
- **Tự động**: Các developer không cần phải chỉ định rõ ràng các trường đã thay đổi, Hibernate thực hiện việc này một cách tự động.

### Nhược điểm hoặc hạn chế:
- **Overhead**: Cơ chế “dirty checking” có thể thêm overhead, đặc biệt khi xử lý một số lượng lớn các entity. Trong hầu hết các trường hợp, chi phí này không đáng kể nhưng có thể trở thành vấn đề trong một số tình huống.
- **Minh bạch**: Một số developer có thể thấy cơ chế này minh bạch hơn, vì Hibernate tự động xác định các trường cần cập nhật.

Để quản lý quy trình “dirty checking” và tối ưu hóa hoạt động của nó, các developer có thể sử dụng nhiều chiến lược và annotation mà Hibernate cung cấp.

## Trạng thái Persistent trong Hibernate
Trong Hibernate, cơ chế “dirty checking” chỉ theo dõi các thay đổi trong các entity ở trạng thái “persistent” (liên kết với một session).

- **Persistent state** là trạng thái duy nhất khi “dirty checking” hoạt động. Ngay khi một đối tượng được liên kết với một session (ví dụ sau khi lưu, truy xuất hoặc đính kèm), nó sẽ ở trạng thái “persistent”.

## Ví dụ về khi nào một entity được “dirty checking” theo dõi:

1. **Sau khi truy xuất một entity**: Khi bạn truy xuất một entity bằng các phương thức như `session.get()`, `session.load()` hoặc một truy vấn HQL, entity được truy xuất sẽ tự động trở thành “persistent”.

```java
User user = session.get(User.class, userId);
user.setEmail("example@gmail.com");
user.setName("UpdatedName"); // "dirty checking" sẽ được áp dụng cho entity này.
```

Khi Hibernate thực hiện “dirty checking” (thường trước khi đóng session hoặc khi gọi rõ ràng `session.flush()`), nó nhận thấy rằng cả hai trường (name và email) đã thay đổi. Tuy nhiên, Hibernate tối ưu hóa các truy vấn và thực hiện một truy vấn SQL UPDATE duy nhất để cập nhật cả hai trường này trong database.

2. **Sau khi lưu một entity mới**: Khi bạn lưu một entity mới bằng `session.save()`, entity đó sẽ trở thành “persistent”.

```java
User newUser = new User();
session.save(newUser);
newUser.setName("NewName"); // "dirty checking" sẽ được áp dụng cho entity này.
```

3. **Khi chuyển từ trạng thái “detached” sang “persistent”**: Nếu bạn có một entity ở trạng thái detached và đính kèm lại vào session (ví dụ: bằng `session.merge()` hoặc `session.update()`), entity đó sẽ trở lại trạng thái “persistent”.

```java
session.merge(detachedUser);
detachedUser.setName("AnotherName"); // "dirty checking" sẽ được áp dụng cho entity này nếu nó trở lại "persistent".
```

Để cơ chế “dirty checking” hoạt động, session phải vẫn mở. Nếu session bị đóng, không có thay đổi nào sẽ được theo dõi cho đến khi entity trở lại trạng thái “persistent” trong một session mới hoặc cùng session.

## SpringData

Bây giờ, chúng ta đã hiểu cách hoạt động ở mức Hibernate, hãy xem các ví dụ với SpringData.

### Mã ví dụ đầy đủ minh họa các tình huống tương tác với Spring Data JPA và tính năng transaction

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. Cập nhật thông thường trong transaction (không cần lưu rõ ràng)
    @Transactional
    public void updateName(Long userId, String newName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(newName);
        // Nhờ vào dirty checking, các thay đổi sẽ được tự động lưu khi kết thúc transaction.
    }

    // 2. Lấy dữ liệu mà không có transaction (các thay đổi sẽ không được lưu tự động)
    public void nonTransactionalUpdateName(Long userId, String newName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(newName);
        // Các thay đổi sẽ không được lưu vì phương thức không nằm trong transaction.
    }
    
    // 3. Lưu rõ ràng các thay đổi trong transaction
    @Transactional
    public void explicitSaveAfterUpdate(Long userId, String newName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(newName);
        userRepository.save(user);  // Lưu rõ ràng các thay đổi, dù không cần thiết trong ngữ cảnh này.
    }
}
```

Mã này minh họa các tình huống khác nhau về tương tác với database trong bối cảnh Spring Data JPA và transaction.

Trong ví dụ `explicitSaveAfterUpdate`, việc lưu rõ ràng là không cần thiết vì cơ chế "dirty checking" của Hibernate sẽ tự động đồng bộ các thay đổi với database khi kết thúc `transaction`.

### Kết luận
Bọc một phương thức trong `@Transactional` là cách dễ dàng nhất để đảm bảo cơ chế “dirty checking” của Hibernate hoạt động. Khi một phương thức được bọc trong `@Transactional`, các entity được truy xuất hoặc lưu trong phương thức đó tự động trở thành một phần của Hibernate Session (hoặc JPA EntityManager) và ở trạng thái “managed” hoặc “persistent”.


# 3. Tóm tắt về Optimistic Locking và Pessimistic Locking trong Hibernate

### Optimistic Locking
**Khái niệm**: Phương pháp này dựa trên giả định rằng xung đột dữ liệu hiếm khi xảy ra. Thay vì lock dữ liệu, nó chỉ kiểm tra khi dữ liệu thực sự được cập nhật, thường sử dụng một cột `version` trong entity.

**Ví dụ với Spring Data**:

```java
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String name;
    private double price;
}

public interface ProductRepository extends JpaRepository<Product, Long> {}

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void updatePrice(Long id, double newPrice) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setPrice(newPrice);
        productRepository.save(product);
    }
}
```

Trong ví dụ trên, khi hai luồng đồng thời cố gắng update giá của cùng một sản phẩm, luồng đầu tiên sẽ thành công, còn luồng thứ hai sẽ gặp lỗi `ObjectOptimisticLockingFailureException` do không khớp version.

**SQL sinh ra cho phương thức `updatePrice`**:

```sql
SELECT id, name, price, version FROM product WHERE id = ?
UPDATE product SET name = ?, price = ?, version = ? WHERE id = ? AND version = ?
```

- **Read operations** (như `findById`) không chặn (non-blocking) và có thể thực hiện song song mà không kiểm tra cột version.
- **Write operations** (như `save`) sẽ kiểm tra cột version để đảm bảo dữ liệu chưa thay đổi từ lần read trước.

Trong các trường hợp cần đảm bảo rằng dữ liệu read là mới nhất, cần sử dụng chiến lược Pessimistic Locking.

---

### Pessimistic Locking
**Khái niệm**: Phương pháp này giả định rằng tranh chấp dữ liệu có khả năng xảy ra, vì vậy sẽ lock dữ liệu trong suốt thời gian của một transaction để ngăn chặn truy cập từ các transaction khác.

**Ví dụ với Spring Data**:

```java
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
}

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findByIdLocked(Long id);
}

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void updatePrice(Long id, double newPrice) {
        Product product = productRepository.findByIdLocked(id)
            .orElseThrow(EntityNotFoundException::new);
        product.setPrice(newPrice);
    }
}
```

Annotation `@Lock(LockModeType.PESSIMISTIC_WRITE)` đảm bảo rằng một write lock sẽ được áp dụng khi `findByIdLocked` được gọi. Sử dụng `@Transactional` đảm bảo tính toàn vẹn của các thao tác trong transaction.

**SQL sinh ra cho phương thức `updatePrice`**:

```sql
SELECT id, name, price FROM product WHERE id = ? FOR UPDATE
UPDATE product SET name = ?, price = ? WHERE id = ?
```

- Phương pháp này ngăn chặn xung đột truy cập dữ liệu đồng thời bằng cách lock dữ liệu cho đến khi transaction hoàn tất.

---

### Kết luận
Trong Spring Data JPA, có hai chiến lược locking chính:

- **@Transactional + @Lock(LockModeType.PESSIMISTIC_WRITE)**: Đảm bảo tính nhất quán nghiêm ngặt và ngăn xung đột, nhưng có thể làm giảm throughput.
- **@Version**: Sử dụng Optimistic Locking, chỉ kiểm tra version khi ghi, giúp tăng throughput khi xung đột dữ liệu ít xảy ra.

Lựa chọn giữa hai phương pháp dựa trên tần suất truy cập đồng thời dự kiến và mức độ cần thiết về tính nhất quán của dữ liệu.
