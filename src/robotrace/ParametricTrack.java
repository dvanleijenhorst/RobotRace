
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) {
        return new Vector(10 * Math.cos(2 * Math.PI * t),
                          14 * Math.sin(2 * Math.PI * t),
                          1);
    }

    @Override
    protected Vector getTangent(double t) {
        return new Vector(-20 * Math.PI * Math.sin(2 * Math.PI * t),
                           28 * Math.PI * Math.cos(2 * Math.PI * t),
                           0);
    }

    @Override
    protected Vector getNormal(double t) {
        double length = Math.sqrt(Math.pow(28 * Math.PI * Math.cos(2 * Math.PI * t), 2) +
                                  Math.pow(20 * Math.PI * Math.sin(2 * Math.PI * t), 2));
        return new Vector(28 * Math.PI * Math.cos(2 * Math.PI * t) / length,
                          20 * Math.PI * Math.sin(2 * Math.PI * t) / length,
                          0);
    }
}
