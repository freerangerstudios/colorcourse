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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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

public class Options implements Screen {
	
	Dialog removeDialog;
	
	private boolean canifade = true;
	
	private Main game;
	private boolean show;
		
	//Scene2D
	private Stage stage;
	private Table table;
	private Container<Label> versionContainer, coinCountContainer;
	private Container<ImageButton> backBtnContainer, soundBtnContainer;
	private Container<TextButton> removeDataBtnContainer;

	private Label version, coinCount;
	private String VERSION;
	
	private Skin skin;
	private TextButton removeDataButton;
	private ImageButton backButton, englishButton, germanButton, spanishButton, swedishButton, soundBtn;
	private BitmapFont font, fontAlt; 
	private TextureAtlas atlas;
	private Texture titleTexture;
	private Image title;
	private Container<Image> titleContainer;
	
	private LanguageManager lang;
	private Preferences prefs, prefs2;
	
	public Options(final Main game) {
		lang = new LanguageManager();
		
		show = true;
		this.game = game;
		
		prefs = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.gamedata");
		prefs.flush();
		
		prefs2 = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.options");
		prefs2.flush();
			
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
		atlas = new TextureAtlas("data/menu/options/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		backBtnContainer = new Container<ImageButton>();
		backBtnContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		backBtnContainer.setFillParent(true);
		versionContainer = new Container<Label>();
		versionContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		versionContainer.setFillParent(true);
		coinCountContainer = new Container<Label>();
		coinCountContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		coinCountContainer.setFillParent(true);
		removeDataBtnContainer = new Container<TextButton>();
		removeDataBtnContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		removeDataBtnContainer.setFillParent(true);
		soundBtnContainer = new Container<ImageButton>();
		soundBtnContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		soundBtnContainer.setFillParent(true);
		titleContainer = new Container<Image>();
		titleContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		titleContainer.setFillParent(true);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		if(lang.getLanguage() == 1)titleTexture = new Texture(Gdx.files.internal("data/menu/options/title_german.png"));
		else if(lang.getLanguage() == 3)titleTexture = new Texture(Gdx.files.internal("data/menu/options/title_swedish.png"));
		else if(lang.getLanguage() == 2)titleTexture = new Texture(Gdx.files.internal("data/menu/options/title_spanish.png"));
		else if(lang.getLanguage() == 0)titleTexture = new Texture(Gdx.files.internal("data/menu/options/title_english.png"));
		else titleTexture = new Texture(Gdx.files.internal("data/menu/victory/title_english.png"));
		titleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		title = new Image(titleTexture); 
		VERSION = "v1.0";
		createFonts();
		
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;
		
		TextButtonStyle redBtnWide = new TextButtonStyle();
		redBtnWide.up = skin.getDrawable("red_wide_up");
		redBtnWide.down = skin.getDrawable("red_wide_down");
		if(lang.getLanguage() == 1) redBtnWide.font = fontAlt;
		else redBtnWide.font = font;
		redBtnWide.pressedOffsetY = -2;
		
		ImageButtonStyle backBtnStyle = new ImageButtonStyle();
		backBtnStyle.up = skin.getDrawable("yellow_rect_up");
		backBtnStyle.down = skin.getDrawable("yellow_rect_down");
		backBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle soundBtnStyle = new ImageButtonStyle();
		soundBtnStyle.up = skin.getDrawable("yellow_rect_up");
		soundBtnStyle.down = skin.getDrawable("yellow_rect_down");
		soundBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle englishBtnStyle = new ImageButtonStyle();
		englishBtnStyle.up = skin.getDrawable("green_rect_up");
		englishBtnStyle.down = skin.getDrawable("green_rect_down");
		englishBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle germanBtnStyle = new ImageButtonStyle();
		germanBtnStyle.up = skin.getDrawable("green_rect_up");
		germanBtnStyle.down = skin.getDrawable("green_rect_down");
		germanBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle spanishBtnStyle = new ImageButtonStyle();
		spanishBtnStyle.up = skin.getDrawable("green_rect_up");
		spanishBtnStyle.down = skin.getDrawable("green_rect_down");
		spanishBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle swedishBtnStyle = new ImageButtonStyle();
		swedishBtnStyle.up = skin.getDrawable("green_rect_up");
		swedishBtnStyle.down = skin.getDrawable("green_rect_down");
		swedishBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle redBtnRect = new ImageButtonStyle();
		redBtnRect.up = skin.getDrawable("red_rect_up");
		redBtnRect.down = skin.getDrawable("red_rect_down");
		redBtnRect.pressedOffsetY = -2;
				
		removeDataButton = new TextButton(lang.getText().get("Remove Data"), redBtnWide);	
		removeDataButton.pad(0, 5, 10, 5);
		removeDataButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(lang.getLanguage() == 0){
					// English
					switchScreen("removeDataDialog_en");	
				}else if(lang.getLanguage() == 1){
					// German
					switchScreen("removeDataDialog_de");	
				}else if(lang.getLanguage() == 2){
					// Spanish
					switchScreen("removeDataDialog_es");	
				}else if(lang.getLanguage() == 3){
					// Swedish
					switchScreen("removeDataDialog_sv");	
				}
			}
		});
		
		Texture backBtnImage = new Texture(Gdx.files.internal("data/menu/options/icons/arrowleft.png"));
		backBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		backButton = new ImageButton(backBtnStyle);
		backButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(backBtnImage));
		backButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(backBtnImage));
		backButton.pad(0, 0, 5, 2);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("menu");
			}
		});
		
		soundBtn = new ImageButton(soundBtnStyle);
		if(game.getSoundSetting()){
			Texture soundBtnImage = new Texture(Gdx.files.internal("data/menu/options/icons/musicon.png"));
			soundBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
			soundBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundBtnImage));
			soundBtn.setChecked(true);
		}else {
			Texture soundBtnImage = new Texture(Gdx.files.internal("data/menu/options/icons/musicoff.png"));
			soundBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
			soundBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundBtnImage));
			soundBtn.setChecked(false);
		}
		soundBtn.pad(0, 0, 5, 2);
		soundBtn.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(soundBtn.isChecked()){
					// On
					Texture soundBtnImage = new Texture(Gdx.files.internal("data/menu/options/icons/musicon.png"));
					
					soundBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
					soundBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundBtnImage));
					game.setSoundSetting(true);
				}
				else{
					// Off
					Texture soundBtnImage = new Texture(Gdx.files.internal("data/menu/options/icons/musicoff.png"));
					
					soundBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
					soundBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundBtnImage));
					game.setSoundSetting(false);
				}
				
				prefs2.putBoolean("com.freerangerstudios.colorcourse.options.sound", game.getSoundSetting());
				prefs2.flush();
			}
		});
		
		Texture englishBtnTex = new Texture(Gdx.files.internal("data/menu/options/flags/great_britain.png"));
		englishBtnTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		englishButton = new ImageButton(englishBtnStyle);
		englishButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(englishBtnTex));
		englishButton.pad(0, 0, 5, 2);
		englishButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				lang.setLanguage(0);
				switchScreen("options");
			}
		});
		
		Texture germanBtnTex = new Texture(Gdx.files.internal("data/menu/options/flags/germany.png"));
		germanBtnTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		germanButton = new ImageButton(germanBtnStyle);
		germanButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(germanBtnTex));
		germanButton.pad(0, 0, 5, 2);
		germanButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				lang.setLanguage(1);
				switchScreen("options");
			}
		});
		
		Texture spanishBtnTex = new Texture(Gdx.files.internal("data/menu/options/flags/spain.png"));
		spanishBtnTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		spanishButton = new ImageButton(spanishBtnStyle);
		spanishButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(spanishBtnTex));
		spanishButton.pad(0, 0, 5, 2);
		spanishButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				lang.setLanguage(2);
				switchScreen("options");
			}
		});
		
		Texture swedishBtnTex = new Texture(Gdx.files.internal("data/menu/options/flags/sweden.png"));
		swedishBtnTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		swedishButton = new ImageButton(swedishBtnStyle);
		swedishButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(swedishBtnTex));
		swedishButton.pad(0, 0, 5, 2);
		swedishButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				lang.setLanguage(3);
				switchScreen("options");
			}
		});
		
		version = new Label(VERSION, textStyle);
		coinCount = new Label(lang.getText().get("Coins")+ ": " + game.getTotalCoins(), textStyle);
		//layout
		table.setY(49);
		//table.add(title);
		//table.getCell(title).space(33);
		table.row();
		
		table.add(englishButton);
		table.getCell(englishButton).space(0, 20, 0, 20);
		table.add(germanButton);
		table.getCell(germanButton).space(0, 20, 0, 20);
		table.add(spanishButton);
		table.getCell(spanishButton).space(0, 20, 0, 20);
		table.add(swedishButton);
		
		table.row();
		
		//table.add(removeDataButton);
		
		table.row();
		
		stage.addActor(table);
		
		titleContainer.setActor(title);
		titleContainer.setFillParent(true);
		titleContainer.align(Align.center);
		titleContainer.setPosition(0, 140);
		stage.addActor(titleContainer);
		
		removeDataBtnContainer.setActor(removeDataButton);
		removeDataBtnContainer.setFillParent(true);
		removeDataBtnContainer.align(Align.center);
		removeDataBtnContainer.setPosition(39, -21);
		stage.addActor(removeDataBtnContainer);
		
		soundBtnContainer.setActor(soundBtn);
		soundBtnContainer.align(Align.center);
		soundBtnContainer.setPosition(-108, -18);
		stage.addActor(soundBtnContainer);

		backBtnContainer.setActor(backButton);
		backBtnContainer.align(Align.topLeft);
		backBtnContainer.setPosition(20, -20);
		stage.addActor(backBtnContainer);
		
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
		game.ui_click.play();
		
		if(screen == "menu" && canifade){
			game.setScreenWithFade(new Menu(game), 1.5f);
			canifade = false;
		}else if(screen == "options" && canifade){
			game.setScreenWithFade(new Options(game), 1.5f);
			canifade = false;
		}else if(screen == "credits" && canifade){
			game.setScreenWithFade(new Credits(game), 1.5f);
		}
		else if(screen == "exit" && canifade){
			Gdx.app.exit();
		}else if(screen == "removeDataDialog_en"){
			game.setScreenWithFade(new DialogManager(game, "Remove Data Question", "Yes", false, "No", false, this, false), 1f);
		}else if(screen == "removeDataDialog_de"){
			game.setScreenWithFade(new DialogManager(game, "Remove Data Question", "Yes", false, "No", false, this, true), 1f);
		}else if(screen == "removeDataDialog_es"){
			game.setScreenWithFade(new DialogManager(game, "Remove Data Question", "Yes", true, "No", false, this, true), 1f);
		}else if(screen == "removeDataDialog_sv"){
			game.setScreenWithFade(new DialogManager(game, "Remove Data Question", "Yes", false, "No", false, this, true), 1f);
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
	}
	
	public void show() {}
	public void pause() {}
	public void resume() {}

	public void option1() {
		// Option 1: Yes
		prefs.putInteger("com.freerangerstudios.colorcourse.gamedata.totalCoins", 0);
		game.setTotalCoins(0);
		prefs.putInteger("com.freerangerstudios.colorcourse.gamedata.levelsUnlocked", 1);
		game.levelsUnlocked = 1;
		prefs2.putBoolean("com.freerangerstudios.colorcourse.options.sound", true);
		game.setSoundSetting(true);
		lang.setLanguage(0);
		prefs.flush();
		prefs2.flush();
		switchScreen("options");
		
	}
	
	public void option2() {
		// Option 2: No
		switchScreen("options");
	}
}
