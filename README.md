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
