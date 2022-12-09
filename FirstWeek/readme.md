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


- What is ReentrantReadWriteLock? How does it differ from ReentrantLock? When
  might you use it ?


- What is a “spurious wakeup” ? When can one happen and why doesn’t a
  well-written program care if one does ?


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
