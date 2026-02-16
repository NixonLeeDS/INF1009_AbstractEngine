package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.inf1009.engine.manager.*;
import com.inf1009.engine.scene.*;
import com.inf1009.engine.sound.Sound;
import com.inf1009.engine.input.KeyboardDevice;

public class GameMaster extends ApplicationAdapter {

    // UML fields
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

        // Managers
        em  = new EntityManager();
        sm  = new SceneManager();
        mm  = new MovementManager();
        io  = new InputManager();
        snd = new SoundManager();
        cm  = new CollisionManager(em);

        cm.addCollisionListener(snd);

        // Audio
        Sound bgm = new Sound("audio/bgm.wav", true, 100);
        Sound hit = new Sound("audio/hit.wav", false, 100);

        snd.addSound(bgm);
        snd.addSound(hit);
        snd.playMusic("audio/bgm.wav");

        // Input
        io.registerDevice(KeyboardDevice.wasd());

        // Scenes
        sm.addScene("start", new StartScene(this));
        sm.addScene("settings", new SettingsScene(sm, io, snd, batch));
        sm.addScene("sim", new SimulatorScene(em, mm, io, batch, sm));
        sm.addScene("end", new EndScene(this));

        sm.setScene("start");
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        em.update(dt);
        cm.update();
        sm.update(dt);
    }

    @Override
    public void dispose() {

        sm.dispose();

        if (batch != null)
            batch.dispose();
    }

    // UML getters
    public EntityManager getEntityManager() { return em; }
    public SceneManager getSceneManager() { return sm; }
    public MovementManager getMovementManager() { return mm; }
    public CollisionManager getCollisionManager() { return cm; }
    public InputManager getIOManager() { return io; }
    public SoundManager getSoundManager() { return snd; }
    public SpriteBatch getBatch() { return batch; }
}
