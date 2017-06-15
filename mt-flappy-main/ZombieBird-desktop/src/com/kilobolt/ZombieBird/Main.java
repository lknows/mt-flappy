package com.kilobolt.ZombieBird;

import mtsuFBGame.mtsu_FlappyBird;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ZombieBird";
		cfg.useGL20 = false;
		cfg.width = 1080 / 3;
		cfg.height = 1920 / 3;
		
		new LwjglApplication(new mtsu_FlappyBird(), cfg);
	}
}
