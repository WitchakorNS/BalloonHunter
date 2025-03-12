import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Shop extends JPanel {
    private final Image bg;
    private JPanel cardPanel2;
    private CardLayout cardLayout2;
    private JLabel coinLabel;
    private Inventory inventory; // เพิ่มการอ้างอิงไปยัง Inventory

    private boolean isMP5Owned = false;
    private boolean isAKOwned = false;
    private boolean isSGOwned = false;
    private boolean isM4Owned = false;

    public Shop(JPanel cardPanel2, CardLayout cardLayout2) {
        this.cardPanel2 = cardPanel2;
        this.cardLayout2 = cardLayout2;
        this.bg = new ImageIcon(getClass().getResource("/images/Shopbg1.png")).getImage();
        setLayout(null);
        
        // Load gun ownership statuses
        loadGunOwnerships();

        JButton mp5Button = new JButton();
        mp5Button.setBounds(175,175, 250, 250);
        mp5Button.setIcon(getScaledIcon(isMP5Owned ? "/images/gun/mp5_buy.png" : "/images/gun/mp5.png", 250, 250));
        mp5Button.setBorderPainted(false);
        mp5Button.setContentAreaFilled(false);
        mp5Button.setEnabled(!isMP5Owned);
        mp5Button.addActionListener((ActionEvent e) -> {
            int coin = GamePreferences.loadCoin();
            if (!isMP5Owned && coin >= 100) {
                coin -= 100;
                GamePreferences.saveCoin(coin);

                coinLabel.setText("Coins: " + coin);
                isMP5Owned = true;
                GamePreferences.saveMP5Owned(true);
                mp5Button.setIcon(getScaledIcon("/images/gun/mp5_buy.png", 250, 250));
                mp5Button.setEnabled(false);
                
                // อัปเดต Inventory หากมีการตั้งค่า
                if (inventory != null) {
                    inventory.refreshInventory(); // เรียกเมธอดใหม่เพื่ออัปเดตปืนที่มี
                }
                
                JOptionPane.showMessageDialog(this, "You have successfully purchased MP5!", "Purchase Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (isMP5Owned) {
                JOptionPane.showMessageDialog(this, "You have already purchased an MP5!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money! Must have at least 100 Coins", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(mp5Button);

        JButton akButton = new JButton();
        akButton.setBounds(615, 175, 250, 250);
        akButton.setIcon(getScaledIcon(isAKOwned ? "/images/gun/ak_buy.png" : "/images/gun/ak.png", 250, 250));
        akButton.setBorderPainted(false);
        akButton.setContentAreaFilled(false);
        akButton.setEnabled(!isAKOwned);
        akButton.addActionListener((ActionEvent e) -> {
            int coin = GamePreferences.loadCoin();
            if (!isAKOwned && coin >= 200) {
                coin -= 200;
                GamePreferences.saveCoin(coin);
                coinLabel.setText("Coins: " + coin);
                isAKOwned = true;
                GamePreferences.saveAKOwned(true);
                akButton.setIcon(getScaledIcon("/images/gun/ak_buy.png", 250, 250));
                akButton.setEnabled(false);
                
                // อัปเดต Inventory หากมีการตั้งค่า
                if (inventory != null) {
                    inventory.refreshInventory(); // เรียกเมธอดใหม่เพื่ออัปเดตปืนที่มี
                }
                
                JOptionPane.showMessageDialog(this, "You have successfully purchased AK47!", "Purchase Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (isAKOwned) {
                JOptionPane.showMessageDialog(this, "You have already purchased an AK47!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money! Must have at least 200 Coins", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(akButton);

        JButton shotgunButton = new JButton();
        shotgunButton.setBounds(175, 480, 250, 250);
        shotgunButton.setIcon(getScaledIcon(isSGOwned ? "/images/gun/shotgun_buy.png" : "/images/gun/shotgun.png", 250, 250));
        shotgunButton.setBorderPainted(false);
        shotgunButton.setContentAreaFilled(false);
        shotgunButton.setEnabled(!isSGOwned);
        shotgunButton.addActionListener((ActionEvent e) -> {
            int coin = GamePreferences.loadCoin();
            if (!isSGOwned && coin >= 300) {
                coin -= 300;
                GamePreferences.saveCoin(coin);
                coinLabel.setText("Coins: " + coin);
                isSGOwned = true;
                GamePreferences.saveSGOwned(true);
                shotgunButton.setIcon(getScaledIcon("/images/gun/shotgun_buy.png", 250, 250));
                shotgunButton.setEnabled(false);
                
                // อัปเดต Inventory หากมีการตั้งค่า
                if (inventory != null) {
                    inventory.refreshInventory(); // เรียกเมธอดใหม่เพื่ออัปเดตปืนที่มี
                }
                
                JOptionPane.showMessageDialog(this, "You have successfully purchased Shotgun!", "Purchase Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (isSGOwned) {
                JOptionPane.showMessageDialog(this, "You have already purchased an Shotgun!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money! Must have at least 300 Coins", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(shotgunButton);

        JButton m4Button = new JButton();
        m4Button.setBounds(615, 480, 250, 250);
        m4Button.setIcon(getScaledIcon(isM4Owned ? "/images/gun/m4_buy.png" : "/images/gun/m4.png", 250, 250));
        m4Button.setBorderPainted(false);
        m4Button.setContentAreaFilled(false);
        m4Button.setEnabled(!isM4Owned);
        m4Button.addActionListener((ActionEvent e) -> {
            int coin = GamePreferences.loadCoin();
            if (!isM4Owned && coin >= 500) {
                coin -= 500;
                GamePreferences.saveCoin(coin);
                coinLabel.setText("Coins: " + coin);
                isM4Owned = true;
                GamePreferences.saveM4Owned(true);
                m4Button.setIcon(getScaledIcon("/images/gun/m4_buy.png", 250, 250));
                m4Button.setEnabled(false);
                
                // อัปเดต Inventory หากมีการตั้งค่า
                if (inventory != null) {
                    inventory.refreshInventory(); // เรียกเมธอดใหม่เพื่ออัปเดตปืนที่มี
                }
                
                JOptionPane.showMessageDialog(this, "You have successfully purchased M4!", "Purchase Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (isM4Owned) {
                JOptionPane.showMessageDialog(this, "You have already purchased an M4!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money! Must have at least 500 Coins", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(m4Button);

        JButton exit = new JButton();
        exit.setBounds(5, 5,60,57);
        exit.setIcon(getScaledIcon("/images/exit.png", 60, 57));
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.addActionListener((ActionEvent e) -> {
            cardLayout2.show(cardPanel2, "StartMenu");
        });
        add(exit);

        int coin = GamePreferences.loadCoin();
        coinLabel = new JLabel("Coins: " + coin, SwingConstants.CENTER);
        coinLabel.setFont(new Font("Arial", Font.BOLD, 25));
        coinLabel.setForeground(Color.WHITE);
        coinLabel.setBounds(850, 10, 200, 30);
        add(coinLabel);
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            int updatedCoin = GamePreferences.loadCoin();
            coinLabel.setText("Coins: " + updatedCoin);
        });
        timer.start();
    }
    
    // เพิ่มเมธอดเพื่อรับ Inventory
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    private void loadGunOwnerships() {
        isMP5Owned = GamePreferences.isMP5Owned();
        isAKOwned = GamePreferences.isAKOwned();
        isSGOwned = GamePreferences.isSGOwned();
        isM4Owned = GamePreferences.isM4Owned();
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