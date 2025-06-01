import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPanel extends JPanel{
  private JLabel title = new JLabel("WARCABY");
  private Board board;
  private StatPanel statPanel;

  StartingPanel(){
    setBounds(0,0, 700, 750);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(new Color(245, 235, 200));


    JButton button = new JButton("jeden komputer");
    handleButton(button);
    button.setVisible(true);

    configTitle();
    title.setVisible(true);

    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(Box.createRigidArea(new Dimension(0, 250)));
    add(title);
    add(Box.createRigidArea(new Dimension(0, 50)));
    add(button);
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public void setStatPanel(StatPanel statPanel) {
    this.statPanel = statPanel;
  }

  private void configTitle(){
    title.setForeground(new Color(100, 50, 20));
    title.setBackground(new Color(245, 235, 200));
    title.setOpaque(true);
    title.setFont(new Font("Arial", Font.BOLD, 50));
  }

  private void handleButton(JButton button){
    button.setAlignmentX(CENTER_ALIGNMENT);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
        board.setVisible(true);
        statPanel.setVisible(true);
        Players.startGame();
      }
    });
  }
}
