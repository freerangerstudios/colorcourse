package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class Player {

	private Main game;
	private Body body;
	private Play play;

	private int numCoins;
	private int totalCoins;
	private int color;
	
	private Texture redIdle;
	private Texture redJump;
	Animation<TextureRegion> redIdleAnim;
	Animation<TextureRegion> redJumpAnim;
	
	private Texture orangeIdle;
	private Texture orangeJump;
	Animation<TextureRegion> orangeIdleAnim;
	Animation<TextureRegion> orangeJumpAnim;
	
	private Texture yellowIdle;
	private Texture yellowJump;
	Animation<TextureRegion> yellowIdleAnim;
	Animation<TextureRegion> yellowJumpAnim;
	
	private Texture greenIdle;
	private Texture greenJump;
	Animation<TextureRegion> greenIdleAnim;
	Animation<TextureRegion> greenJumpAnim;
	
	private Texture blueIdle;
	private Texture blueJump;
	Animation<TextureRegion> blueIdleAnim;
	Animation<TextureRegion> blueJumpAnim;
	
	private Texture pinkIdle;
	private Texture pinkJump;
	Animation<TextureRegion> pinkIdleAnim;
	Animation<TextureRegion> pinkJumpAnim;
	
	float stateTime;
	
	private int idle_col = 1;
	private int idle_row = 62;
	private int jump_col = 1;
	private int jump_row = 29;
	
	private boolean hidden = false;

	public Player(Body body, Main game, Play play) {
		this.body = body;
		this.game = game;
		this.play = play;
		
		redIdle = game.playerRed_idle;
	    redJump = game.playerRed_jump;	
		orangeIdle = game.playerOrange_idle;
	    orangeJump = game.playerOrange_jump;
	    yellowIdle = game.playerYellow_idle;
	    yellowJump = game.playerYellow_jump;
	    greenIdle = game.playerGreen_idle;
	    greenJump = game.playerGreen_jump;
	    blueIdle = game.playerBlue_idle;
	    blueJump = game.playerBlue_jump;
	    pinkIdle = game.playerPink_idle;
	    pinkJump = game.playerPink_jump;
	    
	    redIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    redJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    orangeIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    orangeJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    yellowIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    yellowJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    greenIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    greenJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    blueIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    blueJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    pinkIdle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    pinkJump.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		
		//red
		TextureRegion[][] tmpRedIdle = TextureRegion.split(redIdle, redIdle.getWidth() / idle_row, redIdle.getHeight() / idle_col);	
		TextureRegion[] redIdleFrames = new TextureRegion[idle_row * idle_col];
		int redIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {redIdleFrames[redIdle_index++] = tmpRedIdle[i][j];}}
		redIdleAnim = new Animation<TextureRegion>(0.0125f, redIdleFrames);
		
		TextureRegion[][] tmpRedJump = TextureRegion.split(redJump, redJump.getWidth() / jump_row, redJump.getHeight() / jump_col);	
		TextureRegion[] redJumpFrames = new TextureRegion[jump_row * jump_col];
		int redJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {redJumpFrames[redJump_index++] = tmpRedJump[i][j];}}
		redJumpAnim = new Animation<TextureRegion>(0.0125f, redJumpFrames);
		
		//orange
		TextureRegion[][] tmpOrangeIdle = TextureRegion.split(orangeIdle, orangeIdle.getWidth() / idle_row, orangeIdle.getHeight() / idle_col);	
		TextureRegion[] orangeIdleFrames = new TextureRegion[idle_row * idle_col];
		int orangeIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {orangeIdleFrames[orangeIdle_index++] = tmpOrangeIdle[i][j];}}
		orangeIdleAnim = new Animation<TextureRegion>(0.0125f, orangeIdleFrames);
		
		TextureRegion[][] tmpOrangeJump = TextureRegion.split(orangeJump, orangeJump.getWidth() / jump_row, orangeJump.getHeight() / jump_col);	
		TextureRegion[] orangeJumpFrames = new TextureRegion[jump_row * jump_col];
		int orangeJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {orangeJumpFrames[orangeJump_index++] = tmpOrangeJump[i][j];}}
		orangeJumpAnim = new Animation<TextureRegion>(0.0125f, orangeJumpFrames);
		
		//yellow
		TextureRegion[][] tmpYellowIdle = TextureRegion.split(yellowIdle, yellowIdle.getWidth() / idle_row, yellowIdle.getHeight() / idle_col);	
		TextureRegion[] yellowIdleFrames = new TextureRegion[idle_row * idle_col];
		int yellowIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {yellowIdleFrames[yellowIdle_index++] = tmpYellowIdle[i][j];}}
		yellowIdleAnim = new Animation<TextureRegion>(0.0125f, yellowIdleFrames);
				
		TextureRegion[][] tmpYellowJump = TextureRegion.split(yellowJump, yellowJump.getWidth() / jump_row, yellowJump.getHeight() / jump_col);	
		TextureRegion[] yellowJumpFrames = new TextureRegion[jump_row * jump_col];
		int yellowJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {yellowJumpFrames[yellowJump_index++] = tmpYellowJump[i][j];}}
		yellowJumpAnim = new Animation<TextureRegion>(0.0125f, yellowJumpFrames);
		
		//green
		TextureRegion[][] tmpGreenIdle = TextureRegion.split(greenIdle, greenIdle.getWidth() / idle_row, greenIdle.getHeight() / idle_col);	
		TextureRegion[] greenIdleFrames = new TextureRegion[idle_row * idle_col];
		int greenIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {greenIdleFrames[greenIdle_index++] = tmpGreenIdle[i][j];}}
		greenIdleAnim = new Animation<TextureRegion>(0.0125f, greenIdleFrames);
						
		TextureRegion[][] tmpGreenJump = TextureRegion.split(greenJump, greenJump.getWidth() / jump_row, greenJump.getHeight() / jump_col);	
		TextureRegion[] greenJumpFrames = new TextureRegion[jump_row * jump_col];
		int greenJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {greenJumpFrames[greenJump_index++] = tmpGreenJump[i][j];}}
		greenJumpAnim = new Animation<TextureRegion>(0.0125f, greenJumpFrames);
		
		//blue
		TextureRegion[][] tmpBlueIdle = TextureRegion.split(blueIdle, blueIdle.getWidth() / idle_row, blueIdle.getHeight() / idle_col);	
		TextureRegion[] blueIdleFrames = new TextureRegion[idle_row * idle_col];
		int blueIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {blueIdleFrames[blueIdle_index++] = tmpBlueIdle[i][j];}}
		blueIdleAnim = new Animation<TextureRegion>(0.0125f, blueIdleFrames);
								
		TextureRegion[][] tmpBlueJump = TextureRegion.split(blueJump, blueJump.getWidth() / jump_row, blueJump.getHeight() / jump_col);	
		TextureRegion[] blueJumpFrames = new TextureRegion[jump_row * jump_col];
		int blueJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {blueJumpFrames[blueJump_index++] = tmpBlueJump[i][j];}}
		blueJumpAnim = new Animation<TextureRegion>(0.0125f, blueJumpFrames);
		
		//pink
		TextureRegion[][] tmpPinkIdle = TextureRegion.split(pinkIdle, pinkIdle.getWidth() / idle_row, pinkIdle.getHeight() / idle_col);	
		TextureRegion[] pinkIdleFrames = new TextureRegion[idle_row * idle_col];
		int pinkIdle_index = 0;
		for (int i = 0; i < idle_col; i++) {for (int j = 0; j < idle_row; j++) {pinkIdleFrames[pinkIdle_index++] = tmpPinkIdle[i][j];}}
		pinkIdleAnim = new Animation<TextureRegion>(0.0125f, pinkIdleFrames);
										
		TextureRegion[][] tmpPinkJump = TextureRegion.split(pinkJump, pinkJump.getWidth() / jump_row, pinkJump.getHeight() / jump_col);	
		TextureRegion[] pinkJumpFrames = new TextureRegion[jump_row * jump_col];
		int pinkJump_index = 0;
		for (int i = 0; i < jump_col; i++) {for (int j = 0; j < jump_row; j++) {pinkJumpFrames[pinkJump_index++] = tmpPinkJump[i][j];}}
		pinkJumpAnim = new Animation<TextureRegion>(0.0125f, pinkJumpFrames);
		
		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrameRedIdle = redIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameRedJump = redJumpAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameOrangeIdle = orangeIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameOrangeJump = orangeJumpAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameYellowIdle = yellowIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameYellowJump = yellowJumpAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameGreenIdle = greenIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameGreenJump = greenJumpAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameBlueIdle = blueIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameBlueJump = blueJumpAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFramePinkIdle = pinkIdleAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFramePinkJump = pinkJumpAnim.getKeyFrame(stateTime, true);


		sb.begin();
		if(!hidden) {
			if(color == 1){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFrameRedIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFrameRedJump, body.getPosition().x * B2DVars.PPM - (45 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}else if(color == 2){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFrameOrangeIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFrameOrangeJump, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}else if(color == 3){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFrameYellowIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFrameYellowJump, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}else if(color == 4){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFrameGreenIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFrameGreenJump, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}else if(color == 5){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFrameBlueIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFrameBlueJump, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}else if(color == 6){
				if(play.getContactListener().isPlayerOnGround()) sb.draw(currentFramePinkIdle, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
				else sb.draw(currentFramePinkJump, body.getPosition().x * B2DVars.PPM - (36 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
			}
		}
		sb.end();
	}

	public void collectCoin(){
		numCoins += 1;
	}
	public int getNumCoins(){
		return numCoins;
	}
	public void setNumCoins(int i){
		numCoins = i;
	}
	public void setTotalCoins(int i){
		totalCoins = i;
	}
	public int getTotalCoins(){
		return totalCoins;
	}
	public void setColor(int color){
		this.color = color;
	}
	public int getColor(){
		return color;
	}
	public Body getBody(){
		return body;
	}
	public Vector2 getPosition(){
		return body.getPosition();
	}
	public void setHidden(boolean hidden){
		this.hidden = hidden;
	}
}