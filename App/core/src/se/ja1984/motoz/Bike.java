package se.ja1984.motoz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 * Created by Jonathan on 2015-08-04.
 */
public class Bike extends InputAdapter {

    private Body chassis, leftWheel, rightWheel;
    private WheelJoint leftAxis, rightAxis;
    private float motorSpeed = 50;
    private boolean flipped;

    public Bike(World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height) {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("box2d/bike.json"));
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        chassis = world.createBody(bodyDef);

        // left wheel
        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height / 3.5f);

        wheelFixtureDef.shape = wheelShape;

        leftWheel = world.createBody(bodyDef);
        leftWheel.createFixture(wheelFixtureDef);

        // right wheel
        rightWheel = world.createBody(bodyDef);
        rightWheel.createFixture(wheelFixtureDef);

        // left axis
        WheelJointDef axisDef = new WheelJointDef();
        axisDef.bodyA = chassis;
        axisDef.bodyB = leftWheel;
        axisDef.localAnchorA.set(-width / 2 * .75f + wheelShape.getRadius(), -height / 2 * 1.25f);
        axisDef.frequencyHz = chassisFixtureDef.density;
        axisDef.localAxisA.set(Vector2.Y);
        axisDef.maxMotorTorque = chassisFixtureDef.density * 10;
        leftAxis = (WheelJoint) world.createJoint(axisDef);

        // right axis
        axisDef.bodyB = rightWheel;
        axisDef.localAnchorA.x *= -1;

        rightAxis = (WheelJoint) world.createJoint(axisDef);
        loader.attachFixture(chassis, "bike", chassisFixtureDef, width);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.UP:
                if(!flipped) {
                    leftAxis.enableMotor(true);
                    leftAxis.setMotorSpeed(-motorSpeed);
                }
                else{
                    rightAxis.enableMotor(true);
                    rightAxis.setMotorSpeed(+motorSpeed);
                }
                break;
            case Input.Keys.DOWN:
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(0);

                rightAxis.enableMotor(true);
                rightAxis.setMotorSpeed(0);
                break;

            case Input.Keys.LEFT:
                rightWheel.applyLinearImpulse(1.5f,17.5f,0,0,true);
                break;

            case Input.Keys.RIGHT:
                leftWheel.applyLinearImpulse(-.5f, 17.5f, 0, 0, true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                leftAxis.enableMotor(false);
                rightAxis.enableMotor(false);
                break;
        }
        return true;
    }

    public Body getChassis() {
        return chassis;
    }

    public void turn() {
        flipped = !flipped;
    }
}
