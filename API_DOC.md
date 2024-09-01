### API Documentation

#### Response

**Description:** Represents an API response in the application.

**Fields:**

- `ApiToken token`: The token used for the request.
- `String endpoint_name`: The name of the endpoint.
- `String endpoint_method`: The HTTP method used for the endpoint.
- `String response_time`: The time when the response was created.
- `String server_name`: The name of the server.
- `String user_email`: The email of the user associated with the request.
- `String response_description`: A description of the response.
- `String response_code`: The code representing the response status.
- `String response_usedTokenKey`: The token code used for the request.
- `Map<String, Object> body`: The body of the response.

**Constructors:**

1. **Default Constructor**
    - **Description:** Initializes a new response with default values.
    - **Parameters:**
        - `String endpoint`: The name of the endpoint.
        - `String endpoint_method`: The HTTP method used for the endpoint.
    - **Usage:**
      ```java
      Response response = new Response("endpoint_name", "GET");
      ```

**Methods:**

1. **prepareDocument**
    - **Description:** Prepares a document representation of the response.
    - **Returns:** `Document` representing the response.
    - **Usage:**
      ```java
      Document doc = response.prepareDocument();
      ```

2. **summarizeResponse**
    - **Description:** Summarizes the response after the object is created and filled with data.
    - **Usage:**
      ```java
      response.summarizeResponse();
      ```

---

#### HealthEndpoint

**Description:** Handles the health check endpoint for the application.

**Base URL:** `/api/health`

**Endpoints:**

1. **GET /api/health**
    - **Description:** Returns the health status of the application.
    - **Request Parameters:** None
    - **Request Headers:** None
    - **Response:**
        - **response_code:** `health_ok`
        - **response_description:** `Health endpoint working`
        - **body:**
            - `database_connected`: Boolean indicating if the database is connected.
            - `server_name`: Name of the server.

**Example Usage:**

```bash
curl -X GET http://localhost:8080/api/health
```

**Example Response:**

```json
{
  "endpoint": "/api/health",
  "method": "GET",
  "response_code": "health_ok",
  "response_description": "Health endpoint working",
  "body": {
    "database_connected": true,
    "server_name": "MyServer"
  }
}
```

---

#### TaskEndpoint

**Description:** Handles task-related operations for the application.

**Base URL:** `/api/v1/task`

**Endpoints:**

1. **POST /api/v1/task/create**
    - **Description:** Creates a new task.
    - **Request Headers:**
        - `token`: Token to validate.
    - **Request Body:**
        - `task_name`: Name of the task.
    - **Response:**
        - **response_code:** `task_created`, `task_not_created`, `token_notactive`, `token_invalid`, `task_error`
        - **response_description:** Description of the response.
    - **Example Usage:**
      ```bash
      curl -X POST http://localhost:8080/api/v1/task/create -H "token: your_token" -d '{"task_name":"New Task"}'
      ```

2. **POST /api/v1/task/update**
    - **Description:** Updates an existing task.
    - **Request Headers:**
        - `token`: Token to validate.
    - **Request Body:** Whole task data.
    - **Response:**
        - **response_code:** `task_updated`, `task_not_updated`, `token_notactive`, `token_invalid`, `task_error`
        - **response_description:** Description of the response.
    - **Example Usage:**
      ```bash
      curl -X POST http://localhost:8080/api/v1/task/update -H "token: your_token" -d '{"task_id":"task_id_value", "task_name":"Updated Task", "task_status":"IN PROGRESS", "task_timestamp":"2024-08-30T19:51:22.064258790", "task_comments":["Comment 1", "Comment 2"]}'
      ```

3. **POST /api/v1/task/change-status**
    - **Description:** Changes the status of a task.
    - **Request Headers:**
        - `token`: Token to validate.
    - **Request Body:**
        - `task_id`: ID of the task.
        - `task_status`: New status of the task.
    - **Response:**
        - **response_code:** `task_status_changed`, `task_status_not_changed`, `task_status_invalid`, `token_notactive`, `token_invalid`, `task_error`
        - **response_description:** Description of the response.
    - **Example Usage:**
      ```bash
      curl -X POST http://localhost:8080/api/v1/task/change-status -H "token: your_token" -d '{"task_id":"task_id_value", "task_status":"DONE"}'
      ```

4. **GET /api/v1/task/list**
    - **Description:** Lists all tasks for the authenticated user.
    - **Request Headers:**
        - `token`: Token to validate.
    - **Response:**
        - **response_code:** `task_list_created`, `token_notactive`, `token_invalid`, `task_error`
        - **response_description:** Description of the response.
        - **body:** A collection of tasks.
    - **Example Usage:**
      ```bash
      curl -X GET http://localhost:8080/api/v1/task/list -H "token: your_token"
      ```

**Example Response for List Tasks:**

```json
{
  "endpoint": "/api/v1/task/list",
  "method": "GET",
  "response_code": "task_list_created",
  "response_description": "Task collection created",
  "body": {
    "task_id_value": {
      "user_id": "user_id_value",
      "task_name": "Task Name",
      "task_status": "NEW",
      "task_timestamp": "2024-08-30T19:51:22.064258790",
      "task_comments": ["Comment 1", "Comment 2"]
    }
  }
}
```

---

#### TokenEndpoint

**Description:** Handles token-related operations for the application.

**Base URL:** `/api/v1/token`

**Endpoints:**

1. **GET /api/v1/token/create**
    - **Description:** Creates a new token for the given API key and API code.
    - **Request Body:**
        - `api_key`: The API key.
        - `api_code`: The API code.
    - **Response:**
        - **response_code:** `token_created`, `token_not_created`, `token_error`
        - **response_description:** Description of the response.
        - **body:**
            - `token`: The created token (if successful).
            - `user_email`: The email of the user associated with the token (if successful).
    - **Example Usage:**
      ```bash
      curl -X GET http://localhost:8080/api/v1/token/create -d '{"api_key":"your_api_key", "api_code":"your_api_code"}'
      ```

2. **GET /api/v1/token/validate**
    - **Description:** Validates the given token.
    - **Request Headers:**
        - `token`: The token to validate.
    - **Response:**
        - **response_code:** `token_valid`, `token_notactive`, `token_invalid`, `token_error`
        - **response_description:** Description of the response.
        - **body:**
            - `token`: The validated token (if successful).
            - `user_email`: The email of the user associated with the token (if successful).
    - **Example Usage:**
      ```bash
      curl -X GET http://localhost:8080/api/v1/token/validate -H "token: your_token"
      ```

**Example Response for Create Token:**

```json
{
  "endpoint": "/api/v1/token/create",
  "method": "GET",
  "response_code": "token_created",
  "response_description": "Created token for API call",
  "body": {
    "token": {
      "token_value": "generated_token_value",
      "token_owner": "user_id_value",
      "token_active": 1
    },
    "user_email": "user@example.com"
  }
}
```

**Example Response for Validate Token:**

```json
{
  "endpoint": "/api/v1/token/validate",
  "method": "GET",
  "response_code": "token_valid",
  "response_description": "Token is valid",
  "body": {
    "token": {
      "token_value": "validated_token_value",
      "token_owner": "user_id_value",
      "token_active": 1
    },
    "user_email": "user@example.com"
  }
}
```