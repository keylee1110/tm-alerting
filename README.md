# Transaction Monitoring & Alerting System

Một hệ thống full-stack mô phỏng quy trình giám sát giao dịch tài chính, được xây dựng cho các đội ngũ tuân thủ (compliance). Ứng dụng có khả năng xử lý giao dịch, tự động tạo cảnh báo cho các hoạt động đáng ngờ, và cung cấp một giao diện web để người dùng quản lý và xử lý các cảnh báo đó.

## 🚀 Live Demo & Hướng Dẫn Test

Bạn có thể trải nghiệm dự án qua giao diện web hoặc tương tác trực tiếp với API.

-   **Frontend URL (Giao diện Web):** [https://tm-alerting.vercel.app/]
-   **Backend API (Swagger UI):** [https://tm-alerting.onrender.com/swagger-ui.html](https://tm-alerting.onrender.com/swagger-ui.html)



### Cách 1: Trải nghiệm qua Giao diện Web (Khuyên dùng)

Đây là cách nhanh nhất để xem các tính năng chính của ứng dụng.

1.  **Truy cập trang Frontend** ở URL trên.
2.  **Đăng nhập** bằng tài khoản demo có sẵn:
    -   **Email:** `admin1@example.com`
    -   **Password:** `123`
3.  **Khám phá Dashboard:**
    -   Xem danh sách các cảnh báo giao dịch đáng ngờ.
    -   Sử dụng bộ lọc để xem cảnh báo theo trạng thái (`PENDING`, `COMPLETED`).
    -   Nhấp vào một cảnh báo để xem chi tiết.
4.  **Xử lý cảnh báo:**
    -   Trong chi tiết cảnh báo, chọn "Resolve", nhập lý do và xác nhận.
    -   Bạn sẽ thấy trạng thái của cảnh báo được cập nhật.
5.  **Kiểm tra Audit Log:**
    -   Truy cập trang Audit Log (nếu có) để thấy hành động "Resolve" của bạn đã được hệ thống ghi lại.

### Cách 2: Test API với Swagger UI (Dành cho Technical Reviewer)

Cách này cho phép tương tác trực tiếp với các endpoint của backend.

1.  **Truy cập trang Swagger UI** của backend.
2.  **Tạo tài khoản mới:**
    -   Tìm đến endpoint `POST /api/auth/register`.
    -   Nhấp "Try it out" và điền thông tin của bạn vào request body. Ví dụ:
        ```json
        {
          "email": "your-email@gmail.com",
          "password": "your-strong-password",
          "fullName": "Your Name"
        }
        ```
    -   Nhấn "Execute" để tạo tài khoản.
3.  **Đăng nhập để lấy Token:**
    -   Tìm đến endpoint `POST /api/auth/login`.
    -   Nhấp "Try it out", điền tài khoản bạn vừa tạo. **Quan trọng:** đặt `useCookie` thành `false` để token được trả về trong response.
        ```json
        {
          "email": "your-email@gmail.com",
          "password": "your-strong-password",
          "useCookie": false
        }
        ```
    -   Nhấn "Execute" và sao chép giá trị `token` trong phần response body.
4.  **Ủy quyền (Authorize):**
    -   Ở đầu trang Swagger, nhấp vào nút "Authorize".
    -   Trong ô "Value", dán token theo định dạng `Bearer <your_token>`. Ví dụ: `Bearer eyJhbGciOiJIUzI1NiJ9...`
    -   Nhấp "Authorize" để xác nhận. Giờ đây mọi yêu cầu từ Swagger sẽ được xác thực.
5.  **Thử các endpoint được bảo vệ:**
    -   Thử `GET /api/alerts` để lấy danh sách cảnh báo.
    -   Thử `PATCH /api/alerts/{id}/resolve` để xử lý một cảnh báo.

---

## ✨ Tính năng chính

-   **Giao diện Hiện đại:** Dashboard được xây dựng bằng Next.js và Tailwind CSS, responsive và dễ sử dụng.
-   **Xác thực an toàn:** Đăng nhập bằng JWT, hỗ trợ cả `Bearer Token` và `HttpOnly`, `Secure`, `SameSite=None` cookie để tăng cường bảo mật.
-   **Quản lý Cảnh báo:** Tạo, xem, lọc và xử lý các cảnh báo giao dịch đáng ngờ.
-   **Audit Log:** Tự động ghi lại tất cả các hành động quan trọng (đăng nhập, xử lý cảnh báo) để đảm bảo tính minh bạch.
-   **Tài liệu API tự động:** Tích hợp Swagger UI để cung cấp tài liệu API trực quan và tương tác.

---

## 🛠️ Công nghệ sử dụng

-   **Backend:**
    -   Java 21, Spring Boot 3
    -   Spring Security (JWT Authentication)
    -   MongoDB
-   **Frontend:**
    -   Next.js, React
    -   TypeScript
    -   Tailwind CSS
-   **Build & Dependencies:**
    -   Maven (Backend)
    -   NPM (Frontend)
-   **Deployment:**
    -   Backend: Docker, Render
    -   Frontend: Vercel

---

## 🏁 Hướng dẫn cài đặt Local

### Yêu cầu

-   JDK 21
-   Node.js & npm
-   Maven
-   Docker

### Backend

1.  **Clone repository.**
2.  **Khởi động PostgreSQL bằng Docker:**
    ```bash
    docker run -d --name postgres-dev -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=tmalerting_dev postgres
    ```
3.  **Cấu hình file `application-dev.yml`:**
    Mở file `src/main/resources/application-dev.yml` và đảm bảo thông tin kết nối database khớp với Docker container.
4.  **Chạy ứng dụng:**
    ```bash
    # Từ thư mục gốc của dự án
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
    ```
    Backend sẽ chạy tại `http://localhost:8080`.

### Frontend

1.  **Di chuyển vào thư mục frontend** (giả sử tên là `frontend`):
    ```bash
    cd frontend
    ```
2.  **Cài đặt dependencies:**
    ```bash
    npm install
    ```
3.  **Tạo file môi trường `.env.local`:**
    Tạo file `.env.local` và khai báo URL của backend:
    ```
    NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
    ```
4.  **Chạy ứng dụng:**
    ```bash
    npm run dev
    ```
    Frontend sẽ chạy tại `http://localhost:3000`.
