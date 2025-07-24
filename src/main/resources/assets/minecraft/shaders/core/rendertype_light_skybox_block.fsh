#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;

uniform vec3 playerPos;
uniform vec3 blockPos;

uniform float posDiffX;
uniform float posDiffY;

out vec4 fragColor;

void main() {
    float scale = 0.25; // Controls zoom

    // Compute shifted coordinate: zoomed texCoord + position offset
    vec2 shiftedCoord = (texCoord * scale) + (vec2(posDiffX, posDiffY) * scale);

    fragColor = texture(Sampler0, shiftedCoord);
}