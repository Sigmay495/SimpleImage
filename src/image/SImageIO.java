package image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SImageIO {

    /**
     * BufferedImageを用いて画像読み込みをする。
     *
     * @param fileName ファイル名
     * @param channel  チャネル数
     * @return 読み込んだSImage
     */
    private static SImage readBufferedImage(String fileName, int channel) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new ImageIOException(e.getMessage());
        }
        var image = new SImage(bi.getWidth(), bi.getHeight(), channel);
        switch (channel) {
            case 1:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++)
                        image.setInt(x, y, bi.getRGB(x, y) & 0xff);
                break;
            case 3:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++) {
                        int pixel = bi.getRGB(x, y);
                        int red = pixel >> 16 & 0xff;
                        int green = pixel >> 8 & 0xff;
                        int blue = pixel & 0xff;
                        image.setRGB(x, y, new int[]{red, green, blue});
                    }
                break;
            case 4:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++) {
                        int pixel = bi.getRGB(x, y);
                        int alpha = pixel >> 24 & 0xff;
                        int red = pixel >> 16 & 0xff;
                        int green = pixel >> 8 & 0xff;
                        int blue = pixel & 0xff;
                        image.setRGB(x, y, new int[]{alpha, red, green, blue});
                    }
                break;
        }
        return image;
    }

    /**
     * BufferedImageへと型を変換する。
     *
     * @param image 変換前のSImage
     * @return 変換後のBufferedImage
     */
    private static BufferedImage toBufferedImage(SImage image) {
        var bi = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
        switch (image.channel) {
            case 1:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++) {
                        int value = image.getInt(x, y);
                        bi.setRGB(x, y, 255 << 24 | value << 16 | value << 8 | value);
                    }
                break;
            case 3:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++) {
                        int[] rgb = image.getRGB(x, y);
                        bi.setRGB(x, y, 255 << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
                    }
                break;
            case 4:
                for (int y = 0; y < image.height(); y++)
                    for (int x = 0; x < image.width(); x++) {
                        int[] argb = image.getARGB(x, y);
                        bi.setRGB(x, y, argb[0] << 24 | argb[1] << 16 | argb[2] << 8 | argb[3]);
                    }
                break;
        }
        return bi;
    }

    /**
     * BufferedImageを用いて画像書き出しをする。
     *
     * @param image    書き出すSImage
     * @param fileName ファイル名
     */
    private static void writeBufferedImage(SImage image, String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            ImageIO.write(toBufferedImage(image), extension, new File(fileName));
        } catch (IOException e) {
            throw new ImageIOException(e.getMessage());
        }
    }

    /**
     * 画像を読み込む。
     *
     * @param fileName ファイル名
     * @param channel  チャネル数
     * @return 読み込んだSImage
     */
    public static SImage read(String fileName, int channel) {
        if (channel == 1 || channel == 3 || channel == 4)
            throw new ImageIOException("チャネル数は1または3、4でなければなりません: " + channel);
        return readBufferedImage(fileName, channel);
    }

    /**
     * カラー画像を読み込む。
     *
     * @param fileName ファイル名
     * @return 読み込んだSImage
     */
    public static SImage read(String fileName) {
        return read(fileName, 3);
    }

    /**
     * 画像を書き出す。
     *
     * @param image    書き出すSImage
     * @param fileName ファイル名
     */
    public static void write(SImage image, String fileName) {
        writeBufferedImage(image, fileName);
    }

    /**
     * 画像をフレームにて表示する。
     *
     * @param image 表示するSImage
     */
    public static void show(SImage image) {
        new Viewer(toBufferedImage(image));
    }

    /**
     * 画像表示用のフレーム
     */
    private static class Viewer extends JFrame {
        /**
         * 表示する画像
         */
        BufferedImage bi;

        /**
         * コンストラクタ
         *
         * @param bi BufferedImage型の画像
         */
        public Viewer(BufferedImage bi) {
            this.bi = bi;
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(bi.getWidth(), bi.getHeight());
            setVisible(true);
            setResizable(false);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(bi, 0, 0, null);
        }
    }

}
