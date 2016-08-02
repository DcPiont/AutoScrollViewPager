package POJO;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 用来存储ImageView和每个ImageVIew的信息的JavaBean
 * Created by DcPiont on 2016/7/28.
 */

public class ImageViewShow {
    private Context context;
    private ImageView imageView;
    private String message;

    public ImageViewShow(Context context){
        this.context = context;
        setImageView(new ImageView(context));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void OnClickListener(){
        getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //每张图片的点击事件
                Toast.makeText(context,getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
