#version 150

// Removed: #moj_import <matrix.glsl> - We'll implement rotation directly

uniform sampler2D Sampler0; // Only one sampler now

uniform float GameTime;
uniform int EndPortalLayers; // Still exists, but its direct use in a loop is simplified
uniform float PlayerYaw;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
    vec3(0.022087, 0.098399, 0.110818),
    vec3(0.011892, 0.095924, 0.089485),
    vec3(0.027636, 0.101689, 0.100326),
    vec3(0.046564, 0.109883, 0.114838),
    vec3(0.064901, 0.117696, 0.097189),
    vec3(0.063761, 0.086895, 0.123646),
    vec3(0.084817, 0.111994, 0.166380),
    vec3(0.097489, 0.154120, 0.091064),
    vec3(0.106152, 0.131144, 0.195191),
    vec3(0.097721, 0.110188, 0.187229),
    vec3(0.133516, 0.138278, 0.148582),
    vec3(0.070006, 0.243332, 0.235792),
    vec3(0.196766, 0.142899, 0.214696),
    vec3(0.047281, 0.315338, 0.321970),
    vec3(0.204675, 0.390010, 0.302066),
    vec3(0.080955, 0.314821, 0.661491)
);

const mat4 SCALE_TRANSLATE = mat4(
    0.5, 0.0, 0.0, 0.25,
    0.0, 0.5, 0.0, 0.25,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

// Custom 2x2 rotation matrix function (replaces mat2_rotate_z from matrix.glsl)
mat2 custom_mat2_rotate(float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return mat2(
        c,  s, // Column 0
       -s,  c  // Column 1
    );
}

mat4 end_portal_transform(float layer) {
    // Original translation logic
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 17.0 / layer,
        0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    // Combine GameTime rotation with PlayerYaw rotation
    // PlayerYaw is already in radians from Java, GameTime is also an angle.
    // We can multiply GameTime to control its speed.
    float combinedAngle = (GameTime * 0.5) + PlayerYaw; // Adjust 0.5 for GameTime rotation speed

    // Use our custom rotation function
    mat2 rotate = custom_mat2_rotate(combinedAngle);

    // Original scaling logic
    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    // Combine scale, rotate, translate, and the base SCALE_TRANSLATE
    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    // Initialize color with the base layer, tinted by the first color.
    // We start with the first color from the array for the base.
    vec3 color = textureProj(Sampler0, texProj0).rgb * COLORS[0];

    // Loop through the additional "layers" to create the swirling, multi-colored effect.
    // Each iteration uses the same Sampler0 but applies a different transformation
    // and a different color tint.
    for (int i = 0; i < EndPortalLayers; i++) {
        // Calculate the transformation for the current "layer"
        // Use float(i + 1) to ensure 'layer' is never zero and varies for each iteration.
        vec4 transformedTexCoord = texProj0 * end_portal_transform(float(i + 1));

        // Sample Sampler0 with the transformed coordinates for this "layer"
        vec3 layerColor = textureProj(Sampler0, transformedTexCoord).rgb;

        // Tint this "layer" with a color from the COLORS array
        layerColor *= COLORS[i];

        // Additively blend this layer's color to the total color
        color += layerColor;
    }

    // Set the final fragment color with full opacity
    fragColor = vec4(color, 1.0);
}
