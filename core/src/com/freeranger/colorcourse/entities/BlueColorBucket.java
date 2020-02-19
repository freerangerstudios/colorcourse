package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;

public class BlueColorBucket extends B2DSprite{
	
	@SuppressWarnings("unused")
	private Main game;

	public BlueColorBucket(Body body, Main game) {
		super(body);
		this.game = game;
		
		Texture tex = game.blue_cb;
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1/12f);
	}

}
