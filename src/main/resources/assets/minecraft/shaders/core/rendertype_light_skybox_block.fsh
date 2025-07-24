#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;

uniform vec3 playerPos;
uniform vec3 blockPos;

uniform float posDiffX;
uniform float posDiffY;

out vec4 fragColor;

void main() {
    float scale = 0.25;

    vec2 zoomedTexCoord = (texCoord - 0.5) * scale + 0.5;

//    float shiftAmountX = playerPos.x - blockPos.x;
//    float shiftAmountY = playerPos.y - blockPos.y;

//    vec2 shiftedTexCoord = vec2(fract(zoomedTexCoord.x - shiftAmountX), fract(zoomedTexCoord.y - shiftAmountY));
    vec2 shiftedTexCoord = vec2(zoomedTexCoord.x - posDiffX*scale, zoomedTexCoord.y - posDiffY*scale);

    fragColor = texture(Sampler0, shiftedTexCoord);
}