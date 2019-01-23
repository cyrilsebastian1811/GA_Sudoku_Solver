package GeneticAlgo;

import java.util.Iterator;

import IndividualAndPopulation.Individuals;
import IndividualAndPopulation.Population;

public class Selection{
    public Individuals select(Population population)
    {
        Iterator<Individuals> iterator=population.getPopulus().iterator();
        double randomCheckPoint =Math.random();
        while(iterator.hasNext()) {
            Individuals selectedIndividual=iterator.next();
            double checkpt = 2*(selectedIndividual.getFitness()/population.getTotalFitness());

            if(randomCheckPoint<=checkpt)
            {
                return selectedIndividual;
            }
            randomCheckPoint-=checkpt;
        }
        throw new RuntimeException("Selection failed !");
    }

    public boolean testingSelection(){
        Population popo=new Population();
        popo.creatrandomIndividuals();
        Individuals ind=select(popo);
//        System.out.println(popo.getBestIndividual().getFitness()+"-"+);
        if((popo.getBestIndividual().getFitness()-ind.getFitness())<20) return true;

        return false;
    }
}
