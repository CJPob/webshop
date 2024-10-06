A Java enterprise webshop application.

User Interaction:
Users interact with the application via the UI

Servlets:
The servlets handle incoming requests from the UI. They act as controllers forwarding requests to appropriate handlers.

Handlers (Facade Layer):
Servlets call methods in handlers, which encapsulate business logic. Handlers act as a facade, abstracting the underlying complexity of models and database access. This separation ensures that the servlets remain lightweight and focused on controlling the flow.

Models (Business Objects):
Handlers interact with models (such as User, Item, Order). These models represent the core business objects and contain data and logic relevant to the application's domain.

Database Access:
Models use DB classes to interact with the database. The DB classes use database operations (CRUD) and manage database connections through DBManager, for a clean separation between business logic and data persistence.

Response Back to UI:
Once the data is processed, the handlers return results to the servlets, which then forward the response back to the UI, presenting the result to the user.

The tables are normalized and the relationship is visualized in the image

![db_tables](https://github.com/user-attachments/assets/9d09d622-d474-47a1-90e5-1ca6bfbae0ce)



Enviroments
Java: Oracle OpenJDK 21
Server: Tomcat 10.1.30
IDE: IntelliJ IDEA Ultimate
Database: MySQL
