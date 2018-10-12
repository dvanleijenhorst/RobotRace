package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glColor3f(0, 0, 0);
        gl.glTranslated(0, 0, 4.5);
        gl.glPushMatrix();
            gl.glScaled(1, 2, 3);
            glut.glutSolidCube(1);
            gl.glScaled(1, (float)1/2, (float)1/3);
            drawHead(gl, glu, glut, tAnim);
            // draw left arm
            gl.glTranslated(0, 1.5, 1.5);
            drawLimb(gl, glu, glut, 18, 35);
            // draw right arm
            gl.glTranslated(0, -3, 0);
            drawLimb(gl, glu, glut, -18, 35);
            // draw left leg
            gl.glTranslated(0, 2, -3);
            drawLimb(gl, glu, glut, -25, -12);
            // draw right leg
            gl.glTranslated(0, -1, 0);
            drawLimb(gl, glu, glut, 25, -25);
        gl.glPopMatrix();
        gl.glTranslated(0, 0, -4.5);
    }

    private void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
            gl.glTranslated(0, 0, 2);
            glut.glutSolidCube((float)1.2);
            drawHat(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }

    private void drawHat(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
            gl.glTranslated(0, 0, .5);
            glut.glutSolidCone(1.5, 1, 10, 10);
        gl.glPopMatrix();
    }

    private void drawLimb(GL2 gl, GLU glu, GLUT glut, float joint1, float joint2) {
        gl.glColor3f(1, 0, 0);
        gl.glPushMatrix();
            // translate to joint position
            gl.glTranslated(0, 0, -0.5);
            gl.glRotated(joint1, 0, 1, 0);
            gl.glTranslated(0, 0, 0.5);

            // draw top of limb
            gl.glScaled(1, 1, 1.5);
            gl.glTranslated(0, 0, -0.5);
            glut.glutSolidCube(1);

            // do another joint
            gl.glScaled(1, 1, 1/1.5);
            gl.glTranslated(0, 0, -0.5);
            gl.glRotated(-joint2, 0, 1, 0);
            gl.glTranslated(0, 0, 0.5);
            gl.glScaled(1, 1, 1.5);

            gl.glTranslated(0, 0, -1);
            glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    
}
