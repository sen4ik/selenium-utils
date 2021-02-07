package org.sen4ik.utils.selenium.enums;

public enum MobileScreenSizes {

    _1024x1366_iPadPro(1024, 1366),
    _768x1024_iPad(768, 1024),
    _375x812_iPhoneX(375, 812),
    _411x731_pixel2(411, 731),
    _360x640_motoG4(360, 640);

    public final int width;
    public final int height;

    MobileScreenSizes(int width, int height) {
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
