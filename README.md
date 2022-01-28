# Bowling Score

This project has the primary goal to read a file containing bowling plays, calculate the match score and print the
results to stdout.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

What things you need to install

```
Java 11 installed and added to command line
Maven installed and added to command line
```

### Installing

```
git clone https://github.com/danikyl/Bowling-Score.git
cd 'Bowling-Score'
``` 

### Running tests

```
mvn test
``` 

### Compiling and running the application

```
mvn package
``` 

Now the .jar is generated inside the target folder. To run the jar you need to specify a file containing the bowling
plays. You can find valid files examples at "src/test/resources/positive" and invalid files examples at "
src/test/resources/negative"
You can also specify a output file for the java program. If you do so, the match scores will be printed in the specified
file, but if you choose to not specify a output file, the results will be printed in the console. So let's run the
application. In the project root folder run:

```
java -jar target/java-challenge-0.0.1-SNAPSHOT.jar ./src/test/resources/positive/scores.txt > outputfile.csv
``` 

If you check the project root folder, you will now find the outputfile.csv there containing the match results.

![Result File Example](https://i.imgur.com/vspYA7S.png)

## Authors

* **Daniel Fucci**
