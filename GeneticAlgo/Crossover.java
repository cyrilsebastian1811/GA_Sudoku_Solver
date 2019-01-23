package GeneticAlgo;

import IndividualAndPopulation.*;

public class Crossover {
    private Individuals child;
    private Individuals[] parents;
    private int indexOfParent;

    public Crossover(int numOfParents){
        this.parents=new Individuals[numOfParents];
        this.indexOfParent=0;
    }
    public void setChild(Individuals individual){
        this.child=individual;
    }

    public void addingParent(Individuals parent){
        if(parent==null) throw new IllegalArgumentException("Parent cannot be null in a uniform-crossover");
        this.parents[this.indexOfParent]=parent;
        indexOfParent+=1;
    }

    public Individuals crossOver(){
        if(this.needsParent()) throw new RuntimeException("Uniformselection needs more parents before cross can be executed");
        //////////////////////////////
        this.child=new Individuals();
        //////////////////////////////

        for(int i =0; i<this.child.getGene().length;i++)
        {
            int randomNum = (int) (Math.random()*this.parents.length);
            this.child.writeChromosome(i,this.parents[randomNum].readChromosome(i));
        }
        this.indexOfParent=0;
        return this.child;
    }

    public boolean needsParent() {
        return this.indexOfParent<this.parents.length;
    }

    public String toString() {
        return child.toString();
    }

}
