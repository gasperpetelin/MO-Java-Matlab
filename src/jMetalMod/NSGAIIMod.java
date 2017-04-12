package jMetalMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

public class NSGAIIMod<S extends Solution<?>> extends AbstractGeneticAlgorithmMod<S, List<S>> {
    protected final int maxEvaluations;
    protected final SolutionListEvaluator<S> evaluator;
    protected int evaluations;

    public NSGAIIMod(Problem<S> problem, int maxEvaluations, int populationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem);
        this.maxEvaluations = maxEvaluations;
        this.setMaxPopulationSize(populationSize);
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = selectionOperator;
        this.evaluator = evaluator;
    }

    protected void initProgress() {
        this.evaluations = this.getMaxPopulationSize();
    }

    protected void updateProgress() {
        this.evaluations += this.getMaxPopulationSize();
    }

    protected boolean isStoppingConditionReached() {
        return this.evaluations >= this.maxEvaluations;
    }

    protected List<S> evaluatePopulation(List<S> population) {
        population = this.evaluator.evaluate(population, this.getProblem());
        return population;
    }

    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        List<S> jointPopulation = new ArrayList();
        jointPopulation.addAll(population);
        jointPopulation.addAll(offspringPopulation);
        Ranking<S> ranking = this.computeRanking(jointPopulation);
        return this.crowdingDistanceSelection(ranking);
    }

    public List<S> getResult() {
        return this.getNonDominatedSolutions(this.getPopulation());
    }

    protected Ranking<S> computeRanking(List<S> solutionList) {
        Ranking<S> ranking = new DominanceRanking();
        ranking.computeRanking(solutionList);
        return ranking;
    }

    protected List<S> crowdingDistanceSelection(Ranking<S> ranking) {
        CrowdingDistance<S> crowdingDistance = new CrowdingDistance();
        List<S> population = new ArrayList(this.getMaxPopulationSize());
        int rankingIndex = 0;

        while(this.populationIsNotFull(population)) {
            if(this.subfrontFillsIntoThePopulation(ranking, rankingIndex, population)) {
                this.addRankedSolutionsToPopulation(ranking, rankingIndex, population);
                ++rankingIndex;
            } else {
                crowdingDistance.computeDensityEstimator(ranking.getSubfront(rankingIndex));
                this.addLastRankedSolutionsToPopulation(ranking, rankingIndex, population);
            }
        }

        return population;
    }

    protected boolean populationIsNotFull(List<S> population) {
        return population.size() < this.getMaxPopulationSize();
    }

    protected boolean subfrontFillsIntoThePopulation(Ranking<S> ranking, int rank, List<S> population) {
        return ranking.getSubfront(rank).size() < this.getMaxPopulationSize() - population.size();
    }

    protected void addRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S> population) {
        List<S> front = ranking.getSubfront(rank);
        Iterator var5 = front.iterator();

        while(var5.hasNext()) {
            S solution = (S) var5.next();
            population.add(solution);
        }

    }

    protected void addLastRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S> population) {
        List<S> currentRankedFront = ranking.getSubfront(rank);
        Collections.sort(currentRankedFront, new CrowdingDistanceComparator());

        for(int i = 0; population.size() < this.getMaxPopulationSize(); ++i) {
            population.add(currentRankedFront.get(i));
        }

    }

    protected List<S> getNonDominatedSolutions(List<S> solutionList) {
        return SolutionListUtils.getNondominatedSolutions(solutionList);
    }

    public String getName() {
        return "NSGAII";
    }

    public String getDescription() {
        return "Nondominated Sorting Genetic Algorithm version II";
    }
}
