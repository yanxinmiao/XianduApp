package cn.xiandu.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.xiandu.app.activity.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dell on 2016/5/28.
 * <p/>
 * 加载图片的工具列
 */
public class ImageUtils {

    /**
     * 使用Glide 加载网络图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)//如果this 是activity，当activity销毁时，Glide 将会自动停止请求。如果target 是独立于应用的 activity 生命周期，这时就要传application
                .load(url)//source 可以是网络、资源文件、SD中图片文件和Uri,可以加载gif格式)
//                .thumbnail(0.1f)//先加载缩略图，在加载全图
//                .transform()//转换 模糊
//                .asGif()
//                .override(200,200)//重新设置图片大小
                .bitmapTransform(new BlurTransformation(context))
                .dontAnimate()
                .placeholder(R.drawable.default_news_cat_pic)//占位符
                .error(R.drawable.default_news_cat_pic)//错误占位符,error()接受的参数只能是已经初始化的 drawable 对象或者指明它的资源(R.drawable.<drawable-keyword>)。
                .crossFade()//淡入淡出动画,int duration 可以设置动画时间
                .centerCrop()
//                .animate( android.R.anim.slide_in_left ) // or R.anim.zoom_in//使用自定义动画
//                .dontAnimate()//不使用动画
//                .skipMemoryCache(true)//不保存到内存
//                .listener(requestListener)

                /**
                 *  DiskCacheStrategy.NONE 什么都不缓存
                 DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
                 DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
                 DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                 */
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不保存到硬盘
//                .into(simpleTarget);//使用此方法，需要再调用asBitmap(),因为加载的资源可能是gif
                .into(imageView);//传ImageView 作为参数给 .into()，Glide 将会用 ImageView 的大小去限制图像的大小
    }
    public static void loadImage(Context context, String url, ImageView imageView ,int defaultIcon){
        Glide.with(context)//如果this 是activity，当activity销毁时，Glide 将会自动停止请求。如果target 是独立于应用的 activity 生命周期，这时就要传application
                .load(url)//source 可以是网络、资源文件、SD中图片文件和Uri,可以加载gif格式)
//                .thumbnail(0.1f)//先加载缩略图，在加载全图
//                .transform(new BlurTransformation(this))//转换 模糊
//                .asGif()
//                .override(200,200)//重新设置图片大小
                .placeholder(defaultIcon)//占位符
                .error(defaultIcon)//错误占位符,error()接受的参数只能是已经初始化的 drawable 对象或者指明它的资源(R.drawable.<drawable-keyword>)。
                .crossFade()//淡入淡出动画,int duration 可以设置动画时间
                .centerCrop()
//                .animate( android.R.anim.slide_in_left ) // or R.anim.zoom_in//使用自定义动画
//                .dontAnimate()//不使用动画
//                .skipMemoryCache(true)//不保存到内存
//                .listener(requestListener)

                /**
                 *  DiskCacheStrategy.NONE 什么都不缓存
                 DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
                 DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
                 DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                 */
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不保存到硬盘
//                .into(simpleTarget);//使用此方法，需要再调用asBitmap(),因为加载的资源可能是gif
                .into(imageView);//传ImageView 作为参数给 .into()，Glide 将会用 ImageView 的大小去限制图像的大小
    }
}