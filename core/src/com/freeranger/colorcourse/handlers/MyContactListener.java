package com.freeranger.colorcourse.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.entities.BlueMovingBlock;
import com.freeranger.colorcourse.entities.Boss;
import com.freeranger.colorcourse.entities.Checkpoint;
import com.freeranger.colorcourse.entities.DarkEvilE;
import com.freeranger.colorcourse.entities.Doomfist;
import com.freeranger.colorcourse.entities.Door;
import com.freeranger.colorcourse.entities.EvilE;
import com.freeranger.colorcourse.entities.FallingRock;
import com.freeranger.colorcourse.entities.Ghost;
import com.freeranger.colorcourse.entities.GreenMovingBlock;
import com.freeranger.colorcourse.entities.OrangeMovingBlock;
import com.freeranger.colorcourse.entities.PinkMovingBlock;
import com.freeranger.colorcourse.entities.RangedEnemy;
import com.freeranger.colorcourse.entities.Reaper;
import com.freeranger.colorcourse.entities.RedMovingBlock;
import com.freeranger.colorcourse.entities.SpikeBall;
import com.freeranger.colorcourse.entities.TheShell;
import com.freeranger.colorcourse.entities.YellowMovingBlock;
import com.freeranger.colorcourse.screens.Play;

public class MyContactListener implements ContactListener {

	private boolean hasPlayedImpact = false;

	private int numFootContacts;
	private Array<Body> coinsToRemove;
	private Array<Body> redcolorbucketsToRemove;
	private Array<Body> orangecolorbucketsToRemove;
	private Array<Body> yellowcolorbucketsToRemove;
	private Array<Body> greencolorbucketsToRemove;
	private Array<Body> bluecolorbucketsToRemove;
	private Array<Body> pinkcolorbucketsToRemove;
	
	private Array<Body> bodiesToRemove;
	
	private Play play;
	private Main game;
	
	Class<EvilE> evilE = EvilE.class;
	String userdata = evilE.toString();
	
	Class<DarkEvilE> darkEvilE = DarkEvilE.class;
	String Darkuserdata = darkEvilE.toString();

	public MyContactListener(Main game, Play play) {
		super();
		coinsToRemove = new Array<Body>();
		redcolorbucketsToRemove = new Array<Body>();
		orangecolorbucketsToRemove = new Array<Body>();
		yellowcolorbucketsToRemove = new Array<Body>();
		greencolorbucketsToRemove = new Array<Body>();
		bluecolorbucketsToRemove = new Array<Body>();
		pinkcolorbucketsToRemove = new Array<Body>();
		bodiesToRemove = new Array<Body>();
			
		this.play = play;
		this.game = game;
	}
	
