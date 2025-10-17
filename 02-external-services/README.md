## External Services

To simplify our learning process and focus only on **Java**, **Concurrency**, and **Virtual Thread** concepts, I have created a JAR file named `external-services.jar`.

Running this JAR will expose mocked API endpoints for:

* `product-service`
* `rating-service`
* `trip-planning-service`
* `flight-search-service`

These services are heavily mocked and hardcoded. They are **not part of the exercise** They only exist to support our learning.

## How to Run

1. Make sure you have **Java 21 or above** installed.
2. Download [this JAR](https://github.com/vinsguru/java-virtual-thread-course/raw/refs/heads/master/02-external-services/external-services.jar).
3. Place the downloaded JAR anywhere on your machine.
4. Open a terminal or command prompt and navigate to the folder where the JAR is located.
5. Run:

   ```bash
   java -jar external-services.jar
   ```
6. By default, the server runs on **port 7070**.
7. You can access the Swagger UI here: [http://localhost:7070/swagger-ui/](http://localhost:7070/swagger-ui/)

## Using Docker (Optional)

If you prefer not to run the JAR directly on your machine, you can use Docker.

**Dockerfile**

```dockerfile
FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR app
ADD https://github.com/vinsguru/java-virtual-thread-course/raw/refs/heads/master/02-external-services/external-services.jar external-services.jar
CMD java -jar external-services.jar
```

Then build and run the image with port mapping:

```bash
docker build -t external-services .
docker run -p 7070:7070 external-services
```

## Changing the Port

To run the server on a different port:

```bash
java -jar external-services.jar --server.port=8080
```