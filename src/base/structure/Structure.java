package base.structure;

import java.util.HashSet;
import java.util.Set;

public class Structure {
    /**
     * 構造タイプの定義
     */
    private enum Type {
        /**
         * 正方形
         */
        Square,
        /**
         * 長方形
         */
        Rectangle,
        /**
         * ひし形
         */
        Diamond,
        /**
         * 円
         */
        Circle,
        /**
         * 楕円
         */
        Ellipse;
    }

    /**
     * 座標集合
     */
    private Set<Point> pointSet;
    /**
     * 構造要素の型
     */
    private Type type;
    /**
     * x半径
     */
    private int rx;
    /**
     * y半径
     */
    private int ry;

    /**
     * 構造要素を生成する。
     *
     * @param structureName 構造要素タイプ名
     * @param rx            x半径
     * @param ry            x半径
     */
    public Structure(String structureName, int rx, int ry) {
        pointSet = new HashSet<>();
        switch (structureName.toLowerCase()) {
            case "square":
                type = Type.Square;
                this.rx = rx;
                this.ry = rx;
                rectangle();
                break;
            case "rectangle":
                type = Type.Rectangle;
                this.rx = rx;
                this.ry = ry;
                rectangle();
                break;
            case "diamond":
                type = Type.Diamond;
                this.rx = rx;
                this.ry = ry;
                diamond();
                break;
            case "circle":
                type = Type.Circle;
                this.rx = rx;
                this.ry = rx;
                ellipse();
                break;
            case "ellipse":
                type = Type.Ellipse;
                this.rx = rx;
                this.ry = ry;
                ellipse();
                break;
            default:
                throw new StructureMakingException("登録されていない型の構造要素です: " + structureName);
        }
        if (this.rx <= 0)
            throw new StructureMakingException("半径の値が不正です: " + rx);
        if (this.ry <= 0)
            throw new StructureMakingException("半径の値が不正です: " + ry);
    }

    /**
     * 構造要素を生成する。
     *
     * @param structureName 構造要素タイプ名
     * @param r             半径
     */
    public Structure(String structureName, int r) {
        this(structureName, r, r);
    }

    /**
     * 長方形を生成する。
     */
    private void rectangle() {
        for (int dy = -ry; dy <= ry; dy++)
            for (int dx = -rx; dx <= rx; dx++)
                pointSet.add(new Point(dx, dy));
    }

    /**
     * ひし形を生成する。
     */
    private void diamond() {
        double lx = rx > 0 ? rx : 1;
        double ly = ry > 0 ? ry : 1;
        for (int dy = -ry; dy <= ry; dy++)
            for (int dx = -rx; dx <= rx; dx++)
                if (Math.abs(dx) / lx + Math.abs(dy) / ly <= 1)
                    pointSet.add(new Point(dx, dy));
    }

    /**
     * 楕円を生成する。
     */
    private void ellipse() {
        double lx = rx > 0 ? rx * rx : 1;
        double ly = ry > 0 ? ry * ry : 1;
        for (int dy = -ry; dy <= ry; dy++)
            for (int dx = -rx; dx <= rx; dx++)
                if (dx * dx / lx + dy * dy / ly <= 1)
                    pointSet.add(new Point(dx, dy));
    }

}
