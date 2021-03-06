package ch.talionis.rbx.views;

import android.graphics.Path;

class BlockViewPathGenerator {
    private static final float halfStrokeWidthFactor = 0.05f;
    private static final float roundRectRadiusFactor = 0;


    static Path start(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float triangleHalf = width * 0.2f;
        float triangleHalfLarge = width * 0.24f;
        float halfStrokeWidth = halfStrokeWidthFactor * width;

        Path path = new Path();
        path.setLastPoint(0.5f * width, 0);
        path.lineTo(width - roundRectRadius, 0);
        path.arcTo(width - roundRectRadius, 0, width, roundRectRadius, 270, 90, false);
        path.lineTo(width, height * 0.5f - halfStrokeWidth);
        path.lineTo(0.5f * width + triangleHalf, height * 0.5f - halfStrokeWidth);

        path.lineTo(0.5f * width - triangleHalf, 0.5f*height - triangleHalfLarge);

        path.lineTo(0.5f * width - triangleHalf, 0.5f*height + triangleHalfLarge);
        path.lineTo(0.5f * width + triangleHalf, height * 0.5f + halfStrokeWidth);

        path.lineTo(width, height * 0.5f + halfStrokeWidth);
        path.lineTo(width, height - roundRectRadius);
        path.arcTo(width - roundRectRadius, height - roundRectRadius, width, height, 0, 90, false);
        path.lineTo(roundRectRadius, height);
        path.arcTo(0, height - roundRectRadius, roundRectRadius, height, 90, 90, false);
        path.lineTo(0, roundRectRadius);
        path.arcTo(0, 0, roundRectRadius, roundRectRadius, 180, 90, false);
        return path;
    }

    static Path end(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float centerCircleRadius = width * 0.2f;
        float halfStrokeWidth = halfStrokeWidthFactor * width;
        float correctiveDegrees = 14;

        Path path = new Path();
        path.setLastPoint(0.5f * width, 0);
        path.lineTo(width - roundRectRadius, 0);
        path.arcTo(width - roundRectRadius, 0, width, roundRectRadius, 270, 90, false);
        path.lineTo(width, height * 0.5f - halfStrokeWidth);
        path.lineTo(0.5f * width + centerCircleRadius, height * 0.5f - halfStrokeWidth);
        path.arcTo(0.5f * width - centerCircleRadius, height * 0.5f - centerCircleRadius, 0.5f * width + centerCircleRadius, height * 0.5f + centerCircleRadius, -correctiveDegrees, -360 + 2 * correctiveDegrees, false);
        path.lineTo(width, height * 0.5f + halfStrokeWidth);
        path.lineTo(width, height - roundRectRadius);
        path.arcTo(width - roundRectRadius, height - roundRectRadius, width, height, 0, 90, false);
        path.lineTo(roundRectRadius, height);
        path.arcTo(0, height - roundRectRadius, roundRectRadius, height, 90, 90, false);
        path.lineTo(0, roundRectRadius);
        path.arcTo(0, 0, roundRectRadius, roundRectRadius, 180, 90, false);
        return path;
    }

    static Path cornerLarge(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float halfStrokeWidth = halfStrokeWidthFactor * width;

        Path path = new Path();
        path.setLastPoint(0.5f * width, 0);
        path.lineTo(width - roundRectRadius, 0);
        path.arcTo(width - roundRectRadius, 0, width, roundRectRadius, 270, 90, false);
        path.lineTo(width, height - roundRectRadius);
        path.arcTo(width - roundRectRadius, height - roundRectRadius, width, height, 0, 90, false);

        path.lineTo(0.5f * width + halfStrokeWidth, height);
        path.lineTo(0.5f * width + halfStrokeWidth, 0.5f * height - halfStrokeWidth);
        path.lineTo(0, 0.5f * height - halfStrokeWidth);

        path.lineTo(0, roundRectRadius);
        path.arcTo(0, 0, roundRectRadius, roundRectRadius, 180, 90, false);
        return path;
    }

    static Path cornerSmall(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float halfStrokeWidth = halfStrokeWidthFactor * width;

        Path path = new Path();
        path.setLastPoint(0.5f * width - halfStrokeWidth, height);

        path.lineTo(0.5f * width - halfStrokeWidth, 0.5f * height + halfStrokeWidth);
        path.lineTo(0, 0.5f * height + halfStrokeWidth);
        path.lineTo(0, height - roundRectRadius);
        path.arcTo(0, height - roundRectRadius, roundRectRadius, height, 180, -90, false);

        path.lineTo(0.5f * width - halfStrokeWidth, height);
        return path;
    }

    static Path none(int width, int height) {
        Path path = new Path();
        path.setLastPoint(0.5f * width, 0);
        path.lineTo(width, 0);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, 0);
        return path;
    }

    static Path lineTop(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float halfStrokeWidth = halfStrokeWidthFactor * width;

        Path path = new Path();
        path.setLastPoint(0.5f * width, 0);
        path.lineTo(width - roundRectRadius, 0);
        path.arcTo(width - roundRectRadius, 0, width, roundRectRadius, 270, 90, false);
        path.lineTo(width, 0.5f * height - halfStrokeWidth);
        path.lineTo(0, 0.5f * height - halfStrokeWidth);
        path.lineTo(0, roundRectRadius);
        path.arcTo(0, 0, roundRectRadius, roundRectRadius, 180, 90, false);
        return path;
    }

    static Path lineBottom(int width, int height) {
        float roundRectRadius = width * roundRectRadiusFactor;
        float halfStrokeWidth = halfStrokeWidthFactor * width;

        Path path = new Path();
        path.setLastPoint(0,  0.5f * height + halfStrokeWidth);
        path.setLastPoint(width,  0.5f * height + halfStrokeWidth);
        path.lineTo(width, height - roundRectRadius);
        path.arcTo(width - roundRectRadius, height - roundRectRadius, width, height, 0, 90, false);
        path.lineTo(roundRectRadius, height);
        path.arcTo(0, height - roundRectRadius, roundRectRadius, height, 90, 90, false);
        path.lineTo(0,  0.5f * height + halfStrokeWidth);
        return path;
    }

    private BlockViewPathGenerator() {
    }
}
