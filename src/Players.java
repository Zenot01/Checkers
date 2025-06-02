import java.util.ArrayList;

public class Players{
  public static ArrayList<Point> playerOne = new ArrayList<>();
  public static ArrayList<Point> playerTwo = new ArrayList<>();
  public static ArrayList<Point> playerOneKings = new ArrayList<>();
  public static ArrayList<Point> playerTwoKings = new ArrayList<>();
  public static round_n round = round_n.PLAYERTWO;
  public enum round_n{
    PLAYERONE,
    PLAYERTWO,
  }
  protected static boolean endGame = false;
  protected static round_n winner;

  public static void startGame(){
    playerOne.clear();
    playerTwo.clear();
    playerOneKings.clear();
    playerTwoKings.clear();
    for (int rows = 0; rows < 4; ++rows)
    {
      for (int columns = 0; columns < 10; ++columns)
      {
        if ((rows % 2 == 0 && columns % 2 == 0) || (rows % 2 != 0 && columns % 2 != 0))
        {
          playerOne.add(new Point(columns, rows));
          playerTwo.add(new Point(columns, rows + 6));
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
