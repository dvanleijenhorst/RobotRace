#version 120

uniform sampler2D tex;

varying vec3 P;
varying vec3 N;

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
    vec4 result = vec4(0.0,0.0,0.0,1.0);

    result += 0.3 * mat.ambient * light.ambient;

    vec3 L = normalize(light.position.xyz - P);
    result += mat.diffuse * light.diffuse * max(dot(N,L),0);

    vec3 E = normalize(-P);
    vec3 V = normalize(2*dot(N,L)*N - L);
    result += mat.specular * light.specular * pow(max(dot(V,E),0),0.3 * mat.shininess);

    return result;
}

void main()
{
    gl_LightSourceParameters light = gl_LightSource[0];
    gl_MaterialParameters mat = gl_FrontMaterial;

    gl_FragColor = texture2D(tex, gl_TexCoord[0].st);
}
