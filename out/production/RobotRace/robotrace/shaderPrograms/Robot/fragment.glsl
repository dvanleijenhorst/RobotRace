// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 N, P, lightP;

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
    vec4 result  = vec4(0,0,0,1);        // opaque black

    result += mat.ambient * light.ambient;

    vec3 L = normalize(lightP - P);                    // vector towards light source
    result +=  mat.diffuse * light.diffuse * dot(N, L);

    vec3 V = normalize(-P);                    // direction towards viewer
	vec3 R = normalize(-reflect(L, N));
    result += mat.specular * light.specular * pow(dot(R, V), mat.shininess);

    return result;
}

void main()
{
    gl_LightSourceParameters light = gl_LightSource[0];
    gl_MaterialParameters mat = gl_FrontMaterial;
    gl_FragColor = shading(P, N, light, mat);   // function from Phong shader
}
