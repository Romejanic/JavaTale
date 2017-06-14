#version 330 core

in vec2 uv;

uniform sampler2D sprite;
uniform vec4 tintColor;
uniform int useTexture;

out vec4 fragColor;

void main() {
	if(useTexture <= 0) {
		fragColor = tintColor;
	} else {
		fragColor = texture(sprite, uv) * tintColor;
	}
}