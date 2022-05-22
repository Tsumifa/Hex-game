package src.IA;

import java.util.Random;
import src.debug.Debug;

public class RandomAI {

    private Random random = new Random();
    private Debug debugManager;

    public RandomAI( Debug debugManager ) {

        this.debugManager = debugManager;

    } 

    public int[] playMove( int[][] board ) {

        Boolean moveValid = false;
        int row = 0;
        int col = 0;

        while ( !moveValid ) {

            row = random.nextInt( board.length );
            col = random.nextInt( board.length );

            if ( board[row][col] == 0 ) moveValid = true;

        }
        
        int[] move = {row, col};
        return move;
    
    }
}
