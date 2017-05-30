package com.jjf.youtube;

/**
 * Created by jjf_lenovo on 2017/5/15.
 */
public class FmtStreamMap {
    public String fallbackHost;
    /**
     * ��Ƶǩ��
     */
    public String s;
    public String itag;
    public String type;
    /**
     *
     */
    public String quality;
    /**
     * ԭʼ��ַ[����������������Ϊ��ʵ���ص�ַ]
     */
    public String url;
    /**
     * ����ǩ��
     */
    public String sig;
    /**
     * ��Ƶ����
     */
    public String title;

    public String mediatype;

    public boolean encrypted;
    /**
     * ��Ƶ����[mp4,3gp]
     */
    public String extension;
    /**
     * �ֱ��������Ϣ
     */
    public Resolution resolution;
    /**
     * ����Ƶ��Ӧ��JS��������
     */
    public String html5playerJS;
    /**
     * ��ƵID
     */
    public CharSequence videoid;
    /**
     * ��Ƶ���ص�ַ
     */
    public String realUrl;

    @Override
    public String toString() {
        return "FmtStreamMap [fallbackHost=" + fallbackHost + ", s=" + s + ", itag=" + itag + ", type=" + type + ", quality=" + quality
                + ", url=" + url + ", sig=" + sig + ", title=" + title + ", mediatype=" + mediatype + ", encrypted=" + encrypted
                + ", extension=" + extension + ", resolution=" + resolution + ", html5playerJS=" + html5playerJS + ", videoid=" + videoid
                + "]";
    }

    public String getStreamString() {
        if (resolution != null) {
            return String.format("%s (%s)", extension, resolution.resolution);
        } else {
            return null;
        }
    }
}