package particlesimulatorgdx;

import com.badlogic.gdx.graphics.Color;

public class Concrete extends Solid {
    private static String matType = "Solid";
    private static double matDensity = 1;
    private static Color matColor = Color.GRAY;

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
