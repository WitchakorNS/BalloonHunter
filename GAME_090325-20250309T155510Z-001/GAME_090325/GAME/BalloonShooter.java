import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import Weapons.*;

public class BalloonShooter extends JPanel implements ActionListener, MouseListener {
    private final int WIDTH = 1200, HEIGHT = 900; // w: 800 h: 600
    private int GAME_TIME = 60;
    private int BALLOON_SPEED = 3;
    private int RANDOM_SPAWN_SPEED = 14;
    private final String[] COLOR_NAMES = {"RED", "BLUE", "GREEN", "YELLOW"};
    private int coin;

    // score's and combo's variables
    private final int baseScore = 500;
    private int damageBonus;
    private double damageMultiplier;
    private double comboMultiplier = 1.00;
    private double addScore;
    private boolean continuous_shot = false;
    private long lastClickTime;
    private final int COMBO_TIMEOUT = 5000;
    private ArrayList<PopupScore> popups = new ArrayList<>();
    private ComboTimerPopup comboTimerPopup = null;

    private Timer timer;
    private ArrayList<Balloon> balloons;
    private Random random;
    private String playerColor;
    private int score;
    private long startTime;
    private int level = 1; 
    private int gameTime = GAME_TIME;
    private Image bg;
    private boolean isGameOver = false;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Gun selectedGun;
    private JLabel gunLabel;

    public BalloonShooter(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.bg = new ImageIcon(getClass().getResource("images/Background3.png")).getImage();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addMouseListener(this);

        //this.coin = GamePreferences.loadCoin();
        loadSelectedGun();
        updateCursor();
        
        balloons = new ArrayList<>();
        random = new Random();
        playerColor = COLOR_NAMES[random.nextInt(COLOR_NAMES.length)];
        score = 0;
        startTime = System.currentTimeMillis();
        
        timer = new Timer(30, this);
    }

