# 🚀 High-Concurrency Distributed Flash Sale Platform

A lightweight, high-performance distributed flash sale and e-commerce backend built with **Spring Boot** and **Spring Cloud**[cite: 1, 8]. It is architected to handle massive traffic spikes during promotional events (e.g., Prime Day sales), focusing on low latency, strong data consistency, and horizontal scalability[cite: 4, 7].

---

## 🛠️ Tech Stack

- **Core Framework**: Java 8/17, Spring Boot, Spring Cloud (Consul, OpenFeign, Gateway)[cite: 1, 8]
- **Database & Sharding**: MySQL 8.0, MyBatis, Sharding-JDBC (ShardingSphere), Snowflake ID[cite: 1, 3, 7]
- **Caching & Locking**: Redis (Jedis), Lua Scripting, Redis Distributed Lock[cite: 4]
- **Message Queue**: Apache RocketMQ (Async Peak Shaving & Delay Messages)[cite: 5]
- **Search Engine**: Elasticsearch 7.4 + IK Analyzer (Full-text tokenization)[cite: 6]
- **Resilience**: Alibaba Sentinel (QPS Flow Rate Limiting)[cite: 7]

---

## ⚡ Core Highlights & Engineering Solutions

### 1. Flash Sale & Zero-Overselling Architecture
- **Pre-warmed Cache**: Product inventory is preloaded into Redis before sales launch[cite: 4].
- **Atomic Pre-deduction**: Executes atomic Lua scripts (`exists` -> `get` -> `decr`) in Redis memory, filtering out 95%+ invalid traffic before reaching the database[cite: 4].
- **Distributed Lock**: Utilizes `SET key requestId NX PX` with Lua verification to prevent race conditions across distributed nodes[cite: 4].

### 2. Asynchronous Peak Shaving & Auto-Cancellation
- **Write Buffer**: After Redis stock validation, orders are pushed to **RocketMQ** for decoupled, asynchronous database persistence[cite: 4, 5].
- **Delayed Rollback**: Uses RocketMQ delay-level messages to automatically cancel unpaid orders after 10 minutes and roll back locked inventory[cite: 3, 5].

### 3. Full-Text Search Optimization
- Replaced unindexed MySQL `LIKE` queries with **Elasticsearch**[cite: 6].
- Implemented `ik_smart` tokenization and `multiMatchQuery` over product names and descriptions for fast search response[cite: 6].

### 4. Database Sharding & Distributed ID
- **Horizontal Sharding**: Sharded databases by `user_id` and tables by `order_id` via **Sharding-JDBC** to remove single-node storage and I/O bottlenecks[cite: 7].
- **Unique Primary Keys**: Integrated 64-bit **Snowflake ID** algorithm to guarantee globally unique, chronologically ordered keys without collision[cite: 7].

### 5. Traffic Throttling & Protection
- Applied **Alibaba Sentinel** QPS rate limiting at the API Gateway and controller layers to defend against traffic spikes and malicious scraping[cite: 7].

---

## 📋 Quick Setup

```bash
# 1. Start core middleware
consul agent -dev &
redis-server &
mqnamesrv & mqbroker -n localhost:9876 autoCreateTopic=true &
./bin/elasticsearch &

# 2. Build & run services
mvn clean package -DskipTests
java -jar ServiceProvider/target/ServiceProvider-0.0.1-SNAPSHOT.jar &
java -jar OnlineShoppingGateway/target/OnlineShoppingGateway-0.0.1-SNAPSHOT.jar &