# done
The best web application for getting shit done. No bullshit functions no bullshit layout.<br>
Straight to the point - just make shit done.

## Technologies Used

This project is built with a variety of technologies:

- **Java**: The main language used for the backend of the application.

- **Spring Boot**: A framework used to simplify the setup and development of the application.

- **Maven**: A build automation tool used primarily for Java projects.

- **JavaScript**: Used for some parts of the application.

- **TypeScript**: A statically typed superset of JavaScript that adds optional types.

- **MongoDB**: The database used to store the tasks.

- **Vaadin**: A platform for building single-page web applications in Java.

# DoneApplication

DoneApplication is a task management application built with Java, Spring Boot, and Maven. It also uses JavaScript and TypeScript for some parts of the application.

## Overview

The application allows users to manage their tasks effectively. Users can add new tasks, update existing tasks, and load tasks from the database. The tasks are stored in a MongoDB database.

## Key Classes

- `DoneApplication`: This is the main class of the application. It connects to the database and runs the web application and menu if the database connection is successful.

- `DatabaseTask`: This class is responsible for performing database operations related to tasks. It can insert new tasks, update existing tasks, and load tasks from the database.

- `TaskDataManager`: This class is responsible for managing task data on the database. It can insert new tasks into the database.

## Getting Started

To get started with the DoneApplication, you need to provide the database URL as an argument when running the application. The application will connect to the database and start the web application and menu.

## Contributing

Contributions are welcome. Please feel free to fork the project and submit a pull request with your changes.

## License

DoneApplication is open-source software licensed under the MIT license.