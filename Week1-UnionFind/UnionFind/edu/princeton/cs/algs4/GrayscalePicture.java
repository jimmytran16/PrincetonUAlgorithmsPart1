// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.image.RenderedImage;
import java.awt.Container;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.Icon;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public final class GrayscalePicture implements ActionListener
{
    private BufferedImage image;
    private JFrame frame;
    private String filename;
    private boolean isOriginUpperLeft;
    private final int width;
    private final int height;
    
    public GrayscalePicture(final int width, final int height) {
        this.isOriginUpperLeft = true;
        if (width < 0) {
            throw new IllegalArgumentException("width must be non-negative");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must be non-negative");
        }
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, 1);
    }
    
    public GrayscalePicture(final GrayscalePicture picture) {
        this.isOriginUpperLeft = true;
        if (picture == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        this.width = picture.width();
        this.height = picture.height();
        this.image = new BufferedImage(this.width, this.height, 1);
        this.filename = picture.filename;
        this.isOriginUpperLeft = picture.isOriginUpperLeft;
        for (int col = 0; col < this.width(); ++col) {
            for (int row = 0; row < this.height(); ++row) {
                this.image.setRGB(col, row, picture.image.getRGB(col, row));
            }
        }
    }
    
    public GrayscalePicture(final String name) {
        this.isOriginUpperLeft = true;
        if (name == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        this.filename = name;
        try {
            final File file = new File(name);
            if (file.isFile()) {
                this.image = ImageIO.read(file);
            }
            else {
                URL url = this.getClass().getResource(name);
                if (url == null) {
                    url = this.getClass().getClassLoader().getResource(name);
                }
                if (url == null) {
                    url = new URL(name);
                }
                this.image = ImageIO.read(url);
            }
            if (this.image == null) {
                throw new IllegalArgumentException("could not read image: " + name);
            }
            this.width = this.image.getWidth(null);
            this.height = this.image.getHeight(null);
            for (int col = 0; col < this.width; ++col) {
                for (int row = 0; row < this.height; ++row) {
                    final Color color = new Color(this.image.getRGB(col, row));
                    final Color gray = toGray(color);
                    this.image.setRGB(col, row, gray.getRGB());
                }
            }
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("could not open image: " + name, ioe);
        }
    }
    
    private static Color toGray(final Color color) {
        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final int y = (int)Math.round(0.299 * r + 0.587 * g + 0.114 * b);
        return new Color(y, y, y);
    }
    
    public JLabel getJLabel() {
        if (this.image == null) {
            return null;
        }
        final ImageIcon icon = new ImageIcon(this.image);
        return new JLabel(icon);
    }
    
    public void setOriginUpperLeft() {
        this.isOriginUpperLeft = true;
    }
    
    public void setOriginLowerLeft() {
        this.isOriginUpperLeft = false;
    }
    
    public void show() {
        if (this.frame == null) {
            this.frame = new JFrame();
            final JMenuBar menuBar = new JMenuBar();
            final JMenu menu = new JMenu("File");
            menuBar.add(menu);
            final JMenuItem menuItem1 = new JMenuItem(" Save...   ");
            menuItem1.addActionListener(this);
            menuItem1.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem1);
            this.frame.setJMenuBar(menuBar);
            this.frame.setContentPane(this.getJLabel());
            this.frame.setDefaultCloseOperation(2);
            if (this.filename == null) {
                this.frame.setTitle(this.width + "-by-" + this.height);
            }
            else {
                this.frame.setTitle(this.filename);
            }
            this.frame.setResizable(false);
            this.frame.pack();
            this.frame.setVisible(true);
        }
        this.frame.repaint();
    }
    
    public int height() {
        return this.height;
    }
    
    public int width() {
        return this.width;
    }
    
    private void validateRowIndex(final int row) {
        if (row < 0 || row >= this.height()) {
            throw new IllegalArgumentException("row index must be between 0 and " + (this.height() - 1) + ": " + row);
        }
    }
    
    private void validateColumnIndex(final int col) {
        if (col < 0 || col >= this.width()) {
            throw new IllegalArgumentException("column index must be between 0 and " + (this.width() - 1) + ": " + col);
        }
    }
    
    private void validateGrayscaleValue(final int gray) {
        if (gray < 0 || gray >= 256) {
            throw new IllegalArgumentException("grayscale value must be between 0 and 255");
        }
    }
    
    public Color get(final int col, final int row) {
        this.validateColumnIndex(col);
        this.validateRowIndex(row);
        final Color color = new Color(this.image.getRGB(col, row));
        return toGray(color);
    }
    
    public int getGrayscale(final int col, final int row) {
        this.validateColumnIndex(col);
        this.validateRowIndex(row);
        if (this.isOriginUpperLeft) {
            return this.image.getRGB(col, row) & 0xFF;
        }
        return this.image.getRGB(col, this.height - row - 1) & 0xFF;
    }
    
    public void set(final int col, final int row, final Color color) {
        this.validateColumnIndex(col);
        this.validateRowIndex(row);
        if (color == null) {
            throw new IllegalArgumentException("color argument is null");
        }
        final Color gray = toGray(color);
        this.image.setRGB(col, row, gray.getRGB());
    }
    
    public void setGrayscale(final int col, final int row, final int gray) {
        this.validateColumnIndex(col);
        this.validateRowIndex(row);
        this.validateGrayscaleValue(gray);
        final int rgb = gray | gray << 8 | gray << 16;
        if (this.isOriginUpperLeft) {
            this.image.setRGB(col, row, rgb);
        }
        else {
            this.image.setRGB(col, this.height - row - 1, rgb);
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        final GrayscalePicture that = (GrayscalePicture)other;
        if (this.width() != that.width()) {
            return false;
        }
        if (this.height() != that.height()) {
            return false;
        }
        for (int col = 0; col < this.width(); ++col) {
            for (int row = 0; row < this.height(); ++row) {
                if (this.getGrayscale(col, row) != that.getGrayscale(col, row)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.width + "-by-" + this.height + " grayscale picture (grayscale values given in hex)\n");
        for (int row = 0; row < this.height; ++row) {
            for (int col = 0; col < this.width; ++col) {
                int gray = 0;
                if (this.isOriginUpperLeft) {
                    gray = (0xFF & this.image.getRGB(col, row));
                }
                else {
                    gray = (0xFF & this.image.getRGB(col, this.height - row - 1));
                }
                sb.append(String.format("%3d ", gray));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }
    
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }
    
    public void save(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument to save() is null");
        }
        this.save(new File(name));
        this.filename = name;
    }
    
    public void save(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("argument to save() is null");
        }
        this.filename = file.getName();
        if (this.frame != null) {
            this.frame.setTitle(this.filename);
        }
        final String suffix = this.filename.substring(this.filename.lastIndexOf(46) + 1);
        if (!"jpg".equalsIgnoreCase(suffix)) {
            if (!"png".equalsIgnoreCase(suffix)) {
                System.out.println("Error: filename must end in .jpg or .png");
                return;
            }
        }
        try {
            ImageIO.write(this.image, suffix, file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final FileDialog chooser = new FileDialog(this.frame, "Use a .png or .jpg extension", 1);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            this.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }
    
    public static void main(final String[] args) {
        final GrayscalePicture picture = new GrayscalePicture(args[0]);
        StdOut.printf("%d-by-%d\n", picture.width(), picture.height());
        final GrayscalePicture copy = new GrayscalePicture(picture);
        picture.show();
        copy.show();
        while (!StdIn.isEmpty()) {
            final int row = StdIn.readInt();
            final int col = StdIn.readInt();
            final int gray = StdIn.readInt();
            picture.setGrayscale(row, col, gray);
            StdOut.println(picture.get(row, col));
            StdOut.println(picture.getGrayscale(row, col));
        }
    }
}
