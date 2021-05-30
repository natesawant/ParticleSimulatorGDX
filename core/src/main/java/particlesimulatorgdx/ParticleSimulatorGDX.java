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
	private Pixmap staticPixmap; //new

	@Override
	public void create() {
		batch = new SpriteBatch();
		//logo = new Texture("badlogic.png");
			
		
		staticPixmap = staticGen();
		staticTexture = new Texture(staticPixmap); //new
		staticPixmap.dispose(); //new
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(staticTexture, 0, 0);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		staticTexture.dispose();
		//grid.dispose(); //new
	}

	public Pixmap staticGen() {
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
		return _pixmap;
	}
}