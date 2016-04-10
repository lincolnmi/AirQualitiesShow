package jason.tongji.model;

/**
 * Created by Jason on 2016/4/10.
 */
public class CityAir {

    private String kouzhao_head;
    private String kouzhao_content;
    private String chuxing_head;
    private String chuxing_content;
    private String yundong_head;
    private String yundong_content;
    private String kaichuan_head;
    private String kaichuan_content;
    private String wuranLevel;
    private String background;
    private String quality;

    public CityAir(String quality) {
        setQuality(quality);
        setBackgroundByQuality(quality);
        setKouzhao(quality);
        setYundong(quality);
        setChuxing(quality);
        setKaichuan(quality);
        setWuranLevelByQuality(quality);
    }

    private void setWuranLevelByQuality(String quality) {
        if (quality.equals("优")) {
            setWuranLevel("wuranlevel_1");
        } else if (quality.equals("良")) {
            setWuranLevel("wuranlevel_2");
        } else if (quality.equals("轻度污染")) {
            setWuranLevel("wuranlevel_3");
        } else if (quality.equals("中度污染")) {
            setWuranLevel("wuranlevel_4");
        } else if (quality.equals("重度污染")) {
            setWuranLevel("wuranlevel_5");
        } else if (quality.equals("严重污染")) {
            setWuranLevel("wuranlevel_6");
        }
    }

    private void setKouzhao(String quality) {
        if (quality.equals("优")) {
            setKouzhao_head("无需口罩");
            setKouzhao_content("请大口的呼吸自然空气");
        } else if (quality.equals("良")) {
            setKouzhao_head("请佩戴");
            setKouzhao_content("出入密集场合请佩戴口罩");
        } else if (quality.equals("轻度污染")||quality.equals("中度污染")
                ||quality.equals("重度污染")||quality.equals("严重污染")) {
            setKouzhao_head("必须佩戴");
            setKouzhao_content("需佩戴N95以上口罩");
        }
    }

    private void setYundong(String quality) {
        if (quality.equals("优")) {
            setYundong_head("室外运动");
            setYundong_content("非常适合室外运动");
        } else if (quality.equals("良")) {
            setYundong_head("室内运动");
            setYundong_content("适当减少室外运动");
        } else if (quality.equals("轻度污染")||quality.equals("中度污染")) {
            setYundong_head("室内运动");
            setYundong_content("适合在室内作些柔和性运动");
        } else if (quality.equals("重度污染")||quality.equals("严重污染")) {
            setYundong_head("室内运动");
            setYundong_content("尽量不要留在室外");
        }
    }

    private void setKaichuan(String quality) {
        if (quality.equals("优")) {
            setKaichuan_head("开窗通风");
            setKaichuan_content("非常适合开窗通风");
        } else if (quality.equals("良")) {
            setKaichuan_head("开窗通风");
            setKaichuan_content("请减少开窗通风时间");
        } else if (quality.equals("轻度污染")||quality.equals("中度污染")) {
            setKaichuan_head("不适合开窗");
            setKaichuan_content("请下午三点后开窗通风");
        } else if (quality.equals("重度污染")||quality.equals("严重污染")) {
            setKaichuan_head("关窗");
            setKaichuan_content("请珍惜室内每一份空气，切勿开窗");
        }
    }


    private void setChuxing(String quality) {
        if (quality.equals("优")) {
            setChuxing_head("适合出行");
            setChuxing_content("非常适合出门呼吸新鲜空气");
        } else if (quality.equals("良")) {
            setChuxing_head("适合出行");
            setChuxing_content("空气清新，适合出行");
        } else if (quality.equals("轻度污染")||quality.equals("中度污染")) {
            setChuxing_head("不适合出行");
            setChuxing_content("应适当减少室外出行");
        } else if (quality.equals("重度污染")||quality.equals("严重污染")) {
            setChuxing_head("不适合出行");
            setChuxing_content("尽量减少外出");
        }
    }

    private void setBackgroundByQuality(String quality) {
        if (quality.equals("优")) {
            setWuranLevel("wuranlevel_1");
        } else if (quality.equals("良")) {
            setWuranLevel("wuranlevel_2");
        } else if (quality.equals("轻度污染")) {
            setWuranLevel("wuranlevel_3");
        } else if (quality.equals("中度污染")) {
            setWuranLevel("wuranlevel_4");
        } else if (quality.equals("重度污染")) {
            setWuranLevel("wuranlevel_5");
        } else if (quality.equals("严重污染")) {
            setWuranLevel("wuranlevel_6");
        }
    }

    public void setKouzhao_head(String kouzhao_head) {
        this.kouzhao_head = kouzhao_head;
    }

    public String getKouzhao_head() {
        return kouzhao_head;
    }

    public void setKouzhao_content(String kouzhao_content) {
        this.kouzhao_content = kouzhao_content;
    }

    public String getKouzhao_content() {
        return kouzhao_content;
    }

    public void setChuxing_head(String chuxing_head) {
        this.chuxing_head = chuxing_head;
    }

    public String getChuxing_head() {
        return chuxing_head;
    }

    public void setChuxing_content(String chuxing_content) {
        this.chuxing_content = chuxing_content;
    }

    public String getChuxing_content() {
        return chuxing_content;
    }

    public void setYundong_head(String yundong_head) {
        this.yundong_head = yundong_head;
    }

    public String getYundong_head() {
        return yundong_head;
    }

    public void setYundong_content(String yundong_content) {
        this.yundong_content = yundong_content;
    }

    public String getYundong_content() {
        return yundong_content;
    }

    public void setKaichuan_head(String kaichuan_head) {
        this.kaichuan_head = kaichuan_head;
    }

    public void setKaichuan_content(String kaichuan_content) {
        this.kaichuan_content = kaichuan_content;
    }

    public String getKaichuan_head() {
        return kaichuan_head;
    }

    public String getKaichuan_content() {
        return kaichuan_content;
    }

    public void setWuranLevel(String wuranLevel) {
        this.wuranLevel = wuranLevel;
    }

    public String getWuranLevel() {
        return wuranLevel;
    }

    public void setQuality(String quality) {
        if (quality==null||quality.equals("")){
            quality = "优";
        }
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

}



