package cl.pucv.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Asteroids extends ApplicationAdapter {
	ModelBatch batch;
    PerspectiveCamera cam;
    Viewport viewport;
    Model model;
    ModelInstance instance;

    Environment env;
	
	@Override
	public void create () {
		batch = new ModelBatch();


        /* Configurar cámara */
        cam = new PerspectiveCamera(67,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        cam.position.set(10, 10, 10);
        cam.lookAt(0, 0, 0);
        cam.near = 1;
        cam.far = 300;
        cam.update();

        viewport = new ScreenViewport(cam);

        /* Cargar modelo */

        ModelBuilder mb = new ModelBuilder();
        model = new G3dModelLoader(
                     new JsonReader())
                         .loadModel(
                                 Gdx.files.internal("spaceship.g3dj")
                         );
            // new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            // Usage.Position | Usage.Normal);

        /* Instanciar modelo */

        instance = new ModelInstance(model);

        /* Crear ambiente */

        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight,
                    .4f, .4f, .4f, 1.f));
        env.add(new DirectionalLight().set(
                    .8f, .8f, .8f, -1.f, -.8f, -.2f));
	}

	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin(cam);
		batch.render(instance, env);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        model.dispose();
	}

    @Override public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
