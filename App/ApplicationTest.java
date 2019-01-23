package App;

import GeneticAlgo.Mutation;
import GeneticAlgo.Selection;
import IndividualAndPopulation.Individuals;
import IndividualAndPopulation.Population;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTest {
    @Test
    public void TestCrossover(){
        World world=new World();
        boolean check=world.testinEvolution();
        Assertions.assertTrue(check);
    }

    @Test
    public void Testmutation(){
        Mutation m=new Mutation();
        Assertions.assertTrue(m.testingmutattion());
    }

    @Test
    public void TestIndividual(){
        Individuals ind=new Individuals();
        Assertions.assertTrue(ind.testingIndividuals());
    }

    @Test
    public void TestGene(){
        Individuals ind=new Individuals();
        Assertions.assertTrue(ind.testinggene());
    }

    @Test
    public void TestPopulation(){
        Population popo=new Population();
        Assertions.assertTrue(popo.testingFirstPopulation());
    }

    @Test
    public void TestSelection(){
        Selection select=new Selection();
        Assertions.assertTrue(select.testingSelection());
    }
}