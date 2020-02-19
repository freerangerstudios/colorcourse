package com.freeranger.colorcourse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freeranger.colorcourse.screens.Menu;

public class Main extends Game {

	public Actor fadeActor = new Actor();
	private ShapeRenderer fadeRenderer;

	public Texture coin;
	public Texture emerald;
	public Texture red_cb;
	public Texture orange_cb;
	public Texture yellow_cb;
	public Texture green_cb;
	public Texture blue_cb;
	public Texture pink_cb;
	public Texture lock;
	public Texture heart_sheet;
	public Texture red_jumppad;
	public Texture orange_jumppad;
	public Texture yellow_jumppad;
	public Texture green_jumppad;
	public Texture blue_jumppad;
	public Texture pink_jumppad;
	public Texture red_key;
	public Texture red_keyblock;
	public Texture orange_key;
	public Texture orange_keyblock;
	public Texture yellow_key;
	public Texture yellow_keyblock;
	public Texture green_key;
	public Texture green_keyblock;
	public Texture blue_key;
	public Texture blue_keyblock;
	public Texture pink_key;
	public Texture pink_keyblock;
	public Texture evile_left;
	public Texture evile_right;
	public Texture dark_evile_left;
	public Texture dark_evile_right;
	public Texture test;
	public Texture checkpoint_inactive;
	public Texture checkpoint_active;
	public Texture checkpoint_used;
	public Texture playerRed_idle;
	public Texture playerRed_jump;
	public Texture playerOrange_idle;
	public Texture playerOrange_jump;
	public Texture playerYellow_idle;
	public Texture playerYellow_jump;
	public Texture playerGreen_idle;
	public Texture playerGreen_jump;
	public Texture playerBlue_idle;
	public Texture playerBlue_jump;
	public Texture playerPink_idle;
	public Texture playerPink_jump;
	public Texture mountains_layer1;
	public Texture mountains_layer2;
	public Texture mountains_layer3;
	public Texture mountains_layer4;
	public Texture rangedEnemy_idle_left;
	public Texture rangedEnemy_idle_right;
	public Texture rangedEnemy_shooting_left;
	public Texture rangedEnemy_shooting_right;
	public Texture rangedEnemy_bullet;
	public Texture plant_of_doom;
	public Texture the_shell;
	public Texture ghost_left;
	public Texture ghost_right;
	public Texture ghost_up;
	public Texture ghost_down;
	public Texture ghost_up_left;
	public Texture ghost_up_right;
	public Texture ghost_down_left;
	public Texture ghost_down_right;
	public Texture falling_rock;
	public Texture falling_rock_sprites;
	public Texture falling_rock_tired_sprites;
	public Texture doomfist_happy_left;
	public Texture doomfist_happy_right;
	public Texture doomfist_angry_left;
	public Texture doomfist_angry_right;
	public Texture the_pipe_ball;
	public Texture the_pipe_over;
	public Texture the_pipe_under;
	public Texture reaper_idle;
	public Texture reaper_left;
	public Texture reaper_right;
	public Texture red_block;
	public Texture orange_block;
	public Texture yellow_block;
	public Texture green_block;
	public Texture blue_block;
	public Texture pink_block;
	public Texture spike_ball;
	public Texture heart_powerup;
	public Texture speed_powerup;
	public Texture jump_powerup;
	public Texture temp_heart_powerup;
	public Texture temp_heart;
	public Texture level_1_overlay;
	public Texture level_1_behind;
	public Texture level_1_behind_german;
	public Texture level_1_behind_spanish;
	public Texture level_1_behind_swedish;
	public Texture level_2_overlay;
	public Texture level_2_behind;
    public Texture level_2_behind_german;
    public Texture level_2_behind_spanish;
    public Texture level_2_behind_swedish;
    public Texture level_3_overlay;
	public Texture level_3_behind;
	public Texture level_4_overlay;
	public Texture level_4_behind;
	public Texture level_5_overlay;
	public Texture level_5_behind;
	public Texture level_6_overlay;
	public Texture level_6_behind;
	public Texture level_7_overlay;
	public Texture level_7_behind;
	public Texture level_8_overlay;
	public Texture level_8_behind;
	public Texture level_9_overlay;
	public Texture level_9_behind;
	public Texture level_10_overlay;
	public Texture level_10_behind;
	public Texture level_11_overlay;
	public Texture level_11_behind;
	public Texture level_12_overlay;
	public Texture level_12_behind;
	public Texture level_13_overlay;
	public Texture level_13_behind;
	public Texture level_14_overlay;
	public Texture level_14_behind;
	public Texture level_15_overlay;
	public Texture level_15_behind;
	public Texture level_16_overlay;
	public Texture level_16_behind;
	public Texture level_17_overlay;
	public Texture level_17_behind;
	public Texture level_18_overlay;
	public Texture level_18_behind;
	public Texture level_19_overlay;
	public Texture level_19_behind;
	public Texture boss_level_bg_1;
	public Texture door;
	public Texture reaper_idle_sheet;
	public Texture reaper_left_sheet;
	public Texture reaper_right_sheet;

	public Texture boss_idle_left_stage1;
	public Texture boss_idle_right_stage1;
	public Texture boss_immune_left_stage1;
	public Texture boss_immune_right_stage1;
	public Texture boss_idle_left_stage2;
	public Texture boss_idle_right_stage2;
	public Texture boss_immune_left_stage2;
	public Texture boss_immune_right_stage2;
	public Texture boss_idle_left_stage3;
	public Texture boss_idle_right_stage3;
	public Texture boss_immune_left_stage3;
	public Texture boss_immune_right_stage3;
	public Texture boss_idle_left_stage4;
	public Texture boss_idle_right_stage4;
	public Texture boss_immune_left_stage4;
	public Texture boss_immune_right_stage4;
	
