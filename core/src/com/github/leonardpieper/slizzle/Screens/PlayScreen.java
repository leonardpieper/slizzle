package com.github.leonardpieper.slizzle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.leonardpieper.slizzle.Scenes.Hud;
import com.github.leonardpieper.slizzle.Slizzle;
import com.github.leonardpieper.slizzle.Sprites.Emoji;
import com.github.leonardpieper.slizzle.Tools.B2WorldCreater;
import com.github.leonardpieper.slizzle.Tools.SimpleDirectionGestureDetector;

/**
 * Created by Leonard on 02.02.2017.
 */

public class PlayScreen implements Screen {

    private Slizzle game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

//    Tiled Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

//    Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Emoji player;

    public PlayScreen(Slizzle game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameCam.update();
        gamePort = new FitViewport(Slizzle.V_WIDTH / Slizzle.PPM, Slizzle.V_HEIGHT / Slizzle.PPM, gameCam);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Slizzle.PPM);


        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        player = new Emoji(world);

        new B2WorldCreater(world, map);

        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                handleSwipe(0);
            }

            @Override
            public void onRight() {
                handleSwipe(2);
            }

            @Override
            public void onLeft() {
                handleSwipe(3);
            }

            @Override
            public void onDown() {
                handleSwipe(1);
            }
        }));
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.setLinearVelocity(0,0);
            player.b2body.applyLinearImpulse(new Vector2(0, 1f), player.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            player.b2body.setLinearVelocity(0,0);
            player.b2body.applyLinearImpulse(new Vector2(0, -1f), player.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2)
            player.b2body.setLinearVelocity(0,0);
            player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-2)
            player.b2body.setLinearVelocity(0,0);
            player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
    }
    public void handleSwipe(int dir){
        switch (dir){
            case 0:
                player.b2body.setLinearVelocity(0,0);
                player.b2body.applyLinearImpulse(new Vector2(0, 1f), player.b2body.getWorldCenter(), true);
                break;
            case 1:
                player.b2body.setLinearVelocity(0,0);
                player.b2body.applyLinearImpulse(new Vector2(0, -1f), player.b2body.getWorldCenter(), true);
                break;
            case 2:
                player.b2body.setLinearVelocity(0,0);
                player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
                break;
            case 3:
                player.b2body.setLinearVelocity(0,0);
                player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
                break;
        }
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);

        //update the gamecam with correct coordinates after changes
        gameCam.update();

        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        //Clear game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //render Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
