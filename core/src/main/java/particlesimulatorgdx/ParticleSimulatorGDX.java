package particlesimulatorgdx;

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

	private Texture staticTexture; 

	private Texture simTexture;

	final int WIDTH = 720;
	final int HEIGHT = 480;

	private Material[][] oldGrid = new Material[WIDTH][HEIGHT], newGrid = oldGrid;

	public void updateGrid() { //updates the behavior and position of each cell 
		newGrid = oldGrid; //Makes the new grid the exact same as the old grid, now have 2 copies.
		for (int i = 0; i < WIDTH; i++) { //Iterates through horizontal axis
			for (int j = HEIGHT - 1; j >= 0; j--) {

				//System.out.println("Index (" + i + ", " + j + ") is null: " + (oldGrid[0] == null)); //Iterates through vertical axis
				if (oldGrid[i][j] != null && j + 1 < oldGrid[i].length) { //checks if it is a valid material and that it won't be out of bounds
					Material bottomCell = oldGrid[i][j + 1]; //makes a temporary cell that is below the one we are checking the behavior of.
					if (oldGrid[i][j].goDown(bottomCell)) { //returns true if it can move downwards
						oldGrid[i][j + 1] = oldGrid[i][j]; //moves the element down one space in the new grid
						oldGrid[i][j] = null; //replaces the old space with null
					}
				else {
					Material bottomRightCell;
					Material bottomLeftCell;
					Material rightCell;
					Material leftCell;
					if (Math.random() > 0.5) {
						if (oldGrid[i][j] != null && i + 1 < oldGrid.length && j + 1 < HEIGHT) { //checks if it is a valid material and that it won't be out of bounds
							bottomRightCell = oldGrid[i + 1][j + 1]; //makes a temporary cell that is to the right of the one we are checking the behavior of.
							if (oldGrid[i][j].goBottomRight(bottomRightCell)) { //returns true if it can move rightwards
								oldGrid[i + 1][j + 1] = oldGrid[i][j]; //moves the element right one space and one down in the new grid
								oldGrid[i][j] = null; //replaces the old space with null
							}
						}
						if (oldGrid[i][j] != null && i - 1 >= 0 && j + 1 < HEIGHT) { //checks if it is a valid material and that it won't be out of bounds
							bottomLeftCell = oldGrid[i - 1][j + 1]; //makes a temporary cell that is to the left of the one we are checking the behavior of.
							if (oldGrid[i][j].goBottomLeft(bottomLeftCell)) { //returns true if it can move leftwards
								oldGrid[i - 1][j + 1] = oldGrid[i][j]; //moves the element left one space and down one in the new grid
								oldGrid[i][j] = null; //replaces the old space with null
							}
						}
					}
						

					else {
						if (oldGrid[i][j] != null && i - 1 >= 0 && j + 1 < HEIGHT) { //checks if it is a valid material and that it won't be out of bounds
							bottomLeftCell = oldGrid[i - 1][j + 1]; //makes a temporary cell that is to the left of the one we are checking the behavior of.
							if (oldGrid[i][j].goBottomLeft(bottomLeftCell)) { //returns true if it can move leftwards
								oldGrid[i - 1][j + 1] = oldGrid[i][j]; //moves the element left one space and down one in the new grid
								oldGrid[i][j] = null; //replaces the old space with null
							}
						}

						if (oldGrid[i][j] != null && i + 1 < oldGrid.length && j + 1 < HEIGHT) { //checks if it is a valid material and that it won't be out of bounds
							bottomRightCell = oldGrid[i + 1][j + 1]; //makes a temporary cell that is to the right of the one we are checking the behavior of.
							if (oldGrid[i][j].goBottomRight(bottomRightCell)) { //returns true if it can move rightwards
								oldGrid[i + 1][j + 1] = oldGrid[i][j]; //moves the element right one space and one down in the new grid
								oldGrid[i][j] = null; //replaces the old space with null
							}
						}	
					}
				}
			}
		}
	}
}


	public void drawGrid() { //draws each pixel using the data from the 2D cell array
		Pixmap simPixmap = new Pixmap(WIDTH,HEIGHT,Format.RGBA8888); //initializes a new pixmap
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

	public void genRand(Material mat, double prob) {	
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (Math.random() < prob) oldGrid[i][j] = mat;
			}
		}
	}

public void genCircle(int x, int y, int r, Material mat, boolean filled) {

		double distance;

		if (filled) {
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
		if (!filled) {
			for (int i = x - r; i < x + r; i++) {
				for (int j = y - r; j < y + r; j++) {
					if (i >= 0 && j >= 0 && i < WIDTH && j < HEIGHT) {
						distance = Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
						if (distance <= r && distance >= r - 1.25) {
							oldGrid[i][j] = mat;
						}
					}
				}
			}
		}
	}
	public void genRectangle(int x, int y, int width, int height, Material mat, boolean filled) {
		if (filled) {
			for (int i = x - width/2; i < x + width/2; i++) {
				for (int j = y - height/2; j < y + height/2; j++) {
					if (i >= 0 && j >= 0 && i < WIDTH && j < HEIGHT) {
						oldGrid[i][j] = mat;
					}
				}
			}
		}
		if (!filled) {
			for (int i = x - width/2; i < x + width/2; i++) {
				if (i >= 0 && i < WIDTH && y - height/2 >= 0 && y + height/2 < HEIGHT) {
					oldGrid[i][y - height/2] = mat;
					oldGrid[i][y + height/2] = mat;
				}
			}
			for (int j = y - height/2; j < y + height/2; j++) {
				if (j >= 0 && j < HEIGHT && x - width/2 >= 0 && x + width/2 < WIDTH) {
					oldGrid[x - width/2][j] = mat;
					oldGrid[x + width/2][j] = mat;
				}
			}
		}
	}

	public void staticGen() {
		Pixmap _pixmap = new Pixmap(WIDTH,HEIGHT,Format.RGBA8888); //new
		int cells[][] = new int[WIDTH][HEIGHT];
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

		Material sandMat = new Sand();
		Material concreteMat = new Concrete();
		Material waterMat = new Water();
		
		genCircle(360, 240, 150, sandMat, true);
		genCircle(600, 250, 60, sandMat, true);
		genCircle(60, 60, 60, waterMat, true);
		genCircle(600, 350, 60, sandMat, true);
		genCircle(600, 400, 50, concreteMat, false);
		genRectangle(500, 300, 200, 50, concreteMat, false);
		genRand(waterMat, 0.40);
		
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
	}

}