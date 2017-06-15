package gameobjects;

import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Pipe extends Scrollable {

    private Random r;
    
    private Rectangle pipeUp, pipeDown, barUp, barDown;

    public static final int VERTICAL_GAP = 45;
    public static final int PIPE_WIDTH = 24;
    public static final int PIPE_HEIGHT = 11;
    private float groundY;
    
    private boolean isScored = false;

    // When Pipe's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Pipe(float x, float y, int width, int height, float scrollSpeed,
            float groundY) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        pipeUp = new Rectangle();
        pipeDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();

        this.groundY = groundY;
    }
    
    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // Our pipe width is 24. The bar is only 22 pixels wide. So the pipe
        // must be shifted by 1 pixel to the left (so that the pipe is centered
        // with respect to its bar).
        
        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        pipeUp.set(position.x - (PIPE_WIDTH - width) / 2, position.y + height
                - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT);
        pipeDown.set(position.x - (PIPE_WIDTH - width) / 2, barDown.y,
        		PIPE_WIDTH, PIPE_HEIGHT);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(90) + 15;
        isScored = false;
    }
    
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }
    
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), pipeUp) || Intersector
                        .overlaps(bird.getBoundingCircle(), pipeDown));
        }
        return false;
    }
    
    public Rectangle getPipeUp() {
        return pipeUp;
    }

    public Rectangle getPipeDown() {
        return pipeDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }
    
    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

}
