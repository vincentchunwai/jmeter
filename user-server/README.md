# user-server

## Description
- To start the server run ./start-container.sh
The `user-server` is a Spring Boot application that provides user management features.

## Features
- **User Registration**: Allows users to register by providing an email and password.
- **User Authentication**: Supports user authentication and authorization with different roles (ADMIN, USER).
- **User Information Management**: Provides endpoints to manage user information, including updating email and password.
- **Error Handling**: Includes comprehensive error handling with custom exception classes and error messages.
- **Testing**: Utilizes both unit and integration tests for ensuring application functionality.

## Configuration
The application is configured with:
- **Database**: Utilizes H2 in-memory database for testing and PostgreSQL for production.
- **Security**: Configures Spring Security for user authentication and authorization.
- **Flyway**: Manages database migrations to ensure consistent data structures.
- **Password Encryption**: Utilizes BCrypt to securely store user passwords.

## Testing
1. **UserControllerTest**:
   - Tests user registration functionality.
   - Validates user data saving and retrieval.

2. **UserServerApplicationTests**:
   - Tests the `findAllUser` method of the `UserService` class.
   - Verifies the retrieval of user information from the database.

## How to Use
- Clone this repository.
- Configure your `application.yml` or `application-test.yml` file.
- Build and run the application using Maven.
- Access the endpoints using your preferred tool or web browser.

## Security
- User information is stored securely using BCrypt password encryption.
- Spring Security is configured for user authentication and authorization.

## Error Handling
- Custom exceptions are used for handling errors.
- Detailed error messages are provided to assist with debugging.

