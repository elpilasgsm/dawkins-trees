package org.sfedu.sockets.utisl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Zaporozhets on 08.11.2019.
 */
public class DrawTree {


    public static BufferedImage paint(int w,
                                      double width,
                                      double l,
                                      double minL,
                                      double lengthFactor,
                                      double widthFactor,
                                      double angleDelta,
                                      int maxLevel) {
        BufferedImage image = new BufferedImage(w, w,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, w);
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Point2D start = new Point2D.Double(
                w * 0.5,
                w * 0.8);
        g.setColor(Color.BLACK);
        drawTree(g, start, l, minL, width, 0, lengthFactor, widthFactor, angleDelta, maxLevel, 0);
        return image;
    }


    private static void drawTree(Graphics2D g,
                                 Point2D start, double length, double minLength,
                                 double width, double alpha,
                                 double lengthFactor,
                                 double widthFactor,
                                 double angleDelta,
                                 int maxLevel, int currLevel) {
        currLevel++;
        if (length < minLength || currLevel > maxLevel) {
            return;
        }
        g.setStroke(new BasicStroke((float) width,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Point2D end = new Point2D.Double(
                start.getX() + Math.sin(alpha + Math.PI) * length,
                start.getY() + Math.cos(alpha + Math.PI) * length);
        g.draw(new Line2D.Double(start, end));
        drawTree(g, end, length * lengthFactor, minLength,
                width * widthFactor, alpha + angleDelta, lengthFactor, widthFactor, angleDelta, maxLevel, currLevel);
        drawTree(g, end, length * lengthFactor, minLength,
                width * widthFactor, alpha - angleDelta, lengthFactor, widthFactor, angleDelta, maxLevel, currLevel);
    }


}
