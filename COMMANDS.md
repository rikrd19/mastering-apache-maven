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

Recursively traverses the directory tree starting from the specified location and lists matching filesystem entries.

For example:

```bash
find . -type f
```

`.` means the current directory.

`-type f` limits the result to regular files.

Without additional filtering, `find` can enumerate files and directories recursively. It does not execute anything merely by listing them.

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

- Maven version
- Maven installation directory
- Java version used by Maven
- Java home
- Operating system information

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

### `brew list --versions`

Lists installed Homebrew packages and their versions.

```bash
brew list --versions | grep -E 'openjdk|maven'
```

Example:

```text
maven 3.9.16
openjdk@17 17.0.9
openjdk@21 21.0.12.1
openjdk@25 25.0.4
openjdk 26.0.1
```

The exact installed versions depend on the environment.

---

### `export JAVA_HOME`

Defines which JDK installation should be used through `JAVA_HOME`.

On macOS:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v <version>)
```

For example:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

This sets `JAVA_HOME` to the selected JDK.

---

### `export PATH`

Places the selected JDK's binaries at the beginning of `PATH`.

```bash
export PATH=$JAVA_HOME/bin:$PATH
```

Together, these commands allow the shell and tools such as Maven to use the selected JDK:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH=$JAVA_HOME/bin:$PATH
```

`JAVA_HOME` identifies the JDK.

`PATH` determines which `java` and `javac` executables are found first.

---

### `javac`

`javac` is the Java compiler.

It takes `.java` source code and produces `.class` files containing JVM bytecode.

For example:

```bash
javac App.java
```

The resulting `.class` files contain bytecode that can be executed by a compatible JVM.

A single `.java` file can generate multiple `.class` files, for example when inner or anonymous classes are used.

When compiling code that depends on other classes, `javac` requires the appropriate classpath.

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

- modified files
- new files
- deleted files
- staged changes
- the current branch

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

Commit messages in this course should clearly describe the change.

---

## 4. Maven Installation and Project Generation

### `mvn archetype:generate`

Generates a new Maven project from an archetype.

```bash
mvn archetype:generate
```

Without additional parameters, Maven enters interactive mode and presents options for generating a project.

---

### Maven archetype

An archetype is a project template.

An archetype can generate a predefined Maven project structure such as a simple Java project, a web project, a Maven plugin or other project types.

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

Important parameters:

- `groupId` → identifies the project's namespace.
- `artifactId` → identifies the project.
- `archetypeArtifactId` → specifies the project template.
- `archetypeVersion` → specifies the version of the template.
- `interactiveMode=false` → runs Maven without interactive questions.

---

### `-D`

The `-D` syntax defines a Java system property.

For example:

```bash
-DgroupId=com.renzo.maven
```

Conceptually, Maven receives a property equivalent to:

```java
System.getProperty("groupId")
```

Maven plugins can read these properties as parameters.

This mechanism allows plugin configuration to be supplied from the command line.

---

## 5. Maven Project Structure

The main Maven project used in the course is `hello-maven`.

Its relevant structure is:

```text
hello-maven/
├── .mvn/
│   ├── maven.config
│   └── jvm.config
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── renzo/
    │   │           └── maven/
    │   │               └── App.java
    │   └── resources/
    └── test/
        └── java/
            └── com/
                └── renzo/
                    └── maven/
                        └── AppTest.java
```

### Important files and directories

`pom.xml`

The main Maven project configuration file.

`.mvn/`

Project-level Maven configuration directory.

`src/main/java/`

Contains application source code.

`src/main/resources/`

Contains application resources.

`src/test/java/`

Contains test source code.

`target/`

Contains build output generated by Maven.

Examples include:

```text
target/classes/
target/test-classes/
target/surefire-reports/
target/*.jar
```

`target/` is generated output and should not normally be committed to Git.

---

## 6. Maven Project and Java Versions

### Project version vs Java version

These are different concepts.

The following:

```xml
<version>1.0.0-SNAPSHOT</version>
```

is the version of the Maven project.

The following:

```xml
<maven.compiler.release>17</maven.compiler.release>
```

defines the Java release targeted by project compilation.

`SNAPSHOT` indicates a development version of the Maven artifact.

It does not refer to a Java version.

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

Maven can run using one JDK while the Maven compiler plugin targets another Java release, provided the installed JDK/toolchain supports the requested compilation target.

---

## 7. Planned Java LTS Laboratory

The course will progressively include projects targeting the following Java versions:

```text
Java 8
Java 11
Java 17
Java 21
```