    private void updateCursor() {
        String gunName = getGunName(selectedGun);
        String cursorPath = "images/" + gunName + "_Cursor.png";
        
        ImageIcon gunIcon = new ImageIcon(getClass().getResource(cursorPath));
        if (gunIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Cursor gunCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                gunIcon.getImage(), new Point(0, 0), gunName + " Cursor");
            setCursor(gunCursor);
        } else {
            System.out.println("Error: Unable to load " + cursorPath);
        }
    }
    
    private void loadSelectedGun() {
        String gunType = GamePreferences.getSelectedGun();
        
        switch (gunType) {
            case "MP5":
                selectedGun = new MP5();
                damageMultiplier = ((MP5)selectedGun).getDamages();
                break;
            case "AK47":
                selectedGun = new AK47();
                damageMultiplier = ((AK47)selectedGun).getDamages();
                break;
            case "Shotgun":
                selectedGun = new Shotgun();
                damageMultiplier = ((Shotgun)selectedGun).getDamages();
                break;
            case "M4":
                selectedGun = new M4();
                damageMultiplier = ((M4)selectedGun).getDamages();
                break;
            default:
                selectedGun = new Pistol();
                damageMultiplier = ((Pistol)selectedGun).getDamages();
                break;
        }
    }
    
    private String getGunName(Gun gun) {
        if (gun instanceof MP5) return "MP5";
        else if (gun instanceof AK47) return "AK47";
        else if (gun instanceof Shotgun) return "Shotgun";
        else if (gun instanceof M4) return "M4";
        else return "Pistol";
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);
        for (Balloon b : balloons) {
            b.draw(g);
        }
        g.setFont(new Font("Verdana", Font.PLAIN, 15));
        g.setColor(Color.BLACK);

        g.drawString("Level: " + level, 10, 20);
        g.drawString("Score: " + score, 10, 40);
        g.drawString("Time Left: " + (gameTime - getElapsedTime()) + "s", 10, 60);
        g.drawString("Gun Color: " + playerColor, 10, 80);
        g.drawString("Score Target: " + getLevelScore(), 10, 100);
        
        this.coin = GamePreferences.loadCoin();
        g.drawString("Coin: " + coin, 10, 120);
        g.drawString("Gun: " + getGunName(selectedGun) + " (Damage: " + (500+500*damageMultiplier) + ")", 10, 140);
        updateCursor();

        for (int i = 0; i < popups.size(); i++) {
            PopupScore popup = popups.get(i);
            popup.draw(g);
            if (popup.isExpired()) {
                popups.remove(i);
                i--;
            }
        }

        if (comboTimerPopup != null) {
            comboTimerPopup.draw(g);
            if (comboTimerPopup.isExpired()) {
                comboTimerPopup = null;
                comboMultiplier = 1.00;
                continuous_shot = false;
            }
        }
    }
    
    private int getElapsedTime() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (System.currentTimeMillis() - lastClickTime > COMBO_TIMEOUT) {
            comboMultiplier = 1.00;
            continuous_shot = false;
        }

        //System.out.println("Elapsed Time: " + getElapsedTime() + " Game Time: " + gameTime + " Score: " + score);
        if(score < 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!!!");
            Sound.stopBackgroundMusic();
            ResetToMenu();
            cardLayout.show(cardPanel, "StartMenu");
            Sound.playBackgroundMusic("C:\\Users\\ASUS\\Downloads\\GAME (3)\\GAME\\AllSound\\bgsong3.wav");
        }
        if (getElapsedTime() >= gameTime || score >= getLevelScore()) 
        {
            if (score >= getLevelScore()) 
            {
                
                if (level < 5) 
                {
                    JOptionPane.showMessageDialog(this, "Level " + level + " Completed! Final Score: " + score);
                    resetLevel();
                    level++;
                }

                else 
                {
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "You win! Final Score: " + score);
                    Sound.stopBackgroundMusic();
                    ResetToMenu();
                    cardLayout.show(cardPanel, "StartMenu");
                    Sound.playBackgroundMusic("C:\\\\Users\\\\ASUS\\\\Downloads\\\\GAME (3)\\\\GAME\\\\AllSound\\\\bgsong3.wav");
                }
                
            } 

            else 
            {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! You didn't meet the score requirement. Final Score: " + score);
                Sound.stopBackgroundMusic();
                ResetToMenu();
                cardLayout.show(cardPanel, "StartMenu");
            }
        }

        if (random.nextInt(RANDOM_SPAWN_SPEED) == 1) 
        {
            balloons.add(new Balloon(random.nextInt(WIDTH - 40), 0, COLOR_NAMES[random.nextInt(COLOR_NAMES.length)]));
        }
        
        for (int i = 0; i < balloons.size(); i++) 
        {
            balloons.get(i).y += BALLOON_SPEED;

            if (balloons.get(i).y > HEIGHT) 
            {
                balloons.remove(i);
                i--;
            }
        }
        repaint();
    }

    private int getLevelScore() {
        switch (level) {
            case 1: return 5000;
            case 2: return 15000;
            case 3: return 30000;
            case 4: return 60000;
            case 5: return 100000; // ผมทำไว้เยอะ ๆ แต่ปรับได้นะคับ
            default: return 0;
        }
    }

    private void resetLevel() {
        startTime = System.currentTimeMillis();
        gameTime -= 5;
        BALLOON_SPEED += 2;
        RANDOM_SPAWN_SPEED -= 3;
        score = 500; // From lvl2, initial score will be set as 500 to prevent instant GameOver.
        coin += 5;
        playerColor = COLOR_NAMES[random.nextInt(COLOR_NAMES.length)];
        GamePreferences.saveCoin(coin);
        balloons.clear();
    }

    public void ResetToMenu() {
        startTime = System.currentTimeMillis();
        level = 1;
        BALLOON_SPEED = 3;
        RANDOM_SPAWN_SPEED = 20;
        score = 0;
        GamePreferences.saveCoin(coin);
        balloons.clear();
    }

    public void startGame() {
        Sound.playBackgroundMusic("C:\\Users\\ASUS\\Downloads\\GAME (3)\\GAME\\AllSound\\bgsong2.wav");
        startTime = System.currentTimeMillis();
        gameTime = GAME_TIME;
        score = 0;
        balloons.clear();
        loadSelectedGun(); // Reload selected gun when starting
        timer.start();
        repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
        int mx = e.getX(), my = e.getY();
        boolean hit = false;

        if (System.currentTimeMillis() - lastClickTime > COMBO_TIMEOUT) {
            comboMultiplier = 1.00;
            continuous_shot = false;
            comboTimerPopup = null;
        }
        
        lastClickTime = System.currentTimeMillis();
        
        for (int i = 0; i < balloons.size(); i++) {
            Balloon b = balloons.get(i);
            if (new Rectangle(b.x, b.y, 90, 81).contains(mx, my)) //w: 72 h: 65
            {
                if (b.color.equals(playerColor)) 
                {   
                    damageBonus = (int)(baseScore * damageMultiplier); // Add damage multiplier to score
                    if (continuous_shot)
                        comboMultiplier += 0.01;
                    addScore = (baseScore + damageBonus) * comboMultiplier;
                    score += addScore;
                    continuous_shot = true;
                    popups.add(new PopupScore(mx, my, (int)addScore, Color.GREEN));
                    comboTimerPopup = new ComboTimerPopup(mx, my - 20, COMBO_TIMEOUT / 1000);

                    coin += 2;
                    GamePreferences.saveCoin(coin);
                    new Thread(() -> Sound.playSound("C:\\Users\\ASUS\\Downloads\\GAME (1)\\GAME\\AllSound\\pop.wav")).start();
                } 

                else 
                {
                    score -= 350;
                    continuous_shot = false;
                    comboMultiplier = 1.00;
                    popups.add(new PopupScore(mx, my, -350, Color.RED));
                    comboTimerPopup = null;
                    new Thread(() -> Sound.playSound("C:\\Users\\ASUS\\Downloads\\GAME (1)\\GAME\\AllSound\\bruh.wav")).start();
                }
                
                balloons.remove(i);
                hit = true;
                break;
            }
        }
        
        if (!hit) {
            score -= 175;
            continuous_shot = false;
            comboMultiplier = 1.00;
            popups.add(new PopupScore(mx, my, -175, Color.RED));
            comboTimerPopup = null;
            new Thread(() -> Sound.playSound("C:\\Users\\ASUS\\Downloads\\GAME (1)\\GAME\\AllSound\\bruh.wav")).start();
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
