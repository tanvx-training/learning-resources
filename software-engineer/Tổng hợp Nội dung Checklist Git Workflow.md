# Tổng hợp Nội dung Checklist Git Workflow

## I. Cài đặt & Cấu hình Ban đầu

*   **Quy tắc:** Cấu hình tên và email Git toàn cục khớp với tài khoản Git hosting (GitHub, GitLab, Bitbucket...). 
    *   Lệnh: `git config --global user.name "Tên Của Bạn"`
    *   Lệnh: `git config --global user.email "email@example.com"`
*   **Quy tắc:** Thiết lập xác thực SSH Key thay vì HTTPS để tránh nhập mật khẩu/token liên tục và tăng bảo mật.
    *   Lưu ý: Tham khảo tài liệu của Git hosting để tạo và thêm SSH key.
*   **Quy tắc:** Tạo file `.gitignore` trong thư mục gốc của dự án để loại bỏ các file không cần thiết (file build, log, file cấu hình local, thư mục dependencies...). 
    *   Lưu ý: Sử dụng các template `.gitignore` có sẵn cho ngôn ngữ/framework của bạn (ví dụ: từ gitignore.io).
*   **Quy tắc:** Cấu hình editor mặc định cho Git (ví dụ: VS Code, Vim) nếu cần.
    *   Lệnh: `git config --global core.editor "code --wait"` (cho VS Code)

## II. Branching Strategy (Chiến lược Nhánh - Ví dụ: Gitflow-like)

*   **Quy tắc:** Nhánh `main` (hoặc `master`) luôn chứa code ổn định, sẵn sàng cho production. **KHÔNG BAO GIỜ** commit trực tiếp lên `main`.
*   **Quy tắc:** Nhánh `develop` là nhánh tích hợp chính, chứa code đã hoàn thành các feature và chờ release. Đây là nhánh cơ sở cho các feature branch.
*   **Quy tắc:** Tạo nhánh mới từ `develop` cho mỗi feature/task mới. Đặt tên nhánh rõ ràng.
    *   Lệnh: `git checkout develop`
    *   Lệnh: `git pull origin develop` (Luôn lấy code mới nhất từ `develop` trước khi tạo nhánh mới)
    *   Lệnh: `git checkout -b feature/ten-feature-ngan-gon` (Ví dụ: `feature/JIRA-123-user-login`)
*   **Quy tắc:** Nhánh `release/version` được tạo từ `develop` khi chuẩn bị cho release. Chỉ chứa bug fix liên quan đến release.
*   **Quy tắc:** Nhánh `hotfix/issue` được tạo từ `main` để sửa lỗi khẩn cấp trên production. Sau khi fix xong phải merge lại vào cả `main` và `develop`.
*   **Lưu ý:** Tuân thủ quy ước đặt tên nhánh của dự án (nếu có).

## III. Quy tắc Commit

*   **Quy tắc:** Commit thường xuyên với những thay đổi nhỏ, logic, hoàn chỉnh một phần công việc.
    *   Lưu ý: Tránh commit những thay đổi quá lớn, khó review.
*   **Quy tắc:** Viết commit message rõ ràng, súc tích, theo quy ước chung (ví dụ: Conventional Commits).
    *   Format: `<type>[optional scope]: <description>` (Ví dụ: `feat: add user login API`, `fix(auth): resolve password reset bug`, `docs: update README installation guide`)
    *   `<type>`: `feat` (tính năng mới), `fix` (sửa lỗi), `docs` (tài liệu), `style` (format code), `refactor` (tái cấu trúc code), `test` (thêm/sửa test), `chore` (công việc khác không ảnh hưởng code chạy: build, dependencies...).
    *   Sử dụng thì hiện tại, dạng mệnh lệnh (Ví dụ: "Add feature" thay vì "Added feature").
*   **Quy tắc:** Tham chiếu đến ID của task/issue (nếu có) trong commit message.
    *   Ví dụ: `feat: implement user profile page (JIRA-123)`
