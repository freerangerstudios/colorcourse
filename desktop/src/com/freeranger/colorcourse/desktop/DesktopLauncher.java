package com.freeranger.colorcourse.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.freeranger.colorcourse.Main;

import java.awt.*;

public class DesktopLauncher {
	
	// RESTRICTION: COLOR BUCKETS HAVE TO BE PLACED A BLOCK OVER GROUND NOT CONTACT WITH GROUND BECAUSE THEN PLAYER CAN STAND 
	//				STILL AND WON'T GET KILLED BY BEING WRONG COLOR OR SOMETHING IT'S WEIRD BUT IT'S A BOX2D RESTRICTION BOI RIP.

	//	- To export: open git in project folder (the one w/gradlew.bat) and run './gradlew desktop:dist'

	// TODO Add icon
	
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		cfg.height = Toolkit.getDefaultToolkit().getScreenSize().height;
		cfg.title = "Color Course | FPS: ??";
		cfg.fullscreen = true;
		cfg.backgroundFPS = -1;
		cfg.foregroundFPS = 60;
		cfg.resizable = true;
		cfg.addIcon("data/icon_64x.png", Files.FileType.Internal);
		cfg.addIcon("data/icon_48x.png", Files.FileType.Internal);
		cfg.addIcon("data/icon_32x.png", Files.FileType.Internal);
		cfg.addIcon("data/icon_24x.png", Files.FileType.Internal);
		cfg.addIcon("data/icon_16x.png", Files.FileType.Internal);


		new LwjglApplication(new Main(), cfg);
	}
}