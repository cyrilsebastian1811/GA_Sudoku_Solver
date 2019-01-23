package App;
import GridAndUnit.Grid;
import IndividualAndPopulation.Individuals;
import InputGrid.Reader;
import Prep.Problem;

public class Application {
    private Grid grid;
    private Problem presolvedgrid;
    private static Application data=null;
    private Reader reader;
    private Application() {
        System.out.println("Starting Sudoku Solver:");
        reader=new Reader("Grid1.txt");
        grid=reader.reader();
        System.out.println("The Grid we are trying to solve:");
        System.out.println(grid);
    }

    public static Application getinstance() {
        if(data==null) data=new Application();
        return data;
    }

    public Grid getGrid() {
        return grid;
    }

    public Problem getPreSolvedGrid() {
        presolvedgrid=new Problem(grid);
        return presolvedgrid;
    }

    public static void main(String[] args) {
        Application SudokuToSolve=Application.getinstance();
        Grid grid=SudokuToSolve.getGrid();

        World world=new World();
		Individuals solution=world.findSolution();
		System.out.println(solution);

    }
}
