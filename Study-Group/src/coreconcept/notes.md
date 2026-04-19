# 📘 Spring Boot Interview Notes

---

## ❓ Q1. Why does a Spring App consume more memory over time?

**Answer:** It can be due to:

1. **Memory Leak**

    * Memory keeps increasing and never drops
    * Objects are retained and not garbage collected

2. **Improper Caching**

    * No cache eviction
    * Unlimited cache size

3. **Thread Leaks**

    * ExecutorService not shutdown
    * Too many threads created dynamically

4. **Hibernate / JPA Issues**

    * Persistence context not cleared in large/batch operations

5. **Too Many Beans / Auto Configuration**

    * Unnecessary dependencies
    * Unused starters increasing memory footprint

6. **Logging / Buffering Issues**

    * Large logs stored in memory before writing

7. **Unclosed Resources**

    * DB connections
    * Input/Output streams

---

### 🔍 How to Diagnose

#### 🛠️ Tools

* Heap Dump → Analyze using Eclipse MAT
* VisualVM / JConsole
* Actuator Metrics → `/actuator/metrics`

#### ✅ What to Check

* Which objects are growing continuously
* Garbage Collector activity (frequent / ineffective)
* Thread count (unexpected increase)

---

## ❓ Q2. Two users order same product, stock becomes negative

### (Optimistic & Pessimistic Locking)

**Answer:**
This happens due to a **race condition**, where multiple users read and update the same data concurrently.

We can solve this using locking mechanisms in Spring Boot with Hibernate.

---

### 🔒 1. Pessimistic Locking

* Acquire lock first → perform operation → release lock
* Locks the database row
* Other users must wait

#### 🔄 Flow

```text
User A locks row 🔒  
User B waits ⏳  
User A updates & releases  
User B proceeds  
```

#### ✅ Characteristics

* Strong consistency
* Slower / heavy
* Can cause blocking

---

### ⚡ 2. Optimistic Locking

* Assumes conflicts are rare
* No lock upfront
* Uses **version field**

👉 Similar to **Compare-And-Swap (CAS)**
(Update only if current value matches expected value)

#### 🔄 Flow

```text
User A & B read version = 1  
User A updates → version = 2 ✅  
User B tries → ❌ fails (version mismatch)  
```

#### ✅ Characteristics

* Fast and scalable
* No blocking
* Requires retry logic
* Example: `@Version` annotation

---

### ⚖️ Difference

| Feature     | Optimistic Locking | Pessimistic Locking |
| ----------- | ------------------ | ------------------- |
| Approach    | Detect conflict    | Prevent conflict    |
| Locking     | ❌ No               | ✅ Yes               |
| Performance | Fast               | Slower              |
| Waiting     | ❌ No               | ✅ Yes               |

---

### ✅ Best Practice (Production)

```sql
UPDATE product
SET stock = stock - 1
WHERE id = ? AND stock > 0;
```

* Rows affected = 1 → success
* Rows affected = 0 → out of stock

👉 Atomic and most reliable approach

---

### 🧠 Final Interview Line

> Use pessimistic locking when strict consistency is required and optimistic locking for better performance and scalability.

---

## ❓ Q3. How do you detect bean initialization issues in large applications?

---

### 🔍 1. Check Application Logs (First Step)

Look for:

* `BeanCreationException`
* `UnsatisfiedDependencyException`
* `NoSuchBeanDefinitionException`

👉 Helps identify failing bean and root cause

---

### ⚙️ 2. Enable Debug Mode

```properties
debug=true
```

👉 Shows auto-configuration report (created/skipped beans)

---

### 📊 3. Use Actuator

**Endpoint:**

```text
/actuator/beans
```

👉 Shows:

* All beans
* Dependencies
* Scope

---

### 🔄 4. Detect Circular Dependencies

**Example:**

```text
A → B → A
```

**Error:**

```text
BeanCurrentlyInCreationException
```

#### ✅ Fix:

* Constructor injection
* `@Lazy`
* Refactor design

---

### 🧪 5. Use Debugging / Breakpoints

* Add breakpoints in constructor or `@PostConstruct`
* Check initialization flow and null dependencies

---

### 🔁 6. Check Bean Lifecycle Hooks

```java
@PostConstruct
public void init() {
    // log initialization
}
```

👉 Helps track initialization order

---

### 🧩 7. Profile Issues

```java
@Profile("prod")
```

👉 Bean not created if profile is inactive

---

### ⚠️ 8. Configuration Issues

* Missing `@Component`, `@Service`, etc.
* Wrong package scanning
* Incorrect `@ComponentScan`

---

### 🧠 9. Large Application Strategy

* Isolate modules
* Disable components to find failing part
* Trace dependency chain

---

### 🧠 Final Interview Answer

> To detect bean initialization issues, analyze startup logs, enable debug mode, use Actuator `/beans`, and check for circular dependencies or configuration issues. In large systems, isolate modules and debug step-by-step.

---

## ❓ Q4. How to handle 10x traffic spikes?

---

### ✅ Key Strategies

1. **Horizontal Scaling**

    * Run multiple instances behind load balancer (e.g., NGINX)

2. **Auto Scaling (Cloud)**

    * Automatically increase/decrease instances (e.g., Amazon Web Services)

3. **Caching**

    * Use Redis to reduce DB load

4. **Async Processing**

    * Use queues like Apache Kafka
    * Offload heavy tasks

5. **Database Optimization**

    * Indexing
    * Connection pooling
    * Read replicas

6. **Rate Limiting**

    * Prevent system overload

7. **Graceful Degradation**

    * Disable non-critical features under load

8. **Circuit Breaker**

    * Use Resilience4j

9. **Monitoring**

    * Prometheus + Grafana

10. **Load Testing**

* Use JMeter

---

### 🧠 Final Interview Line

> Handle 10x traffic by scaling horizontally, caching frequently accessed data, optimizing database usage, applying rate limiting, and offloading heavy tasks asynchronously.

---

## ❓ Q5. How to check if Database Connection Pool is Exhausted?

---

### 🚨 Symptoms

* Slow APIs / requests hanging
* Errors like:

```text
Connection is not available, request timed out
HikariPool - Connection is not available
```

---

### 🔍 Detection

#### 1. Enable Pool Logs

```properties
logging.level.com.zaxxer.hikari=DEBUG
```

---

#### 2. Use Actuator Metrics

Endpoint:

```text
/actuator/metrics/hikaricp.connections
```

👉 Check:

* `active` → used connections
* `idle` → free connections
* `pending` → waiting threads

👉 If:

* active = max
* pending > 0

➡️ Pool exhausted

---

#### 3. Check DB Side

```sql
SHOW PROCESSLIST;
```

---

#### 4. Thread Dump (Advanced)

* Threads in **WAITING** state for connection

---

### ⚠️ Root Causes

* Connection leaks (not closed)
* Slow queries
* Long transactions
* High traffic
* Small pool size

---

### 🛠️ Fixes

* Increase pool size carefully
* Enable leak detection
* Optimize queries
* Reduce transaction scope
* Use read replicas

---

### 🧠 Final Interview Line

> I detect connection pool exhaustion using Hikari metrics, logs, and thread analysis. If active connections hit max and requests are waiting, the pool is exhausted.

---
