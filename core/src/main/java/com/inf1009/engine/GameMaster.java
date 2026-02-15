package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.*;
import com.inf1009.engine.scene.*;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;

    private EntityManager em;
    private SceneManager sm;
    private MovementManager mm;
    private CollisionManager cm;
    private InputManager io;
    private SoundManager snd;

    @Override
    public void create() {
        batch = new SpriteBatch();

        em = new EntityManager();
        sm = new SceneManager();
        mm = new MovementManager();
        cm = new CollisionManager(em);
        cm.addCollisionListener(snd);
        io = new InputManager();
        snd = new SoundManager();
        

        io.registerDevice(com.inf1009.engine.input.KeyboardDevice.wasd());

        sm.addScene("start", new StartScene(this));
        sm.addScene("sim", new SimulatorScene(this));
        sm.addScene("end", new EndScene(this));
        sm.setScene("start");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        sm.update(dt);
    }

    @Override
    public void dispose() {
        sm.dispose();
        if (batch != null) batch.dispose();
    }

    public EntityManager getEntityManager() { return em; }
    public SceneManager getSceneManager() { return sm; }
    public MovementManager getMovementManager() { return mm; }
    public CollisionManager getCollisionManager() { return cm; }
    public InputManager getInputManager() { return io; }
    public InputManager getIOManager() { return io; }
    public SoundManager getSoundManager() { return snd; }
    public SpriteBatch getBatch() { return batch; }
}
