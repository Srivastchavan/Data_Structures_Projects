import java.util.*;

public class MazeApp
{
    public static void main(String [] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the value for number of rows of the Maze: ");
        int row = sc.nextInt();
        System.out.println("Please enter the value for number of columns of the Maze: ");
        int column = sc.nextInt();

        MazeGenerator mg = new MazeGenerator(row, column);
        mg.printMaze();
    }
}
