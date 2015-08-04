package se.ja1984.motoz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameClass extends ApplicationAdapter {
	SpriteBatch batch;

	public final static int ITERATIONS         	= 30;
	public final static double TIME_STEP       	= 1.0/60.0;
	public final static int SCREEN_WIDTH   		= 800;
	public final static int SCREEN_HEIGHT   	= 480;
	public final static double DRAW_SCALE      	= 50;

	Box2DDebugRenderer debugRender;
	Matrix4 debugMatrix;
	private OrthographicCamera camera;

	private World world;
	private Sprite screen;
	private Bike bike;

	@Override
	public void create () {
		world = new World(new Vector2(0, -10.0f), true);
		debugRender = new Box2DDebugRenderer(true,true,true,true,true,true);
		camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
		debugMatrix=new Matrix4(camera.combined);
		addGround();

		bike = new Bike(world);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRender.render(world, debugMatrix);
		camera.update();
	}


	private void addGround(){
//		Body box = createBox(BodyDef.BodyType.StaticBody, 1, 1, 0);
//		box.setTransform(30, 3, 0);
//		box = createBox(BodyDef.BodyType.StaticBody, 1.2f, 1.2f, 0);
//		box.setTransform(5, 2.4f, 0);
//
//		for(int i = 0; i < 20; i++) {
//			box = createBox(BodyDef.BodyType.DynamicBody, (float)Math.random(), (float)Math.random(), 3);
//			box.setTransform((float)Math.random() * 10f - (float)Math.random() * 10f, (float)Math.random() * 10 + 6, (float)(Math.random() * 2 * Math.PI));
//		}


		// add the ground //

		Body box = createBox(BodyDef.BodyType.StaticBody, 50, 50, 0);


		boxDef.SetAsOrientedBox(1, 2, new b2Vec2(-50, 0.5), 0);
		body.CreateShape(boxDef);

		boxDef.SetAsOrientedBox(1, 2, new b2Vec2(50, 0.5), 0);
		body.CreateShape(boxDef);

		boxDef.SetAsOrientedBox(3, 0.5, new b2Vec2(5, 1.5), Math.PI/4);
		body.CreateShape(boxDef);

		boxDef.SetAsOrientedBox(3, 0.5, new b2Vec2(3.5, 1), Math.PI/8);
		body.CreateShape(boxDef);

		boxDef.SetAsOrientedBox(3, 0.5, new b2Vec2(9, 1.5), -Math.PI/4);
		body.CreateShape(boxDef);

		boxDef.SetAsOrientedBox(3, 0.5, new b2Vec2(10.5, 1), -Math.PI/8);
		body.CreateShape(boxDef);

		body.SetMassFromShapes();

	}

	private Body createBox(BodyDef.BodyType type, float width, float height, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);


		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height); //<--- = boxDef.SetAsOrientedBox(3, 0.5, new b2Vec2(5, 1.5), Math.PI/4);
		box.createFixture(poly, density);
		poly.dispose();

		return box;
	}

}
