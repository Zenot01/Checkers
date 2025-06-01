import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {
  protected static JLabel scoreLabel = new JLabel("Tura: Gracz 1");

  StatPanel(){
    setBounds(0,0, 700, 50);
    setVisible(false);
    setBackground(new Color(245, 235, 200));
    SetScoreLabel();
    add(scoreLabel);
  }

  private void SetScoreLabel(){
    scoreLabel.setForeground(new Color(100, 50, 20));
    scoreLabel.setBackground(new Color(245, 235, 200));
    scoreLabel.setOpaque(true);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
  }
}
