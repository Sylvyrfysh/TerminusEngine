package com.sylvyrfysh.terminusengine.camera;

import org.lwjgl.util.vector.Vector3f;

public class CMathUtils {

    public static final float DEGTORAD = (float) (Math.PI / 180d);

    public static final Vector3f EAST = new Vector3f(1f, 0f, 0f);
    public static final Vector3f UP = new Vector3f(0f, 1f, 0f);
    public static final Vector3f NORTH = new Vector3f(0f, 0f, 1f);

    public static float cot(float angle) {
        return (float) (1f / Math.tan(angle));
    }

    public static float degreesToRadians(float degrees) {
        return degrees * DEGTORAD;
    }

    public static float randRange(float from, float to) {
        return ((float) Math.random() * (to - from)) + from;
    }

    public static int randRange(int from, int to) {
        return (int) (Math.random() * (to - from)) + from;
    }

    public static float randAngle() {
        return (float) (Math.random() * 2.0 * Math.PI);
    }

    public static Vector3f randomDirection() {
        double azimuth = Math.random() * 2.0 * Math.PI;
        float z = (float) ((Math.random() * 2.0) - 1.0);
        float f = (float) Math.sqrt(1.0 - z * z);
        return new Vector3f((float) Math.cos(azimuth) * f, (float) Math.sin(azimuth) * f, z);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector3f unitVector(double angle) {
        return new Vector3f((float) Math.cos(angle * DEGTORAD), 0f, (float) Math.sin(angle * DEGTORAD));
    }
    
}