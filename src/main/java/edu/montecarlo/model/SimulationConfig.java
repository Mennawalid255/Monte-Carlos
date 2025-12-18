package edu.montecarlo.model;

public class SimulationConfig {
    private final long totalPoints; 
    private final int numTasks; 
    private final int numThreads; 

    public SimulationConfig(long totalPoints, int numTasks, int numThreads) {
        this.totalPoints = totalPoints;
        this.numTasks = numTasks;
        this.numThreads = numThreads;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public int getNumThreads() {
        return numThreads;
    }

    @Override
    public String toString() {
        return String.format("Config[points=%,d, tasks=%d, threads=%d]",
                totalPoints, numTasks, numThreads);
    }
}
