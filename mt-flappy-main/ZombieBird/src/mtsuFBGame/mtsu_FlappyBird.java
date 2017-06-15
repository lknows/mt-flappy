package mtsuFBGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import screens.SplashScreen;
import fbHelpers.AssetLoader;

public class mtsu_FlappyBird extends Game{

	@Override
	public void create() {
		Gdx.app.log("mtsu_FlappyBird", "created");
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}
	
	@Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}