package particlesimulatorgdx;

import java.util.Arrays;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ParticleSimulatorGDX extends ApplicationAdapter {
	private SpriteBatch batch;

	private Pixmap staticPixmap; //static generator
	private Texture staticTexture; 

	private Pixmap simPixmap;
	private Texture simTexture;

	final int WIDTH = 720;
	final int HEIGHT = 480;

	private Material[][] oldGrid = new Material[720][480], newGrid = oldGrid;

	public void updateGrid() { //updates the behavior and position of each cell 
		double rand;
		newGrid = oldGrid; //Makes the new grid the exact same as the old grid, now have 2 copies.
		for (int i = 0; i < WIDTH; i++) { //Iterates through horizontal axis
			for (int j = HEIGHT - 1; j >= 0; j--) {
				rand = Math.random();
				
				//System.out.println("Index (" + i + ", " + j + ") is null: " + (oldGrid[0] == null)); //Iterates through vertical axis
				if (oldGrid[i][j] != null && j + 1 < oldGrid[i].length) { //checks if it is a valid material and that it won't be out of bounds
					Material bottomCell = oldGrid[i][j + 1]; //makes a temporary cell that is below the one we are checking the behavior of.
					if (oldGrid[i][j].goDown(bottomCell)) { //returns true if it can move downwards
						oldGrid[i][j + 1] = oldGrid[i][j]; //moves the element down one space in the new grid
						oldGrid[i][j] = null; //replaces the old space with null
					}
				if (rand >= 0.5) {
					if (oldGrid[i][j] != null && i + 1 < oldGrid[i].length) { //checks if it is a valid material and that it won't be out of bounds
					Material rightCell = oldGrid[i + 1][j]; //makes a temporary cell that is to the right of the one we are checking the behavior of.
					if (oldGrid[i][j].goRight(rightCell)) { //returns true if it can move rightwards
						oldGrid[i + 1][j] = oldGrid[i][j]; //moves the element right one space in the new grid
						oldGrid[i][j] = null; //replaces the old space with null
					}
				}
				else {
					if (oldGrid[i][j] != null && i - 1 > 0) { //checks if it is a valid material and that it won't be out of bounds
					Material leftCell = oldGrid[i - 1][j]; //makes a temporary cell that is to the left of the one we are checking the behavior of.
					if (oldGrid[i][j].goRight(leftCell)) { //returns true if it can move leftwards
						oldGrid[i - 1][j] = oldGrid[i][j]; //moves the element left one space in the new grid
						oldGrid[i][j] = null; //replaces the old space with null
					}
				}
			}
		//oldGrid = newGrid;
				}
			}
			
		}
	}
}

	public void drawGrid() { //draws each pixel using the data from the 2D cell array
		Pixmap simPixmap = new Pixmap(720,480,Format.RGBA8888); //initializes a new pixmap
		//gridPixmap.setColor(Color.WHITE); //sets color to white
		//gridPixmap.fill(); //fills in the entire pixmap with white

		for (int i = 0; i < WIDTH; i++) { //Iterates through horizontal axis
			for (int j = 0; j < HEIGHT; j++) { //Iterates through vertical axis
				if (oldGrid[i][j] != null) { //Checks if it is a valid material to prevent errors
					simPixmap.setColor(newGrid[i][j].getColor());
					//simPixmap.setColor(Color.TAN); //sets color to tan, should be newGrid[i][j].getColor() later on
					simPixmap.drawPixel(i, j); //draws a pixel of said color at (i, j)
				}
				else {
					simPixmap.setColor(Color.WHITE);
					simPixmap.drawPixel(i, j); //draws a pixel of said color at (i, j)
				}
			}
		}
		simTexture = new Texture(simPixmap);
		simPixmap.dispose();
		batch.draw(simTexture, 0, 0);
	}

	public void genRandSand() {
		Material sandMat = new Sand();
		
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				double rand = Math.random();
				if (rand > 0.5) oldGrid[i][j] = sandMat;
			}
		}
	}

	public void genCircle(int x, int y, int r, Material mat) {

		double distance;

		for (int i = x - r; i < x + r; i++) {
			for (int j = y - r; j < y + r; j++) {
				if (i >= 0 && j >= 0 && i < WIDTH && j < HEIGHT) {
					distance = Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
					if (distance <= r) {
						oldGrid[i][j] = mat;
					}
				}
			}
		}
	}

	public void staticGen() {
		Pixmap _pixmap = new Pixmap(720,480,Format.RGBA8888); //new
		int cells[][] = new int[720][480];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				double rand = Math.random();
				if (rand > 0.5) cells[i][j] = 1;
				else cells[i][j] = 0;
			}
		}
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == 1) {
					_pixmap.setColor(Color.BLACK);
					_pixmap.drawPixel(i, j);
				}
				else {
					_pixmap.setColor(Color.WHITE);
					_pixmap.drawPixel(i, j);
				}
			}
		}
		staticTexture = new Texture(_pixmap);
		_pixmap.dispose(); //static gen
		batch.draw(staticTexture, 0, 0);
	}

	@Override
	public void create() {
		batch = new SpriteBatch();

		//genRandSand();

		Material sandMat = new Sand();
		Material concreteMat = new Concrete();
		genCircle(360, 240, 150, sandMat);
		genCircle(600, 250, 60, sandMat);
		genCircle(600, 400, 50, concreteMat);
		
		//staticGen(); //static gen
		
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateGrid();

		batch.begin();

		drawGrid();
		//staticGen(); //static gen

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		simTexture.dispose();
		staticTexture.dispose();
		//grid.dispose(); //new
	}

}