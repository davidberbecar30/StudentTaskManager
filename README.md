# Student Task Manager API

A Spring Boot-based REST API for managing tasks, featuring JWT-based authentication and role-based access control (RBAC).

## Features

- **User Authentication:** Secure registration and login using JWT.
- **Task Management:** Create, list, and delete tasks.
- **Role-Based Access Control:** Differentiated access for `USER` and `ADMIN` roles.
- **Swagger Documentation:** Integrated OpenAPI 3.0 documentation.
- **Security:** CSRF protection disabled (stateless), JWT filter for authenticated requests.

## Security & Authentication

The application uses **JWT (JSON Web Token)** for stateless authentication.

1.  **Login:** Send a POST request to `/auth/login`.
2.  **Receive Token:** On success, the server returns a JWT string.
3.  **Use Token:** For protected endpoints, include the token in the `Authorization` header:
    `Authorization: Bearer <your_token>`

### Roles
- `ROLE_USER`: Can manage their own tasks.
- `ROLE_ADMIN`: Full access to all tasks and user administration.


## Getting Started

## API Endpoints

### Authentication (`/auth`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| POST | `/auth/register` | Register a new user | Permit All |
| POST | `/auth/login` | Login and receive JWT token | Permit All |

**Request Body (UserDTO):**
```json
{
  "username": "exampleUser",
  "password": "examplePassword"
}
```

### Admin Operations (`/admin`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| POST | `/admin/user` | Create a new user (admin-level) | ROLE_ADMIN |
| GET | `/admin/user` | List all registered users | ROLE_ADMIN |

### Task Management (`/tasks`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| GET | `/tasks/my` | Get tasks for the current user | ROLE_USER, ROLE_ADMIN |
| GET | `/tasks` | List all tasks in the system | ROLE_ADMIN |
| POST | `/tasks` | Create a new task | ROLE_USER, ROLE_ADMIN |
| DELETE | `/tasks/{id}` | Delete a task by ID | ROLE_ADMIN |

**Request Body (TaskDTO):**
```json
{
  "title": "Task Title",
  "description": "Task Description"
}
```

## API Documentation (Swagger)

Once the application is running, you can access the Swagger UI for interactive documentation:
`http://localhost:8080/swagger-ui/index.html`

## Testing

The project includes:
- `UserServiceTest`: Unit tests for user management logic.
- `TaskServiceTest`: Unit tests for task management logic.
- `SecurityIntegrationTest`: Integration tests for access control and authentication.

## Technologies Used

- **Java 25**
- **Spring Boot 4.0.5**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL** (Database)
- **JSON Web Token (JWT)** (Authentication)
- **Lombok** (Boilerplate reduction)
- **Maven** (Build tool)
- **SpringDoc OpenAPI (Swagger UI)**
