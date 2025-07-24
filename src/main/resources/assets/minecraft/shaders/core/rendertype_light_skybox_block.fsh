#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;
uniform vec3 playerPos;
out vec4 fragColor;

void main() {
    fragColor = texture(Sampler0, texCoord*0.5);
}