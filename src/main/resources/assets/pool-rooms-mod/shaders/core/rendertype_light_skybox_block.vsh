#version 150

#moj_import <projection.glsl>

in vec3 Position;

uniform mat4 ModelMat;           // block model transform
uniform mat4 ProjMat;            // camera projection
uniform mat4 ViewTranslationMat; // camera translation only, no rotation

out vec4 texProj0;

void main() {
    // Transform position by model matrix
    vec4 worldPos = ModelMat * vec4(Position, 1.0);

    // Apply only camera translation (no rotation)
    vec4 viewPos = ViewTranslationMat * worldPos;

    // Apply projection
    gl_Position = ProjMat * viewPos;

    texProj0 = projection_from_position(gl_Position);
}