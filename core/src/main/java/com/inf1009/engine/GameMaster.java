package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.CollisionManager;
import com.inf1009.engine.manager.EntityManager;
import com.inf1009.engine.manager.IOManager;
import com.inf1009.engine.manager.MovementManager;
import com.inf1009.engine.manager.SceneManager;
import com.inf1009.engine.scene.EndScreen;
import com.inf1009.engine.scene.SimulatorScreen;
import com.inf1009.engine.scene.StartScreen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class GameMaster extends ApplicationAdapter {

    // rendering
    private SpriteBatch batch;

    // Core managers
    private EntityManager ent;
    private SceneManager sm;
    private MovementManager mm;
    private CollisionManager cm;
    private IOManager io;


    //audio
    private Music bgm;
    private Sound coinSound;

    @Override
    public void create() {

        // initialize the rendering and managers
        batch = new SpriteBatch();
        ent = new EntityManager();
        sm  = new SceneManager();
        mm  = new MovementManager();
        cm  = new CollisionManager();
        io  = new IOManager();

        //Screen code
        sm.addScreen("start", new StartScreen(this));
        sm.addScreen("sim", new SimulatorScreen(this));
        sm.addScreen("end", new EndScreen(this)); //acts as pause too
        sm.setScreen("start");


        //background music code
        bgm = Gdx.audio.newMusic(Gdx.files.internal("audio/bgm.wav"));
        bgm.setLooping(true);
        bgm.setVolume(0.4f);
        bgm.play();

        //sound effect
        coinSound = Gdx.audio.newSound(Gdx.files.internal("audio/coin.wav"));

    }

    //toggle audio
    public void toggleAudio() {
    if (bgm == null) return;
    if (bgm.isPlaying()) bgm.pause();
    else bgm.play();
    }
    public boolean isAudioOn() {
        return bgm != null && bgm.isPlaying();
    }


    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        sm.render(dt);
    }

    @Override
    public void dispose() {
        sm.dispose();
        batch.dispose();
        if (bgm != null) bgm.dispose();
        if (coinSound != null) coinSound.dispose();

    }

    public SpriteBatch getBatch() { return batch; }
    public EntityManager getEntityManager() { return ent; }
    public SceneManager getSceneManager() { return sm; }
    public MovementManager getMovementManager() { return mm; }
    public CollisionManager getCollisionManager() { return cm; }
    public IOManager getIOManager() { return io; }
    public Sound getCoinSound() { return coinSound; }

}
