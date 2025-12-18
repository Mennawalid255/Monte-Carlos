package edu.montecarlo.model;

import java.util.Random;

public class SequentialPiEstimator implements PiEstimator {

    @Override
    public double estimatePi(SimulationConfig config) {
        Random random = new Random();
        long pointsInsideCircle = 0;

        for (long i = 0; i < config.getTotalPoints(); i++) {
            double x = random.nextDouble(); // Random x in [0, 1)
            double y = random.nextDouble(); // Random y in [0, 1)

            if (x * x + y * y <= 1.0) {
                pointsInsideCircle++;
            }
        }

        // π = 4 × (circle area / square area)
        return 4.0 * pointsInsideCircle / config.getTotalPoints();
    }
}
