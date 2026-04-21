# Student Task Manager API

A Spring Boot-based REST API for managing tasks, featuring JWT-based authentication and role-based access control (RBAC).

## Features

- **User Authentication:** Secure registration and login using JWT.
- **Task Management:** Create, list, and delete tasks.
- **Role-Based Access Control:** Differentiated access for `USER` and `ADMIN` roles.
- **Swagger Documentation:** Integrated OpenAPI 3.0 documentation.
- **Security:** CSRF protection disabled (stateless), JWT filter for authenticated requests.
- **Dockerized Deployment:** Multi-container setup with Docker and Docker Compose for the app and PostgreSQL database, with persistent volume storage.

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

The recommended way to run the project is via Docker Compose, which spins up the Spring Boot app and a PostgreSQL database together.

### Prerequisites
- Docker and Docker Compose installed.

### Running with Docker Compose

From inside the `App/` folder (where the `Dockerfile` and `docker-compose.yml` live):

```bash
docker compose up --build
```

This will:
1. Pull the `postgres:16` image from Docker Hub.
2. Build the Spring Boot app image using the `Dockerfile`.
3. Start both containers on a shared private network.
4. Expose the API on `http://localhost:8080` and PostgreSQL on `localhost:5432`.

To stop everything:

```bash
docker compose down
```

To stop and also wipe the database:

```bash
docker compose down -v
```

### Configuration

The `docker-compose.yml` overrides the database connection via environment variables, so the app inside the container connects to the `postgres` service on the Docker network instead of `localhost`. The default credentials (for local development) are:

- Database: `studenttaskmanager`
- Username: `davidberbecar`
- Password: `1234`

PostgreSQL data is persisted in a Docker-managed named volume (`postgres-data`), so your tables and data survive container restarts.

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
- **Docker** & **Docker Compose** (Containerization and orchestration)
