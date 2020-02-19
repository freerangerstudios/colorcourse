package com.freeranger.colorcourse.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.handlers.LanguageManager;

public class Victory implements Screen {

	private boolean canifade = true;
	
	private Main game;
	private boolean show;
		
	//Scene2D
	private Stage stage;
	private Table table;
	private Container<Label> coinCountContainer;
	private Label coinCount;
	
	private Skin skin;
	private TextButton nextLevelButton, playAgainButton, mainMenuButton;
	private BitmapFont font, fontAlt; 
	private TextureAtlas atlas;
	private Texture titleTexture;
	private Image title;
	
	private LanguageManager lang;
	private Preferences prefs;

	public Victory(final Main game) {
		show = true;
		this.game = game;
		lang = new LanguageManager();
			
		// set up viewport
		if (Gdx.graphics.getWidth() == 1366){
			stage = new Stage(new ExtendViewport(1366, 768));
		}
		else{
			stage = new Stage(new ExtendViewport(1280, 720));
		}
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		//Scene2D
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("data/menu/victory/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		coinCountContainer = new Container<Label>();
		coinCountContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		coinCountContainer.setFillParent(true);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(lang.getLanguage() == 1)titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_german.png"));
		else if(lang.getLanguage() == 3)titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_swedish.png"));
		else if(lang.getLanguage() == 2)titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_spanish.png"));
		else if(lang.getLanguage() == 0)titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_english.png"));
		else titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_english.png"));
		titleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		title = new Image(titleTexture); 
		createFonts();
		
		LabelStyle coinCountStyle = new LabelStyle();
		coinCountStyle.font = font;
		coinCountStyle.fontColor = Color.WHITE;
		
		TextButtonStyle greenBtnWide = new TextButtonStyle();
		greenBtnWide.up = skin.getDrawable("green_wide_up");
		greenBtnWide.down = skin.getDrawable("green_wide_down");
		if(lang.getLanguage() == 3 || lang.getLanguage() == 1) greenBtnWide.font = fontAlt;
		else greenBtnWide.font = font;
		greenBtnWide.pressedOffsetY = -2;
		
		TextButtonStyle yellowBtnWide = new TextButtonStyle();
		yellowBtnWide.up = skin.getDrawable("yellow_wide_up");
		yellowBtnWide.down = skin.getDrawable("yellow_wide_down");
		yellowBtnWide.font = font;
		yellowBtnWide.pressedOffsetY = -2;
	
		TextButtonStyle redBtnWide = new TextButtonStyle();
		redBtnWide.up = skin.getDrawable("red_wide_up");
		redBtnWide.down = skin.getDrawable("red_wide_down");
		if(lang.getLanguage() == 1 || lang.getLanguage() == 2) redBtnWide.font = fontAlt;
		else redBtnWide.font = font;
		redBtnWide.pressedOffsetY = -2;
		
		nextLevelButton = new TextButton(lang.getText().get("Next Level"), greenBtnWide);	
		nextLevelButton.pad(0, 10, 10, 10);
		nextLevelButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("nextlevel");
				if(game.getSoundSetting()) playSound("click");
			}
		});
		
		playAgainButton = new TextButton(lang.getText().get("Play Again"), yellowBtnWide);	
		playAgainButton.pad(0, 10, 10, 10);
		playAgainButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("playagain");
				if(game.getSoundSetting()) playSound("click");
			}
		});
		
		mainMenuButton = new TextButton(lang.getText().get("Main Menu"), redBtnWide);	
		mainMenuButton.pad(0, 0, 10, 0);
		mainMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("mainmenu");
				if(game.getSoundSetting()) playSound("click");
			}
		});
		
		coinCount = new Label(lang.getText().get("Coins") + ": " + game.getTotalCoins(), coinCountStyle);
		//layout
		table.setY(30);
		table.add(title);
		table.getCell(title).space(33);
		table.row();
		table.add(nextLevelButton);
		table.getCell(nextLevelButton).space(20);
		
		table.row();
		
		table.add(playAgainButton);
		table.getCell(playAgainButton).space(20);
		
		table.row();
		
		table.add(mainMenuButton);
		table.getCell(mainMenuButton).space(20);
		
		
		//table.debug();
		stage.addActor(table);
		
		coinCount.setAlignment(Align.center);
		coinCountContainer.setActor(coinCount);
		coinCountContainer.align(Align.top);
		coinCountContainer.setPosition(0, -10);
		stage.addActor(coinCountContainer);
		
		prefs = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.gamedata");
		
		if(game.level >= game.levelsUnlocked){
			game.levelsUnlocked++;
			prefs.putInteger("com.freerangerstudios.colorcourse.gamedata.levelsUnlocked", game.levelsUnlocked);
		}
		
		prefs.putInteger("com.freerangerstudios.colorcourse.gamedata.totalCoins", game.getTotalCoins());
	    prefs.flush();		
	}
	
	private void createFonts() {
	    FileHandle fontFile = Gdx.files.internal("data/fonts/menufont.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    
	    FileHandle fontFileAlt = Gdx.files.internal("data/fonts/thefont.ttf");
	    FreeTypeFontGenerator generatorAlt = new FreeTypeFontGenerator(fontFileAlt);
	    FreeTypeFontParameter parameterAlt = new FreeTypeFontParameter();
	    
	    parameter.size = 28;
	    parameter.minFilter = TextureFilter.Linear;
	    parameter.magFilter = TextureFilter.Linear;
	    parameterAlt.size = 31;
	    parameterAlt.minFilter = TextureFilter.Linear;
	    parameterAlt.magFilter = TextureFilter.Linear;
	    font = generator.generateFont(parameter);
	    fontAlt = generatorAlt.generateFont(parameterAlt);
	    generator.dispose();
	    generatorAlt.dispose();
	}
	
	public void switchScreen(String screen){
		if(screen == "nextlevel" && canifade){ 
			game.level++;
			if(game.level > game.levelsUnlocked){
				game.level--;
			}
			
			if(game.level == 1) game.setScreenWithFade(new Play(game, 192 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
			else game.setScreenWithFade(new Play(game, 48 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
			
	    	canifade = false;
		}else if(screen == "playagain" && canifade){
			if(game.level == 1) game.setScreenWithFade(new Play(game, 192 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
			else game.setScreenWithFade(new Play(game, 48 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 2.4f);
	    	canifade = false;
		}else if(screen == "mainmenu" && canifade){
	    	game.setScreenWithFade(new Menu(game), 1.5f);
			canifade = false; 
		}
	}
	
	public void playSound(String sound){
		if(sound.equals("click")){
			game.ui_click.play();
		}
	}

	public void update(float delta) {
		if(show){
			
		}
	}

	public void render(float delta) {
		if(show){
			Gdx.gl.glClearColor(191f / 255f, 215f / 255f, 1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
			
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
	
	public void hide() {
		show = false;
		dispose();
	}

	public void dispose() {
		stage.dispose();
		atlas.dispose();
		titleTexture.dispose();
		font.dispose();
		fontAlt.dispose();
	}
	
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
    	
    	/*shopBtnContainer.setPosition(x, y);
    	exitBtnContainer.setPosition(x, y);
    	shopBtnContainer.setPosition(x, y);*/
	}
	
	public void show() {}
	public void pause() {}
	public void resume() {}
}
