package IndividualAndPopulation;

import java.util.Arrays;

import App.Application;
import GridAndUnit.Grid;
import Prep.Problem;


public final class Individuals extends Grid implements Comparable<Individuals>
{
    private Problem problem;
    private int gene[];

    public Individuals(){
        super(Application.getinstance().getPreSolvedGrid().getGrid());
        this.problem=Application.getinstance().getPreSolvedGrid();
        this.gene=new int[this.problem.getEmptyCells().length];
        this.fillingRandomVals();
    }

    public Individuals (Individuals other){
        super(other);
        this.problem=other.problem;
        this.gene= Arrays.copyOf(other.getGene(),other.getGene().length);
    }

    private void fillingRandomVals()
    {
        for(int i=0;i<this.gene.length;i++)
        {
            this.writeRandomChromosome(i);
        }
    }

    public void writeChromosome(int index,int value)
    {

        this.write(this.problem.getEmptyCells()[index],value);
        this.gene[index]=value;
    }

    public void writeRandomChromosome(int index)
    {
        int gridIndex=this.problem.getEmptyCells()[index];
        Integer[] validNumber =this.problem.getValidNumberForIndex(gridIndex);

        int randomIndex=(int)(Math.random()*validNumber.length);
        this.writeChromosome(index,validNumber[randomIndex]);
    }

    public int readChromosome(int index)
    {
        return this.getGene()[index];
    }

    public int[] getGene()
    {
        return this.gene;
    }

    public double getFitness()
    {
    	double fitness=(216-(double)getGridConflicts())/216;
    	return fitness;
//        return Math.pow((1.0/(this.getGridConflicts()+1)), 3);
    }

    public int compareTo(Individuals o)
    {
        if (this.getFitness()<o.getFitness())
            return 1;
        else
            return -1;
    }

    public String toString() {
        String str="Grid Conflicts : "+this.GridConflicts+"\n"+super.toString()+"\n";
        return str;
    }

    /*
     * For Testing Purposes
     * Testing the Problems  getValidNumberForIndex(int index);
     * */

    public boolean testingIndividuals() {
        int check=0;
        int index=0;
        for(Integer CellIndex: problem.getEmptyCells()) {
            Integer[] validnumbers=problem.getValidNumberForIndex(CellIndex);
            for(Integer n:validnumbers) {
                if(n==gene[index]) {
                    check++;
                    index++;
                    break;
                }
            }
        }
        return check==problem.getEmptyCells().length;
    }

    public boolean testinggene(){
        return this.gene.length==this.problem.getEmptyCells().length;
    }


}