# Game đoán số

## Tổng quan
Ứng dụng Game đoán số cho phép người dùng đăng ký, đăng nhập và tham gia đoán số.
Hệ thống có leaderboard, quản lý phiên đăng nhập bằng JWT, tối ưu hiệu năng bằng Redis, và được dockerized toàn bộ.

## Tanstack
* Frontend:ReactJs, Tanstack Query, Tanstack Router, Zustand, Axios
* Backend: Java spring boot, jwt, validation, mybatis, redus
* Database: MySQL
* Tools: Docker, docker componse

## Xử lý bài toán
1. Một user gọi nhiều lần /guess cùng 1 lúc:
* Dùng @Transactional để block record khi update
* Ở phía FE sẽ xử lý disable button submit khi đang thực hiện request /guess

2. Đảm bảo khi hệ thống có lượng user lớn, các api /leaderboard/me vẫn trả
kết quả nhanh
* Xử dụng redis(ZSet) để lưu thông tin leaderboard
* Flow: User đoán đúng -> cập nhật thông tin ở redis -> cập nhật thông tin ở database(Nếu restart server -> Load dữ liệu leaderboard từ database lưu vào redis)

## Bảo mật
* Xử dụng jwt: accessToken có thời gian tồn tại thấp và refreshToken có thời gian tồn tại lâu
* Dùng accessToken để xác thực người dùng, khi token hết hạn thì sẽ call api để nhận accessToken và refreshToken mới để tiếp tục phiên đăng nhập(Bước này cần refreshToken để nhận token mới)

## Logic cho tỷ lệ thắng của user là 5$
``` java
public class RandomGuess {
    private static int winPercent = 5;
    private static List<Integer> listNumber = List.of(1, 2, 3, 4, 5);

    public static int generateRandomNumber(int userNumber) {
        // random number from 0 to 99, 100 numbers -> 100%
        int random = (int) (Math.random() * 100);

        // winPercent = 5 -> 0 to 4 is win(0 to 4 -> 5 numbers -> 5%)
        if (random < winPercent) {
            return userNumber;
        } else {
            List<Integer> numbers = new ArrayList<>(listNumber);
            numbers.remove(Integer.valueOf(userNumber));
            return numbers.get((int) (Math.random() * numbers.size()));
        }
    }
}
```

## Cách chạy dự án
```
git cline https://github.com/NguyenTHanhAn853202/guess-test.git
cd guess-test

docker componse up -build
docker exec -it mysql mysql -u root -p
/// Nhập password
CREATE DATABASE IF NOT EXISTS guess_db;
USE guess_db;
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    score INT DEFAULT 0,
    turns INT DEFAULT 20,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

CREATE INDEX idx_score_desc ON users (score DESC);
CREATE INDEX idx_username ON users (username);

docker restart guess-be
// Truy cập localhost:4173
// Test Postman: localhost:8080/api/v1/{path}
```

* Hệ thống FE đã xử lý duy trì phiên đăng nhập cho user.

## Cấu trúc file
1. BE
```
├───src
   ├───main
       ├───java
       │   └───com
       │       └───app
       │           └───guess
       │               │   GuessApplication.java
       │               │
       │               ├───configuration
       │               │       RedisConfig.java
       │               │       Security.java
       │               │       StartupServer.java
       │               │
       │               ├───constant
       │               │       ErrorCode.java
       │               │       NotAuthentication.java
       │               │       RedisKey.java
       │               │       TokenName.java
       │               │
       │               ├───controller
       │               │       AuthController.java
       │               │       ...
       │               │
       │               ├───dto
       │               │   │   UserDetailsDto.java
       │               │   │
       │               │   ├───request
       │               │   │       GuessRequest.java
       │               │   │       ...
       │               │   │
       │               │   └───response
       │               │           ApiResponse.java
       │               │           ErrorResponse.java
       │               │           GuessResponse.java
       │               │           ...
       │               │
       │               ├───exception
       │               │       AppException.java
       │               │       GlobalException.java
       │               │
       │               ├───mapper
       │               │       UserMapper.java
       │               │
       │               ├───middleware
       │               │       Filter.java
       │               │
       │               ├───model
       │               │       BaseEntity.java
       │               │       CustomUserDetails.java
       │               │       User.java
       │               │
       │               ├───service
       │               │   │   AuthService.java
       │               │   │   ...
       │               │   │
       │               │   └───imp
       │               │           AutheServiceImp.java
       │               │           ...
       │               │
       │               ├───utils
       │               │       CookieUtils.java
       │               │       ...
       │               │
       │               └───validation
       │                   ├───constraint
       │                   │       PasswordMatcher.java
       │                   │
       │                   └───validator
       │                           PasswordMatcherValidator.java
       │
       └───resources
           │   application.yml
           │
           └───mapper

```
2. FE
```
└───src
    │   App.tsx
    │   index.css
    │   main.tsx
    │
    ├───app
    │   ├───router
    │   │       index.ts
    │   │       router.tsx
    │   │
    │   ├───services
    │   │       auth.ts
    │   │       ...
    │   │
    │   └───stores
    │       ├───api
    │       │       index.ts
    │       │       useAuth.ts
    │       │       ...
    │       │
    │       └───user
    │               index.ts
    │               useUser.ts
    ├───components
    │   ├───Input
    │   │       index.ts
    │   │       Input.tsx
    │   │
    │   └───layout
    │           AuthLayout.tsx
    │           index.ts
    │           Layout.tsx
    │
    ├───configuration
    │       api.ts
    │
    ├───constants
    │       apiPath.ts
    │       errorCode.ts
    │       index.ts
    │       screen.ts
    │
    ├───pages
    │   ├───Guess
    │   │   │   Guess.tsx
    │   │   │   index.ts
    │   │   │
    │   │   └───hooks
    │   │           useGuess.ts
    │   │           useGuessForm.ts
    │   │
    │   ├───Leaderboard
    │   │       index.ts
    │   │       LeadeBoard.tsx
    │   │
    │   ├───Profile
    │   │   │   index.ts
    │   │   │   Profile.tsx
    │   │   │
    │   │   └───hooks
    │   │           useProfile.ts
    │   │
    │   ├───SignIn
    │   │   │   index.ts
    │   │   │   SignIn.tsx
    │   │   │
    │   │   └───hooks
    │   │           useSignIn.ts
    │   │           useSignInForm.ts
    │   │
    │   └───SignUp
    │       │   index.ts
    │       │   Signup.tsx
    │       │
    │       └───hooks
    │               useSignUp.ts
    │               useSignUpForm.ts
    │
    ├───types
    │       error.ts
    │       index.ts
    │
    └───utils
            errorMapper.ts
            index.ts
```