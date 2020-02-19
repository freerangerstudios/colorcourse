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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.LanguageManager;

public class Credits implements Screen {
	
	// TODO Add language manager because spanish doesn't work
		
	private boolean canifade = true;
	
	private Main game;
	private boolean show;
		
	//Scene2D
	private Stage stage;
	private Table table;
	private Container<Image> creditsContainer;
	private Container<ImageButton> backBtnContainer;
	private Container<Label> coinCountContainer;
	private Label coinCount;
	
	private TextureAtlas atlas;
	private Skin skin;
	private BitmapFont font, font2, fontAlt, font2Alt; 
	private Image creditsImg;
	private Texture creditsTexture;
	private ImageButton backBtn;
	private Label credits_line1;
	private Label credits_line2;
	private Label credits_line3;
	private Label credits_line4;
	private Label credits_line5;
	
	private LanguageManager lang;

	public Credits(final Main game) {
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
		atlas = new TextureAtlas("data/menu/credits/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		creditsContainer = new Container<Image>();
		creditsContainer.setFillParent(true);
		backBtnContainer = new Container<ImageButton>();
		backBtnContainer.setFillParent(true);
		coinCountContainer = new Container<Label>();
		coinCountContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		coinCountContainer.setFillParent(true);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setY(140);
		creditsTexture = new Texture(Gdx.files.internal("data/menu/credits/green_rect_huge.png"));
		creditsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		creditsImg = new Image(creditsTexture); 
		
		ImageButtonStyle backBtnStyle = new ImageButtonStyle();
		backBtnStyle.up = skin.getDrawable("yellow_rect_up");
		backBtnStyle.down = skin.getDrawable("yellow_rect_down");
		backBtnStyle.pressedOffsetY = -2;
		
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
		
		createFonts();
		
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = font2;
		textStyle.fontColor = Color.WHITE;
		
		LabelStyle creditsStyle = new LabelStyle();
		if(lang.getLanguage() == 2){
			creditsStyle.font = fontAlt;
		}else {
			creditsStyle.font = font;
		}
		creditsStyle.fontColor = Color.WHITE;	
		credits_line1 = new Label(lang.getText().get("Credits 1"), creditsStyle);
		credits_line1.setAlignment(Align.center);
		credits_line2 = new Label(lang.getText().get("Credits 2"), creditsStyle);
		credits_line2.setAlignment(Align.center);
		credits_line3 = new Label(lang.getText().get("Credits 3"), creditsStyle);
		credits_line3.setAlignment(Align.center);
		credits_line4 = new Label(lang.getText().get("Credits 4"), creditsStyle);
		credits_line4.setAlignment(Align.center);
		credits_line5 = new Label(lang.getText().get("Credits 5"), creditsStyle);
		credits_line5.setAlignment(Align.center);
		
		//layout
		coinCount = new Label(lang.getText().get("Coins") + ": " + game.getTotalCoins(), textStyle);
		
		backBtnContainer.setActor(backBtn);
		backBtnContainer.setFillParent(true);
		backBtnContainer.align(Align.topLeft);
		backBtnContainer.setPosition(20, -20);
		stage.addActor(backBtnContainer);
		
		creditsContainer.setActor(creditsImg);
		creditsContainer.setFillParent(true);
		stage.addActor(creditsContainer);
		
		coinCount.setAlignment(Align.center);
		coinCountContainer.setActor(coinCount);
		coinCountContainer.align(Align.top);
		coinCountContainer.setPosition(0, -10);
		stage.addActor(coinCountContainer);
		
		if(lang.getLanguage() == 2)table.padTop(20);
		table.add(credits_line1);
		table.row();
		table.add(credits_line2);
		table.row();
		table.add(credits_line3);
		table.row();
		table.add(credits_line4);
		table.row();
		table.add(credits_line5);

		//table.debug(); //TODO remove later
		stage.addActor(table);
	}
	
	private void createFonts() {
	    FileHandle fontFile = Gdx.files.internal("data/fonts/menufont.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = 21;
	    parameter.minFilter = TextureFilter.Linear;
	    parameter.magFilter = TextureFilter.Linear;
	    font = generator.generateFont(parameter);
	    parameter.size = 28;
	    font2 = generator.generateFont(parameter);
	    generator.dispose();
	    
	    FileHandle fontFileAlt = Gdx.files.internal("data/fonts/thefont.ttf");
	    FreeTypeFontGenerator generatorAlt = new FreeTypeFontGenerator(fontFileAlt);
	    FreeTypeFontParameter parameterAlt = new FreeTypeFontParameter();
	    parameterAlt.size = 24;
	    parameterAlt.minFilter = TextureFilter.Linear;
	    parameterAlt.magFilter = TextureFilter.Linear;
	    fontAlt = generatorAlt.generateFont(parameterAlt);
	    parameter.size = 31;
	    font2Alt = generatorAlt.generateFont(parameterAlt);
	    generatorAlt.dispose();
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
		creditsTexture.dispose();
		atlas.dispose();
		font.dispose();
		font2.dispose();
		fontAlt.dispose();
		font2Alt.dispose();
	}
	
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
	}
	
	public void show() {}
	public void pause() {}
	public void resume() {}
}
