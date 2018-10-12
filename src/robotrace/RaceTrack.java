package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        final float N = 25;
        Vector rem = Vector.O;
        Vector remn = Vector.O;

        for (int i = 0; i <= N; i++) {
            Vector v = getPoint(i/N);
            Vector n = getNormal(i/N);

            if (rem != Vector.O && remn != Vector.O) {
                gl.glColor3f(0f,255f,0f);
                gl.glBegin(gl.GL_POLYGON);
                    gl.glVertex3d(v.x, v.y, v.z);
                    gl.glVertex3d(rem.x, rem.y, rem.z);
                    gl.glVertex3d(rem.x + 2 * laneWidth * remn.x, rem.y + 2 * laneWidth * remn.y, rem.z);
                    gl.glVertex3d(v.x + 2 * laneWidth * n.x, v.y + 2 * laneWidth * n.y, v.z);
                gl.glEnd();
                gl.glColor3f(0f,255f,255f);
                gl.glBegin(gl.GL_POLYGON);
                    gl.glVertex3d(v.x, v.y, v.z);
                    gl.glVertex3d(rem.x, rem.y, rem.z);
                    gl.glVertex3d(rem.x - 2 * laneWidth * remn.x, rem.y - 2 * laneWidth * remn.y, rem.z);
                    gl.glVertex3d(v.x - 2 * laneWidth * n.x, v.y - 2 * laneWidth * n.y, v.z);
                gl.glEnd();
            }
            rem = v;
            remn = n;
        }
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){

        return Vector.O;

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return Vector.O;

    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);

    protected abstract Vector getNormal(double t);
}
