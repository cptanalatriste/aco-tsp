package tsp.isula.sample;

import isula.aco.ConfigurationProvider;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class ProblemConfiguration implements ConfigurationProvider {

    public String getFileName() {
        return "C:\\Users\\Carlos G. Gavidia\\git\\aco-tsp\\src\\main\\resources\\berlin52.tsp";
    }

    public int getNumberOfAnts() {
        return 10;
    }

    public double getEvaporationRatio() {
        return 0.6;
    }

    public int getNumberOfIterations() {
        return 50;
    }


    public double getInitialPheromoneValue() {
        return 0.0;
    }


}
