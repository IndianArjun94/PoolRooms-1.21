#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;

uniform vec3 playerPos;
uniform vec3 blockPos;

out vec4 fragColor;

void main() {
    float scale = 0.1;

    float shiftAmountX = playerPos.x - blockPos.x;
    float shiftAmountY = playerPos.y - blockPos.y;

    vec2 shiftedTexCoord = vec2(fract(texCoord.x - shiftAmountX), fract(texCoord.y - shiftAmountY));

    fragColor = texture(Sampler0, shiftedTexCoord);
}