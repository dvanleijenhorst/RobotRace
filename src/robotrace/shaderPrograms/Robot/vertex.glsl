// simple vertex shader
#version 120
uniform bool ambient, diffuse, specular;
varying vec3 N, P, lightP;

void main()
{
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_FrontColor = gl_Color;

    N = normalize(gl_NormalMatrix * gl_Normal);
	P = vec3((gl_ModelViewMatrix * gl_Vertex));
	lightP = vec3(gl_ModelViewMatrix * gl_LightSource[0].position);
}