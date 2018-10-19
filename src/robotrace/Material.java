package robotrace;

/**
* Materials that can be used for the robots.
*/
public enum Material {

    /** 
     * Gold material properties.
     * Modify the default values to make it look like gold.
     */
    GOLD (
            
        new float[] {237f / 255f, 195f / 255f, 21f / 255f, 1},
        new float[] {240f / 255, 211f / 255, 94f / 255, 1},
        1

    ),

    /**
     * Silver material properties.
     * Modify the default values to make it look like silver.
     */
    SILVER (
            
        new float[] {212f / 255, 212f / 255, 212f / 255, 1},
        new float[] {161f / 255, 161f / 255, 161f / 255, 1},
        1

    ),

    /** 
     * Orange material properties.
     * Modify the default values to make it look like orange.
     */
    ORANGE (
            
        new float[] {255f / 255, 77f / 255, 0, 1},
        new float[] {204f / 255, 61f / 255, 0, 1f},
        .0f

    ),

    /**
     * Wood material properties.
     * Modify the default values to make it look like Wood.
     */
    WOOD (

        new float[] {127f / 255, 61f / 255, 30f / 255, 1},
        new float[] {127f / 255, 87f / 255, 68f / 255, 1f},
        0

    );

    /** The diffuse RGBA reflectance of the material. */
    float[] diffuse;

    /** The specular RGBA reflectance of the material. */
    float[] specular;
    
    /** The specular exponent of the material. */
    float shininess;

    /**
     * Constructs a new material with diffuse and specular properties.
     */
    private Material(float[] diffuse, float[] specular, float shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
}
