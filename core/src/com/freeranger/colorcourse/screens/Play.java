package com.freeranger.colorcourse.screens;

import static com.freeranger.colorcourse.handlers.B2DVars.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.entities.BlueColorBucket;
import com.freeranger.colorcourse.entities.BlueJumppad;
import com.freeranger.colorcourse.entities.BlueKey;
import com.freeranger.colorcourse.entities.BlueKeyblock;
import com.freeranger.colorcourse.entities.BlueMovingBlock;
import com.freeranger.colorcourse.entities.Boss;
import com.freeranger.colorcourse.entities.BossBullet;
import com.freeranger.colorcourse.entities.Checkpoint;
import com.freeranger.colorcourse.entities.Coin;
import com.freeranger.colorcourse.entities.DarkEvilE;
import com.freeranger.colorcourse.entities.Doomfist;
import com.freeranger.colorcourse.entities.Door;
import com.freeranger.colorcourse.entities.EvilE;
import com.freeranger.colorcourse.entities.FallingRock;
import com.freeranger.colorcourse.entities.Ghost;
import com.freeranger.colorcourse.entities.GreenColorBucket;
import com.freeranger.colorcourse.entities.GreenJumppad;
import com.freeranger.colorcourse.entities.GreenKey;
import com.freeranger.colorcourse.entities.GreenKeyblock;
import com.freeranger.colorcourse.entities.GreenMovingBlock;
import com.freeranger.colorcourse.entities.HUD;
import com.freeranger.colorcourse.entities.HeartPowerup;
import com.freeranger.colorcourse.entities.JumpPowerup;
import com.freeranger.colorcourse.entities.OrangeColorBucket;
import com.freeranger.colorcourse.entities.OrangeJumppad;
import com.freeranger.colorcourse.entities.OrangeKey;
import com.freeranger.colorcourse.entities.OrangeKeyblock;
import com.freeranger.colorcourse.entities.OrangeMovingBlock;
import com.freeranger.colorcourse.entities.PinkColorBucket;
import com.freeranger.colorcourse.entities.PinkJumppad;
import com.freeranger.colorcourse.entities.PinkKey;
import com.freeranger.colorcourse.entities.PinkKeyblock;
import com.freeranger.colorcourse.entities.PinkMovingBlock;
import com.freeranger.colorcourse.entities.PlantOfDoom;
import com.freeranger.colorcourse.entities.Player;
import com.freeranger.colorcourse.entities.RangedEnemy;
import com.freeranger.colorcourse.entities.RangedEnemyBullet;
import com.freeranger.colorcourse.entities.Reaper;
import com.freeranger.colorcourse.entities.RedColorBucket;
import com.freeranger.colorcourse.entities.RedJumppad;
import com.freeranger.colorcourse.entities.RedKey;
import com.freeranger.colorcourse.entities.RedKeyblock;
import com.freeranger.colorcourse.entities.RedMovingBlock;
import com.freeranger.colorcourse.entities.SpeedPowerup;
import com.freeranger.colorcourse.entities.SpikeBall;
import com.freeranger.colorcourse.entities.TempHealthPowerup;
import com.freeranger.colorcourse.entities.ThePipeBall;
import com.freeranger.colorcourse.entities.ThePipeOver;
import com.freeranger.colorcourse.entities.ThePipeUnder;
import com.freeranger.colorcourse.entities.TheShell;
import com.freeranger.colorcourse.entities.YellowColorBucket;
import com.freeranger.colorcourse.entities.YellowJumppad;
import com.freeranger.colorcourse.entities.YellowKey;
import com.freeranger.colorcourse.entities.YellowKeyblock;
import com.freeranger.colorcourse.entities.YellowMovingBlock;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.handlers.Log;
import com.freeranger.colorcourse.handlers.MyContactListener;
import com.freeranger.colorcourse.handlers.Shake;

import java.util.Random;

public class Play implements Screen {

	private int playerColor = 1; // 1-6, red-pink
	private boolean onJumpPad = false;
	
	private int health;
	private boolean cantDoStuff = false;
	private float tileSize;

	private World world;
	private Box2DDebugRenderer b2dr;
	private TmxMapLoader loader;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;
	private OrthographicCamera b2dCam;

	private Player player;
	private MyContactListener cl;
	private Main game;
	private HUD hud;

	private Array<Coin> coins;
	private Array<EvilE> eviles;
	private Array<DarkEvilE> darkEviles;
	private Array<PlantOfDoom> plantOfDooms;
	private Array<RangedEnemy> rangedEnemies;
	private Array<RangedEnemyBullet> rangedEnemyBullets;
	private Array<TheShell> theShells;
	public Array<Ghost> ghosts; // TODO make getter and setter instead
	private Array<RedColorBucket> redColorBuckets;
	private Array<OrangeColorBucket> orangeColorBuckets;
	private Array<YellowColorBucket> yellowColorBuckets;
	private Array<GreenColorBucket> greenColorBuckets;
	private Array<BlueColorBucket> blueColorBuckets;
	private Array<PinkColorBucket> pinkColorBuckets;
	private Array<Checkpoint> checkpoints;
	private Array<RedJumppad> redJumppads;
	private Array<OrangeJumppad> orangeJumppads;
	private Array<YellowJumppad> yellowJumppads;
	private Array<GreenJumppad> greenJumppads;
	private Array<BlueJumppad> blueJumppads;
	private Array<PinkJumppad> pinkJumppads;
	private Array<RedKey> redKeys;
	private Array<OrangeKey> orangeKeys;
	private Array<YellowKey> yellowKeys;
	private Array<GreenKey> greenKeys;
	private Array<BlueKey> blueKeys;
	private Array<PinkKey> pinkKeys;
	private Array<RedKeyblock> redKeyblocks;
	private Array<OrangeKeyblock> orangeKeyblocks;
	private Array<YellowKeyblock> yellowKeyblocks;
	private Array<GreenKeyblock> greenKeyblocks;
	private Array<BlueKeyblock> blueKeyblocks;
	private Array<PinkKeyblock> pinkKeyblocks;
	private Array<FallingRock> fallingRocks;
	private Array<Doomfist> doomfists;
	private Array<ThePipeBall> thePipeBalls;
	private Array<ThePipeOver> thePipeOver;
	private Array<ThePipeUnder> thePipeUnder;
	private Array<Reaper> reapers;
	private Array<RedMovingBlock> redMovingBlocks;
	private Array<OrangeMovingBlock> orangeMovingBlocks;
	private Array<YellowMovingBlock> yellowMovingBlocks;
	private Array<GreenMovingBlock> greenMovingBlocks;
	private Array<BlueMovingBlock> blueMovingBlocks;
	private Array<PinkMovingBlock> pinkMovingBlocks;
	private Array<SpikeBall> spikeBalls;
	private Array<HeartPowerup> heartPowerups;
	private Array<SpeedPowerup> speedPowerups;
	private Array<JumpPowerup> jumpPowerups;
	private Array<TempHealthPowerup> tempHealthPowerups;
	private Array<Door> doors;
	private Array<Boss> bosses;
	private Array<BossBullet> bossBullets;
	
	private SpriteBatch sb;
	private Texture tilemap_overlay;
	private Texture tilemap_behind;

	private Viewport vw;
	//private Viewport vw2;
	private OrthographicCamera cam;
	public Shake shake;

	private float xLayer1, xLayer2, xLayer3, xLayer4;
	private float cloudDistanceTraveled = 0;
	
	private int inAirTimer = 0;
	private boolean jumpedFromJumppad = false;

	private ParticleEffect red, orange, yellow, green, blue, pink;
	private boolean renderPlayerDeathEffect = false;
	
	private boolean show;
	
	private boolean isPlayerFast = false;
	private int jumpsLeft = 2;
	private boolean canDoubleJump = false;
	
	private String specialLevel;

	private int debug;
	private boolean noDeathMode;
	private Random r;

    private Preferences prefs2;

	private boolean isBossAlive = true;

	// Ghost Death Particle
	private ParticleEffect ghostDeath;
	private ParticleEffectPool ghostDeathPool;
	private Array<ParticleEffectPool.PooledEffect> ghostDeathEffects;

	//Red Keyblock Particle
	private ParticleEffect redKeyBlockParticle;
	private ParticleEffectPool redKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> redKeyBlockParticleEffects;

	//Orange Keyblock Particle
	private ParticleEffect orangeKeyBlockParticle;
	private ParticleEffectPool orangeKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> orangeKeyBlockParticleEffects;

	//Yellow Keyblock Particle
	private ParticleEffect yellowKeyBlockParticle;
	private ParticleEffectPool yellowKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> yellowKeyBlockParticleEffects;

	//Green Keyblock Particle
	private ParticleEffect greenKeyBlockParticle;
	private ParticleEffectPool greenKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> greenKeyBlockParticleEffects;

	//Blue Keyblock Particle
	private ParticleEffect blueKeyBlockParticle;
	private ParticleEffectPool blueKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> blueKeyBlockParticleEffects;

	//Pink Keyblock Particle
	private ParticleEffect pinkKeyBlockParticle;
	private ParticleEffectPool pinkKeyBlockParticlePool;
	private Array<ParticleEffectPool.PooledEffect> pinkKeyBlockParticleEffects;

	//Evil-E Death Particle
	private ParticleEffect evileDeathParticle;
	private ParticleEffectPool evileDeathParticlePool;
	private Array<ParticleEffectPool.PooledEffect> evileDeathParticleEffects;

	//Dark Evil-E Death Particle
	private ParticleEffect darkEvileDeathParticle;
	private ParticleEffectPool darkEvileDeathParticlePool;
	private Array<ParticleEffectPool.PooledEffect> darkEvileDeathParticleEffects;

	//Red Colorbucket Particle
	private ParticleEffect redColorBucketParticle;
	private ParticleEffectPool redColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> redColorBucketParticleEffects;

	//Orange Colorbucket Particle
	private ParticleEffect orangeColorBucketParticle;
	private ParticleEffectPool orangeColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> orangeColorBucketParticleEffects;

	//Yellow Colorbucket Particle
	private ParticleEffect yellowColorBucketParticle;
	private ParticleEffectPool yellowColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> yellowColorBucketParticleEffects;

	//Green Colorbucket Particle
	private ParticleEffect greenColorBucketParticle;
	private ParticleEffectPool greenColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> greenColorBucketParticleEffects;

	//Blue Colorbucket Particle
	private ParticleEffect blueColorBucketParticle;
	private ParticleEffectPool blueColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> blueColorBucketParticleEffects;

	//Pink Colorbucket Particle
	private ParticleEffect pinkColorBucketParticle;
	private ParticleEffectPool pinkColorBucketParticlePool;
	private Array<ParticleEffectPool.PooledEffect> pinkColorBucketParticleEffects;

	//Reaper Death Particle
	private ParticleEffect reaperDeathParticle;
	private ParticleEffectPool reaperDeathParticlePool;
	private Array<ParticleEffectPool.PooledEffect> reaperDeathParticleEffects;

