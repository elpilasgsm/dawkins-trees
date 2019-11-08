package org.sfedu.sockets.ga;

import org.sfedu.sockets.model.GAResponse;
import org.sfedu.sockets.model.Solution;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaporozhets on 07.11.2019.
 */
public class SimpleGA {
    private List<Solution> population;
    private final Solution target;
    private double crossover = 0.87;
    private double mutation = 0.05;
    private final static String CHARS = "abcdefghijklmnopqrstuvwxyz ".toUpperCase();
    private final int populationSize;
    private final int iterations;
    private final static Random RND = new Random();

    private final SimpMessagingTemplate messagingTemplate;


    private void sendPopulation(int iteration, boolean isDone) {
        GAResponse response = new GAResponse();
        response.setIterationNumber(iteration);
        response.setTotalIterations(iterations);
        response.setCurrent(population.get(0));
        response.setFinished(isDone);
        messagingTemplate.convertAndSend("/topic/population", response);
    }

    public SimpleGA(String target, int populationSize, int iterations, SimpMessagingTemplate messagingTemplate) {
        this.iterations = iterations;
        population = new ArrayList<>(populationSize);
        this.messagingTemplate = messagingTemplate;
        this.populationSize = populationSize;
        this.target = new Solution(target, null);
        int size = this.target.getData().length;
        for (int i = 0; i < this.populationSize; i++) {
            char[] sol = new char[size];
            for (int j = 0; j < size; j++) {
                sol[j] = CHARS.charAt(RND.nextInt(CHARS.length()));
            }
            population.add(new Solution(sol, this.target));
        }
        Collections.sort(population);
        sendPopulation(0, false);
    }


    private void mutation() {
        Solution solution1 = population.get(RND.nextInt(populationSize));
        char child1[] = new char[solution1.getData().length];

        for (int i = 0; i < solution1.getData().length; i++) {
            if (Math.random() < mutation) {
                child1[i] = CHARS.charAt(RND.nextInt(CHARS.length()));
            } else {
                child1[i] = solution1.getData()[i];
            }
        }
        population.add(new Solution(child1, target));
    }

    private void crossover() {
        Solution solution1 = population.get(RND.nextInt(populationSize));
        Solution solution2 = population.get(RND.nextInt(populationSize));
        while (solution1 == solution2) {
            solution2 = population.get(RND.nextInt(populationSize));
        }
        int cutPoint = RND.nextInt(solution1.getData().length - 2) + 1;
        char child1[] = new char[solution1.getData().length];
        char child2[] = new char[solution1.getData().length];
        for (int i = 0; i < solution1.getData().length; i++) {
            if (i < cutPoint) {
                child1[i] = solution1.getData()[i];
                child2[i] = solution2.getData()[i];
            } else {
                child2[i] = solution1.getData()[i];
                child1[i] = solution2.getData()[i];
            }
        }
        population.add(new Solution(child1, target));
        population.add(new Solution(child2, target));
    }

    public void selection() {
        Collections.sort(population);
        if (populationSize < population.size()) {
            population = new ArrayList<>(population.subList(0, populationSize));
        }
    }

    public void run() {
        for (int iteration = 0; iteration < iterations; iteration++) {
            for (int i = 0; i < crossover * populationSize; i++) {
                crossover();
                mutation();
            }
            selection();
            if (population.get(0).getFitness() >= 1.0) {
                sendPopulation(iteration, true);
                return;
            }
            if (iteration % 2 == 0)
                sendPopulation(iteration, false);
        }
        sendPopulation(iterations - 1, true);
    }
}
