![image](https://github.com/user-attachments/assets/f6a3dad0-247c-40f7-8614-283b2fbe9d26)


## A Java enterprise webshop application.

**User Interaction (JSP):**
Users interact with the application via the UI

**Servlets:**
The servlets handle incoming requests from the UI. They act as controllers forwarding requests to appropriate handlers.

**Handlers (Facade Layer):**
Servlets call methods in handlers, which encapsulate business logic. Handlers act as a facade, abstracting the underlying complexity of models and database access. This separation ensures that the servlets remain lightweight and focused on controlling the flow.

**Models (Business Objects):**
Handlers interact with models (such as User, Item, Order). These models represent the core business objects and contain data and logic relevant to the application's domain.

**Database Access:**
Models use DB classes to interact with the database. The DB classes use database operations (CRUD) and manage database connections through DBManager, for a clean separation between business logic and data persistence. 

**Response Back to UI:**
Once the data is processed, the handlers return results to the servlets, which then forward the response back to the UI, presenting the result to the user.

The tables are normalized and the relationship is visualized in the image

![db_tables](https://github.com/user-attachments/assets/b941f3d2-9c94-4de4-be0f-d48cf536d2c1)


**DBManager and Hikari Connection Pool:** 
DBManager is implemented as a singleton to ensure that only one instance manages the connection pool. It efficiently handles multiple database transactions by providing each transaction with a connection from HikariCP, thereby supporting concurrency. Each call to getConnection() retrieves a separate connection from the pool, suitable for handling multiple concurrent database transactions.


**Enviroments:**
IDE - IntelliJ IDEA Ultimate
Java - Oracle OpenJDK 21
Server - Tomcat 10.1.30
Database - MySQL    



**Architecture: 3-tier**
![image](https://github.com/user-attachments/assets/7df57954-8ff0-487e-83d6-90e7c8eade9c)



