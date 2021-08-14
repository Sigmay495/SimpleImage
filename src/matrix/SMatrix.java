package matrix;

public class SMatrix {
    /**
     * 要素
     */
    private double[][] elements;
    /**
     * 行数
     */
    private int row;
    /**
     * 列数
     */
    private int column;

    /**
     * 零行列を生成する。
     *
     * @param r 行数．
     * @param c 列数．
     * @since 2018.9.25
     */
    public SMatrix(int r, int c) {
        row = r;
        column = c;
        elements = new double[r][c];
    }

    /**
     * 行列に値をセットする。
     *
     * @param r 行インデックス
     * @param c 列インデックス
     * @param v 値
     */
    public void set(int r, int c, double v) {
        elements[r][c] = v;
    }

    /**
     * 行列の要素を取得する。
     *
     * @param r 行インデックス
     * @param c 列インデックス
     * @return 要素
     */
    public double get(int r, int c) {
        return elements[r][c];
    }

    /**
     * 行数を取得する。
     *
     * @return 行数
     */
    public int row() {
        return row;
    }

    /**
     * 列数を取得する。
     *
     * @return 列数
     */
    public int column() {
        return column;
    }

    /**
     * 転置行列を取得する。
     *
     * @return 転置行列
     */
    public final SMatrix transpose() {
        SMatrix m = new SMatrix(column, row);
        for (int r = 0; r < row; r++)
            for (int c = 0; c < column; c++)
                m.elements[c][r] = elements[r][c];
        return m;
    }

    /**
     * 単位行列を生成する。
     *
     * @param row    行数
     * @param column 列数
     * @return 単位行列
     */
    public static SMatrix eye(int row, int column) {
        SMatrix m = new SMatrix(row, column);
        for (int i = 0; i < Math.min(row, column); i++)
            m.set(i, i, 1);
        return m;
    }

    /**
     * 単位正方行列を生成する。
     *
     * @param i サイズ
     * @return 単位正方行列
     */
    public static SMatrix eye(int i) {
        return eye(i, i);
    }

}
