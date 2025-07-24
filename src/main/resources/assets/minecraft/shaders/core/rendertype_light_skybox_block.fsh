#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;
in vec3 blockPos;

uniform vec3 playerPos;

//uniform float posDiffX;
//uniform float posDiffY;

out vec4 fragColor;

void main() {
    float scale = 0.25; // Controls zoom

    // Compute shifted coordinate: zoomed texCoord + position offset
    vec2 shiftedCoord = (texCoord * scale) + (vec2(-(playerPos.x-blockPos.x), playerPos.y-blockPos.y) * scale);

    fragColor = texture(Sampler0, shiftedCoord);
}