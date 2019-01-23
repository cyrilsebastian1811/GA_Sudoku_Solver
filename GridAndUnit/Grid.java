package GridAndUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grid {
    protected Unit[] rows;
    protected Unit[] columns;
    protected Unit[] blocks;
    protected int GridConflicts;
    protected int EmptyFieldsCount;

    public Grid(){
        this.GridConflicts=0;
        this.rows=new Unit[9];
        this.columns=new Unit[9];
        this.blocks=new Unit[9];
        this.EmptyFieldsCount=81;
        FillUnits(this.rows);
        FillUnits(this.columns);
        FillUnits(this.blocks);
    }

    public Grid(Grid other){
        this.GridConflicts=other.getGridConflicts();
        this.EmptyFieldsCount=other.EmptyFieldsCount;
        this.rows=this.copyUnits(other.rows);
        this.columns=this.copyUnits(other.columns);
        this.blocks=this.copyUnits(other.blocks);
//        this.rows=new Unit[9];
//        this.columns=new Unit[9];
//        this.blocks=new Unit[9];
//        for(int i=0;i<9;i++) {
//            for(int j=0;j<9;j++) {
//                this.rows[i].write(j, other.rows[i].read(j));
//                this.rows[i].copy(other.rows[i]);
//
//                this.columns[i].write(j, other.columns[i].read(j));
//                this.columns[i].copy(other.columns[i]);
//
//                this.blocks[i].write(j, other.blocks[i].read(j));
//                this.blocks[i].copy(other.blocks[i]);
//            }
//        }
    }

    private Unit[] copyUnits(Unit[] other) {
        Unit[] copy = new Unit[other.length];
        for (int i = 0; i < other.length; i++) {
            copy[i] = new Unit(other[i]);
        }
        return copy;
    }

    public void FillUnits(Unit[] units){
        for(int i=0;i<units.length;i++){
            units[i]=new Unit();
        }
    }


    public void write(int x,int y,int number){
        int Block=((y/3)*3)+(x/3);
        int BlockIndex=((y%3)*3)+(x%3);

        if(this.rows[y].read(x)==0) EmptyFieldsCount-=1;
        if(number==0) EmptyFieldsCount+=1;

        this.GridConflicts-=this.rows[y].getConflicts();
        this.GridConflicts-=this.columns[x].getConflicts();
        this.GridConflicts-=this.blocks[Block].getConflicts();


        this.rows[y].write(x,number);
        this.rows[y].setGridIndex(x,this.CellIndexByRow(x,y));
        this.columns[x].write(y,number);
        this.columns[x].setGridIndex(y,CellIndexByRow(x,y));        // might have to change the way gridIndex is calculated for a column
        this.blocks[Block].write(BlockIndex,number);
        this.blocks[Block].setGridIndex(BlockIndex,CellIndexByRow(x,y));        // might have to change the way gridIndex is calculated for a block

        this.GridConflicts+=this.rows[y].getConflicts();
        this.GridConflicts+=this.columns[x].getConflicts();
        this.GridConflicts+=this.blocks[Block].getConflicts();

    }

    public void write(int cellIndex,int number){
//    	System.out.println("I was here"+number);
        write(cellIndex%9,cellIndex/9,number);
    }

    // only used in this class
    public int CellIndexByRow(int x,int y){
//        System.out.println(y*9+x);
        return y*9+x;
    }

    public int getRowByCellIndex(int cellIndex){
        return cellIndex/9;
    }

    public int getColumnByCellIndex(int cellIndex){
        return cellIndex%9;
    }

    public int getblockByCellIndex(int cellIndex){
        return ((getRowByCellIndex(cellIndex)/3)*3)+(getColumnByCellIndex(cellIndex)/3);
    }



    public int[] getGridIndicesForRow(int rowIndex){
        return rows[rowIndex].getGridIndices();
    }
    public int[] getGridIndicesForColumn(int columnIndex){
        return columns[columnIndex].getGridIndices();
    }
    public int[] getGridIndicesForBlock(int blockIndex){
        return blocks[blockIndex].getGridIndices();
    }


//    public int[] getGridIndicesForRowByCellIndex(int cellIndex){
//        int index=getRowByCellIndex(cellIndex);
//        int[] temp=getGridIndicesForRow(index);
//        return temp;
//    }
//    public int[] getGridIndicesForColumnByCellIndex(int cellIndex){
//        int index=getColumnByCellIndex(cellIndex);
//        int[] temp=getGridIndicesForColumn(index);
//        return temp;
//    }
//    public int[] getGridIndicesForBlockByCellIndex(int cellIndex){
//        int index=getblockByCellIndex(cellIndex);
//        int[] temp=getGridIndicesForBlock(index);
//        return temp;
//    }

    public int getRowByIndex(int index) {
        return index / 9;
    }


    public int getColumnByIndex(int index) {
        return index % 9;
    }


    public int getBlockByIndex(int index) {
        return (this.getRowByIndex(index) / 3) * 3 +
                (this.getColumnByIndex(index) / 3);
    }

    public int[] getRowForIndex(int index) {
        return this.rows[this.getRowByIndex(index)].getGridIndices();
    }


    public int[] getColumnForIndex(int index) {
        return this.columns[this.getColumnByIndex(index)].getGridIndices();
    }


    public int[] getBlockForIndex(int index) {
        return this.blocks[this.getBlockByIndex(index)].getGridIndices();
    }


    public int read(int index){
        return this.rows[getRowByCellIndex(index)].read(getColumnByCellIndex(index));
    }

//    public Set<Integer> getEmptyCells(){
//        Set<Integer> emptyFields=new HashSet<>();
//        for(int i=0;i<9;i++){
//            for(int j=0;j<9;j++){
//                if(rows[i].read(j)==0) {
//                    emptyFields.add(CellIndexByRow(j,i));
//                }
//            }
//        }
////        System.out.println(emptyFields);
//        return emptyFields;
//    }

    public Set<Integer> getEmptyCells(){
        Set<Integer> emptyFields=new HashSet<>();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(rows[i].read(j)==0) {
                    emptyFields.add(CellIndexByRow(j,i));
                }
            }
        }
