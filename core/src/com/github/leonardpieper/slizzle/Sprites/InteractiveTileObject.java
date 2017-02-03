package com.github.leonardpieper.slizzle.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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

public abstract class InteractiveTileObject {
    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Rectangle bounds;
    private Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef  bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(bounds.getX() + bounds.getWidth() / 2 / Slizzle.PPM, bounds.getY() +bounds.getHeight() / 2 / Slizzle.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Slizzle.PPM, bounds.getHeight() / 2 / Slizzle.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
