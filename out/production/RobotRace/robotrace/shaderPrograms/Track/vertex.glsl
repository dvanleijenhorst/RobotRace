#version 120

varying vec3 P;
varying vec3 N;

void main()
{
    N = normalize(gl_NormalMatrix * gl_Normal);
    P = vec3(gl_ModelViewMatrix * gl_Vertex);

    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}