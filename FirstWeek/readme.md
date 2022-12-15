# Day one
- What guarantees does the Java memory model make regarding initialization
  safety ? Is it always necessary to use locks to safely publish objects
  between threads ?


A final variable is assured to be initialized during construction even trying to access the value while the objet is still constructed. 
We can avoid lock for intialization safety just by making sure to not escape the object reference to other threads during construction 
(escape can be done by sending reference to observer, starting a thread ...) 
- What is the double-checked locking anti-pattern? Why is it an anti-pattern ?


It is an anti pattern because the compiler can reorganize instructions so that the double check doesn't exist in the compiled bytecode and still allow return of uninitialized object

# Day two

- ReentrantLock supports a fairness parameter. What does it mean for a lock
  to be “fair” ? Why might you choose to use a fair lock? Why might you not ?


Fairness is used to allow the longest waiting thread to have the lock (aka FIFO). But it adds overhead and can be slower than without using fairness. 
Using fairness make sure that some threads are not starving for a long time. But if the program comes to a state that it has starving thread then maybe it's time 
to rethink the number of threads.
- What is ReentrantReadWriteLock? How does it differ from ReentrantLock? When
  might you use it ?


ReentrantLock lock down access whether it is for read or write and only one lock can be alive. 
But ReadWriteLock has both a write lock and read lock. 
It allows any number of read access as long as no write lock is held. 
A write lock can only have one held and it blocks any read access until the write lock is released.
This lock can be useful when a lot of concurrent read access are expected but update are fewer in number and short.
- What is a “spurious wakeup” ? When can one happen and why doesn’t a
  well-written program care if one does ?


A "spurious wakeup" is a thread waiting a condition that get a signal that the condition is fulfilled and can resume its execution 
but when it checks the condition another thread changed the condition and can only wait for another signal. This is a condition race.
A well written program should not have this kind of wake up since if more than one thread is waiting for the condition there will always be at least one thread losing the race. 
Only one thread should receive the signal and not all of them. 
- What is AtomicIntegerFieldUpdater ? How does it differ from AtomicInteger? When
  might you use it ?


- What would happen if the loop within the “dining philosophers” implementation
  that uses condition variables was replaced with a simple if statement ?
  What failure modes might you see ? What would happen if the call to signal()
  was replaced by signalAll() ? What problems (if any) would this cause ?


- Just as intrinsic locks are more limited than ReentrantLock, they also support
  a more limited type of condition variable. Rewrite the dining philosophers
  to use an intrinsic lock plus the wait() and notify() or notifyAll() methods. Why
  is it less efficient than using ReentrantLock ?


- Write a version of ConcurrentSortedList that uses a single lock instead of
  hand-over-hand locking. Benchmark it against the other version. Does
  hand-over-hand locking provide any performance advantage? When might
  it be a good choice ? When might it not ?


The hand over locking add unecessary lock/unlock overhead for single thread use. 
We see its advantage once we try to insert concurrently on multiple thread against the single lock.

# Day 3 

- The documentation for ForkJoinPool—how does a fork/join pool differ from
  a thread pool ? When might you prefer one, and when the other ?


- What is work-stealing and when might it be useful ? How would you
  implement work-stealing with the facilities provided by java.util.concurrent ?


- What is the difference between a CountDownLatch and a CyclicBarrier ? When
  might you use one, and when the other ?


- What is Amdahl’s law? What does it say about the maximum theoretical
  speedup we might be able to get for our word-counting algorithm ?


- Rewrite the producer-consumer code to use a separate “end of data” flag
  instead of a poison pill. Make sure that your solution correctly handles
  the cases where the producer runs faster than the consumer and vice
  versa. What will happen if the consumer has already tried to remove
  something from the queue when the “end of data” flag is set ? Why do you
  think that the poison-pill approach is so commonly used ?


- Run the different versions of the word-count program on your computer,
  as well as any others you can get access to. How do the performance
  graphs differ from one computer to another ? If you could run it on a
  computer with 32 cores, do you think you would see anything close to a
  32x speedup ?