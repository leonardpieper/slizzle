package com.github.leonardpieper.slizzle.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.github.leonardpieper.slizzle.Slizzle;

/**
 * Created by Leonard on 02.02.2017.
 */

public class Emoji extends Sprite{
    public World world;
    public Body b2body;

    public Emoji(World world){
        this.world = world;
        defineEmoji();
    }

    public void defineEmoji(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / Slizzle.PPM, 100 / Slizzle.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Slizzle.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
