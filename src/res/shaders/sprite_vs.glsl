#version 330 core

layout(location = 0) in vec3 vertex;

out float factor;

void main() {
	gl_Position = vec4(vertex, 1.);
	
	vec2 pos = (gl_Position.xy*.5+.5);
	factor = pos.x * pos.y;
}