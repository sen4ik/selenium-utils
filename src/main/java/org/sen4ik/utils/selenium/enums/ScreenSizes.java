package org.sen4ik.utils.selenium.enums;

public enum ScreenSizes {

    _1024x768(1024, 768),
    _1280x1024(1280, 1024),
    _1600x900(1600, 900),
    _1680x1050(1680, 1050),
    _1920x1080(1920, 1080),
    _1920x1200(1920, 1200),
    _1920x1440(1920, 1440);

    public final int width;
    public final int height;

    ScreenSizes(int width, int height) {
        this.width = width;
        this.height = height;
    }

    final int width() {
        return width;
    }

    final int height() {
        return height;
    }
}
