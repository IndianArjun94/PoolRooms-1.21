#version 150

#moj_import <projection.glsl>
#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;
uniform int FogShape;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;

out vec4 lightMapColor;
out vec4 texProj0;
out vec4 glPos;

// Add this uniform - Minecraft provides this directly
uniform vec3 CameraPosition; // This is the player's eye position in world coordinates

// Add this uniform for the texture projection matrix (no camera rotation)
uniform mat4 TextureProjectionMatrix;

void main() {
    vec3 pos = Position + ChunkOffset;
    vec4 viewPosition = ModelViewMat * vec4(pos, 1.0);
    gl_Position = ProjMat * viewPosition;

    // Correct fog_distance usage
    vertexDistance = fog_distance(viewPosition.xyz, FogShape);

    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    glPos = gl_Position;
    texProj0 = TextureProjectionMatrix * vec4(pos - CameraPosition, 1.0);

    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
