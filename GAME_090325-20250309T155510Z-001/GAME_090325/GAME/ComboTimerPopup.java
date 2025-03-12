import java.awt.*;

class ComboTimerPopup {
    int x, y;
    long startTime;
    int timeout;
    
    public ComboTimerPopup(int x, int y, int timeout) {
        this.x = x;
        this.y = y;
        this.timeout = timeout;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return getRemainingTime() <= 0;
    }

    public int getRemainingTime() {
        return timeout - (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Combo Time: " + getRemainingTime() + "s", x, y);
    }
}