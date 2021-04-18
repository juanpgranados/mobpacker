# Mobpacker :package:  
Mobpacker is a Java Library project which solves the following packaging problem:  
  
>You want to send your friend a package with different things.  
Each thing you put inside the package has such parameters as index number, weight and cost. The  
package has a weight limit. Your goal is to determine which things to put into the package so that the  
total weight is less than or equal to the package limit and the total cost is as large as possible.  
  
That problem matches with the well-known knapsack problem.  
   
  
### Requirements:memo:  
* JDK 11  
* Maven 3.6.3

## How to use :computer:
Once you have cloned the repository, you can install it on your maven repository by maven command line
```bash
mvn install  
```
Include the maven dependency in your project ``pom.xml`` file
```xml
<dependency>
    <groupId>com.mobiquity</groupId>
    <artifactId>packer</artifactId>
    <version>1.0.0</version>  
</dependency>
```
Import Packer class
```java
import com.mobiquity.packer.Packer;
```
Use the library by calling pack method
```java
Packer.pack("/input/file/path");
```
## Unit tests execution :satellite:
This library has unit tests built with JUnit 5. They validate logic cases and also exception cases.
Unit tests can be run by the command
```bash
mvn test  
```
In addition, when unit tests are executed, Jacoco plugin checks the code coverage and generates a report. You can check that by opening the file
```
target/site/jacoco/index.html
```
Jacoco has the parameter of cover ratio, if it is not met, the build will fail. You can find it in the ``pom.xml`` file.
## Aproach :eyeglasses:
### Algorithm :chart:
After research how to solve the knapsack problem, I found one of the best ways is to use dynamic programming. It consists of making a data table where you put the gain of each possible solution. You iterate over the table increasing the knapsack capacity and looking for the best combination for each item of the list. See also [Wikipedia article](https://en.wikipedia.org/wiki/Knapsack_problem).

The dynamic programming solution algorithm was designed to work with integer values, thus I improved it to work with floating-point weight values. Some logic was added in order to decide which cell should be selected to continue the algorithm execution.

Also, other algorithms can be implemented, the current structure allows new implementations.

### Source code structure :chart:
All packages and classes have been defined following *SOLID* principles and separated concerns.

Some Domain-driven Design concepts were used for object naming and testing.

Package list under ``com.mobiquity``

 - :file_folder:**exception** Exception classes
 - :file_folder:**knapsack** Contains knapsack problem processors
 - :file_folder:**model** Data objects
 - :file_folder:**packer** Main package. Contains Packer class
 - :file_folder:**parser** Helper classes for parsing files
 - :file_folder:**validator** Helper classes for validating data

New knapsack solution algorithms can be added by a new class that implements the ``KnapsackProcessor`` interface.
### How it works :chart:
This diagram represents how the library works when is called.

![sequence](https://github.com/juanpgranados/mobpacker/blob/master/sequence_diagram.png?raw=true)

### Other decisions :heavy_check_mark:

 - Weight and cost are stored as BigDecimal in order to obtain better precision.
 - An exception is thrown if any constraint is not met.
 - Code has been scanned with SonarLint looking for code smell to fix.
 - TDD practice was used.

## Key libraries and tools :wrench: :hammer:

 - **Java** Programming language.
 - **Maven** Build automation tool.
 -  **JUnit** Unit testing framework.
 - **JaCoCo** Java code coverage tool.
 - **Lombok** Java library to avoid write repetitive code.