*   **Quy tắc:** Kiểm tra lại các thay đổi trước khi commit.
    *   Lệnh: `git status` (Xem file nào đã thay đổi)
    *   Lệnh: `git diff` (Xem chi tiết thay đổi chưa stage)
    *   Lệnh: `git diff --staged` (Xem chi tiết thay đổi đã stage)
*   **Quy tắc:** Chỉ commit những file liên quan đến thay đổi đang thực hiện.
    *   Lệnh: `git add <file>` (Stage từng file cụ thể)
    *   Lệnh: `git add .` (Stage tất cả thay đổi - **cẩn thận**)
    *   Lệnh: `git add -p` (Stage từng phần thay đổi trong file - rất hữu ích)
*   **Lệnh:** `git commit -m "feat: implement user login"`

## IV. Pull Request (PR) / Merge Request (MR)

*   **Quy tắc:** Đảm bảo nhánh feature của bạn đã được cập nhật code mới nhất từ nhánh gốc (`develop`).
    *   Lệnh: `git checkout feature/ten-feature`
    *   Lệnh: `git pull origin develop` (Giải quyết conflict nếu có)
*   **Quy tắc:** Push nhánh feature lên remote repository.
    *   Lệnh: `git push origin feature/ten-feature`
*   **Quy tắc:** Tạo PR/MR từ nhánh feature vào nhánh `develop` (hoặc nhánh đích theo quy trình dự án).
*   **Quy tắc:** Viết tiêu đề và mô tả PR/MR rõ ràng:
    *   Mô tả mục đích của PR (What & Why).
    *   Các thay đổi chính đã thực hiện.
    *   Cách để kiểm thử (How to test).
    *   Đính kèm ảnh chụp màn hình/GIF nếu có thay đổi UI.
    *   Tham chiếu đến task/issue liên quan.
*   **Quy tắc:** Gán reviewer phù hợp (theo quy định của team).
*   **Quy tắc:** Theo dõi và phản hồi các comment review kịp thời. Push thêm commit để sửa đổi nếu cần.
*   **Quy tắc:** Sau khi PR/MR được approve và merge, xóa nhánh feature trên remote (thường có tùy chọn tự động) và local.
    *   Lệnh (local): `git checkout develop`, `git pull origin develop`, `git branch -d feature/ten-feature`

## V. Giải quyết Conflict (Xung đột)

*   **Nguyên nhân:** Xảy ra khi merge 2 nhánh có thay đổi trên cùng một dòng code, hoặc khi một nhánh sửa file mà nhánh kia đã xóa.
*   **Quy trình xử lý:**
    1.  **Cập nhật nhánh đích:** `git checkout feature/my-feature`, `git pull origin develop` (hoặc nhánh gốc gây conflict).
    2.  **Xác định file conflict:** Git sẽ báo lỗi và đánh dấu các file bị conflict.
    3.  **Mở file conflict:** Tìm các đoạn được đánh dấu bởi `<<<<<<< HEAD`, `=======`, `>>>>>>> ten-nhanh-khac`.
    4.  **Chỉnh sửa thủ công:** Giữ lại phần code mong muốn từ cả hai phía, xóa các dấu conflict (`<<<<<<<`, `=======`, `>>>>>>>`). Đảm bảo logic code sau khi sửa là đúng.
    5.  **Stage file đã sửa:** `git add <file-da-sua-conflict>`.
    6.  **Commit kết quả merge:** `git commit` (Git thường tự tạo commit message dạng "Merge branch 'develop' into feature/my-feature", có thể giữ nguyên hoặc sửa lại nếu cần).
    7.  **Kiểm thử kỹ:** Đảm bảo chức năng hoạt động đúng sau khi giải quyết conflict.
*   **Lưu ý:** Luôn `pull` code mới nhất từ nhánh gốc trước khi bắt đầu code hoặc trước khi push/tạo PR để giảm thiểu conflict.

## VI. Các Tình huống Thường Gặp & Cách Giải Quyết

*   **Tình huống:** Muốn sửa commit gần nhất (chưa push).
    *   **Sửa commit message:** `git commit --amend -m "New correct message"`
    *   **Thêm thay đổi vào commit:** `git add <file-moi>`, `git commit --amend --no-edit`
