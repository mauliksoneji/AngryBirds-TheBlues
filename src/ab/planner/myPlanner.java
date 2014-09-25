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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class myPlanner {

    public static boolean isHorizontal(Point targetPoint){
        BufferedImage screenshot = ActionRobot.doScreenShot();
        Vision vision = new Vision(screenshot);

        List<ABObject> blocks = vision.findBlocksMBR();
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
            System.out.println(block.area+"________area");
        }
        System.out.println(sum_h+"~~~~~~~~~~~~~~~"+sum_v);
        if(sum_h <= sum_v)
            return true;
        return false;
    }
}
