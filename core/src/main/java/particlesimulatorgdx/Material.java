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

    public Material[] moveCell(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }
    /*
    public Material[] goUp(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }

    public Material[] goDown(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }

    public Material[] goRight(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }

    public Material[] goLeft(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }
    */
}
