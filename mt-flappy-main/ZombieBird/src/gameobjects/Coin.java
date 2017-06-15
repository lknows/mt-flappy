package gameobjects;

import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Coin extends Scrollable {

    private Random r;
    
    private Rectangle coin;

    public static final int VERTICAL_GAP = 45;
    public static final int COIN_WIDTH = 7;
    public static final int COIN_HEIGHT = 4;
    private float groundY;
    
    private boolean isScored = false;
    
    private boolean isHidden = false;

    // When Coin's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Coin(float x, float y, int width, int height, float scrollSpeed,
            float groundY) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        
        coin = new Rectangle();

        this.groundY = groundY;
    }
    
    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        coin.set(position.x, position.y, width, height);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        position.y = r.nextInt(40) + 55;
        isScored = false;
        isHidden = false;
    }
    
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        isHidden = false;
        reset(x);
    }
    
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth() ) {
            return (Intersector.overlaps(bird.getBoundingCircle(), coin)
                    );
        }
        return false;
    }
    
    public Rectangle getCoin() {
        return coin;
    }
    
    public boolean isScored() {
        return isScored;
    }
    
    public boolean isHidden() {
        return isHidden;
    }

    public void setScored(boolean b) {
        isScored = b;
    }
    
    public void hideCoin() {
        isHidden = true;
    }

}