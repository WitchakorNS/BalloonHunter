import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameMenu extends JFrame {
    private JLabel coinLabel;
    private final Image backgroundImage;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private BalloonShooter gamePanel;

    public GameMenu() {
        this.backgroundImage = new ImageIcon(getClass().getResource("images/Background4.png")).getImage();
        setTitle("Balloon Hunter!");
        setSize(1050, 850); // w: 800 h: 600
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        Sound.playBackgroundMusic("C:\\Users\\ASUS\\Downloads\\GAME (3)\\GAME\\AllSound\\bgsong3.wav");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createStartMenu(), "StartMenu");
        cardPanel.add(new Rules(cardPanel, cardLayout), "Rules");
        cardPanel.add(new Shop(cardPanel, cardLayout), "Shop");
        cardPanel.add(new Inventory(cardPanel, cardLayout), "Inventory");

        setContentPane(cardPanel);
        setVisible(true);

        Shop shopPanel = new Shop(cardPanel, cardLayout);
        Inventory inventoryPanel = new Inventory(cardPanel, cardLayout);

        // ตั้งค่าการอ้างอิงซึ่งกันและกัน
        shopPanel.setInventory(inventoryPanel);
        inventoryPanel.setGamePanel(gamePanel);

        // เพิ่มคลาสต่างๆ ลงใน cardPanel
        cardPanel.add(shopPanel, "Shop");
        cardPanel.add(inventoryPanel, "Inventory");
    }

    private JPanel createStartMenu() {
        JPanel startMenuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        startMenuPanel.setLayout(null);
    
        int btnWidth = 300, btnHeight = 75;// w: 200 h: 50
    
        JButton startButton = new JButton();
        startButton.setBounds(375, 415, btnWidth, btnHeight);
        startButton.setIcon(getScaledIcon("images/start.png", btnWidth, btnHeight));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Rules");
            if (gamePanel == null) {
                gamePanel = new BalloonShooter(cardPanel, cardLayout);;
                cardPanel.add(gamePanel, "Game");
            }
            
        });
        startMenuPanel.add(startButton);
    
        JButton shopButton = new JButton();
        shopButton.setBounds(375, 505, btnWidth, btnHeight);
        shopButton.setIcon(getScaledIcon("images/shop.png", btnWidth, btnHeight));
        shopButton.setBorderPainted(false);
        shopButton.setContentAreaFilled(false);
        shopButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Shop");
        });
        startMenuPanel.add(shopButton);
    
        JButton inventButton = new JButton();
        inventButton.setBounds(375, 595, btnWidth, btnHeight);
        inventButton.setIcon(getScaledIcon("images/inventory.png", btnWidth, btnHeight));
        inventButton.setBorderPainted(false);
        inventButton.setContentAreaFilled(false);
        inventButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Inventory");
        });
        startMenuPanel.add(inventButton);
    
    
        return startMenuPanel;
    }

    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}
