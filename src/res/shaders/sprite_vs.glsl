#version 330 core

layout(location = 0) in vec2 vertex;
layout(location = 1) in vec2 texCoords;

uniform mat4 projMat;
uniform mat4 modelMat;

out vec2 uv;

void main() {
	gl_Position = projMat * modelMat * vec4(vertex, 0., 1.);
	uv = texCoords;
}