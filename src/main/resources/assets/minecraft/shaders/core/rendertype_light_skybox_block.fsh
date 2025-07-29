#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;
in vec3 blockPos;

uniform vec3 playerPos;

out vec4 fragColor;

void main() {
//    float zoom = abs(max(min(0.1 * abs(playerPos.z-blockPos.z), 1), 0.00001)); // Controls zoom
    float zDiff = ((playerPos.z-blockPos.z));
//    float zDiff = 0;

    float finalZDiff = abs(zDiff);
    float zoom = 0.1 / (1.0 + 0.15 * finalZDiff); // smoother than exp
//    float zoom = 1 / (1.0 * finalZDiff); // smoother than exp
    float speedMultipler = 0.01;

    vec2 blockBasePos = floor(blockPos.xy);
    vec2 localPos = blockPos.xy - blockBasePos; // This is identical to texCoord, but ensures precision
    vec2 worldAlignedPos = blockBasePos + localPos;

    // Compute shifted coordinate: zoomed texCoord + position offset
    vec2 shiftedCoord = (worldAlignedPos - playerPos.xy * speedMultipler);

    vec2 zoomedCoord = (shiftedCoord - 0.5) * zoom + 0.5;

    zoomedCoord.y = zoomedCoord.y + 0.02;

    zoomedCoord.y = 1.0 - zoomedCoord.y;

    fragColor = texture(Sampler0, zoomedCoord);

//    if (zDiff+zOffset > 0) {
//        fragColor = texture(Sampler0, zoomedCoord);
//    } else {
//        fragColor = vec4(0,0.5,1,0);
//    }
}
