# Lab 14 — Spring Boot and Maven Packaging

## Objective

Build, package, run and inspect a Spring Boot application using Apache Maven and the Java command line.

This laboratory demonstrates the relationship between Maven's build lifecycle, dependency resolution, Spring Boot executable JAR packaging and HTTP endpoint verification.

## Technologies

- Java
- Apache Maven
- Spring Boot
- Embedded Apache Tomcat
- HTTP and curl

## Project Configuration

The project uses Spring Boot `4.1.1` and configures Java compilation through the `java.version` property in the POM.

The application exposes a basic HTTP endpoint at `/`.

## Application Endpoint

The application uses `@RestController` and `@GetMapping("/")` to handle HTTP GET requests to the root path.

Expected response:

```text
Spring Boot build verified!
```

## 1. Build the Application

Run the following command from the project directory:

```bash
mvn clean package
```

- `clean` removes the previous build output.
- `package` compiles the source code and packages the application.
- The Spring Boot Maven Plugin repackages the conventional JAR as an executable JAR.

Expected artifact:

```text
target/spring-boot-demo-1.0.0-SNAPSHOT.jar
```

The build also preserves the original JAR as:

```text
target/spring-boot-demo-1.0.0-SNAPSHOT.jar.original
```

## 2. Run the Executable JAR

```bash
java -jar target/spring-boot-demo-1.0.0-SNAPSHOT.jar
```

Spring Boot starts the application and its embedded Tomcat server.

Expected behavior:

- The application starts successfully.
- Tomcat listens on port `8080`.
- The process remains running while the server is active.

## 3. Verify the HTTP Endpoint

Open another terminal and execute:

```bash
curl -i http://localhost:8080/
```

Expected response:

```text
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8

Spring Boot build verified!
```

The HTTP status `200` confirms that the request was handled successfully.

## 4. Inspect the Executable JAR

```bash
jar tf target/spring-boot-demo-1.0.0-SNAPSHOT.jar
```

This command lists the entries inside the JAR without executing or modifying it.

Important paths:

| Path                               | Purpose                               |
| ---------------------------------- | ------------------------------------- |
| `META-INF/MANIFEST.MF`             | JAR metadata and launch configuration |
| `BOOT-INF/classes/`                | Compiled application classes          |
| `BOOT-INF/lib/`                    | Nested dependency JARs                |
| `org/springframework/boot/loader/` | Spring Boot launch mechanism          |
## 5. Inspect the Manifest

```bash
unzip -p target/spring-boot-demo-1.0.0-SNAPSHOT.jar META-INF/MANIFEST.MF
```

Important entries:

- `Main-Class`: `org.springframework.boot.loader.launch.JarLauncher`
- `Start-Class`: `com.renzo.maven.SpringBootDemoApplication`
- `Spring-Boot-Classes`: `BOOT-INF/classes/`
- `Spring-Boot-Lib`: `BOOT-INF/lib/`

`Main-Class` identifies the launcher started by `java -jar`. `Start-Class` identifies the application's main class.

## 6. Compare the Original and Repackaged JARs

Inspect the original artifact:

```bash
jar tf target/spring-boot-demo-1.0.0-SNAPSHOT.jar.original
```

The original JAR contains the compiled application class and Maven metadata. The repackaged executable JAR additionally contains the Spring Boot launcher and nested dependencies.

The Spring Boot Maven Plugin preserves the original artifact and replaces the main JAR with the executable version.

Use `jar tf` to inspect archive contents. Use `java -jar` to verify that the executable JAR starts, and `curl` to verify the HTTP endpoint.

## 7. Rebuild After a Source Code Change

Modify the response returned by the application endpoint in `SpringBootDemoApplication.java`.

Rebuild the application:

```bash
mvn clean package
```

Run the newly packaged JAR:

```bash
java -jar target/spring-boot-demo-1.0.0-SNAPSHOT.jar
```

Verify the updated response from another terminal:

```bash
curl -i http://localhost:8080/
```

Expected response body:

```text
Spring Boot build verified!
```

The source code change is not automatically reflected in an existing JAR. Rebuilding the application generates a new artifact containing the updated compiled code.

## 8. Spring Boot Profiles

Spring Boot profiles allow different configurations for different environments.

Configuration files:

- `src/main/resources/application.properties`: default configuration.
- `src/main/resources/application-dev.properties`: development configuration.
- `src/main/resources/application-prod.properties`: production configuration.

The default port is 8080, the `dev` profile uses port 8081, and the `prod` profile uses port 8082.

Run the development profile:

```bash
mvn -f 14-spring-boot/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

Run the production profile:

```bash
mvn -f 14-spring-boot/pom.xml spring-boot:run -Dspring-boot.run.profiles=prod
```

Spring Boot profiles control runtime configuration. Maven profiles customize Maven build behavior.

## 9. Maven Profiles vs. Spring Boot Profiles

Maven profiles customize the build configuration. Spring Boot profiles
customize the application configuration at runtime.

### Maven Profile

The `dev` Maven profile is defined in `pom.xml` and sets the
`environment.name` property to `Development`.

Activate the Maven profile and inspect the active profiles:

```bash
mvn -f 14-spring-boot/pom.xml help:active-profiles -Pdev
```

Evaluate the Maven property:

```bash
mvn -f 14-spring-boot/pom.xml help:evaluate -Dexpression=environment.name -q -DforceStdout -Pdev
```

Expected output:

```text
Development
```

### Spring Boot Profile

Activate the Spring Boot `dev` profile when running the application:

```bash
mvn -f 14-spring-boot/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

The application uses port 8081 when this profile is active.

### Key Difference

The Maven option `-Pdev` activates a Maven profile. The property `-Dspring-boot.run.profiles=dev` activates a Spring Boot profile through the Spring Boot Maven Plugin.

Activating one profile does not automatically activate the other.