	private boolean canDie = true;

	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		// Boss head
		if(fa.getUserData() != null && fa.getUserData().equals("boss_head") && canDie){
			if(fb.getUserData() != null && fb.getUserData().equals("player")){
				fb.getBody().setLinearVelocity(new Vector2(fb.getBody().getLinearVelocity().x, 0f));
				if(((Boss)fa.getBody().getUserData()).isDamageImmune() && play.getIsBossAlive()){
					play.die();
					canDie = false;
				}else {
					fb.getBody().applyLinearImpulse(new Vector2(0, 20f), fb.getBody().getWorldCenter(), true);
					((Boss)fa.getBody().getUserData()).damage(1);
				}
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals("boss_head") && canDie){
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0f));
				if(((Boss)fb.getBody().getUserData()).isDamageImmune() && play.getIsBossAlive()){
					play.die();
					canDie = false;
				}else {
					fa.getBody().applyLinearImpulse(new Vector2(0, 20f), fb.getBody().getWorldCenter(), true);
					((Boss)fb.getBody().getUserData()).damage(1);
				}
			}
		}
		
		// Boss foot sensor
		if(fa.getUserData() != null && fa.getUserData().equals("boss_foot")){
			((Boss)fa.getBody().getUserData()).setGrounded(true);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("boss_foot")){
			((Boss)fb.getBody().getUserData()).setGrounded(true);
		}
		
		// Boss side sensors
		if(fa.getUserData() != null && fa.getUserData().equals("boss_side_sensor_left")){
			((Boss)fa.getBody().getUserData()).setDirection(1);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("boss_side_sensor_left")){
			((Boss)fb.getBody().getUserData()).setDirection(1);
		}

		if(fa.getUserData() != null && fa.getUserData().equals("boss_side_sensor_right")){
			((Boss)fa.getBody().getUserData()).setDirection(-1);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("boss_side_sensor_right")){
			((Boss)fb.getBody().getUserData()).setDirection(-1);
		}
		
		// Boss bullet
		if (fb.getUserData() != null && fb.getUserData().equals("boss_bullet") && canDie) {
			bodiesToRemove.add(fb.getBody());
			if (fa.getUserData() != null && fa.getUserData().equals("player") && play.getIsBossAlive()) {
				play.die();
				canDie = false;
			}
		}
		if (fa.getUserData() != null && fa.getUserData().equals("boss_bullet") && canDie) {
			bodiesToRemove.add(fa.getBody());
			if (fb.getUserData() != null && fb.getUserData().equals("player") && play.getIsBossAlive()) {
				play.die();
				canDie = false;
			}
		}

		// Boss
		if(fa.getUserData() != null && fa.getUserData().equals("player") && canDie){
			if(fb.getUserData() != null){
				if(fb.getUserData().equals("boss") || fb.getUserData().equals("boss_foot") || fb.getUserData().equals("boss_side_sensor_left") || fb.getUserData().equals("boss_side_sensor_right")  && play.getIsBossAlive()){
					play.die();
					canDie = false;

				}
			}
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("door")){
			System.out.println("fb");
			if(play.getPlayerX() < fb.getBody().getPosition().x + 16 && play.getPlayerX() > fb.getBody().getPosition().x - 16){
				((Door)fb.getBody().getUserData()).setHasPlayerOpened(true);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("falling_rock_foot")){
			 ((FallingRock)fa.getBody().getUserData()).set_is_on_ground(true);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("falling_rock_foot")){
			 ((FallingRock)fb.getBody().getUserData()).set_is_on_ground(true);
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("falling_rock_foot")){
			if(play.getPlayerX() < fb.getBody().getPosition().x + 10 && play.getPlayerX() > fb.getBody().getPosition().x - 10){
                play.shake.shake(0.2f);
    			game.falling_rock_impact.play(0.3f);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("reaper_foot")){
			 ((Reaper)fa.getBody().getUserData()).setOnGround(true);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("reaper_foot")){
			 ((Reaper)fb.getBody().getUserData()).setOnGround(true);
		}
		
		if (fa == null || fb == null)
			return;
		
		    if(!play.isCantDoStuff()){

			// foot sensor
			if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
				numFootContacts++;
				play.setJumpsLeft(2);
				if(!hasPlayedImpact)game.impact_01.play();
				hasPlayedImpact = true;
			}
			if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
				numFootContacts++;
				play.setJumpsLeft(2);
				if(!hasPlayedImpact)game.impact_01.play();
				hasPlayedImpact = true;
			}

			if(!isPlayerOnGround()){
				hasPlayedImpact = false;
			}

			// coins
			if (fa.getUserData() != null && fa.getUserData().equals("coin")) {
				coinsToRemove.add(fa.getBody());
				bodiesToRemove.add(fa.getBody());
			}
			if (fb.getUserData() != null && fb.getUserData().equals("coin")) {
				coinsToRemove.add(fb.getBody());
				bodiesToRemove.add(fb.getBody());
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("falling_rock") || fb.getUserData() != null && fb.getUserData().equals("falling_rock_foot")){
					play.die();
				}
			}
			
			// Moving Blocks
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("red_moving_block")){
                	((RedMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 1){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("red_moving_block")){
                	if(play.getPlayerColor() != 1){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("orange_moving_block")){
                	((OrangeMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 2){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("orange_moving_block")){
                	if(play.getPlayerColor() != 2){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("yellow_moving_block")){
                	((YellowMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 3){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("yellow_moving_block")){
                	if(play.getPlayerColor() != 3){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("green_moving_block")){
                	((GreenMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 4){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("green_moving_block")){
                	if(play.getPlayerColor() != 4){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("blue_moving_block")){
                	((BlueMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 5){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("blue_moving_block")){
                	if(play.getPlayerColor() != 5){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if(fb.getUserData() != null && fb.getUserData().equals("pink_moving_block")){
                	((PinkMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(true);
                	if(play.getPlayerColor() != 6){
    					play.die();
    				}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if(fb.getUserData() != null && fb.getUserData().equals("pink_moving_block")){
                	if(play.getPlayerColor() != 6){
    					play.die();
    				}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("spike_ball")) {
                	((SpikeBall)fb.getBody().getUserData()).setPlayerOnPlatform(true);
					play.die();
				}
			}
			
			// heart powerup
			if (fb.getUserData() != null && fb.getUserData().equals("heart_powerup")) {
				if(play.getPlayerHealth() < 3){
					bodiesToRemove.add(fb.getBody());
					Gdx.app.log(Log.EVENT, "+1 HP");
					play.setPlayerHealth(play.getPlayerHealth() + 1);
					game.heart_powerup_sound.play();
				}
			}
			
			// speed powerup
			if (fb.getUserData() != null && fb.getUserData().equals("speed_powerup")) {
				if(!play.isPlayerFast()){
					bodiesToRemove.add(fb.getBody());
					Gdx.app.log(Log.EVENT, "Speed Powerup");
					play.setPlayerFast(true);
					game.speed_powerup_sound.play();
				}
			}
			
			// jump powerup
			if (fb.getUserData() != null && fb.getUserData().equals("jump_powerup")) {
				if(!play.isCanDoubleJump()){
					bodiesToRemove.add(fb.getBody());
					Gdx.app.log(Log.EVENT, "Jump Powerup");
					play.setCanDoubleJump(true);
					game.jump_powerup_sound.play();
				}
			}
			
			// jump powerup
			if (fb.getUserData() != null && fb.getUserData().equals("temp_health_powerup")) {
				if(game.getTempHealth() < 2){
					bodiesToRemove.add(fb.getBody());
					Gdx.app.log(Log.EVENT, "Temp Health Powerup");
					game.setTempHealth(game.getTempHealth() + 1);
					game.temp_heart_powerup_sound.play();
				}
			}

			// Evil-E
			String[] udList = {"red", "orange", "yellow", "green", "blue", "pink", "orange_gravity_block",
					           "evile_left_sensor", "evile_right_sensor", "blockers", "red_jumppad",
					           "orange_jumppad", "yellow_jumppad", "green_jumppad", "blue_jumppad", "pink_jumppad",
					           "red_keyblock", "barrel", "normalblocks", "dark_evile_left_sensor", "dark_evile_right_sensor", 
					           "deathblocks", "the_shell_left_sensor", "the_shell_right_sensor", "ranged_enemy"};

				for (String s : udList) {
					if (fa.getUserData() != null && fa.getUserData().equals(s)) {
						if (fb.getUserData() != null && fb.getUserData().equals("evile_left_sensor")) {
							((EvilE) fb.getBody().getUserData()).setVelocity(0.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("evile_right_sensor")) {
								((EvilE) fa.getBody().getUserData()).setVelocity(-0.5f);
							}
						} else if (fb.getUserData() != null && fb.getUserData().equals("evile_right_sensor")) {
							((EvilE) fb.getBody().getUserData()).setVelocity(-0.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("evile_left_sensor")) {
								((EvilE) fa.getBody().getUserData()).setVelocity(0.5f);
							}
						}
					}
					if (fb.getUserData() != null && fb.getUserData().equals(s)) {
						if (fa.getUserData() != null && fa.getUserData().equals("evile_left_sensor")) {
							((EvilE) fa.getBody().getUserData()).setVelocity(0.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("evile_right_sensor")) {
								((EvilE) fa.getBody().getUserData()).setVelocity(-0.5f);
							}
						} else if (fa.getUserData() != null && fa.getUserData().equals("evile_right_sensor")) {
							((EvilE) fa.getBody().getUserData()).setVelocity(-0.5f);
							if (fb.getUserData() != null && fb.getUserData().equals("evile_left_sensor")) {
								((EvilE) fb.getBody().getUserData()).setVelocity(0.5f);
							}
						}
					}
				}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("evile_remove") && fa.getBody().getLinearVelocity().y < 0) {
					game.slime_01.play();
					bodiesToRemove.add(fb.getBody());
					((EvilE)fb.getBody().getUserData()).setDead(true);
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					fa.getBody().applyLinearImpulse(new Vector2(0, 8.5f), fa.getBody().getWorldCenter(), true);
					play.createEvilEDeathParticleEffect(fb.getBody().getPosition());
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("evile_death_sensor") && !((EvilE)fb.getBody().getUserData()).getDead()) {
					Gdx.app.log(Log.EVENT, "Death by Evil-E");
					play.die();
				}
			}
			
			// Dark Evil-E		
				for (String item : udList) {
					if (fa.getUserData() != null && fa.getUserData().equals(item)) {
						if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_left_sensor")) {
							((DarkEvilE) fb.getBody().getUserData()).setVelocity(1f);
							if (fa.getUserData() != null && fa.getUserData().equals("dark_evile_right_sensor")) {
								((DarkEvilE) fa.getBody().getUserData()).setVelocity(-1f);
							}
						} else if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_right_sensor")) {
							((DarkEvilE) fb.getBody().getUserData()).setVelocity(-1f);
							if (fa.getUserData() != null && fa.getUserData().equals("dark_evile_left_sensor")) {
								((DarkEvilE) fa.getBody().getUserData()).setVelocity(1f);
							}
						}
					}
					if (fb.getUserData() != null && fb.getUserData().equals(item)) {
						if (fa.getUserData() != null && fa.getUserData().equals("dark_evile_left_sensor")) {
							((DarkEvilE) fa.getBody().getUserData()).setVelocity(1f);
							if (fa.getUserData() != null && fa.getUserData().equals("dark_evile_right_sensor")) {
								((DarkEvilE) fa.getBody().getUserData()).setVelocity(-1f);
							}
						} else if (fa.getUserData() != null && fa.getUserData().equals("dark_evile_right_sensor")) {
							((DarkEvilE) fa.getBody().getUserData()).setVelocity(-1f);
							if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_left_sensor")) {
								((DarkEvilE) fb.getBody().getUserData()).setVelocity(1f);
							}
						}
					}
				}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_remove") && fa.getBody().getLinearVelocity().y < 0) {
					game.slime_01.play();
					((DarkEvilE)fb.getBody().getUserData()).setCanKill(false);
					DarkEvilE ud = ((DarkEvilE)fb.getBody().getUserData());
					ud.setHp(ud.getHp() - 1);
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					if(ud.getHp() <= 0){
						bodiesToRemove.add(fb.getBody());
						((DarkEvilE)fb.getBody().getUserData()).setDead(true);
						fa.getBody().applyLinearImpulse(new Vector2(0, 8.5f), fa.getBody().getWorldCenter(), true);
						play.createDarkEvilEDeathParticleEffect(fb.getBody().getPosition());
					}else {
						fa.getBody().applyLinearImpulse(new Vector2(0, 5.5f), fa.getBody().getWorldCenter(), true);
					}
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_death_sensor") && !((DarkEvilE)fb.getBody().getUserData()).getDead() && ((DarkEvilE)fb.getBody().getUserData()).getCanKill()) {
					Gdx.app.log(Log.EVENT, "Death by Dark Evil-E");
					play.die();
				}
			}
			
			// The Shell
				for (String value : udList) {
					if (fa.getUserData() != null && fa.getUserData().equals(value)) {
						if (fb.getUserData() != null && fb.getUserData().equals("the_shell_left_sensor")) {
							((TheShell) fb.getBody().getUserData()).setVelocity(1.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("the_shell_right_sensor")) {
								((TheShell) fa.getBody().getUserData()).setVelocity(-1.5f);
							}
						} else if (fb.getUserData() != null && fb.getUserData().equals("the_shell_right_sensor")) {
							((TheShell) fb.getBody().getUserData()).setVelocity(-1.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("the_shell_left_sensor")) {
								((TheShell) fa.getBody().getUserData()).setVelocity(1.5f);
							}
						}
					}
					if (fb.getUserData() != null && fb.getUserData().equals(value)) {
						if (fa.getUserData() != null && fa.getUserData().equals("the_shell_left_sensor")) {
							((TheShell) fa.getBody().getUserData()).setVelocity(1.5f);
							if (fa.getUserData() != null && fa.getUserData().equals("the_shell_right_sensor")) {
								((TheShell) fa.getBody().getUserData()).setVelocity(-1.5f);
							}
						} else if (fa.getUserData() != null && fa.getUserData().equals("the_shell_right_sensor")) {
							((TheShell) fa.getBody().getUserData()).setVelocity(-1.5f);
							if (fb.getUserData() != null && fb.getUserData().equals("the_shell_left_sensor")) {
								((TheShell) fb.getBody().getUserData()).setVelocity(1.5f);
							}
						}
					}
				}
			
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("the_shell_remove") && fa.getBody().getLinearVelocity().y < 0) {
					TheShell ud = ((TheShell)fb.getBody().getUserData());
					ud.setCanChangeState(false);
					if(ud.isMoving()){
						ud.setMoving(false);
					}else {
						ud.setMoving(true);
					}
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					fa.getBody().applyLinearImpulse(new Vector2(0, 8.5f), fa.getBody().getWorldCenter(), true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("the_shell") && ((TheShell)fb.getBody().getUserData()).isCanChangeState()) {
					Gdx.app.log(Log.EVENT, "Death by The Shell");
					play.die();
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("the_shell")) {
					Gdx.app.log(Log.EVENT, "Death by The Shell");
					play.die();
				}
			}
			
			// Ranged enemy
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("ranged_enemy_remove") && fa.getBody().getLinearVelocity().y < 0) {
					bodiesToRemove.add(fb.getBody());
					game.slime_01.play();
					((RangedEnemy)fb.getBody().getUserData()).setDead(true);
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					fa.getBody().applyLinearImpulse(new Vector2(0, 8.5f), fa.getBody().getWorldCenter(), true);
					play.createEvilEDeathParticleEffect(fb.getBody().getPosition());
				}
			}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("ranged_enemy_death_sensor") && !((RangedEnemy)fb.getBody().getUserData()).getDead()) {
					Gdx.app.log(Log.EVENT, "Death by Ranged Enemy");
					play.die();
				}
			}
			
			// Ranged Enemy Bullet Logic
			if (fb.getUserData() != null && fb.getUserData().equals("ranged_enemy_bullet")) {
				bodiesToRemove.add(fb.getBody());
				if (fa.getUserData() != null && fa.getUserData().equals("player")) {
					play.die();
				}
			}
			if (fa.getUserData() != null && fa.getUserData().equals("ranged_enemy_bullet")) {
				bodiesToRemove.add(fa.getBody());
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					play.die();
				}
			}
			
			// plant of doom
			if (fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("plant_of_doom")) {
					play.die();
				}
			}
			
			// Ghost
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("ghost_remove") && fa.getBody().getLinearVelocity().y < 0) {
					((Ghost)fb.getBody().getUserData()).setCanKill(false);
					Ghost ud = ((Ghost)fb.getBody().getUserData());
					ud.setHp(ud.getHp() - 1);
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					if(ud.getHp() <= 0){
						bodiesToRemove.add(fb.getBody());
						((Ghost)fb.getBody().getUserData()).setDead(true);
						fa.getBody().applyLinearImpulse(new Vector2(0, 10.6f), fa.getBody().getWorldCenter(), true);
						game.smoke_02.play();
						play.createGhostDeathParticleEffect(fb.getBody().getPosition());
					}else {
						fa.getBody().applyLinearImpulse(new Vector2(0, 7.5f), fa.getBody().getWorldCenter(), true);
						game.smoke_01.play();
					}
				}
			}
			if(fa.getUserData() != null && fa.getUserData().equals("player") && canDie){
				if (fb.getUserData() != null && fb.getUserData().equals("ghost") && !((Ghost)fb.getBody().getUserData()).getDead() && ((Ghost)fb.getBody().getUserData()).getCanKill()) {
					Gdx.app.log(Log.EVENT, "Death by Ghost");
					play.die();
				}
			}
				
			//Doomfist
			String[] doomfistUdList = {"red", "orange", "yellow", "green", "blue", "pink",
			           "evile_left_sensor", "evile_right_sensor", "red_jumppad",
			           "orange_jumppad", "yellow_jumppad", "green_jumppad", "blue_jumppad", "pink_jumppad",
			           "red_keyblock", "barrel", "normalblocks", "dark_evile_left_sensor", "dark_evile_right_sensor", 
			           "deathblocks", "the_shell_left_sensor", "the_shell_right_sensor", "ranged_enemy"};
				for (String s : doomfistUdList) {
					if (fa.getUserData() != null && fa.getUserData().equals(s)) {
						if (fb.getUserData() != null && fb.getUserData().equals("doomfist_left_sensor")) {
							((Doomfist) fb.getBody().getUserData()).setVelocityX(1f);
							if (fa.getUserData() != null && fa.getUserData().equals("doomfist_right_sensor")) {
								((Doomfist) fa.getBody().getUserData()).setVelocityX(-1f);
							}
						} else if (fb.getUserData() != null && fb.getUserData().equals("doomfist_right_sensor")) {
							((Doomfist) fb.getBody().getUserData()).setVelocityX(-1f);
							if (fa.getUserData() != null && fa.getUserData().equals("doomfist_left_sensor")) {
								((Doomfist) fa.getBody().getUserData()).setVelocityX(1f);
							}
						}
					}
					if (fb.getUserData() != null && fb.getUserData().equals(s)) {
						if (fa.getUserData() != null && fa.getUserData().equals("doomfist_left_sensor")) {
							((Doomfist) fa.getBody().getUserData()).setVelocityX(1f);
							if (fa.getUserData() != null && fa.getUserData().equals("doomfist_right_sensor")) {
								((Doomfist) fa.getBody().getUserData()).setVelocityX(-1f);
							}
						} else if (fa.getUserData() != null && fa.getUserData().equals("doomfist_right_sensor")) {
							((Doomfist) fa.getBody().getUserData()).setVelocityX(-1f);
							if (fb.getUserData() != null && fb.getUserData().equals("doomfist_left_sensor")) {
								((Doomfist) fb.getBody().getUserData()).setVelocityX(1f);
							}
						}
					}
				}
			
			if(fa.getUserData() != null && fa.getUserData().equals("player") || fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("doomfist")) {
					Gdx.app.log(Log.EVENT, "Death by Doomfist");
					play.die();
				}
			}
			
			//the pipe
			if (fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("the_pipe_ball_collision")) {
					play.die();
				}
			}
			
			//reaper
			if(fa.getUserData() != null && fa.getUserData().equals("foot")){
				if (fb.getUserData() != null && fb.getUserData().equals("reaper_remove") && fa.getBody().getLinearVelocity().y < 0) {
					((Reaper)fb.getBody().getUserData()).setCanKill(false);
					Reaper ud = ((Reaper)fb.getBody().getUserData());
					ud.setHp(ud.getHp() - 1);
					fa.getBody().setLinearVelocity(new Vector2(fa.getBody().getLinearVelocity().x, 0));
					if(ud.getHp() <= 0){
						bodiesToRemove.add(fb.getBody());
						((Reaper)fb.getBody().getUserData()).setDead(true);
						fa.getBody().applyLinearImpulse(new Vector2(0, 8.5f), fa.getBody().getWorldCenter(), true);
						play.createReaperDeathParticleEffect(fb.getBody().getPosition());
					}else {
						fa.getBody().applyLinearImpulse(new Vector2(0, 5.5f), fa.getBody().getWorldCenter(), true);
					}
				}
			}
			 
			if(fa.getUserData() != null && fa.getUserData().equals("player")){
				if (fb.getUserData() != null && fb.getUserData().equals("reaper") || fb.getUserData() != null && fb.getUserData().equals("reaper_foot")){
					if(!((Reaper)fb.getBody().getUserData()).getDead() && ((Reaper)fb.getBody().getUserData()).getCanKill()) {
						Gdx.app.log(Log.EVENT, "Death by Reaper");
						play.die();
					}
				}		
			}
			
			// flag (victory)
			if (fa.getUserData() != null && fa.getUserData().equals("flag")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Level complete!");
					play.win();
				}
			}
	
			// red color bucket
			if (play.getPlayerColor() != 1) {
				if (fa.getUserData() != null && fa.getUserData().equals("red_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Red");
					play.setPlayerColor(1);
					redcolorbucketsToRemove.add(fa.getBody());
					play.setColor(1);
					bodiesToRemove.add(fa.getBody());
				}
				if (fb.getUserData() != null && fb.getUserData().equals("red_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Red");
					play.setPlayerColor(1);
					redcolorbucketsToRemove.add(fb.getBody());
					play.setColor(1);
					bodiesToRemove.add(fb.getBody());
				}
			}

			// orange color bucket
			if (play.getPlayerColor() != 2) {
				if (fa.getUserData() != null && fa.getUserData().equals("orange_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Orange");
					play.setPlayerColor(2);
					orangecolorbucketsToRemove.add(fa.getBody());
					play.setColor(2);
					
					bodiesToRemove.add(fa.getBody());
				}
				if (fb.getUserData() != null && fb.getUserData().equals("orange_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Orange");
					play.setPlayerColor(2);
					orangecolorbucketsToRemove.add(fb.getBody());
					play.setColor(2);
					
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// yellow color bucket
			if (play.getPlayerColor() != 3) {
				if (fa.getUserData() != null && fa.getUserData().equals("yellow_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Yellow");
					play.setPlayerColor(3);
					yellowcolorbucketsToRemove.add(fa.getBody());
					play.setColor(3);
					bodiesToRemove.add(fa.getBody());
				}
			   	if (fb.getUserData() != null && fb.getUserData().equals("yellow_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Yellow");
					play.setPlayerColor(3);
					yellowcolorbucketsToRemove.add(fb.getBody());
					play.setColor(3);
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// green color bucket
			if (play.getPlayerColor() != 4) {
				if (fa.getUserData() != null && fa.getUserData().equals("green_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Green");
					play.setPlayerColor(4);
					greencolorbucketsToRemove.add(fa.getBody());
					play.setColor(4);
					bodiesToRemove.add(fa.getBody());
				}
		     	if (fb.getUserData() != null && fb.getUserData().equals("green_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Green");
					play.setPlayerColor(4);
					greencolorbucketsToRemove.add(fb.getBody());
					play.setColor(4);
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// blue color bucket
			if (play.getPlayerColor() != 5) {
				if (fa.getUserData() != null && fa.getUserData().equals("blue_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Blue");
					play.setPlayerColor(5);
					bluecolorbucketsToRemove.add(fa.getBody());
					play.setColor(5);
					bodiesToRemove.add(fa.getBody());
				}
				if (fb.getUserData() != null && fb.getUserData().equals("blue_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Blue");
					play.setPlayerColor(5);
					bluecolorbucketsToRemove.add(fb.getBody());
					play.setColor(5);
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// pink color bucket
			if (play.getPlayerColor() != 6) {
				if (fa.getUserData() != null && fa.getUserData().equals("pink_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Pink");
					play.setPlayerColor(6);
					pinkcolorbucketsToRemove.add(fa.getBody());
					play.setColor(6);
					bodiesToRemove.add(fa.getBody());
				}
			   	if (fb.getUserData() != null && fb.getUserData().equals("pink_color_bucket")) {
					Gdx.app.log(Log.COLOR_CHANGE, "Pink");
					play.setPlayerColor(6);
					pinkcolorbucketsToRemove.add(fb.getBody());
					play.setColor(6);
					bodiesToRemove.add(fb.getBody());
				}
			}

			// red
			if(play.getPlayerColor() == 0) return;
			if (play.getPlayerColor() != 1 && fa.getUserData() != null && fa.getUserData().equals("red")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by red block");
					play.die();
				}
			}

			// orange
			if (play.getPlayerColor() != 2 && fa.getUserData() != null && fa.getUserData().equals("orange")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by orange block");
					play.die();
				}
			}

			// yellow
			if (play.getPlayerColor() != 3 && fa.getUserData() != null && fa.getUserData().equals("yellow")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by yellow block");
					play.die();
				}
			}

			// green
			if (play.getPlayerColor() != 4 && fa.getUserData() != null && fa.getUserData().equals("green")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log("[DEATH]", "Death by green block");
					play.die();
				}
			}

			// blue
			if (play.getPlayerColor() != 5 && fa.getUserData() != null && fa.getUserData().equals("blue")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by blue block");
					play.die();
				}
			}
			
			// pink
			if (play.getPlayerColor() != 6 && fa.getUserData() != null && fa.getUserData().equals("pink")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by pink block");
					play.die();
				}
			}
			
			// spike
			if (fa.getUserData() != null && fa.getUserData().equals("spikes")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by spike");
					play.die();
				}
			}
			
			// death block
			if (fa.getUserData() != null && fa.getUserData().equals("deathblocks")) {
				if (fb.getUserData() != null && fb.getUserData().equals("player")) {
					Gdx.app.log(Log.EVENT, "Death by a \"death block\".");
					play.die();
				}
			}

			// red jumppad and red keyblock
			if (play.getPlayerColor() != 1 && fb.getUserData() != null){
				if(fb.getUserData().equals("red_jumppad") || fb.getUserData().equals("red_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by red jumppad or key block");
						play.die();
					}
				}
			}
			
			// orange jumppad and orange keyblock
			if (play.getPlayerColor() != 2 && fb.getUserData() != null){
				if(fb.getUserData().equals("orange_jumppad") || fb.getUserData().equals("orange_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by orange jumppad or key block");
						play.die();
					}
				}
			}
			
			// yellow jumppad and yellow keyblock
			if (play.getPlayerColor() != 3 && fb.getUserData() != null){
				if(fb.getUserData().equals("yellow_jumppad") || fb.getUserData().equals("yellow_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by yellow jumppad or key block");
						play.die();
					}
				}
			}
			
			// green jumppad and green keyblock
			if (play.getPlayerColor() != 4 && fb.getUserData() != null){
				if(fb.getUserData().equals("green_jumppad") || fb.getUserData().equals("green_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by green jumppad or key block");
						play.die();
					}
				}
			}
			
			// blue jumppad and blue keyblock
			if (play.getPlayerColor() != 5 && fb.getUserData() != null){
				if(fb.getUserData().equals("blue_jumppad") || fb.getUserData().equals("blue_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by blue jumppad or key block");
						play.die();
					}
				}
			}
			
			// pink jumppad and pink keyblock
			if (play.getPlayerColor() != 6 && fb.getUserData() != null){
				if(fb.getUserData().equals("pink_jumppad") || fb.getUserData().equals("pink_keyblock")) {
					if (fa.getUserData() != null && fa.getUserData().equals("player")) {
						Gdx.app.log(Log.EVENT, "Death by pink jumppad or key block");
						play.die();
					}
				}
			}

			// checkpoint 
			if (fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("checkpoint")) {
					if(game.getRespawnPos().x != ((Checkpoint)fb.getBody().getUserData()).getPosition().x 
							|| game.getRespawnPos().y != ((Checkpoint)fb.getBody().getUserData()).getPosition().y ) {
						
						if(game.getSoundSetting()) game.checkpoint.play(1f);
						
						play.setSpawnpoint(((Checkpoint)fb.getBody().getUserData()).getPosition().x,
								((Checkpoint)fb.getBody().getUserData()).getPosition().y);
						
						game.setRespawnColor(((Checkpoint)fb.getBody().getUserData()).color);
					}
				}
			}
			
			// red key
			if (play.getPlayerColor() == 1 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("red_key")) {
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// orange key
			if (play.getPlayerColor() == 2 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("orange_key")) {
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// yellow key
			if (play.getPlayerColor() == 3 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("yellow_key")) {
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			// green key
			if (play.getPlayerColor() == 4 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("green_key")) {
						bodiesToRemove.add(fb.getBody());
				}
			}
			
			// blue key
			if (play.getPlayerColor() == 5 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("blue_key")) {
					bodiesToRemove.add(fb.getBody());
				}
			}
						
			// pink key
			if (play.getPlayerColor() == 6 && fa.getUserData() != null && fa.getUserData().equals("player")) {
				if (fb.getUserData() != null && fb.getUserData().equals("pink_key")) {
					bodiesToRemove.add(fb.getBody());
				}
			}
			
			//jump pads
			if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
				if (fb.getUserData() != null && fb.getUserData().equals("red_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("orange_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("yellow_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("green_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("blue_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}else if (fb.getUserData() != null && fb.getUserData().equals("pink_jumppad_sensor")) {
					play.setOnJumpPad(true);
				}
			}
		} 
	}

	public void endContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		// Boss foot sensor
		if(fa.getUserData() != null && fa.getUserData().equals("boss_foot")){
			((Boss)fa.getBody().getUserData()).setGrounded(false);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("boss_foot")){
			((Boss)fb.getBody().getUserData()).setGrounded(false);
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("door")){
			((Door)fb.getBody().getUserData()).setHasPlayerOpened(false);	
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("falling_rock_foot")){
			 ((FallingRock)fa.getBody().getUserData()).set_is_on_ground(false);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("falling_rock_foot")){
			 ((FallingRock)fb.getBody().getUserData()).set_is_on_ground(false);
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("reaper_foot")){
			 ((Reaper)fa.getBody().getUserData()).setOnGround(false);
		}
		if(fb.getUserData() != null && fb.getUserData().equals("reaper_foot")){
			 ((Reaper)fb.getBody().getUserData()).setOnGround(false);
		}

		if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
		}
		
		//jump pads
		if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
			if (fb.getUserData() != null && fb.getUserData().equals("red_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}else if (fb.getUserData() != null && fb.getUserData().equals("orange_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}else if (fb.getUserData() != null && fb.getUserData().equals("yellow_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}else if (fb.getUserData() != null && fb.getUserData().equals("green_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}else if (fb.getUserData() != null && fb.getUserData().equals("blue_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}else if (fb.getUserData() != null && fb.getUserData().equals("pink_jumppad_sensor")) {
				play.setOnJumpPad(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("red_moving_block")){
            	((RedMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("orange_moving_block")){
            	((OrangeMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("yellow_moving_block")){
            	((YellowMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("green_moving_block")){
            	((GreenMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("blue_moving_block")){
            	((BlueMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("pink_moving_block")){
            	((PinkMovingBlock)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if(fb.getUserData() != null && fb.getUserData().equals("spike_ball")){
            	((SpikeBall)fb.getBody().getUserData()).setPlayerOnPlatform(false);
			}
		}
			
		// Dark Evil-E
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if (fb.getUserData() != null && fb.getUserData().equals("dark_evile_remove")) {
				((DarkEvilE)fb.getBody().getUserData()).setCanKill(true);
			}
		}
		
		// Ghost
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if (fb.getUserData() != null && fb.getUserData().equals("ghost_remove")) {
				((Ghost)fb.getBody().getUserData()).setCanKill(true);
			}
		}
		
		// Reaper
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if (fb.getUserData() != null && fb.getUserData().equals("reaper_remove")) {
				((Reaper)fb.getBody().getUserData()).setCanKill(true);
			}
		}
		
		// The Shell
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			if (fb.getUserData() != null && fb.getUserData().equals("the_shell_remove")) {
				((TheShell)fb.getBody().getUserData()).setCanChangeState(true);		
			}
		}
	}

	public boolean isPlayerOnGround() {
		return numFootContacts > 0;
	}
	public Array<Body> getCoinsToRemove() {
		return coinsToRemove;
	}
	public Array<Body> getRedColorBucketsToRemove() {
		return redcolorbucketsToRemove;
	}
    public Array<Body> getOrangeColorBucketsToRemove() {
		return orangecolorbucketsToRemove;
	}
	public Array<Body> getYellowColorBucketsToRemove() {
		return yellowcolorbucketsToRemove;
	}
	public Array<Body> getGreenColorBucketsToRemove() {
		return greencolorbucketsToRemove;
	}
	public Array<Body> getBlueColorBucketsToRemove() {
		return bluecolorbucketsToRemove;
	}
	public Array<Body> getPinkColorBucketsToRemove() {
		return pinkcolorbucketsToRemove;
	}
	public Array<Body> getBodiesToRemove() {
		return bodiesToRemove;
	}
	
	public void addBodyToRemove(Body body) {
		bodiesToRemove.add(body);
	}

	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
}
