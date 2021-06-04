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
    /*
    oldGrid[i    ][j + 1], //bottom = 0
	oldGrid[i + 1][j + 1], //bottom right = 1
    oldGrid[i + 1][j    ], //right = 2
    oldGrid[i + 1][j - 1], //top right = 3
    oldGrid[i    ][j - 1], //top = 4
    oldGrid[i - 1][j - 1], //top left = 5
    oldGrid[i - 1][j    ], //left = 6
    oldGrid[i - 1][j + 1] //bottom left = 7
    oldGrid[i    ][j    ] //center = 8
    */

    @Override
    public Material[] moveCell(Material[] adjCells) {
        Material[] newAdjCells = adjCells;

        //goes straight down
        if (adjCells[0] == null) {
            newAdjCells[8] = adjCells[0];
            newAdjCells[0] = adjCells[8];
        }
        ///*
        else { 
            if (Math.random() > .5) {
                if (adjCells[1] == null) {
                    newAdjCells[8] = adjCells[1];
                    newAdjCells[1] = adjCells[8];
                }
            }
            else {
                if (adjCells[7] == null) {
                    newAdjCells[8] = adjCells[7];
                    newAdjCells[7] = adjCells[8];
                }
            }
        }
        //*/

        return newAdjCells;
    }

    /*
    @Override
    public Material[] goUp(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }

    @Override
    public Material[] goDown(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }

    @Override
    public boolean goRight(Material adjCell) {
        return false;
    }

    @Override
    public Material[] goLeft(Material[] adjCells) {
        Material[] newAdjCells = adjCells;
        return newAdjCells;
    }
    */
}