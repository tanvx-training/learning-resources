# Checklist Chi Tiết Làm Rõ Yêu Cầu Dự Án

**Hướng dẫn:** Sử dụng checklist này khi bạn nhận được tài liệu yêu cầu cho một chức năng hoặc dự án mới. Đánh dấu vào ô checkbox khi bạn đã thực hiện hoặc tìm hiểu xong. Ghi chú lại trạng thái hoặc kết quả vào phần "Trạng thái/Ghi chú". Mục tiêu là đảm bảo bạn hiểu rõ yêu cầu trước khi bắt đầu code.

---

## I. Tổng quan & Phạm vi

*   [ ] **Câu hỏi:** Mục tiêu chính của chức năng/dự án này là gì? Nó giải quyết vấn đề gì cho ai?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Ai là người dùng cuối (end-users) của chức năng này? Họ sẽ sử dụng nó trong bối cảnh nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Phạm vi công việc cụ thể của tôi trong chức năng/dự án này bao gồm những phần nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có những tài liệu nào khác (SRS, FRS, Use Case, User Story, BRD, UI/UX Mockup...) liên quan đến chức năng này mà tôi cần đọc?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có thuật ngữ chuyên ngành (domain-specific terms) nào tôi chưa hiểu rõ không?
    *   `Trạng thái/Ghi chú:`

## II. Yêu cầu Chức năng (Functional Requirements)

