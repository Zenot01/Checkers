import java.util.ArrayList;
import java.util.List;

public class Players{

//  0 - empty
//  1 - playerOne pawn
//  2 - playerTwo pawn
//  10 - playerOne king
//  20 - playerTwo king
  public static int[][] board = new int[10][10];

  public static round_n round = round_n.PLAYERTWO;
  public enum round_n{
    PLAYERONE,
    PLAYERTWO,
  }
  protected static boolean endGame = false;
  protected static round_n winner;

  public static void startGame(){
    for (int i = 0; i < 10; ++i){
      for (int k = 0; k < 10; ++k){
        board[i][k] = 0;
      }
    }


    for (int rows = 0; rows < 4; ++rows)
    {
      for (int columns = 0; columns < 10; ++columns)
      {
        if ((rows % 2 == 0 && columns % 2 == 0) || (rows % 2 != 0 && columns % 2 != 0))
        {
          board[rows][columns] = 1;
          board[rows + 6][columns] = 2;
        }
      }
    }
  }

  public static void switchRounds(){
    if (round == round_n.PLAYERTWO){
      round = round_n.PLAYERONE;
      StatPanel.scoreLabel.setText("Tura: Gracz 2");
    }
    else if (round == round_n.PLAYERONE){
      round = round_n.PLAYERTWO;
      StatPanel.scoreLabel.setText("Tura: Gracz 1");
    }
  }
}
