# aco-tsp
A Java Program that solves the Travelling Salesman Problem using the Ant System algorithm. The algorithm details and configuration where taken from in Section 6.3 of Clever Algorithms by Jason Brownlee.

In the same fashion as the book, we use the berlin52 instance from TSPLIB as a testbed for the program.

The Ant-Colony Algorithm
------------------------
This program uses a classic Ant System approach with some peculiarities for the Travelling Salesman Problem described in the book. To implement this algorithm, we use the Isula Framework.

```java
        double[][] problemRepresentation = getRepresentationFromFile(fileName);

        ProblemConfiguration configurationProvider = new ProblemConfiguration(problemRepresentation);
        AntColony<Integer, TspEnvironment> colony = getAntColony(configurationProvider);
        TspEnvironment environment = new TspEnvironment(problemRepresentation);

        AcoProblemSolver<Integer, TspEnvironment> solver = new AcoProblemSolver<>();
        solver.initialize(environment, colony, configurationProvider);
        solver.addDaemonActions(new StartPheromoneMatrix<Integer, TspEnvironment>(),
                new PerformEvaporation<Integer, TspEnvironment>());

        solver.addDaemonActions(getPheromoneUpdatePolicy());

        solver.getAntColony().addAntPolicies(new RandomNodeSelection<Integer, TspEnvironment>());
        solver.solveProblem();
```
The implemented process has the following characteristics:
* The initial value of the cells of the pheromone matrix is a function of the number of cities in the problem instance and the quality of a random solution. See the ProblemConfiguration class for more details.
* The evaporation ratio for the algorithm is 0.6 as described in the book. When all the ants have finished building their solutions, they deposit pheromone in the corresponding cells of the pheromone matrix it in a quantity proportional to the solution quality.
* The Ants use the Random Proportional Rule for selecting solution components while traversing the problem graph, as it corresponds on an Ant System algorithm.

The results 
-----------
For the berlin52 problem instance, the optional solution has a total distance of 7542 units. Under the current configuration, the solutions produced by the algorithm are around 10100 after an execution time of 0.5 seconds.
