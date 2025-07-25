#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;
in vec3 blockPos;

uniform vec3 playerPos;

out vec4 fragColor;

void main() {
    float zoom = 0.1; // Controls zoom
    float speedMultipler = 0.01;

    vec2 blockBasePos = floor(blockPos.xy);
    vec2 localPos = blockPos.xy - blockBasePos; // This is identical to texCoord, but ensures precision
    vec2 worldAlignedPos = blockBasePos + localPos;

    // Compute shifted coordinate: zoomed texCoord + position offset
//    vec2 shiftedCoord = fract((texCoord * scale) + (vec2(-(playerPos.x-blockPos.x), playerPos.y-blockPos.y) * scale));

    vec2 shiftedCoord = fract((worldAlignedPos - playerPos.xy * speedMultipler) * zoom);

    shiftedCoord.y = 1.0 - shiftedCoord.y;

    fragColor = texture(Sampler0, shiftedCoord);
}