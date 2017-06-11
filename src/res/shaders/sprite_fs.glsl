#version 330 core

in float factor;

out vec4 fragColor;

void main() {
	fragColor = vec4(1.,.2,.2,1.);
	fragColor.xyz *= factor;
}