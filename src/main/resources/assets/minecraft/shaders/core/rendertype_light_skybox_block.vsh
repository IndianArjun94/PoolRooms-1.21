#version 150

//layout(location = 0) in vec3 aPosition; // Vertex position
//layout(location = 1) in vec2 aTexCoord; // Texture coordinates
//layout(location = 2) in vec3 blockPos; // Unique position for this block

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;

out vec2 texCoord;
out vec3 blockPos;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    texCoord = UV0; // Pass directly

    blockPos = Position + ChunkOffset;
}