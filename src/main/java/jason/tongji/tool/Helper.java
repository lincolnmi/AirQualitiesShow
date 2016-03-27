package jason.tongji.tool;

/**
 * Created by Jason on 2016/3/27.
 */
public class Helper {

    public static String getColorByQuality(String quality) {
        System.out.println(quality);
        if (quality.equals("优")) {
            return "#7db364";
        } else if (quality.equals("良")) {
            return "#e3b649";
        } else if (quality.equals("轻度污染")) {
            return "#e38748";
        } else if (quality.equals("中度污染")) {
            return "#e07373";
        } else if (quality.equals("重度污染")) {
            return "#9590c9";
        } else if (quality.equals("严重污染")) {
            return "#9f6aa2";
        } else {
            return "#7db364";
        }
    }

    public static String getTips(String quality) {
        if (quality.equals("优")) {
            return "空气很好，可以外出活动，呼吸新鲜空气。";
        } else if (quality.equals("良")) {
            return "可以正常在户外活动，易敏感人群应减少外出。";
        } else if (quality.equals("轻度污染")) {
            return "敏感人群症状易加剧，应避免高强度户外锻炼，外出时做好防护措施。";
        } else if (quality.equals("中度污染")) {
            return "应减少户外活动，外出时佩戴口罩，敏感人群应尽量避免外出。";
        } else if (quality.equals("重度污染")) {
            return "应减少户外活动，外出时佩戴口罩，敏感人群应留在室内。";
        } else if (quality.equals("严重污染")) {
            return "应减少户外活动，外出人员采取防护措施";
        } else {
            return "空气很好，可以外出活动，呼吸新鲜空气。";
        }
    }

}
