package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction[] = { new Vector(0, 1, 0), new Vector(0, 1, 0) };

    /** The material from which this robot is built. */
    private final Material material;

    public float headSize = 1.2f;
    public float torsoLength = 3f;
    public float torsoThickness = 1f;
    public float armLength = 6;
    public float armThickness = 1f;
    public float legLength = 6;
    public float legThickness = 1f;
    
    

    public double totalAngle[] = { 0, 0 };

    public float baseSpeed;

    public float t;

    public int e, f;

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
        Vector currentPosition = position.add(new Vector(0, 0, .5 * (this.legLength + this.torsoLength) + Math.cos(tAnim * 2) * .125));
        gl.glColor3f(0, 0, 0);

        gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_SPECULAR, material.specular, 0);
        gl.glMaterialf(gl.GL_FRONT_AND_BACK, gl.GL_SHININESS, material.shininess);

        gl.glPushMatrix();
            gl.glRotated(90, 0, 0, 1);
            gl.glScaled(0.25,0.25,0.25);
            gl.glTranslated(currentPosition.x, currentPosition.y, currentPosition.z);
            gl.glScaled(this.torsoThickness, 2 * this.torsoThickness, this.torsoLength);
            glut.glutSolidCube(1);
            gl.glScaled((float)1/this.torsoThickness, (float)1/(2*this.torsoThickness), (float)1/this.torsoLength);

            drawHead(gl, glu, glut, tAnim);

            // draw left arm
            gl.glTranslated(0, this.torsoThickness + .5 * this.armThickness, .5 * this.torsoLength);
            drawArm(gl, glu, glut, (float)Math.cos(tAnim) * 18, (float)(30 - Math.cos(tAnim) * 15));
            // draw right arm
            gl.glTranslated(0, -this.torsoThickness * 2 - this.armThickness, 0);
            drawArm(gl, glu, glut, -(float)Math.cos(tAnim) * 18,  (float)(30 + Math.cos(tAnim) * 15));

            // draw left leg
            gl.glTranslated(0, 2 * this.torsoThickness, -this.torsoLength);
            drawLeg(gl, glu, glut, -(float)Math.cos(tAnim) * 25,  (float)(-18.5 - Math.cos(tAnim + 3.14/2) * 16.5));
            // draw right leg
            gl.glTranslated(0, -this.torsoThickness, 0);
            drawLeg(gl, glu, glut, (float)Math.cos(tAnim) * 25, (float)(-18.5 + Math.cos(tAnim + 3.14/2) * 16.5));
        gl.glPopMatrix();
    }

    private void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
            gl.glTranslated(0, 0, .5 * (this.torsoLength + this.headSize));
            glut.glutSolidCube(this.headSize);
            drawHat(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }

    private void drawHat(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
            gl.glTranslated(0, 0, this.headSize * .5);
            glut.glutSolidCone(1.5, 1, 10, 10);
        gl.glPopMatrix();
    }

    private void drawArm(GL2 gl, GLU glu, GLUT glut, float joint1, float joint2) {
        drawLimb(gl, glu, glut, joint1, joint2, 0.5f, this.armLength, this.armThickness);
    }

    private void drawLeg(GL2 gl, GLU glu, GLUT glut, float joint1, float joint2) {
        drawLimb(gl, glu, glut, joint1, joint2, 0f, this.legLength, this.legThickness);
    }

    private void drawLimb(GL2 gl, GLU glu, GLUT glut, float joint1, float joint2, float jointHeight, float length, float thickness) {
        gl.glColor3f(1, 0, 0);
        gl.glPushMatrix();
            // translate to joint position
            gl.glTranslated(0, 0, -jointHeight);
            gl.glRotated(joint1, 0, 1, 0);
            gl.glTranslated(0, 0, jointHeight);

            // draw top of limb
            gl.glScaled(thickness, thickness, .25 * length);
            gl.glTranslated(0, 0, -0.5);
            glut.glutSolidCube(1);

            // do another joint
            gl.glScaled(1/thickness, 1/thickness, 4/length);
            gl.glTranslated(0, 0, -0.5);
            gl.glRotated(-joint2, 0, 1, 0);
            gl.glTranslated(0, 0, 0.5);
            gl.glScaled(thickness, thickness, .25*length);

            gl.glTranslated(0, 0, -1);
            glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public double getSpeed() {
        return baseSpeed + e * Math.cos(f * t);
    }
}
