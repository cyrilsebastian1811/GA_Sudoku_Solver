package Prep;

import java.util.*;
import GridAndUnit.Grid;
public class Problem {

    protected Grid grid;
//    protected Set<Integer> EmptyCellsOfGrid;
    public Set<Integer> EmptyCellsOfGrid;
    protected Map<Integer,Integer[]> EmptyCellsOfRows;
    protected Map<Integer,Set<Integer>> ValidNumbersForEmptyGridCells;
    protected Map<Integer,Integer[]> CellValidNumbersArray;

    public Problem(Grid grid){
        this.grid=grid;
        preprocessing();
    }

    public Integer[] getEmptyCells(){
        return this.EmptyCellsOfGrid.toArray(new Integer[this.EmptyCellsOfGrid.size()]);

    }

    public Integer[] getvariableFieldsForRow(int index){
        return this.EmptyCellsOfRows.get(index);
    }


    public Integer[] getValidNumberForIndex(int index){
        return this.CellValidNumbersArray.get(index);
    }

//    ????????????????????
    public Set<Integer> getsetofvalidNumbers(int index){
        return this.ValidNumbersForEmptyGridCells.get(index);
    }

    public void toArray(){
        this.CellValidNumbersArray=new HashMap<>();
        for(Map.Entry<Integer,Set<Integer>> x:this.ValidNumbersForEmptyGridCells.entrySet()){
            this.CellValidNumbersArray.put(x.getKey(),x.getValue().toArray(new Integer[x.getValue().size()]));
        }
    }

    public boolean IsnumberValidForIndex(int index,int number){
        return this.ValidNumbersForEmptyGridCells.get(index).contains(number);
    }

    protected void preprocessing(){
        this.setEmptyCells();
        this.presolve();
        this.getEmptyCellsOfUnits();
        this.toArray();
    }

    public void setEmptyCells(){
        EmptyCellsOfGrid=grid.getEmptyCells();
    }

    public void getEmptyCellsOfUnits()
    {
        this.EmptyCellsOfRows=new HashMap<>();

        for(int i=0;i<9;i++){
            ArrayList<Integer> variableFieldsForRows=new ArrayList<>();
            ArrayList<Integer> variableFieldsForColumns=new ArrayList<>();
            ArrayList<Integer> variableFieldsForBlocks=new ArrayList<>();

            int[] row=grid.getGridIndicesForRow(i);
            for(int j=0;j<9;j++){
                if (EmptyCellsOfGrid.contains(row[j])) variableFieldsForRows.add(row[j]);
            }
            int n=variableFieldsForRows.size();
            this.EmptyCellsOfRows.put(i,variableFieldsForRows.toArray(new Integer[n]));
        }
    }

    public void getValidNumbersForEmptyGridCells(){
        this.ValidNumbersForEmptyGridCells=new HashMap<>();
        for(Integer index: this.getEmptyCells()){
            Set<Integer> variablenumbers=new HashSet<>();
            for(int j=1;j<10;j++){
                this.grid.write(index,j);
                if(this.grid.getGridConflicts()==0) {
                    variablenumbers.add(j);
                }

                this.grid.write(index,0);

            }
            this.ValidNumbersForEmptyGridCells.put(index,variablenumbers);
        }
    }

    public void presolve(){
        boolean gridChanged=true;
        while (gridChanged && EmptyCellsOfGrid.size()>0) {
            getValidNumbersForEmptyGridCells();
            gridChanged=FillGrid();
        }
        getValidNumbersForEmptyGridCells();
    }

    public boolean FillGrid(){
        Set<Integer> x=this.EmptyCellsOfGrid;
        for(Integer index: x){
            if(addtoGrid(index,this.grid.getRowForIndex(index))){
                this.EmptyCellsOfGrid.remove(index);
                return true;
            }
            if(addtoGrid(index,this.grid.getColumnForIndex(index))){
                this.EmptyCellsOfGrid.remove(index);
                return true;
            }
            if(addtoGrid(index,this.grid.getBlockForIndex(index))){
                this.EmptyCellsOfGrid.remove(index);
                return true;
            }
        }
        return false;
    }

    public boolean addtoGrid(int index,int[] gridIndices){
        Set<Integer> validnumbersCopy=new HashSet<>(this.ValidNumbersForEmptyGridCells.get(index));
        for(int UnitIndex:gridIndices){
            if(UnitIndex!=index && this.EmptyCellsOfGrid.contains(UnitIndex)){
                Iterator<Integer> iterator=validnumbersCopy.iterator();
                while(iterator.hasNext()){
                    int number=iterator.next();
                    if(this.ValidNumbersForEmptyGridCells.get(UnitIndex).contains(number)){
                        iterator.remove();
                    }
                }
            }
        }
        if(validnumbersCopy.size()==1){
            this.grid.write(index,validnumbersCopy.iterator().next());
            return true;
        }
        return false;
    }

    public Grid getGrid() {
        return grid;
    }

    public String toString() {
        return grid.toString();
    }
}