package com.freeranger.colorcourse.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.LanguageManager;

public class Menu implements Screen {
	
	/*
	 * - Make all buttons work
	 */
	
	private boolean canifade = true;
	
	private Main game;
	private boolean show;
		
	//Scene2D
	private Stage stage;
	private Table table;
	private Container<ImageButton> shopBtnContainer, exitBtnContainer;
	private Container<Label> versionContainer, coinCountContainer;
	private Label version, coinCount;
	private String VERSION;
	
	private Skin skin;
	private TextButton playButton, optionsButton, creditsButton;
	private ImageButton shopButton, exitButton;
	private BitmapFont font; 
	private TextureAtlas atlas;
	private Texture titleTexture;
	private Image title;
	
	private LanguageManager lang;

	public Menu(final Main game) {
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
		atlas = new TextureAtlas("data/menu/main/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		shopBtnContainer = new Container<ImageButton>();
		shopBtnContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shopBtnContainer.setFillParent(true);
		exitBtnContainer = new Container<ImageButton>();
		exitBtnContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		exitBtnContainer.setFillParent(true);
		versionContainer = new Container<Label>();
		versionContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		versionContainer.setFillParent(true);
		coinCountContainer = new Container<Label>();
		coinCountContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		coinCountContainer.setFillParent(true);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		titleTexture = new Texture(Gdx.files.internal("data/menu/main/title.png"));
		titleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		title = new Image(titleTexture); 
		VERSION = "v1.0";
		createFonts();
		
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;
		
		TextButtonStyle greenBtnWide = new TextButtonStyle();
		greenBtnWide.up = skin.getDrawable("green_wide_up");
		greenBtnWide.down = skin.getDrawable("green_wide_down");
		greenBtnWide.font = font;
		greenBtnWide.pressedOffsetY = -2;
		
		TextButtonStyle yellowBtnWide = new TextButtonStyle();
		yellowBtnWide.up = skin.getDrawable("yellow_wide_up");
		yellowBtnWide.down = skin.getDrawable("yellow_wide_down");
		yellowBtnWide.font = font;
		yellowBtnWide.pressedOffsetY = -2;
		
		TextButtonStyle redBtnWide = new TextButtonStyle();
		redBtnWide.up = skin.getDrawable("red_wide_up");
		redBtnWide.down = skin.getDrawable("red_wide_down");
		redBtnWide.font = font;
		redBtnWide.pressedOffsetY = -2;
		
		ImageButtonStyle yellowBtnRect = new ImageButtonStyle();
		yellowBtnRect.up = skin.getDrawable("yellow_rect_up");
		yellowBtnRect.down = skin.getDrawable("yellow_rect_down");
		yellowBtnRect.pressedOffsetY = -2;
		
		ImageButtonStyle redBtnRect = new ImageButtonStyle();
		redBtnRect.up = skin.getDrawable("red_rect_up");
		redBtnRect.down = skin.getDrawable("red_rect_down");
		redBtnRect.pressedOffsetY = -2;
		
		playButton = new TextButton(lang.getText().get("Play"), greenBtnWide);	
		playButton.pad(0, 0, 10, 0);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("lvlselect");
				if(game.getSoundSetting())playSound("click");
			}
		});
		
		optionsButton = new TextButton(lang.getText().get("Options"), yellowBtnWide);	
		optionsButton.pad(0, 0, 10, 0);
		optionsButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("options");
				if(game.getSoundSetting())playSound("click");
			}
		});
		
		creditsButton = new TextButton(lang.getText().get("Credits"), redBtnWide);	
		creditsButton.pad(0, 0, 10, 0);
		creditsButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("credits");
				if(game.getSoundSetting())playSound("click");
			}
		});
		
		Texture shopBtnImage = new Texture(Gdx.files.internal("data/menu/main/icons/cart.png"));
		shopBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shopButton = new ImageButton(yellowBtnRect);
		shopButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(shopBtnImage));
		shopButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(shopBtnImage));
		shopButton.pad(0, 0, 5, 5);
		shopButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				//Shop is not added yet
				switchScreen("shop");
				if(game.getSoundSetting())playSound("click");
			}
		});
		
		Texture exitBtnImage = new Texture(Gdx.files.internal("data/menu/main/icons/door.png"));
		exitBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		exitButton = new ImageButton(redBtnRect);
		exitButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(exitBtnImage));
		exitButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(exitBtnImage));
		exitButton.pad(0, 0, 5, 2);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		version = new Label(VERSION , textStyle);
		coinCount = new Label(lang.getText().get("Coins")+ ": " + game.getTotalCoins(), textStyle);
		//layout
		table.setY(30);
		table.add(title);
		table.getCell(title).space(33);
		table.row();
		table.add(playButton);
		table.getCell(playButton).space(20);
		
		table.row();
		
		table.add(optionsButton);
		table.getCell(optionsButton).space(20);
		
		table.row();
		
		table.add(creditsButton);
		
		table.row();
		
		//table.debug();
		stage.addActor(table);
		
		/*shopBtnContainer.setActor(shopButton);
		shopBtnContainer.align(Align.bottomLeft);
		shopBtnContainer.setPosition(20, 20);
		stage.addActor(shopBtnContainer);*/
		
		exitBtnContainer.setActor(exitButton);
		exitBtnContainer.align(Align.bottomRight);
		exitBtnContainer.setPosition(-20, 20);
		stage.addActor(exitBtnContainer);
		
		version.setAlignment(Align.center);
		versionContainer.setActor(version);
		versionContainer.align(Align.bottom);
		versionContainer.setPosition(0, 10);
		stage.addActor(versionContainer);
		
		coinCount.setAlignment(Align.center);
		coinCountContainer.setActor(coinCount);
		coinCountContainer.align(Align.top);
		coinCountContainer.setPosition(0, -10);
		stage.addActor(coinCountContainer);
	}
	
	private void createFonts() {
	    FileHandle fontFile = Gdx.files.internal("data/fonts/menufont.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = 28;
	    parameter.minFilter = TextureFilter.Linear;
	    parameter.magFilter = TextureFilter.Linear;
	    font = generator.generateFont(parameter);
	    generator.dispose();
	}
	
	public void switchScreen(String screen){
		if(screen == "lvlselect" && canifade){
			game.setScreenWithFade(new LevelSelectWorld1(game), 1.5f);
			canifade = false;
		}else if(screen == "options" && canifade){
			game.setScreenWithFade(new Options(game), 1.5f);
			canifade = false;
		}else if(screen == "shop" && canifade){
			// TODO when shop is added (lightweight, powerups like temp hp)
		}else if(screen == "credits" && canifade){
			game.setScreenWithFade(new Credits(game), 1.5f);
		}
		else if(screen == "exit" && canifade){
			Gdx.app.exit();
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
	}
	
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
	}
	
	public void show() {}
	public void pause() {}
	public void resume() {}
}
