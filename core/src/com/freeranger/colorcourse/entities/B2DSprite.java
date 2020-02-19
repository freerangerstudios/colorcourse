package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.handlers.Animation;
import com.freeranger.colorcourse.handlers.B2DVars;

public class B2DSprite {
	
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;
	protected Vector2 velocity;
	
	public B2DSprite(Body body){
		this.body = body;
		animation = new Animation();
		velocity = new Vector2(-0.5f, -2);
	}
	
	public void setAnimation(TextureRegion[] reg, float delay){
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}
	
	public void update(float delta){
		animation.update(delta);
	}
	
	public void setVelocity(float x){
		velocity.x = x;
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		sb.draw(
		  animation.getFrame(), 
		  body.getPosition().x * B2DVars.PPM - (width / 2),
		  body.getPosition().y * B2DVars.PPM - (height / 2)
	    );
		sb.end();
	}
	
	public Body getBody(){return body;}
	public Vector2 getPosition(){return body.getPosition();}
	public float getWidth(){return width;}
	public float getHeight(){return height;}
}
