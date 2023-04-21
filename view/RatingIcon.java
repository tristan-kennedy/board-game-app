package view;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RatingIcon implements Icon {
    private final int width;
    private final int height;
    private String rating;
    private final Color color;
    private static final Color GREEN = new Color(29, 128, 76);
    private static final Color BLUE = new Color(25, 120, 179);
    private static final Color RED = new Color(215, 25, 37);
    private static final Color GREY = new Color(102, 110, 117);

    public RatingIcon(float numRating, int width, int height) {
        this.width = width;
        this.height = height;

        rating = String.format("%.1f", numRating);
        if (numRating == 10)        rating = "10";
        if (numRating >= 8)         color = GREEN;
        else if (numRating >= 5)    color = BLUE;
        else if (numRating >= 1)    color = RED;
        else                      { color = GREY; rating = "N/A"; }
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(color);
        g2.drawRect(x, y, width, height);
        g2.fillRect(x, y, width, height);
        g2.setFont(c.getFont().deriveFont(Font.BOLD, (float) width/2 - 1));
        g2.setColor(Color.WHITE);
        Rectangle2D bounds = g2.getFont().getStringBounds(rating, g2.getFontRenderContext());

        int xOffset = (int) Math.round((width - bounds.getWidth()) / 2);
        int yOffset = (int) Math.round((height - bounds.getHeight()) / 2 - bounds.getY());
        g2.drawString(rating, x + xOffset, y + yOffset);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
