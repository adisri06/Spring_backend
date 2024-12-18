SecurityConfig

 * Security configuration class responsible for setting up application security.
 * 
 * - Configures access control rules for different endpoints based on user roles.
 *   - POST, PUT, DELETE on "/customer/**", "/order/**", "/order-item/**" require "ADMIN" role.
 *   - GET on "/customer/**", "/order/**", "/order-item/**" allowed for "USER" role.
 * 
 * - Enables HTTP Basic Authentication and CSRF protection (disabled for simplicity).
 * 
 * - Configures CORS (Cross-Origin Resource Sharing) to allow cross-origin requests with specific rules.
 * 
 * - Defines the password encoder using BCrypt and an in-memory user details service for authentication.
 * 
 * - Uses a CorsConfigurationSource bean to define allowed origins, headers, and methods.
 * 
 * - Configures the application to allow non-HTTPS requests and to handle CORS requests globally.
--------------------------------------------------------------------------------------------------

CustomerController class
/**
 * The CustomerController class handles all customer-related HTTP requests.
 * It exposes several endpoints to interact with customer data, such as adding, updating,
 * deleting, and retrieving customers.
 *
 * Key Endpoints:
 * 1. **/allCustomerRecords** (GET) - Fetch all customer records.
 * 2. **/customerRecord/{mobileNumber}** (GET) - Fetch a customer by mobile number.
 * 3. **/customerName/{name}** (GET) - Fetch customers by name.
 * 4. **/insertCustomers** (POST) - Insert a new customer.
 * 5. **/updatecustomer** (PUT) - Update an existing customer.
 * 6. **/deleteCustomer/{phoneNumber}** (DELETE) - Delete a customer by phone number.
 * 7. **/customerPages** (GET) - Retrieve paginated customer records by plan ID.
 * 8. **/userLoans** (GET) - Retrieve user loan details (currently not fully implemented).
 * 9. **Filters** - Filters customer data based on various parameters like name, age, gender, etc.
 * 10. **Projections** - Fetch customer projections filtered by plan ID.
 * 
 * All methods are annotated with appropriate HTTP verbs (GET, POST, PUT, DELETE) and return 
 * ResponseEntity objects with status codes and payloads.
 *
 * Logging is implemented at various points to track successful operations, warnings, and errors.
 * CommonExceptions are used for error handling to provide clear, consistent error messages.
 *
 * Caching is enabled via @EnableCaching to improve performance for frequently accessed data.
 */
--------------------------------------------------------------------------------------------------

UserController
 /**
 * The UserController class provides REST endpoints for interacting with user data.
 * It allows for retrieving user information by user ID and supports versioning of the API.
 * 
 * Key Endpoints:
 * 1. **/{id}** (GET) - Fetches user details by user ID.
 * 2. **/{id}?version={version}** (GET) - Fetches user details by ID with versioning support, allowing different versions of the user data.
 * 
 * Versioning:
 * - The API supports versioning, where version 1 returns basic user data, and version 2 can return user data with additional fields (e.g., phone number).
 * 
 * Error Handling:
 * - If the user is not found, the API returns a 404 status with a null body.
 * - Runtime exceptions are caught, and appropriate error responses are returned.
 * 
 * Service Layer:
 * - User details are retrieved from the `UserService`, which handles business logic such as fetching user details and supporting versioned data retrieval.
 */
 --------------------------------------------------------------------------------------------------
CustomerPageableService
 /**
 * The CustomerPageableService class handles the business logic related to customer data with pagination and caching.
 * It interacts with the repository layer to retrieve paginated customer data based on the provided plan ID.
 * The service is responsible for:
 * - Fetching paginated customer data sorted by name.
 * - Using cache to store and retrieve customer data to improve performance.
 * 
 * Key Functionalities:
 * 1. **filterByPlanIdPages**: 
 *    - Retrieves customer data for a given plan ID with pagination (page, size).
 *    - Uses the cache to store and fetch data for efficient retrieval.
 *    - Logs the data retrieval process and cache status.
 * 2. **getCustomerNamesFromCache**:
 *    - Fetches customer names from the cache based on the provided plan ID, page, and size.
 *    - If data is not in cache (cache miss), it fetches the data from the repository and populates the cache.
 * 3. **fetchAndCacheCustomerData**:
 *    - Fetches customer data from the repository when cache miss occurs.
 *    - Caches the fetched data for future retrieval.
 * 4. **cacheCustomerData**:
 *    - Caches the fetched customer data with a unique key based on the plan ID, page, and size.
 * 5. **getCacheValue**:
 *    - Retrieves cached customer data from the cache by generating a unique cache key.

 * Logging and error handling:
 * - The class uses SLF4J and Apache Commons logging for logging important information and errors.
 * - If an error occurs at any stage (fetching, caching, or processing data), a `CommonExceptions` is thrown with a descriptive message.
 * - Proper logging is performed at various steps, including cache misses, successful data retrieval, and errors.

 * Cache Implementation:
 * - The cache is managed through Spring's `CacheManager`.
 * - Data is cached for faster access and to reduce repeated database hits.

 * Error Handling:
 * - The class handles exceptions that may occur during the data retrieval process and logs errors for better traceability.
 */