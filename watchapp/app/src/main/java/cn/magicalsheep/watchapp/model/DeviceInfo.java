package cn.magicalsheep.watchapp.model;

public class DeviceInfo {
    private String id;
    private float xginfo;
    private float yginfo;
    private float zginfo;
    private float xainfo;
    private float yainfo;
    private float zainfo;
    private float xsinfo;
    private float ysinfo;
    private float zsinfo;

    public DeviceInfo(String id, float xginfo, float yginfo, float zginfo, float xainfo, float yainfo, float zainfo, float xsinfo, float ysinfo, float zsinfo) {
        this.id = id;
        this.xginfo = xginfo;
        this.yginfo = yginfo;
        this.zginfo = zginfo;
        this.xainfo = xainfo;
        this.yainfo = yainfo;
        this.zainfo = zainfo;
        this.xsinfo = xsinfo;
        this.ysinfo = ysinfo;
        this.zsinfo = zsinfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getXainfo() {
        return xainfo;
    }

    public float getXginfo() {
        return xginfo;
    }

    public float getXsinfo() {
        return xsinfo;
    }

    public float getYainfo() {
        return yainfo;
    }

    public float getYginfo() {
        return yginfo;
    }

    public float getYsinfo() {
        return ysinfo;
    }

    public float getZainfo() {
        return zainfo;
    }

    public float getZginfo() {
        return zginfo;
    }

    public float getZsinfo() {
        return zsinfo;
    }

    public void setXainfo(float xainfo) {
        this.xainfo = xainfo;
    }

    public void setXginfo(float xginfo) {
        this.xginfo = xginfo;
    }

    public void setXsinfo(float xsinfo) {
        this.xsinfo = xsinfo;
    }

    public void setYainfo(float yainfo) {
        this.yainfo = yainfo;
    }

    public void setYginfo(float yginfo) {
        this.yginfo = yginfo;
    }

    public void setYsinfo(float ysinfo) {
        this.ysinfo = ysinfo;
    }

    public void setZainfo(float zainfo) {
        this.zainfo = zainfo;
    }

    public void setZginfo(float zginfo) {
        this.zginfo = zginfo;
    }

    public void setZsinfo(float zsinfo) {
        this.zsinfo = zsinfo;
    }
}
