# Tổng hợp và Phân tích Thông tin cho Hướng dẫn Làm rõ Yêu cầu

## Mục tiêu:
Cung cấp hướng dẫn thực tế cho lập trình viên Java về cách làm rõ yêu cầu dự án khi nhận tài liệu, dựa trên thông tin từ các nguồn đã tham khảo (Viblo, CodeStar).

## Các điểm chính cần đưa vào hướng dẫn:

**1. Giới thiệu - Tại sao làm rõ yêu cầu lại quan trọng với Lập trình viên?**
*   Đảm bảo xây dựng đúng sản phẩm, đúng chức năng theo mong muốn của khách hàng/người dùng.
*   Tránh lãng phí thời gian và công sức sửa lỗi hoặc làm lại các chức năng bị hiểu sai.
*   Phát hiện sớm các vấn đề tiềm ẩn (mâu thuẫn, thiếu sót) trong yêu cầu giúp tiết kiệm chi phí (lỗi phát hiện muộn rất tốn kém).
*   Giúp lập trình viên đóng góp ý kiến cải tiến, nâng cao chất lượng sản phẩm.
*   Là cơ sở để ước tính công việc và lập kế hoạch chính xác hơn.

**2. Hiểu các loại tài liệu yêu cầu thường gặp:**
*   **SRS (Software Requirement Specification):** Tài liệu chi tiết nhất, mô tả cả yêu cầu chức năng và phi chức năng.
*   **FRS (Function Requirement Specification):** Tập trung mô tả chi tiết các chức năng hệ thống cần làm.
*   **Use Cases:** Mô tả các kịch bản tương tác giữa người dùng (hoặc hệ thống khác) với hệ thống.
*   **User Stories:** Mô tả ngắn gọn mong muốn của người dùng về một tính năng (Thường theo format: "Là một [vai trò], tôi muốn [làm gì đó] để [đạt được mục tiêu]").
*   **UI/UX Mockups/Prototypes:** Thiết kế giao diện và luồng trải nghiệm người dùng.
*   **BRD (Business Requirement Document):** Tài liệu cấp cao hơn, mô tả mục tiêu kinh doanh, phạm vi dự án.
*   *(Lưu ý: Lập trình viên cần biết mình đang đọc loại tài liệu nào để hiểu mức độ chi tiết và mục đích của nó)*

**3. Quy trình làm rõ yêu cầu cho Lập trình viên:**
*   **Bước 1: Đọc tổng quan:**
    *   Nắm bắt mục tiêu chính của dự án/chức năng.
    *   Xác định phạm vi công việc của mình.
    *   Hiểu bối cảnh chung, lĩnh vực nghiệp vụ (domain).
*   **Bước 2: Phân tích chi tiết:**
    *   Đọc kỹ từng phần, từng yêu cầu liên quan đến công việc.
    *   Phân biệt yêu cầu chức năng (Functional - System *does what?*) và phi chức năng (Non-functional - System *is how?* e.g., performance, security, usability).
    *   Đối chiếu thông tin giữa các tài liệu khác nhau (VD: SRS khớp với Use Case và UI không?).
    *   Mường tượng luồng xử lý dữ liệu (Data flow) và luồng người dùng (User flow).
*   **Bước 3: Xác định điểm chưa rõ ràng, mâu thuẫn, thiếu sót:**
    *   **Chưa rõ ràng (Ambiguity):** Thuật ngữ khó hiểu, mô tả mơ hồ, có nhiều cách hiểu.
    *   **Thiếu sót (Incompleteness):** Thiếu mô tả cho các trường hợp đặc biệt (edge cases), luồng lỗi (error handling), giá trị mặc định, ràng buộc dữ liệu (validation rules).
    *   **Mâu thuẫn (Contradiction):** Các yêu cầu trái ngược nhau trong cùng một tài liệu hoặc giữa các tài liệu khác nhau.
    *   **Giả định ngầm (Implicit Assumptions):** Những điều người viết tài liệu cho là hiển nhiên nhưng không nêu rõ.
*   **Bước 4: Chuẩn bị câu hỏi:**
    *   Liệt kê các điểm cần làm rõ từ Bước 3.
    *   Soạn câu hỏi cụ thể, đi thẳng vào vấn đề.
    *   Sử dụng các dạng câu hỏi: "Nếu... thì sao?" (What if scenarios), "Có thể cho ví dụ cụ thể về...?", "Yêu cầu này có nghĩa là...?", "Tôi hiểu X theo cách Y, có đúng không?", "Trường hợp Z thì xử lý thế nào?"
    *   Nhóm các câu hỏi liên quan lại với nhau.
*   **Bước 5: Giao tiếp và xác nhận:**
    *   Xác định đúng người cần hỏi (BA - Business Analyst, PM - Project Manager, Tech Lead, Khách hàng?).
    *   Chọn kênh giao tiếp phù hợp (họp trực tiếp/online, email, chat, công cụ quản lý dự án - Jira, Redmine...). Ưu tiên các kênh có lưu lại lịch sử trao đổi.
    *   Trình bày câu hỏi rõ ràng, mạch lạc.
    *   Lắng nghe kỹ câu trả lời, hỏi lại nếu chưa rõ.
    *   **Quan trọng:** Ghi lại các câu trả lời và xác nhận lại sự hiểu biết của mình để đảm bảo tất cả các bên đều thống nhất.

**4. Một số thách thức thường gặp và cách xử lý:**
*   **Yêu cầu thay đổi liên tục:** Hiểu quy trình quản lý thay đổi của dự án. Khi có thay đổi, cần phân tích ảnh hưởng và xác nhận lại.
*   **Khó khăn trong giao tiếp:** Sử dụng ngôn ngữ đơn giản, tránh thuật ngữ kỹ thuật quá sâu khi nói chuyện với người không chuyên về kỹ thuật. Có thể dùng hình vẽ, sơ đồ để minh họa. Luôn xác nhận lại để đảm bảo hai bên hiểu đúng ý nhau.
*   **Tự đưa ra giả định:** Tránh việc này bằng cách luôn đặt câu hỏi khi có bất kỳ điều gì không chắc chắn.

**5. Kết luận:**
*   Nhấn mạnh lại việc làm rõ yêu cầu là trách nhiệm của lập trình viên, không chỉ của BA hay PM.
*   Khuyến khích sự chủ động đặt câu hỏi.
*   Việc làm rõ yêu cầu sớm giúp dự án thành công và công việc của lập trình viên hiệu quả hơn.

## Nguồn tham khảo chính:
*   https://viblo.asia/p/cong-viec-phan-tich-yeu-cau-trong-mot-du-an-phan-mem-5WQvzgxxRk3E
*   https://codestar.vn/cac-loai-tai-lieu-yeu-cau-trong-du-an-phan-mem/

