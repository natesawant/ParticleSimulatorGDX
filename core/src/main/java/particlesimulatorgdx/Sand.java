package particlesimulatorgdx;

import com.badlogic.gdx.graphics.Color;

public class Sand extends Solid {
    private static String matType = "Solid";
    private static double matDensity = 1;
    private static Color matColor = Color.TAN;

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

    @Override
    public boolean goUp(Material adjCell) {
        return false;
    }

    @Override
    public boolean goDown(Material adjCell) {
        if (adjCell == null) return true;
        else return false;
    }

    @Override
    public boolean goRight(Material adjCell) {
        if (adjCell == null) return true;
        return false;
    }

    @Override
    public boolean goLeft(Material adjCell) {
        return false;
    }
}