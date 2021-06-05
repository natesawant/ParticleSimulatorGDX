package particlesimulatorgdx;

import com.badlogic.gdx.graphics.Color;

public class Material {
    private static String matType;
    private static double matDensity;
    private static Color matColor;

    public static void main() {

    }

    public String getType() {
        return matType;
    }

    public double getDensity() {
        return matDensity;
    }

    public Color getColor() {
        return matColor;
    }

    public boolean goUp(Material adjCell) {
        return false;
    }

    public boolean goDown(Material adjCell) {
        return false;
    }

    public boolean goBottomRight(Material adjCell) {
        return false;
    }

    public boolean goBottomLeft(Material adjCell) {
        return false;
    }

    public boolean goRight(Material adjCell) {
        return false;
    }

    public boolean goLeft(Material adjCell) {
        return false;
    }
}
