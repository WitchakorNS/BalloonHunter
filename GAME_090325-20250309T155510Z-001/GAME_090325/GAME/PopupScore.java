import java.awt.*;

class PopupScore {
    int x, y;
    int score;
    long startTime;
    Color color;

    public PopupScore(int x, int y, int score, Color color) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.color = color;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > 1000;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("+" + score, x, y);
        y -= 1;
    }
}