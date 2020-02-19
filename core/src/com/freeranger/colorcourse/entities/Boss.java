package com.freeranger.colorcourse.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.handlers.Shake;
import com.freeranger.colorcourse.screens.GameCompleted;
import com.freeranger.colorcourse.screens.Play;

import static com.freeranger.colorcourse.handlers.B2DVars.PPM;

public class Boss {
	
	@SuppressWarnings("unused")
	private Main game;
	private Play play;
	private Body body;

	private boolean finalTimerSet = false;
	
	private enum State {
		MOVING,
		ATTACKING,
		DEAD
	}
	
	private State state;
		
	private Texture left;
	private Texture right;
	private Texture left_immune;
	private Texture right_immune;
	private Texture left_stage2;
	private Texture right_stage2;
	private Texture left_immune_stage2;
	private Texture right_immune_stage2;
	private Texture left_stage3;
	private Texture right_stage3;
	private Texture left_immune_stage3;
	private Texture right_immune_stage3;
	private Texture left_stage4;
	private Texture right_stage4;
	private Texture left_immune_stage4;
	private Texture right_immune_stage4;
	
	private boolean isGrounded = false;
	private int direction = -1; // -1 = left, 1 = right
	private Random r;
	
	private int stateTimer = 300;
	private final int MOVING_STATE_TIME = 375;
	
	private int attackIndex = 0;	
	private boolean attacked = false;
	private boolean startedAttack = false;
	private boolean lerpedToStill = false;
	
	//normal attack variables
	private int newBulletTime = 0; // Time left to spawn another bullet
	private final int defaultNewBulletTime = 30; // Default value for newBulletTime
	private int bulletsLeft = 10; // Bullets left to fire
	
	private int stage = 1;
	private int hp = 10;
	private int damageImmuneTimer = 0;
	private final int defaultDamageImmuneTimer = 100;
	private int timeUntilDamageImmune = 20;
	private final int defaultTimeUntilDamageImmune = 20;
	private boolean damaged = false;

	private final int defaultNewBulletWaveTime = 60;
    private int newBulletWaveTime = 0;
    private int bulletWavesLeft = 3;

    private int ghostSpawnTimer = 10;
    private final int defaultGhostSpawnTimer = 10;
    private int afterGhostSpawnTimer = 30;
    private final int defaultAfterGhostSpawnTimer = 30;

	private final Vector2 PLACE_HOLDER = Vector2.Zero;
    private int specialAttackJumpTimer = 0;
    private int randAttackTime = 0;
    private int waitTimer = 0;
    private float finalTimer = 5f;
    private int facingDirection = -1;
    
    private Shake shake;

	// Boss Death Particle
	private ParticleEffect bossDeathParticle;
	private ParticleEffectPool bossDeathParticlePool;
	private Array<ParticleEffectPool.PooledEffect> bossDeathParticleEffects;

	// Red Confetti
	private ParticleEffect redConfetti;
	private ParticleEffectPool redConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> redConfettiEffects;

	// Orange Confetti
	private ParticleEffect orangeConfetti;
	private ParticleEffectPool orangeConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> orangeConfettiEffects;

	// Yellow Confetti
	private ParticleEffect yellowConfetti;
	private ParticleEffectPool yellowConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> yellowConfettiEffects;

	// Green Confetti
	private ParticleEffect greenConfetti;
	private ParticleEffectPool greenConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> greenConfettiEffects;

	// Light Blue Confetti
	private ParticleEffect lightblueConfetti;
	private ParticleEffectPool lightblueConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> lightblueConfettiEffects;

	// Blue Confetti
	private ParticleEffect blueConfetti;
	private ParticleEffectPool blueConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> blueConfettiEffects;

	// Pink Confetti
	private ParticleEffect pinkConfetti;
	private ParticleEffectPool pinkConfettiPool;
	private Array<ParticleEffectPool.PooledEffect> pinkConfettiEffects;

