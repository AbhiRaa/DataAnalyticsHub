# AP_Assignment2_DataAnalyticsHub
This is an assignment for Advance Programming - Sem2 course at RMIT - Abhinav .(s3977487)

## JavaFX Application
This is a JavaFX-based application with features for managing user profiles, posts, and other functionalities.

## Development Environment
- IDE: Eclipse
- Java Version: 20.0.2
- JavaFX Version: javafx-sdk-21
- Database: SQLite
- Testing Framework: JUnit 4

## Installation & Running

1. Setting Up the Environment:
- Install Java 20.0.2.
- Download and set up JavaFX javafx-sdk-21.
- Ensure SQLite is set up on your machine.
- Install JUnit 4 for running tests.
- Clone the Repository: git clone <repository-url>

2. Open in Eclipse:
- Launch the Eclipse IDE.
- Choose File -> Open Projects from File System.
- Navigate to the directory where you cloned the repository and select the project.

3. Configure JavaFX:
- Right-click on the project in the Project Explorer.
- Select Properties -> Java Build Path -> Libraries.
- Add the javafx-sdk-21 library.

4. Run the Application:
- Navigate to the Main class.
- Right-click and select Run As -> Java Application.

5. Running Tests:
- Tests are written using JUnit 4.
- Navigate to the test class you wish to run.
- Right-click and select Run As -> JUnit Test.

## Design
### Application Design Patterns
The application has been structured using several design patterns to enhance modularity, maintainability, and clarity. The primary patterns implemented include:
1. Facade Pattern: The GUIViewFacade serves as a simplified interface to a more complex subsystem. This hides the complexities of the system and provides a simpler interface to the client.
2. Factory Pattern: Used in the creation of User and Post service components, ensuring that the creation logic is centralized and consistent across the application.
3. Singleton Pattern: Ensures that classes like the database connection manager have only one instance and provide a global point to this instance.
### MVC Architecture
MVC (Model-View-Controller) is an architectural pattern that separates the representation of information from the user's interaction with it. The pattern is implemented as:
1. Model: Represents the core of the application. It defines and manages the data, logic, and rules of the application. In our case, classes like User and Post act as models.
2. View: Represents the UI of the application. It displays the data from the model to the user and sends user commands to the controller. JavaFX components and custom views, like DashboardView or PostListView, act as the view in our application.
3. Controller: Acts as an interface between the Model and View. It takes the user input from the view, processes it (with possible updates to the model), and returns the display output to the view. Classes like UserController and PostController play the role of controllers.

For a detailed understanding of the object-oriented design of the application, please refer to the provided design diagram. 
![Alt text](<AP ASS2 - Page 5.png>)