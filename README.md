
# ATP-Project-PartC

This project is a Java-based application focused on maze generation, solving, and visualization. The application is built with a Model-View-ViewModel (MVVM) architecture and includes a graphical user interface (GUI) for user interaction.
## Overview
This project features:
- **Servers**: Two dedicated servers for maze generation and solving using search algorithms (e.g., BFS, DFS). Communication is achieved through a client-server architecture.
- **Multi-threading**: Uses a **thread pool** (`ExecutorService`) for handling multiple concurrent tasks efficiently.
- **Design Patterns**: Implements **MVVM** for clean architecture, **Observable** for state change notifications, and **Client-Server** for distributed processing.
- **GUI**: Built with JavaFX for an intuitive and visually engaging user interface.
  
## Features

- **Maze Generation**: Create mazes of varying complexity using a dedicated server.
- **Maze Solving**: Solve mazes through advanced search algorithms provided by a server.
- **Interactive GUI**: Built with JavaFX for a user-friendly interface.
- **Multi-threading**: Utilizes a thread pool for efficient handling of asynchronous tasks.
- **Structured Code**: Organized using the MVVM architecture for separation of concerns.
- **Design Patterns**: Implements the Observable design pattern to notify the ViewModel and View of state changes in the Model.

## Project Structure

```
ATP-Project-PartC
├── AllMaze/                  # Maze-related assets
├── META-INF/                 # Meta-information for the project
├── out/artifacts/            # Compiled artifacts
├── resources/                # Resources for the application (e.g., images, audio)
├── src/main/java/            # Source code
│   └── project_partc/
│       ├── Model/            # Model layer (business logic)
│       ├── View/             # View layer (GUI components)
│       └── ViewModel/        # ViewModel layer (interface between View and Model)
├── target/classes/           # Compiled class files
├── mvnw                     # Maven wrapper
├── mvnw.cmd                 # Maven wrapper for Windows
├── pom.xml                  # Maven project configuration file
├── win.mp3                  # Audio file used in the application
```

## Requirements

- Java Development Kit (JDK) 8 or later
- Apache Maven 3.6.0 or later
- JavaFX 11 or later for the GUI components

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/shirmor/ATP-Project.git
   cd ATP-Project-PartC
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn exec:java
   ```

## Technical Details

### Servers
- The project includes two servers:
  1. **Maze Generation Server**: Generates mazes based on specified dimensions.
  2. **Maze Solving Server**: Solves mazes using search algorithms like BFS and DFS.
- Communication with servers is handled via a client-server architecture.

### Multi-threading
- A thread pool (`ExecutorService`) is used to handle multiple tasks concurrently, ensuring smooth operation and responsiveness.

### Design Patterns
- **MVVM (Model-View-ViewModel)**: Ensures a clean separation of concerns.
- **Observable**: Used in the `Model` to notify the `ViewModel` and `View` about state changes.
- **Client-Server Architecture**: Facilitates maze generation and solving through dedicated servers.

### GUI
- Built using **JavaFX**, providing an interactive and visually appealing user interface.

## Usage

- Launch the application.
- Use the GUI to:
  - Generate a maze of your desired size.
  - Solve the maze using built-in algorithms.
  - Save and load mazes for later use.
- Navigate the maze using keyboard controls.

## Directory Details

- **Model**: Contains the business logic and core functionalities, including maze generation and solving.
- **View**: Implements the user interface using JavaFX.
- **ViewModel**: Acts as a bridge between the Model and View, managing user commands and updating the UI.

## Dependencies

The project uses Maven for dependency management. The required dependencies are specified in the `pom.xml` file.

## Contributors

- **Shir Mordechai**
- **Netta Meiri**
- **Gil Ari Agmon**

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- This project is part of an academic course or initiative on advanced Java programming.
