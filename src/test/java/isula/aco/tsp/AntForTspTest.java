package isula.aco.tsp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static isula.aco.tsp.AntForTsp.getDistance;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AntForTspTest {

    private TspEnvironment environment;
    private AntForTsp ant;

    @BeforeEach
    void setUp() {
        double[][] problemRepresentation = {{1, 1},
            {2, 2},
            {2, 3},
            {3, 3},
            {3, 2},
            {3, 1}};

        environment = new TspEnvironment(problemRepresentation, EdgeWeightType.EUCLIDEAN_DISTANCE);
        ant = new AntForTsp(environment.getNumberOfCities());
    }

    @Test
    void testGetSolutionCost() {

        ant.clear();
        ant.visitNode(0, environment);
        ant.visitNode(1, environment);
        ant.visitNode(2, environment);
        ant.visitNode(3, environment);
        ant.visitNode(4, environment);
        ant.visitNode(5, environment);

        double expectedCost = Math.round(6 + Math.sqrt(2));
        assertEquals(expectedCost, ant.getSolutionCost(environment));


    }

    @Test
    void testGetDistance() {

        double expectedDistance = Math.round(Math.sqrt(2));
        assertEquals(expectedDistance, getDistance(0, 1, environment));

        expectedDistance = 1.0;
        assertEquals(expectedDistance, getDistance(1, 2, environment));

    }
}