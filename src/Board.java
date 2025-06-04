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

  public Board(){
    setBounds(0,50, 700, 700);
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
    if (Players.round == Players.round_n.PLAYERTWO)
    {
      for (int i = 0; i < Players.playerTwo.size(); ++i){
        if (Players.playerTwo.get(i).y == row && Players.playerTwo.get(i).x == column){
          choosenPawn.y = row;
          choosenPawn.x = column;
          choosed = true;
          repaint();
          errorChoose = false;
          return;
        }
      }
    }
    else if (Players.round == Players.round_n.PLAYERONE)
    {
      for (int i = 0; i < Players.playerOne.size(); ++i){
        if (Players.playerOne.get(i).y == row && Players.playerOne.get(i).x == column){
          choosenPawn.y = row;
          choosenPawn.x = column;
          choosed = true;
          repaint();
          errorChoose = false;
          return;
        }
      }
    }
    ErrorMessage.wrongPawn();
    errorChoose = true;
  }


  private void handlePawn(int row, int column){
    if(Players.round == Players.round_n.PLAYERTWO){
      for (int i = 0; i < Players.playerTwo.size(); ++i){
        if (Players.playerTwo.get(i).x == choosenPawn.x && Players.playerTwo.get(i).y == choosenPawn.y){
          if (checkMove(Players.playerTwo.get(i).y, Players.playerTwo.get(i).x, row, column) == -1){
            errorMove = true;
            return;
          }
          Players.playerTwo.get(i).x = column;
          Players.playerTwo.get(i).y = row;
          promoteToKing(row, i);
          choosed = false;
          errorMove = false;
        }
      }
    }
    else if(Players.round == Players.round_n.PLAYERONE){
      for (int i = 0; i < Players.playerOne.size(); ++i){
        if (Players.playerOne.get(i).x == choosenPawn.x && Players.playerOne.get(i).y == choosenPawn.y){
          if (checkMove(Players.playerOne.get(i).y, Players.playerOne.get(i).x, row, column) == -1){
            errorMove = true;
            return;
          }
          Players.playerOne.get(i).x = column;
          Players.playerOne.get(i).y = row;
          promoteToKing(row, i);
          choosed = false;
          errorMove = false;
        }
      }
    }
    handleBeating(row, column);
    repaint();
  }


  private int checkMove(int row, int column, int toRow, int toColumn){
    if (row == toRow && column == toColumn){ErrorMessage.wrongMove(); return -1;}
    if (!(row + 1 == toRow || row - 1 == toRow)){ErrorMessage.wrongMove(); return -1;}
    if (!(column + 1 == toColumn || column - 1 == toColumn)){ErrorMessage.wrongMove(); return -1;}
    if (checkBeating() == -1) {ErrorMessage.possibleBeating(); return -1;}

    return 0;
  }


  private int checkBeating(){
    if (Players.round == Players.round_n.PLAYERTWO){
      for (int i = 0; i < Players.playerOne.size(); ++i){
        if ((Players.playerOne.get(i).x == choosenPawn.x + 1 || Players.playerOne.get(i).x == choosenPawn.x - 1) && (Players.playerOne.get(i).y == choosenPawn.y + 1 || Players.playerOne.get(i).y == choosenPawn.y - 1)){
          for (int k = 0; k < Players.playerOne.size(); ++k){
            if ((Players.playerOne.get(k).x == choosenPawn.x + 2 || Players.playerOne.get(k).x == choosenPawn.x - 2) && (Players.playerOne.get(k).y == choosenPawn.y + 2 || Players.playerOne.get(k).y == choosenPawn.y - 2)) return 0;
          }
          return -1;
        }
      }
    } else if (Players.round == Players.round_n.PLAYERONE){
      for (int i = 0; i < Players.playerTwo.size(); ++i){
        if ((Players.playerTwo.get(i).x == choosenPawn.x + 1 || Players.playerTwo.get(i).x == choosenPawn.x - 1) && (Players.playerTwo.get(i).y == choosenPawn.y + 1 || Players.playerTwo.get(i).y == choosenPawn.y - 1)){
          for (int k = 0; k < Players.playerTwo.size(); ++k){
            if ((Players.playerTwo.get(k).x == choosenPawn.x + 2 || Players.playerTwo.get(k).x == choosenPawn.x - 2) && (Players.playerTwo.get(k).y == choosenPawn.y + 2 || Players.playerTwo.get(k).y == choosenPawn.y - 2)) return 0;
          }
          return -1;
        }
      }
    }

    return 0;
  }


  private void handleBeating(int toRow, int toColumn){
    if (Players.round == Players.round_n.PLAYERTWO){
      for (int i = 0; i < Players.playerOne.size(); ++i){
        if (Players.playerOne.get(i).x == toColumn && Players.playerOne.get(i).y == toRow){
          Players.playerOne.remove(i);
          if (Players.playerOne.isEmpty()){
            Players.winner = Players.round_n.PLAYERTWO;
            handleEndGame();
          }
          return;
        }
      }
    }
    else if (Players.round == Players.round_n.PLAYERONE){
      for (int i = 0; i < Players.playerTwo.size(); ++i){
        if (Players.playerTwo.get(i).x == toColumn && Players.playerTwo.get(i).y == toRow){
          Players.playerTwo.remove(i);
          if (Players.playerTwo.isEmpty()){
            Players.winner = Players.round_n.PLAYERONE;
            handleEndGame();
          }
          return;
        }
      }
    }
  }


  private void promoteToKing(int row, int ind){
    if (Players.round == Players.round_n.PLAYERTWO){
      if (row == 0){
        Players.playerTwoKings.add(new Point(Players.playerTwo.get(ind).x, Players.playerTwo.get(ind).y));
        Players.playerTwo.remove(ind);
      }
    }else if (Players.round == Players.round_n.PLAYERONE){
      if (row == 9){
        Players.playerOneKings.add(new Point(Players.playerOne.get(ind).x, Players.playerOne.get(ind).y));
        Players.playerOne.remove(ind);
      }
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
    g.setColor(new Color(120, 72, 33));
    for(int i = 0; i < Players.playerOne.size(); ++i){
      g.fillOval(Players.playerOne.get(i).x * 70 + 10, Players.playerOne.get(i).y * 70 + 10, 50, 50);
    }

    g.setColor(new Color(255, 239, 184));
    for(int i = 0; i < Players.playerTwo.size(); ++i){
      g.fillOval(Players.playerTwo.get(i).x * 70 + 10, Players.playerTwo.get(i).y * 70 + 10, 50, 50);
    }

    if (choosed){
      g.setColor(new Color(64, 224, 208));
      g.fillOval(choosenPawn.x * 70 + 10, choosenPawn.y * 70 + 10, 50, 50);
    }

    for (int i = 0; i < Players.playerOneKings.size(); ++i){
      g.setColor(new Color(120, 72, 33));
      g.fillOval(Players.playerOneKings.get(i).x * 70 + 10, Players.playerOneKings.get(i).y * 70 + 10, 50, 50);
      g.setColor(Color.WHITE);
      g.fillOval(Players.playerOneKings.get(i).x * 70 + 20, Players.playerOneKings.get(i).y * 70 + 20, 30, 30);
    }
    for (int i = 0; i < Players.playerTwoKings.size(); ++i){
      g.setColor(new Color(255, 239, 184));
      g.fillOval(Players.playerTwoKings.get(i).x * 70 + 10, Players.playerTwoKings.get(i).y * 70 + 10, 50, 50);
      g.setColor(Color.WHITE);
      g.fillOval(Players.playerTwoKings.get(i).x * 70 + 20, Players.playerTwoKings.get(i).y * 70 + 20, 30, 30);
    }
  }

}
