package ab.demo;

import ab.demo.other.Shot;
import ab.planner.TrajectoryPlanner;
import ab.vision.ABObject;
import ab.vision.Vision;

import java.awt.*;
import java.util.*;

/**
 * Created by Mayank on 31-Oct-14.
 */
public class findHills
{
    private static TrajectoryPlanner tp = new TrajectoryPlanner();
    public static boolean isReachable(Vision vision, Point target, Shot shot) {
        //test whether the trajectory can pass the target without considering obstructions
        Point releasePoint = new Point(shot.getX() + shot.getDx(), shot.getY() + shot.getDy());
        int traY = tp.getYCoordinate(vision.findSlingshotMBR(), releasePoint, target.x);
        if (Math.abs(traY - target.y) > 100) {
            //System.out.println(Math.abs(traY - target.y));
            return false;
        }
        System.out.println("No. of hills :"+vision.findHills().size());
        boolean result = true;
        java.util.List<Point> points = tp.predictTrajectory(vision.findSlingshotMBR(), releasePoint);
        for (Point point : points) {
            if (point.x < 840 && point.y < 480 && point.y > 100 && point.x > 400)
                for (ABObject ab : vision.findHills()) {
                    if (
                            ((ab.contains(point) && !ab.contains(target)) || Math.abs(vision.getMBRVision()._scene[point.y][point.x] - 72) < 10)
                                    && point.x < target.x
                            )
                        return false;
                }

        }
        return result;
    }
}