Java 21 will be the main modern reference version.

Java 8 and Java 11 will be used to study legacy and enterprise compatibility.

Java 17 will represent the transition to modern enterprise Java.

Java 26 remains an installed current/experimental JDK and can be used when demonstrating JDK compatibility and version selection.

---

# 8. Maven Lifecycle

Maven provides multiple lifecycles.

The main Maven lifecycles are:

```text
clean
default
site
```

The `default` lifecycle contains the main build phases.

---

## `mvn validate`

Executes the `validate` phase of the Maven `default` lifecycle.

```bash
mvn validate
```

The `validate` phase checks that the project is correctly configured and that the required information is available before the build continues.

---

## `mvn compile`

Executes the Maven `default` lifecycle up to and including the `compile` phase.

```bash
mvn compile
```

For a Java project, it compiles source code from:

```text
src/main/java/
```

and places compiled classes under:

```text
target/classes/
```

It does not compile or execute tests.

---

## `mvn test`

Executes the Maven `default` lifecycle up to and including the `test` phase.

```bash
mvn test
```

For the Java project, Maven:

- compiles the main source code
- compiles test source code
- executes tests through the Maven Surefire Plugin

Test reports are normally generated under:

```text
target/surefire-reports/
```

---

## `mvn package`

Executes the Maven `default` lifecycle up to and including the `package` phase.

```bash
mvn package
```

For a project with:

```xml
<packaging>jar</packaging>
```

Maven creates a JAR artifact under:

```text
target/
```

For example:

```text
target/hello-maven-1.0.0-SNAPSHOT.jar
```

`package` creates the artifact for the current project.

It does not install that artifact into the local Maven repository.

---

## `mvn verify`

Executes the Maven `default` lifecycle up to and including the `verify` phase.

```bash
mvn verify
```

`verify` occurs after `package` in the default lifecycle.

It allows additional verification steps to run before an artifact is installed or deployed.

Running `verify` does not install the artifact into the local Maven repository.

---

## `mvn install`

Executes the Maven `default` lifecycle up to and including the `install` phase.

```bash
mvn install
```

In addition to building the project, Maven installs the project's artifact and POM into the local repository.

The default local repository is:

```text
~/.m2/repository/
```

For example:

```text
~/.m2/repository/com/renzo/maven/hello-maven/1.0.0-SNAPSHOT/
```

Important distinction:

`install` installs the **current project's artifact** into the local repository.

It does not mean "install all project dependencies."

---

## `mvn deploy`

Executes the Maven `default` lifecycle up to and including the `deploy` phase.

```bash
mvn deploy
```

The `deploy` phase publishes the project artifact to a configured remote Maven repository.

A project needs appropriate repository configuration, such as `distributionManagement`, or an alternative deployment repository supplied through Maven configuration.

If no deployment repository is configured, deployment fails.

---

## `mvn clean`

Executes the `clean` lifecycle.

```bash
mvn clean
```

The clean lifecycle is separate from the default build lifecycle.

For a standard Maven project, `clean` removes generated build output such as:

```text
target/
```

It does not remove:

```text
src/
pom.xml
~/.m2/repository/
```

---

## `mvn clean package`

Executes the `clean` lifecycle and then the `default` lifecycle up to `package`.

```bash
mvn clean package
```

Conceptually:

```text
clean
  ↓
remove previous build output
  ↓
package
  ↓
validate → compile → test → package
```

This is a common command when a clean rebuild is required.

---

# 9. Maven Phases, Plugins and Goals

These concepts should not be confused.

```text
Lifecycle
    ↓
contains phases

Phase
    ↓
represents a stage of the build

Plugin
    ↓
provides build functionality

Goal
    ↓
specific operation provided by a plugin
```

---

## Phase

A phase represents a stage in a Maven lifecycle.

Examples:

```text
validate
compile
test
package
verify
install
deploy
```

For example:

```bash
mvn package
```

Here:

```text
package = lifecycle phase
```

Maven executes the lifecycle phases required to reach `package`.

---

## Plugin

Plugins provide Maven's build functionality.

Examples:

```text
maven-compiler-plugin
maven-surefire-plugin
maven-jar-plugin
maven-dependency-plugin
```

---

## Goal

A goal is a specific operation provided by a plugin.

For example:

```text
maven-compiler-plugin:compile
```

The plugin provides the `compile` goal.

---

## Lifecycle binding

Maven can bind plugin goals to lifecycle phases.

For a JAR project, examples include:

