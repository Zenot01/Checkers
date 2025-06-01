import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGamePanel extends JPanel {
  private JLabel endGameScore = new JLabel();
  private StartingPanel startingPanel;

  EndGamePanel(){
    setBounds(0,0, 700, 750);
    setVisible(false);
    setBackground(new Color(245, 235, 200));

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    configEndGameScore();

    JButton button = new JButton("Zagraj ponownie");
    handleButton(button);
    button.setVisible(true);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    endGameScore.setAlignmentX(Component.CENTER_ALIGNMENT);


    add(Box.createRigidArea(new Dimension(0, 350)));
    add(endGameScore);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(button);

  }

  public void setStartingPanel(StartingPanel startingPanel) {
    this.startingPanel = startingPanel;
  }

  private void configEndGameScore(){
    String text = "Wygral: Gracz 1";

    if (Players.winner == Players.round_n.PLAYERONE) text += "Gracz 1";
    if (Players.winner == Players.round_n.PLAYERTWO) text += "Gracz 2";

    endGameScore.setText(text);

    endGameScore.setForeground(new Color(100, 50, 20));
    endGameScore.setBackground(new Color(245, 235, 200));
    endGameScore.setOpaque(true);
    endGameScore.setFont(new Font("Arial", Font.BOLD, 30));
  }

  private void handleButton(JButton button){
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //TODO endgamepanel znika i pojawia sie na nowo starting panel i atrybuty konca gry sa zerowane
        Players.endGame = false;
        setVisible(false);
        startingPanel.setVisible(true);
      }
    });
  }
}
