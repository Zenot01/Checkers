import javax.swing.*;

public class ErrorMessage {

  static public void wrongPawn(){
    JOptionPane.showMessageDialog(null, "Zostal wybrany zly pionek, wybierz inny pionek", "Blad", JOptionPane.INFORMATION_MESSAGE);
  }

  static public void wrongMove(){
    JOptionPane.showMessageDialog(null, "Nie mozesz przemiescic pionka na to pole, wybierz inne pole", "Blad", JOptionPane.INFORMATION_MESSAGE);
  }

  static public void possibleBeating(){
    JOptionPane.showMessageDialog(null, "Mozliwe jest bicie", "Blad", JOptionPane.INFORMATION_MESSAGE);
  }
}
