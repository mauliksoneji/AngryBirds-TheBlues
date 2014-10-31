package ab.planner;

/**
 * Created by Mayank on 25-Sep-14.
 */
import ab.demo.other.ActionRobot;
import ab.vision.ABObject;
import ab.vision.ABType;
import ab.vision.Vision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class heuristic {

    public static ABObject activeBird;

    public static boolean isHorizontal(Point targetPoint){
        BufferedImage screenshot = ActionRobot.doScreenShot();
        Vision vision = new Vision(screenshot);

        List<ABObject> blocks = vision.findBlocksMBR();
        List<ABObject> birds = vision.findBirdsMBR();
        List<ABObject> hills = vision.findHills();
        Rectangle sling = vision.findSlingshotMBR();

        for(ABObject hill : hills)
        {
            if(targetPoint.getX()-hill.getCenter().getX() >= 0 &&
                    targetPoint.getX()-hill.getCenter().getX() < 50 &&
                    targetPoint.getY()-hill.getCenter().getY() >= 0 &&
                    targetPoint.getY()-hill.getCenter().getY() < 15)
                return false;
        }

        if (birds.isEmpty()) activeBird = null;
        else activeBird = birds.get(0);

        if(activeBird != null) {
            double min = Point.distance(sling.getCenterX(), sling.getCenterY(), activeBird.getCenterX(), activeBird.getCenterY());
            for (ABObject a_Bird : birds) {
                if (Point.distance(sling.getCenterX(), sling.getCenterY(), a_Bird.getCenterX(), a_Bird.getCenterY()) < min) {
                    min = Point.distance(sling.getCenterX(), sling.getCenterY(), a_Bird.getCenterX(), a_Bird.getCenterY());
                    activeBird = a_Bird;
                }
            }

            if (activeBird.getType().toString().equalsIgnoreCase("yellowbird")) {
                return true;
            }
        }
        HashMap<ABType,Integer> weight = new HashMap<ABType,Integer>();
        weight.put(ABType.Stone,3);
        weight.put(ABType.Ice,1);
        weight.put(ABType.Wood,2);

        int sum_h = 0,sum_v = 0;
        for(ABObject block : blocks)
        {
            if(targetPoint.getX()-block.getCenter().getX() >= 0 &&
                    targetPoint.getX()-block.getCenter().getX() < 100 &&
                    targetPoint.getY()-block.getCenter().getY() >= 0 &&
                    targetPoint.getY()-block.getCenter().getY() < 15)
            {
                sum_h +=  weight.get(block.type);
            }

            else if (targetPoint.getX()-block.getCenter().getX() >= 0 &&
                    targetPoint.getX()-block.getCenter().getX() < 15 &&
                    targetPoint.getY()-block.getCenter().getY() >= 0 &&
                    targetPoint.getY()-block.getCenter().getY() < 100)
            {
                sum_v +=  weight.get(block.type);
            }
        }
        if(sum_h <= sum_v)
            return true;
        return false;
    }
}
