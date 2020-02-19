package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class PlantOfDoom {	
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture sprite_sheet;
	Animation<TextureRegion> anim;
	float stateTime;
	
	private int col = 1;
	private int row = 8;
	
	public PlantOfDoom(Body body, Main game, Play play) {
		this.game = game;
		this.body = body;
		this.play = play;
		
		sprite_sheet = game.plant_of_doom;
		sprite_sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				
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
		
		anim = new Animation<TextureRegion>(0.06f, animFrames);
		
		stateTime = 0f;

		game.scissor_sound.setLooping(true);
	}

	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		if(!game.scissor_sound.isPlaying()){
			System.out.println("Does not play");
			if(game.getSoundSetting())game.scissor_sound.play();
		}

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);

		sb.begin();
		sb.draw(currentFrame, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));

		sb.end();
	}
	
	public Body getBody(){
		return body;
	}
}