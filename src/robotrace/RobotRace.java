package robotrace;

import java.util.Random;

import static com.jogamp.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 *
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 *
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 *
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures.
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 *
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd();
 *
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {

    /** Array of the four robots. */
    private final Robot[] robots;

    /** Instance of the camera. */
    private final Camera camera;

    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;

    /** Instance of the terrain. */
    private final Terrain terrain;

    private long startTime = System.currentTimeMillis();
    private long updatedT = System.currentTimeMillis();

    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {

        // Create a new array of four robots
        robots = new Robot[4];

        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD

        );

        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER

        );

        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD

        );

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE

        );

        Random r = new Random(System.currentTimeMillis());

        for (Robot robot : robots) {
            robot.baseSpeed = r.nextInt(60 - 40) + 40;
            robot.e = r.nextInt(5 - 1) + 1;
            robot.f = r.nextInt(255 - 245) + 245;
        }

        // Initialize the camera
        camera = new Camera();

        // Initialize the race tracks
        raceTracks = new RaceTrack[2];

        // Track 1
        raceTracks[0] = new ParametricTrack();

        // Track 2
        float g = 3.5f;
        raceTracks[1] = new BezierTrack(
            // First bezier, more complex
            /*new Vector[] {
                new Vector( 12, 4, 1),
                new Vector( 12, 12, 1),
                new Vector( 4, 4, 1),
                new Vector(-4, 12, 1),
                new Vector(-12, 20, 1),
                new Vector(-20, 12, 1),
                new Vector(-12, 4, 1),
                new Vector(-4, -4, 1),
                new Vector(-12, -12, 1),
                new Vector(4, -16, 1),
                new Vector(20, -20, 1),
                new Vector(12, -4, 1)
            }*/
            // Simpler bezier
            new Vector[] {
                new Vector(5, 0, 1),
                new Vector(5, 10, 1),
                new Vector(20, 20, 1),
                new Vector(0, 20, 1),
                new Vector(-20, 20, 1),
                new Vector(-5, 10, 1),
                new Vector(-5, 0, 1),
                new Vector(-5, -10, 1),
                new Vector(-20, -20, 1),
                new Vector(0, -20, 1),
                new Vector(20, -20, 1),
                new Vector(5, -10, 1)
            }
        );

        // Initialize the terrain
        terrain = new Terrain();
    }

    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {

        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);

        // Enable face culling for improved performance
        // gl.glCullFace(GL_BACK);
        // gl.glEnable(GL_CULL_FACE);

	    // Normalize normals.
        gl.glEnable(GL_NORMALIZE);

	// Try to load four textures, add more if you like in the Textures class
        Textures.loadTextures();
        reportError("reading textures");

        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");

    }

    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);

        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);

        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        // Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{5f,5f,5f,1f}, 0);

        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        Robot lowest = robots[0];
        for (Robot robot : robots) {
            if (robot.t < lowest.t) {
                lowest = robot;
            }
        }
        camera.update(gs, lowest);
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                      camera.center.x(), camera.center.y(), camera.center.z(),
                      camera.up.x(),     camera.up.y(),     camera.up.z());
    }

    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {

        gl.glUseProgram(defaultShader.getProgramID());
        reportError("program");

        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);

        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);

        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);

        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


        // Draw hierarchy example.
        //drawHierarchy();

        // Draw the axis frame.
        if (gs.showAxes) {
            drawAxisFrame();
        }

        // Draw the (first) robot.
        gl.glUseProgram(robotShader.getProgramID());

        long oldT = System.currentTimeMillis() - startTime;
        float t = oldT - updatedT;
        updatedT = oldT;

        for (int i = 0; i < 4; i++) {
            gl.glPushMatrix();

            if (robots[i].t == 0) {
                robots[i].t = oldT;
            } else {
                robots[i].t += t / robots[i].getSpeed();
            }

            Vector p = raceTracks[gs.trackNr].getLanePoint(i, robots[i].t / 250);
            Vector v = raceTracks[gs.trackNr].getLaneTangent(i, robots[i].t / 250);
            Vector f = robots[i].direction[gs.trackNr];

            gl.glTranslated(p.x, p.y, p.z);

            // Angle between f and v
            double fdotv = f.x * v.x + f.y * v.y + f.z * v.z;
            double lf = Math.sqrt(Math.pow(f.x, 2) + Math.pow(f.y, 2) + Math.pow(f.z, 2));
            double lv = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2) + Math.pow(v.z, 2));

            // Since acos returns a radian, we convert to degrees
            double angle = Math.acos(fdotv / (lf * lv)) * 180 / Math.PI;

            // Bends can go any way, so we account for negative angles as well
            double crossz = f.x * v.y - f.y * v.x;
            crossz /= Math.abs(crossz);

            robots[i].totalAngle[gs.trackNr] += (crossz * angle);
            gl.glRotated(robots[i].totalAngle[gs.trackNr], 0, 0, 1);

            robots[i].totalAngle[1 - gs.trackNr] = 0;
            robots[i].direction[1 - gs.trackNr] = new Vector(0, 1, 0);

            robots[i].position = p;
            robots[i].direction[gs.trackNr] = v;

            robots[i].draw(gl, glu, glut, robots[i].t * robots[i].baseSpeed / 250);

            gl.glPopMatrix();
        }

        // Draw the race track.
        gl.glUseProgram(trackShader.getProgramID());
        raceTracks[gs.trackNr].draw(gl, glu, glut);

        // Draw the terrain.
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut);
        reportError("terrain:");


    }

    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {
        if (gs.showAxes) {
            drawArrow(2, 0, 0, 1, 0, 0);
            drawArrow(0, 2, 0, 0, 1, 0);
            drawArrow(0, 0, 2, 0, 0, 1);
            gl.glColor3f(1f,1f,0);
            glut.glutSolidSphere(0.2f,50,50);
        }
    }

    /**
     * Draws a single arrow
     */
    public void drawArrow(float x, float y, float z, float r, float g, float b) {
        gl.glLineWidth(2.5f);
        gl.glColor3f(r,g,b);
        gl.glBegin(gl.GL_LINES);
        gl.glVertex3f(0.0f,0.0f,0.0f);
        gl.glVertex3f(x,y,z);
        gl.glEnd();
        gl.glPushMatrix();
            gl.glTranslated(x,y,z);
            gl.glRotated(-90,y,-x,z);
            glut.glutSolidCone(0.2,0.5,50,50);
        gl.glPopMatrix();
    }

    /**
     * Drawing hierarchy example.
     *
     * This method draws an "arm" which can be animated using the sliders in the
     * RobotRace interface. The A and B sliders rotate the different joints of
     * the arm, while the C, D and E sliders set the R, G and B components of
     * the color of the arm respectively.
     *
     * The way that the "arm" is drawn (by calling {@link #drawSecond()}, which
     * in turn calls {@link #drawThird()} imposes the following hierarchy:
     *
     * {@link #drawHierarchy()} -> {@link #drawSecond()} -> {@link #drawThird()}
     */
    private void drawHierarchy() {
        gl.glColor3d(gs.sliderC, gs.sliderD, gs.sliderE);
        gl.glPushMatrix();
            gl.glScaled(1, 0.5, 0.5);
            glut.glutSolidCube(1);
            gl.glScaled(1, 2, 2);
            gl.glTranslated(0.5, 0, 0);
            gl.glRotated(gs.sliderA * -90.0, 0, 1, 0);
            drawSecond();
        gl.glPopMatrix();
    }

    private void drawSecond() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
        gl.glScaled(1, 2, 2);
        gl.glTranslated(0.5, 0, 0);
        gl.glRotated(gs.sliderB * -90.0, 0, 1, 0);
        drawThird();
    }

    private void drawThird() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
    }


    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}
