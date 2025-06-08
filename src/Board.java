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
  private boolean[] beatings = new boolean[4];

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
    if (checkPawn(row, column)){
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
    }
    ErrorMessage.wrongPawn();
    errorChoose = true;
  }


  private boolean checkPawn(int choosedRow, int choosedColumn){
    boolean ok = false;
    boolean possibleBeating = false;
    for (int i = 0;i < 4;++i) beatings[i] = false;

    for (int row = 0; row < 10; row++) {
      for (int column = 0; column < 10; column++) {
        if (Players.board[row][column] == 2){
          //check right
          if (column < 8){
            //top right
            if (row > 1){
              if (Players.board[row - 1][column + 1] == 1 && Players.board[row - 2][column + 2] == 0){
                if (choosedRow == row && choosedColumn == column) ok = true;
                possibleBeating = true;
                beatings[0] = true;
              }
            }
          }
          //check left
          if (column > 1){
            //top left
            if (row > 1){
              if (Players.board[row - 1][column - 1] == 1 && Players.board[row - 2][column - 2] == 0){
                if (choosedRow == row && choosedColumn == column) ok = true;
                possibleBeating = true;
                beatings[1] = true;
              }
            }
          }
        } else if (Players.board[row][column] == 1){
          //check right
          if (column < 8){
            //bottom right
            if (row > 8){
              if (Players.board[row + 1][column + 1] == 2 && Players.board[row + 2][column + 2] == 0){
                if (choosedRow == row && choosedColumn == column) ok = true;
                possibleBeating = true;
                beatings[2] = true;
              }
            }
          }
          //check left
          if (column > 1){
            //bottom left
            if (row > 8){
              if (Players.board[row + 1][column - 1] == 2 && Players.board[row + 2][column - 2] == 0){
                if (choosedRow == row && choosedColumn == column) ok = true;
                possibleBeating = true;
                beatings[3] = true;
              }
            }
          }
        }
      }
    }
    if (possibleBeating && !ok) return false;

    return true;
  }


  private void handlePawn(int toRow, int toColumn){
    if (checkMove(choosenPawn.y, choosenPawn.x, toRow, toColumn) == -1){
      errorMove = true;
      return;
    }
    handleBeating(choosenPawn.y, choosenPawn.x);
    Players.board[choosenPawn.y][choosenPawn.x] = 0;
    if (Players.round == Players.round_n.PLAYERONE) Players.board[toRow][toColumn] = 1;
    else Players.board[toRow][toColumn] = 2;
    promoteToKing(toRow, toColumn);
    choosed = false;
    errorMove = false;

    repaint();
  }


  private void handleBeating(int row, int column){
    if (beatings[0]){
      Players.board[row - 1][column + 1] = 0;
    } else if (beatings[1]){
      Players.board[row + 1][column + 1] = 0;
    } else if (beatings[2]){
      Players.board[row - 1][column - 1] = 0;
    } else if (beatings[3]){
      Players.board[row + 1][column - 1] = 0;
    }

    boolean end = true;
    for (int row_2 = 0; row_2 < 10; row_2++) {
      for (int column_2 = 0; column_2 < 10; column_2++) {
        if (Players.round == Players.round_n.PLAYERONE){
          if (Players.board[row_2][column_2] == 2 || Players.board[row_2][column_2] == 20) end = false;
        }
        else{
          if (Players.board[row_2][column_2] == 1 || Players.board[row_2][column_2] == 10) end = false;
        }
      }
    }

    if (end) handleEndGame();
  }


  private int checkMove(int row, int column, int toRow, int toColumn){
    if (row == toRow && column == toColumn){ErrorMessage.wrongMove(); return -1;}

    int res = checkBeating(row, column, toRow, toColumn);
    if (res == 1) {ErrorMessage.possibleBeating(); return -1;}
    else if (res == 2) return 0;


    if (Players.round == Players.round_n.PLAYERTWO && Players.board[row][column] == 2){ //TODO add exception and beating for kings
      if (toRow > row) {ErrorMessage.wrongMove(); return -1;}
    } else if (Players.round == Players.round_n.PLAYERONE && Players.board[row][column] == 1){
      if (toRow < row) {ErrorMessage.wrongMove(); return -1;}
    }
    if (!(row + 1 == toRow || row - 1 == toRow)){ErrorMessage.wrongMove(); return -1;}
    if (!(column + 1 == toColumn || column - 1 == toColumn)){ErrorMessage.wrongMove(); return -1;}

    if (Players.board[toRow][toColumn] != 0) {ErrorMessage.wrongMove(); return -1;}

    return 0;
  }


  private int checkBeating(int row, int column, int toRow, int toColumn){
    boolean execute = false;
    boolean possible = false;

    for (int i = 0; i < 4; i++) {
      if (beatings[i]) possible = true;
    }

    if (beatings[0]){
      if (row - 2 == toRow && column + 2 == toColumn) execute = true;
    }
    if (beatings[1]){
      if (row + 2 == toRow && column + 2 == toColumn) execute = true;
    }
    if (beatings[2]){
      if (row + 2 == toRow && column - 2 == toColumn) execute = true;
    }
    if (beatings[3]){
      if (row - 2 == toRow && column - 2 == toColumn) execute = true;
    }

    if (possible && !execute) return 1;
    if (execute) return 2;
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