	public Sound color_bucket;
	public Sound coin_collect;
	public Sound emerald_collect;
	public Sound checkpoint;
	public Sound win;
	public Sound crash;
	public Sound woosh;
	private  Music walking;
	public Sound game_over;
	public Sound hurt;
	public Sound key;
	public Sound jump_powerup_sound;
	public Sound speed_powerup_sound;
	public Sound temp_heart_powerup_sound;
	public Sound heart_powerup_sound;
	public Sound ui_click;
	public Sound falling_rock_impact;
	public Music tictac;
	public Music generic_bg_music;
	public Music mysterious_magic_bg_music;
	public Sound shot_sound;
	public Music scissor_sound;
	public Music boss_fight_music;
	public Sound teleport_sound;
	public Sound impact_01;
	public Sound slime_01;
	public Sound smoke_01;
	public Sound smoke_02;
	public Music victory;
	public Sound spawn;
	public Sound locked;

	public Sound boss_hurt;
	public Sound boss_new_stage;
	public Music boss_landing;

	public int level = 1;
	public int levelsUnlocked;
	private int health = 3;
	private int tempHealth = 0; // 0 = No temp health, 1/2 = 1/2 temp health
	private int respawnColor = 0;
	private Vector2 respawnPos = new Vector2(0, 0);

	private AssetManager manager = new AssetManager();

	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;

	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;

	private boolean soundSetting;
	
	private int time;
	private int totalCoins;

