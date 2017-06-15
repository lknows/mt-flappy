package gameobjects;

import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Circle;

public class Projectile extends Scrollable {

    private Random r;
    
    private Circle cannonball;

    public static final int VERTICAL_GAP = 45;
    public static final int COIN_WIDTH = 7;
    public static final int COIN_HEIGHT = 4;
    private float groundY;
    

    // When Coin's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Projectile(float x, float y, int width, int height, float scrollSpeed,
            float groundY) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        
        cannonball = new Circle();

        this.groundY = groundY;
    }
    
    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
        
     // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f; -- same dimensitons as the bird, make smaller?
        cannonball.set(position.x - 9, position.y, 6.5f);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the scroll speed to r
        velocity.x = -(r.nextInt(40) + 59);
        position.y = r.nextInt(40) + 55;
       
    }
    
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
       
        reset(x);
    }
    
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth() ) {
            return (Intersector.overlaps(bird.getBoundingCircle(), cannonball)
                    );
        }
        return false;
    }
    
    public Circle getCannonball() {
        return cannonball;
    }
    

}