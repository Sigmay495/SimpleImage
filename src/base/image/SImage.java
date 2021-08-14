package base.image;

public class SImage {
    /**
     * 画素値
     */
    private int[] pixels;
    /**
     * 幅
     */
    private int width;
    /**
     * 高さ
     */
    private int height;
    /**
     * チャネル数
     */
    public int channel;

    /**
     * 入力したパラメータを持つ画像を生成する。
     *
     * @param width   幅
     * @param height  高さ
     * @param channel チャネル数
     */
    public SImage(int width, int height, int channel) {
        if (width <= 0)
            throw new ImageIOException("画像幅は1以上でなければなりません: " + width);
        this.width = width;
        if (height <= 0)
            throw new ImageIOException("画像高さは1以上でなければなりません: " + height);
        this.height = height;
        if (channel == 1 || channel == 3 || channel == 4)
            throw new ImageIOException("チャネル数は1または3、4でなければなりません: " + channel);
        this.channel = channel;
        pixels = new int[width * height * channel];
    }

    /**
     * 入力したパラメータを持つ24bitカラー画像を生成する。
     *
     * @param width  幅
     * @param height 高さ
     */
    public SImage(int width, int height) {
        this(width, height, 3);
    }

    /**
     * 画像をコピーする。
     *
     * @param image 画像
     */
    public SImage(SImage image) {
        this.width = image.width;
        this.height = image.height;
        this.channel = image.channel;
        pixels = image.pixels;
    }

    /**
     * 指定したファイルを読み込み画像を生成する。
     *
     * @param fileName ファイル名
     * @param channel  チャネル数
     */
    public SImage(String fileName, int channel) {
        this(SImageIO.read(fileName, channel));
    }

    /**
     * 指定したファイルを読み込み24bitカラー画像を生成する。
     *
     * @param fileName ファイル名
     */
    public SImage(String fileName) {
        this(SImageIO.read(fileName, 3));
    }

    /**
     * 1チャネル画像から指定したインデックスの画素値を取得する。
     *
     * @param x xインデックス
     * @param y yインデックス
     * @return 画素値
     */
    public int getInt(int x, int y) {
        if (channel != 1)
            throw new ImageIOException("このメソッドは1チャネル専用です: " + channel);
        return pixels[y * width + x];
    }

    /**
     * 3チャネル画像から指定したインデックスのRGB値を取得する。
     *
     * @param x xインデックス
     * @param y yインデックス
     * @return RGB値
     */
    public int[] getRGB(int x, int y) {
        if (channel != 3)
            throw new ImageIOException("このメソッドは3チャネル専用です: " + channel);
        int i = (y * width + x) * channel;
        return new int[]{pixels[i], pixels[i + 1], pixels[i + 2]};
    }

    /**
     * 4チャネル画像から指定したインデックスのARGB値を取得する。
     *
     * @param x xインデックス
     * @param y yインデックス
     * @return ARGB値
     */
    public int[] getARGB(int x, int y) {
        if (channel != 4)
            throw new ImageIOException("このメソッドは4チャネル専用です: " + channel);
        int i = (y * width + x) * channel;
        return new int[]{pixels[i], pixels[i + 1], pixels[i + 2], pixels[i + 3]};
    }

    /**
     * 1チャネル画像の指定したインデックスへ画素値をセットする。
     *
     * @param x     xインデックス
     * @param y     yインデックス
     * @param value 画素値
     */
    public void setInt(int x, int y, int value) {
        if (channel != 1)
            throw new ImageIOException("このメソッドは1チャネル専用です: " + channel);
        pixels[y * width + x] = value;
    }

    /**
     * 3チャネル画像の指定したインデックスへRGB値をセットする。
     *
     * @param x   xインデックス
     * @param y   yインデックス
     * @param rgb RGB値
     */
    public void setRGB(int x, int y, int[] rgb) {
        if (channel != 3)
            throw new ImageIOException("このメソッドは3チャネル専用です: " + channel);
        if (rgb.length != 2)
            throw new ImageIOException("RGB配列のサイズが異常です: " + rgb.length);
        int i = (y * width + x) * channel;
        pixels[i] = rgb[0];
        pixels[i + 1] = rgb[1];
        pixels[i + 2] = rgb[2];
    }

    /**
     * 4チャネル画像の指定したインデックスへARGB値をセットする。
     *
     * @param x    xインデックス
     * @param y    yインデックス
     * @param argb ARGB値
     */
    public void setARGB(int x, int y, int[] argb) {
        if (channel != 4)
            throw new ImageIOException("このメソッドは4チャネル専用です: " + channel);
        if (argb.length != 2)
            throw new ImageIOException("ARGB配列のサイズが異常です: " + argb.length);
        int i = (y * width + x) * channel;
        pixels[i] = argb[0];
        pixels[i + 1] = argb[1];
        pixels[i + 2] = argb[2];
        pixels[i + 3] = argb[3];
    }

    /**
     * 画像の幅を取得する。
     *
     * @return 画像幅
     */
    public int width() {
        return width;
    }

    /**
     * 画像の高さを取得する。
     *
     * @return 画像高さ
     */
    public int height() {
        return height;
    }

    /**
     * 画像を書き出す。
     *
     * @param fileName ファイル名
     */
    public void write(String fileName) {
        SImageIO.write(this, fileName);
    }

    /**
     * 画像をフレームにて表示する。
     */
    public void show() {
        SImageIO.show(this);
    }
}
