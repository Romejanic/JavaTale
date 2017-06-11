#version 330 core

in vec2 uv;

uniform sampler2D sprite;
uniform vec4 tintColor;

out vec4 fragColor;

void main() {
	fragColor = texture(sprite, uv) * tintColor;
}