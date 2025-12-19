# Order Service with Spring Cloud & RabbitMQ

This project demonstrates a simple Microservices architecture using **Spring Boot** and **RabbitMQ**. It implements a Producer-Consumer pattern where the Order Service publishes order events to a RabbitMQ queue, and a Consumer (within the same app for demonstration) processes them.

## 🚀 Tech Stack
*   **Java 17**
*   **Spring Boot 3.x**
*   **Spring AMQP (RabbitMQ)**
*   **Docker**
*   **Lombok**

## 🛠️ Prerequisites
*   Java 17+ Installed
*   Docker Installed

## 🐇 Step 1: Start RabbitMQ
Before running the application, you must have RabbitMQ running. We use Docker for this.

```bash
docker run -d --name usis-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
*   **Dashboard**: [http://localhost:15672](http://localhost:15672) (User: `guest`, Pass: `guest`)

## 🏃‍♂️ Step 2: Run the Application

### Option A: Run Locally (Maven)
```bash
./mvnw spring-boot:run
```
The application will start on port `8080`.

### Option B: Run with Docker
1.  **Build the JAR**:
    ```bash
    ./mvnw clean package -DskipTests
    ```
2.  **Build the Image**:
    ```bash
    docker build -t order-service .
    ```
3.  **Run the Container**:
    ```bash
    docker run -d -p 8080:8080 --name order-service --link usis-rabbit:rabbitmq -e SPRING_RABBITMQ_HOST=rabbitmq order-service
    ```

## 🧪 Step 3: Test the API
Send a POST request to place an order. This will trigger the **Producer** to send a message and the **Consumer** to print it.

```bash
curl -X POST http://localhost:8080/order \
    -H "Content-Type: application/json" \
    -d '{"name": "Pizza", "qty": 1, "price": 12.5}'
```

**Expected Output (Application Logs):**
```
Message Received from Queue: Order(orderId=..., name=Pizza, qty=1, price=12.5)
```

## 📂 Project Structure
*   **`config/RabbitMQConfig`**: Configures the Exchange (`order_exchange`), Queue (`order_queue`), and Binding. Also defines the JSON MessageConverter.
*   **`controller/OrderController`**: REST Controller that receives the HTTP request and publishes the message.
*   **`consumer/OrderConsumer`**: Listens to `order_queue` and processes messages.
*   **`dto/Order`**: Data Transfer Object shared between Producer and Consumer.
