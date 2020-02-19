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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.handlers.LanguageManager;

public class LevelSelectWorld1 implements Screen {
	
	//Scene2D
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	private BitmapFont font; 
	
	private TextButton[] levelBtn = new TextButton[20];
	private TextButtonStyle unlockedLevel, completedLevel, lockedLevel;
	private ImageButton backBtn, prevWorldBtn, nextWorldBtn;
	private Container<ImageButton> backBtnContainer, prevWorldContainer, nextWorldContainer;
	private Container<Label> coinCountContainer;
	private Container<Image> titleContainer;
	private Label coinCount;
	private Texture titleTexture;
	private Image title;
	
	private boolean check = true;
		
	private Main game;
	private boolean canifade = true;
	private Viewport vw;
	
	private boolean show;
	private boolean locked;
	
	private LanguageManager lang;

	public LevelSelectWorld1(final Main game) {
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
		
		//Scene2D UI
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("data/menu/levelselect/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		backBtnContainer = new Container<ImageButton>();
		backBtnContainer.setFillParent(true);
		prevWorldContainer = new Container<ImageButton>();
		nextWorldContainer = new Container<ImageButton>();
		coinCountContainer = new Container<Label>();
		coinCountContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		coinCountContainer.setFillParent(true);
		titleContainer = new Container<Image>();
		titleContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		titleContainer.setFillParent(true);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
		if(lang.getLanguage() == 1)titleTexture = new Texture(Gdx.files.internal("data/menu/levelselect/title_german.png"));
		else if(lang.getLanguage() == 3)titleTexture = new Texture(Gdx.files.internal("data/menu/levelselect/title_swedish.png"));
		else if(lang.getLanguage() == 2)titleTexture = new Texture(Gdx.files.internal("data/menu/levelselect/title_spanish.png"));
		else if(lang.getLanguage() == 0)titleTexture = new Texture(Gdx.files.internal("data/menu/levelselect/title_english.png"));
		else titleTexture = new Texture(Gdx.files.internal("data/menu/levelselect/title_english.png"));
		titleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		title = new Image(titleTexture); 
		
		createFonts();
		
		ImageButtonStyle backBtnStyle = new ImageButtonStyle();
		backBtnStyle.up = skin.getDrawable("yellow_rect_up");
		backBtnStyle.down = skin.getDrawable("yellow_rect_down");
		backBtnStyle.pressedOffsetY = -2;
		
		ImageButtonStyle forwardBtnStyle = new ImageButtonStyle();
		forwardBtnStyle.up = skin.getDrawable("yellow_rect_up");
		forwardBtnStyle.down = skin.getDrawable("yellow_rect_down");
		forwardBtnStyle.pressedOffsetY = -2;
		
		lockedLevel = new TextButtonStyle();
		lockedLevel.up = skin.getDrawable("red_rect_up");
		lockedLevel.down = skin.getDrawable("red_rect_down");
		lockedLevel.font = font;
		lockedLevel.pressedOffsetY = -2;
		
		unlockedLevel = new TextButtonStyle();
		unlockedLevel.up = skin.getDrawable("yellow_rect_up");
		unlockedLevel.down = skin.getDrawable("yellow_rect_down");
		unlockedLevel.font = font;
		unlockedLevel.pressedOffsetY = -2;
		
		completedLevel = new TextButtonStyle();
		completedLevel.up = skin.getDrawable("green_rect_up");
		completedLevel.down = skin.getDrawable("green_rect_down");
		completedLevel.font = font;
		completedLevel.pressedOffsetY = -2;
		
		Texture backBtnImage = new Texture(Gdx.files.internal("data/menu/credits/icons/left_arrow.png"));
		backBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		backBtn = new ImageButton(backBtnStyle);
		backBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(backBtnImage));
		backBtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(backBtnImage));
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("menu");
				if(game.getSoundSetting()) playSound("click");
			}
		});
		
		/*prevWorldBtn = new ImageButton(backBtnStyle);
		prevWorldBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/menu/credits/icons/left_arrow.png"))));
		prevWorldBtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/menu/credits/icons/left_arrow.png"))));
		prevWorldBtn.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				//Play error sound
			}
		});*/
		
		Texture nextWorldBtnImage = new Texture(Gdx.files.internal("data/menu/levelselect/icons/arrowright.png"));
		backBtnImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		nextWorldBtn = new ImageButton(forwardBtnStyle);
		nextWorldBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(nextWorldBtnImage));
		nextWorldBtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(nextWorldBtnImage));
		nextWorldBtn.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				switchScreen("nextworld");
				if(game.getSoundSetting()) playSound("click");
			}
		});
		
		table.setY(-43);
		table.row();
				
		for(int i = 1; i < 21; i++){			
			if(game.levelsUnlocked > i){
				levelBtn[i-1] = new TextButton(Integer.toString(i), completedLevel);
				locked = false;
			}
			else if(game.levelsUnlocked == i){
				levelBtn[i-1] = new TextButton(Integer.toString(i), unlockedLevel);
				locked = false;
			}
			else{
				locked = true;
				levelBtn[i-1] = new TextButton("", lockedLevel);
			}
						
			table.add(levelBtn[i-1]);
			
			table.getCell(levelBtn[i-1]).space(10, 10, 10, 10);
			if(i == 5 || i == 10 || i == 15) table.row();
		}
		
		//layout
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;	
		
		coinCount = new Label(lang.getText().get("Coins")+ ": " + game.getTotalCoins(), textStyle);
		
		titleContainer.setActor(title);
		titleContainer.setFillParent(true);
		titleContainer.align(Align.center);
		titleContainer.setPosition(0, 140);
		stage.addActor(titleContainer);
		
		backBtnContainer.setActor(backBtn);
		backBtnContainer.setFillParent(true);
		backBtnContainer.align(Align.topLeft);
		backBtnContainer.setPosition(20, -20);
		stage.addActor(backBtnContainer);
		
		coinCount.setAlignment(Align.center);
		coinCountContainer.setActor(coinCount);
		coinCountContainer.align(Align.top);
		coinCountContainer.setPosition(0, -10);
		stage.addActor(coinCountContainer);
		
		/*prevWorldContainer.setActor(prevWorldBtn);
		prevWorldContainer.setFillParent(true);
		prevWorldContainer.align(Align.bottomLeft);
		prevWorldContainer.setPosition(20, 20);
		stage.addActor(prevWorldContainer);*/
		
		/*nextWorldContainer.setActor(nextWorldBtn);
		nextWorldContainer.setFillParent(true);
		nextWorldContainer.align(Align.bottomRight);
		nextWorldContainer.setPosition(-20, 20);
		stage.addActor(nextWorldContainer);*/
		
		//add listeners for level button 1-20
		levelBtn[0].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(1);}});
		levelBtn[1].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(2);}});
		levelBtn[2].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(3);}});
		levelBtn[3].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(4);}});
		levelBtn[4].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(5);}});
		levelBtn[5].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(6);}});
		levelBtn[6].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(7);}});
		levelBtn[7].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(8);}});
		levelBtn[8].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(9);}});
		levelBtn[9].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(10);}});
		levelBtn[10].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(11);}});
		levelBtn[11].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(12);}});
		levelBtn[12].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(13);}});
		levelBtn[13].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(14);}});
		levelBtn[14].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(15);}});
		levelBtn[15].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(16);}});
		levelBtn[16].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(17);}});
		levelBtn[17].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(18);}});
		levelBtn[18].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(19);}});
		levelBtn[19].addListener(new ClickListener(){public void clicked (InputEvent event, float x, float y) {switchLevel(20);}});
		
		stage.addActor(table);
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
	
	private void switchLevel(int lvl){
		if(lvl <= game.levelsUnlocked){
			if(canifade){
				System.out.println("unlocked");
				game.level = lvl;
				System.out.println(game.level);
				if(game.getSoundSetting()) playSound("click");
				
				if(lvl == 1)game.setScreenWithFade(new Play(game, 192 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 1.5f);
				else {
					game.setScreenWithFade(new Play(game, 48 / B2DVars.PPM, 948 / B2DVars.PPM, 1, "no", 0), 1.5f);
				}
				canifade = false;
			}
		}else{
			System.out.println(lvl + " is locked");
			game.locked.play();
		}
	}
	
	public void switchScreen(String screen){
		if(screen == "menu" && canifade){
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
		if(show) {
			
		}
	}

	public void render(float delta) {
		if(show) {
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
		font.dispose();
		atlas.dispose();
	}
	public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
	}
	public void show() {}
	public void pause() {}
	public void resume() {}
}