	public Play(Main game, float playerX, float playerY, int playerColor, String specialLevel, int startCoins) {
		debug = 0;
		noDeathMode = false;
		r = new Random();

		prefs2 = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.options");

		show = true;
		this.game = game;
		shake = new Shake(60);
		sb = new SpriteBatch();
		
		this.specialLevel = specialLevel;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		health = game.getHealth();

		// Set up Box2D
		world = new World(new Vector2(0, -20.81f), true);
		cl = new MyContactListener(game, this);
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
	
		createPlayer(playerX, playerY);
		createTiles();
		createCoins();
		createEvilE();
		createDarkEvilE();
		createRangedEnemies();
		createPlantOfDooms();
		createTheShells();
		createGhosts();
		createFallingRocks();
		createDoomfists();
		createThePipeBalls();
		createThePipeOver();
		createThePipeUnder();
		createReapers();
		createRedColorBuckets();
		createOrangeColorBuckets();
		createYellowColorBuckets();
		createGreenColorBuckets();
		createBlueColorBuckets();
		createPinkColorBuckets();
		createCheckpoints();
		createRedJumppads();
		createOrangeJumppads();
		createYellowJumppads();
		createGreenJumppads();
		createBlueJumppads();
		createPinkJumppads();
		createRedKeys();
		createRedKeyblocks();
		createOrangeKeys();
		createOrangeKeyblocks();
		createYellowKeys();
		createYellowKeyblocks();
		createGreenKeys();
		createGreenKeyblocks();
		createBlueKeys();
		createBlueKeyblocks();
		createPinkKeys();
		createPinkKeyblocks();
		createRedMovingBlocks();
		createOrangeMovingBlocks();
		createYellowMovingBlocks();
		createGreenMovingBlocks();
		createBlueMovingBlocks();
		createPinkMovingBlocks();
		createSpikeBalls();
		createHeartPowerups();
		createSpeedPowerups();
		createJumpPowerups();
		createTempHealthPowerups();
		createDoors();
		createBosses();
		
		player.setNumCoins(startCoins);

		// Setup textures and HUD
		hud = new HUD(player, this, game);
		
		game.mountains_layer1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		game.mountains_layer2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		game.mountains_layer3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		game.mountains_layer4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		game.boss_level_bg_1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// set up viewport
		if (Gdx.graphics.getWidth() == 1366){
			vw = new FitViewport(1366, 768, cam);
		    //vw2 = new FitViewport(1280, 720, game.getHudCam());
		}
		else{
			vw = new FitViewport(1280, 720, cam);
		}

		vw.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
				
		//setup player  
		setPlayerColor(playerColor);
		setColor(playerColor);
		
		//setup time
		startTimer();
		
		if(game.getTime() <= 0){
			// TODO Fix times
			switch (game.level) {
			case 1:
				game.setTime(120 + 1); // set to correct time
				break;
			case 2:
				game.setTime(120 + 1); // set to correct time
				break;
			case 3:
				game.setTime(140 + 1); // set to correct time
				break;
			case 4:
				game.setTime(170 + 1); // set to correct time
				break;
			case 5:
				game.setTime(180 + 1); // set to correct time
				break;
			case 6:
				game.setTime(190 + 1); // set to correct time
				break;
			case 7:
				game.setTime(200 + 1); // set to correct time
				break;
			case 8:
				game.setTime(210 + 1); // set to correct time
				break;
			case 9:
				game.setTime(220 + 1); // set to correct time
				break;
			case 10:
				game.setTime(230 + 1); // set to correct time
				break;
			case 11:
				game.setTime(240 + 1); // set to correct time
				break;
			case 12:
				game.setTime(250 + 1); // set to correct time
				break;
			case 13:
				game.setTime(260 + 1); // set to correct time
				break;
			case 14:
				game.setTime(270 + 1); // set to correct time
				break;
			case 15:
				game.setTime(280 + 1); // set to correct time
				break;
			case 16:
				game.setTime(290 + 1); // set to correct time
				break;
			case 17:
				game.setTime(300 + 1); // set to correct time
				break;
			case 18:
				game.setTime(310 + 1); // set to correct time
				break;
			case 19:
				game.setTime(320 + 1); // set to correct time
				break;
			case 20:
				game.setTime(500 + 1); // set to correct time
				break;
			}
			
			System.out.println("Play screen initialized.");	
		}
		
		//set up particles
		red = new ParticleEffect();
		red.load(Gdx.files.internal("data/particles/red.gdx"), Gdx.files.internal("data/particles"));
		red.start();
		orange = new ParticleEffect();
		orange.load(Gdx.files.internal("data/particles/orange.gdx"), Gdx.files.internal("data/particles"));
		orange.start();
		yellow = new ParticleEffect();
		yellow.load(Gdx.files.internal("data/particles/yellow.gdx"), Gdx.files.internal("data/particles"));
		yellow.start();
		green = new ParticleEffect();
		green.load(Gdx.files.internal("data/particles/green.gdx"), Gdx.files.internal("data/particles"));
		green.start();
		blue = new ParticleEffect();
		blue.load(Gdx.files.internal("data/particles/blue.gdx"), Gdx.files.internal("data/particles"));
		blue.start();
		pink = new ParticleEffect();
		pink.load(Gdx.files.internal("data/particles/pink.gdx"), Gdx.files.internal("data/particles"));
		pink.start();
		
		rangedEnemyBullets = new Array<RangedEnemyBullet>();
		bossBullets = new Array<BossBullet>();
		game.generic_bg_music.setVolume(0.17f);
		game.generic_bg_music.setLooping(true);
		game.mysterious_magic_bg_music.setVolume(0.24f);
		game.mysterious_magic_bg_music.setLooping(true);
		game.boss_fight_music.setVolume(0.3f);
		game.boss_fight_music.setLooping(true);
		
		if(game.getSoundSetting() && game.level == 20){
			if(specialLevel == "boss"){
				game.boss_fight_music.play();
			}else{
				game.mysterious_magic_bg_music.play();
			}
		}else if(game.getSoundSetting()){
			game.generic_bg_music.play();
		}
		
		game.tictac.setVolume(0.2f);

		// Ghost Death Effect
		ghostDeath = new ParticleEffect();
		ghostDeath.load(Gdx.files.internal("data/particles/ghost_death.gdx"), Gdx.files.internal("data/particles"));
		ghostDeath.start();

		ghostDeathPool = new ParticleEffectPool(ghostDeath, 0, 10);
		ghostDeathEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Red Keyblock Effect
		redKeyBlockParticle = new ParticleEffect();
		redKeyBlockParticle.load(Gdx.files.internal("data/particles/red_keyblock.gdx"), Gdx.files.internal("data/particles"));
		redKeyBlockParticle.start();
		redKeyBlockParticlePool = new ParticleEffectPool(redKeyBlockParticle, 0, 50);
		redKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Orange Keyblock Effect
		orangeKeyBlockParticle = new ParticleEffect();
		orangeKeyBlockParticle.load(Gdx.files.internal("data/particles/orange_keyblock.gdx"), Gdx.files.internal("data/particles"));
		orangeKeyBlockParticle.start();
		orangeKeyBlockParticlePool = new ParticleEffectPool(orangeKeyBlockParticle, 0, 50);
		orangeKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Yellow Keyblock Effect
		yellowKeyBlockParticle = new ParticleEffect();
		yellowKeyBlockParticle.load(Gdx.files.internal("data/particles/yellow_keyblock.gdx"), Gdx.files.internal("data/particles"));
		yellowKeyBlockParticle.start();
		yellowKeyBlockParticlePool = new ParticleEffectPool(yellowKeyBlockParticle, 0, 50);
		yellowKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Green Keyblock Effect
		greenKeyBlockParticle = new ParticleEffect();
		greenKeyBlockParticle.load(Gdx.files.internal("data/particles/green_keyblock.gdx"), Gdx.files.internal("data/particles"));
		greenKeyBlockParticle.start();
		greenKeyBlockParticlePool = new ParticleEffectPool(greenKeyBlockParticle, 0, 50);
		greenKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Blue Keyblock Effect
		blueKeyBlockParticle = new ParticleEffect();
		blueKeyBlockParticle.load(Gdx.files.internal("data/particles/blue_keyblock.gdx"), Gdx.files.internal("data/particles"));
		blueKeyBlockParticle.start();
		blueKeyBlockParticlePool = new ParticleEffectPool(blueKeyBlockParticle, 0, 50);
		blueKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Pink Keyblock Effect
		pinkKeyBlockParticle = new ParticleEffect();
		pinkKeyBlockParticle.load(Gdx.files.internal("data/particles/pink_keyblock.gdx"), Gdx.files.internal("data/particles"));
		pinkKeyBlockParticle.start();
		pinkKeyBlockParticlePool = new ParticleEffectPool(pinkKeyBlockParticle, 0, 50);
		pinkKeyBlockParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Evil-E Death Effect
		evileDeathParticle = new ParticleEffect();
		evileDeathParticle.load(Gdx.files.internal("data/particles/evile_death.gdx"), Gdx.files.internal("data/particles"));
		evileDeathParticle.start();
		evileDeathParticlePool = new ParticleEffectPool(evileDeathParticle, 0, 5);
		evileDeathParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Dark Evil-E Death Effect
		darkEvileDeathParticle = new ParticleEffect();
		darkEvileDeathParticle.load(Gdx.files.internal("data/particles/dark_evile_death.gdx"), Gdx.files.internal("data/particles"));
		darkEvileDeathParticle.start();
		darkEvileDeathParticlePool = new ParticleEffectPool(darkEvileDeathParticle, 0, 5);
		darkEvileDeathParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Red Color Bucket Effect
		redColorBucketParticle = new ParticleEffect();
		redColorBucketParticle.load(Gdx.files.internal("data/particles/red_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		redColorBucketParticle.start();
		redColorBucketParticlePool = new ParticleEffectPool(redColorBucketParticle, 0, 5);
		redColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Orange Color Bucket Effect
		orangeColorBucketParticle = new ParticleEffect();
		orangeColorBucketParticle.load(Gdx.files.internal("data/particles/orange_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		orangeColorBucketParticle.start();
		orangeColorBucketParticlePool = new ParticleEffectPool(orangeColorBucketParticle, 0, 5);
		orangeColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Yellow Color Bucket Effect
		yellowColorBucketParticle = new ParticleEffect();
		yellowColorBucketParticle.load(Gdx.files.internal("data/particles/yellow_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		yellowColorBucketParticle.start();
		yellowColorBucketParticlePool = new ParticleEffectPool(yellowColorBucketParticle, 0, 5);
		yellowColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Green Color Bucket Effect
		greenColorBucketParticle = new ParticleEffect();
		greenColorBucketParticle.load(Gdx.files.internal("data/particles/green_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		greenColorBucketParticle.start();
		greenColorBucketParticlePool = new ParticleEffectPool(greenColorBucketParticle, 0, 5);
		greenColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Blue Color Bucket Effect
		blueColorBucketParticle = new ParticleEffect();
		blueColorBucketParticle.load(Gdx.files.internal("data/particles/blue_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		blueColorBucketParticle.start();
		blueColorBucketParticlePool = new ParticleEffectPool(blueColorBucketParticle, 0, 5);
		blueColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Pink Color Bucket Effect
		pinkColorBucketParticle = new ParticleEffect();
		pinkColorBucketParticle.load(Gdx.files.internal("data/particles/pink_colorbucket.gdx"), Gdx.files.internal("data/particles"));
		pinkColorBucketParticle.start();
		pinkColorBucketParticlePool = new ParticleEffectPool(pinkColorBucketParticle, 0, 5);
		pinkColorBucketParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Reaper Death Effect
		reaperDeathParticle = new ParticleEffect();
		reaperDeathParticle.load(Gdx.files.internal("data/particles/reaper_death.gdx"), Gdx.files.internal("data/particles"));
		reaperDeathParticle.start();
		reaperDeathParticlePool = new ParticleEffectPool(reaperDeathParticle, 0, 5);
		reaperDeathParticleEffects = new Array<ParticleEffectPool.PooledEffect>();
	}
	
	private Timer.Task countdown = new Timer.Task() {
	    public void run() {
	       game.setTime(game.getTime() - 1);
	    }
	};
	
	private void startTimer(){
	    Timer.schedule(countdown, 1, 1);
	} 

	public void doorWarp(String location){
		System.out.println(location);
		
		game.setHealth(1);
		game.setTempHealth(0);
		game.mysterious_magic_bg_music.stop();
		game.tictac.stop();
		cantDoStuff = true;
		countdown.cancel();

		game.teleport_sound.play();
		
		game.setScreenWithFade(new Play(game, 816/B2DVars.PPM, 300/B2DVars.PPM, 1, "boss", player.getNumCoins()), 2.4f);
	}

	//<editor-fold desc="[FOLDING REGION]: handleInput(), update(), removeBodies(), render()">
	private void handleInput() {
		Gdx.graphics.setTitle("Color Course | FPS: " + Gdx.graphics.getFramesPerSecond());
		
		float velocity;
		if(isPlayerFast){
			velocity = 5f;
		}else velocity = 4f;
		
		if (!cantDoStuff && isBossAlive) {
			if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.getBody().setLinearVelocity(-velocity, player.getBody().getLinearVelocity().y);
			} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player.getBody().setLinearVelocity(velocity, player.getBody().getLinearVelocity().y);
			} else {
				if(!cl.isPlayerOnGround()){
					if(player.getBody().getLinearVelocity().x > 0){
						//moving right
						player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x - 0.5f, player.getBody().getLinearVelocity().y);
					}else if(player.getBody().getLinearVelocity().x < 0){
						//moving left
						player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x + 0.5f, player.getBody().getLinearVelocity().y);
					}
					
					if(player.getBody().getLinearVelocity().x > -.01f && player.getBody().getLinearVelocity().x < .01f){
						player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
					}
				}else {
					player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
				}
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.UP)) {
				if (inAirTimer <= 2 && player.getBody().getLinearVelocity().y <= 0) {
					if(onJumpPad){
						//Player is jumping on a jumppad
						player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 0));
						player.getBody().applyLinearImpulse(new Vector2(0, 14.5f), player.getBody().getWorldCenter(), true);
						if(game.getSoundSetting())game.woosh.play(); // Play the jumping-on-jumppad sound effect
						jumpedFromJumppad = true;
						onJumpPad = false;
					}
				}
			}
			
			if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)) {
				if (player.getBody().getLinearVelocity().y <= 0) {
					if(!onJumpPad){
						if(inAirTimer <= 2 && jumpsLeft == 2){
							//player is jumping normally
							player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 0));
							player.getBody().applyLinearImpulse(new Vector2(0, 8.5f), player.getBody().getWorldCenter(), true);
							jumpedFromJumppad = false;
							if(canDoubleJump)jumpsLeft = 1;
							else jumpsLeft = 0;
						}else if(jumpsLeft == 1){
							//player is jumping normally
							player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 0));
							player.getBody().applyLinearImpulse(new Vector2(0, 8.5f), player.getBody().getWorldCenter(), true);
							jumpedFromJumppad = false;
							jumpsLeft = 0;
						}
					}
				}
			}else if(player.getBody().getLinearVelocity().y > 0 && !onJumpPad && !jumpedFromJumppad) {
				player.getBody().applyLinearImpulse(new Vector2(0, -.3f), player.getBody().getWorldCenter(), true);
			}

			if(player.getBody().getLinearVelocity().y > 0 && jumpedFromJumppad){
				switch(playerColor){
					case 1:
						ParticleEffectPool.PooledEffect effect = redKeyBlockParticlePool.obtain();
						effect.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						redKeyBlockParticleEffects.add(effect);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect.start();
						break;
					case 2:
						ParticleEffectPool.PooledEffect effect2 = orangeKeyBlockParticlePool.obtain();
						effect2.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						orangeKeyBlockParticleEffects.add(effect2);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect2.start();
						break;
					case 3:
						ParticleEffectPool.PooledEffect effect3 = yellowKeyBlockParticlePool.obtain();
						effect3.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						yellowKeyBlockParticleEffects.add(effect3);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect3.start();
						break;
					case 4:
						ParticleEffectPool.PooledEffect effect4 = greenKeyBlockParticlePool.obtain();
						effect4.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						greenKeyBlockParticleEffects.add(effect4);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect4.start();
						break;
					case 5:
						ParticleEffectPool.PooledEffect effect5 = blueKeyBlockParticlePool.obtain();
						effect5.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						blueKeyBlockParticleEffects.add(effect5);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect5.start();
						break;
					case 6:
						ParticleEffectPool.PooledEffect effect6 = pinkKeyBlockParticlePool.obtain();
						effect6.setPosition(player.getBody().getPosition().x*PPM, player.getBody().getPosition().y*PPM);
						pinkKeyBlockParticleEffects.add(effect6);
						System.out.println(player.getBody().getPosition().x*PPM+ "  |  " + player.getBody().getPosition().y*PPM);
						effect6.start();
						break;
				}
			}

		}
				
		// jump timer
		if(cl.isPlayerOnGround()){
			inAirTimer = 0;
		}else inAirTimer++;
	}

	public void update(float delta) {
		// [Coins -> Temp Health] Mechanic
		if((player.getNumCoins() + game.getTotalCoins()) >= 200){

			if(game.getTempHealth() < 2 && specialLevel != "boss")  {
				// Logic
				int numCoins = player.getNumCoins();
				int totalCoins = game.getTotalCoins();

				if (numCoins >= 200) {
					numCoins -= 200;
				} else {
					numCoins -= 200;
					totalCoins -= -numCoins;
					numCoins = 0;
				}

				// Action
				player.setNumCoins(numCoins);
				game.setTotalCoins(totalCoins);
				game.setTempHealth(game.getTempHealth()+1);

				// Sound
				game.temp_heart_powerup_sound.play();
			}
		}

		if(show){
			// shake update
			shake.update(Gdx.graphics.getDeltaTime(), cam, new Vector2(cam.position.x, cam.position.y));
			
			removeBodies();
			
	    	handleInput();
			for (int i = 0; i < eviles.size; i++) {
				Body b = eviles.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				eviles.get(i).update(delta);
			}
			for (int i = 0; i < reapers.size; i++) {
				Body b = reapers.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				reapers.get(i).update(delta);
			}
			for (int i = 0; i < darkEviles.size; i++) {
				Body b = darkEviles.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				darkEviles.get(i).update(delta);
			}
			for (int i = 0; i < rangedEnemies.size; i++) {
				Body b = rangedEnemies.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				rangedEnemies.get(i).update(delta);
			}
			for (int i = 0; i < rangedEnemyBullets.size; i++) {
				Body b = rangedEnemyBullets.get(i).getBody();
				if(!b.isActive()) b.setActive(true);
				rangedEnemyBullets.get(i).update(delta);
			}
			for (int i = 0; i < theShells.size; i++) {
				Body b = theShells.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				theShells.get(i).update(delta);
			}
			for (int i = 0; i < doomfists.size; i++) {
				Body b = doomfists.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				doomfists.get(i).update(delta);
			}
			for (int i = 0; i < fallingRocks.size; i++) {
				fallingRocks.get(i).update(delta);
			}
			for (int i = 0; i < ghosts.size; i++) {
				Body b = ghosts.get(i).getBody();
				if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
					b.setActive(true);
				}else b.setActive(false);
				ghosts.get(i).update(delta);
			}

			for (int i = 0; i < redMovingBlocks.size; i++) {
				redMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < orangeMovingBlocks.size; i++) {
				orangeMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < yellowMovingBlocks.size; i++) {
				yellowMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < greenMovingBlocks.size; i++) {
				greenMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < blueMovingBlocks.size; i++) {
				blueMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < pinkMovingBlocks.size; i++) {
				pinkMovingBlocks.get(i).update(delta);
			}
			for (int i = 0; i < spikeBalls.size; i++) {
				spikeBalls.get(i).update(delta);
			}
			for (int i = 0; i < doors.size; i++) {
				doors.get(i).update(delta);
			}
			for (int i = 0; i < bosses.size; i++) {
				bosses.get(i).update(delta);
			}
			for (int i = 0; i < bossBullets.size; i++) {
				bossBullets.get(i).update(delta);
			}
	
			// die by fall
			if (player.getPosition().y < -2 && !cantDoStuff) {
				if(game.getSoundSetting() && health > 1)game.crash.play(1.0f);
				die();
				Gdx.app.log(Log.EVENT, "Died by falling into the void");
		    }
			
			//die by time
			if(game.getTime() < 1 && !cantDoStuff){
				countdown.cancel();
				die();
				Gdx.app.log(Log.EVENT, "Died by running out of time");
			}
			
			// set cam to follow player & fix cam wrong location for 1366x768
			if(specialLevel.equals("no")) {
					if(!renderPlayerDeathEffect){
					if (Gdx.graphics.getWidth() == 1366) cam.position.lerp(new Vector3(player.getPosition().x * PPM, Main.V_HEIGHT / 2 + 24, 0), 0.2f);
					else cam.position.lerp(new Vector3(player.getPosition().x * PPM, Main.V_HEIGHT / 2, 0), 0.2f);
				}
			}else if(specialLevel.equals("boss")) {
				if(!renderPlayerDeathEffect){
					if (Gdx.graphics.getWidth() == 1366) cam.position.lerp(new Vector3(1252, Main.V_HEIGHT / 2 + 24, 0), 0.2f);
					else cam.position.lerp(new Vector3(1252, Main.V_HEIGHT / 2, 0), 0.2f);
				}
			}
			// set box2D camera to follow regular camera
			b2dCam.position.x = cam.position.x / PPM;
			b2dCam.position.y = cam.position.y / PPM;
	
			cam.update();
			game.getHudCam().update();
			b2dCam.update();

			
			if(game.getTime() <= 10 && !game.tictac.isPlaying() && !cantDoStuff){
				if(game.getSoundSetting())game.tictac.play();
			}
		}
	}
	
	private void removeBodies(){
		// remove bodies
		Array<Body> bodies = cl.getBodiesToRemove();

		for (int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			Object o = b.getUserData();

			if (o instanceof Coin) {
				coins.removeValue((Coin) b.getUserData(), true);
				world.destroyBody(b);
				player.setNumCoins(player.getNumCoins() + ((Coin) o).getValue());
				if(((Coin) o).getValue() == 1){
					if(game.getSoundSetting())game.coin_collect.play(1.0f);
				}else{
					if(game.getSoundSetting())game.emerald_collect.play(1.0f);
				}
				Gdx.app.log(Log.COIN_COLLECTION, Integer.toString(player.getNumCoins()));
			} else if (o instanceof RedColorBucket) {
				ParticleEffectPool.PooledEffect effect = redColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				redColorBucketParticleEffects.add(effect);
				effect.start();
				redColorBuckets.removeValue((RedColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof OrangeColorBucket) {
				ParticleEffectPool.PooledEffect effect = orangeColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				orangeColorBucketParticleEffects.add(effect);
				effect.start();
				orangeColorBuckets.removeValue((OrangeColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof YellowColorBucket) {
				ParticleEffectPool.PooledEffect effect = yellowColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				yellowColorBucketParticleEffects.add(effect);
				effect.start();
				yellowColorBuckets.removeValue((YellowColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof GreenColorBucket) {
				ParticleEffectPool.PooledEffect effect = greenColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				greenColorBucketParticleEffects.add(effect);
				effect.start();
				greenColorBuckets.removeValue((GreenColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof BlueColorBucket) {
				ParticleEffectPool.PooledEffect effect = blueColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				blueColorBucketParticleEffects.add(effect);
				effect.start();
				blueColorBuckets.removeValue((BlueColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof PinkColorBucket) {
				ParticleEffectPool.PooledEffect effect = pinkColorBucketParticlePool.obtain();
				effect.setPosition(b.getPosition().x*PPM, b.getPosition().y*PPM);
				pinkColorBucketParticleEffects.add(effect);
				effect.start();
				pinkColorBuckets.removeValue((PinkColorBucket) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.color_bucket.play(1.0f);
			} else if (o instanceof EvilE) {
				eviles.removeValue((EvilE) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 1);
				world.destroyBody(b);
			} else if (o instanceof Reaper) {
				reapers.removeValue((Reaper) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 10);
				world.destroyBody(b);
			} else if (o instanceof DarkEvilE) {
				darkEviles.removeValue((DarkEvilE) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 2);
				world.destroyBody(b);
			} else if (o instanceof Doomfist) {
				doomfists.removeValue((Doomfist) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 5);
				world.destroyBody(b);
			} else if (o instanceof RangedEnemy) {
				rangedEnemies.removeValue((RangedEnemy) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 2);
				world.destroyBody(b);
			} else if (o instanceof Ghost) {
				ghosts.removeValue((Ghost) b.getUserData(), true);
				player.setNumCoins(player.getNumCoins() + 10);
				world.destroyBody(b);
			}else if (o instanceof RangedEnemyBullet) {
				rangedEnemyBullets.removeValue((RangedEnemyBullet) b.getUserData(), true);
				world.destroyBody(b);
			}else if (o instanceof Checkpoint){
				checkpoints.removeValue((Checkpoint) b.getUserData(), true);
				world.destroyBody(b);
			}else if (o instanceof HeartPowerup){
				heartPowerups.removeValue((HeartPowerup) b.getUserData(), true);
				world.destroyBody(b);
			}else if (o instanceof SpeedPowerup){
				speedPowerups.removeValue((SpeedPowerup) b.getUserData(), true);
				world.destroyBody(b);
			}else if (o instanceof JumpPowerup){
				jumpPowerups.removeValue((JumpPowerup) b.getUserData(), true);
				world.destroyBody(b);
			}else if (o instanceof TempHealthPowerup){
				tempHealthPowerups.removeValue((TempHealthPowerup) b.getUserData(), true);
				world.destroyBody(b);
			}
			else if (o instanceof Boss){
				bosses.removeValue((Boss) b.getUserData(), true);
				world.destroyBody(b);
			}
			//red key and keyblock
			else if (o instanceof RedKey){
				for(int ii = 0; ii < redKeyblocks.size; ii++){
					Body bb = redKeyblocks.get(ii).getBody();

					if(((RedKeyblock)bb.getUserData()).id == ((RedKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = redKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						redKeyBlockParticleEffects.add(effect);
						effect.start();
						bodies.add(bb);		
					}
				}
			    redKeys.removeValue((RedKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);
			}else if(o instanceof RedKeyblock) {
				redKeyblocks.removeValue((RedKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
			//orange key and keyblock
			else if (o instanceof OrangeKey){
				for(int ii = 0; ii < orangeKeyblocks.size; ii++){
					Body bb = orangeKeyblocks.get(ii).getBody();
					if(((OrangeKeyblock)bb.getUserData()).id == ((OrangeKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = orangeKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						orangeKeyBlockParticleEffects.add(effect);
						effect.start();
						bodies.add(bb);		
					}
				}
			    orangeKeys.removeValue((OrangeKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);
			}else if(o instanceof OrangeKeyblock) {
				orangeKeyblocks.removeValue((OrangeKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
			//yellow key and keyblock
			else if (o instanceof YellowKey){
				for(int ii = 0; ii < yellowKeyblocks.size; ii++){
					Body bb = yellowKeyblocks.get(ii).getBody();
					if(((YellowKeyblock)bb.getUserData()).id == ((YellowKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = yellowKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						yellowKeyBlockParticleEffects.add(effect);
						effect.start();
						bodies.add(bb);		
					}
				}
			    yellowKeys.removeValue((YellowKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);
			}else if(o instanceof YellowKeyblock) {
				yellowKeyblocks.removeValue((YellowKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
			//green key and keyblock
			else if (o instanceof GreenKey){
				for(int ii = 0; ii < greenKeyblocks.size; ii++){
					Body bb = greenKeyblocks.get(ii).getBody();
					if(((GreenKeyblock)bb.getUserData()).id == ((GreenKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = greenKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						greenKeyBlockParticleEffects.add(effect);
						effect.start();
						bodies.add(bb);		
					}
				}
			    greenKeys.removeValue((GreenKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);	
			}else if(o instanceof GreenKeyblock) {
				greenKeyblocks.removeValue((GreenKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
			//blue key and keyblock
			else if (o instanceof BlueKey){
				for(int ii = 0; ii < blueKeyblocks.size; ii++){
					Body bb = blueKeyblocks.get(ii).getBody();
					if(((BlueKeyblock)bb.getUserData()).id == ((BlueKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = blueKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						blueKeyBlockParticleEffects.add(effect);
						effect.start();

						bodies.add(bb);		
					}
				}
			    blueKeys.removeValue((BlueKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);
			}else if(o instanceof BlueKeyblock) {
				blueKeyblocks.removeValue((BlueKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
			//pink key and keyblock
			else if (o instanceof PinkKey){
				for(int ii = 0; ii < pinkKeyblocks.size; ii++){
					Body bb = pinkKeyblocks.get(ii).getBody();
					if(((PinkKeyblock)bb.getUserData()).id == ((PinkKey)b.getUserData()).id){
						ParticleEffectPool.PooledEffect effect = pinkKeyBlockParticlePool.obtain();
						effect.setPosition(bb.getPosition().x*PPM, bb.getPosition().y*PPM);
						pinkKeyBlockParticleEffects.add(effect);
						effect.start();
						bodies.add(bb);		
					}
				}
			    pinkKeys.removeValue((PinkKey) b.getUserData(), true);
				world.destroyBody(b);
				if(game.getSoundSetting())game.key.play(.7f);
			}else if(o instanceof PinkKeyblock) {
				pinkKeyblocks.removeValue((PinkKeyblock)b.getUserData(), true);
				world.destroyBody(b);
			}
		}
		bodies.clear();
	}

	public void render(float delta) {
		if(show){
			update(delta);
			world.step(Math.min(Gdx.graphics.getDeltaTime(), 0.15f), 6, 2);
			
			if(debug == 0){
				if(game.level == 20){
					Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
				}else {
					Gdx.gl.glClearColor(0.77f, 0.94f, 1, 1);
				}
			}
			else Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
			////////////// Parallax background //////////////
			xLayer1 = player.getBody().getPosition().x * (PPM/7) + cloudDistanceTraveled; //clouds

			if(game.level == 20){
				if(specialLevel.equals("boss")){
					xLayer2 = 100; //wall
				}else {
					xLayer2 = player.getBody().getPosition().x * (PPM/4); //wall
				}
			}
			else xLayer2 = player.getBody().getPosition().x * (PPM/12); //mountains
			
			xLayer3 = player.getBody().getPosition().x * (PPM/8); //pillars
			xLayer4 = player.getBody().getPosition().x * (PPM/4); //grass
			cloudDistanceTraveled += 50f * Gdx.graphics.getDeltaTime();
			
			sb.begin();
			if(debug == 0 && game.level != 20){
				if(Gdx.graphics.getWidth() == 1366 && Gdx.graphics.getHeight() == 768) {
					//1366x768 resolution
					sb.draw(game.mountains_layer1, -43, -24, 1366, 768, (int)xLayer1, -10, 1366, 768, false, false);
					sb.draw(game.mountains_layer2, -43, -24, 1366, 768, (int)xLayer2, -53, 1366, 768, false, false);
					sb.draw(game.mountains_layer3, -43, -24, 1366, 768, (int)xLayer3, -53, 1366, 768, false, false);
					sb.draw(game.mountains_layer4, -43, -24, 1366, 768, (int)xLayer4, -53, 1366, 768, false, false);
				}else {
					//Normal resolution (probably)
					sb.draw(game.mountains_layer1, 0, 0, game.mountains_layer1.getWidth(), game.mountains_layer1.getHeight(), (int)xLayer1, 0, game.mountains_layer1.getWidth(), game.mountains_layer1.getHeight(), false, false);
					sb.draw(game.mountains_layer2, 0, 0, game.mountains_layer2.getWidth(), game.mountains_layer2.getHeight(), (int)xLayer2, 0, game.mountains_layer2.getWidth(), game.mountains_layer2.getHeight(), false, false);
					sb.draw(game.mountains_layer3, 0, 0, game.mountains_layer3.getWidth(), game.mountains_layer3.getHeight(), (int)xLayer3, 0, game.mountains_layer3.getWidth(), game.mountains_layer3.getHeight(), false, false);
					sb.draw(game.mountains_layer4, 0, 0, game.mountains_layer4.getWidth(), game.mountains_layer4.getHeight(), (int)xLayer4, 0, game.mountains_layer4.getWidth(), game.mountains_layer4.getHeight(), false, false);
				}
			}else if(debug == 0){
				//1366x768 resolution
				sb.draw(game.boss_level_bg_1, -43, 0, 1366, 780, (int)xLayer2, -53, 1920, 1080, false, false);
			}else {
				if(Gdx.graphics.getWidth() == 1366 && Gdx.graphics.getHeight() == 768) {
					//Normal resolution (probably)
					sb.draw(game.boss_level_bg_1, 0, 0, 1280, 720, (int)xLayer2, 0, game.boss_level_bg_1.getWidth(), game.boss_level_bg_1.getHeight(), false, false);
				}
			}
			sb.end();
			////////////// End of parallax background //////////////

			// draw box2d world
			if (debug != 0) b2dr.render(world, b2dCam.combined);
			if (debug != 2) {
				sb.setProjectionMatrix(cam.combined);
				sb.begin();
				if(game.level != 20)sb.draw(tilemap_behind, 0, 0);
				sb.end();
				
				// draw tilemap
				sb.setProjectionMatrix(new Matrix4());
				tmr.setView(cam);
				tmr.render();	
				
				sb.setProjectionMatrix(cam.combined);
				
				// draw coins
				for (int i = 0; i < coins.size; i++) {
					Body b = coins.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						coins.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw red color buckets
				for (int i = 0; i < redColorBuckets.size; i++) {
					Body b = redColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						redColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw orange color buckets
				for (int i = 0; i < orangeColorBuckets.size; i++) {
					Body b = orangeColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						orangeColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw yellow color buckets
				for (int i = 0; i < yellowColorBuckets.size; i++) {
					Body b = yellowColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						yellowColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw green color buckets
				for (int i = 0; i < greenColorBuckets.size; i++) {
					Body b = greenColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						greenColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw blue color buckets
				for (int i = 0; i < blueColorBuckets.size; i++) {
					Body b = blueColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						blueColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw pink color buckets
				for (int i = 0; i < pinkColorBuckets.size; i++) {
					Body b = pinkColorBuckets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						pinkColorBuckets.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw evil-e's
				for (int i = 0; i < eviles.size; i++) {
					Body b = eviles.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						eviles.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw the pipe balls
				for (int i = 0; i < thePipeBalls.size; i++) {
					Body b = thePipeBalls.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						thePipeBalls.get(i).render(sb);
					}
				}
				// draw the pipe over
				for (int i = 0; i < thePipeOver.size; i++) {
					Body b = thePipeOver.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						thePipeOver.get(i).render(sb);
					}
				}
				// draw the pipe under
				for (int i = 0; i < thePipeUnder.size; i++) {
					Body b = thePipeUnder.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						thePipeUnder.get(i).render(sb);
					}
				}
				// draw dark evil-e's
				for (int i = 0; i < darkEviles.size; i++) {
					Body b = darkEviles.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						darkEviles.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw dark reapers
				for (int i = 0; i < reapers.size; i++) {
					Body b = reapers.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						reapers.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw doomfists
				for (int i = 0; i < doomfists.size; i++) {
					Body b = doomfists.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						doomfists.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw checkpoints
				for (int i = 0; i < checkpoints.size; i++) {
					Body b = checkpoints.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						checkpoints.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw red jumppads
				for (int i = 0; i < redJumppads.size; i++) {
					Body b = redJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						redJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw orange jumppads
				for (int i = 0; i < orangeJumppads.size; i++) {
					Body b = orangeJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						orangeJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw yellow jumppads
				for (int i = 0; i < yellowJumppads.size; i++) {
					Body b = yellowJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						yellowJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
			    //draw green jumppads
				for (int i = 0; i < greenJumppads.size; i++) {
					Body b = greenJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						greenJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw blue jumppads
				for (int i = 0; i < blueJumppads.size; i++) {
					Body b = blueJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						blueJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw pink jumppads
				for (int i = 0; i < pinkJumppads.size; i++) {
					Body b = pinkJumppads.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						pinkJumppads.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw red keys
				for(int i = 0; i < redKeys.size; i++) {
					Body b = redKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						redKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw red key blocks
				for(int i = 0; i < redKeyblocks.size; i++) {
					Body b = redKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						redKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw orange keys
				for(int i = 0; i < orangeKeys.size; i++) {
					Body b = orangeKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						orangeKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw orange key blocks
				for(int i = 0; i < orangeKeyblocks.size; i++) {
					Body b = orangeKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						orangeKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw yellow keys
				for(int i = 0; i < yellowKeys.size; i++) {
					Body b = yellowKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						yellowKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw yellow key blocks
				for(int i = 0; i < yellowKeyblocks.size; i++) {
					Body b = yellowKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						yellowKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw green keys
				for(int i = 0; i < greenKeys.size; i++) {
					Body b = greenKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						greenKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw green key blocks
				for(int i = 0; i < greenKeyblocks.size; i++) {
					Body b = greenKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						greenKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw blue keys
				for(int i = 0; i < blueKeys.size; i++) {
					Body b = blueKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						blueKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw blue key blocks
				for(int i = 0; i < blueKeyblocks.size; i++) {
					Body b = blueKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						blueKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				//draw pink keys
				for(int i = 0; i < pinkKeys.size; i++) {
					Body b = pinkKeys.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						pinkKeys.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw pink key blocks
				for(int i = 0; i < pinkKeyblocks.size; i++) {
					Body b = pinkKeyblocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						pinkKeyblocks.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw ranged enemies
				for (int i = 0; i < rangedEnemies.size; i++) {
					Body b = rangedEnemies.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						rangedEnemies.get(i).render(sb);
						b.setActive(true);
					}else b.setActive(false);
				}
				// draw ranged enemy bullets
				for (int i = 0; i < rangedEnemyBullets.size; i++) {
					Body b = rangedEnemyBullets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						rangedEnemyBullets.get(i).render(sb);
					}
				}
				// draw plant of dooms
				for (int i = 0; i < plantOfDooms.size; i++) {
					Body b = plantOfDooms.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						plantOfDooms.get(i).render(sb);
					}else{
						game.scissor_sound.stop();
					}
				}
				// draw the shells
				for (int i = 0; i < theShells.size; i++) {
					Body b = theShells.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						theShells.get(i).render(sb);
					}
				}
				// draw the ghosts
				for (int i = 0; i < ghosts.size; i++) {
					Body b = ghosts.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						ghosts.get(i).render(sb);
					}
				}
				// draw the falling rocks
				for (int i = 0; i < fallingRocks.size; i++) {
					Body b = fallingRocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						fallingRocks.get(i).render(sb);
					}
				}
				// draw red moving blocks
				for (int i = 0; i < redMovingBlocks.size; i++) {
					Body b = redMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						redMovingBlocks.get(i).render(sb);
					}
				}
				// draw orange moving blocks
				for (int i = 0; i < orangeMovingBlocks.size; i++) {
					Body b = orangeMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						orangeMovingBlocks.get(i).render(sb);
					}
				}
				// draw yellow moving blocks
				for (int i = 0; i < yellowMovingBlocks.size; i++) {
					Body b = yellowMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						yellowMovingBlocks.get(i).render(sb);
					}
				}
				// draw green moving blocks
				for (int i = 0; i < greenMovingBlocks.size; i++) {
					Body b = greenMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						greenMovingBlocks.get(i).render(sb);
					}
				}
				// draw blue moving blocks
				for (int i = 0; i < blueMovingBlocks.size; i++) {
					Body b = blueMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						blueMovingBlocks.get(i).render(sb);
					}
				}
				// draw pink moving blocks
				for (int i = 0; i < pinkMovingBlocks.size; i++) {
					Body b = pinkMovingBlocks.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						pinkMovingBlocks.get(i).render(sb);
					}
				}
				// draw spike balls
				for (int i = 0; i < spikeBalls.size; i++) {
					Body b = spikeBalls.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						spikeBalls.get(i).render(sb);
					}
				}
				// draw heart powerups
				for (int i = 0; i < heartPowerups.size; i++) {
					Body b = heartPowerups.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						heartPowerups.get(i).render(sb);
					}
				}
				// draw speed powerups
				for (int i = 0; i < speedPowerups.size; i++) {
					Body b = speedPowerups.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						speedPowerups.get(i).render(sb);
					}
				}
				// draw jump powerups
				for (int i = 0; i < jumpPowerups.size; i++) {
					Body b = jumpPowerups.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						jumpPowerups.get(i).render(sb);
					}
				}
				// draw temp health powerups
				for (int i = 0; i < tempHealthPowerups.size; i++) {
					Body b = tempHealthPowerups.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						tempHealthPowerups.get(i).render(sb);
					}
				}
				// draw doors
				for (int i = 0; i < doors.size; i++) {
					Body b = doors.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 9) && b.getPosition().x < (player.getPosition().x + 9)){
						doors.get(i).render(sb);
					}
				}
				// draw bosses
				for (int i = 0; i < bosses.size; i++) {
					Body b = bosses.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 13) && b.getPosition().x < (player.getPosition().x + 13)){
						bosses.get(i).render(sb);
					}
				}
				// draw boss bullets
				for (int i = 0; i < bossBullets.size; i++) {
					Body b = bossBullets.get(i).getBody();
					if(b.getPosition().x > (player.getPosition().x - 13) && b.getPosition().x < (player.getPosition().x + 13)){
						bossBullets.get(i).render(sb);
					}
				}

				// draw player
				player.render(sb);
				
				sb.begin();
				sb.draw(tilemap_overlay, 0, 0);
				sb.end();
				
			}
			
			// Player death effect
			if(renderPlayerDeathEffect){
				sb.setProjectionMatrix(cam.combined);	
				sb.begin();	
				if(((Player)player.getBody().getUserData()).getColor() == 1){
					red.update(delta);
					red.draw(sb, delta);
				}else if(((Player)player.getBody().getUserData()).getColor() == 2){
					orange.update(delta);
					orange.draw(sb, delta);
				}else if(((Player)player.getBody().getUserData()).getColor() == 3){
					yellow.update(delta);
					yellow.draw(sb, delta);
				}else if(((Player)player.getBody().getUserData()).getColor() == 4){
					green.update(delta);
					green.draw(sb, delta);
				}else if(((Player)player.getBody().getUserData()).getColor() == 5){
					blue.update(delta);
					blue.draw(sb, delta);
				}else if(((Player)player.getBody().getUserData()).getColor() == 6){
					pink.update(delta);
					pink.draw(sb, delta);
				}
				sb.end();
			}

			sb.begin();
			for(ParticleEffectPool.PooledEffect effect : ghostDeathEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					ghostDeathEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : redKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					redKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : orangeKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					orangeKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : yellowKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					yellowKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : greenKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					greenKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : blueKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					blueKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : pinkKeyBlockParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					pinkKeyBlockParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : evileDeathParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					evileDeathParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : darkEvileDeathParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					darkEvileDeathParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : redColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					redColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : orangeColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					orangeColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : yellowColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					yellowColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : greenColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					greenColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : blueColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					blueColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : pinkColorBucketParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					pinkColorBucketParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			for(ParticleEffectPool.PooledEffect effect : reaperDeathParticleEffects){
				effect.update(delta);
				effect.draw(sb, delta);
				if(effect.isComplete()){
					reaperDeathParticleEffects.removeValue(effect, true);
					effect.free();
				}
			}
			sb.end();

			// draw hud
			sb.setProjectionMatrix(game.getHudCam().combined);
			hud.render(sb);

			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		}
	}
	//</editor-fold>

	//<editor-fold desc="[FOLDING REGION]: Box2D Create Methods, for example createPlayer()">
	private void createPlayer(float x, float y) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();

		// create player
		bdef.position.set(x, y);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);

		shape.setAsBox(14 / PPM, 14 / PPM);
		fdef.friction = 0f;
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_INTERACTABLE_TRANSPARENT | 
				B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

		body.createFixture(fdef).setUserData("player");

		// foot sensor
		shape.setAsBox(13 / PPM, 5 / PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY; // Is this ok to do?
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("foot");

		player = new Player(body, game, this);
		body.setUserData(player);
		shape.dispose();

	}
	private void createTiles() {
		loader = new TmxMapLoader();
	
		if (specialLevel.equals("boss")){
			tileMap = loader.load("data/levels/worlds/world_1/level_20/boss_part/room/lvl20_boss_part.tmx");
			tilemap_overlay = game.level_19_overlay; // change to correct
			tilemap_behind = game.level_19_behind; // change to correct
		}else {
			switch (game.level) {
			case 1:
				tileMap = loader.load("data/levels/worlds/world_1/level_1/lvl1.tmx");
				int lang = prefs2.getInteger("com.freerangerstudios.colorcourse.options.language", 0); // Will be equal to preference: gamedata

                if (lang == 0) {
                    // English
					tilemap_behind = game.level_1_behind;
                } else if (lang == 1) {
                    // German
					tilemap_behind = game.level_1_behind_german;
                } else if (lang == 2) {
                    // Spanish
					tilemap_behind = game.level_1_behind_spanish;
                } else if (lang == 3) {
                    // Swedish
					tilemap_behind = game.level_1_behind_swedish;
                }

                tilemap_overlay = game.level_1_overlay;
				break;
			case 2:
				tileMap = loader.load("data/levels/worlds/world_1/level_2/lvl2.tmx");
				int lang1 = prefs2.getInteger("com.freerangerstudios.colorcourse.options.language", 0); // Will be equal to preference: gamedata

				if (lang1 == 0) {
					// English
					tilemap_behind = game.level_2_behind;
				} else if (lang1 == 1) {
					// German
					tilemap_behind = game.level_2_behind_german;
				} else if (lang1 == 2) {
					// Spanish
					tilemap_behind = game.level_2_behind_spanish;
				} else if (lang1 == 3) {
					// Swedish
					tilemap_behind = game.level_2_behind_swedish;
				}

				tilemap_overlay = game.level_2_overlay;
				break;
			case 3:
				tileMap = loader.load("data/levels/worlds/world_1/level_3/lvl3.tmx");
				tilemap_overlay = game.level_3_overlay; 
				tilemap_behind = game.level_3_behind; 
				break;
			case 4:
				tileMap = loader.load("data/levels/worlds/world_1/level_4/lvl4.tmx");
				tilemap_overlay = game.level_4_overlay; 
				tilemap_behind = game.level_4_behind; 
				break;
			case 5:
				tileMap = loader.load("data/levels/worlds/world_1/level_5/lvl5.tmx");
				tilemap_overlay = game.level_5_overlay; 
				tilemap_behind = game.level_5_behind; 
				break;
			case 6:
				tileMap = loader.load("data/levels/worlds/world_1/level_6/lvl6.tmx");
				tilemap_overlay = game.level_6_overlay; 
				tilemap_behind = game.level_6_behind;
				break;
			case 7:
				tileMap = loader.load("data/levels/worlds/world_1/level_7/lvl7.tmx");
				tilemap_overlay = game.level_7_overlay;
				tilemap_behind = game.level_7_behind;
				break;
			case 8:
				tileMap = loader.load("data/levels/worlds/world_1/level_8/lvl8.tmx");
				tilemap_overlay = game.level_8_overlay;
				tilemap_behind = game.level_8_behind;
				break;
			case 9:
				tileMap = loader.load("data/levels/worlds/world_1/level_9/lvl9.tmx");
				tilemap_overlay = game.level_9_overlay;
				tilemap_behind = game.level_9_behind;
				break;
			case 10:
				tileMap = loader.load("data/levels/worlds/world_1/level_10/lvl10.tmx");
				tilemap_overlay = game.level_10_overlay;
				tilemap_behind = game.level_10_behind;
				break;	
			case 11:
				tileMap = loader.load("data/levels/worlds/world_1/level_11/lvl11.tmx");
				tilemap_overlay = game.level_11_overlay;
				tilemap_behind = game.level_11_behind;
				break;
			case 12:
				tileMap = loader.load("data/levels/worlds/world_1/level_12/lvl12.tmx");
				tilemap_overlay = game.level_12_overlay;
				tilemap_behind = game.level_12_behind;
				break;
			case 13:
				tileMap = loader.load("data/levels/worlds/world_1/level_13/lvl13.tmx");
				tilemap_overlay = game.level_13_overlay;
				tilemap_behind = game.level_13_behind;
				break;
			case 14:
				tileMap = loader.load("data/levels/worlds/world_1/level_14/lvl14.tmx");
				tilemap_overlay = game.level_14_overlay;
				tilemap_behind = game.level_14_behind;
				break;
			case 15:
				tileMap = loader.load("data/levels/worlds/world_1/level_15/lvl15.tmx");
				tilemap_overlay = game.level_15_overlay;
				tilemap_behind = game.level_15_behind;
				break;
			case 16:
				tileMap = loader.load("data/levels/worlds/world_1/level_16/lvl16.tmx");
				tilemap_overlay = game.level_16_overlay;
				tilemap_behind = game.level_16_behind;
				break;
			case 17:
				tileMap = loader.load("data/levels/worlds/world_1/level_17/lvl17.tmx");
				tilemap_overlay = game.level_17_overlay;
				tilemap_behind = game.level_17_behind;
				break;
			case 18:
				tileMap = loader.load("data/levels/worlds/world_1/level_18/lvl18.tmx");
				tilemap_overlay = game.level_18_overlay;
				tilemap_behind = game.level_18_behind;
				break;
			case 19:
				tileMap = loader.load("data/levels/worlds/world_1/level_19/lvl19.tmx");
				tilemap_overlay = game.level_19_overlay;
				tilemap_behind = game.level_19_behind;
				break;
			case 20:
				tileMap = loader.load("data/levels/worlds/world_1/level_20/level_part/lvl20_part_1.tmx");
				tilemap_overlay = new Texture(Gdx.files.internal("data/levels/worlds/world_1/level_20/blank.png"));
				tilemap_behind = new Texture(Gdx.files.internal("data/levels/worlds/world_1/level_20/blank.png"));
				// did not work for some weird reason.... ...fix!!!!
				break;
			}		
		}
		tilemap_overlay.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tilemap_behind.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		tmr = new OrthogonalTiledMapRenderer(tileMap);
		cam.update();

		// get the size of the tiles
		tileSize = tileMap.getProperties().get("tilewidth", Integer.class);

		// create box2d bodies for all tiledmap layers
		TiledMapTileLayer layer;

		layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
		createLayer(layer, B2DVars.BIT_BLOCKS, "red", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("orange");
		createLayer(layer, B2DVars.BIT_BLOCKS, "orange", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("yellow");
		createLayer(layer, B2DVars.BIT_BLOCKS, "yellow", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
		createLayer(layer, B2DVars.BIT_BLOCKS, "green", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
		createLayer(layer, B2DVars.BIT_BLOCKS, "blue", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("pink");
		createLayer(layer, B2DVars.BIT_BLOCKS, "pink", false, false);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("normalblocks");
		createLayer(layer, B2DVars.BIT_BLOCKS, "normalblocks", false, false);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("deathblocks");
		createLayer(layer, B2DVars.BIT_BLOCKS, "deathblocks", false, false);

		layer = (TiledMapTileLayer) tileMap.getLayers().get("flag");
		createLayer(layer, B2DVars.BIT_BLOCKS, "flag", true, false);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("blockers");
		createLayer(layer, B2DVars.BIT_BLOCKER, "blockers", true, false);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("spikes");
		createLayer(layer, B2DVars.BIT_BLOCKS, "spikes", false, true);
	}
	private void createLayer(TiledMapTileLayer layer, short bits, String path, boolean ghost, boolean triangle) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		// go through all cells in the layer
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				// get cell
				Cell cell = layer.getCell(col, row);

				// check if cell exists
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;

				// create body + fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + .5f) * tileSize / PPM, (row + .5f) * tileSize / PPM);

				ChainShape cs = new ChainShape();
				Vector2[] v;
				
				if(triangle){
					v = new Vector2[4];
					v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM); // down left
					v[1] = new Vector2(0 / PPM, tileSize / 2 / PPM); // up
					v[2] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM); // down right
					v[3] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM); // down left (close chain)
				}else {
					v = new Vector2[5];
					v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
					v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
					v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
					v[3] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM);
					v[4] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				}

				cs.createChain(v);
				fdef.shape = cs;
				
				if(specialLevel.equals("boss")) fdef.friction = 0.1f;
				else fdef.friction = 0f;
				fdef.restitution = 0f;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_BLOCKS;

				fdef.isSensor = ghost;
				
				world.createBody(bdef).createFixture(fdef).setUserData(path);
				cs.dispose();
			}
		}
	}
	private void createEvilE() {
		eviles = new Array<EvilE>();
		MapLayer layer = tileMap.getLayers().get("evile");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("evile");

			// player collision/death sensor
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("evile_death_sensor");
			
			// left sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(-13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("evile_left_sensor");
			
			// right sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY  | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("evile_right_sensor");
			
			// destroy sensor
			shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("evile_remove");
			
			EvilE c = new EvilE(body, game);
			eviles.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createRedMovingBlocks() {
		redMovingBlocks = new Array<RedMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_red");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("red_moving_block");
			
			RedMovingBlock c = new RedMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			redMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createSpikeBalls() {
		spikeBalls = new Array<SpikeBall>();
		MapLayer layer = tileMap.getLayers().get("moving_spikeball");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("spike_ball");
			
			SpikeBall c = new SpikeBall(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			spikeBalls.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createOrangeMovingBlocks() {
		orangeMovingBlocks = new Array<OrangeMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_orange");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("orange_moving_block");
			
			OrangeMovingBlock c = new OrangeMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			orangeMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createYellowMovingBlocks() {
		yellowMovingBlocks = new Array<YellowMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_yellow");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("yellow_moving_block");
			
			YellowMovingBlock c = new YellowMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			yellowMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createGreenMovingBlocks() {
		greenMovingBlocks = new Array<GreenMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_green");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("green_moving_block");
			
			GreenMovingBlock c = new GreenMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			greenMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createBlueMovingBlocks() {
		blueMovingBlocks = new Array<BlueMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_blue");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("blue_moving_block");
			
			BlueMovingBlock c = new BlueMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			blueMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createPinkMovingBlocks() {
		pinkMovingBlocks = new Array<PinkMovingBlock>();
		MapLayer layer = tileMap.getLayers().get("movingblock_pink");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKER;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.restitution = 0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("pink_moving_block");
			
			PinkMovingBlock c = new PinkMovingBlock(body, game, this, mo.getProperties().get("direction", Integer.class), 
					mo.getProperties().get("minPos", Integer.class), mo.getProperties().get("maxPos", Integer.class), 
					mo.getProperties().get("velocity", Float.class), mo.getProperties().get("timerLength", Integer.class));
			pinkMovingBlocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createThePipeBalls() {
		thePipeBalls = new Array<ThePipeBall>();
		MapLayer layer = tileMap.getLayers().get("thepipeball");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS;
			fdef.isSensor = false;
			fdef.restitution = 1.0f;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("the_pipe_ball");

			// player collision/death sensor
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("the_pipe_ball_collision");
			
			
			ThePipeBall c = new ThePipeBall(body, game, this);
			thePipeBalls.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createThePipeOver() {
		thePipeOver = new Array<ThePipeOver>();

		MapLayer layer = tileMap.getLayers().get("thepipeover");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (4 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16/PPM, 4/PPM);

			fdef.shape = shape;
			fdef.isSensor = false; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("the_pipe_over");
			ThePipeOver c = new ThePipeOver(body, game, this);
			thePipeOver.add(c);

			body.setUserData(c);
			shape.dispose();
		}

	}
	private void createThePipeUnder() {
		thePipeUnder = new Array<ThePipeUnder>();

		MapLayer layer = tileMap.getLayers().get("thepipeunder");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (13 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(13/PPM, 16/PPM);

			fdef.shape = shape;
			fdef.isSensor = false; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("the_pipe_under");
			ThePipeUnder c = new ThePipeUnder(body, game, this);
			thePipeUnder.add(c);

			body.setUserData(c);
			shape.dispose();
		}

	}
	private void createDarkEvilE() {
		darkEviles = new Array<DarkEvilE>();
		MapLayer layer = tileMap.getLayers().get("dark_evile");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("dark_evile");

			// player collision/death sensor
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("dark_evile_death_sensor");
			
			// left sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(-13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("dark_evile_left_sensor");
			
			// right sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY  | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("dark_evile_right_sensor");
			
			// destroy sensor
			shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("dark_evile_remove");
			
			DarkEvilE c = new DarkEvilE(body, game);
			darkEviles.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createPlantOfDooms() {
		plantOfDooms = new Array<PlantOfDoom>();
		MapLayer layer = tileMap.getLayers().get("plant_of_doom");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape shape = new CircleShape();
			shape.setRadius(14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("plant_of_doom");
		
			PlantOfDoom c = new PlantOfDoom(body, game, this);
			plantOfDooms.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createGhosts() {
		ghosts = new Array<Ghost>();
		MapLayer layer = tileMap.getLayers().get("ghost");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.KinematicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("ghost");
			
			// destroy sensor
			shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("ghost_remove");
		
			Ghost c = new Ghost(body, game, this, mo.getProperties().get("speed", Integer.class));
			ghosts.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createTheShells() {
		theShells = new Array<TheShell>();
		MapLayer layer = tileMap.getLayers().get("the_shell");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 7 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("the_shell");
			
			// left sensor
			shape.setAsBox(3 / PPM, 5 / PPM, new Vector2(-13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("the_shell_left_sensor");
			
			// right sensor
			shape.setAsBox(3 / PPM, 5 / PPM, new Vector2(13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY  | B2DVars.BIT_BLOCKER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("the_shell_right_sensor");
			
			// destroy sensor
			shape.setAsBox(17 / PPM, 5 / PPM, new Vector2(0, 13 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("the_shell_remove");
			
			TheShell c = new TheShell(body, game, this);
			theShells.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createFallingRocks() {
		fallingRocks = new Array<FallingRock>();
		MapLayer layer = tileMap.getLayers().get("fallingrock");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (32 / PPM), y + (32 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(31 / PPM, 31 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("falling_rock");
			
			// foot sensor
			shape.setAsBox(29 / PPM, 5 / PPM, new Vector2(0, -24 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS;
			fdef.isSensor = false;
			body.createFixture(fdef).setUserData("falling_rock_foot");
			
			FallingRock c = new FallingRock(body, game, mo.getProperties().get("delay", Integer.class));
			fallingRocks.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createRangedEnemies() {
		rangedEnemies = new Array<RangedEnemy>();
		MapLayer layer = tileMap.getLayers().get("ranged_enemy");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("ranged_enemy");
			
			// player collision sensor
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("ranged_enemy_death_sensor");
			
			// destroy sensor
			shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("ranged_enemy_remove");
			
			RangedEnemy c = new RangedEnemy(body, game, this);
			rangedEnemies.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createDoomfists() {
		doomfists = new Array<Doomfist>();
		MapLayer layer = tileMap.getLayers().get("doomfist");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_INTERACTABLE_SOLID;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("doomfist");

			// left sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(-13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_ENEMY;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("doomfist_left_sensor");
			
			// right sensor
			shape.setAsBox(3 / PPM, 12 / PPM, new Vector2(13 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_ENEMY;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("doomfist_right_sensor");
			
			Doomfist c = new Doomfist(body, game, this);
			doomfists.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createReapers() {
		reapers = new Array<Reaper>();
		MapLayer layer = tileMap.getLayers().get("reaper");

		if (layer == null) return;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_INTERACTABLE_SOLID;
			fdef.isSensor = false;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("reaper");
			
			shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("reaper_remove");
						
			shape.setAsBox(13 / PPM, 5 / PPM, new Vector2(0, -13 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS | B2DVars.BIT_ENEMY | B2DVars.BIT_PLAYER; // Is this ok to do?
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("reaper_foot");
			
			Reaper c = new Reaper(body, game, this);
			reapers.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	public void createBullet(Vector2 position, int direction) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		bdef.type = BodyType.KinematicBody;
		bdef.position.set(position.x, position.y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4 / PPM, 4 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY_BULLET;
		fdef.filter.maskBits = B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER | B2DVars.BIT_INTERACTABLE_SOLID;
		fdef.isSensor = false;
		Body body = world.createBody(bdef);
		body.createFixture(fdef).setUserData("ranged_enemy_bullet");
			
		RangedEnemyBullet c = new RangedEnemyBullet(body, game, this, cl, position, direction);
		rangedEnemyBullets.add(c);
		body.setUserData(c);
		shape.dispose();	
		
	}
	public void createBossBullet(Vector2 position, int mode, Vector2 um_bulletVel, float bulletSpeed) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		bdef.type = BodyType.KinematicBody;
		bdef.position.set(position.x, position.y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4 / PPM, 4 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY_BULLET;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		fdef.isSensor = false;
		Body body = world.createBody(bdef);
		body.createFixture(fdef).setUserData("boss_bullet");
		BossBullet c = new BossBullet(body, game, this, cl, position, mode, um_bulletVel, bulletSpeed);
		bossBullets.add(c);
		body.setUserData(c);
		shape.dispose();	
		
	}
	public void createGhost(Vector2 position) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		bdef.type = BodyType.KinematicBody;

		bdef.position.set(position.x + (16 / PPM), position.y + (16 / PPM));

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(14 / PPM, 14 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		fdef.isSensor = false;
		Body body = world.createBody(bdef);
		body.createFixture(fdef).setUserData("ghost");

		// destroy sensor
		shape.setAsBox(17 / PPM, 12 / PPM, new Vector2(0, 18 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("ghost_remove");

		Ghost c = new Ghost(body, game, this, 3);
		ghosts.add(c);
		body.setUserData(c);
		shape.dispose();
	}
	private void createCheckpoints() {
		checkpoints = new Array<Checkpoint>();

		MapLayer layer = tileMap.getLayers().get("checkpoints");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(14 / PPM, 14 / PPM);
			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("checkpoint");
			Checkpoint c = new Checkpoint(body, game, mo.getProperties().get("Color", Integer.class));
			checkpoints.add(c);

			body.setUserData(c);
			shape.dispose();
		}

	}
	private void createCoins() {
		coins = new Array<Coin>();

		MapLayer layer = tileMap.getLayers().get("coins");
		MapLayer layer2 = tileMap.getLayers().get("emeralds");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);
			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("coin");
			Coin c = new Coin(body, game, 1);
			coins.add(c);

			body.setUserData(c);
			cshape.dispose();
		}
		for (MapObject mo : layer2.getObjects()) {
			bdef.type = BodyType.StaticBody;
			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;
			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);
			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("coin");
			Coin c = new Coin(body, game, 5);
			coins.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createRedColorBuckets() {
		redColorBuckets = new Array<RedColorBucket>();

		MapLayer layer = tileMap.getLayers().get("red_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("red_color_bucket");
			RedColorBucket c = new RedColorBucket(body, game);
			redColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createOrangeColorBuckets() {
		orangeColorBuckets = new Array<OrangeColorBucket>();

		MapLayer layer = tileMap.getLayers().get("orange_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("orange_color_bucket");
			OrangeColorBucket c = new OrangeColorBucket(body, game);
			orangeColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}
	}
	private void createYellowColorBuckets() {
		yellowColorBuckets = new Array<YellowColorBucket>();

		MapLayer layer = tileMap.getLayers().get("yellow_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("yellow_color_bucket");
			YellowColorBucket c = new YellowColorBucket(body, game);
			yellowColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createGreenColorBuckets() {
		greenColorBuckets = new Array<GreenColorBucket>();

		MapLayer layer = tileMap.getLayers().get("green_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;
			

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("green_color_bucket");
			GreenColorBucket c = new GreenColorBucket(body, game);
			greenColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createBlueColorBuckets() {
		blueColorBuckets = new Array<BlueColorBucket>();

		MapLayer layer = tileMap.getLayers().get("blue_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("blue_color_bucket");
			BlueColorBucket c = new BlueColorBucket(body, game);
			blueColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createPinkColorBuckets() {
		pinkColorBuckets = new Array<PinkColorBucket>();

		MapLayer layer = tileMap.getLayers().get("pink_color_buckets");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {

			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			CircleShape cshape = new CircleShape();
			cshape.setRadius(14 / PPM);

			fdef.shape = cshape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("pink_color_bucket");
			PinkColorBucket c = new PinkColorBucket(body, game);
			pinkColorBuckets.add(c);

			body.setUserData(c);
			cshape.dispose();
		}

	}
	private void createRedJumppads() {
		redJumppads = new Array<RedJumppad>();

		MapLayer layer = tileMap.getLayers().get("red_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("red_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("red_jumppad_sensor");
			
			
			
			RedJumppad c = new RedJumppad(body, game);
			redJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createOrangeJumppads() {
		orangeJumppads = new Array<OrangeJumppad>();

		MapLayer layer = tileMap.getLayers().get("orange_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("orange_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("orange_jumppad_sensor");

			OrangeJumppad c = new OrangeJumppad(body, game);
			orangeJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createYellowJumppads() {
		yellowJumppads = new Array<YellowJumppad>();

		MapLayer layer = tileMap.getLayers().get("yellow_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("yellow_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("yellow_jumppad_sensor");	
			
			YellowJumppad c = new YellowJumppad(body, game);
			yellowJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createGreenJumppads() {
		greenJumppads = new Array<GreenJumppad>();

		MapLayer layer = tileMap.getLayers().get("green_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("green_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("green_jumppad_sensor");	
			
			GreenJumppad c = new GreenJumppad(body, game);
			greenJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createBlueJumppads() {
		blueJumppads = new Array<BlueJumppad>();
        
		MapLayer layer = tileMap.getLayers().get("blue_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("blue_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("blue_jumppad_sensor");
			
			BlueJumppad c = new BlueJumppad(body, game);
			blueJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createPinkJumppads() {
		pinkJumppads = new Array<PinkJumppad>();

		MapLayer layer = tileMap.getLayers().get("pink_jumppads");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_BULLET;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("pink_jumppad");
			
			// jump(top) sensor
			shape.setAsBox(14 / PPM, 3 / PPM, new Vector2(0, 15/PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("pink_jumppad_sensor");
			
			
			
			PinkJumppad c = new PinkJumppad(body, game);
			pinkJumppads.add(c);
			
			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createRedKeys() {
		redKeys = new Array<RedKey>();

		MapLayer layer = tileMap.getLayers().get("red_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("red_key");
			RedKey c = new RedKey(body, game, mo.getProperties().get("id", Integer.class));
			redKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createRedKeyblocks() {
		redKeyblocks = new Array<RedKeyblock>();

		MapLayer layer = tileMap.getLayers().get("red_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("red_keyblock");
			RedKeyblock c = new RedKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			redKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createOrangeKeys() {
		orangeKeys = new Array<OrangeKey>();

		MapLayer layer = tileMap.getLayers().get("orange_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("orange_key");
			OrangeKey c = new OrangeKey(body, game, mo.getProperties().get("id", Integer.class));
			orangeKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createOrangeKeyblocks() {
		orangeKeyblocks = new Array<OrangeKeyblock>();

		MapLayer layer = tileMap.getLayers().get("orange_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("orange_keyblock");
			OrangeKeyblock c = new OrangeKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			orangeKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createYellowKeys() {
		yellowKeys = new Array<YellowKey>();

		MapLayer layer = tileMap.getLayers().get("yellow_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("yellow_key");
		    YellowKey c = new YellowKey(body, game, mo.getProperties().get("id", Integer.class));
			yellowKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createYellowKeyblocks() {
		yellowKeyblocks = new Array<YellowKeyblock>();

		MapLayer layer = tileMap.getLayers().get("yellow_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("yellow_keyblock");
		    YellowKeyblock c = new YellowKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			yellowKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createGreenKeys() {
		greenKeys = new Array<GreenKey>();

		MapLayer layer = tileMap.getLayers().get("green_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("green_key");
			GreenKey c = new GreenKey(body, game, mo.getProperties().get("id", Integer.class));
			greenKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createGreenKeyblocks() {
		greenKeyblocks = new Array<GreenKeyblock>();

		MapLayer layer = tileMap.getLayers().get("green_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("green_keyblock");
			GreenKeyblock c = new GreenKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			greenKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createBlueKeys() {
		blueKeys = new Array<BlueKey>();

		MapLayer layer = tileMap.getLayers().get("blue_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("blue_key");
			BlueKey c = new BlueKey(body, game, mo.getProperties().get("id", Integer.class));
			blueKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createBlueKeyblocks() {
		blueKeyblocks = new Array<BlueKeyblock>();

		MapLayer layer = tileMap.getLayers().get("blue_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("blue_keyblock");
			BlueKeyblock c = new BlueKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			blueKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createPinkKeys() {
		pinkKeys = new Array<PinkKey>();

		MapLayer layer = tileMap.getLayers().get("pink_keys");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true; // can travel through
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("pink_key");
			PinkKey c = new PinkKey(body, game, mo.getProperties().get("id", Integer.class));
			pinkKeys.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createPinkKeyblocks() {
		pinkKeyblocks = new Array<PinkKeyblock>();

		MapLayer layer = tileMap.getLayers().get("pink_keyblocks");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY_BULLET | B2DVars.BIT_ENEMY;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("pink_keyblock");
			PinkKeyblock c = new PinkKeyblock(body, game, mo.getProperties().get("id", Integer.class));
			pinkKeyblocks.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createHeartPowerups() {
		heartPowerups = new Array<HeartPowerup>();

		MapLayer layer = tileMap.getLayers().get("heart_powerup");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("heart_powerup");
			HeartPowerup c = new HeartPowerup(body, game);
			heartPowerups.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createSpeedPowerups() {
		speedPowerups = new Array<SpeedPowerup>();

		MapLayer layer = tileMap.getLayers().get("speed_powerup");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("speed_powerup");
			SpeedPowerup c = new SpeedPowerup(body, game);
			speedPowerups.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createJumpPowerups() {
		jumpPowerups = new Array<JumpPowerup>();

		MapLayer layer = tileMap.getLayers().get("jump_powerup");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("jump_powerup");
			JumpPowerup c = new JumpPowerup(body, game);
			jumpPowerups.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createTempHealthPowerups() {
		tempHealthPowerups = new Array<TempHealthPowerup>();

		MapLayer layer = tileMap.getLayers().get("temp_health_powerup");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (16 / PPM), y + (16 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("temp_health_powerup");
			TempHealthPowerup c = new TempHealthPowerup(body, game);
			tempHealthPowerups.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createDoors() {
		doors = new Array<Door>();

		MapLayer layer = tileMap.getLayers().get("doors");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (48 / PPM), y + (48 / PPM));

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(48 / PPM, 48 / PPM);

			fdef.shape = shape;
			fdef.isSensor = true;
			fdef.friction = 0f;
			fdef.filter.categoryBits = B2DVars.BIT_INTERACTABLE_TRANSPARENT;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("door");
			Door c = new Door(body, mo.getProperties().get("location", String.class), game, this);
			doors.add(c);

			body.setUserData(c);
			shape.dispose();
		}
	}
	private void createBosses() {
		bosses = new Array<Boss>();

		MapLayer layer = tileMap.getLayers().get("boss");

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.DynamicBody;

			float x = mo.getProperties().get("x", Float.class) / PPM;
			float y = mo.getProperties().get("y", Float.class) / PPM;

			bdef.position.set(x + (64 / PPM), y + (64 / PPM));

			PolygonShape shape = new PolygonShape();
			
			shape.setAsBox(64 / PPM, 64 / PPM);
			fdef.shape = shape;
			fdef.isSensor = false;
			fdef.friction = 0.1f;
			fdef.density = 100f;
			bdef.fixedRotation = true;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_BLOCKS | B2DVars.BIT_INTERACTABLE_SOLID;
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("boss");
			
			shape.setAsBox(62 / PPM, 20 / PPM, new Vector2(0, -70 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("boss_foot");
			
			shape.setAsBox(64 / PPM, 10 / PPM, new Vector2(0, 60 / PPM), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS | B2DVars.BIT_PLAYER;
			fdef.isSensor = false;
			body.createFixture(fdef).setUserData("boss_head");
			
			shape.setAsBox(7 / PPM, 50 / PPM, new Vector2(-67 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("boss_side_sensor_left");
			
			shape.setAsBox(7 / PPM, 50 / PPM, new Vector2(67 / PPM, 0), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
			fdef.filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("boss_side_sensor_right");

			Boss c = new Boss(body, game, this);
			bosses.add(c);
			body.setUserData(c);
			shape.dispose();
		}
	}
	//</editor-fold>

	public void createGhostDeathParticleEffect(Vector2 pos){
		ParticleEffectPool.PooledEffect effect = ghostDeathPool.obtain();
		effect.setPosition(pos.x*PPM, pos.y*PPM);
		ghostDeathEffects.add(effect);
		effect.start();
	}

	public void createEvilEDeathParticleEffect(Vector2 pos){
		ParticleEffectPool.PooledEffect effect = evileDeathParticlePool.obtain();
		effect.setPosition(pos.x*PPM, pos.y*PPM);
		evileDeathParticleEffects.add(effect);
		effect.start();
	}

	public void createDarkEvilEDeathParticleEffect(Vector2 pos){
		ParticleEffectPool.PooledEffect effect = darkEvileDeathParticlePool.obtain();
		effect.setPosition(pos.x*PPM, pos.y*PPM);
		darkEvileDeathParticleEffects.add(effect);
		effect.start();
	}

	public void createReaperDeathParticleEffect(Vector2 pos){
		ParticleEffectPool.PooledEffect effect = reaperDeathParticlePool.obtain();
		effect.setPosition(pos.x*PPM, pos.y*PPM);
		reaperDeathParticleEffects.add(effect);
		effect.start();
	}

	public void stopBossCollisionWithPlayer(){
		for (int i = 0; i < bosses.size; i++) {
			Body b = bosses.get(i).getBody();
			for(int j = 0; j < bosses.get(i).getBody().getFixtureList().size; j++){
				Filter filter = new Filter();
				filter.maskBits = B2DVars.BIT_INTERACTABLE_SOLID | B2DVars.BIT_BLOCKS;
				filter.categoryBits = B2DVars.BIT_ENEMY;
				bosses.get(i).getBody().getFixtureList().get(j).setFilterData(filter);
			}
		}
	}

	public void setColor(int color) {
		player.setColor(color);
	}

	public void die() {
	    if(!noDeathMode) {
            System.out.println("Player died.");
            game.generic_bg_music.stop();
            game.mysterious_magic_bg_music.stop();
            game.tictac.stop();
			game.boss_fight_music.stop();
			cantDoStuff = true;
            red.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            orange.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            yellow.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            green.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            blue.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            pink.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
            ((Player) player.getBody().getUserData()).setHidden(true);
            player.getBody().setLinearVelocity(new Vector2(0, player.getBody().getLinearVelocity().y));
            if (game.getTempHealth() == 0) health--;
            else game.setTempHealth(game.getTempHealth() - 1);
            countdown.cancel();
            shake.shake(0.5f);
            renderPlayerDeathEffect = true;

            if (health > 0 && game.getTime() > 0) {
                game.setHealth(health);
                if (game.getRespawnPos().x != 0 && game.getRespawnPos().y != 0) {
                    game.setScreenWithFade(new Play(game, game.getRespawnPos().x, game.getRespawnPos().y, game.getRespawnColor(), "no", 0), 1.5f);
                    if (game.getSoundSetting() && player.getBody().getPosition().y > 0) game.hurt.play();
                } else {
                    if (game.level == 1)
                        game.setScreenWithFade(new Play(game, 192 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
                    else game.setScreenWithFade(new Play(game, 48 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
                    if (game.getSoundSetting() && player.getBody().getPosition().y > 0) game.hurt.play();
                }
            } else {
                game.setRespawnPos(new Vector2(0, 0));
                game.setHealth(3);
                game.setTempHealth(0);
                game.setTime(0);
                if (game.getSoundSetting()) game.game_over.play(1.3f);
                game.setScreenWithFade(new Death(game), 2.5f);
            }
        }
	}
	
	public void setSpawnpoint(float x, float y){
		game.getRespawnPos().x = x;
		game.getRespawnPos().y = y;
	}

	public void win() {
		game.generic_bg_music.stop();
		game.mysterious_magic_bg_music.stop();
		game.tictac.stop();
		game.boss_fight_music.stop();
		if(game.getSoundSetting())game.win.play(1.0f);
		player.getBody().setLinearVelocity(new Vector2(0, player.getBody().getLinearVelocity().y));
		game.setRespawnPos(new Vector2(0, 0));
		cantDoStuff = true;
		game.setHealth(3);
		game.setTempHealth(0);
		countdown.cancel();
		game.setTime(0);
		game.setTotalCoins(game.getTotalCoins() + player.getNumCoins());
		game.setScreenWithFade(new Victory(game), 1.5f);
		System.out.println("Coins: " + game.getTotalCoins());
	}

	public void show() {}
	public void pause() {}
	public void resume() {}
	public void hide() {
		show = false;
		dispose();
	}

	public void resize(int width, int height) {
		b2dCam.viewportWidth = width / PPM;
		b2dCam.viewportHeight = height / PPM;
		b2dCam.update();
		vw.update(width, height);
	}

	public void dispose() {
		game.scissor_sound.stop();

		tmr.dispose();
		tileMap.dispose();
		world.dispose();
		b2dr.dispose();
		sb.dispose();

		evileDeathParticle.dispose();
		darkEvileDeathParticle.dispose();
		redKeyBlockParticle.dispose();
		orangeKeyBlockParticle.dispose();
		yellowKeyBlockParticle.dispose();
		greenKeyBlockParticle.dispose();
		blueKeyBlockParticle.dispose();
		pinkKeyBlockParticle.dispose();
		red.dispose();
		orange.dispose();
		yellow.dispose();
		green.dispose();
		blue.dispose();
		pink.dispose();
		redColorBucketParticle.dispose();
		orangeColorBucketParticle.dispose();
		yellowColorBucketParticle.dispose();
		greenColorBucketParticle.dispose();
		blueColorBucketParticle.dispose();
		pinkColorBucketParticle.dispose();
		reaperDeathParticle.dispose();
	}
	
	public float getPlayerX(){ return player.getBody().getPosition().x;}
	public float getPlayerY(){ return player.getBody().getPosition().y;}
	public void setPlayerX(float x){ player.getBody().getPosition().x = x;}
	public void setPlayerY(float y){ player.getBody().getPosition().y = y;}

	public Player getPlayer(){ return player; }
	public int getPlayerColor(){return playerColor;}
	public void setPlayerColor(int playerColor){this.playerColor = playerColor;}
	public boolean isCantDoStuff(){return cantDoStuff;}
	public int getPlayerHealth(){return health;}
	public void setPlayerHealth(int health){this.health = health;}
	public TiledMap getTileMap(){return tileMap;}
	public boolean isOnJumpPad() {return onJumpPad;}
	public void setOnJumpPad(boolean onJumpPad){this.onJumpPad = onJumpPad;}
	public MyContactListener getContactListener(){return cl;}
	public boolean isPlayerFast() {return isPlayerFast;}
	public void setPlayerFast(boolean isPlayerFast) {this.isPlayerFast = isPlayerFast;}
	public void setJumpsLeft(int jumpsLeft) {this.jumpsLeft = jumpsLeft;}
	public int getJumpsLeft(){return jumpsLeft;}
	public boolean isCanDoubleJump() {return canDoubleJump;}
	public void setCanDoubleJump(boolean canDoubleJump) {this.canDoubleJump = canDoubleJump;}
	public String getSpecialLevel(){
	    return specialLevel;
    }
    public Boss getBoss(){
	    return bosses.get(0);
    }
    public boolean getIsBossAlive(){
		return isBossAlive;
	}
	public void setBossAlive(boolean newValue){
		isBossAlive = newValue;
	}
}