```text
compile phase
    ↓
maven-compiler-plugin:compile

test phase
    ↓
maven-surefire-plugin:test

package phase
    ↓
maven-jar-plugin:jar

install phase
    ↓
maven-install-plugin:install

deploy phase
    ↓
maven-deploy-plugin:deploy
```

The exact plugin versions and bindings depend on Maven configuration, packaging and plugin configuration.

---

## Direct plugin goal execution

A plugin goal can also be executed directly without specifying a lifecycle phase.

Example:

```bash
mvn dependency:tree
```

Here:

```text
dependency = plugin
tree       = goal
```

It is therefore a plugin goal invocation, not a lifecycle phase.

---

# 10. Maven Dependency Commands

## `mvn dependency:tree`

Displays the project's dependency tree.

```bash
mvn dependency:tree
```

It shows direct and transitive dependencies.

Example:

```text
com.renzo.maven:app:jar:1.0.0-SNAPSHOT
+- com.renzo.maven:core:jar:1.0.0-SNAPSHOT:compile
\- org.apache.commons:commons-lang3:jar:3.17.0:compile
```

The notation:

```text
dependency:3.7.0:tree
```

means:

- Plugin: `maven-dependency-plugin`
- Version: `3.7.0`
- Goal: `tree`

---

# 11. Maven POM Investigation

## `mvn help:effective-pom`

Displays the effective POM.

```bash
mvn help:effective-pom
```

The effective POM represents Maven's resulting project configuration after processing elements such as:

- inheritance
- interpolation
- profiles
- plugin configuration
- dependency management

It is useful when investigating what Maven is actually using.

For example, it can reveal lifecycle executions:

```xml
<execution>
    <id>default-compile</id>
    <phase>compile</phase>
    <goals>
        <goal>compile</goal>
    </goals>
</execution>
```

It can also reveal inherited properties and plugin configuration.

---

## `mvn help:effective-pom -Doutput=effective-pom.xml`

Writes the effective POM to a file.

```bash
mvn help:effective-pom -Doutput=effective-pom.xml
```

This is useful for detailed inspection of Maven's effective configuration.

Generated investigation files such as `effective-pom.xml` should not automatically be committed to the project repository.

---

## `mvn help:describe`

Displays detailed information about a Maven plugin or goal.

Example:

```bash
mvn help:describe \
  -Dplugin=org.apache.maven.plugins:maven-compiler-plugin \
  -Ddetail=true \
  -Dgoal=compile
```

This can show:

- plugin information
- goal information
- lifecycle phase binding
- available parameters
- user properties

For example, the compiler plugin's `compile` goal exposes the `release` parameter.

---

## `mvn help:evaluate`

Evaluates a Maven expression.

Example:

```bash
mvn help:evaluate -Dexpression=environment -Pdev -q -DforceStdout
```

This is useful for inspecting Maven properties and profile values.

---

# 12. Maven Plugin Configuration

## `<pluginManagement>`

`<pluginManagement>` is used to manage plugin versions and configuration.

Example:

```xml
<pluginManagement>
    <plugins>
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
        </plugin>
    </plugins>
</pluginManagement>
```

A plugin appearing only inside `pluginManagement` is not automatically executed merely because it is declared there.

`pluginManagement` provides configuration that can be used by actual plugin declarations.

---

## `<plugins>`

`<plugins>` explicitly declares plugins used by the project.

Example:

```xml
<plugins>
    <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
    </plugin>
</plugins>
```

A plugin declared here can contain configuration that controls how its goals are executed.

Example:

```xml
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <release>17</release>
    </configuration>
</plugin>
```

This configures the compiler plugin to target Java 17.

---

# 13. Maven Dependencies and Dependency Management

## `<dependencies>`

`<dependencies>` declares dependencies that the project actually uses.

Example:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.17.0</version>
    </dependency>
</dependencies>
```

The dependency becomes part of the project's dependency graph.

---

## `<dependencyManagement>`

`<dependencyManagement>` centralizes dependency versions and configuration.

Example:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.17.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

`dependencyManagement` does **not** automatically add the dependency to the project.

A module still needs to declare the dependency under `<dependencies>`:

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
```

The version can then be omitted because it is supplied by `dependencyManagement`.

The distinction is:

```text
dependencyManagement
    ↓
controls the version/configuration

dependencies
    ↓
declares actual usage
```

---

# 14. Maven Multi-Module Projects

A multi-module Maven project contains multiple Maven projects built together through a root project.

Example structure:

```text
13-multi-module/
├── pom.xml
├── core/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
└── app/
    ├── pom.xml
    └── src/
        └── main/
            └── java/
```

