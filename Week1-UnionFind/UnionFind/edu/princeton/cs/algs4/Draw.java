// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Iterator;
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
import java.awt.Image;
import java.util.Map;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

public final class Draw implements ActionListener, MouseListener, MouseMotionListener, KeyListener
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
    private static final double BORDER = 0.0;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static final int DEFAULT_SIZE = 512;
    private static final double DEFAULT_PEN_RADIUS = 0.002;
    private static final Font DEFAULT_FONT;
    private Color penColor;
    private int width;
    private int height;
    private double penRadius;
    private boolean defer;
    private double xmin;
    private double ymin;
    private double xmax;
    private double ymax;
    private String name;
    private final Object mouseLock;
    private final Object keyLock;
    private Font font;
    private JLabel draw;
    private BufferedImage offscreenImage;
    private BufferedImage onscreenImage;
    private Graphics2D offscreen;
    private Graphics2D onscreen;
    private JFrame frame;
    private boolean isMousePressed;
    private double mouseX;
    private double mouseY;
    private final LinkedList<Character> keysTyped;
    private final TreeSet<Integer> keysDown;
    private final ArrayList<DrawListener> listeners;
    
    public Draw(final String name) {
        this.width = 512;
        this.height = 512;
        this.defer = false;
        this.name = "Draw";
        this.mouseLock = new Object();
        this.keyLock = new Object();
        this.frame = new JFrame();
        this.isMousePressed = false;
        this.mouseX = 0.0;
        this.mouseY = 0.0;
        this.keysTyped = new LinkedList<Character>();
        this.keysDown = new TreeSet<Integer>();
        this.listeners = new ArrayList<DrawListener>();
        this.name = name;
        this.init();
    }
    
    public Draw() {
        this.width = 512;
        this.height = 512;
        this.defer = false;
        this.name = "Draw";
        this.mouseLock = new Object();
        this.keyLock = new Object();
        this.frame = new JFrame();
        this.isMousePressed = false;
        this.mouseX = 0.0;
        this.mouseY = 0.0;
        this.keysTyped = new LinkedList<Character>();
        this.keysDown = new TreeSet<Integer>();
        this.listeners = new ArrayList<DrawListener>();
        this.init();
    }
    
    private void init() {
        if (this.frame != null) {
            this.frame.setVisible(false);
        }
        this.frame = new JFrame();
        this.offscreenImage = new BufferedImage(2 * this.width, 2 * this.height, 2);
        this.onscreenImage = new BufferedImage(2 * this.width, 2 * this.height, 2);
        this.offscreen = this.offscreenImage.createGraphics();
        this.onscreen = this.onscreenImage.createGraphics();
        this.offscreen.scale(2.0, 2.0);
        this.setXscale();
        this.setYscale();
        this.offscreen.setColor(Draw.DEFAULT_CLEAR_COLOR);
        this.offscreen.fillRect(0, 0, this.width, this.height);
        this.setPenColor();
        this.setPenRadius();
        this.setFont();
        this.clear();
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.offscreen.addRenderingHints(hints);
        final RetinaImageIcon icon = new RetinaImageIcon(this.onscreenImage);
        (this.draw = new JLabel(icon)).addMouseListener(this);
        this.draw.addMouseMotionListener(this);
        this.frame.setContentPane(this.draw);
        this.frame.addKeyListener(this);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(2);
        this.frame.setFocusTraversalKeysEnabled(false);
        this.frame.setTitle(this.name);
        this.frame.setJMenuBar(this.createMenuBar());
        this.frame.pack();
        this.frame.requestFocusInWindow();
        this.frame.setVisible(true);
    }
    
    public void setLocationOnScreen(final int x, final int y) {
        if (x <= 0 || y <= 0) {
            throw new IllegalArgumentException();
        }
        this.frame.setLocation(x, y);
    }
    
    public void setDefaultCloseOperation(final int value) {
        this.frame.setDefaultCloseOperation(value);
    }
    
    public void setCanvasSize(final int canvasWidth, final int canvasHeight) {
        if (canvasWidth < 1 || canvasHeight < 1) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        this.width = canvasWidth;
        this.height = canvasHeight;
        this.init();
    }
    
    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("File");
        menuBar.add(menu);
        final JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(this);
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
    
    public void setXscale() {
        this.setXscale(0.0, 1.0);
    }
    
    public void setYscale() {
        this.setYscale(0.0, 1.0);
    }
    
    public void setXscale(final double min, final double max) {
        validate(min, "min");
        validate(max, "max");
        final double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        this.xmin = min - 0.0 * size;
        this.xmax = max + 0.0 * size;
    }
    
    public void setYscale(final double min, final double max) {
        validate(min, "min");
        validate(max, "max");
        final double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        this.ymin = min - 0.0 * size;
        this.ymax = max + 0.0 * size;
    }
    
    private double scaleX(final double x) {
        return this.width * (x - this.xmin) / (this.xmax - this.xmin);
    }
    
    private double scaleY(final double y) {
        return this.height * (this.ymax - y) / (this.ymax - this.ymin);
    }
    
    private double factorX(final double w) {
        return w * this.width / Math.abs(this.xmax - this.xmin);
    }
    
    private double factorY(final double h) {
        return h * this.height / Math.abs(this.ymax - this.ymin);
    }
    
    private double userX(final double x) {
        return this.xmin + x * (this.xmax - this.xmin) / this.width;
    }
    
    private double userY(final double y) {
        return this.ymax - y * (this.ymax - this.ymin) / this.height;
    }
    
    public void clear() {
        this.clear(Draw.DEFAULT_CLEAR_COLOR);
    }
    
    public void clear(final Color color) {
        validateNotNull(color, "color");
        this.offscreen.setColor(color);
        this.offscreen.fillRect(0, 0, this.width, this.height);
        this.offscreen.setColor(this.penColor);
        this.draw();
    }
    
    public double getPenRadius() {
        return this.penRadius;
    }
    
    public void setPenRadius() {
        this.setPenRadius(0.002);
    }
    
    public void setPenRadius(final double radius) {
        validate(radius, "pen radius");
        validateNonnegative(radius, "pen radius");
        this.penRadius = radius * 512.0;
        final BasicStroke stroke = new BasicStroke((float)this.penRadius, 1, 1);
        this.offscreen.setStroke(stroke);
    }
    
    public Color getPenColor() {
        return this.penColor;
    }
    
    public void setPenColor() {
        this.setPenColor(Draw.DEFAULT_PEN_COLOR);
    }
    
    public void setPenColor(final Color color) {
        validateNotNull(color, "color");
        this.penColor = color;
        this.offscreen.setColor(this.penColor);
    }
    
    public void setPenColor(final int red, final int green, final int blue) {
        if (red < 0 || red >= 256) {
            throw new IllegalArgumentException("red must be between 0 and 255");
        }
        if (green < 0 || green >= 256) {
            throw new IllegalArgumentException("green must be between 0 and 255");
        }
        if (blue < 0 || blue >= 256) {
            throw new IllegalArgumentException("blue must be between 0 and 255");
        }
        this.setPenColor(new Color(red, green, blue));
    }
    
    public void xorOn() {
        this.offscreen.setXORMode(Draw.DEFAULT_CLEAR_COLOR);
    }
    
    public void xorOff() {
        this.offscreen.setPaintMode();
    }
    
    public JLabel getJLabel() {
        return this.draw;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public void setFont() {
        this.setFont(Draw.DEFAULT_FONT);
    }
    
    public void setFont(final Font font) {
        validateNotNull(font, "font");
        this.font = font;
    }
    
    public void line(final double x0, final double y0, final double x1, final double y1) {
        validate(x0, "x0");
        validate(y0, "y0");
        validate(x1, "x1");
        validate(y1, "y1");
        this.offscreen.draw(new Line2D.Double(this.scaleX(x0), this.scaleY(y0), this.scaleX(x1), this.scaleY(y1)));
        this.draw();
    }
    
    private void pixel(final double x, final double y) {
        validate(x, "x");
        validate(y, "y");
        this.offscreen.fillRect((int)Math.round(this.scaleX(x)), (int)Math.round(this.scaleY(y)), 1, 1);
    }
    
    public void point(final double x, final double y) {
        validate(x, "x");
        validate(y, "y");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double r = this.penRadius;
        if (r <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.fill(new Ellipse2D.Double(xs - r / 2.0, ys - r / 2.0, r, r));
        }
        this.draw();
    }
    
    public void circle(final double x, final double y, final double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * radius);
        final double hs = this.factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.draw(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void filledCircle(final double x, final double y, final double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * radius);
        final double hs = this.factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.fill(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void ellipse(final double x, final double y, final double semiMajorAxis, final double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * semiMajorAxis);
        final double hs = this.factorY(2.0 * semiMinorAxis);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.draw(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void filledEllipse(final double x, final double y, final double semiMajorAxis, final double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * semiMajorAxis);
        final double hs = this.factorY(2.0 * semiMinorAxis);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.fill(new Ellipse2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void arc(final double x, final double y, final double radius, final double angle1, double angle2) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "arc radius");
        validate(angle1, "angle1");
        validate(angle2, "angle2");
        validateNonnegative(radius, "arc radius");
        while (angle2 < angle1) {
            angle2 += 360.0;
        }
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * radius);
        final double hs = this.factorY(2.0 * radius);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.draw(new Arc2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs, angle1, angle2 - angle1, 0));
        }
        this.draw();
    }
    
    public void square(final double x, final double y, final double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * halfLength);
        final double hs = this.factorY(2.0 * halfLength);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.draw(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void filledSquare(final double x, final double y, final double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * halfLength);
        final double hs = this.factorY(2.0 * halfLength);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.fill(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void rectangle(final double x, final double y, final double halfWidth, final double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * halfWidth);
        final double hs = this.factorY(2.0 * halfHeight);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.draw(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void filledRectangle(final double x, final double y, final double halfWidth, final double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(2.0 * halfWidth);
        final double hs = this.factorY(2.0 * halfHeight);
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.fill(new Rectangle2D.Double(xs - ws / 2.0, ys - hs / 2.0, ws, hs));
        }
        this.draw();
    }
    
    public void polygon(final double[] x, final double[] y) {
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
        path.moveTo((float)this.scaleX(x[0]), (float)this.scaleY(y[0]));
        for (int j = 0; j < n3; ++j) {
            path.lineTo((float)this.scaleX(x[j]), (float)this.scaleY(y[j]));
        }
        path.closePath();
        this.offscreen.draw(path);
        this.draw();
    }
    
    public void filledPolygon(final double[] x, final double[] y) {
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
        path.moveTo((float)this.scaleX(x[0]), (float)this.scaleY(y[0]));
        for (int j = 0; j < n3; ++j) {
            path.lineTo((float)this.scaleX(x[j]), (float)this.scaleY(y[j]));
        }
        path.closePath();
        this.offscreen.fill(path);
        this.draw();
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
            final URL url = Draw.class.getResource("/" + filename);
            if (url == null) {
                throw new IllegalArgumentException("image " + filename + " not found");
            }
            icon = new ImageIcon(url);
        }
        return icon.getImage();
    }
    
    public void picture(final double x, final double y, final String filename) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(filename, "filename");
        final Image image = getImage(filename);
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final int ws = image.getWidth(null);
        final int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        this.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), null);
        this.draw();
    }
    
    public void picture(final double x, final double y, final String filename, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");
        final Image image = getImage(filename);
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final int ws = image.getWidth(null);
        final int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        this.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), null);
        this.offscreen.rotate(Math.toRadians(degrees), xs, ys);
        this.draw();
    }
    
    public void picture(final double x, final double y, final String filename, final double scaledWidth, final double scaledHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");
        final Image image = getImage(filename);
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(scaledWidth);
        final double hs = this.factorY(scaledHeight);
        if (ws < 0.0 || hs < 0.0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        else {
            this.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), (int)Math.round(ws), (int)Math.round(hs), null);
        }
        this.draw();
    }
    
    public void picture(final double x, final double y, final String filename, final double scaledWidth, final double scaledHeight, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");
        final Image image = getImage(filename);
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final double ws = this.factorX(scaledWidth);
        final double hs = this.factorY(scaledHeight);
        if (ws < 0.0 || hs < 0.0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1.0 && hs <= 1.0) {
            this.pixel(x, y);
        }
        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        this.offscreen.drawImage(image, (int)Math.round(xs - ws / 2.0), (int)Math.round(ys - hs / 2.0), (int)Math.round(ws), (int)Math.round(hs), null);
        this.offscreen.rotate(Math.toRadians(degrees), xs, ys);
        this.draw();
    }
    
    public void text(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        this.offscreen.setFont(this.font);
        final FontMetrics metrics = this.offscreen.getFontMetrics();
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final int ws = metrics.stringWidth(text);
        final int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float)(xs - ws / 2.0), (float)(ys + hs));
        this.draw();
    }
    
    public void text(final double x, final double y, final String text, final double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(text, "text");
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        this.text(x, y, text);
        this.offscreen.rotate(Math.toRadians(degrees), xs, ys);
    }
    
    public void textLeft(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        this.offscreen.setFont(this.font);
        final FontMetrics metrics = this.offscreen.getFontMetrics();
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float)xs, (float)(ys + hs));
        this.draw();
    }
    
    public void textRight(final double x, final double y, final String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");
        this.offscreen.setFont(this.font);
        final FontMetrics metrics = this.offscreen.getFontMetrics();
        final double xs = this.scaleX(x);
        final double ys = this.scaleY(y);
        final int ws = metrics.stringWidth(text);
        final int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float)(xs - ws), (float)(ys + hs));
        this.draw();
    }
    
    @Deprecated
    public void show(final int t) {
        this.show();
        this.pause(t);
        this.enableDoubleBuffering();
    }
    
    public void pause(final int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }
    
    public void show() {
        this.onscreen.drawImage(this.offscreenImage, 0, 0, null);
        this.frame.repaint();
    }
    
    private void draw() {
        if (!this.defer) {
            this.show();
        }
    }
    
    public void enableDoubleBuffering() {
        this.defer = true;
    }
    
    public void disableDoubleBuffering() {
        this.defer = false;
    }
    
    public void save(final String filename) {
        validateNotNull(filename, "filename");
        final File file = new File(filename);
        final String suffix = filename.substring(filename.lastIndexOf(46) + 1);
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(this.offscreenImage, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if ("jpg".equalsIgnoreCase(suffix)) {
            final WritableRaster raster = this.offscreenImage.getRaster();
            final WritableRaster newRaster = raster.createWritableChild(0, 0, this.width, this.height, 0, 0, new int[] { 0, 1, 2 });
            final DirectColorModel cm = (DirectColorModel)this.offscreenImage.getColorModel();
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
        final FileDialog chooser = new FileDialog(this.frame, "Use a .png or .jpg extension", 1);
        chooser.setVisible(true);
        final String filename = chooser.getFile();
        if (filename != null) {
            this.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }
    
    public void addListener(final DrawListener listener) {
        this.show();
        this.listeners.add(listener);
        this.frame.addKeyListener(this);
        this.frame.addMouseListener(this);
        this.frame.addMouseMotionListener(this);
        this.frame.setFocusable(true);
    }
    
    public boolean isMousePressed() {
        synchronized (this.mouseLock) {
            return this.isMousePressed;
        }
    }
    
    @Deprecated
    public boolean mousePressed() {
        synchronized (this.mouseLock) {
            return this.isMousePressed;
        }
    }
    
    public double mouseX() {
        synchronized (this.mouseLock) {
            return this.mouseX;
        }
    }
    
    public double mouseY() {
        synchronized (this.mouseLock) {
            return this.mouseY;
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = this.userX(e.getX());
            this.mouseY = this.userY(e.getY());
            this.isMousePressed = true;
        }
        if (e.getButton() == 1) {
            for (final DrawListener listener : this.listeners) {
                listener.mousePressed(this.userX(e.getX()), this.userY(e.getY()));
            }
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        synchronized (this.mouseLock) {
            this.isMousePressed = false;
        }
        if (e.getButton() == 1) {
            for (final DrawListener listener : this.listeners) {
                listener.mouseReleased(this.userX(e.getX()), this.userY(e.getY()));
            }
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getButton() == 1) {
            for (final DrawListener listener : this.listeners) {
                listener.mouseClicked(this.userX(e.getX()), this.userY(e.getY()));
            }
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = this.userX(e.getX());
            this.mouseY = this.userY(e.getY());
        }
        for (final DrawListener listener : this.listeners) {
            listener.mouseDragged(this.userX(e.getX()), this.userY(e.getY()));
        }
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = this.userX(e.getX());
            this.mouseY = this.userY(e.getY());
        }
    }
    
    public boolean hasNextKeyTyped() {
        synchronized (this.keyLock) {
            return !this.keysTyped.isEmpty();
        }
    }
    
    public char nextKeyTyped() {
        synchronized (this.keyLock) {
            return this.keysTyped.removeLast();
        }
    }
    
    public boolean isKeyPressed(final int keycode) {
        synchronized (this.keyLock) {
            return this.keysDown.contains(keycode);
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysTyped.addFirst(e.getKeyChar());
        }
        for (final DrawListener listener : this.listeners) {
            listener.keyTyped(e.getKeyChar());
        }
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysDown.add(e.getKeyCode());
        }
        for (final DrawListener listener : this.listeners) {
            listener.keyPressed(e.getKeyCode());
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysDown.remove(e.getKeyCode());
        }
        for (final DrawListener listener : this.listeners) {
            listener.keyReleased(e.getKeyCode());
        }
    }
    
    public static void main(final String[] args) {
        final Draw draw1 = new Draw("Test client 1");
        draw1.square(0.2, 0.8, 0.1);
        draw1.filledSquare(0.8, 0.8, 0.2);
        draw1.circle(0.8, 0.2, 0.2);
        draw1.setPenColor(Draw.MAGENTA);
        draw1.setPenRadius(0.02);
        draw1.arc(0.8, 0.2, 0.1, 200.0, 45.0);
        final Draw draw2 = new Draw("Test client 2");
        draw2.setCanvasSize(900, 200);
        draw2.setPenRadius();
        draw2.setPenColor(Draw.BLUE);
        final double[] x = { 0.1, 0.2, 0.3, 0.2 };
        final double[] y = { 0.2, 0.3, 0.2, 0.1 };
        draw2.filledPolygon(x, y);
        draw2.setPenColor(Draw.BLACK);
        draw2.text(0.2, 0.5, "bdfdfdfdlack text");
        draw2.setPenColor(Draw.WHITE);
        draw2.text(0.8, 0.8, "white text");
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
        DEFAULT_PEN_COLOR = Draw.BLACK;
        DEFAULT_CLEAR_COLOR = Draw.WHITE;
        DEFAULT_FONT = new Font("SansSerif", 0, 16);
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
