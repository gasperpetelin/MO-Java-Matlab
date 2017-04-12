package jMetalMod;

import org.uma.jmetal.algorithm.impl.AbstractEvolutionaryAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public abstract class AbstractGeneticAlgorithmMod<S extends Solution<?>, Result> extends AbstractEvolutionaryAlgorithm<S, Result>
{
    private int maxPopulationSize;
    protected SelectionOperator<List<S>, S> selectionOperator;
    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;

    public void setMaxPopulationSize(int maxPopulationSize) {
        this.maxPopulationSize = maxPopulationSize;
    }

    public int getMaxPopulationSize() {
        return this.maxPopulationSize;
    }

    public SelectionOperator<List<S>, S> getSelectionOperator() {
        return this.selectionOperator;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return this.crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return this.mutationOperator;
    }

    public AbstractGeneticAlgorithmMod(Problem<S> problem) {
        this.setProblem(problem);
    }

    protected List<S> createInitialPopulation() {
        List<S> population = new ArrayList(this.getMaxPopulationSize());

        for(int i = 0; i < this.getMaxPopulationSize(); ++i) {
            S newIndividual = this.getProblem().createSolution();
            population.add(newIndividual);
        }

        return population;
    }

    protected List<S> selection(List<S> population) {
        List<S> matingPopulation = new ArrayList(population.size());

        for(int i = 0; i < this.getMaxPopulationSize(); ++i) {
            S solution = (S) this.selectionOperator.execute(population);
            matingPopulation.add(solution);
        }

        return matingPopulation;
    }

    protected List<S> reproduction(List<S> population) {
        int numberOfParents = this.crossoverOperator.getNumberOfParents();
        this.checkNumberOfParents(population, numberOfParents);
        List<S> offspringPopulation = new ArrayList(this.getMaxPopulationSize());


        if(false)
        {

            for (int i = 0; i < this.getMaxPopulationSize() / numberOfParents; i++)
            {
                List<S> parents = new ArrayList(numberOfParents);
                for (int j = 0; j < numberOfParents; ++j)
                {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, this.getMaxPopulationSize() + 1);
                    parents.add(population.get(randomNum));
                }
                List<S> offspring = (List) this.crossoverOperator.execute(parents);
                Iterator var7 = offspring.iterator();
                while (var7.hasNext())
                {
                    S s = (S) var7.next();
                    this.mutationOperator.execute(s);
                    offspringPopulation.add(s);
                }
            }
        }
        else
        {

            for (int i = 0; i < this.getMaxPopulationSize(); i += numberOfParents)
            {
                List<S> parents = new ArrayList(numberOfParents);
                for (int j = 0; j < numberOfParents; ++j)
                {
                    parents.add(population.get(i + j));
                }
                List<S> offspring = (List) this.crossoverOperator.execute(parents);
                Iterator var7 = offspring.iterator();
                while (var7.hasNext())
                {
                    S s = (S) var7.next();
                    this.mutationOperator.execute(s);
                    offspringPopulation.add(s);
                }
            }
        }

        return offspringPopulation;
    }

    protected void checkNumberOfParents(List<S> population, int numberOfParentsForCrossover) {
        if(population.size() % numberOfParentsForCrossover != 0) {
            throw new JMetalException("Wrong number of parents: the remainder if the population size (" + population.size() + ") is not divisible by " + numberOfParentsForCrossover);
        }
    }
}
