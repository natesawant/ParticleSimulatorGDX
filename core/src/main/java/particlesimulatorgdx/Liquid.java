package particlesimulatorgdx;

import com.badlogic.gdx.graphics.Color;

public class Liquid extends Material {
    private static String matType = "Liquid";
    private static double matDensity = 1;
    private static Color matColor = Color.BLACK;

    @Override
    public String getType() {
        return matType;
    }

    @Override
    public double getDensity() {
        return matDensity;
    }

    @Override
    public Color getColor() {
        return matColor;
    }
}