	public Boss(Body body, Main game, Play play) {
		this.game = game;
		this.body = body;
		this.play = play;

		shake = new Shake(60);
		
		left = game.boss_idle_left_stage1;
		right = game.boss_idle_right_stage1;
		left.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		left_immune = game.boss_immune_left_stage1;
		right_immune = game.boss_immune_right_stage1;
		left_immune.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_immune.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		left_stage2 = game.boss_idle_left_stage2;
		right_stage2 = game.boss_idle_right_stage2;
		left_stage2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_stage2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		left_immune_stage2 = game.boss_immune_left_stage2;
		right_immune_stage2 = game.boss_immune_right_stage2;
		left_immune_stage2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_immune_stage2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		left_stage3 = game.boss_idle_left_stage3;
		right_stage3 = game.boss_idle_right_stage3;
		left_stage3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_stage3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		left_immune_stage3 = game.boss_immune_left_stage3;
		right_immune_stage3 = game.boss_immune_right_stage3;
		left_immune_stage3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_immune_stage3.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		left_stage4 = game.boss_idle_left_stage4;
		right_stage4 = game.boss_idle_right_stage4;
		left_stage4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_stage4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		left_immune_stage4 = game.boss_immune_left_stage4;
		right_immune_stage4 = game.boss_immune_right_stage4;
		left_immune_stage4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right_immune_stage4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		r = new Random();
		
		state = State.MOVING;

		game.boss_landing.setVolume(0.3f);

		// Boss Death Effect
		bossDeathParticle = new ParticleEffect();
		bossDeathParticle.load(Gdx.files.internal("data/particles/boss_death.gdx"), Gdx.files.internal("data/particles"));
		bossDeathParticle.start();
		bossDeathParticlePool = new ParticleEffectPool(bossDeathParticle, 0, 5);
		bossDeathParticleEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Red Confetti Effect
		redConfetti = new ParticleEffect();
		redConfetti.load(Gdx.files.internal("data/particles/confetti/red_confetti.gdx"), Gdx.files.internal("data/particles"));
		redConfetti.start();
		redConfettiPool = new ParticleEffectPool(redConfetti, 0, 500);
		redConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Orange Confetti Effect
		orangeConfetti = new ParticleEffect();
		orangeConfetti.load(Gdx.files.internal("data/particles/confetti/orange_confetti.gdx"), Gdx.files.internal("data/particles"));
		orangeConfetti.start();
		orangeConfettiPool = new ParticleEffectPool(orangeConfetti, 0, 500);
		orangeConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Yellow Confetti Effect
		yellowConfetti = new ParticleEffect();
		yellowConfetti.load(Gdx.files.internal("data/particles/confetti/yellow_confetti.gdx"), Gdx.files.internal("data/particles"));
		yellowConfetti.start();
		yellowConfettiPool = new ParticleEffectPool(yellowConfetti, 0, 500);
		yellowConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Green Confetti Effect
		greenConfetti = new ParticleEffect();
		greenConfetti.load(Gdx.files.internal("data/particles/confetti/green_confetti.gdx"), Gdx.files.internal("data/particles"));
		greenConfetti.start();
		greenConfettiPool = new ParticleEffectPool(greenConfetti, 0, 500);
		greenConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Light Blue Confetti Effect
		lightblueConfetti = new ParticleEffect();
		lightblueConfetti.load(Gdx.files.internal("data/particles/confetti/lightblue_confetti.gdx"), Gdx.files.internal("data/particles"));
		lightblueConfetti.start();
		lightblueConfettiPool = new ParticleEffectPool(lightblueConfetti, 0, 500);
		lightblueConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Blue Confetti Effect
		blueConfetti = new ParticleEffect();
		blueConfetti.load(Gdx.files.internal("data/particles/confetti/blue_confetti.gdx"), Gdx.files.internal("data/particles"));
		blueConfetti.start();
		blueConfettiPool = new ParticleEffectPool(blueConfetti, 0, 500);
		blueConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();

		// Pink Confetti Effect
		pinkConfetti = new ParticleEffect();
		pinkConfetti.load(Gdx.files.internal("data/particles/confetti/pink_confetti.gdx"), Gdx.files.internal("data/particles"));
		pinkConfetti.start();
		pinkConfettiPool = new ParticleEffectPool(pinkConfetti, 0, 500);
		pinkConfettiEffects = new Array<ParticleEffectPool.PooledEffect>();
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		if(body.getLinearVelocity().x < 0){
			facingDirection = -1;
		}else if(body.getLinearVelocity().x > 0){
			facingDirection = 1;
		}
		if(hp > 0) {
			if (facingDirection == -1) {
				// left
				if (damageImmuneTimer > 0) {
					switch (stage) {
						case 1:
							sb.draw(left_immune, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 2:
							sb.draw(left_immune_stage2, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 3:
							sb.draw(left_immune_stage3, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 4:
							sb.draw(left_immune_stage4, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;

					}
				} else {
					switch (stage) {
						case 1:
							sb.draw(left, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 2:
							sb.draw(left_stage2, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 3:
							sb.draw(left_stage3, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 4:
							sb.draw(left_stage4, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;

					}
				}
			} else {
				// right (or if something doesn't go as planned)
				if (damageImmuneTimer > 0) {
					switch (stage) {
						case 1:
							sb.draw(right_immune, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 2:
							sb.draw(right_immune_stage2, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 3:
							sb.draw(right_immune_stage3, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 4:
							sb.draw(right_immune_stage4, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;

					}
				} else {
					switch (stage) {
						case 1:
							sb.draw(right, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 2:
							sb.draw(right_stage2, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 3:
							sb.draw(right_stage3, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;
						case 4:
							sb.draw(right_stage4, body.getPosition().x * B2DVars.PPM - (128 / 2), body.getPosition().y * B2DVars.PPM - (128 / 2));
							break;

					}
				}
			}
		}
		for(ParticleEffectPool.PooledEffect effect : bossDeathParticleEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				bossDeathParticleEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : redConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				redConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : orangeConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				orangeConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : yellowConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				yellowConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : greenConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				greenConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : blueConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				blueConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : lightblueConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				lightblueConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}

		for(ParticleEffectPool.PooledEffect effect : pinkConfettiEffects){
			effect.update(Gdx.graphics.getDeltaTime());
			effect.draw(sb, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()){
				pinkConfettiEffects.removeValue(effect, true);
				effect.free();
			}
		}
		sb.end();
	}

	public void confettiRain(){
		for(int i = 0; i < 2000; i++) {
			Random r = new Random();
			ParticleEffectPool.PooledEffect effect;
			switch(r.nextInt(6)){
				case 0:
					effect = redConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					redConfettiEffects.add(effect);
					effect.start();
					break;
				case 1:
					effect = orangeConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					orangeConfettiEffects.add(effect);
					effect.start();
					break;
				case 2:
					effect = yellowConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					yellowConfettiEffects.add(effect);
					effect.start();
					break;
				case 3:
					effect = greenConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					greenConfettiEffects.add(effect);
					effect.start();
					break;
				case 4:
					effect = blueConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					blueConfettiEffects.add(effect);
					effect.start();
					break;
				case 5:
					effect = lightblueConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					lightblueConfettiEffects.add(effect);
					effect.start();
					break;
				default:
					effect = pinkConfettiPool.obtain();
					effect.setPosition(r.nextInt(Gdx.graphics.getWidth())+r.nextInt(150)-80, r.nextInt(650)+Gdx.graphics.getHeight()-400+r.nextInt(150));
					pinkConfettiEffects.add(effect);
					effect.start();
					break;
			}
		}
	}

	public void update(float delta){
		if(body.getLinearVelocity().y > 1f && damageImmuneTimer <= 2) damageImmuneTimer = 2;

	    if(play.ghosts.size > 0){
	        if(damageImmuneTimer <= 0){
	            damageImmuneTimer = 50;
            }
        }

		if(hp > 10){
			hp = 10;
		}
		if(hp > 7){
			// 1, 9, 8
			stage = 1;
		}else if(hp > 4){
			// 7, 6, 5
			stage = 2;
		}else if(hp > 1){
			// 4, 3, 2
			stage = 3;
		}else if(hp > 0){
			// 1
			stage = 4;
			state = State.ATTACKING;
		}else {
			// He dead.
			state = State.DEAD;
			play.stopBossCollisionWithPlayer();
		}

		// damage immune timer logic
		if(hp > 0) {
			if (damageImmuneTimer > 0) {
				damageImmuneTimer--;
			} else if (damageImmuneTimer < 0) {
				damageImmuneTimer = 0;
			}
		}else damageImmuneTimer = 0;
		
		if(timeUntilDamageImmune > 0 && damaged){
			timeUntilDamageImmune--;
		}else if(damaged){
			timeUntilDamageImmune = defaultTimeUntilDamageImmune;
			damageImmuneTimer = defaultDamageImmuneTimer;
			damaged = false;
		}
		
		// state timer
		if(stateTimer > 0){
			if(waitTimer <= 0){
				stateTimer--;
			}else {
				waitTimer--;
			}
		}else{
			if(state == State.MOVING){
				state = State.ATTACKING;
			}
		}
		
		// state logic
		if(state == State.MOVING){
			if(waitTimer <= 0) {
				if (direction == -1) {
					body.setLinearVelocity(new Vector2(-2f, body.getLinearVelocity().y));
				} else if (direction == 1) {
					body.setLinearVelocity(new Vector2(2f, body.getLinearVelocity().y));
				} else {
					body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
				}

				if (r.nextInt(200) == 50) {
					body.applyLinearImpulse(new Vector2(0, 600f), body.getWorldCenter(), true);
				}

				if (isGrounded) {
					body.applyLinearImpulse(new Vector2(0, 600f), body.getWorldCenter(), true);
					if(!game.boss_landing.isPlaying())game.boss_landing.play();
					boolean i = r.nextBoolean();
					if (i) {
						direction = 1;
					} else {
						direction = -1;
					}
				}
			}
		}else if(state == State.ATTACKING){

			// stand still (lerp smoothly)
			body.getLinearVelocity().lerp(new Vector2(0, body.getLinearVelocity().y), 0.3f);
			
			if(body.getLinearVelocity().x == 0){
				lerpedToStill = true;
			}
			
			if(lerpedToStill){
				if(!attacked){
						switch(stage){
						case 1:
							if(attackIndex == 0) {
								dashAttack(1);
								damageImmuneTimer = 5;
								if(attacked){
									attackIndex = 1;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									//body.applyLinearImpulse(new Vector2(0, 750f), body.getWorldCenter(), true);
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
                                }
							}else if(attackIndex == 1){
								normalShootAttack(1);
								if(attacked){
									attackIndex = 0;
									
									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									waitTimer = 150;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}
							break;
						case 2:
							if(attackIndex == 0){
								dashAttack(2);
								damageImmuneTimer = 5;
								if(attacked){
									attackIndex = 2;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}

							}else if(attackIndex == 1){
								normalShootAttack(2);
								if(attacked){
									attackIndex = 0;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									waitTimer = 150;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}else if(attackIndex == 2){
                                damageImmuneTimer = 5;
                                ultraShootAttack();
								if(attacked){
									attackIndex = 1;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}
							break;
						case 3:
							if(attackIndex == 0){
								ghostSpawnAttack();
								if(attacked) {
									attackIndex = 2;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME + 250;
									body.applyLinearImpulse(new Vector2(0, 750f), body.getWorldCenter(), true);
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}else if(attackIndex == 1){
								normalShootAttack(3);
								if(attacked) {
									attackIndex = 0;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									waitTimer = 150;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}else if(attackIndex == 2){
								damageImmuneTimer = 5;
								ultraShootAttack();
								if(attacked){
									attackIndex = 1;

									// Change state to moving
									state = State.MOVING;
									stateTimer = MOVING_STATE_TIME;
									attacked = false;
									startedAttack = false;
									lerpedToStill = false;
								}
							}
							break;
						case 4:
                            specialAttack();
                            if(!startedAttack)body.applyLinearImpulse(new Vector2(0, 600f), body.getWorldCenter(), true);
                            if(attacked){
                                attackIndex = 1;

                                // Change state to moving
                                state = State.MOVING;
                                stateTimer = MOVING_STATE_TIME;
                                attacked = false;
                                startedAttack = false;
                                lerpedToStill = false;
                            }
							break;
						}
				}
			}
		}else if(state == State.DEAD){
			body.setLinearVelocity(0, body.getLinearVelocity().y);
			play.setBossAlive(false);
			game.boss_fight_music.setVolume(game.boss_fight_music.getVolume() - Gdx.graphics.getDeltaTime() * .5f);
			if(game.getSoundSetting() && !game.victory.isPlaying() && finalTimer > 0 && !finalTimerSet) game.victory.play();

			finalTimer -= Gdx.graphics.getDeltaTime();
			if(finalTimer <= 0){
				finalTimerSet = true;
				//set screen to "game completed" screen
				finalTimer = 1000f;
				game.mysterious_magic_bg_music.stop();
				game.boss_fight_music.stop();
				game.generic_bg_music.stop();
				game.setScreenWithFade(new GameCompleted(game), 2f);
			}
		}
	}

	private void specialAttack(){
        startedAttack = true;

        if(specialAttackJumpTimer > 0){
        	specialAttackJumpTimer--;
		}else{
			body.applyLinearImpulse(new Vector2(0, 3000f), body.getWorldCenter(), true);
			specialAttackJumpTimer = 200;
			randAttackTime = r.nextInt(40) + 130;
			shake.shake(0.5f);
			if(!game.boss_landing.isPlaying())game.boss_landing.play();
		}

		if (r.nextInt(220) == 50) {
			direction = -direction;
		}

		if (direction == -1) {
			body.setLinearVelocity(new Vector2(-1.9f, body.getLinearVelocity().y));
		} else if (direction == 1) {
			body.setLinearVelocity(new Vector2(1.9f, body.getLinearVelocity().y));
		}

		if(specialAttackJumpTimer == randAttackTime){
			if(r.nextInt(1) == 0){
			    float specialAttackBulletSpeed = 8.2f;
				if(play.getPlayerX() < body.getPosition().x){
					play.createBossBullet(body.getPosition(), 0, Vector2.Zero, specialAttackBulletSpeed); // Bullet 4: Slight down and left
					play.createBossBullet(body.getPosition(), 1, new Vector2(3, -1), specialAttackBulletSpeed); // Bullet 7: Slight down and right
				}else {
					play.createBossBullet(body.getPosition(), 1, new Vector2(-3, -1), specialAttackBulletSpeed); // Bullet 4: Slight down and left
					play.createBossBullet(body.getPosition(), 0, Vector2.Zero, specialAttackBulletSpeed); // Bullet 7: Slight down and right
				}
				play.createBossBullet(body.getPosition(), 1, new Vector2(-3, 1), specialAttackBulletSpeed); // Bullet 1: Very slight up and left
				play.createBossBullet(body.getPosition(), 1, new Vector2(-5, 1), specialAttackBulletSpeed); // Bullet 2: Extremely slight up and left
				//play.createBossBullet(body.getPosition(), 1, new Vector2(-1, 0), specialAttackBulletSpeed); // Bullet 3: Left
				play.createBossBullet(body.getPosition(), 1, new Vector2(-5, -1), specialAttackBulletSpeed); // Bullet 4: Extremely slight down and left
				play.createBossBullet(body.getPosition(), 1, new Vector2(5, -1), specialAttackBulletSpeed); // Bullet 7: Extremely slight down and right
				//play.createBossBullet(body.getPosition(), 1, new Vector2(1, 0), specialAttackBulletSpeed); // Bullet 8: Right
				play.createBossBullet(body.getPosition(), 1, new Vector2(5, 1), specialAttackBulletSpeed); // Bullet 9: Extremely slight up and right
				play.createBossBullet(body.getPosition(), 1, new Vector2(3, 1), specialAttackBulletSpeed); // Bullet 10: Very slight up and right
				game.shot_sound.play();
			}
		}
    }

	private void dashAttack(int stage){
		float xForce;
		if(stage == 1){
			xForce = 1550f;
		}else {
			xForce = 1750f;
		}
		if(play.getPlayerX() >= body.getPosition().x && !startedAttack){
			body.applyLinearImpulse(new Vector2(xForce, 0), body.getWorldCenter(), true);
			direction = 1;
			game.woosh.play();
		}else if(play.getPlayerX() < body.getPosition().x && !startedAttack){
			body.applyLinearImpulse(new Vector2(-xForce, 0), body.getWorldCenter(), true);
			direction = -1;
			game.woosh.play();
		}
		
		if(body.getLinearVelocity().x == 0 && startedAttack){
			attacked = true;
		}
		
		startedAttack = true;
	}
	
	private void normalShootAttack(int stage){
		float bulletSpeed;
		if(stage == 1){
			bulletSpeed = 8.6f;
		}else if(stage == 2) {
			bulletSpeed = 8.8f;
		}else {
			bulletSpeed = 9f;
		}
		startedAttack = true;
		
		if(newBulletTime > 0){
			newBulletTime--;
		}else {
			if(bulletsLeft > 0){
				play.createBossBullet(body.getPosition(), 0, PLACE_HOLDER, bulletSpeed);
				game.shot_sound.play();
				bulletsLeft--;
				newBulletTime = defaultNewBulletTime;
			}else {
				attacked = true;
				newBulletTime = defaultNewBulletTime;
				bulletsLeft = 10;
			}
		}
	}

	private void ultraShootAttack(){
		startedAttack = true;

		if(play.getPlayerX() < body.getPosition().x && body.getPosition().x * B2DVars.PPM - play.getPlayerX() * B2DVars.PPM > 150){
			body.setLinearVelocity(new Vector2(.75f, body.getLinearVelocity().y));
		}else if(play.getPlayerX() > body.getPosition().x && play.getPlayerX() * B2DVars.PPM - body.getPosition().x * B2DVars.PPM > 150){
			body.setLinearVelocity(new Vector2(-.75f, body.getLinearVelocity().y));
		}else{
            body.setLinearVelocity(new Vector2(0f, body.getLinearVelocity().y));
        }

        if(isGrounded){
            body.applyLinearImpulse(new Vector2(0, 450f), body.getWorldCenter(), true);
			shake.shake(0.3f);
			if(!game.boss_landing.isPlaying())game.boss_landing.play();
		}

		if(newBulletWaveTime > 0){
			newBulletWaveTime--;
		}else {
			if(bulletWavesLeft > 0){
			    switch(bulletWavesLeft){
                    case 3:
                        float bulletSpeed3 = 7.9f;
                        if(play.getPlayerX() < body.getPosition().x){
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed3); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 1, new Vector2(2, -1), bulletSpeed3); // Bullet 7: Slight down and right
						}else {
							play.createBossBullet(body.getPosition(), 1,  new Vector2(-2, -1), bulletSpeed3); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed3); // Bullet 7: Slight down and right
						}

						play.createBossBullet(body.getPosition(), 1, new Vector2(-1, 1), bulletSpeed3); // Bullet 1: Up and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-2, 1), bulletSpeed3); // Bullet 2: Slight up and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-1, 0), bulletSpeed3); // Bullet 3: Left
						//play.createBossBullet(body.getPosition(), 1, new Vector2(-1, -1), bulletSpeed3); // Bullet 5: Down and left
                        //play.createBossBullet(body.getPosition(), 1, new Vector2(1, -1), bulletSpeed3); // Bullet 6: Down and right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(2, 0), bulletSpeed3); // Bullet 8: Right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(2, 1), bulletSpeed3); // Bullet 9: Slight up and right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(1, 1), bulletSpeed3); // Bullet 10: Up and right
                        break;
                    case 2:
                        float bulletSpeed2 = 8.7f;
                        if(play.getPlayerX() < body.getPosition().x){
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed2); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 1, new Vector2(2, -1), bulletSpeed2); // Bullet 7: Slight down and right
						}else {
							play.createBossBullet(body.getPosition(), 1,  new Vector2(-2, -1), bulletSpeed2); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed2); // Bullet 7: Slight down and right
						}
						play.createBossBullet(body.getPosition(), 1, new Vector2(-2, 1), bulletSpeed2); // Bullet 1: Slight up and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-3, 1), bulletSpeed2); // Bullet 2: Very slight up and left
                        //play.createBossBullet(body.getPosition(), 1, new Vector2(-1, 0), bulletSpeed2); // Bullet 3: Left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-3, -1), bulletSpeed2); // Bullet 4: Very slight down and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(3, -1), bulletSpeed2); // Bullet 7: Very slight down and right
                        //play.createBossBullet(body.getPosition(), 1, new Vector2(1, 0), bulletSpeed2); // Bullet 8: Right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(3, 1), bulletSpeed2); // Bullet 9: Very slight up and right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(2, 1), bulletSpeed2); // Bullet 10: Slight up and right
                        break;
                    case 1:
                        float bulletSpeed1 = 8.7f;
                        if(play.getPlayerX() < body.getPosition().x){
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed1); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 1, new Vector2(3, -1), bulletSpeed1); // Bullet 7: Slight down and right
						}else {
							play.createBossBullet(body.getPosition(), 1, new Vector2(-3, -1), bulletSpeed1); // Bullet 4: Slight down and left
							play.createBossBullet(body.getPosition(), 0, Vector2.Zero, bulletSpeed1); // Bullet 7: Slight down and right
						}
						play.createBossBullet(body.getPosition(), 1, new Vector2(-3, 1), bulletSpeed1); // Bullet 1: Very slight up and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-5, 1), bulletSpeed1); // Bullet 2: Extremely slight up and left
                        //play.createBossBullet(body.getPosition(), 1, new Vector2(-1, 0), bulletSpeed1); // Bullet 3: Left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(-5, -1), bulletSpeed1); // Bullet 4: Extremely slight down and left
                        play.createBossBullet(body.getPosition(), 1, new Vector2(5, -1), bulletSpeed1); // Bullet 7: Extremely slight down and right
                        //play.createBossBullet(body.getPosition(), 1, new Vector2(1, 0), bulletSpeed1); // Bullet 8: Right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(5, 1), bulletSpeed1); // Bullet 9: Extremely slight up and right
                        play.createBossBullet(body.getPosition(), 1, new Vector2(3, 1), bulletSpeed1); // Bullet 10: Very slight up and right
                        break;
                }

                bulletWavesLeft--;
				newBulletWaveTime = defaultNewBulletWaveTime;
				game.shot_sound.play();
			}else {
			    body.setLinearVelocity(new Vector2(0f, body.getLinearVelocity().y));
				attacked = true;
				newBulletWaveTime = defaultNewBulletWaveTime;
				bulletWavesLeft = 3;
			}
		}
	}

	private void ghostSpawnAttack(){
		if(ghostSpawnTimer > 0){
			ghostSpawnTimer--;
		}else if(!startedAttack){
			play.createGhost(new Vector2(body.getPosition().x - 100 / B2DVars.PPM, body.getPosition().y + 120 / B2DVars.PPM));
			play.createGhost(new Vector2(body.getPosition().x - 150 / B2DVars.PPM, body.getPosition().y + 250 / B2DVars.PPM));
			play.createGhost(new Vector2(body.getPosition().x + 90 / B2DVars.PPM, body.getPosition().y + 200 / B2DVars.PPM));
			//play.createGhost(new Vector2(body.getPosition().x + 180 / B2DVars.PPM, body.getPosition().y + 80 / B2DVars.PPM));

			body.applyLinearImpulse(new Vector2(0, 600f), body.getWorldCenter(), true);
			startedAttack = true;
			game.spawn.play();
		}

		if(startedAttack){
			if(afterGhostSpawnTimer > 0){
				afterGhostSpawnTimer--;
			}else {
				attacked = true;
				ghostSpawnTimer = defaultGhostSpawnTimer;
				afterGhostSpawnTimer = defaultAfterGhostSpawnTimer;
			}
		}
	}

	public Body getBody(){
		return body;
	}
	
	public boolean isGrounded(){
		return isGrounded;
	}
	public void setGrounded(boolean grounded){
		isGrounded = grounded;
	}
	public void invertDirection(){
		direction = -direction;
	}
	public void setDirection(int direction){
		this.direction = direction;
	}
	public boolean isDamageImmune(){
		return damageImmuneTimer > 0;
	}
	public void damage(int amount){
		damaged = true;
		if(damageImmuneTimer <= 0){
			hp -= amount;
		}

		if(hp == 7){
			attackIndex = 2;
			game.boss_new_stage.play();
		}else if(hp == 4){
			attackIndex = 0;
			game.boss_new_stage.play();
		}else if(hp == 1){
			attackIndex = 0;
			game.boss_new_stage.play();
		}else if(hp == 0){
			game.boss_new_stage.play();
			ParticleEffectPool.PooledEffect effect = bossDeathParticlePool.obtain();
			effect.setPosition(body.getPosition().x*PPM, body.getPosition().y*PPM);
			bossDeathParticleEffects.add(effect);
			confettiRain();
			effect.start();
		}else{
			game.boss_hurt.play();
		}
		timeUntilDamageImmune = defaultTimeUntilDamageImmune;
	}
	public int getHp(){
		return hp;
	}
}