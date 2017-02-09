package cn.xiandu.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yanxm on 2017/2/8.
 */
@Entity //用于标识这是一个需要Greendao帮我们生成代码的bean
public class HomeBean implements Parcelable {


    /**
     * id : wechat_20150401071581
     * title : 号外：集宁到乌兰花的班车出事了！！！！！
     * source : 内蒙那点事儿
     * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-214279.jpg/168
     * mark :
     * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20150401071581
     */
    @Id //添加了 @Id 注解，这个就是主键了
    private String id;
    private String title;
    private String source;
    private String firstImg;
    private String mark;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.source);
        dest.writeString(this.firstImg);
        dest.writeString(this.mark);
        dest.writeString(this.url);
    }

    public HomeBean() {
    }

    protected HomeBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.source = in.readString();
        this.firstImg = in.readString();
        this.mark = in.readString();
        this.url = in.readString();
    }

    @Generated(hash = 2010710034)
    public HomeBean(String id, String title, String source, String firstImg, String mark, String url) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.firstImg = firstImg;
        this.mark = mark;
        this.url = url;
    }

    public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel source) {
            return new HomeBean(source);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };
}
