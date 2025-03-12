import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import Weapons.*;

public class Inventory extends JPanel {
    private final Image bg;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ArrayList<Gun> ownedGuns;
    private Gun selectedGun;
    
    // Labels to show currently selected weapon
    private JLabel selectedWeaponLabel;
    private JLabel damageLabel;
    
    // Reference to the BalloonShooter instance for updating cursor
    private BalloonShooter gamePanel;
    
    // เก็บปุ่มต่างๆ ไว้เป็นตัวแปรสมาชิก
    private JButton pistolButton;
    private JButton mp5Button;
    private JButton akButton;
    private JButton sgButton;
    private JButton m4Button;
    
    public Inventory(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.bg = new ImageIcon(getClass().getResource("/images/inventorybg.png")).getImage();
        setLayout(null);
        
        // Initialize guns collection
        ownedGuns = new ArrayList<>();
        // Always have a pistol
        ownedGuns.add(new Pistol());
        selectedGun = new Pistol(); // Default gun
        
        // Check saved owned guns
        loadOwnedGuns();
        
        // Exit button
        JButton exitButton = new JButton();
        exitButton.setBounds(5, 5, 60, 57); // w: 60 h: 57
        exitButton.setIcon(getScaledIcon("/images/exit.png", 60, 57));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "StartMenu");
        });
        add(exitButton);
        
        // Selected weapon info
        selectedWeaponLabel = new JLabel("Selected: Pistol", SwingConstants.CENTER);
        selectedWeaponLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectedWeaponLabel.setForeground(Color.WHITE);
        selectedWeaponLabel.setBounds(750, 15, 400, 30);
        add(selectedWeaponLabel);
        
        damageLabel = new JLabel("Damage: 500.0", SwingConstants.CENTER);
        damageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        damageLabel.setForeground(Color.WHITE);
        damageLabel.setBounds(750, 38, 400, 30);
        add(damageLabel);
        
        // Create weapon buttons
        createWeaponButtons();
    }
    
    // เพิ่มเมธอดเพื่อรับการอ้างอิงไปยัง BalloonShooter
    public void setGamePanel(BalloonShooter gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    // เพิ่มเมธอดรีเฟรชอินเวนทอรี
    public void refreshInventory() {
        // ลบปืนทั้งหมด
        ownedGuns.clear();
        
        // เพิ่มปืนที่มีอยู่กลับเข้าไป
        ownedGuns.add(new Pistol()); // เพิ่มปืนพื้นฐานเสมอ
        
        // โหลดปืนที่มีทั้งหมดจาก GamePreferences
        loadOwnedGuns();
        
        // อัปเดตสถานะปุ่มจากปืนที่มี
        updateButtonStatus();
    }
    
    private void loadOwnedGuns() {
        // Load owned guns from GamePreferences
        if (GamePreferences.isMP5Owned()) {
            addGunIfNotExists(new MP5());
        }
        if (GamePreferences.isAKOwned()) {
            addGunIfNotExists(new AK47());
        }
        if (GamePreferences.isSGOwned()) {
            addGunIfNotExists(new Shotgun());
        }
        if (GamePreferences.isM4Owned()) {
            addGunIfNotExists(new M4());
        }
    }
    
    private void addGunIfNotExists(Gun gun) {
        boolean exists = false;
        for (Gun g : ownedGuns) {
            if (g.getClass().equals(gun.getClass())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            ownedGuns.add(gun);
        }
    }
    
    private void createWeaponButtons() {
        // Pistol (default)
        pistolButton = createGunButton("Pistol", "/images/gun/pistol_inven.png", new Pistol(), 150, 260, true);
        add(pistolButton);
        
        // MP5
        mp5Button = createGunButton("MP5", "/images/gun/mp5_inven.png", new MP5(), 423, 260, GamePreferences.isMP5Owned());
        add(mp5Button);
        
        // AK-47
        akButton = createGunButton("AK47", "/images/gun/ak47_inven.png", new AK47(), 696, 260, GamePreferences.isAKOwned());
        add(akButton);
        
        // Shotgun
        sgButton = createGunButton("Shotgun", "/images/gun/shotgun_inven.png", new Shotgun(), 287, 534, GamePreferences.isSGOwned());
        add(sgButton);
        
        // M4
        m4Button = createGunButton("M4", "/images/gun/m4_inven.png", new M4(), 560, 534, GamePreferences.isM4Owned());
        add(m4Button);
    }
    
    // เพิ่มเมธอดอัปเดตสถานะปุ่ม
    private void updateButtonStatus() {
        // อัปเดตสถานะปุ่มตามปืนที่มี
        mp5Button.setEnabled(GamePreferences.isMP5Owned());
        akButton.setEnabled(GamePreferences.isAKOwned());
        sgButton.setEnabled(GamePreferences.isSGOwned());
        m4Button.setEnabled(GamePreferences.isM4Owned());
    }
    
    private JButton createGunButton(String name, String imagePath, Gun gun, int x, int y, boolean enabled) {
        JButton button = new JButton();
        button.setBounds(x, y, 243, 244); // w: 243 h: 244
        button.setIcon(getScaledIcon(imagePath, 243, 244));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setEnabled(enabled);
        
        // Add tooltip
        String damage = "0.0";
        if (gun instanceof Pistol) damage = String.valueOf(((Pistol)gun).getDamages());
        else if (gun instanceof MP5) damage = String.valueOf(((MP5)gun).getDamages());
        else if (gun instanceof AK47) damage = String.valueOf(((AK47)gun).getDamages());
        else if (gun instanceof Shotgun) damage = String.valueOf(((Shotgun)gun).getDamages());
        else if (gun instanceof M4) damage = String.valueOf(((M4)gun).getDamages());
        
        button.setToolTipText(name + " - Damage: " + damage);
        
        button.addActionListener((ActionEvent e) -> {
            selectGun(gun, name);
            updateGunIcons(button);
        });
        
        return button;
    }

    private void updateGunIcons(JButton selectedButton) {
        // Reset icon ของปืนทั้งหมดเป็นปกติ
        pistolButton.setIcon(getScaledIcon("/images/gun/pistol_inven.png", 243, 244));
        mp5Button.setIcon(getScaledIcon("/images/gun/mp5_inven.png", 243, 244));
        akButton.setIcon(getScaledIcon("/images/gun/ak47_inven.png", 243, 244));
        sgButton.setIcon(getScaledIcon("/images/gun/shotgun_inven.png", 243, 244));
        m4Button.setIcon(getScaledIcon("/images/gun/m4_inven.png", 243, 244));
        
        // ตรวจสอบว่าเป็นปืนอะไรแล้วเปลี่ยน Icon
        if (selectedButton == pistolButton) {
            selectedButton.setIcon(getScaledIcon("/images/gun/Pistol_equipped.png", 243, 244));
        } else if (selectedButton == mp5Button) {
            selectedButton.setIcon(getScaledIcon("/images/gun/MP5_equipped.png", 243, 244));
        } else if (selectedButton == akButton) {
            selectedButton.setIcon(getScaledIcon("/images/gun/AK47_equipped.png", 243, 244));
        } else if (selectedButton == sgButton) {
            selectedButton.setIcon(getScaledIcon("/images/gun/Shotgun_equipped.png", 243, 244));
        } else if (selectedButton == m4Button) {
            selectedButton.setIcon(getScaledIcon("/images/gun/M4_equipped.png", 243, 244));
        }
    }
    
    
    public void selectGun(Gun gun, String name) {
        selectedGun = gun;
        
        // Update labels
        selectedWeaponLabel.setText("Selected: " + name);
        
        // Get damage value based on gun type
        double damage = 0.0;
        if (gun instanceof Pistol) damage = ((Pistol)gun).getDamages();
        else if (gun instanceof MP5) damage = ((MP5)gun).getDamages();
        else if (gun instanceof AK47) damage = ((AK47)gun).getDamages();
        else if (gun instanceof Shotgun) damage = ((Shotgun)gun).getDamages();
        else if (gun instanceof M4) damage = ((M4)gun).getDamages();
        
        double all_damage = 500 + 500*damage;
        damageLabel.setText("Damage: " + all_damage);
        
        // Save selected gun to preferences
        if (gun instanceof Pistol) GamePreferences.saveSelectedGun("Pistol");
        else if (gun instanceof MP5) GamePreferences.saveSelectedGun("MP5");
        else if (gun instanceof AK47) GamePreferences.saveSelectedGun("AK47");
        else if (gun instanceof Shotgun) GamePreferences.saveSelectedGun("Shotgun");
        else if (gun instanceof M4) GamePreferences.saveSelectedGun("M4");
        
        // ทดลองแสดง cursor ของปืนในหน้า Inventory ด้วย
        String cursorPath = "/images/" + name + "_Cursor.png";
        ImageIcon gunIcon = new ImageIcon(getClass().getResource(cursorPath));
        if (gunIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Cursor gunCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                gunIcon.getImage(), new Point(0, 0), name + " Cursor");
            setCursor(gunCursor);
        }
    }
    
    public Gun getSelectedGun() {
        return selectedGun;
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