package gameworld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import gameobjects.Bird;
import gameobjects.Coin;
import gameobjects.Grass;
import gameobjects.Pipe;
import gameobjects.Projectile;
import gameobjects.ScrollHandler;
import gameworld.GameWorld.GameMode;
import tweenAccessors.Value;
import tweenAccessors.ValueAccessor;
import fbHelpers.AssetLoader;
import fbHelpers.InputHandler;
import ui.SimpleButton;


public class GameRenderer {
	
	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch batcher;
	
	private int midPointY;
    
    // Game Objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    private Coin coin1, coin2;
    private Projectile ball1, ball2;

    // Game Assets
    private TextureRegion bg, grass, coinTex;
    private Animation birdAnimation;
    private TextureRegion birdMid;
    private TextureRegion pipeUp, pipeDown, bar;
    private TextureRegion ready, fbLogo, gameOver, highScore, 
    		scoreboard, star, noStar, retry;
    
    // Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();
    
    // Buttons
    private List<SimpleButton> menuButtons;
    private Color transitionColor;
    private SimpleButton mainMenuButton;
	
	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        
        this.midPointY = midPointY;
        
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getMenuButtons();
        
        this.mainMenuButton = ((InputHandler) Gdx.input.getInputProcessor())
        		.getMainMenuButton();
        
        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);
        
        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
        // ? setupTweens();
        
        transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
    }
	
	private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
    }
	
	private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
        coin1 = scroller.getCoin1();
        coin2 = scroller.getCoin2();
        ball1 = scroller.getBall1();
        ball2 = scroller.getBall2();

    }

    private void initAssets() {
    	bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		coinTex = AssetLoader.coinTex;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		pipeUp = AssetLoader.pipeUp;
		pipeDown = AssetLoader.pipeDown;
		bar = AssetLoader.bar;
		ready = AssetLoader.ready;
		fbLogo = AssetLoader.fbLogo;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		scoreboard = AssetLoader.scoreboard;
		retry = AssetLoader.retry;
		star = AssetLoader.star;
		noStar = AssetLoader.noStar;
    }
    
    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawPipeTops() {

        batcher.draw(pipeUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(pipeDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(pipeUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(pipeDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(pipeUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(pipeDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {

        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
        
    }
    
    private void drawBalls() {
    	shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(ball1.getCannonball().x, ball1.getCannonball().y,
        		ball1.getCannonball().radius);
        shapeRenderer.circle(ball2.getCannonball().x, ball2.getCannonball().y,
        		ball2.getCannonball().radius);
        shapeRenderer.end();
    }
    
    public void drawCoins() {
    	
    	if (!coin1.isHidden())
    	{
    		batcher.draw(coinTex, pipe2.getX() - 25,
                coin1.getY(), Coin.COIN_HEIGHT, Coin.COIN_WIDTH);
    	}
    	
    	if (!coin2.isHidden())
    	{
    		batcher.draw(coinTex, pipe3.getX() - 25,
    			coin2.getY(), Coin.COIN_HEIGHT, Coin.COIN_WIDTH);
    	}
    	
    	
    	
    }
    
    private void drawBirdCentered(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }
    
    private void drawBird(float runTime) {

        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

    }

    private void drawMenuUI() {
//        batcher.draw(AssetLoader.fbLogo, 136 / 2 - 56, midPointY - 50,
//                AssetLoader.fbLogo.getRegionWidth() / 1.2f,
//                AssetLoader.fbLogo.getRegionHeight() / 1.2f);
        
        // Draw shadow first
        AssetLoader.shadow.draw(batcher, "Flappy Bird", 136 / 2 - 50, midPointY - 50);
        // Draw text
        AssetLoader.font.draw(batcher, "Flappy Bird", 136 / 2 - 50, midPointY - 50);
        

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }
        
        // button labels
        // Draw text
        AssetLoader.whiteFont.draw(batcher, "Coins", 30, midPointY + 43);
        // Draw text
        AssetLoader.whiteFont.draw(batcher, "Normal", 56, midPointY + 43);
        // Draw text
        AssetLoader.whiteFont.draw(batcher, "Dodge", 88, midPointY + 43);

    }
    
    private void drawScoreboard() {
		batcher.draw(scoreboard, 22, midPointY - 30, 97, 37);

		batcher.draw(noStar, 25, midPointY - 15, 10, 10);
		batcher.draw(noStar, 37, midPointY - 15, 10, 10);
		batcher.draw(noStar, 49, midPointY - 15, 10, 10);
		batcher.draw(noStar, 61, midPointY - 15, 10, 10);
		batcher.draw(noStar, 73, midPointY - 15, 10, 10);

		if (myWorld.getScore() > 2) {
			batcher.draw(star, 73, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 17) {
			batcher.draw(star, 61, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 50) {
			batcher.draw(star, 49, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 80) {
			batcher.draw(star, 37, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 120) {
			batcher.draw(star, 25, midPointY - 15, 10, 10);
		}

		int length = ("" + myWorld.getScore()).length();

		AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
				104 - (2 * length), midPointY - 20);

		int length2 = ("" + AssetLoader.getHighScore()).length();
		AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
				104 - (2.5f * length2), midPointY - 3);
		
		this.mainMenuButton.draw(batcher);
		
		// button labels
        // Draw text
        AssetLoader.whiteFont.draw(batcher, "Main Menu?", 48, midPointY + 43);

	}
    
    private void drawRetry() {
		batcher.draw(retry, 36, midPointY + 10, 66, 14);
	}

	private void drawReady() {
		batcher.draw(ready, 36, midPointY - 50, 68, 14);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
	}

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }
    
    private void drawHighScore() {
		batcher.draw(highScore, 22, midPointY - 50, 96, 14);
	}
	
	public void render(float delta, float runTime) {
        Gdx.app.log("GameRenderer", "render");

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(181 / 255.0f, 236 / 255.0f, 244 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);

        // 1. Draw Grass
        drawGrass();

        // 2. Draw Pipes
        drawPipes();
        batcher.enableBlending();

        // 3. Draw PipeTops (requires transparency)
        drawPipeTops();
        
        if (this.myWorld.getCurrentMode() == GameMode.COIN)
        {
        	drawCoins();
        }
        
        if (this.myWorld.getCurrentMode() == GameMode.PROJ)
        {
        	drawBalls();
        }

        if (myWorld.isRunning()) {
			drawBird(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawBird(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawBird(runTime);
			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawBird(runTime);
			drawHighScore();
			drawRetry();
		}
        
        drawGrass();
        
        // End SpriteBatch
        batcher.end();
        
        drawTransition(delta);
        
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
//        
//        /*
//         * Excuse the mess below. Temporary code for testing bounding
//         * rectangles.
//         */
//        // Bar up for pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
//                pipe1.getBarUp().width, pipe1.getBarUp().height);
//        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
//                pipe2.getBarUp().width, pipe2.getBarUp().height);
//        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
//                pipe3.getBarUp().width, pipe3.getBarUp().height);
//
//        // Bar down for pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
//                pipe1.getBarDown().width, pipe1.getBarDown().height);
//        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
//                pipe2.getBarDown().width, pipe2.getBarDown().height);
//        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
//                pipe3.getBarDown().width, pipe3.getBarDown().height);
//
//        // Pipe up for Pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getPipeUp().x, pipe1.getPipeUp().y,
//                pipe1.getPipeUp().width, pipe1.getPipeUp().height);
//        shapeRenderer.rect(pipe2.getPipeUp().x, pipe2.getPipeUp().y,
//                pipe2.getPipeUp().width, pipe2.getPipeUp().height);
//        shapeRenderer.rect(pipe3.getPipeUp().x, pipe3.getPipeUp().y,
//                pipe3.getPipeUp().width, pipe3.getPipeUp().height);
//
//        // Pipe down for Pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getPipeDown().x, pipe1.getPipeDown().y,
//                pipe1.getPipeDown().width, pipe1.getPipeDown().height);
//        shapeRenderer.rect(pipe2.getPipeDown().x, pipe2.getPipeDown().y,
//                pipe2.getPipeDown().width, pipe2.getPipeDown().height);
//        shapeRenderer.rect(pipe3.getPipeDown().x, pipe3.getPipeDown().y,
//                pipe3.getPipeDown().width, pipe3.getPipeDown().height);
//        
//        shapeRenderer.end();
        
    }
	
	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}
	
	private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

}
