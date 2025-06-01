import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


public class Main {
  public static void main(String[] args) {
    //frame
    JFrame frame = new JFrame("Warcaby");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(714, 787);
    frame.setLayout(null);

    //elements
    StatPanel statPanel = new StatPanel();
    Board board = new Board();
    StartingPanel startingPanel = new StartingPanel();
    EndGamePanel endGamePanel = new EndGamePanel();

    //setters
    startingPanel.setStatPanel(statPanel);
    startingPanel.setBoard(board);
    board.setStatPanel(statPanel);
    board.setEndGamePanel(endGamePanel);
    endGamePanel.setStartingPanel(startingPanel);

    //add
    frame.add(endGamePanel);
    frame.add(startingPanel);
    frame.add(statPanel);
    frame.add(board);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
