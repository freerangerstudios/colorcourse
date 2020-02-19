package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;

public class FallingRock {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	
	private Texture sprite;

	private Vector2 startPos;
	private int state; // 0 = fall, 1 = on ground, 2 = levitating
	private float on_ground_timer;
	
	private boolean is_on_ground;
	
	private int delay;

	private Texture aggressive;
	private Texture tired;
	Animation<TextureRegion> tiredAnim;
	Animation<TextureRegion> aggressiveAnim;
	float stateTime;

	private int col = 1;
	private int row = 16;
	
	public FallingRock(Body body, Main game, int delay) {
		this.game = game;
		this.body = body;
		this.delay = delay;

		startPos = new Vector2(body.getPosition());
		state = 0;
		on_ground_timer = 2.5f;
		is_on_ground = false;
		sprite = game.falling_rock;

		aggressive = game.falling_rock_sprites;
		tired = game.falling_rock_tired_sprites;

		aggressive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		tired.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		TextureRegion[][] tmpaggressive = TextureRegion.split(aggressive,
				aggressive.getWidth() / row,
				aggressive.getHeight() / col);
		TextureRegion[][] tmptired = TextureRegion.split(tired,
				tired.getWidth() / row,
				tired.getHeight() / col);

		TextureRegion[] rightFrames = new TextureRegion[row * col];
		TextureRegion[] leftFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				rightFrames[index++] = tmpaggressive[i][j];
			}
		}

		int index2 = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				leftFrames[index2++] = tmptired[i][j];
			}
		}

		aggressiveAnim = new Animation<TextureRegion>(0.125f, rightFrames);
		tiredAnim = new Animation<TextureRegion>(0.125f, leftFrames);

		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime();
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrameAggressive = aggressiveAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameTired = tiredAnim.getKeyFrame(stateTime, true);

		sb.begin();
		if(body.getLinearVelocity().y < 0){
			sb.draw(currentFrameAggressive, body.getPosition().x * B2DVars.PPM - (64 / 2), body.getPosition().y * B2DVars.PPM - (64 / 2));
		}else{
			sb.draw(currentFrameTired, body.getPosition().x * B2DVars.PPM - (64 / 2), body.getPosition().y * B2DVars.PPM - (64 / 2));
		}
		sb.end();
	}
	
	public void update(float delta){	
		if(delay > 0){
			delay--;
			body.setLinearVelocity(new Vector2(0f, 35 / B2DVars.PPM));
		}else{
			if(state == 0){
				// fall stuff
				if(is_on_ground){
					state = 1;
				}
			}else if(state == 1){
				// on ground stuff
				on_ground_timer-=delta;
				
				if(on_ground_timer <= 0){
					state = 2;
					on_ground_timer = 2.5f;
				}
			}else if(state == 2){
				// levitation stuff
				if(body.getPosition().y < startPos.y){
					body.setLinearVelocity(body.getLinearVelocity().x, 175 / B2DVars.PPM);
				}else{
					state = 0;
				}
			}
		}
	}
	
	public Body getBody(){
		return body;
	}

	public boolean is_on_ground() {
		return is_on_ground;
	}

	public void set_is_on_ground(boolean is_on_ground) {
		this.is_on_ground = is_on_ground;
	}
}