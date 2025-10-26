# TM-Alerting: Transaction Monitoring & Alerting API

Đây là một API backend được xây dựng bằng Java & Spring Boot cho một hệ thống giám sát giao dịch ngân hàng mô phỏng. Ứng dụng có khả năng xử lý các giao dịch, áp dụng các quy tắc để phát hiện hoạt động đáng ngờ, và tạo ra các cảnh báo để nhân viên có thể xem xét.

## 🚀 Live Demo

Toàn bộ tài liệu và khả năng tương tác trực tiếp với API đều có sẵn thông qua Swagger UI.

**URL:** **https://tm-alerting.onrender.com/swagger-ui.html**

_(Lưu ý: Tên miền `tm-alerting.onrender.com` là ví dụ, bạn hãy thay thế bằng URL ứng dụng của bạn trên Render. Dịch vụ trên gói miễn phí có thể cần 30-60 giây để khởi động nếu không có ai truy cập.)_

---

## ✨ Tính năng chính

-   **Xác thực & Phân quyền:** Hệ thống đăng ký/đăng nhập dựa trên JWT (JSON Web Tokens).
-   **Quản lý Giao dịch:** Các endpoint để tạo và truy vấn lịch sử giao dịch.
-   **Hệ thống Cảnh báo:** Tự động tạo cảnh báo dựa trên các quy tắc nghiệp vụ (ví dụ: giao dịch có giá trị cao).
-   **Quản lý Cảnh báo:** Cho phép người dùng (nhân viên) xem, lọc và cập nhật trạng thái của các cảnh báo.
-   **Audit Log:** Ghi lại các hành động quan trọng trong hệ thống.
-   **Tài liệu API tự động:** Tích hợp Swagger UI để cung cấp tài liệu API trực quan và tương tác.

---

## 🛠️ Công nghệ sử dụng

-   **Backend:**
    -   Java 21
    -   Spring Boot 3
    -   Spring Security (JWT Authentication)
    -   Spring Data MongoDB
-   **Database:** MongoDB
-   **Build/Dependency:** Maven
-   **Deployment:** Docker
-   **API Documentation:** springdoc-openapi

---

## 🏁 Hướng dẫn cài đặt và chạy Local

### Yêu cầu
-   JDK 21
-   Maven
-   Docker

### Các bước thực hiện

1.  **Clone repository:**
    ```bash
    git clone <your-repository-url>
    cd tm-alerting
    ```

2.  **Khởi động MongoDB bằng Docker:**
    Cách dễ nhất để có một database local là chạy MongoDB trong Docker.
    ```bash
    docker run -d -p 27017:27017 --name mongo-dev mongo:latest
    ```

3.  **Tạo file môi trường `.env`:**
    Tạo một file tên là `.env` ở thư mục gốc của project bằng cách sao chép từ file `.env.example`.
    ```bash
    # Trên Windows (Command Prompt)
    copy .env.example .env
    ```
    File `.env.example` đã có sẵn giá trị cho `MONGO_URI_DEV` để kết nối tới Docker container ở trên.

4.  **Cấu hình biến môi trường:**
    Mở file `.env` và điền một giá trị bí mật cho `JWT_SECRET`.
    ```
    # Ví dụ trong file .env
    MONGO_URI_DEV=mongodb://localhost:27017/tmalerting_dev
    JWT_SECRET=DayLaChuoiBiMatSieuDaiSieuAnToanCuaBan_DungChepCuaToi
    ```

5.  **Chạy ứng dụng:**
    Sử dụng Maven wrapper để khởi động ứng dụng với profile `dev`.
    ```bash
    # Trên Windows
    mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
    ```
    Ứng dụng sẽ chạy tại `http://localhost:8080`.

---

## 📖 API Documentation

Tài liệu chi tiết về tất cả các API endpoint có thể được truy cập tại Swagger UI sau khi bạn khởi động ứng dụng:

`http://localhost:8080/swagger-ui.html`