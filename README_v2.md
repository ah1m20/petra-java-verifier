# petra-java-verifier
Verifier for the Java variant of Petra.

[![version alpha-SNAPSHOT](https://img.shields.io/badge/version-alpha--SNAPSHOT-orange?style=shield)](https://github.com/cognitionbox/petra)
[![Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-brightgreen?style=shield)](https://www.apache.org/licenses/LICENSE-2.0) [![<CognitionBox>](https://circleci.com/gh/cognitionbox/petra.svg?style=shield)](https://circleci.com/gh/cognitionbox) [![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=shield)](https://github.com/cognitionbox/petra)

## Overview ##

### What is Petra? ###

* A new way to structure OOP programs for Verification & Validation oriented programming.
* A verifier for Java 8 OOP programs written using the Petra standard.
* Can be implemented with any language that supports lambdas and Java style interfaces
* Supports parallel processing and aims to support distributed computing.

Petra is a concise and expressive object-oriented
general purpose workflow programming language which aims to make large,
state-oriented, parallel and distributed programs easier to code and verify.                         
The aim of Petra is to provide a modern programming paradigm,
a concise language and tooling to meet today's complex software needs.
Petra has verification in mind at it has been designed from the ground up to
produce safe, regularized, concise, object-orientated, parallel, distributed systems,
which are easy to reason about and to minimize and detected programming errors,
whilst being a practical tool for industry. Petra's reference implementation is a
general purpose embedded style language in Java 8, with built-in automatic software
verification features. The embedded style language has been designed in a way that makes
it feel like a native language. Most embedded style languages in Java use method chaining,
however Petra uses a function sequence pattern for better readability and a simpler
implementation. Petra programs are easy to reason about and they are constructed in a
declarative manor. Petra has parallel processing built into its core and a program can be
executed in sequential or parallel, whilst maintaining the same semantics across these modes.

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

### Objects ###
Objects contain one or more boolean methods which are disjoint from each other.
Objects are either base or not, base objects contain primitive data types,
non-primitive views contain other views or a single collection of a specific view.
Multiple objects are composed using Cartesian product of sets.

### Object Soundness ###

A view is sound iff each of its non-primitive views are sound and it contains only disjoint
boolean methods. Consider a view named Light which contains a Power view of boolean methods,
on and off, composed with a Button view, on and off. These views both contain logically disjoint boolean methods
which is part way to view soundness. To be fully sound the Light view would need to provide
disjoint boolean methods which capture less than or equal to the entire product of
Button and Light (which represents the observable state space).

E.g.

let Light contains boolean methods on and off.

{% raw %}
```
Light = {on,off}

Button = {on,off}

Power = {on,off}

Button X Power = {(on,on), (on,off), (off,on), (off,off)}
```
{% endraw %}

The propositional formulas of Light's boolean methods needs to form a general function
(a function that is not injective, surjective or bijective) between the product
Button X Power and the Light's boolean methods,
so that all states are mapped to a higher level abstraction.

```
boolean on(){return power.on() && button.on();} // {(on,on)}
boolean off(){return power.off() || button.off();} // {(off,on),(on,off),(off,off)}
```

As we can see here both the resulting sets are disjoint and their
sum is less than or equal to the product Button X Power, and therefore we have a sound view.

### Kase Soundness ###
All kase predconditions within a kases block must cover sets of states which are disjoint.,
i.e. below we can see that ```light.on()``` covers ```{(on,on)}``` and ```light.off()``` covers ```{(on,off), (off,on), (off,off)}```
and these sets are disjoint, hence the kases are sound.

### Kase Completeness ###
The root graph/edge in a Petra program must satisfy kase completeness,
i.e. the graph/edge must have kase preconditions which cover the underlying state space of the view which the graph/edge acts on.
E.g. consider the Light view and assume that the root graph/edge is toggle.
We can see that the toggle's kase preconditions cover the Light view's state space which is the product ```Button X Power = {(on,on), (on,off), (off,on), (off,off)}``` as
```light.on()``` covers ```{(on,on)}``` and ```light.off()``` covers ```{(on,off), (off,on), (off,off)}```.

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

### @Graph Reachability & Refinement ###
Given all the views are sound and the kases are complete. The next step is to show that
for any input, there will be a matching kase precondition, and after matching the input
will be transformed by the steps of the matched kase, such that the matched kase postcondition
will become satisfied. In combination with the soundness and completeness checks this is
what makes a Petra program valid. If this is shown for every graph in program which is used by the root graph,
then we have demonstrated both reachability, refinement and therefore the functional correctness of the system.
This check along with the above soundness and completeness checks are automated by Petra's
verification system, however a developer should understand how the checks work in order to
program more efficiently.

#### Translations ####

In order for Petra to reason about reachability, each graph within a Petra program is
translated into only sequential steps. This is achieved by the following rules.
Please note a lighter weight version of the syntax is used to make it easier to present the rules.
The lighter weight syntax omits parenthesis for calls to zero-arg methods and omits lamdas.

#### 1. Kase Combination ####
For each of the graph's kases, each step within the kase, has its kases combined.
They are combined into a single kase whos pre and post condition is the disjoint sum of the
pre/post conditions respectively.

E.g. a step containing two kases
```kase(light.on, light.off, {...})``` and
```kase(light.off, light.on, {...})```, translates to a single kase
```kase(light.on ^ light.off, light.off ^ light.on, {...})```


#### 2. Seq Steps Combination ####
Two or more sequential steps (seq) are translated into a single
sequential step if the first steps post-condition implies the second steps pre-condition,
i.e. if the set of states covered by the first steps post-condition is contained by or is equal to the
set of states covered by the second steps pre-condition.
E.g. ```seq(x, kase(e.on, e.off, {...}))``` followed by ```seq(x, kase(e.off, e.on, {...}))``` translates to
```seq(x, kase(e.on, e.on, {...}))```


#### 3. Seperated Seq Steps Combination ####
Two or more sequential steps (seq) that operate on seperate memory regions are translated into a single
sequential step which aggregates the pre/post conditions by logically conjunction.

E.g. a kase containing two steps
```seq(light.button, kase(button.on, button.off, {...}))``` and
```seq(light.power, kase(power.on, power.off, {...}))``` translates to
```seq(light, kase(button.on & power.on, button.off & power.off, {...}))```


#### 4. Seperated Par Steps within Join ####
Two or more parrallel steps (par) that operate on seperate memory regions are
translated into a single sequential step which aggregates the pre/post conditions
by logically conjunction.

E.g. a kase containing two steps
```par(light.button, kase(button.on, button.off))``` and
```par(light.power, kase(power.on, power.off, {...}))``` translates to
```seq(light, kase(button.on & power.on, button.off & power.off, {...}))```

### Reachability & Refinement Checks ###
Once translated into sequential steps, reachability can be check by simply applying the pre/post
conditions to the kases current symbolic state until there are no more steps left to use or we cannot
progress.
E.g. lets consider a kase which acts on the Light view, with pre/post condition,
light.on, light.off respectively, and has translated down to the following 3 steps:
```
seq(light, kase(button.on & power.on, button.off & power.off, {...}))
seq(light, kase(button.off & power.off, button.on & power.on, {...}))
seq(light, kase(button.on & power.on, button.off & power.off, {...}))
```

As previously the state space of the Light view is given by the Cartesian product of Button X Power.
```Light = Button X Power = {(on,on), (on,off), (off,on), (off,off)}```

The first step initializes the kase's view to its matching state: ```{(on,on)}```

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