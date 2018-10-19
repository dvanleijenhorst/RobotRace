#version 120

varying vec3 p;
varying float offset;

void main()
{
    gl_FragColor = gl_Color;
    if (offset > 0.5) {
        gl_FragColor = vec4(0.27, 1, 0.377, 1);
    } else if (offset <= 0.5 && offset >= 0) {
        gl_FragColor = vec4(1, 1, 0.27, 1);
    } else {
        gl_FragColor = vec4(0.27, 0.3648, 1, 1);
    }
}
