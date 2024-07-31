Application Documentation
=========================

Running the Application
-----------------------

To run the application, follow these steps:

1.  **Build the Project:** Ensure you have [Maven](https://maven.apache.org/) installed, and run the following command to build the project:

    ```bash
    ./mvnw clean install
    ```

2.  **Run the Application:** Execute the application using:

    ```bash
    ./mvnw spring-boot:run
    ```
    
    The application will start on [http://localhost:8080](http://localhost:8080) by default.

Basic Authentication
--------------------

The application uses Basic Authentication with the following credentials:

*   **Username:** `admin`
*   **Password:** `password`

Accessing Swagger UI
--------------------

Swagger UI provides an interactive API documentation and can be accessed at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Interacting with APIs via Swagger UI
------------------------------------

1.  **Navigate to the Swagger UI URL:** Open the provided Swagger UI link in your web browser.


2.  **Explore Available Endpoints:** You will see a list of API endpoints organized by tags. Click on any endpoint to expand and view detailed information.


3.  **Make API Requests:** To interact with an API:

    *   Click on the endpoint you want to test.
    *   Click the "Try it out" button.
    *   Enter any required parameters and click "Execute."

Authenticating on Swagger UI
----------------------------

1.  **Authorize:**

    *   Click on the "Authorize" button located on the top right of the Swagger UI page.
    

2.  **Enter Credentials:**

    *   In the "Value" field, enter the credentials in the format: `admin:password`.
    *   Click "Authorize."


3.  **Use the API Endpoints:**

    *   Once authenticated, you can make requests to protected endpoints using Swagger UI.

* * *