//        System.out.println(emptyFields);
        return emptyFields;
//        Set<Integer> emptyFields = new HashSet<>();
//        for (int rowIndex = 0; rowIndex < this.rows.length; rowIndex++) {
//            int[] row = this.rows[rowIndex].toArray();
//            for (int colIndex = 0; colIndex < row.length; colIndex++) {
//                if (row[colIndex] == 0) {
//                    emptyFields.add((rowIndex * 9) + colIndex);
//                }
//            }
//        }
//        System.out.println(emptyFields);
//        return emptyFields;
    }

    public int getGridConflicts(){
        return GridConflicts;
    }

    public String toString(){
        String str="";
        for(int i=0;i<9;i++){
            if(i%3==0) {
                if(i>0 && i<9) {
                    str+="|-------|-------|-------|\n";
                }else {
                    str+="|-----------------------|\n";
                }
            }
            str+=rows[i].print()+"\n";
        }
        str+="|-----------------------|";
        return str;
    }


    /*
     * might have to delete this*/
    public Grid getgrid() {
        return this;
    }






//    private final int blockSize;
//    private int conflicts;
//    private int countEmptyFields;
//    private int sideLength;
//    private SudokuUnit[] rows;
//    private SudokuUnit[] columns;
//    private SudokuUnit[] blocks;
//
//    /**
//     * The main constructor
//     * @param blockSize the size of a single block (the whole grid has a side length of blockSize squared)
//     */
//    public SudokuGrid(int blockSize) {
//        this.blockSize = blockSize;
//        this.rows = new SudokuUnit[blockSize * blockSize];
//        this.columns = new SudokuUnit[blockSize * blockSize];
//        this.blocks = new SudokuUnit[blockSize * blockSize];
//        this.sideLength = this.blockSize * this.blockSize;
//        this.countEmptyFields = this.sideLength * this.sideLength;
//        this.fillBlank(this.rows);
//        this.fillBlank(this.columns);
//        this.fillBlank(this.blocks);
//    }
//
//    /**
//     * Copy constructor
//     * @param other the SudokuGrid to copy
//     */
//    public SudokuGrid(SudokuGrid other) {
//        this.blockSize = other.getBlockSize();
//        this.sideLength = other.getSideLength();
//        this.conflicts = other.getConflicts();
//        this.countEmptyFields = other.countEmptyFields;
//        this.rows = this.copyUnits(other.rows);
//        this.columns = this.copyUnits(other.columns);
//        this.blocks = this.copyUnits(other.blocks);
//    }
//
//    /**
//     * Makes a deep copy of a SudokuUnit-array
//     * @param other the unit-array to copy
//     * @return a copy of the passed SudokuUnit-array
//     */
//    private SudokuUnit[] copyUnits(SudokuUnit[] other) {
//        SudokuUnit[] copy = new SudokuUnit[other.length];
//        for (int i = 0; i < other.length; i++) {
//            copy[i] = new SudokuUnit(other[i]);
//        }
//        return copy;
//    }
//
//    /**
//     * Fills a unit array with blank sudoku-units
//     * @param arr the array to fill
//     */
//    private void fillBlank(SudokuUnit[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = new SudokuUnit(1, this.getSideLength(), 1);
//        }
//    }
//
//
//    public int getValidMin() {
//        return 1;
//    }
//
//
//    public int getValidMax() {
//        return this.getBlockSize() * this.getBlockSize();
//    }
//
//
//    public void write(int x, int y, int number) {
//        int blockNum = ((y / this.blockSize) * this.blockSize) + (x / this.blockSize);
//        int blockIndex = ((y % this.blockSize) * this.blockSize) + (x % this.blockSize);
//
//        if (this.rows[y].read(x) == 0) countEmptyFields -= 1;
//        if (number == 0) countEmptyFields += 1;
//
//        this.conflicts -= this.rows[y].getConflicts();
//        this.conflicts -= this.columns[x].getConflicts();
//        this.conflicts -= this.blocks[blockNum].getConflicts();
//
//        this.rows[y].write(x, number);
//        this.rows[y].setGridIndex(x, this.getIndexByRow(y, x)); //TODO: do not implement here
//        this.columns[x].write(y, number);
//        this.columns[x].setGridIndex(y, this.getIndexByColumn(x, y)); //TODO: do not implement here
//        this.blocks[blockNum].write(blockIndex, number);
//        this.blocks[blockNum].setGridIndex(blockIndex, this.getIndexByBlock(blockNum, blockIndex)); //TODO: do not implement here
//
////        if(number==0) System.out.println("B->"+this.getIndexByBlock(blockNum, blockIndex));
//
//        this.conflicts += this.rows[y].getConflicts();
//        this.conflicts += this.columns[x].getConflicts();
//        this.conflicts += this.blocks[blockNum].getConflicts();
//    }
//
//
//    public int read(int index) {
//        return this.rows[index / (this.getSideLength())].read(index % this.getSideLength());
//    }
//
//
//    public void write(int index, int number) {
//        this.write(index % this.getSideLength(), index / this.getSideLength(), number);
//    }
//
//
//    public int getConflicts() {
//        return this.conflicts;
//    }
//
//
//    public int getBlockSize() {
//        return this.blockSize;
//    }
//
//
//    public int getSideLength() {
//        return this.sideLength;
//    }
//
////
////    public String toString() {
////        String line = new String(
////                new char[this.rows[0].print(this.blockSize).length()]
////        ).replace('\0', '-') + '\n';
////        String value = "";
////        for(int i = 0; i < this.rows.length; i++) {
////            if (i % this.blockSize == 0) {
////                value += line;
////            }
////            value += this.rows[i].print(this.blockSize) + '\n';
////        }
////        value += line;
////        return value.trim();
////    }
//
//
//    public Set<Integer> getEmptyFields() {
//        Set<Integer> emptyFields = new HashSet<>();
//        for (int rowIndex = 0; rowIndex < this.rows.length; rowIndex++) {
//            int[] row = this.rows[rowIndex].toArray();
//            for (int colIndex = 0; colIndex < row.length; colIndex++) {
//                if (row[colIndex] == 0) {
//                    emptyFields.add((rowIndex * this.getSideLength()) + colIndex);
//                }
//            }
//        }
////        System.out.println(emptyFields);
//        return emptyFields;
//    }
//
//
//    public int getIndexByRow(int row, int position){
//        return row * this.getSideLength() + position;
//    }
//
//
//    public int getIndexByColumn(int column, int position) {
//        return position * this.getSideLength() + column;
//    }
//
//
//    public int getIndexByBlock(int block, int position) {
//        return ((block / this.getBlockSize()) * this.getSideLength() * this.getBlockSize()) +
//                (position / this.getBlockSize()) * this.getSideLength() +
//                ((block % this.getBlockSize()) * this.getBlockSize()) +
//                (position % this.getBlockSize());
//    }
//
//
//    public int getRowByIndex(int index) {
//        return index / this.getSideLength();
//    }
//
//
//    public int getColumnByIndex(int index) {
//        return index % this.getSideLength();
//    }
//
//
//    public int getBlockByIndex(int index) {
//        return (this.getRowByIndex(index) / this.getBlockSize()) * this.getBlockSize() +
//                (this.getColumnByIndex(index) / this.getBlockSize());
//    }
//
//
//    public int[] getRowForIndex(int index) {
//        return this.rows[this.getRowByIndex(index)].getGridIndices();
//    }
//
//
//    public int[] getColumnForIndex(int index) {
//        return this.columns[this.getColumnByIndex(index)].getGridIndices();
//    }
//
//
//    public int[] getBlockForIndex(int index) {
//        return this.blocks[this.getBlockByIndex(index)].getGridIndices();
//    }
//
//    public String toString(){
//        String str="";
//        for(int i=0;i<9;i++){
//            if(i%3==0) {
//                if(i>0 && i<9) {
//                    str+="|-------|-------|-------|\n";
//                }else {
//                    str+="|-----------------------|\n";
//                }
//            }
//            str+=rows[i].print()+"\n";
//        }
//        str+="|-----------------------|";
//        return str;
//    }

}