---

## Root `pom.xml`

The root project uses:

```xml
<packaging>pom</packaging>
```

and declares modules:

```xml
<modules>
    <module>core</module>
    <module>app</module>
</modules>
```

This is called **aggregation**.

The root project tells Maven which modules belong to the reactor build.

---

## `mvn clean package` from the root

Running:

```bash
mvn clean package
```

from the multi-module root builds the reactor.

Example reactor order:

```text
Multi Module Demo [pom]
Core              [jar]
App               [jar]
```

Maven determines the correct build order based on the project relationships.

---

## Maven Reactor

The reactor is Maven's mechanism for building multiple related projects together.

In this project:

```text
Root
 ├── Core
 └── App
```

`App` depends on `Core`.

Therefore Maven builds:

```text
Root
  ↓
Core
  ↓
App
```

before completing the reactor build.

---

## Parent POM and inheritance

A child module can declare the root POM as its parent:

```xml
<parent>
    <groupId>com.renzo.maven</groupId>
    <artifactId>multi-module-demo</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>
```

This allows the child to inherit configuration from the parent.

For example, the parent can define:

```xml
<properties>
    <maven.compiler.release>17</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

and child modules can inherit these properties.

---

## Aggregation vs inheritance vs dependency

These are three different relationships.

### Aggregation

The root lists modules:

```xml
<modules>
    <module>core</module>
    <module>app</module>
</modules>
```

This controls which projects are built together.

### Inheritance

The child declares:

```xml
<parent>
    ...
</parent>
```

This allows configuration to be inherited.

### Dependency

`app` declares:

```xml
<dependency>
    <groupId>com.renzo.maven</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

This means `app` actually depends on the artifact produced by `core`.

These concepts can exist simultaneously but they are not the same thing.

---

# 15. Maven Command-Line Error and Debug Options

## `-e`

Enables execution error messages and stack traces.

```bash
mvn compile -e
```

Useful when additional exception information is required.

`-e` does not mean "show Maven phases."

---

## `-X`

Enables Maven debug output.

```bash
mvn compile -X
```

This produces much more detailed diagnostic information.

It is useful for troubleshooting Maven configuration, plugin execution and dependency resolution.

---

# 16. Maven Build Options

## `-DskipTests`

Skips test execution while still compiling test sources.

```bash
mvn package -DskipTests
```

Conceptually:

```text
compile main code
        ↓
compile test code
        ↓
do not execute tests
        ↓
package
```

---

## `-Dmaven.test.skip=true`

Skips both test compilation and test execution.

```bash
mvn package -Dmaven.test.skip=true
```

This is different from:

```bash
-DskipTests
```

because test sources are not compiled when `maven.test.skip=true`.

---

# 17. Maven Build Outputs

Maven normally generates build output under:

```text
target/
```

Examples:

```text
target/classes/
target/test-classes/
target/surefire-reports/
target/*.jar
```

For a JAR project:

```bash
mvn package
```

can produce:

```text
target/hello-maven-1.0.0-SNAPSHOT.jar
```

The artifact name is based on Maven coordinates such as:

```text
artifactId
version
packaging
```

---

# 18. Local Maven Repository

Maven stores downloaded dependencies and locally installed project artifacts in the local repository.

Default location:

```text
~/.m2/repository/
```

For example:

```text
~/.m2/repository/com/renzo/maven/hello-maven/1.0.0-SNAPSHOT/
```

A dependency that is already available locally does not necessarily produce a new download message when Maven builds the project.

This is why a build can successfully use a dependency without displaying a `Downloading...` message.

---

# 19. Important Maven Distinctions

### `package` vs `install`

```text
mvn package
    ↓
builds the artifact under target/

mvn install
    ↓
builds the artifact
    +
installs it into ~/.m2/repository/
```

---

### `verify` vs `install`

```text
mvn verify
    ↓
build and verify
    ↓
does not install the artifact

mvn install
    ↓
build and verify
    ↓
installs the artifact locally
```

---

### Lifecycle phase vs plugin goal

```bash
mvn package
```

`package` is a lifecycle phase.

```bash
mvn dependency:tree
```

`dependency:tree` is a direct plugin goal invocation.

---

### `dependencyManagement` vs `dependencies`

```text
dependencyManagement
    ↓
manages versions/configuration

dependencies
    ↓
declares actual dependencies
```

---

### Maven JDK vs project target

```text
JDK used to run Maven
        ≠
necessarily the Java release targeted by the project
```

---

### Aggregation vs inheritance

