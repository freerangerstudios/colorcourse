package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.LanguageManager;
import com.freeranger.colorcourse.screens.Play;

import java.awt.*;

public class HUD{
	
	private BitmapFont titleFont;
	private BitmapFont textFont;
	
	private Texture heart_sheet;
	private TextureRegion[] hearts;
	
	private Texture temp_heart;
	
	private Player player;
	private Play play;
	
	private Main game;
	
	private LanguageManager lang;

	private ShapeRenderer shapeRenderer;

	private boolean ranOnce = false;
		
	public HUD(Player player, Play play, Main game){
		this.player = player;
		this.play = play;
		this.game = game;
		lang = new LanguageManager();

		shapeRenderer = new ShapeRenderer();
		
		heart_sheet = game.heart_sheet;
		heart_sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		temp_heart = game.temp_heart;
		temp_heart.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		createFonts();
		
		hearts = new TextureRegion[3];
		hearts[0] = new TextureRegion(heart_sheet, 0, 0, 32, 32);
		hearts[1] = new TextureRegion(heart_sheet, 32, 0, 32, 32);
		hearts[2] = new TextureRegion(heart_sheet, 64, 0, 32, 32);		
	}
	
	private void createFonts() {
	    FileHandle path = Gdx.files.internal("data/fonts/menufont.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(path);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;  
	    parameter.size = 18;
	    textFont = generator.generateFont(parameter);
	    parameter.size = 36;
	    titleFont = generator.generateFont(parameter);
	    generator.dispose();
	}
	
	public void render(SpriteBatch sb) {
		//coin and time counters
		int coinw = 20;
		int timew = 1100;
		
		if(lang.getLanguage() == 2) timew = 1050;

		sb.begin();
		titleFont.setColor(1f, 1f, 1f, 1f);
		titleFont.draw(sb, lang.getText().get("Coins") +  ": " + (player.getNumCoins() + game.getTotalCoins()), coinw, 700);
		if(game.getTime() == 10 || game.getTime() == 8 || game.getTime() == 6 || game.getTime() == 4 || game.getTime() == 2
				 || game.getTime() == 0) titleFont.setColor(1f, 0f, 0f, 1f);
		else titleFont.setColor(1f, 1f, 1f, 1f);
		titleFont.draw(sb, lang.getText().get("Time") + ": " + game.getTime(), timew, 700);
		
		//display health
		int hp = play.getPlayerHealth();
		
	    if(hp == 3){
		    sb.draw(hearts[0], 20, 620, 32, 32);
		    sb.draw(hearts[0], 60, 620, 32, 32);
		    sb.draw(hearts[0], 100, 620, 32, 32);
	    }else if(hp == 2){
		    sb.draw(hearts[0], 20, 620, 32, 32);
		    sb.draw(hearts[0], 60, 620, 32, 32);
		    sb.draw(hearts[1], 100, 620, 32, 32);	
	    }else if(hp == 1){
		    sb.draw(hearts[0], 20, 620, 32, 32);
		    sb.draw(hearts[1], 60, 620, 32, 32);
		    sb.draw(hearts[1], 100, 620, 32, 32);
	    }else{
		    sb.draw(hearts[1], 20, 620, 32, 32);
		    sb.draw(hearts[1], 60, 620, 32, 32);
		    sb.draw(hearts[1], 100, 620, 32, 32);
	    }
	    
	    if(game.getTempHealth() == 2){
	    	sb.draw(temp_heart, 140, 620);
	    	sb.draw(temp_heart, 180, 620);
	    }else if(game.getTempHealth() == 1){
	    	sb.draw(temp_heart, 140, 620);
	    }

	    if(play.getSpecialLevel().equals("boss")){
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

			shapeRenderer.setColor(Color.WHITE);
			if(Gdx.graphics.getWidth() == 1366 && Gdx.graphics.getHeight() == 768) {
				shapeRenderer.rect(1366/2-250-1, 768 - 100-2+48, 500+3, 25);
				shapeRenderer.rect(1366/2-250+1-1, 768 - 100+1-2+48, 500-2+3, 25-2);
			}else {
				int screenW, screenH;
				if(ranOnce) {
					screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
					screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
				}else {
					screenW = 1920;
					screenH = 1080;
					ranOnce = true;
				}

				shapeRenderer.rect(screenW/2-screenW/8, screenH + screenH/6, screenW/2+2, 25);
			}
			shapeRenderer.end();
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

			shapeRenderer.setColor(Color.RED);
			int screenW, screenH;

			if(ranOnce) {
				screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
				screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
			}else {
				screenW = 1920;
				screenH = 1080;
				ranOnce = true;
			}

			if(Gdx.graphics.getWidth() == 1366 && Gdx.graphics.getHeight() == 768) {
				shapeRenderer.rect(1366 / 2 - 250, 768 - 100+48, (play.getBoss().getHp() * 50), 25 - 3);
			}else {
				shapeRenderer.rect(screenW / 2 - screenW/8, screenH + screenH/6 + 1, (play.getBoss().getHp() * screenW/20), 25 - 2);
			}


			shapeRenderer.end();
		}

	    sb.end();	    
	}			
}