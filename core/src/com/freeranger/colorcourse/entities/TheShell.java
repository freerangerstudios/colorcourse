package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class TheShell {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture sprite_sheet;
	Animation<TextureRegion> anim;
	float stateTime;
	
	private int col = 1;
	private int row = 1;
	
	private Vector2 velocity;
	private final float MOVE_SPEED = 1.5f;
	private boolean isMoving = true;
	private boolean canChangeState = true;
	
	public TheShell(Body body, Main game, Play play) {
		this.game = game;
		this.body = body;
		this.play = play;

		sprite_sheet = game.the_shell;
		sprite_sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		velocity = new Vector2(-MOVE_SPEED, 0);
		/*		
		TextureRegion[][] tmpanim = TextureRegion.split(sprite_sheet, 
				sprite_sheet.getWidth() / row,
				sprite_sheet.getHeight() / col);
	
		TextureRegion[] animFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				animFrames[index++] = tmpanim[i][j];
			}
		}
		
		anim = new Animation<TextureRegion>(1f, animFrames);
		*/
		stateTime = 0f;		
	}

	public void render(SpriteBatch sb){
		//stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		//TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);
		
		sb.begin();
		
		sb.draw(sprite_sheet, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (18 / 2));

		sb.end();
	}
	
	public void update(float delta){
		if(isMoving){
			body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
		}else {
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
	}
	
	public void setVelocity(float velocity){
		this.velocity.x = velocity;
	}
	
	public Body getBody(){
		return body;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isCanChangeState() {
		return canChangeState;
	}

	public void setCanChangeState(boolean canChangeState) {
		this.canChangeState = canChangeState;
	}
}