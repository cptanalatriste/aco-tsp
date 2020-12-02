package tsp.isula.sample;

import isula.aco.*;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;
import isula.aco.algorithms.antsystem.PerformEvaporation;
import isula.aco.algorithms.antsystem.RandomNodeSelection;
import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
import isula.aco.tsp.AntForTsp;
import isula.aco.tsp.EdgeWeightType;
import isula.aco.tsp.TspEnvironment;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class solves the Berlin52 instance of the TSPLIB repository using an Ant System algorithm,
 * trying to emulate the procedure present in Section 6.3 of the Clever Algorithms book by
 * Jason Brownlee.
 */
public class AcoTspWithIsula {

    public static final String BERLIN_52_TSP_FILE = "berlin52.tsp"; // Lower bound: 7542
    public static final String ATT_48_TSP_FILE = "att48.tsp"; // Lower bound: 10628

    private static Logger logger = Logger.getLogger(AcoTspWithIsula.class.getName());

    public static void main(String... args) throws IOException, ConfigurationException {
        logger.info("ANT SYSTEM FOR THE TRAVELING SALESMAN PROBLEM");

        String fileName = BERLIN_52_TSP_FILE;
//        String fileName = ATT_48_TSP_FILE;
        logger.info("fileName : " + fileName);

        double[][] problemRepresentation = getRepresentationFromFile(fileName);
        EdgeWeightType edgeWeightType = getEdgeWeightTypeFromFile(fileName);
        TspEnvironment environment = new TspEnvironment(problemRepresentation, edgeWeightType);

        TspProblemConfiguration configurationProvider = new TspProblemConfiguration(environment);
        AntColony<Integer, TspEnvironment> colony = getAntColony(configurationProvider);

        AcoProblemSolver<Integer, TspEnvironment> solver = new AcoProblemSolver<>();
        solver.initialize(environment, colony, configurationProvider);
        solver.addDaemonActions(new StartPheromoneMatrix<>(),
            new PerformEvaporation<>());

        solver.addDaemonActions(getPheromoneUpdatePolicy());

        solver.getAntColony().addAntPolicies(new RandomNodeSelection<>());
        solver.solveProblem();
    }

    private static EdgeWeightType getEdgeWeightTypeFromFile(String fileName) throws IOException {
        File file = new File(Objects.requireNonNull(AcoTspWithIsula.class.getClassLoader().getResource(fileName)).getFile());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("EDGE_WEIGHT_TYPE: EUC_2D")) {
                    return EdgeWeightType.EUCLIDEAN_DISTANCE;
                } else if (line.contains("EDGE_WEIGHT_TYPE: ATT")) {
                    return EdgeWeightType.PSEUDO_EUCLIDEAN_DISTANCE;
                }
            }
        }
        return null;
    }

    /**
     * Produces an Ant Colony instance for the TSP problem.
     *
     * @param configurationProvider Algorithm configuration.
     * @return Ant Colony instance.
     */
    public static AntColony<Integer, TspEnvironment> getAntColony(final ConfigurationProvider configurationProvider) {
        return new AntColony<Integer, TspEnvironment>(configurationProvider.getNumberOfAnts()) {
            @Override
            protected Ant<Integer, TspEnvironment> createAnt(TspEnvironment environment) {
                return new AntForTsp(environment.getNumberOfCities());
            }
        };
    }

    /**
     * On TSP, the pheromone value update procedure depends on the distance of the generated routes.
     *
     * @return A daemon action that implements this procedure.
     */
    private static DaemonAction<Integer, TspEnvironment> getPheromoneUpdatePolicy() {
        return new OfflinePheromoneUpdate<Integer, TspEnvironment>() {
            @Override
            protected double getPheromoneDeposit(Ant<Integer, TspEnvironment> ant,
                                                 Integer positionInSolution,
                                                 Integer solutionComponent,
                                                 TspEnvironment environment,
                                                 ConfigurationProvider configurationProvider) {
                return 1 / ant.getSolutionCost(environment);
            }
        };
    }

    public static double[][] getRepresentationFromFile(String fileName) throws IOException {
        List<Double> xCoordinates = new ArrayList<>();
        List<Double> yCoordinates = new ArrayList<>();
        File file = new File(Objects.requireNonNull(AcoTspWithIsula.class.getClassLoader().getResource(fileName)).getFile());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (tokens.length == 3) {
                    xCoordinates.add(Double.parseDouble(tokens[1]));
                    yCoordinates.add(Double.parseDouble(tokens[2]));
                }
            }
        }

        double[][] representation = new double[xCoordinates.size()][2];
        for (int index = 0; index < xCoordinates.size(); index += 1) {
            representation[index][0] = xCoordinates.get(index);
            representation[index][1] = yCoordinates.get(index);

        }

        return representation;
    }


}
