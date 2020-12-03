package tsp.isula.sample;

import isula.aco.tsp.EdgeWeightType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AcoTspWithIsulaTest {

    @Test
    void getEdgeWeightTypeFromFile() throws IOException {

        String euclideanTspFile = "berlin52.tsp";
        EdgeWeightType actualWeightType = AcoTspWithIsula.getEdgeWeightTypeFromFile(euclideanTspFile);
        assertEquals(EdgeWeightType.EUCLIDEAN_DISTANCE, actualWeightType);

        String pseudoEuclideanTspFile = "att48.tsp";
        actualWeightType = AcoTspWithIsula.getEdgeWeightTypeFromFile(pseudoEuclideanTspFile);
        assertEquals(EdgeWeightType.PSEUDO_EUCLIDEAN_DISTANCE, actualWeightType);
    }
}