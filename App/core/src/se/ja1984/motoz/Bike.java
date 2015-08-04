package se.ja1984.motoz;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

/**
 * Created by Jonathan on 2015-08-04.
 */
public class Bike extends Sprite {

    private Body bike;

    private Body frontWheel;
    private Body backWheel;

    private Body frontShock;
    private Body backShock;

    private RevoluteJoint frontMotor;
    private RevoluteJoint backMotor;

    private PrismaticJoint frontSpring;
    private PrismaticJoint backSpring;


    public Bike(World world){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 3.5f);

        bike = world.createBody(bodyDef);
//
//        PolygonDef boxDef = new b2PolygonDef();
//        boxDef.density = 2;
//        boxDef.friction = 0.5;
//        boxDef.restitution = 0.2;
//        boxDef.filter.groupIndex = -1;
//
//        boxDef.SetAsBox(1.5, 0.3);
//        cart.CreateShape(boxDef);
//
//        boxDef.SetAsOrientedBox(0.4, 0.15, new b2Vec2(-1, -0.3), Math.PI/3);
//        cart.CreateShape(boxDef);
//
//        boxDef.SetAsOrientedBox(0.4, 0.15, new b2Vec2(1, -0.3), -Math.PI/3);
//        cart.CreateShape(boxDef);
//
//        cart.SetMassFromShapes();
//
//        boxDef.density = 1;
    }

}
