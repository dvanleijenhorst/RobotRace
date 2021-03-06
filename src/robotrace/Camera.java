package robotrace;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(6f, 6f, 6f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        eye.x = gs.cnt.x + gs.vDist * Math.cos(gs.phi) * Math.cos(-gs.theta);
        eye.y = gs.cnt.y + gs.vDist * Math.cos(gs.phi) * Math.sin(-gs.theta);
        eye.z = gs.cnt.z + gs.vDist * Math.sin(gs.phi);
        center = Vector.O;
        up = Vector.Z;
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        eye.x = focus.position.x;
        eye.y = focus.position.y;
        eye.z = focus.position.z + 2;
        center = new Vector(eye.x + focus.direction[gs.trackNr].x,
                            eye.y + focus.direction[gs.trackNr].y,
                            eye.z);
        up = Vector.Z;
    }
}
