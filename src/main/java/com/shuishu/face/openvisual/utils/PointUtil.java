package com.shuishu.face.openvisual.utils;


import com.shuishu.face.openvisual.core.domain.FaceInfo;
import org.opencv.core.Point;

public class PointUtil {

    /**
     * 转换点对象
     *
     * @param point
     * @return
     */
    public static FaceInfo.Point convert(Point point) {
        return FaceInfo.Point.build((float) point.x, (float) point.y);
    }

    /**
     * 转换点对象
     *
     * @param point
     * @return
     */
    public static Point convert(FaceInfo.Point point) {
        return new Point(point.x, point.y);
    }

    /**
     * 转换点对象
     *
     * @param points
     * @return
     */
    public static Point[] convert(FaceInfo.Points points) {
        Point[] result = new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            result[i] = convert(points.get(i));
        }
        return result;
    }

}
