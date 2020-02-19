package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;

public class GreenJumppad extends B2DSprite{
	
	@SuppressWarnings("unused")
	private Main game;

	public GreenJumppad(Body body, Main game) {
		super(body);
		this.game = game;
		
		Texture tex = game.green_jumppad;
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1/12f);	
	}
	
	public void update(float delta){}
}
