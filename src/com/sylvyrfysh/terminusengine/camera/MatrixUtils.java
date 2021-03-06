package com.sylvyrfysh.terminusengine.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public final class MatrixUtils {

    private static final Vector3f forward = new Vector3f();
    private static final Vector3f side = new Vector3f();
    private static final Vector3f up = new Vector3f();
    private static final Vector3f eye = new Vector3f();

    public static Matrix4f frustum(float width, float height, float fieldOfView, float near, float far) {
        Matrix4f frustum = new Matrix4f();
        float aspectRatio = width / height;

        float yScale = CMathUtils.cot(CMathUtils.degreesToRadians(fieldOfView / 2f));
        float xScale = yScale / aspectRatio;
        float frustumLength = far - near;
        
        System.out.println(xScale);
        System.out.println(yScale);
        

        frustum.m00 = xScale;
        frustum.m11 = yScale;
        frustum.m22 = ((far + near) / frustumLength);
        frustum.m23 = 1f;
        frustum.m32 = -((2f * near * far) / frustumLength);

        return frustum;
    }

    public static Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        forward.set(centerX - eyeX, centerY - eyeY, centerZ - eyeZ);
        forward.normalise();

        up.set(upX, upY, upZ);

        Vector3f.cross(forward, up, side);
        side.normalise();

        Vector3f.cross(side, forward, up);
        up.normalise();

        Matrix4f matrix = new Matrix4f();
        matrix.m00 = side.x;
        matrix.m10 = side.y;
        matrix.m20 = side.z;

        matrix.m01 = up.x;
        matrix.m11 = up.y;
        matrix.m21 = up.z;

        matrix.m02 = -forward.x;
        matrix.m12 = -forward.y;
        matrix.m22 = -forward.z;

        eye.set(-eyeX, -eyeY, -eyeZ);
        matrix.translate(eye);

        return matrix;
    }
}