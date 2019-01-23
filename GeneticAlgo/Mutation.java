package GeneticAlgo;

import App.Application;
import IndividualAndPopulation.Individuals;
import Prep.Problem;

public class Mutation {
    private Problem problem;

    public Mutation(){
        this.problem=Application.getinstance().getPreSolvedGrid();
    }

    public Individuals mutateIndividual(Individuals mutatedindividual){
        if(Math.random()< 0.1)
        {
            return mutatedindividual;
        }

        for(int i = 0; i<(int)(Math.random()*9);i++)
        {
            int row = (int)(Math.random()*9);
            Integer[] emptyFields = this.problem.getvariableFieldsForRow(row);
            if(emptyFields.length>0) {
                int varF1 = emptyFields[(int) (Math.random()*emptyFields.length)];
                for(int varF : emptyFields)
                {
                    if(mutatedindividual.read(varF1)!=mutatedindividual.read(varF)&&
                            this.problem.IsnumberValidForIndex(varF1,mutatedindividual.read(varF))&&
                            this.problem.IsnumberValidForIndex(varF,mutatedindividual.read(varF1))){
                        int tempNum = mutatedindividual.read(varF);
                        mutatedindividual.write(varF1,mutatedindividual.read(varF));
                        mutatedindividual.write(varF,tempNum);
                    }
                }
            }
        }
        return mutatedindividual;
    }

    public boolean testingmutattion(){
        Problem pro=Application.getinstance().getPreSolvedGrid();
//        pro.getValidNumberForIndex();
        Individuals testing=mutateIndividual(new Individuals());
        int[] z=testing.getGene();
        Integer[] f=pro.getEmptyCells();
        int count=0;
        for(Integer i:f){
            if(!pro.getsetofvalidNumbers(i).contains(z[count++])) return false;
        }
        return true;
    }

}
