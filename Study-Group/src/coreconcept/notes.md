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