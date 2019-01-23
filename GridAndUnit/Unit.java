package GridAndUnit;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Unit {

    private int UnitConflicts;
    private int[] Representation;
    private int[] Usages;
    private int[] GridIndices;

    public Unit(){
        this.Representation=new int[9];
        this.Usages=new int[9];
        this.GridIndices=new int[9];
        this.UnitConflicts=0;
    }

    Unit(Unit other){
//        this.Representation=other.Representation;
//        this.Usages=other.Usages;
//        this.GridIndices=other.GridIndices;
//        this.UnitConflicts=other.getConflicts();
        this.UnitConflicts=other.getConflicts();
        this.Representation= Arrays.copyOf(other.Representation,other.Representation.length);
        this.Usages= Arrays.copyOf(other.Usages, other.Usages.length);
        this.GridIndices=other.GridIndices;
    }

//    public void copy(Unit other){
//        this.UnitConflicts=other.getConflicts();
//        this.Representation= Arrays.copyOf(other.Representation,other.Representation.length);
//        this.Usages= Arrays.copyOf(other.Usages, other.Usages.length);
//        this.GridIndices=other.GridIndices;
//
//    }

    public void write(int index, int number){
        int oldnumber=Representation[index];
        if(oldnumber>=1){
            DecreaseConflicts(oldnumber);
            this.Usages[oldnumber-1]-=1;               // might have to change the way Usages are set
        }
        this.Representation[index]=number;
        if(number>=1){
            this.Usages[number-1]+=1;               // might have to change the way Usages are set
            IncreaseConflicts(number);
        }
    }

    private void DecreaseConflicts(int number){
        if(this.Usages[number-1]>1) {
            this.UnitConflicts--;
        }
    }

    private void IncreaseConflicts(int number){
        if(this.Usages[number-1]>1) {
            this.UnitConflicts++;
        }
    }



    public int getConflicts(){
        return this.UnitConflicts;
    }

    public int[] getGridIndices(){
        return this.GridIndices;
    }



    public void setGridIndex(int index, int gridindex){
        this.GridIndices[index]=gridindex;
    }


    public int read(int index){
        return this.Representation[index];
    }
    public String print(){
        String str="";
//        str+="";
        for(int i=0;i<9;i++){
            if(i%3==0) str=str+"| "+this.Representation[i]+" ";
            else str=str+this.Representation[i]+" ";
        }
        return str+"|";
    }

    public int[] toArray() {
        return this.Representation;
    }

}