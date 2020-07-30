// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.NoSuchElementException;
import java.awt.event.MouseEvent;
import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.FontMetrics;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Container;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.Image;
import java.util.Map;
import java.awt.RenderingHints;
import java.util.TreeSet;
import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

public final class StdDraw implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
    public static final Color BLACK;
    public static final Color BLUE;
    public static final Color CYAN;
    public static final Color DARK_GRAY;
    public static final Color GRAY;
    public static final Color GREEN;
    public static final Color LIGHT_GRAY;
    public static final Color MAGENTA;
    public static final Color ORANGE;
    public static final Color PINK;
    public static final Color RED;
    public static final Color WHITE;
    public static final Color YELLOW;
    public static final Color BOOK_BLUE;
    public static final Color BOOK_LIGHT_BLUE;
    public static final Color BOOK_RED;
    public static final Color PRINCETON_ORANGE;
    private static final Color DEFAULT_PEN_COLOR;
    private static final Color DEFAULT_CLEAR_COLOR;
    private static Color penColor;
    private static final int DEFAULT_SIZE = 512;
    private static int width;
    private static int height;
    private static final double DEFAULT_PEN_RADIUS = 0.002;
    private static double penRadius;
    private static boolean defer;
    private static final double BORDER = 0.0;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static double xmin;
    private static double ymin;
    private static double xmax;
    private static double ymax;
    private static Object mouseLock;
    private static Object keyLock;
    private static final Font DEFAULT_FONT;
    private static Font font;
    private static BufferedImage offscreenImage;
    private static BufferedImage onscreenImage;
    private static Graphics2D offscreen;
    private static Graphics2D onscreen;
    private static StdDraw std;
    private static JFrame frame;
    private static boolean isMousePressed;
    private static double mouseX;
    private static double mouseY;
    private static LinkedList<Character> keysTyped;
    private static TreeSet<Integer> keysDown;
    
    private StdDraw() {
    }
    
    public static void setCanvasSize() {
        setCanvasSize(512, 512);
    }
    
    public static void setCanvasSize(final int canvasWidth, final int canvasHeight) {
        if (canvasWidth <= 0) {
            throw new IllegalArgumentException("width must be positive");
        }
        if (canvasHeight <= 0) {
            throw new IllegalArgumentException("height must be positive");
        }
        StdDraw.width = canvasWidth;
        StdDraw.height = canvasHeight;
        init();
    }
    
    private static void init() {
        if (StdDraw.frame != null) {
            StdDraw.frame.setVisible(false);
        }
        StdDraw.frame = new JFrame();
        StdDraw.offscreenImage = new BufferedImage(2 * StdDraw.width, 2 * StdDraw.height, 2);
        StdDraw.onscreenImage = new BufferedImage(2 * StdDraw.width, 2 * StdDraw.height, 2);
        StdDraw.offscreen = StdDraw.offscreenImage.createGraphics();
        StdDraw.onscreen = StdDraw.onscreenImage.createGraphics();
        StdDraw.offscreen.scale(2.0, 2.0);
        setXscale();
        setYscale();
        StdDraw.offscreen.setColor(StdDraw.DEFAULT_CLEAR_COLOR);
        StdDraw.offscreen.fillRect(0, 0, StdDraw.width, StdDraw.height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        StdDraw.offscreen.addRenderingHints(hints);
        final RetinaImageIcon icon = new RetinaImageIcon(StdDraw.onscreenImage);
        final JLabel draw = new JLabel(icon);
        draw.addMouseListener(StdDraw.std);
        draw.addMouseMotionListener(StdDraw.std);
        StdDraw.frame.setContentPane(draw);
        StdDraw.frame.addKeyListener(StdDraw.std);
        StdDraw.frame.setFocusTraversalKeysEnabled(false);
        StdDraw.frame.setResizable(false);
        StdDraw.frame.setDefaultCloseOperation(3);
        StdDraw.frame.setTitle("Standard Draw");
        StdDraw.frame.setJMenuBar(createMenuBar());
        StdDraw.frame.pack();
        StdDraw.frame.requestFocusInWindow();
        StdDraw.frame.setVisible(true);
    }
    
    private static JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("File");
        menuBar.add(menu);
        final JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(StdDraw.std);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }
    
    private static void validate(final double x, final String name) {
        if (Double.isNaN(x)) {
            throw new IllegalArgumentException(name + " is NaN");
        }
        if (Double.isInfinite(x)) {
            throw new IllegalArgumentException(name + " is infinite");
        }
    }
    
    private static void validateNonnegative(final double x, final String name) {
        if (x < 0.0) {
            throw new IllegalArgumentException(name + " negative");
        }
    }
    
    private static void validateNotNull(final Object x, final String name) {
        if (x == null) {
            throw new IllegalArgumentException(name + " is null");
        }
    }
    
    public static void setXscale() {
        setXscale(0.0, 1.0);
    }
    
    public static void setYscale() {
        setYscale(0.0, 1.0);
    }
    
    public static void setScale() {
        setXscale();
        setYscale();
    }
    
    public static void setXscale(final double min, final double max) {
        validate(min, "min");
        validate(max, "max");
        final double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (StdDraw.mouseLock) {
            StdDraw.xmin = min - 0.0 * size;
            StdDraw.xmax = max + 0.0 * size;
        }
    }
    
    public static void setYscale(final double min, final double max) {
        validate(min, "min");
        validate(max, "max");
        final double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (StdDraw.mouseLock) {
            StdDraw.ymin = min - 0.0 * size;
            StdDraw.ymax = max + 0.0 * size;
        }
    }
    
    public static void setScale(final double min, final double max) {
        validate(min, "min");
        validate(max, "max");
        final double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (StdDraw.mouseLock) {
            StdDraw.xmin = min - 0.0 * size;
            StdDraw.xmax = max + 0.0 * size;
            StdDraw.ymin = min - 0.0 * size;
            StdDraw.ymax = max + 0.0 * size;
        }
    }
    
    private static double scaleX(final double x) {
        return StdDraw.width * (x - StdDraw.xmin) / (StdDraw.xmax - StdDraw.xmin);
    }
    
    private static double scaleY(final double y) {
        return StdDraw.height * (StdDraw.ymax - y) / (StdDraw.ymax - StdDraw.ymin);
    }
    
    private static double factorX(final double w) {
        return w * StdDraw.width / Math.abs(StdDraw.xmax - StdDraw.xmin);
    }
    
    private static double factorY(final double h) {
        return h * StdDraw.height / Math.abs(StdDraw.ymax - StdDraw.ymin);
    }
    
    private static double userX(final double x) {
        return StdDraw.xmin + x * (StdDraw.xmax - StdDraw.xmin) / StdDraw.width;
    }
    
    private static double userY(final double y) {
        return StdDraw.ymax - y * (StdDraw.ymax - StdDraw.ymin) / StdDraw.height;
    }
    
    public static void clear() {
        clear(StdDraw.DEFAULT_CLEAR_COLOR);
    }
    
    public static void clear(final Color color) {
        validateNotNull(color, "color");
        StdDraw.offscreen.setColor(color);
        StdDraw.offscreen.fillRect(0, 0, StdDraw.width, StdDraw.height);
        StdDraw.offscreen.setColor(StdDraw.penColor);
        draw();
    }
    
    public static double getPenRadius() {
        return StdDraw.penRadius;
    }
    
    public static void setPenRadius() {
        setPenRadius(0.002);
    }
    
    public static void setPenRadius(final double radius) {
        validate(radius, "pen radius");
        validateNonnegative(radius, "pen radius");
        StdDraw.penRadius = radius;
        final float scaledPenRadius = (float)(radius * 512.0);
        final BasicStroke stroke = new BasicStroke(scaledPenRadius, 1, 1);
        StdDraw.offscreen.setStroke(stroke);
    }
    
    public static Color getPenColor() {
        return StdDraw.penColor;
    }
    
    public static void setPenColor() {
        setPenColor(StdDraw.DEFAULT_PEN_COLOR);
    }
    
    public static void setPenColor(final Color color) {
        validateNotNull(color, "color");
        StdDraw.penColor = color;
        StdDraw.offscreen.setColor(StdDraw.penColor);
    }
    
    public static void setPenColor(final int red, final int green, final int blue) {
        if (red < 0 || red >= 256) {
            throw new IllegalArgumentException("red must be between 0 and 255");
        }
        if (green < 0 || green >= 256) {
            throw new IllegalArgumentException("green must be between 0 and 255");
        }
        if (blue < 0 || blue >= 256) {
            throw new IllegalArgumentException("blue must be between 0 and 255");
        }
        setPenColor(new Color(red, green, blue));
    }
    
    public static Font getFont() {
        return StdDraw.font;
    }
    
    public static void setFont() {
        setFont(StdDraw.DEFAULT_FONT);
    }
    
    public static void setFont(final Font font) {
        validateNotNull(font, "font");
        StdDraw.font = font;
    }
    
    public static void line(final double x0, final double y0, final double x1, final double y1) {
        validate(x0, "x0");
        validate(y0, "y0");
        validate(x1, "x1");
        validate(y1, "y1");
        StdDraw.offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }
    
    private static void pixel(final double x, final double y) {
        validate(x, "x");
        validate(y, "y");
        StdDraw.offscreen.fillRect((int)Math.round(scaleX(x)), (int)Math.round(scaleY(y)), 1, 1);
    }
    
    public static void point(final double x, final double y) {
        validate(x, "x");
        validate(y, "y");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double r = StdDraw.penRadius;
        final float scaledPenRadius = (float)(r * 512.0);
        if (scaledPenRadius <= 1.0f) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.fill(new Ellipse2D.Double(xs - scaledPenRadius / 2.0f, ys - scaledPenRadius / 2.0f, scaledPenRadius, scaledPenRadius));
        }
        draw();
    }
    
    public static void circle(final double x, final double y, final double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * radius);
        final double hs = factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.draw(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void filledCircle(final double x, final double y, final double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * radius);
        final double hs = factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.fill(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void ellipse(final double x, final double y, final double semiMajorAxis, final double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * semiMajorAxis);
        final double hs = factorY(2.0 * semiMinorAxis);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.draw(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void filledEllipse(final double x, final double y, final double semiMajorAxis, final double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * semiMajorAxis);
        final double hs = factorY(2.0 * semiMinorAxis);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.fill(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void arc(final double x, final double y, final double radius, final double angle1, double angle2) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "arc radius");
        validate(angle1, "angle1");
        validate(angle2, "angle2");
        validateNonnegative(radius, "arc radius");
        while (angle2 < angle1) {
            angle2 += 360.0;
        }
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * radius);
        final double hs = factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.draw(new Arc2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs, angle1, angle2 - angle1, 0));
        }
        draw();
    }
    
    public static void square(final double x, final double y, final double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * halfLength);
        final double hs = factorY(2.0 * halfLength);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.draw(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void filledSquare(final double x, final double y, final double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * halfLength);
        final double hs = factorY(2.0 * halfLength);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.fill(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void rectangle(final double x, final double y, final double halfWidth, final double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * halfWidth);
        final double hs = factorY(2.0 * halfHeight);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.draw(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void filledRectangle(final double x, final double y, final double halfWidth, final double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(2.0 * halfWidth);
        final double hs = factorY(2.0 * halfHeight);
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.fill(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        draw();
    }
    
    public static void polygon(final double[] x, final double[] y) {
        validateNotNull(x, "x-coordinate array");
        validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; ++i) {
            validate(x[i], "x[" + i + "]");
        }
        for (int i = 0; i < y.length; ++i) {
            validate(y[i], "y[" + i + "]");
        }
        final int n1 = x.length;
        final int n2 = y.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        final int n3 = n1;
        if (n3 == 0) {
            return;
        }
        final GeneralPath path = new GeneralPath();
        path.moveTo((float)scaleX(x[0]), (float)scaleY(y[0]));
        for (int j = 0; j < n3; ++j) {
            path.lineTo((float)scaleX(x[j]), (float)scaleY(y[j]));
        }
        path.closePath();
        StdDraw.offscreen.draw(path);
        draw();
    }
    
    public static void filledPolygon(final double[] x, final double[] y) {
        validateNotNull(x, "x-coordinate array");
        validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; ++i) {
            validate(x[i], "x[" + i + "]");
        }
        for (int i = 0; i < y.length; ++i) {
            validate(y[i], "y[" + i + "]");
        }
        final int n1 = x.length;
        final int n2 = y.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        final int n3 = n1;
        if (n3 == 0) {
            return;
        }
        final GeneralPath path = new GeneralPath();
        path.moveTo((float)scaleX(x[0]), (float)scaleY(y[0]));
        for (int j = 0; j < n3; ++j) {
            path.lineTo((float)scaleX(x[j]), (float)scaleY(y[j]));
        }
        path.closePath();
        StdDraw.offscreen.fill(path);
        draw();
    }
    
    private static Image getImage(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        ImageIcon icon = new ImageIcon(filename);
        Label_0056: {
            if (icon != null) {
                if (icon.getImageLoadStatus() == 8) {
                    break Label_0056;
                }
            }
            try {
                final URL url = new URL(filename);
                icon = new ImageIcon(url);
            }
            catch (MalformedURLException ex) {}
        }
        if (icon == null || icon.getImageLoadStatus() != 8) {
            final URL url = StdDraw.class.getResource(filename);
            if (url != null) {
                icon = new ImageIcon(url);
            }
        }
        if (icon == null || icon.getImageLoadStatus() != 8) {
            final URL url = StdDraw.class.getResource("/" + filename);
            if (url == null) {
                throw new IllegalArgumentException("image " + filename + " not found");
            }
            icon = new ImageIcon(url);
        }
        return icon.getImage();
    }
    
    public static void picture(final double x, final double y, final String filename) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(filename, "filename");
        final Image image = getImage(filename);
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final int ws = image.getWidth(null);
        final int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        StdDraw.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), null);
        draw();
    }
    
    public static void picture(final double x, final double y, final String filename, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");
        final Image image = getImage(filename);
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final int ws = image.getWidth(null);
        final int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        StdDraw.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        StdDraw.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), null);
        StdDraw.offscreen.rotate(Math.toRadians(degrees), xs, ys);
        draw();
    }
    
    public static void picture(final double x, final double y, final String filename, final double scaledWidth, final double scaledHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");
        final Image image = getImage(filename);
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(scaledWidth);
        final double hs = factorY(scaledHeight);
        if (ws < 0.0 || hs < 0.0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        else {
            StdDraw.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), (int)Math.round(ws), (int)Math.round(hs), null);
        }
        draw();
    }
    
    public static void picture(final double x, final double y, final String filename, final double scaledWidth, final double scaledHeight, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");
        final Image image = getImage(filename);
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final double ws = factorX(scaledWidth);
        final double hs = factorY(scaledHeight);
        if (ws < 0.0 || hs < 0.0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1.0 && hs <= 1.0) {
            pixel(x, y);
        }
        StdDraw.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        StdDraw.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), (int)Math.round(ws), (int)Math.round(hs), null);
        StdDraw.offscreen.rotate(Math.toRadians(degrees), xs, ys);
        draw();
    }
    
    public static void text(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        StdDraw.offscreen.setFont(StdDraw.font);
        final FontMetrics metrics = StdDraw.offscreen.getFontMetrics();
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final int ws = metrics.stringWidth(text);
        final int hs = metrics.getDescent();
        StdDraw.offscreen.drawString(text, (float)(xs - ws / 2.0), (float)(ys + hs));
        draw();
    }
    
    public static void text(final double x, final double y, final String text, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(text, "text");
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        StdDraw.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, text);
        StdDraw.offscreen.rotate(Math.toRadians(degrees), xs, ys);
    }
    
    public static void textLeft(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        StdDraw.offscreen.setFont(StdDraw.font);
        final FontMetrics metrics = StdDraw.offscreen.getFontMetrics();
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final int hs = metrics.getDescent();
        StdDraw.offscreen.drawString(text, (float)xs, (float)(ys + hs));
        draw();
    }
    
    public static void textRight(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        StdDraw.offscreen.setFont(StdDraw.font);
        final FontMetrics metrics = StdDraw.offscreen.getFontMetrics();
        final double xs = scaleX(x);
        final double ys = scaleY(y);
        final int ws = metrics.stringWidth(text);
        final int hs = metrics.getDescent();
        StdDraw.offscreen.drawString(text, (float)(xs - ws), (float)(ys + hs));
        draw();
    }
    
    @Deprecated
    public static void show(final int t) {
        validateNonnegative(t, "t");
        show();
        pause(t);
        enableDoubleBuffering();
    }
    
    public static void pause(final int t) {
        validateNonnegative(t, "t");
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }
    
    public static void show() {
        StdDraw.onscreen.drawImage(StdDraw.offscreenImage, 0, 0, null);
        StdDraw.frame.repaint();
    }
    
    private static void draw() {
        if (!StdDraw.defer) {
            show();
        }
    }
    
    public static void enableDoubleBuffering() {
        StdDraw.defer = true;
    }
    
    public static void disableDoubleBuffering() {
        StdDraw.defer = false;
    }
    
    public static void save(final String filename) {
        validateNotNull(filename, "filename");
        final File file = new File(filename);
        final String suffix = filename.substring(filename.lastIndexOf(46) + 1);
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(StdDraw.onscreenImage, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if ("jpg".equalsIgnoreCase(suffix)) {
            final WritableRaster raster = StdDraw.onscreenImage.getRaster();
            final WritableRaster newRaster = raster.createWritableChild(0, 0, StdDraw.width, StdDraw.height, 0, 0, new int[] { 0, 1, 2 });
            final DirectColorModel cm = (DirectColorModel)StdDraw.onscreenImage.getColorModel();
            final DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(), cm.getRedMask(), cm.getGreenMask(), cm.getBlueMask());
            final BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final FileDialog chooser = new FileDialog(StdDraw.frame, "Use a .png or .jpg extension", 1);
        chooser.setVisible(true);
        final String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }
    
    public static boolean isMousePressed() {
        synchronized (StdDraw.mouseLock) {
            return StdDraw.isMousePressed;
        }
    }
    
    @Deprecated
    public static boolean mousePressed() {
        synchronized (StdDraw.mouseLock) {
            return StdDraw.isMousePressed;
        }
    }
    
    public static double mouseX() {
        synchronized (StdDraw.mouseLock) {
            return StdDraw.mouseX;
        }
    }
    
    public static double mouseY() {
        synchronized (StdDraw.mouseLock) {
            return StdDraw.mouseY;
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        synchronized (StdDraw.mouseLock) {
            StdDraw.mouseX = userX(e.getX());
            StdDraw.mouseY = userY(e.getY());
            StdDraw.isMousePressed = true;
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        synchronized (StdDraw.mouseLock) {
            StdDraw.isMousePressed = false;
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
        synchronized (StdDraw.mouseLock) {
            StdDraw.mouseX = userX(e.getX());
            StdDraw.mouseY = userY(e.getY());
        }
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        synchronized (StdDraw.mouseLock) {
            StdDraw.mouseX = userX(e.getX());
            StdDraw.mouseY = userY(e.getY());
        }
    }
    
    public static boolean hasNextKeyTyped() {
        synchronized (StdDraw.keyLock) {
            return !StdDraw.keysTyped.isEmpty();
        }
    }
    
    public static char nextKeyTyped() {
        synchronized (StdDraw.keyLock) {
            if (StdDraw.keysTyped.isEmpty()) {
                throw new NoSuchElementException("your program has already processed all keystrokes");
            }
            return StdDraw.keysTyped.remove(StdDraw.keysTyped.size() - 1);
        }
    }
    
    public static boolean isKeyPressed(final int keycode) {
        synchronized (StdDraw.keyLock) {
            return StdDraw.keysDown.contains(keycode);
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
        synchronized (StdDraw.keyLock) {
            StdDraw.keysTyped.addFirst(e.getKeyChar());
        }
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        synchronized (StdDraw.keyLock) {
            StdDraw.keysDown.add(e.getKeyCode());
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        synchronized (StdDraw.keyLock) {
            StdDraw.keysDown.remove(e.getKeyCode());
        }
    }
    
    public static void main(final String[] args) {
        square(0.2, 0.8, 0.1);
        filledSquare(0.8, 0.8, 0.2);
        circle(0.8, 0.2, 0.2);
        setPenColor(StdDraw.BOOK_RED);
        setPenRadius(0.02);
        arc(0.8, 0.2, 0.1, 200.0, 45.0);
        setPenRadius();
        setPenColor(StdDraw.BOOK_BLUE);
        final double[] x = { 0.1, 0.2, 0.3, 0.2 };
        final double[] y = { 0.2, 0.3, 0.2, 0.1 };
        filledPolygon(x, y);
        setPenColor(StdDraw.BLACK);
        text(0.2, 0.5, "black text");
        setPenColor(StdDraw.WHITE);
        text(0.8, 0.8, "white text");
    }
    
    static {
        BLACK = Color.BLACK;
        BLUE = Color.BLUE;
        CYAN = Color.CYAN;
        DARK_GRAY = Color.DARK_GRAY;
        GRAY = Color.GRAY;
        GREEN = Color.GREEN;
        LIGHT_GRAY = Color.LIGHT_GRAY;
        MAGENTA = Color.MAGENTA;
        ORANGE = Color.ORANGE;
        PINK = Color.PINK;
        RED = Color.RED;
        WHITE = Color.WHITE;
        YELLOW = Color.YELLOW;
        BOOK_BLUE = new Color(9, 90, 166);
        BOOK_LIGHT_BLUE = new Color(103, 198, 243);
        BOOK_RED = new Color(150, 35, 31);
        PRINCETON_ORANGE = new Color(245, 128, 37);
        DEFAULT_PEN_COLOR = StdDraw.BLACK;
        DEFAULT_CLEAR_COLOR = StdDraw.WHITE;
        StdDraw.width = 512;
        StdDraw.height = 512;
        StdDraw.defer = false;
        StdDraw.mouseLock = new Object();
        StdDraw.keyLock = new Object();
        DEFAULT_FONT = new Font("SansSerif", 0, 16);
        StdDraw.std = new StdDraw();
        StdDraw.isMousePressed = false;
        StdDraw.mouseX = 0.0;
        StdDraw.mouseY = 0.0;
        StdDraw.keysTyped = new LinkedList<Character>();
        StdDraw.keysDown = new TreeSet<Integer>();
        init();
    }
    
    private static class RetinaImageIcon extends ImageIcon
    {
        public RetinaImageIcon(final Image image) {
            super(image);
        }
        
        @Override
        public int getIconWidth() {
            return super.getIconWidth() / 2;
        }
        
        @Override
        public int getIconHeight() {
            return super.getIconHeight() / 2;
        }
        
        @Override
        public synchronized void paintIcon(final Component c, final Graphics g, final int x, final int y) {
            final Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.scale(0.5, 0.5);
            super.paintIcon(c, g2, x * 2, y * 2);
            g2.dispose();
        }
    }
}
