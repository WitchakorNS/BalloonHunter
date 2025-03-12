import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

class Rules extends JPanel {
    private final Image bg;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Rules(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.bg = new ImageIcon(getClass().getResource("/images/Rules1.png")).getImage();
        setLayout(null);

        JButton continueButton = new JButton();
        continueButton.setBounds(375, 700, 300, 75);
        continueButton.setIcon(getScaledIcon("/images/continue.png", 300, 75));
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            BalloonShooter gamePanel = null;
        
            for (Component comp : cardPanel.getComponents()) {
                if (comp instanceof BalloonShooter) {
                    gamePanel = (BalloonShooter) comp;
                    break;
                }
            }
        
            if (gamePanel == null) {
                gamePanel = new BalloonShooter(cardPanel, cardLayout);;
                cardPanel.add(gamePanel, "Game");
            }
            
            Sound.stopBackgroundMusic();
            gamePanel.startGame();
            cardLayout.show(cardPanel, "Game");
        });
        add(continueButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}