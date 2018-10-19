package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glColor3f(0, 1, 1);
        for (int i = -20; i < 20; i++) {
            for (int z = -20; z < 20; z++) {
                gl.glBegin(gl.GL_TRIANGLE_STRIP);
                    gl.glVertex3i(i, z, 0);
                    gl.glVertex3i(i + 1, z, 0);
                    gl.glVertex3i(i, z + 1, 0);
                    gl.glVertex3i(i + 1, z + 1, 0);
                gl.glEnd();
            }
        }
    }
}