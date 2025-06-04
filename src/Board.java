import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {
  private Point choosenPawn = new Point(0, 0);
  private boolean choosed = false;
  private boolean errorChoose = false;
  private boolean errorMove = false;
  private int clickCount = 0;
  protected boolean endGame = false;
  private StatPanel statPanel;
  private EndGamePanel endGamePanel;

  public Board() {
    setBounds(0, 50, 700, 700);
    setVisible(false);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int column = x / 70;
        int row = y / 70;

        if (!errorChoose && !errorMove) clickCount++;
        if (clickCount == 1){
          choosePawn(row, column);
        }
        if (clickCount == 2){
          handlePawn(row, column);
          if (!errorChoose && !errorMove){
            clickCount = 0;
            Players.switchRounds();
          }
        }
      }
    });
  }


  public void setEndGamePanel(EndGamePanel endGamePanel) {
    this.endGamePanel = endGamePanel;
  }


  public void setStatPanel(StatPanel statPanel) {
    this.statPanel = statPanel;
  }


  private void handleEndGame(){
    Players.endGame = true;
    setVisible(false);
    statPanel.setVisible(false);
    endGamePanel.setVisible(true);
  }


  private void choosePawn(int row, int column){
    if (Players.round == Players.round_n.PLAYERONE){
      if (Players.board[row][column] == 1 || Players.board[row][column] == 10){
        choosenPawn.y = row;
        choosenPawn.x = column;
        choosed = true;
        errorChoose = false;
        repaint();
        return;
      }
    } else if (Players.round == Players.round_n.PLAYERTWO){
      if (Players.board[row][column] == 2 || Players.board[row][column] == 20){
        choosenPawn.y = row;
        choosenPawn.x = column;
        choosed = true;
        errorChoose = false;
        repaint();
        return;
      }
    }

    ErrorMessage.wrongPawn();
    errorChoose = true;
  }


  private void handlePawn(int toRow, int toColumn){
    if (checkMove(choosenPawn.y, choosenPawn.x, toRow, toColumn) == -1){
      errorMove = true;
      return;
    }
    Players.board[choosenPawn.y][choosenPawn.x] = 0;
    if (Players.round == Players.round_n.PLAYERONE) Players.board[toRow][toColumn] = 1;
    else Players.board[toRow][toColumn] = 2;
    promoteToKing(toRow, toColumn);
    choosed = false;
    errorMove = false;


    //handleBeating(row, column);
    repaint();
  }


  private int checkMove(int row, int column, int toRow, int toColumn){
    if (row == toRow && column == toColumn){ErrorMessage.wrongMove(); return -1;}
    if (Players.round == Players.round_n.PLAYERTWO && Players.board[row][column] == 2){
      if (toRow > row) {ErrorMessage.wrongMove(); return -1;}
    } else if (Players.round == Players.round_n.PLAYERONE && Players.board[row][column] == 1){
      if (toRow < row) {ErrorMessage.wrongMove(); return -1;}
    }
    if (!(row + 1 == toRow || row - 1 == toRow)){ErrorMessage.wrongMove(); return -1;}
    if (!(column + 1 == toColumn || column - 1 == toColumn)){ErrorMessage.wrongMove(); return -1;}
    if (Players.board[toRow][toColumn] != 0) {ErrorMessage.wrongMove(); return -1;}
    //if (checkBeating(row, column, toRow, toColumn) == -1) {ErrorMessage.possibleBeating(); return -1;}

    return 0;
  }




  private void promoteToKing(int row, int column){
   if (row == 0 && Players.board[row][column] == 2){
     Players.board[row][column] = 20;
    }else if (row == 10 && Players.board[row][column] == 1){
      Players.board[row][column] = 10;
    }
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    paintBoard(g);
    paintPawns(g);
  }


  private void paintBoard(Graphics g){
    Color lightBrown = new Color(181, 136, 99);
    Color cream = new Color(240, 217, 181);
    for (int rows = 0; rows < 10; ++rows){
      for (int columns = 0; columns < 10; ++columns){
        if (rows % 2 == 0){
          if (columns % 2 == 0) g.setColor(lightBrown);
          else g.setColor(cream);
          g.fillRect(columns * 70,rows * 70, 70, 70);
        }
        else{
          if (columns % 2 == 0) g.setColor(cream);
          else g.setColor(lightBrown);
          g.fillRect(columns * 70,rows * 70, 70, 70);
        }
      }
    }
  }


  private void paintPawns(Graphics g){
    for (int row = 0; row < 10; ++row){
      for (int column = 0; column < 10; ++column){

        if (Players.board[row][column] == 2){
          g.setColor(new Color(255, 239, 184));
          g.fillOval(column * 70 + 10, row * 70 + 10, 50, 50);
        }

        if (Players.board[row][column] == 1){
          g.setColor(new Color(120, 72, 33));
          g.fillOval(column * 70 + 10, row * 70 + 10, 50, 50);
        }

        if (Players.board[row][column] == 20){
          g.setColor(new Color(255, 239, 184));
          g.fillOval(column * 70 + 10, row * 70 + 10, 50, 50);
          g.setColor(Color.WHITE);
          g.fillOval(column * 70 + 20, row * 70 + 20, 30, 30);
        }

        if (Players.board[row][column] == 10){
          g.setColor(new Color(120, 72, 33));
          g.fillOval(column * 70 + 10, row * 70 + 10, 50, 50);
          g.setColor(Color.WHITE);
          g.fillOval(column * 70 + 20, row * 70 + 20, 30, 30);
        }
      }
    }


    if (choosed){
      g.setColor(new Color(64, 224, 208));
      g.fillOval(choosenPawn.x * 70 + 10, choosenPawn.y * 70 + 10, 50, 50);
    }
  }
}
