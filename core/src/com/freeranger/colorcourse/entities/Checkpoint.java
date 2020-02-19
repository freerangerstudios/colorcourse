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

public class Checkpoint {
	
	/*
	 * Needs rework when I start using real animations from spriter
	 */
	
	private Main game;
	private Body body;
	
	public int color;
	
	private Texture inactive;
	private Texture active;
	Animation<TextureRegion> activeAnim;
	float stateTime;

	private int col = 1;
	private int row = 4;

	public Checkpoint(Body body, Main game, int color) {
		this.game = game;
		this.body = body;
		
		inactive = game.checkpoint_inactive;
		active = game.checkpoint_active;
		inactive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		active.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		TextureRegion[][] tmpactive = TextureRegion.split(active,
				active.getWidth() / row,
				active.getHeight() / col);

		TextureRegion[] activeFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				activeFrames[index++] = tmpactive[i][j];
			}
		}

		activeAnim = new Animation<TextureRegion>(0.125f, activeFrames);

		this.color = color;

		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		sb.begin();
		if(game.getRespawnPos().x == body.getPosition().x && game.getRespawnPos().y == body.getPosition().y){
			// active
			TextureRegion currentFrameActive = activeAnim.getKeyFrame(stateTime, true);
			sb.draw(currentFrameActive, body.getPosition().x * B2DVars.PPM - (32 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
		}else {
			// inactive
			sb.draw(inactive, body.getPosition().x * B2DVars.PPM - (32 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
		}
		sb.end();
	}

	public Body getBody(){
		return body;
	}

	public Vector2 getPosition(){
		return body.getPosition();
	}
}