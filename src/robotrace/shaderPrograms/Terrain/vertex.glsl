#version 120

varying vec3 p;
varying float offset;

float offset_(vec3 p) {
    float x = p.x;
    float y = p.y;
    return 0.6 * cos(0.3 * x + 0.2 * y) + 0.4 * cos(x - 0.5 * y);
}

void main()
{
    p = gl_Vertex.xyz / gl_Vertex.w;
    offset = offset_(p);
    p = p + offset * vec3(0, 0, 1);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(p, 1);      // model view transform
    gl_FrontColor = gl_Color;
}