# Command Reference

This file contains the commands used throughout the Apache Maven course.

The reference is organized by topic and updated progressively as new commands are introduced.

---

## 1. Terminal

### `pwd`

Shows the current working directory.

```bash
pwd
```

Useful for confirming where you are before executing commands.

---

### `ls -la`

Lists the contents of the current directory, including hidden files.

```bash
ls -la
```

`-l` shows detailed information and `-a` includes hidden files such as `.mvn` and `.git`.

---

### `cd`

Changes the current working directory.

```bash
cd hello-maven
```

To move up one directory:

```bash
cd ..
```

---

### `find`
It recursively traverses the directory tree starting from the specific point (.=current directory) and lists everything it finds(files, directories, symlinks, devices, etc.)
It performs no filtering, does not search by name, and executes nothing. It simply enumerates. The output is one path per line, relative to the starting point.

Searches for files and directories.

```bash
find . -type f
```

`.` means the current directory.

`-type f` limits the result to files.

---

### `cat`

Displays the contents of a text file in the terminal.

```bash
cat pom.xml
```

Useful for quickly inspecting configuration and documentation files.

---

### `touch`

Creates an empty file if it does not already exist.

```bash
touch COMMANDS.md
```

---

## 2. Environment and Java

### `java -version`

Displays the Java runtime version currently available through the `java` command.

```bash
java -version
```

---

### `javac -version`

Displays the version of the Java compiler.

```bash
javac -version
```

---

### `mvn -version`

Displays Maven and Java environment information.

```bash
mvn -version
```

It shows, among other things:

* Maven version
* Maven installation directory
* Java version used by Maven
* Java home
* Operating system information

---

### `which`

Shows which executable will be used when a command is executed.

```bash
which java
which javac
which mvn
```

This is useful for understanding how `PATH` determines which installation is being used.

---

### `echo`

Displays the value of a variable.

```bash
echo "$JAVA_HOME"
```

---

## 3. Git

### `git --version`

Displays the installed Git version.

```bash
git --version
```

---

### `git status`

Shows the current state of the Git working tree.

```bash
git status
```

Useful for seeing:

* modified files
* new files
* deleted files
* staged changes
* the current branch

---

### `git add`

Stages changes for the next commit.

```bash
git add .
```

`.` means the current directory and its contents.

---

### `git commit`

Creates a Git commit from the staged changes.

```bash
git commit -m "feat: create initial Maven project with quickstart archetype"
```

The `-m` option allows the commit message to be specified directly.

---

## 4. Maven

```bash
brew list --versions | grep -E 'openjdk|maven'
results in:
```
```bash
maven 3.9.16
openjdk@26.0.1
openjdk@17 17.0.9
openjdk@21 21.0.12.1
openjdk@25 25.0.4
```

---
`javac` is the Java compiler. It takes `.java` source code and produces `.class` files containing bytecode for the JVM. The name comes from *Java compiler*; the trailing `c` stands for *compiler*. Bytecode is a portable intermediate format: the JVM interprets it and/or JIT-compiles it to machine code at runtime. A single `.java` file can generate multiple `.class` files (inner classes, anonymous classes, etc.), and `javac` requires the correct classpath to resolve dependencies on other classes.

---

The first command defines the location of JDK <version>
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v <version>)
```
The second command ensures that the binaries (java, javac) for this version are executed by default:
```bash
export PATH=$JAVA_HOME/bin:$PATH
```
Without the second command, JAVA_HOME is defined, but running java -version might still point to a different version in your PATH. By using both, the entire system (the shell, Maven, and other tools) sees the exact same version.


### `mvn archetype:generate`

Generates a new Maven project from an archetype.

```bash
mvn archetype:generate
```

Without additional parameters, Maven enters interactive mode and presents a catalog of available archetypes.

----
`An archetype` is a project template. There are hundreds of them. Some are official, others are published by third parties on Maven Central. Each one generates a different structure: a simple Java project, a web project, a Spring Boot module, a Maven plugin, a multi-module project, etc.

---

### `mvn archetype:generate` with explicit parameters

A project can be generated without interactive questions by providing the required parameters.

```bash
mvn archetype:generate \
  -DgroupId=com.renzo.maven \
  -DartifactId=hello-maven \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.5 \
  -DinteractiveMode=false
```
---
The `mvn` script is a shell wrapper that internally launches a JVM and passes arguments to it. `-D` stands for *Define* (it defines a system property); it interprets any argument of the form `-Dkey=value` as a system property (`System.getProperty("key")`).

## Why Maven Uses This Mechanism

Because there is no formal Maven CLI for passing arbitrary parameters to plugins. Maven is a plugin framework; each plugin defines which parameters it accepts. Instead of inventing a syntax like `mvn archetype:generate --group-id=...`, Maven reuses the JVM's native mechanism: system properties.

Rule of thumb: everything that follows `-D` on the `mvn` command line is a parameter that some plugin will read. The `-Dparameter=value` convention is exactly the same as in `java -D...`.

---

Important parameters:

* `groupId` → identifies the project namespace.
* `artifactId` → identifies the project.
* `archetypeArtifactId` → specifies the project template.
* `archetypeVersion` → specifies the version of the template.
* `interactiveMode=false` → runs Maven without interactive questions.

---

## 5. Maven Project Structure

The first Maven project generated in this course contains:

```text
hello-maven/
├── .mvn/
│   ├── maven.config
│   └── jvm.config
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── ricardo/
    │               └── maven/
    │                   └── App.java
    └── test/
        └── java/
            └── com/
                └── ricardos/
                    └── maven/
                        └── AppTest.java
```

### Important files

`pom.xml`

The main Maven project configuration file.

`.mvn/`

Project-level Maven configuration directory.

`src/main/java/`

Contains application source code.

`src/test/java/`

Contains test source code.

---

## 6. Concepts Already Introduced

### Project version vs Java version

These are different concepts.

```xml
<version>1.0-SNAPSHOT</version>
```

is the version of the Maven project.

```xml
<maven.compiler.release>17</maven.compiler.release>
```

defines the Java release targeted by the project compilation.

`SNAPSHOT` indicates a development version. It does not refer to a Java version.

---

### JDK running Maven vs Java target

The JDK used to execute Maven does not necessarily have to be the same Java version targeted by the project.

For example:

```text
JDK running Maven
        ↓
Java 26

Project compilation target
        ↓
Java 17
```

This distinction will be explored further in the course.

---

## 7. Planned Java LTS Laboratory

The course will progressively include projects targeting the following Java LTS versions:

```text
Java 8
Java 11
Java 17
Java 21
```

Java 21 will be the main modern reference version.

Java 8 and Java 11 will be used to study legacy and enterprise compatibility, while Java 17 will represent the transition to modern enterprise Java.

Java 26 remains installed as the current/experimental JDK and will be used when useful for demonstrating JDK compatibility and version selection.
