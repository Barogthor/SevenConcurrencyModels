- What guarantees does the Java memory model make regarding initialization
  safety ? Is it always necessary to use locks to safely publish objects
  between threads ?


A final variable is assured to be initialized during construction even trying to access the value while the objet is still constructed. 
We can avoid lock for intialization safety just by making sure to not escape the object reference to other threads during construction 
(escape can be done by sending reference to observer, starting a thread ...) 
- What is the double-checked locking anti-pattern? Why is it an anti-pattern ?


It is an anti pattern because the compiler can reorganize instructions so that the double check doesn't exist in the compiled bytecode and still allow return of uninitialized object