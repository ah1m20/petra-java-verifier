# petra-java-verifier
Verifier for the Java variant of Petra.

## Overview ##

### What is Petra? ###

* A new way to structure OOP programs which enables efficient and comprehensive formal verification, 
whilst simplifying code so that its easier to validate (e.g using code reviews and testing etc.)
* Petra uses theory from formal verification and has been specifically design from the ground up to be a 
formal method and a programming language.
* ```petra-java-verifier``` is the first implementation of Petra which verifies Java 8 OOP programs written using an 
expressive subset of Java 8.

## How to program in Petra? ##

There are some notions in Petra which should be understood in order to learn Petra more easily.

## Notions ##

### Verification ###
Proves absence of errors. 
A mathematical / logical process which provides a proof of system correctness.
Correctness is defined by the verification system, for example the Java compiler can verify that
a typed variable cannot be assigned to a value of the wrong type. Compilers for OOP languages in general 
only provide a weak set of guarantees and these do not easily relate to the behaviour of a program with respect 
to a domain problem. Functional languages are better in this respect however they do not scale well for large systems,
which have many sub-systems, and those which are more naturally modelled with objects and/or state, and often the variety/complexity 
of code written in functional languages leads to systems which are hard to understand and verify.

### Validation ###
Proves presence of errors.
Formal verification methods aim to provide very strong correctness guarantees, 
however even a comprehensively verified system can still go wrong, if there is a mismatch between the system 
and the context it is being used in. For example a verified automobile should not be used as a floatation device. 
This is addressed by validation. Validation includes review and testing processed 
which allows stakeholders to gain confidence in the system. 
Validation is used to increase the likely-hood that a system does what it was intended to do.
Code in formal methods must be easy to validate in order for the method to be useful.
This requires the code to be as modular as possible (for testing) and as close to natural language as possible (for reviews).

### V&V ###
Verification and Validation (V&V) are both required to ensure systems meet the expectation of stakeholders.

### Top down / Bottom up reasoning ###
Petra composes programs using a hierarchical composition of both data and control flow.
When programming it can be useful to think in terms of both top-down decomposition
of a domain and bottom-up composition of abstractions over primitive values.

## Petra's features ##

Petra provides strong correctness guarantees which include:
Determinism for both sequential and parallel programs (which means programs behave like functions) and
Reachability of states (all methods guarantee that given an input, the method terminates and the result is an output,
as specified by the method's contract pre- and post-conditions). These checks are fully automated so the developer
does have to perform any manual proof steps and therefore does not have to be an expert in formal methods.
The checks have also designed to be efficient to compute such that they scale well for systems of any size.
In addition, Petra offers a fluid way to express domain problems within code in a way that allows for domain level instructions
to be verified, and Petra code can directly translate into a controlled English,
which is a subset of english which is used to remove ambiguity.

### Example: Light System ###

##### Main #####
This is used to start a Petra system.
```java
import static com.cognitionbox.petra.ast.interp.util.Program.start;

public class Main {
    public static void main(String[] args){
        start(new Light());
    }
}
```

#### Light (entry point) ####
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

### Explaination ###

The state symbolic space of the Light object is given by the Cartesian product of the symbolic states 
of Power and Control (which are defined by the boolean methods) i.e.
```Power = {on,off}``` and
```Control = {on,off}``` therefore
```Light = Power X Control = {(on,on), (on,off), (off,on), (off,off)}```.
The ```toggle``` method has two cases, one for going from ```off``` to ```on``` and the other from ```on``` to ```off```.
Each case is correct as the composition of method calls in each result in transitions which respect the
pre/post conditions of the case. This is also true for the methods in the ```Power``` and ```Control``` objects.

### How do I get set up? ###

#### Development Environment ####

Clone the repo by running ```git clone https://github.com/ah1m20/petra-java-verifier``` then if previously not installed
download and install both Java JDK (8 or greater) and the Maven build tool.
We recommend to use JetBrains IntelliJ IDEA (https://www.jetbrains.com/idea/download/) as an IDE for Java development and to use its built in Maven integration, however
Maven can be downloaded separately from https://maven.apache.org/download.cgi and can be installed using instructions from 
https://maven.apache.org/install.html


#### Maven Dependancies ####

Once cloned, use a command line to navigate to the project's root directory and run 
```mvn clean install``` to build the project jar.
Then install the jar to your local maven repo by using the command below and filling in the correct path to the built .jar file (which can be found by looking at the log output of the previous step):
```mvn install:install-file –Dfile=<path-to-jar-in-local-maven-repo> -DgroupId=com.cognitionbox.petra -DartifactId=petra-java-verification -Dversion=1.0-SNAPSHOT``` see below for example.
##### Example #####
```mvn install:install-file –Dfile=C:\Users\xyz\.m2\com\cognitionbox\petra\petra-java-verifier\1.0-SNAPSHOT\petra-java-verifier-1.0-SNAPSHOT.jar -DgroupId=com.cognitionbox.petra -DartifactId=petra-java-verification -Dversion=1.0-SNAPSHOT```

Then add this maven dependency to your Java project:
```
<dependency>
    <groupId>com.cognitionbox.petra</groupId>
    <artifactId>petra-java-verification</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Then within your Java project test folder, create a verification class for the entry point of the program you would like to verify (see below for example).
```java
@RunWith(Parameterized.class)
public class LightVerification extends Verification {
    public LightVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Light.class);
    }
}
```
Then run the above class in order to run the Petra verification process for the chosen entry point.

### Use Cases ###
Petra is well suited to back-end server processing, including but not limited to:
modelling and execution of critical workflows for business processes, AI, machine learning,
smart contract execution, blockchain applications, infrastructure orchestration and task coordination.

### JVM language support ###
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
