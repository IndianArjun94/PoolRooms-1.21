#version 150

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord;

uniform float uvScale; // e.g. 0.1 to make texture 10x bigger

uniform vec3 playerPos; // player world position (x, y, z)


void main() {
    vec4 worldPos = ModelViewMat * vec4(Position, 1.0);

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    texCoord = (UV0 + worldPos.xz - playerPos.xz) * uvScale;
}