	public void create() {
		//setup preferences
		Preferences prefs = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.gamedata");
		Preferences prefs2 = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.options");
		levelsUnlocked = prefs.getInteger("com.freerangerstudios.colorcourse.gamedata.levelsUnlocked", 1);
		totalCoins = prefs.getInteger("com.freerangerstudios.colorcourse.gamedata.totalCoins", 0);
		soundSetting = prefs2.getBoolean("com.freerangerstudios.colorcourse.options.sound", true);
		prefs.flush();
		
		// load assets
		manager.load("data/collectibles/coin.png", Texture.class);
		manager.load("data/collectibles/emerald.png", Texture.class);

		manager.load("data/player/player_red_idle.png", Texture.class);
		manager.load("data/player/player_orange_idle.png", Texture.class);
		manager.load("data/player/player_yellow_idle.png", Texture.class);
		manager.load("data/player/player_blue_idle.png", Texture.class);
		manager.load("data/player/player_green_idle.png", Texture.class);
		manager.load("data/player/player_pink_idle.png", Texture.class);

		manager.load("data/buckets/red_color_bucket.png", Texture.class);
		manager.load("data/buckets/orange_color_bucket.png", Texture.class);
		manager.load("data/buckets/yellow_color_bucket.png", Texture.class);
		manager.load("data/buckets/green_color_bucket.png", Texture.class);
		manager.load("data/buckets/blue_color_bucket.png", Texture.class);
		manager.load("data/buckets/pink_color_bucket.png", Texture.class);
		manager.load("data/misc/lock.png", Texture.class);
		manager.load("data/misc/test.png", Texture.class);
		manager.load("data/misc/heart.png", Texture.class);
	    manager.load("data/levels/jumppads/red_jumppad.png", Texture.class);
	    manager.load("data/levels/jumppads/orange_jumppad.png", Texture.class);
	    manager.load("data/levels/jumppads/yellow_jumppad.png", Texture.class);
	    manager.load("data/levels/jumppads/green_jumppad.png", Texture.class);
	    manager.load("data/levels/jumppads/blue_jumppad.png", Texture.class);
	    manager.load("data/levels/jumppads/pink_jumppad.png", Texture.class);
	    manager.load("data/levels/keyblocks/red_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/red_keyblock.png", Texture.class);
	    manager.load("data/levels/keyblocks/orange_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/orange_keyblock.png", Texture.class);
	    manager.load("data/levels/keyblocks/yellow_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/yellow_keyblock.png", Texture.class);
	    manager.load("data/levels/keyblocks/green_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/green_keyblock.png", Texture.class);
	    manager.load("data/levels/keyblocks/blue_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/blue_keyblock.png", Texture.class);
	    manager.load("data/levels/keyblocks/pink_key.png", Texture.class);
	    manager.load("data/levels/keyblocks/pink_keyblock.png", Texture.class);
	    manager.load("data/enemies/evile/evile_left.png", Texture.class);
		manager.load("data/enemies/evile/evile_right.png", Texture.class);
		manager.load("data/enemies/evile/dark_evile_left.png", Texture.class);
		manager.load("data/enemies/evile/dark_evile_right.png", Texture.class);
	    manager.load("data/misc/checkpoint_active.png", Texture.class);
	    manager.load("data/misc/checkpoint_inactive.png", Texture.class);
	    manager.load("data/misc/checkpoint_used.png", Texture.class);
	    manager.load("data/player/idle/red_idle.png", Texture.class);
	    manager.load("data/player/jump/red_jump.png", Texture.class);
	    manager.load("data/player/idle/orange_idle.png", Texture.class);
	    manager.load("data/player/jump/orange_jump.png", Texture.class);
	    manager.load("data/player/idle/yellow_idle.png", Texture.class);
	    manager.load("data/player/jump/yellow_jump.png", Texture.class);
	    manager.load("data/player/idle/green_idle.png", Texture.class);
	    manager.load("data/player/jump/green_jump.png", Texture.class);
	    manager.load("data/player/idle/blue_idle.png", Texture.class);
	    manager.load("data/player/jump/blue_jump.png", Texture.class);
	    manager.load("data/player/idle/pink_idle.png", Texture.class);
	    manager.load("data/player/jump/pink_jump.png", Texture.class);
	    manager.load("data/background/mountains/img1.png", Texture.class);
	    manager.load("data/background/mountains/img2.png", Texture.class);
	    manager.load("data/background/mountains/img3.png", Texture.class);
	    manager.load("data/background/mountains/img4.png", Texture.class); 
	    manager.load("data/enemies/ranged/idle/idle_left.png", Texture.class);
	    manager.load("data/enemies/ranged/idle/idle_right.png", Texture.class);
		manager.load("data/enemies/ranged/shooting/shooting_left.png", Texture.class);
		manager.load("data/enemies/ranged/shooting/shooting_right.png", Texture.class);
	    manager.load("data/enemies/ranged/bullet.png", Texture.class);
	    manager.load("data/enemies/plantofdoom/sprite_sheet.png", Texture.class);
	    manager.load("data/enemies/theshell/sprite.png", Texture.class);
	    manager.load("data/enemies/ghost/left.png", Texture.class);
	    manager.load("data/enemies/ghost/right.png", Texture.class);
	    manager.load("data/enemies/ghost/up.png", Texture.class);
	    manager.load("data/enemies/ghost/down.png", Texture.class);
	    manager.load("data/enemies/ghost/up_left.png", Texture.class);
	    manager.load("data/enemies/ghost/up_right.png", Texture.class);
	    manager.load("data/enemies/ghost/down_left.png", Texture.class);
	    manager.load("data/enemies/ghost/down_right.png", Texture.class);
	    manager.load("data/enemies/fallingrock/sprite.png", Texture.class);
	    manager.load("data/enemies/doomfist/left_happy.png", Texture.class);
	    manager.load("data/enemies/doomfist/right_happy.png", Texture.class);
	    manager.load("data/enemies/doomfist/left_angry.png", Texture.class);
	    manager.load("data/enemies/doomfist/right_angry.png", Texture.class);
	    manager.load("data/enemies/thepipe/ball.png", Texture.class);
	    manager.load("data/enemies/thepipe/pipe_over.png", Texture.class);
	    manager.load("data/enemies/thepipe/pipe_under.png", Texture.class);
	    manager.load("data/enemies/reaper/idle.png", Texture.class);
	    manager.load("data/enemies/reaper/left.png", Texture.class);
	    manager.load("data/enemies/reaper/right.png", Texture.class);
	    manager.load("data/levels/other/red_block.png", Texture.class);
	    manager.load("data/levels/other/orange_block.png", Texture.class);
	    manager.load("data/levels/other/yellow_block.png", Texture.class);
	    manager.load("data/levels/other/green_block.png", Texture.class);
	    manager.load("data/levels/other/blue_block.png", Texture.class);
	    manager.load("data/levels/other/pink_block.png", Texture.class);
	    manager.load("data/levels/other/spike_ball.png", Texture.class);
	    manager.load("data/misc/heart_powerup.png", Texture.class);
	    manager.load("data/misc/speed_powerup.png", Texture.class);
	    manager.load("data/misc/jump_powerup.png", Texture.class);
	    manager.load("data/misc/temp_heart_powerup.png", Texture.class);
	    manager.load("data/misc/temp_heart.png", Texture.class);
	    manager.load("data/misc/door_portal.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_1/lvl1_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_1/lvl1_behind.png", Texture.class);
		manager.load("data/levels/worlds/world_1/level_1/lvl1_behind_german.png", Texture.class);
		manager.load("data/levels/worlds/world_1/level_1/lvl1_behind_spanish.png", Texture.class);
		manager.load("data/levels/worlds/world_1/level_1/lvl1_behind_swedish.png", Texture.class);
		manager.load("data/levels/worlds/world_1/level_2/lvl2_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_2/lvl2_behind.png", Texture.class);
        manager.load("data/levels/worlds/world_1/level_2/lvl2_behind_german.png", Texture.class);
        manager.load("data/levels/worlds/world_1/level_2/lvl2_behind_spanish.png", Texture.class);
        manager.load("data/levels/worlds/world_1/level_2/lvl2_behind_swedish.png", Texture.class);
        manager.load("data/levels/worlds/world_1/level_3/lvl3_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_3/lvl3_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_4/lvl4_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_4/lvl4_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_5/lvl5_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_5/lvl5_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_6/lvl6_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_6/lvl6_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_7/lvl7_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_7/lvl7_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_8/lvl8_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_8/lvl8_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_9/lvl9_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_9/lvl9_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_10/lvl10_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_10/lvl10_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_11/lvl11_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_11/lvl11_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_12/lvl12_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_12/lvl12_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_13/lvl13_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_13/lvl13_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_14/lvl14_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_14/lvl14_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_15/lvl15_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_15/lvl15_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_16/lvl16_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_16/lvl16_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_17/lvl17_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_17/lvl17_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_18/lvl18_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_18/lvl18_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_19/lvl19_overlay.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_19/lvl19_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_19/lvl19_behind.png", Texture.class);
	    manager.load("data/levels/worlds/world_1/level_20/background/background.png", Texture.class);
	    manager.load("data/enemies/boss/boss_left.png", Texture.class);
	    manager.load("data/enemies/boss/boss_right.png", Texture.class);
	    manager.load("data/enemies/boss/boss_immune_left.png", Texture.class);
	    manager.load("data/enemies/boss/boss_immune_right.png", Texture.class);
		manager.load("data/enemies/boss/boss_left_stage2.png", Texture.class);
		manager.load("data/enemies/boss/boss_right_stage2.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_left_stage2.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_right_stage2.png", Texture.class);
		manager.load("data/enemies/boss/boss_left_stage3.png", Texture.class);
		manager.load("data/enemies/boss/boss_right_stage3.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_left_stage3.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_right_stage3.png", Texture.class);
		manager.load("data/enemies/boss/boss_left_stage4.png", Texture.class);
		manager.load("data/enemies/boss/boss_right_stage4.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_left_stage4.png", Texture.class);
		manager.load("data/enemies/boss/boss_immune_right_stage4.png", Texture.class);
		manager.load("data/enemies/fallingrock/falling_rock.png", Texture.class);
		manager.load("data/enemies/fallingrock/falling_rock_tired.png", Texture.class);
		manager.load("data/enemies/reaper/idle/idle.png", Texture.class);
		manager.load("data/enemies/reaper/left/left.png", Texture.class);
		manager.load("data/enemies/reaper/right/right.png", Texture.class);

		manager.load("data/sounds/splash.mp3", Sound.class);
	    manager.load("data/sounds/coin.mp3", Sound.class);
		manager.load("data/sounds/emerald.mp3", Sound.class);
		manager.load("data/sounds/checkpoint.mp3", Sound.class);
	    manager.load("data/sounds/win.mp3", Sound.class);
	    manager.load("data/sounds/crash.mp3", Sound.class);
	    manager.load("data/sounds/woosh.mp3", Sound.class);
	    manager.load("data/sounds/walking.mp3", Music.class);
	    manager.load("data/sounds/gameover.mp3", Sound.class);
	    manager.load("data/sounds/hurt.mp3", Sound.class);
	    manager.load("data/sounds/key.mp3", Sound.class);
	    manager.load("data/sounds/powerup_jump.mp3", Sound.class);
	    manager.load("data/sounds/powerup_speed.mp3", Sound.class);
	    manager.load("data/sounds/powerup_heart.mp3", Sound.class);
	    manager.load("data/sounds/powerup_heart_temp.mp3", Sound.class);
	    manager.load("data/sounds/ui_audio/click.mp3", Sound.class);
	    manager.load("data/sounds/music/generic_bg.mp3", Music.class);
	    manager.load("data/sounds/tictac.mp3", Music.class);
	    manager.load("data/sounds/music/mysterious_magic_bg.mp3", Music.class);
	    manager.load("data/sounds/falling_rock_impact.mp3", Sound.class);
	    manager.load("data/sounds/shot.ogg", Sound.class);
		manager.load("data/sounds/scissor.mp3", Music.class);
		manager.load("data/sounds/teleport.mp3", Sound.class);
		manager.load("data/sounds/boss_fight_music.mp3", Music.class);
		manager.load("data/sounds/impact_01.mp3", Sound.class);
		manager.load("data/sounds/slime.mp3", Sound.class);
		manager.load("data/sounds/smoke_01.mp3", Sound.class);
		manager.load("data/sounds/smoke_02.mp3", Sound.class);
		manager.load("data/sounds/victory.mp3", Music.class);
		manager.load("data/sounds/boss/hurt.mp3", Sound.class);
		manager.load("data/sounds/boss/landing.mp3", Music.class);
		manager.load("data/sounds/boss/new_stage.mp3", Sound.class);
		manager.load("data/sounds/boss/spawn.mp3", Sound.class);
		manager.load("data/sounds/locked.wav", Sound.class);

		manager.finishLoading();
		
		// assing assets
		if(manager.isLoaded("data/collectibles/coin.png")) coin = manager.get("data/collectibles/coin.png", Texture.class);
		if(manager.isLoaded("data/collectibles/emerald.png")) emerald = manager.get("data/collectibles/emerald.png", Texture.class);
		if(manager.isLoaded("data/buckets/red_color_bucket.png")) red_cb = manager.get("data/buckets/red_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/buckets/orange_color_bucket.png")) orange_cb = manager.get("data/buckets/orange_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/buckets/yellow_color_bucket.png")) yellow_cb = manager.get("data/buckets/yellow_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/buckets/green_color_bucket.png")) green_cb = manager.get("data/buckets/green_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/buckets/blue_color_bucket.png")) blue_cb = manager.get("data/buckets/blue_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/buckets/pink_color_bucket.png")) pink_cb = manager.get("data/buckets/pink_color_bucket.png", Texture.class);
		if(manager.isLoaded("data/misc/lock.png")) lock = manager.get("data/misc/lock.png", Texture.class);
		if(manager.isLoaded("data/misc/test.png")) test = manager.get("data/misc/test.png", Texture.class);
		if(manager.isLoaded("data/misc/heart.png")) heart_sheet = manager.get("data/misc/heart.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/red_jumppad.png")) red_jumppad = manager.get("data/levels/jumppads/red_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/orange_jumppad.png")) orange_jumppad = manager.get("data/levels/jumppads/orange_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/yellow_jumppad.png")) yellow_jumppad = manager.get("data/levels/jumppads/yellow_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/green_jumppad.png")) green_jumppad = manager.get("data/levels/jumppads/green_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/blue_jumppad.png")) blue_jumppad = manager.get("data/levels/jumppads/blue_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/jumppads/pink_jumppad.png")) pink_jumppad = manager.get("data/levels/jumppads/pink_jumppad.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/red_key.png")) red_key = manager.get("data/levels/keyblocks/red_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/red_keyblock.png")) red_keyblock = manager.get("data/levels/keyblocks/red_keyblock.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/orange_key.png")) orange_key = manager.get("data/levels/keyblocks/orange_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/orange_keyblock.png")) orange_keyblock = manager.get("data/levels/keyblocks/orange_keyblock.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/yellow_key.png")) yellow_key = manager.get("data/levels/keyblocks/yellow_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/yellow_keyblock.png")) yellow_keyblock = manager.get("data/levels/keyblocks/yellow_keyblock.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/green_key.png")) green_key = manager.get("data/levels/keyblocks/green_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/green_keyblock.png")) green_keyblock = manager.get("data/levels/keyblocks/green_keyblock.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/blue_key.png")) blue_key = manager.get("data/levels/keyblocks/blue_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/blue_keyblock.png")) blue_keyblock = manager.get("data/levels/keyblocks/blue_keyblock.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/pink_key.png")) pink_key = manager.get("data/levels/keyblocks/pink_key.png", Texture.class);
		if(manager.isLoaded("data/levels/keyblocks/pink_keyblock.png")) pink_keyblock = manager.get("data/levels/keyblocks/pink_keyblock.png", Texture.class);
		if(manager.isLoaded("data/enemies/evile/evile_left.png")) evile_left = manager.get("data/enemies/evile/evile_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/evile/evile_right.png")) evile_right = manager.get("data/enemies/evile/evile_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/evile/dark_evile_left.png")) dark_evile_left = manager.get("data/enemies/evile/dark_evile_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/evile/dark_evile_right.png")) dark_evile_right = manager.get("data/enemies/evile/dark_evile_right.png", Texture.class);
		if(manager.isLoaded("data/misc/checkpoint_active.png")) checkpoint_active = manager.get("data/misc/checkpoint_active.png", Texture.class);
		if(manager.isLoaded("data/misc/checkpoint_inactive.png")) checkpoint_inactive = manager.get("data/misc/checkpoint_inactive.png", Texture.class);
		if(manager.isLoaded("data/misc/checkpoint_used.png")) checkpoint_used = manager.get("data/misc/checkpoint_used.png", Texture.class);
		if(manager.isLoaded("data/misc/door_portal.png")) door = manager.get("data/misc/door_portal.png", Texture.class);
		if(manager.isLoaded("data/player/idle/red_idle.png")) playerRed_idle = manager.get("data/player/idle/red_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/red_jump.png")) playerRed_jump = manager.get("data/player/jump/red_jump.png", Texture.class);
		if(manager.isLoaded("data/player/idle/orange_idle.png")) playerOrange_idle = manager.get("data/player/idle/orange_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/orange_jump.png")) playerOrange_jump = manager.get("data/player/jump/orange_jump.png", Texture.class);
		if(manager.isLoaded("data/player/idle/yellow_idle.png")) playerYellow_idle = manager.get("data/player/idle/yellow_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/yellow_jump.png")) playerYellow_jump = manager.get("data/player/jump/yellow_jump.png", Texture.class);
		if(manager.isLoaded("data/player/idle/green_idle.png")) playerGreen_idle = manager.get("data/player/idle/green_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/green_jump.png")) playerGreen_jump = manager.get("data/player/jump/green_jump.png", Texture.class);
		if(manager.isLoaded("data/player/idle/blue_idle.png")) playerBlue_idle = manager.get("data/player/idle/blue_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/blue_jump.png")) playerBlue_jump = manager.get("data/player/jump/blue_jump.png", Texture.class);
		if(manager.isLoaded("data/player/idle/pink_idle.png")) playerPink_idle = manager.get("data/player/idle/pink_idle.png", Texture.class);
		if(manager.isLoaded("data/player/jump/pink_jump.png")) playerPink_jump = manager.get("data/player/jump/pink_jump.png", Texture.class);
		if(manager.isLoaded("data/background/mountains/img1.png")) mountains_layer1 = manager.get("data/background/mountains/img1.png", Texture.class);
		if(manager.isLoaded("data/background/mountains/img2.png")) mountains_layer2 = manager.get("data/background/mountains/img2.png", Texture.class);
		if(manager.isLoaded("data/background/mountains/img3.png")) mountains_layer3 = manager.get("data/background/mountains/img3.png", Texture.class);
		if(manager.isLoaded("data/background/mountains/img4.png")) mountains_layer4 = manager.get("data/background/mountains/img4.png", Texture.class);
		if(manager.isLoaded("data/enemies/ranged/idle/idle_left.png")) rangedEnemy_idle_left = manager.get("data/enemies/ranged/idle/idle_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/ranged/idle/idle_right.png")) rangedEnemy_idle_right = manager.get("data/enemies/ranged/idle/idle_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/ranged/shooting/shooting_left.png")) rangedEnemy_shooting_left = manager.get("data/enemies/ranged/shooting/shooting_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/ranged/shooting/shooting_right.png")) rangedEnemy_shooting_right = manager.get("data/enemies/ranged/shooting/shooting_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/ranged/bullet.png")) rangedEnemy_bullet = manager.get("data/enemies/ranged/bullet.png", Texture.class);
		if(manager.isLoaded("data/enemies/plantofdoom/sprite_sheet.png")) plant_of_doom = manager.get("data/enemies/plantofdoom/sprite_sheet.png", Texture.class);
		if(manager.isLoaded("data/enemies/theshell/sprite.png")) the_shell = manager.get("data/enemies/theshell/sprite.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/left.png")) ghost_left = manager.get("data/enemies/ghost/left.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/right.png")) ghost_right = manager.get("data/enemies/ghost/right.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/up.png")) ghost_up = manager.get("data/enemies/ghost/up.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/down.png")) ghost_down = manager.get("data/enemies/ghost/down.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/up_left.png")) ghost_up_left = manager.get("data/enemies/ghost/up_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/up_right.png")) ghost_up_right = manager.get("data/enemies/ghost/up_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/down_left.png")) ghost_down_left = manager.get("data/enemies/ghost/down_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/ghost/down_right.png")) ghost_down_right = manager.get("data/enemies/ghost/down_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/fallingrock/sprite.png")) falling_rock = manager.get("data/enemies/fallingrock/sprite.png", Texture.class);
		if(manager.isLoaded("data/enemies/doomfist/left_happy.png")) doomfist_happy_left = manager.get("data/enemies/doomfist/left_happy.png", Texture.class);
		if(manager.isLoaded("data/enemies/doomfist/right_happy.png")) doomfist_happy_right = manager.get("data/enemies/doomfist/right_happy.png", Texture.class);
		if(manager.isLoaded("data/enemies/doomfist/left_angry.png")) doomfist_angry_left = manager.get("data/enemies/doomfist/left_angry.png", Texture.class);
		if(manager.isLoaded("data/enemies/doomfist/right_angry.png")) doomfist_angry_right = manager.get("data/enemies/doomfist/right_angry.png", Texture.class);
		if(manager.isLoaded("data/enemies/thepipe/ball.png")) the_pipe_ball = manager.get("data/enemies/thepipe/ball.png", Texture.class);
		if(manager.isLoaded("data/enemies/thepipe/pipe_over.png")) the_pipe_over = manager.get("data/enemies/thepipe/pipe_over.png", Texture.class);
		if(manager.isLoaded("data/enemies/thepipe/pipe_under.png")) the_pipe_under = manager.get("data/enemies/thepipe/pipe_under.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/idle.png")) reaper_idle = manager.get("data/enemies/reaper/idle.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/left.png")) reaper_left = manager.get("data/enemies/reaper/left.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/right.png")) reaper_right = manager.get("data/enemies/reaper/right.png", Texture.class);
		if(manager.isLoaded("data/levels/other/red_block.png")) red_block = manager.get("data/levels/other/red_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/orange_block.png")) orange_block = manager.get("data/levels/other/orange_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/yellow_block.png")) yellow_block = manager.get("data/levels/other/yellow_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/green_block.png")) green_block = manager.get("data/levels/other/green_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/blue_block.png")) blue_block = manager.get("data/levels/other/blue_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/pink_block.png")) pink_block = manager.get("data/levels/other/pink_block.png", Texture.class);
		if(manager.isLoaded("data/levels/other/spike_ball.png")) spike_ball = manager.get("data/levels/other/spike_ball.png", Texture.class);
		if(manager.isLoaded("data/misc/heart_powerup.png")) heart_powerup = manager.get("data/misc/heart_powerup.png", Texture.class);
		if(manager.isLoaded("data/misc/speed_powerup.png")) speed_powerup = manager.get("data/misc/speed_powerup.png", Texture.class);
		if(manager.isLoaded("data/misc/jump_powerup.png")) jump_powerup = manager.get("data/misc/jump_powerup.png", Texture.class);
		if(manager.isLoaded("data/misc/temp_heart_powerup.png")) temp_heart_powerup = manager.get("data/misc/temp_heart_powerup.png", Texture.class);
		if(manager.isLoaded("data/misc/temp_heart.png")) temp_heart = manager.get("data/misc/temp_heart.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_1/lvl1_overlay.png")) level_1_overlay = manager.get("data/levels/worlds/world_1/level_1/lvl1_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_1/lvl1_behind.png")) level_1_behind = manager.get("data/levels/worlds/world_1/level_1/lvl1_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_1/lvl1_behind_german.png")) level_1_behind_german = manager.get("data/levels/worlds/world_1/level_1/lvl1_behind_german.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_1/lvl1_behind_spanish.png")) level_1_behind_spanish = manager.get("data/levels/worlds/world_1/level_1/lvl1_behind_spanish.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_1/lvl1_behind_swedish.png")) level_1_behind_swedish = manager.get("data/levels/worlds/world_1/level_1/lvl1_behind_swedish.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_2/lvl2_overlay.png")) level_2_overlay = manager.get("data/levels/worlds/world_1/level_2/lvl2_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_2/lvl2_behind.png")) level_2_behind = manager.get("data/levels/worlds/world_1/level_2/lvl2_behind.png", Texture.class);
        if(manager.isLoaded("data/levels/worlds/world_1/level_2/lvl2_behind_german.png")) level_2_behind_german = manager.get("data/levels/worlds/world_1/level_2/lvl2_behind_german.png", Texture.class);
        if(manager.isLoaded("data/levels/worlds/world_1/level_2/lvl2_behind_spanish.png")) level_2_behind_spanish = manager.get("data/levels/worlds/world_1/level_2/lvl2_behind_spanish.png", Texture.class);
        if(manager.isLoaded("data/levels/worlds/world_1/level_2/lvl2_behind_swedish.png")) level_2_behind_swedish = manager.get("data/levels/worlds/world_1/level_2/lvl2_behind_swedish.png", Texture.class);
        if(manager.isLoaded("data/levels/worlds/world_1/level_3/lvl3_overlay.png")) level_3_overlay = manager.get("data/levels/worlds/world_1/level_3/lvl3_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_3/lvl3_behind.png")) level_3_behind = manager.get("data/levels/worlds/world_1/level_3/lvl3_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_4/lvl4_overlay.png")) level_4_overlay = manager.get("data/levels/worlds/world_1/level_4/lvl4_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_4/lvl4_behind.png")) level_4_behind = manager.get("data/levels/worlds/world_1/level_4/lvl4_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_5/lvl5_overlay.png")) level_5_overlay = manager.get("data/levels/worlds/world_1/level_5/lvl5_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_5/lvl5_behind.png")) level_5_behind = manager.get("data/levels/worlds/world_1/level_5/lvl5_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_6/lvl6_overlay.png")) level_6_overlay = manager.get("data/levels/worlds/world_1/level_6/lvl6_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_6/lvl6_behind.png")) level_6_behind = manager.get("data/levels/worlds/world_1/level_6/lvl6_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_7/lvl7_overlay.png")) level_7_overlay = manager.get("data/levels/worlds/world_1/level_7/lvl7_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_7/lvl7_behind.png")) level_7_behind = manager.get("data/levels/worlds/world_1/level_7/lvl7_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_8/lvl8_overlay.png")) level_8_overlay = manager.get("data/levels/worlds/world_1/level_8/lvl8_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_8/lvl8_behind.png")) level_8_behind = manager.get("data/levels/worlds/world_1/level_8/lvl8_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_9/lvl9_overlay.png")) level_9_overlay = manager.get("data/levels/worlds/world_1/level_9/lvl9_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_9/lvl9_behind.png")) level_9_behind = manager.get("data/levels/worlds/world_1/level_9/lvl9_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_10/lvl10_overlay.png")) level_10_overlay = manager.get("data/levels/worlds/world_1/level_10/lvl10_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_10/lvl10_behind.png")) level_10_behind = manager.get("data/levels/worlds/world_1/level_10/lvl10_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_11/lvl11_overlay.png")) level_11_overlay = manager.get("data/levels/worlds/world_1/level_11/lvl11_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_11/lvl11_behind.png")) level_11_behind = manager.get("data/levels/worlds/world_1/level_11/lvl11_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_12/lvl12_overlay.png")) level_12_overlay = manager.get("data/levels/worlds/world_1/level_12/lvl12_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_12/lvl12_behind.png")) level_12_behind = manager.get("data/levels/worlds/world_1/level_12/lvl12_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_13/lvl13_overlay.png")) level_13_overlay = manager.get("data/levels/worlds/world_1/level_13/lvl13_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_13/lvl13_behind.png")) level_13_behind = manager.get("data/levels/worlds/world_1/level_13/lvl13_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_14/lvl14_overlay.png")) level_14_overlay = manager.get("data/levels/worlds/world_1/level_14/lvl14_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_14/lvl14_behind.png")) level_14_behind = manager.get("data/levels/worlds/world_1/level_14/lvl14_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_15/lvl15_overlay.png")) level_15_overlay = manager.get("data/levels/worlds/world_1/level_15/lvl15_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_15/lvl15_behind.png")) level_15_behind = manager.get("data/levels/worlds/world_1/level_15/lvl15_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_16/lvl16_overlay.png")) level_16_overlay = manager.get("data/levels/worlds/world_1/level_16/lvl16_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_16/lvl16_behind.png")) level_16_behind = manager.get("data/levels/worlds/world_1/level_16/lvl16_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_17/lvl17_overlay.png")) level_17_overlay = manager.get("data/levels/worlds/world_1/level_17/lvl17_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_17/lvl17_behind.png")) level_17_behind = manager.get("data/levels/worlds/world_1/level_17/lvl17_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_18/lvl18_overlay.png")) level_18_overlay = manager.get("data/levels/worlds/world_1/level_18/lvl18_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_18/lvl18_behind.png")) level_18_behind = manager.get("data/levels/worlds/world_1/level_18/lvl18_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_19/lvl19_overlay.png")) level_19_overlay = manager.get("data/levels/worlds/world_1/level_19/lvl19_overlay.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_19/lvl19_behind.png")) level_19_behind = manager.get("data/levels/worlds/world_1/level_19/lvl19_behind.png", Texture.class);
		if(manager.isLoaded("data/levels/worlds/world_1/level_20/background/background.png")) boss_level_bg_1 = manager.get("data/levels/worlds/world_1/level_20/background/background.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_left.png")) boss_idle_left_stage1 = manager.get("data/enemies/boss/boss_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_right.png")) boss_idle_right_stage1 = manager.get("data/enemies/boss/boss_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_left.png")) boss_immune_left_stage1 = manager.get("data/enemies/boss/boss_immune_left.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_right.png")) boss_immune_right_stage1 = manager.get("data/enemies/boss/boss_immune_right.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_left_stage2.png")) boss_idle_left_stage2 = manager.get("data/enemies/boss/boss_left_stage2.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_right_stage2.png")) boss_idle_right_stage2 = manager.get("data/enemies/boss/boss_right_stage2.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_left_stage2.png")) boss_immune_left_stage2 = manager.get("data/enemies/boss/boss_immune_left_stage2.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_right_stage2.png")) boss_immune_right_stage2 = manager.get("data/enemies/boss/boss_immune_right_stage2.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_left_stage3.png")) boss_idle_left_stage3 = manager.get("data/enemies/boss/boss_left_stage3.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_right_stage3.png")) boss_idle_right_stage3 = manager.get("data/enemies/boss/boss_right_stage3.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_left_stage3.png")) boss_immune_left_stage3 = manager.get("data/enemies/boss/boss_immune_left_stage3.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_right_stage3.png")) boss_immune_right_stage3 = manager.get("data/enemies/boss/boss_immune_right_stage3.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_left_stage4.png")) boss_idle_left_stage4 = manager.get("data/enemies/boss/boss_left_stage4.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_right_stage4.png")) boss_idle_right_stage4 = manager.get("data/enemies/boss/boss_right_stage4.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_left_stage4.png")) boss_immune_left_stage4 = manager.get("data/enemies/boss/boss_immune_left_stage4.png", Texture.class);
		if(manager.isLoaded("data/enemies/boss/boss_immune_right_stage4.png")) boss_immune_right_stage4 = manager.get("data/enemies/boss/boss_immune_right_stage4.png", Texture.class);
		if(manager.isLoaded("data/enemies/fallingrock/falling_rock.png")) falling_rock_sprites = manager.get("data/enemies/fallingrock/falling_rock.png", Texture.class);
		if(manager.isLoaded("data/enemies/fallingrock/falling_rock_tired.png")) falling_rock_tired_sprites = manager.get("data/enemies/fallingrock/falling_rock_tired.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/idle/idle.png")) reaper_idle_sheet = manager.get("data/enemies/reaper/idle/idle.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/left/left.png")) reaper_left_sheet = manager.get("data/enemies/reaper/left/left.png", Texture.class);
		if(manager.isLoaded("data/enemies/reaper/right/right.png")) reaper_right_sheet = manager.get("data/enemies/reaper/right/right.png", Texture.class);

		if(manager.isLoaded("data/sounds/coin.mp3")) coin_collect = manager.get("data/sounds/coin.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/emerald.mp3")) emerald_collect = manager.get("data/sounds/emerald.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/splash.mp3")) color_bucket = manager.get("data/sounds/splash.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/checkpoint.mp3")) checkpoint = manager.get("data/sounds/checkpoint.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/win.mp3")) win = manager.get("data/sounds/win.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/crash.mp3")) crash = manager.get("data/sounds/crash.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/woosh.mp3")) woosh = manager.get("data/sounds/woosh.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/walking.mp3")) walking = manager.get("data/sounds/walking.mp3", Music.class);
		if(manager.isLoaded("data/sounds/gameover.mp3")) game_over = manager.get("data/sounds/gameover.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/hurt.mp3")) hurt = manager.get("data/sounds/hurt.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/key.mp3")) key = manager.get("data/sounds/key.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/powerup_speed.mp3")) speed_powerup_sound = manager.get("data/sounds/powerup_speed.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/powerup_jump.mp3")) jump_powerup_sound = manager.get("data/sounds/powerup_jump.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/powerup_heart.mp3")) heart_powerup_sound = manager.get("data/sounds/powerup_heart.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/powerup_heart_temp.mp3")) temp_heart_powerup_sound = manager.get("data/sounds/powerup_heart_temp.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/ui_audio/click.mp3")) ui_click = manager.get("data/sounds/ui_audio/click.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/tictac.mp3")) tictac = manager.get("data/sounds/tictac.mp3", Music.class);
		if(manager.isLoaded("data/sounds/falling_rock_impact.mp3")) falling_rock_impact = manager.get("data/sounds/falling_rock_impact.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/scissor.mp3")) scissor_sound = manager.get("data/sounds/scissor.mp3", Music.class);
		if(manager.isLoaded("data/sounds/shot.ogg")) shot_sound = manager.get("data/sounds/shot.ogg", Sound.class);
		if(manager.isLoaded("data/sounds/music/generic_bg.mp3")) generic_bg_music = manager.get("data/sounds/music/generic_bg.mp3", Music.class);
		if(manager.isLoaded("data/sounds/music/mysterious_magic_bg.mp3")) mysterious_magic_bg_music = manager.get("data/sounds/music/mysterious_magic_bg.mp3", Music.class);
		if(manager.isLoaded("data/sounds/boss_fight_music.mp3")) boss_fight_music = manager.get("data/sounds/boss_fight_music.mp3", Music.class);
		if(manager.isLoaded("data/sounds/teleport.mp3")) teleport_sound = manager.get("data/sounds/teleport.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/impact_01.mp3")) impact_01 = manager.get("data/sounds/impact_01.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/slime.mp3")) slime_01 = manager.get("data/sounds/slime.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/smoke_01.mp3")) smoke_01 = manager.get("data/sounds/smoke_01.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/smoke_02.mp3")) smoke_02 = manager.get("data/sounds/smoke_02.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/victory.mp3")) victory = manager.get("data/sounds/victory.mp3", Music.class);
		if(manager.isLoaded("data/sounds/boss/hurt.mp3")) boss_hurt = manager.get("data/sounds/boss/hurt.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/boss/landing.mp3")) boss_landing = manager.get("data/sounds/boss/landing.mp3", Music.class);
		if(manager.isLoaded("data/sounds/boss/new_stage.mp3")) boss_new_stage = manager.get("data/sounds/boss/new_stage.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/boss/spawn.mp3")) spawn = manager.get("data/sounds/boss/spawn.mp3", Sound.class);
		if(manager.isLoaded("data/sounds/locked.wav")) locked = manager.get("data/sounds/locked.wav", Sound.class);


		//modify assets
		mountains_layer1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.MirroredRepeat);
		mountains_layer2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.MirroredRepeat);
		mountains_layer3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.MirroredRepeat);
		mountains_layer4.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.MirroredRepeat);
		boss_level_bg_1.setWrap(Texture.TextureWrap.Repeat, TextureWrap.MirroredRepeat);
		
		walking.setVolume(.6f);
		
		
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);

		fadeRenderer = new ShapeRenderer();
		fadeActor.setColor(0, 0, 0, 0);

		setScreenWithFade(new Menu(this), 1f);
	}

	public void setScreenWithFade(final Screen screen, float duration) {
		fadeActor.setColor(Color.CLEAR);

		fadeActor.addAction(Actions.sequence(Actions.color(Color.BLACK, duration / 2f, Interpolation.fade), Actions.run(new Runnable() {
			public void run() {
				setScreen(screen);
			}
		}), Actions.color(Color.CLEAR, duration / 2f, Interpolation.fade)));
	}

	public void render() {
		super.render();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		fadeActor.act(Gdx.graphics.getDeltaTime());
		float alpha = fadeActor.getColor().a;
		if (alpha != 0) {
			fadeRenderer.begin(ShapeType.Filled);
			fadeRenderer.setColor(0, 0, 0, alpha);
			fadeRenderer.rect(0, 0, (float)Gdx.graphics.getWidth() + 2000, 
						(float)Gdx.graphics.getWidth() + 2000);
			fadeRenderer.end();
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);		
	}

	@Override
	public void dispose() {
		super.dispose();
		coin.dispose();
		red_cb.dispose();
		orange_cb.dispose();
		yellow_cb.dispose();
		green_cb.dispose();
		blue_cb.dispose();
		pink_cb.dispose();
		red_jumppad.dispose();
		orange_jumppad.dispose();
		yellow_jumppad.dispose();
		green_jumppad.dispose();
		blue_jumppad.dispose();
		pink_jumppad.dispose();
		red_key.dispose();
		red_keyblock.dispose();
		orange_key.dispose();
		orange_keyblock.dispose();
		yellow_key.dispose();
		yellow_keyblock.dispose();
		green_key.dispose();
		green_keyblock.dispose();
		blue_key.dispose();
		blue_keyblock.dispose();
		pink_key.dispose();
		pink_keyblock.dispose();
		evile_left.dispose();
		evile_right.dispose();
		checkpoint_inactive.dispose();
		checkpoint_active.dispose();
		checkpoint_used.dispose();
		test.dispose();
		playerRed_idle.dispose();
		playerRed_jump.dispose();
		playerOrange_idle.dispose();
		playerOrange_jump.dispose();
		playerYellow_idle.dispose();
		playerYellow_jump.dispose();
		playerGreen_idle.dispose();
		playerGreen_jump.dispose();
		playerBlue_idle.dispose();
		playerBlue_jump.dispose();
		playerPink_idle.dispose();
		playerPink_jump.dispose();
		
		mountains_layer1.dispose();
		mountains_layer2.dispose();
		mountains_layer3.dispose();
		mountains_layer4.dispose();
		
		color_bucket.dispose();
		coin_collect.dispose();
		checkpoint.dispose();
		win.dispose();
		crash.dispose();
		woosh.dispose();
		walking.dispose();
		game_over.dispose();
		hurt.dispose();
		key.dispose();
		
		manager.dispose();
		fadeRenderer.dispose();
		
		// TODO Add all new sound effects and textures and music and stuff to this dispose thing (do the same in other classes that needs it)
	}

	public void resize(int width, int height) {}
	public void pause() {}
	public void resume() {}

	public SpriteBatch getSb() {
		return sb;
	}
	public OrthographicCamera getHudCam() {
		return hudCam;
	}
	public int getHealth(){
		return health;
	}
	public void setHealth(int hp){
		this.health = hp;
	}
	public int getRespawnColor() {
		return respawnColor;
	}
	public void setRespawnColor(int respawnColor) {
		this.respawnColor = respawnColor;
	}
	public boolean getSoundSetting(){
		return soundSetting;
	}
	public void setSoundSetting(boolean soundSetting) {
		this.soundSetting = soundSetting;
	}
	public Vector2 getRespawnPos(){
		return respawnPos;
	}
	public void setRespawnPos(Vector2 respawnPos){
		this.respawnPos = respawnPos;
	}
	public int getTime(){
		return time;
	}
	public void setTime(int newTime){
		this.time = newTime;
	}
	public void setTotalCoins(int coins){
		this.totalCoins = coins;
	}
	public int getTotalCoins(){
		return totalCoins;
	}
	public int getTempHealth() {
		return tempHealth;
	}
	public void setTempHealth(int tempHealth) {
		this.tempHealth = tempHealth;
	}
}
