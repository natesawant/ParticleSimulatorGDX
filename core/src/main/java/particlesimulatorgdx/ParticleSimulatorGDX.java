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
	//private Texture grid; //new
	private Pixmap staticPixmap; //static gen
	private Pixmap simPixmap;
	private Texture simTexture;
	private Material[][] oldGrid = new Material[720][480], newGrid = oldGrid;

	public void updateGrid() { //updates the behavior and position of each cell 
		newGrid = oldGrid; //Makes the new grid the exact same as the old grid, now have 2 copies.
		for (int i = 0; i < oldGrid.length; i++) { //Iterates through horizontal axis
			for (int j = 0;	j < oldGrid[i].length; j++) { //Iterates through vertical axis
				if (oldGrid[i][j] != null && j < oldGrid[i].length) { //checks if it is a valid material and that it won't be out of bounds
					Material bottomCell = oldGrid[i][j + 1]; //makes a temporary cell that is below the one we are checking the behavior of.
					if (oldGrid[i][j].goDown(bottomCell)) { //returns true if it can move downwards
						newGrid[i][j + 1] = oldGrid[i][j]; //moves the element down one space in the new grid
						newGrid[i][j] = null; //replaces the old space with null
					};
				}
			}
		}
	}

	public void drawGrid() { //draws each pixel using the data from the 2D cell array
		Pixmap gridPixmap = new Pixmap(720,480,Format.RGBA8888); //initializes a new pixmap
		//gridPixmap.setColor(Color.WHITE); //sets color to white
		//gridPixmap.fill(); //fills in the entire pixmap with white
		int width = oldGrid.length; //temporary variable so it doesn't have to check the width each time, hopefully making it faster
		int height = oldGrid[0].length; //temporary variable so it doesn't have to check the height each time, hopefully making it faster

		for (int i = 0; i < width; i++) { //Iterates through horizontal axis
			for (int j = 0; j < height; j++) { //Iterates through vertical axis
				if (oldGrid[i][j] != null) { //Checks if it is a valid material to prevent errors
					//gridPixmap.setColor(newGrid[i][j].getColor());
					//gridPixmap.setColor(Color.BLACK); //sets color to black, should be newGrid[i][j].getColor() later on
					//gridPixmap.drawPixel(i, j); //draws a pixel of said color at (i, j)
				}
				else {
					//gridPixmap.setColor(Color.FIREBRICK);
					//gridPixmap.drawPixel(i, j); //draws a pixel of said color at (i, j)
				}
			}
		}
		simTexture = new Texture(simPixmap);
		//batch.draw(simTexture, 0, 0);
		simPixmap.dispose();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		//logo = new Texture("badlogic.png");
		//drawGrid();
		
		//staticGen(); //static gen
		
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateGrid();
		 

		batch.begin();
		//drawGrid();
		//batch.draw(simTexture, 0, 0);
		staticGen(); //static gen
		//batch.draw(staticTexture, 0, 0); //static gen
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		simTexture.dispose();
		staticTexture.dispose();
		//grid.dispose(); //new
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
}