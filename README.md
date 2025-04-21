# JDBC_XPOLL

XPoll
XPoll is a command-line application built with Java that enables users to create, manage, and participate in interactive polls. The application uses JDBC to connect to a relational database and track all poll-related data, offering a streamlined way to gather group opinions or make decisions.

🧠 Objective
Develop a fully functional command-line polling application using Java, integrating JDBC for database interactions to store and manage user, poll, and response data.

📋 Pre-Requisite Skills
Java

SQL

🎯 Primary Goals
1. Project Setup
Initialize a new Java project.

Configure JDBC and other required dependencies.

2. Requirement Analysis
Understand the core features of a polling system.

Plan and design a clean and intuitive command-line workflow.

3. Core Application Development
Implement user registration and login.

Enable poll creation, management, and response handling.

Follow industry best practices for code quality and structure.

4. Database Integration
Design a normalized relational database schema.

Use JDBC to perform CRUD operations securely and efficiently.

5. Testing
Write unit tests using JUnit.

Validate each feature to ensure correctness and robustness.

📦 Project Summary
XPoll is a Java-based, command-line polling application that facilitates the creation, participation, and management of polls. By leveraging JDBC, the application seamlessly interacts with a relational database to record and retrieve poll-related information. XPoll is ideal for small groups and classroom use where real-time, text-based polling is needed.

📑 Project Deliverables
1. 🧍 User Management
CREATE_USER: Register new users with a username and password.

LOGIN: Authenticate users based on stored credentials.

2. 🗳️ Poll Creation & Management
CREATE_POLL: Create new polls with custom questions and choices.

CLOSE_POLL: Prevent further votes on a poll.

DELETE_POLL: Remove a poll created by the user.

3. 🙋 Poll Interaction
RESPOND_TO_POLL: Vote by selecting one of the provided choices.

VIEW_POLL_SUMMARY: Display poll results with response counts per choice.

4. 🛢️ Database Integration with JDBC
Set up relational tables for:

Users

Polls

Choices

Responses

Implement JDBC operations to:

Insert, update, delete, and query poll-related data.

5. 💻 Command-Line Interface
Clear and intuitive text-based UI.

Users interact through typed commands and prompts.

6. 🧪 Unit Testing
Built comprehensive unit tests using JUnit to validate:

User registration & login

Poll creation & response

Result summaries

Poll closure functionality

🛠️ Tech Stack
Java: Core application logic

JDBC: Database interaction

SQL: Relational database design & management

JUnit: Unit testing

🔧 Skills Demonstrated
Object-Oriented Programming: Applied OOP principles such as encapsulation, inheritance, and abstraction.

Database Design: Structured a normalized schema with appropriate constraints and relationships.

JDBC Integration: Managed database connectivity and transactions.

CLI Design: Built a user-friendly, interactive command-line interface.

Testing: Validated application logic through modular and automated unit tests.

📚 Key Learnings
📌 Technical
Developed a real-world CLI application using Java.

Mastered JDBC and SQL for real-time data operations.

Learned how to design a relational schema with foreign keys and composite primary keys.

Gained practical experience in writing and running unit tests.

💡 Non-Technical
Enhanced problem-solving through real-time feature implementation.

Strengthened attention to detail in handling edge cases and user inputs.

Learned to manage and integrate external dependencies like database connections.
