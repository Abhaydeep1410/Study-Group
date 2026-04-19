### Q1. Why does Spring App consumes more memory over time ?
Ans:  It can be due to  
1. Memory Leak : If memory keeps increasing never drops 
2. Improper caching: If no cache eviction , unlimited cache sizes
3. Thread Leaks: Executor thread not shutdown , too many thread created dynamically
4. Hibernate/JPa: Not clearing persistent context
5. Too many beans/Auto Configuration : unnecessary dependencies , unused starters
6. Logging /Buffering Issue: Huge logs stored in memory before writing
7. Unclosed Resources:  DB COnnections , Input/Output files

### How we can diagnose this
Tools: 
1. HeapDump : Analyze using eclipse MAT
2. Visual VM / Jconsole
3. Actuator mertrics( acutator/mertrics)
WHat to check
1. which objects are growing 
2. garbage collector activities ( frequent or not reclaiming memory)
3. thread count


### Q2. Two users order same product, stock becomes negative (Optimistic & Pessimistic Locking)

Ans.
This happens due to race condition, where multiple users read and update the same data concurrently.

We can solve this using locking mechanisms in Spring Boot with Hibernate:

1. Pessimistic Locking
   Means we first acquire the lock, then perform the operation, and finally release it.
   It locks the database row, so no other transaction can modify it until the lock is released.
   Other users have to wait.

👉 Flow:

User A locks row 🔒
User B waits ⏳
User A updates & releases
User B proceeds

👉 It is heavy and slower, but ensures strong consistency.

2. Optimistic Locking
   Assumes conflicts are rare and does not lock upfront.
   Uses a version field to detect concurrent updates.
   just like compare and swap algo (count,old , new) , only update to new value if current value is equal to old

👉 Flow:

User A & B read version = 1
User A updates → version = 2 ✅
User B tries → ❌ fails (version mismatch)

👉 Throws exception (like OptimisticLockException) → we can retry.

👉 It is fast and scalable, but needs retry handling
ex @Version in spring boot

⚖️ Difference
Pessimistic → Prevent conflict (lock first)
Optimistic → Detect conflict (check later)
✅ Best Practice (Important)

Use atomic DB update:

UPDATE product
SET stock = stock - 1
WHERE id = ? AND stock > 0;
If rows affected = 1 → success
If 0 → out of stock

👉 This is most reliable in production

🧠 Final Line (Interview)

We use pessimistic locking when strict consistency is required and optimistic locking for better performance and scalability.