```text
Aggregation
    ↓
<modules>

Inheritance
    ↓
<parent>
```

They are related concepts but perform different functions.

---

# 20. Commonly Used Build Commands

Quick reference:

```bash
mvn validate
mvn compile
mvn test
mvn package
mvn verify
mvn install
mvn deploy
mvn clean
mvn clean package
```

Dependency investigation:

```bash
mvn dependency:tree
```

POM investigation:

```bash
mvn help:effective-pom
mvn help:describe
mvn help:evaluate
```

Debugging:

```bash
mvn -e compile
mvn -X compile
```

Skipping tests:

```bash
mvn package -DskipTests
mvn package -Dmaven.test.skip=true
```

Multi-module builds:

```bash
mvn clean package
```

from the reactor root builds all declared modules in the correct reactor order.

More advanced multi-module selection commands such as `-pl` and `-am` will be documented when they are introduced in the course.

## Multi-Module Project Selection

Maven provides command-line options to control which projects are included in a multi-module reactor build.

The examples below use this project structure:

```text
13-multi-module/
├── pom.xml
├── core/
│   └── pom.xml
└── app/
    └── pom.xml
```

`app` depends on `core`.

---

### `-pl` — Projects List

`-pl` selects specific projects from the reactor.

```bash
mvn -pl app package
```

This selects only `app`.

If `app` depends on another reactor module such as `core`, Maven does not automatically build that dependency:

```text
App
 ↓
Core
```

The build can therefore fail if `core` is not already available in a repository.

Multiple projects can be selected using commas:

```bash
mvn -pl core,app package
```

This explicitly selects both `core` and `app`.

Maven still respects their dependency order:

```text
Core
  ↓
App
```

The root aggregator is not automatically included when it is not part of the selected project list.

---

### `-am` — Also Make

`-am` means **also make**.

It builds the selected project together with the reactor projects required by that project.

Example:

```bash
mvn -pl app -am package
```

`app` depends on `core`, so Maven builds:

```text
Core
  ↓
App
```

Conceptually:

```text
-pl app
    ↓
select App

-am
    ↓
also include required reactor dependencies
```

This is useful when working on one module while still needing the modules it depends on.

---

### `-amd` — Also Make Dependents

`-amd` means **also make dependents**.

It builds the selected project together with reactor projects that depend on it.

Example:

```bash
mvn -pl core -amd package
```

Because `app` depends on `core`, Maven builds:

```text
Core
  ↓
App
```

Conceptually:

```text
-pl core
    ↓
select Core

-amd
    ↓
also include projects that depend on Core
```

This is useful when a change to a lower-level module may affect its consumers.

---

### `-f` / `--file`

`-f` tells Maven which POM file to use.

Example:

```bash
mvn -f app/pom.xml package
```

Maven uses `app/pom.xml` as the project POM.

This is different from `-pl`.

`-pl` selects projects from the reactor.

`-f` explicitly identifies the POM Maven should use.

For example:

```text
-pl
 ↓
select a project within the reactor

-f
 ↓
use this specific POM
```

Using:

```bash
mvn -f app/pom.xml package
```

does not automatically cause Maven to build `core`.

If `app` requires `core` and `core` is not available from a repository, dependency resolution can fail.

---

### `-N` / `--non-recursive`

`-N` means **non-recursive**.

It prevents Maven from traversing into child modules.

Example:

```bash
mvn package -N
```

When executed from the multi-module root, Maven processes only the root project:

```text
Multi Module Demo [pom]
```

It does not build:

```text
Core [jar]
App  [jar]
```

Without `-N`:

```bash
mvn package
```

the reactor includes the configured modules:

```text
Multi Module Demo
        ↓
      Core
        ↓
       App
```

With `-N`:

```text
Multi Module Demo
```

only the current project is processed.

---

### Multi-Module Selection Summary

```text
-pl
    Select specific projects.

-pl core,app
    Select multiple specific projects.

-am
    Also build required reactor dependencies.

-amd
    Also build reactor dependents.

-f
    Use a specific POM file.

-N
    Disable recursive processing of child modules.
```

Example combinations:

```bash
mvn -pl app package
```

Select `app`.

```bash
mvn -pl app -am package
```

Select `app` and the reactor modules required by it.

```bash
mvn -pl core -amd package
```

Select `core` and the reactor modules that depend on it.

```bash
mvn -pl core,app package
```

Select both `core` and `app`.

```bash
mvn -f app/pom.xml package
```

Use `app/pom.xml` directly.

```bash
mvn package -N
```

Process only the current project and do not recurse into modules.