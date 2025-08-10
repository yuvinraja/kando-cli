# Kando CLI

A tiny local Kanban board for your projects, right in your terminal! 🚀

Kando CLI helps you organize your tasks in a simple, visual Kanban board format with three columns: **Todo**, **In Progress**, and **Done**. Perfect for developers who want to keep track of their project tasks without leaving the command line.


## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (for building from source)

## Installation

### Option 1: Download Pre-built JAR (Recommended)

1. Download the latest `kando.jar` from the [releases page](https://github.com/yourusername/kando-cli/releases)
2. Place it in a directory of your choice
3. Create an alias or script to run it easily

#### Windows (PowerShell)
```powershell
# Create an alias (temporary - for current session)
Set-Alias kando "java -jar C:\path\to\kando.jar"

# Or create a permanent batch file
echo @echo off > kando.bat
echo java -jar "C:\path\to\kando.jar" %* >> kando.bat
# Move kando.bat to a directory in your PATH
```

#### macOS/Linux (Bash/Zsh)
```bash
# Add to your ~/.bashrc or ~/.zshrc
alias kando='java -jar /path/to/kando.jar'

# Or create a script
echo '#!/bin/bash' > /usr/local/bin/kando
echo 'java -jar /path/to/kando.jar "$@"' >> /usr/local/bin/kando
chmod +x /usr/local/bin/kando
```

### Option 2: Build from Source

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/kando-cli.git
   cd kando-cli
   ```

2. **Build with Maven**
   ```bash
   mvn clean package
   ```

3. **Run the application**
   ```bash
   java -jar target/kando-cli-1.0-SNAPSHOT.jar
   ```

### Option 3: Install with Maven (Development)

1. Clone and build as above
2. Install to local Maven repository:
   ```bash
   mvn clean install
   ```

3. Run with Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="com.kando.App"
   ```

## Quick Start

1. **Create your first project**
   ```bash
   kando new "My Project"
   ```

2. **Add some tasks**
   ```bash
   kando add-task "Setup development environment"
   kando add-task "Write documentation"
   kando add-task "Implement feature X"
   ```

3. **View your board**
   ```bash
   kando show
   ```

4. **Move tasks between columns**
   ```bash
   kando move 1 --col in-progress
   kando move 2 --col done
   ```

## Usage

```bash
# Start a new project
$ kando new "Website Redesign"
Created new project: Website Redesign
Switched to project: Website Redesign

# Add some tasks
$ kando add-task "Create wireframes"
Added task [1] "Create wireframes" to column: todo

$ kando add-task "Design homepage"
Added task [2] "Design homepage" to column: todo

$ kando add-task "Implement responsive layout"
Added task [3] "Implement responsive layout" to column: todo

# View the board
$ kando show
todo                 in-progress          done
────────────────────────────────────────────────────────
[1] Create wireframes

[2] Design homepage

[3] Implement responsive layout


Active Project: Website Redesign

# Move a task to in-progress
$ kando move 1 --col in-progress
Moved task [1] "Create wireframes" to column: in-progress

# Complete a task
$ kando move 1 --col done
Moved task [1] "Create wireframes" to column: done
```

## Data Storage

Kando CLI stores all data locally in your home directory:

- **Windows**: `%USERPROFILE%\.kando\data.json`
- **macOS/Linux**: `~/.kando/data.json`

The data is stored in JSON format and includes:
- All your projects
- Tasks with their IDs, titles, and current columns
- Active project information

## Commands Reference

| Command | Description | Example |
|---------|-------------|---------|
| `kando new <name>` | Create new project | `kando new "My Project"` |
| `kando list` | List all projects | `kando list` |
| `kando switch <name>` | Switch to project | `kando switch "Other Project"` |
| `kando add-task <title>` | Add new task | `kando add-task "Fix bug"` |
| `kando show` | Display board | `kando show` |
| `kando move <id> --col <column>` | Move task | `kando move 1 --col done` |
| `kando rename <id> <title>` | Rename task | `kando rename 1 "New title"` |
| `kando delete <id>` | Delete task | `kando delete 1` |
| `kando --help` | Show help | `kando --help` |
| `kando --version` | Show version | `kando --version` |

### Development Setup

1. Clone the repository
2. Ensure you have Java 17+ and Maven 3.6+
3. Run tests: `mvn test`
4. Build: `mvn clean package`
5. Run: `java -jar target/kando-cli-1.0-SNAPSHOT.jar`

## Technology Stack

- **Java 17** - Core language
- **Maven** - Build tool and dependency management
- **PicoCLI** - Command-line interface framework
- **Gson** - JSON serialization/deserialization
- **JUnit** - Testing framework

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
