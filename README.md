# petra-java-verifier
Verifier for the Java variant of Petra.

## Overview ##

### What is Petra? ###

* A new way to structure OOP programs for Verification & Validation oriented programming.
* A verifier for Java 8 OOP programs written using the Petra standard.
* Can be implemented with any language that supports lambdas and Java style interfaces.

## How to program in Petra? ##

There are some notions in Petra which should be understood in order to learn Petra more easily.

## Notions ##

### Verification ###
Proves absence of errors. A mathematical / logical process which provides a proof of system correctness.
Correctness is defined by the verification system in use, for example the Java compiler can verify that
a typed variable cannot be assigned to a value of the wrong type. Compilers in general only provide a weak set of
guarantees that cannot be easily related to verifying the behaviour of a program with respect to a domain problem.

### Validation ###
Proves presence of errors.
Formal verification methods aim to provide very strong correctness guarantees, however even with these methods a verified system
can still go wrong as it can be used if there is a mismatch between itself and the context it is being used in.
For example a verified automobile should not be used as a floatation device. This is where validation comes in and
code in formal methods must be easy to validate in order for the method to be useful.
This requires the code as close to natural language as possible.
A review and testing process which allows stakeholders to gain confidence in the system.
Validation should be used to increase the likely-hood that a system does what it was intended to do.

### V&V ###
Verification and Validation (V&V) are both required to ensure systems meet the expectation of stakeholders.

### Top down / Bottom up reasoning ###
Petra composes programs using a hierarchical composition of both data and control flow.
When programming it can be useful to think in terms of both top-down decomposition
of a domain and bottom-up composition.

#### Example: Light System ####

##### Light #####
```java
public class Light implements Runnable {
    
    private final Power power = new Power();
    private final Control control = new Control();

    public boolean on() { return power.on() && control.on(); }
    public boolean off() { return power.off() || control.off(); }

    public void run() {
        if (off()){
            power.turnOn();
            control.turnOn();
            assert(on());
        } else if (on()){
            par(()->power.turnOff(), ()->control.turnOff());
            assert(off());
        }
    }
}
```

##### Power #####
```java
@Base
public class Power {
   private final Bool bool = new Bool();
   
   public boolean on() { return bool.isTrue(); }
   public boolean off() { return bool.isFalse(); }

   public void turnOn() {
      if (on() ^ off()){
         bool.setTrue();
         assert(on());
      }
   }

   public void turnOff() {
      if (on() ^ off()){
         bool.setFalse();
         assert(off());
      }
   }
}
```

##### Control #####
```java
@Base
public class Control {
   private final Bool bool = new Bool();
   public boolean on() { return bool.isTrue(); }
   public boolean off() { return bool.isFalse(); }

   public void turnOn() {
      if (on() ^ off()){
         bool.setTrue();
         assert(on());
      }
   }

   public void turnOff() {
      if (on() ^ off()){
         bool.setFalse();
         assert(off());
      }
   }
}
```

```java
public class Light implements Runnable {

    //...
   
   public void toggle() {
      if (off()){
         power.turnOn();
         control.turnOn();
         assert(on());
      } else if (on()){
         par(()->power.turnOff(), ()->control.turnOff());
         assert(off());
      }
   }
    
}
```

The state symbolic space of the Light object is given by the Cartesian product of Button X Power.
```Light = Button X Power = {(on,on), (on,off), (off,on), (off,off)}```

1) Executing the 1st step is successful as ```button.on & power.on``` filters Light to produce ```{(on,on)}```
   which contains or is equal to the current state ```{(on,on)}```, and therefore the current state is
   replaced with ```{(off,off)}```.

2) Executing the 2nd step is successful as ```button.off & power.off``` filters Light to produce ```{(off,off)}```
   which contains or is equal to the current state ```{(off,off)}```, and therefore the current state is
   replaced with ```{(on,on)}```.

3) Executing the 3rd step is successful as button.on & power.on filters Light to produce ```{(on,on)}```
   which contains or is equal to the current state ```{(on,on)}```, and therefore the current state is
   replaced with ```{(off,off)}```.

At this point all steps have been used and the final state is ```{(off,off)}```.
This means these steps have successfully moved the state from the kase's precondition to its
postcondition and therefore we have proved reachability and refinement in one go.

### How do I get set up? ###

Clone the repo and navigate to the project's root directory then do
```mvn clean install``` to build and install the project jars to your local maven repo.

Then add this maven dependency to your project:

```
<dependency>
    <groupId>com.cognitionbox.petra</groupId>
    <artifactId>petra-java-verification</artifactId>
    <version>alpha-SNAPSHOT</version>
</dependency>
```

### Use Cases ###
Petra is well suited to back-end processing, including but not limited to,
critical business processes, infrastructure orchestration, task coordination,
modelling and execution of workflows for business processes, AI, machine learning,
smart contract execution and blockchain applications.

### Petra Components, Operations & Features ###

##### Entry Point #####
This is used to start a Petra system and is invoked from the Java main method.
```java
public class PetraExample {
    public static void main(String[] args){
        
        // The line below starts a Petra graph named toggle within the Light view and
        // takes an instance of Light (via the instantiation model, see below) as input and changes its state.
        
        Petra.start(new Light());
    }
}
```

##### JVM language support #####

###### Java 8 ######
The petra-java-verifier has been written in Java 8,
which parses and verifiers a subset of Java 8 which conforms to Petra's standard for OOP.

### Future Work ###
Petra aims to support verification of mapping elements of collections and
verification of a restricted form of while-loop.

### Who do I talk to? ###

The idea of Petra was originally thought up by Aran Hakki in 2011.
Since then he has been researching and developing the system.
He started a PhD in Computer Science at the University of Southampton
in May 2020, with the aim of formalizing the Petra system.
Aran originally read MEng Systems Engineering at Warwick University
and has been a software engineer for over 10 years.


If you are interested in this project and believe you could benefit
from this technology please get in touch.

* ah1m20@soton.ac.uk
* 07399472347