package App;
import GeneticAlgo.Crossover;
import GeneticAlgo.Mutation;
import GeneticAlgo.Selection;
import IndividualAndPopulation.Individuals;
import IndividualAndPopulation.Population;

import java.util.ArrayList;
import java.util.Iterator;

public final class World {

    public Population population;
    public Population backupPopulation;
    public Population restartPopulation;
    public Population generationElites;
    public ArrayList<Double> bestFitnessForGeneration;
    public int generation;

    private Mutation mutation;
    private Crossover crossover;
    private Selection selection;
    private int populationSize;
    private int populationsBeforeRestart;

    public World() {
        this.crossover=new Crossover(2);
        this.selection=new Selection();
        this.mutation=new Mutation();
        this.populationSize = 1000;
//        this.populationsBeforeRestart = 20;
        this.populationsBeforeRestart = 20;
        this.population = new Population();
        this.population.creatrandomIndividuals();
        this.backupPopulation = new Population(this.population);
        this.restartPopulation = new Population(this.population);
        this.generationElites = new Population();
        this.bestFitnessForGeneration = new ArrayList<>();
        this.generation = 1;
    }

    private void restart() {
        System.out.println("The Problem is restarting:");
        if (this.generationElites.isFull()) {
            this.restartPopulation = new Population(this.generationElites);
            this.generationElites = new Population();
        } else {
            this.generationElites.add(this.population.get(this.populationSize / this.populationsBeforeRestart));
        }
        this.population = new Population(this.restartPopulation);
        this.bestFitnessForGeneration = new ArrayList<>();
        this.generation = 1;
    }

    public Individuals findSolution() {
        while (this.population.getBestIndividual().getGridConflicts() > 0) {

            this.Evolution();
            this.bestFitnessForGeneration.add(this.population.getBestIndividual().getFitness());

            if (this.bestFitnessForGeneration.size() - 20 >= 0 &&
                    this.population.getBestIndividual().getFitness() <= this.bestFitnessForGeneration.get(this.bestFitnessForGeneration.size() - 20)) {
                this.restart();
            }
        }
        System.out.println("The Solution to your Sudoku Problem is: ");
        return this.population.getBestIndividual();
    }

//    private void evolvePopulation() {
    private void Evolution() {
        Population newPopulation = new Population();
        this.addElites(newPopulation);
        this.addDescendants(newPopulation);
        this.backupPopulation = this.population;
        this.population = newPopulation;
        this.generation += 1;
    }

    private void addElites(Population newPopulation) {
        newPopulation.add(this.population.get(2));
    }

    private void addDescendants(Population newPopulation) {
        int counter = 2;

        Iterator<Individuals> iterator=this.backupPopulation.getPopulus().iterator();
        while(iterator.hasNext()) {
            Individuals carryovers=iterator.next();
            this.crossover.setChild(carryovers);
            while(this.crossover.needsParent()) {
                this.crossover.addingParent(this.selection.select(this.population));
            }
            newPopulation.add(this.mutation.mutateIndividual(this.crossover.crossOver()));
            if (++counter == this.populationSize) break;
        }
    }

    public Population getBackupPopulation(){
        return this.backupPopulation;
    }

    public Population getevolvedPopulation(){
        return this.population;
    }

    public boolean testinEvolution(){
        Evolution();
        return this.getBackupPopulation().getBestIndividual().getFitness()<this.getevolvedPopulation().getBestIndividual().getFitness()+2;
    }
}
