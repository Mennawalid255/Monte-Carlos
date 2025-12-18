package edu.montecarlo.experiment;

import java.util.ArrayList;
import java.util.List;

import edu.montecarlo.model.ParallelPiEstimator;
import edu.montecarlo.model.PiEstimator;
import edu.montecarlo.model.SequentialPiEstimator;
import edu.montecarlo.model.SimulationConfig;

public class PiExperimentRunner {

    public ExperimentResult runExperiment(PiEstimator estimator,
            SimulationConfig config,
            String type) {
        if (config.getTotalPoints() > 100000) {
            estimator.estimatePi(new SimulationConfig(10000, config.getNumTasks(), config.getNumThreads()));
        }

        long startTime = System.currentTimeMillis();
        double piEstimate = estimator.estimatePi(config);
        long endTime = System.currentTimeMillis();

        long runtime = endTime - startTime;

        return new ExperimentResult(config, piEstimate, runtime, type);
    }


    public List<ExperimentResult> runComprehensiveExperiments(long[] pointsList, int[] threadCounts) {
        List<ExperimentResult> results = new ArrayList<>();

        PiEstimator sequentialEstimator = new SequentialPiEstimator();
        PiEstimator parallelEstimator = new ParallelPiEstimator();

        System.out.println("=== Monte Carlo π Estimation Experiments ===\n");

        for (long points : pointsList) {
            System.out.println("Testing with " + String.format("%,d", points) + " points:");

            // Run sequential 
            SimulationConfig seqConfig = new SimulationConfig(points, 1, 1);
            ExperimentResult seqResult = runExperiment(sequentialEstimator, seqConfig, "Sequential");
            results.add(seqResult);
            System.out.println("  " + seqResult);

            // Run parallel 
            for (int threads : threadCounts) {
                int tasks = threads * 2; // Use 2x tasks as threads for load balancing
                SimulationConfig parConfig = new SimulationConfig(points, tasks, threads);
                ExperimentResult parResult = runExperiment(parallelEstimator, parConfig,
                        "Parallel(" + threads + " threads)");
                results.add(parResult);

                // Calculate speedup
                double speedup = (double) seqResult.getRuntimeMs() / parResult.getRuntimeMs();
                System.out.println("  " + parResult +
                        String.format(" | Speedup: %.2fx", speedup));
            }

            System.out.println();
        }

        return results;
    }


    public void printResultsSummary(List<ExperimentResult> results) {
        System.out.println("\n=== Experiment Summary ===");
        System.out.println(String.format("%-20s | %-15s | %-12s | %-12s | %-10s",
                "Estimator", "Points", "π Estimate", "Error", "Time (ms)"));
        System.out.println("-".repeat(85));

        for (ExperimentResult result : results) {
            System.out.println(String.format("%-20s | %,15d | %.10f | %.10f | %,10d",
                    result.getEstimatorType(),
                    result.getConfig().getTotalPoints(),
                    result.getPiEstimate(),
                    result.getAbsoluteError(),
                    result.getRuntimeMs()));
        }
    }

    public static void main(String[] args) {
        PiExperimentRunner runner = new PiExperimentRunner();

        
        long[] pointsList = { 100_000, 1_000_000, 10_000_000 };
        int[] threadCounts = { 2, 4, 8 };

        
        List<ExperimentResult> results = runner.runComprehensiveExperiments(pointsList, threadCounts);

        
        runner.printResultsSummary(results);

        System.out.println("\nActual π value: " + Math.PI);
    }
}
