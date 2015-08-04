package se.ja1984.motoz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tobias on 2015-08-04.
 */
public class GameScreen implements Screen {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private Bike bike;

    private Array<Body> tmpBodies = new Array<Body>();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        camera.position.set(bike.getChassis().getPosition().x, bike.getChassis().getPosition().y, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(tmpBodies);
        for(Body body : tmpBodies)
            if(body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef(), wheelFixtureDef = new FixtureDef();

        // bike
        fixtureDef.density = 5;
        fixtureDef.friction = .4f;
        fixtureDef.restitution = .3f;

        wheelFixtureDef.density = fixtureDef.density * 1.5f;
        wheelFixtureDef.friction = 50;
        wheelFixtureDef.restitution = .4f;

        bike = new Bike(world, fixtureDef, wheelFixtureDef, 0, 3, 3, 1.25f);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Input.Keys.ENTER:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
                        break;
                    case Input.Keys.SPACE:
                        bike.turn();
                        break;
                }
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }

        }, bike));

        // GROUND
        // body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        // ground shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(-50, 0), new Vector2(50, 0)});

        // fixture definition
        fixtureDef.shape = groundShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

}
