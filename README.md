![Done Icon](./readme_resources/done_icon.png)
# done
The best web application for getting shit done. No bullshit functions no bullshit layout.<br>
Straight to the point - just make shit done.
![img.png](./readme_resources/img.png)
## Technologies Used

This project is built with a variety of technologies:

- **Java**: The main language used for the backend of the application.

- **Spring Boot**: A framework used to simplify the setup and development of the application.

- **Maven**: A build automation tool used primarily for Java projects.

- **JavaScript**: Used for some parts of the application.

- **TypeScript**: A statically typed superset of JavaScript that adds optional types.

- **MongoDB**: The database used to store the tasks.

- **Vaadin**: A platform for building single-page web applications in Java.

## User Interface
![img_1.png](./readme_resources/img_1.png)

The DoneApplication user interface is designed to be simple and straightforward to help you get things done without any distractions.

### Main Page

When you open the application, you'll see the main page. This page displays a list of your tasks. Each task has a title and a checkbox that you can use to mark the task as done.

### Adding a Task
![img_2.png](./readme_resources/img_2.png)<br>
To add a new task, click on the "Add Task" button located at the top of the main page. This will open a form where you can enter the title of your new task. After entering the title, click on the "Save" button to add the task to your list.

### Deleting a Task

To delete a task, click on the "Delete" button next to the task in the list. This will remove the task from your list.

Remember, the goal of DoneApplication is to help you get things done. So, keep your task list up-to-date and try to complete your tasks as soon as possible.

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