
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    private Vector[] controlPoints;

    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
    }
    
    @Override
    protected Vector getPoint(double t) {
        if (t == 0) {
            return controlPoints[0];
        }
        t *= 4;
        double u = t - ((t == Math.floor(t)) ? Math.floor(t) - 1 : Math.floor(t));
        int seg = (t == Math.floor(t)) ? (int)Math.floor(t) - 1 : (int)Math.floor(t);
        Vector p0 = controlPoints[3 * seg];
        Vector p1 = controlPoints[3 * seg + 1];
        Vector p2 = controlPoints[3 * seg + 2];
        Vector p3 = controlPoints[(3 * seg + 3) % 12];
        return new Vector(  Math.pow(1 - u, 3) * p0.x +
                            3 * u * Math.pow(1 - u, 2) * p1.x +
                            3 * Math.pow(u, 2) * (1 - u) * p2.x +
                            Math.pow(u, 3) * p3.x,
                            Math.pow(1 - u, 3) * p0.y +
                            3 * u * Math.pow(1 - u, 2) * p1.y +
                            3 * Math.pow(u, 2) * (1 - u) * p2.y +
                            Math.pow(u, 3) * p3.y,
                            1);
    }

    @Override
    protected Vector getTangent(double t) {
        if (t == 0) {
            return new Vector(  -3 * controlPoints[0].x +
                                3 * controlPoints[1].x,
                                -3 * controlPoints[0].y +
                                3 * controlPoints[1].y,
                                0);
        }
        t *= 4;
        double u = t - ((t == Math.floor(t)) ? Math.floor(t) - 1 : Math.floor(t));
        int seg = (t == Math.floor(t)) ? (int)Math.floor(t) - 1 : (int)Math.floor(t);
        Vector p0 = controlPoints[3 * seg];
        Vector p1 = controlPoints[3 * seg + 1];
        Vector p2 = controlPoints[3 * seg + 2];
        Vector p3 = controlPoints[(3 * seg + 3) % 12];
        return new Vector(  3 * (Math.pow(u, 2) * (p3.x - p0.x + 3 * p1.x - 3 * p2.x) +
                            2 * u * (p0.x - 2 * p1.x + p2.x) - p0.x + p1.x),
                            3 * (Math.pow(u, 2) * (p3.y - p0.y + 3 * p1.y - 3 * p2.y) +
                            2 * u * (p0.y - 2 * p1.y + p2.y) - p0.y + p1.y),
                            0);
    }
}
