package fbHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import gameobjects.Bird;
import gameworld.GameWorld;
import gameworld.GameWorld.GameMode;
import ui.SimpleButton;

public class InputHandler implements InputProcessor {
	
	private Bird myBird;
	private GameWorld myWorld;
	
	private List<SimpleButton> menuButtons;

    private SimpleButton playButton;
    private SimpleButton coinButton;
    private SimpleButton projButton;
    
    private SimpleButton mainMenu;

    private float scaleFactorX;
    private float scaleFactorY;
	
	// Ask for a reference to the Bird when InputHandler is created.
    public InputHandler(GameWorld myWorld, float scaleFactorX,
            float scaleFactorY) {
        this.myWorld = myWorld;
        myBird = myWorld.getBird();

        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(playButton);
        coinButton = new SimpleButton(
                (136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2)) - AssetLoader.playButtonUp.getRegionWidth(),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(coinButton);
        projButton = new SimpleButton(
                (136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2)) + AssetLoader.playButtonUp.getRegionWidth(),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(projButton);
        
        mainMenu = new SimpleButton(
                (136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2)),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
    }
	
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        
        if (myWorld.isMenu()) 
        {
        	if (playButton.isClicked(screenX, screenY))
        	{
        		playButton.isTouchDown(screenX, screenY);
        	}
        	else if (coinButton.isClicked(screenX, screenY))
        	{
        		coinButton.isTouchDown(screenX, screenY);
        	}
        	else if (projButton.isClicked(screenX, screenY))
        	{
        		projButton.isTouchDown(screenX, screenY);
        	}
        	
		} 
        else if (myWorld.isReady()) 
        {
			myWorld.start();
			myBird.onClick();
		} 
        else if (myWorld.isRunning()) 
        {
			myBird.onClick();
		}

		if (myWorld.isGameOver() || myWorld.isHighScore()) 
		{
			if (mainMenu.isClicked(screenX, screenY))
        	{
				mainMenu.isTouchDown(screenX, screenY);
        	}
			else
			{
				myWorld.restart();
			}
		}

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {

        // Can now use Space Bar to play the game, only works with normal mode
        if (keycode == Keys.SPACE) {

            if (myWorld.isMenu()) {
                myWorld.ready(GameMode.NORMAL);
            } else if (myWorld.isReady()) {
                myWorld.start();
            }

            myBird.onClick();

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                myWorld.restart();
            }

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) 
        {
            if (playButton.isTouchUp(screenX, screenY)) 
            {
                myWorld.ready(GameMode.NORMAL);
                return true;
            }
            if (coinButton.isTouchUp(screenX, screenY)) 
            {
                myWorld.ready(GameMode.COIN);
                return true;
            }
            if (projButton.isTouchUp(screenX, screenY)) 
            {
                myWorld.ready(GameMode.PROJ);
                return true;
            }
        }
        else if (myWorld.isGameOver() || myWorld.isHighScore())
        {
        	if (mainMenu.isTouchUp(screenX, screenY))
        	{
        		myWorld.menu();
        	}
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
	
	public SimpleButton getMainMenuButton() {
		return mainMenu;
	}

}
