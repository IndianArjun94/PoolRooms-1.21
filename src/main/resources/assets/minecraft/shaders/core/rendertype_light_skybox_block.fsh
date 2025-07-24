#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;

uniform vec3 playerPos; // player position (x,y,z)

out vec4 fragColor;

void main() {
    vec2 playerOffset = vec2(playerPos.x, playerPos.z) * 0.01; // scale down movement

    vec2 shiftedUV = texCoord + playerOffset;

    fragColor = texture(Sampler0, shiftedUV);
}