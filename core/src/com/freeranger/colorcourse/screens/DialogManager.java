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
import com.freeranger.colorcourse.handlers.LanguageManager;

public class DialogManager implements Screen {
	
	private boolean canifade = true;
	
	private Main game;
	private boolean show;
		
	//Scene2D
	private Stage stage;
	private Table table;
	
	private Container<TextButton> button1Container, button2Container;
	private Container<Label> labelContainer;
	private Container<Image> bgRectContainer;
	
	private Label label;
	private TextButton button1;
	private TextButton button2;
	private Texture bgRectTexture;
	private Image bgRect;
	
	private Skin skin;
	private BitmapFont font, fontAlt; 
	private TextureAtlas atlas;
	private LanguageManager lang;
			
	public DialogManager(final Main game, String question, String option1, boolean option1Alt, String option2, boolean option2Alt, final Screen host, boolean useAltFont) {
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
		
		//here goes all container inits
		bgRectContainer = new Container<Image>();
		bgRectContainer.setFillParent(true);
		labelContainer = new Container<Label>();
		button1Container = new Container<TextButton>();
		button2Container = new Container<TextButton>();
		bgRectTexture = new Texture(Gdx.files.internal("data/menu/dialog/green_rect_huge.png"));
		bgRectTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgRect = new Image(bgRectTexture);
		
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		createFonts();
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = font;
			if(useAltFont){
				textStyle.font = fontAlt;
			}else{
				textStyle.font = font;
			}
		textStyle.fontColor = Color.WHITE;
		
		TextButtonStyle option1BtnStyle = new TextButtonStyle();
		option1BtnStyle.up = skin.getDrawable("yellow_rect_up");
		option1BtnStyle.down = skin.getDrawable("yellow_rect_down");
		if(option1Alt){
			option1BtnStyle.font = fontAlt;
		}else{
			option1BtnStyle.font = font;
		}
		option1BtnStyle.pressedOffsetY = -2;
		
		TextButtonStyle option2BtnStyle = new TextButtonStyle();
		option2BtnStyle.up = skin.getDrawable("yellow_rect_up");
		option2BtnStyle.down = skin.getDrawable("yellow_rect_down");
		if(option2Alt){
			option2BtnStyle.font = fontAlt;
		}else{
			option2BtnStyle.font = font;
		}
		option2BtnStyle.pressedOffsetY = -2;
		
		// Create buttons and labels
		button1 = new TextButton(lang.getText().get(option1), option1BtnStyle);
		button1.pad(0, 0, 5, 2);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				// Do something
				if(host.getClass().getSimpleName().equals("Options")){
					((Options) host).option1();
					if(game.getSoundSetting()) playSound("click");
				}
			}
		});
		
		button2 = new TextButton(lang.getText().get(option2), option2BtnStyle);
		button2.pad(0, 0, 5, 2);
		button2.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				// Do something
				if(host.getClass().getSimpleName().equals("Options")){
					((Options) host).option2();
					if(game.getSoundSetting()) playSound("click");
				}
			}
		});

		label = new Label(lang.getText().get(question), textStyle);

		//// Table Layout ///////
		table.add(button1);
		table.getCell(button1).padRight(35);
		table.add(button2);
		
		bgRectContainer.setActor(bgRect);
		bgRectContainer.setFillParent(true);
		bgRectContainer.align(Align.center);
		bgRectContainer.setPosition(0, 40);
		stage.addActor(bgRectContainer);
		
		labelContainer.setActor(label);
		labelContainer.setFillParent(true);
		labelContainer.align(Align.center);
		labelContainer.setPosition(0, 100);
		stage.addActor(labelContainer);
		
		/////////////////////////
		stage.addActor(table);
	}
	
	private void createFonts() {
	    FileHandle fontFile = Gdx.files.internal("data/fonts/menufont.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = 24;
	    parameter.minFilter = TextureFilter.Linear;
	    parameter.magFilter = TextureFilter.Linear;
	    font = generator.generateFont(parameter);
	    generator.dispose();
	    
	    FileHandle fontFileAlt = Gdx.files.internal("data/fonts/thefont.ttf");
	    FreeTypeFontGenerator generatorAlt = new FreeTypeFontGenerator(fontFileAlt);
	    FreeTypeFontParameter parameterAlt = new FreeTypeFontParameter();
	    parameterAlt.size = 24;
	    parameterAlt.minFilter = TextureFilter.Linear;
	    parameterAlt.magFilter = TextureFilter.Linear;
	    fontAlt = generatorAlt.generateFont(parameterAlt);
	    generatorAlt.dispose();
	}

	public void update(float delta) {
		if(show){
			
		}
	}
	
	public void playSound(String sound){
		if(sound.equals("click")){
			game.ui_click.play();
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
		font.dispose();
		fontAlt.dispose();
	}
	
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
	}
	
	public void show() {}
	public void pause() {}
	public void resume() {}
}