*   [ ] **Câu hỏi:** Luồng hoạt động chính (happy path) của chức năng này diễn ra như thế nào từng bước?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Dữ liệu đầu vào (Inputs) cho chức năng này là gì? Bao gồm những trường nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Định dạng, kiểu dữ liệu, và các ràng buộc (validation rules) cho từng trường dữ liệu đầu vào là gì? (Ví dụ: bắt buộc, độ dài min/max, định dạng email, chỉ nhập số...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Dữ liệu đầu ra (Outputs) của chức năng này là gì? Hiển thị/trả về những thông tin nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Định dạng của dữ liệu đầu ra là gì?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Các bước xử lý logic chính bên trong chức năng này là gì?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có các quy tắc nghiệp vụ (business rules) cụ thể nào cần áp dụng không? (Ví dụ: công thức tính toán, điều kiện logic phức tạp, quy trình phê duyệt...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Chức năng này có cần tương tác (gọi hoặc được gọi bởi) các chức năng/module/service khác trong hệ thống không? Nếu có, tương tác như thế nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có cần tích hợp với hệ thống bên thứ ba (third-party systems) không? Nếu có, sử dụng API nào? Tài liệu API đã có chưa?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có trường hợp sử dụng đặc biệt (edge cases) nào cần xem xét không?
    *   `Trạng thái/Ghi chú:`

## III. Yêu cầu Phi chức năng (Non-Functional Requirements)

*   [ ] **Câu hỏi:** Có yêu cầu cụ thể về hiệu năng (performance) không? (Ví dụ: thời gian phản hồi tối đa, số lượng người dùng đồng thời, throughput...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu cụ thể về bảo mật (security) không? (Ví dụ: yêu cầu xác thực, phân quyền truy cập theo vai trò, mã hóa dữ liệu nhạy cảm, chống tấn công XSS/CSRF...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu về khả năng sử dụng (usability) không? (Thường liên quan đến UI/UX)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu về khả năng bảo trì (maintainability) hoặc coding convention cụ thể không?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Yêu cầu về ghi log (logging) như thế nào? Cần log những thông tin gì, ở mức độ nào (DEBUG, INFO, ERROR)? Định dạng log?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu về giám sát (monitoring) hệ thống không?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Môi trường triển khai (deployment environment) dự kiến là gì? Có yêu cầu đặc biệt nào về môi trường không?
    *   `Trạng thái/Ghi chú:`

## IV. Giao diện Người dùng (UI/UX)

*   [ ] **Câu hỏi:** Đã có thiết kế UI/UX (Mockup, Prototype, Wireframe) cho chức năng này chưa?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Thiết kế UI/UX có khớp hoàn toàn với mô tả yêu cầu chức năng không? Nếu có điểm khác biệt, điểm nào là đúng?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Hoạt động chi tiết của từng thành phần trên giao diện (nút bấm, form nhập liệu, bảng dữ liệu, dropdown...) như thế nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Các quy tắc validation phía client (trình duyệt) là gì?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Nội dung và hình thức hiển thị các thông báo lỗi, cảnh báo, thành công trên giao diện như thế nào?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu về thiết kế đáp ứng (responsive design) để tương thích với các kích thước màn hình khác nhau (desktop, tablet, mobile) không?
    *   `Trạng thái/Ghi chú:`

## V. Dữ liệu (Data)

*   [ ] **Câu hỏi:** Cấu trúc dữ liệu nào (bảng database, collection, data models...) cần được tạo mới hoặc sửa đổi cho chức năng này?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Chi tiết các trường dữ liệu là gì? (Tên trường, kiểu dữ liệu, có null được không, giá trị mặc định, khóa chính, khóa ngoại, index...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Luồng dữ liệu (data flow) của chức năng này như thế nào? Dữ liệu được tạo/đọc/cập nhật/xóa (CRUD) ở những bước nào, ở đâu?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có yêu cầu về di chuyển dữ liệu cũ (data migration) sang cấu trúc mới không?
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Có cần chuẩn bị dữ liệu mẫu (sample data/seed data) cho việc phát triển và kiểm thử không?
    *   `Trạng thái/Ghi chú:`

## VI. Xử lý Lỗi & Ngoại lệ (Error & Exception Handling)

*   [ ] **Câu hỏi:** Liệt kê các trường hợp lỗi hoặc ngoại lệ có thể xảy ra trong quá trình thực hiện chức năng? (Ví dụ: người dùng nhập sai dữ liệu, mất kết nối mạng, tài nguyên không tìm thấy, lỗi hệ thống khác...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Hệ thống cần phản ứng như thế nào đối với từng trường hợp lỗi/ngoại lệ đã liệt kê? (Ví dụ: hiển thị thông báo lỗi cụ thể cho người dùng, ghi log chi tiết, thực hiện rollback transaction, thử lại thao tác...)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Nội dung thông báo lỗi hiển thị cho người dùng cuối cần như thế nào? (Rõ ràng, thân thiện, không tiết lộ thông tin nhạy cảm)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Câu hỏi:** Nội dung log lỗi ghi lại cho đội phát triển/vận hành cần như thế nào? (Chi tiết, có stack trace, context...)
    *   `Trạng thái/Ghi chú:`

## VII. Giao tiếp & Xác nhận

*   [ ] **Câu hỏi:** Ai là người phù hợp nhất để tôi hỏi và làm rõ các điểm còn vướng mắc về yêu cầu này? (BA, PM, Tech Lead, Khách hàng?)
    *   `Trạng thái/Ghi chú:`
*   [ ] **Hành động:** Liệt kê tất cả các điểm chưa rõ ràng, mâu thuẫn, thiếu sót đã phát hiện ra trong quá trình phân tích.
    *   `Trạng thái/Ghi chú:`
*   [ ] **Hành động:** Chuẩn bị các câu hỏi cụ thể, rõ ràng cho từng điểm cần làm rõ.
    *   `Trạng thái/Ghi chú:`
*   [ ] **Hành động:** Liên hệ và trao đổi với người phụ trách để làm rõ các câu hỏi.
    *   `Trạng thái/Ghi chú:`
*   [ ] **Hành động:** Ghi lại các câu trả lời và nội dung đã thống nhất (qua email, comment trong task, biên bản họp...).
    *   `Trạng thái/Ghi chú:`
*   [ ] **Hành động:** Gửi lại bản tóm tắt nội dung đã thống nhất để xác nhận lại với người trả lời.
    *   `Trạng thái/Ghi chú:`
*   [ ] **Yêu cầu đạt được:** Tất cả các điểm vướng mắc đã được giải đáp và thống nhất. Tôi hoàn toàn tự tin về sự hiểu biết của mình đối với yêu cầu này.
    *   `Trạng thái/Ghi chú:`

---

**Lưu ý:** Checklist này là một gợi ý, bạn có thể điều chỉnh, thêm bớt các mục cho phù hợp với từng dự án và chức năng cụ thể.