*   **Tình huống:** Muốn hủy commit gần nhất (chưa push).
    *   **Hủy commit, giữ lại thay đổi (staged):** `git reset --soft HEAD~1`
    *   **Hủy commit, giữ lại thay đổi (unstaged):** `git reset HEAD~1`
    *   **Hủy commit, xóa bỏ hoàn toàn thay đổi:** `git reset --hard HEAD~1` (**CỰC KỲ CẨN THẬN!** Mất dữ liệu không thể khôi phục).
*   **Tình huống:** Lỡ commit vào sai nhánh (ví dụ: commit vào `develop` thay vì `feature/my-feature`, commit chưa push).
    1.  `git log` (Lấy commit hash của commit sai).
    2.  `git checkout feature/my-feature` (Chuyển sang nhánh đúng).
    3.  `git cherry-pick <commit-hash-cua-commit-sai>` (Áp dụng commit đó vào nhánh đúng).
    4.  `git checkout develop` (Quay lại nhánh sai).
    5.  `git reset --hard HEAD~1` (Xóa commit sai khỏi nhánh `develop` - **CẨN THẬN**).
*   **Tình huống:** Muốn hủy một commit đã push lên remote.
    *   **KHÔNG BAO GIỜ** dùng `git reset --hard` trên nhánh đã chia sẻ.
    *   **Cách đúng:** Tạo một commit mới để hoàn tác thay đổi của commit cũ.
        *   Lệnh: `git revert <commit-hash-can-huy>`
        *   Git sẽ tạo một commit mới với nội dung ngược lại commit cần hủy. Push commit revert này lên.
*   **Tình huống:** Muốn tạm thời cất các thay đổi đang làm dở để chuyển sang nhánh khác.
    *   **Cất thay đổi:** `git stash` hoặc `git stash save "Mô tả ngắn"`
    *   **Xem danh sách stash:** `git stash list`
    *   **Áp dụng lại thay đổi gần nhất (và xóa khỏi stash):** `git stash pop`
    *   **Áp dụng lại thay đổi gần nhất (và giữ trong stash):** `git stash apply`
    *   **Áp dụng stash cụ thể:** `git stash apply stash@{index}`
    *   **Xóa stash:** `git stash drop stash@{index}`
*   **Tình huống:** Cập nhật nhánh `main`/`develop` từ remote.
    *   Lệnh: `git checkout main`
    *   Lệnh: `git pull origin main`
*   **Tình huống:** Xóa nhánh local đã được merge.
    *   Lệnh: `git branch -d ten-nhanh-da-merge`
*   **Tình huống:** Buộc xóa nhánh local chưa merge (chắc chắn không cần nữa).
    *   Lệnh: `git branch -D ten-nhanh-chua-merge` (**CẨN THẬN**)

## VII. Lưu ý Chung

*   [ ] **Luôn `pull` trước khi `push`:** `git pull origin <ten-nhanh>` để lấy code mới nhất và giải quyết conflict (nếu có) ở local trước khi đẩy lên.
*   [ ] **Giao tiếp với team:** Hiểu rõ workflow và quy tắc Git của dự án/team. Hỏi khi không chắc chắn.
*   [ ] **Sử dụng công cụ hỗ trợ:** Các Git client (SourceTree, GitKraken, VS Code Git extension...) có thể giúp trực quan hóa và thực hiện các thao tác dễ dàng hơn, đặc biệt khi mới bắt đầu.
*   [ ] **Đừng sợ thử nghiệm (trên nhánh riêng):** Tạo nhánh thử nghiệm để học các lệnh Git phức tạp trước khi áp dụng vào nhánh chính.
*   [ ] **Hiểu sự khác biệt `fetch` và `pull`:** `git fetch` chỉ tải thông tin từ remote về, không tự merge. `git pull` = `git fetch` + `git merge`.
*   [ ] **Backup thường xuyên (nếu cần):** Mặc dù Git là hệ thống phân tán, việc có backup riêng cho các dự án quan trọng không bao giờ thừa.
