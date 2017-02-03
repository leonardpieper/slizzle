package com.github.leonardpieper.slizzle.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.github.leonardpieper.slizzle.Slizzle;

/**
 * Created by Leonard on 03.02.2017.
 */

public class B2WorldCreater {
    public B2WorldCreater(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2) / Slizzle.PPM, (rect.getY() + rect.getHeight() / 2) / Slizzle.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / Slizzle.PPM, (rect.getHeight() / 2) / Slizzle.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
