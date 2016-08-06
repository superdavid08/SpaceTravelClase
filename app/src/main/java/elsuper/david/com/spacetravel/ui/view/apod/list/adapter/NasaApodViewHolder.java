package elsuper.david.com.spacetravel.ui.view.apod.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import elsuper.david.com.spacetravel.R;
import elsuper.david.com.spacetravel.model.Photo;

/**
 * Created by Alumno on 05/08/2016.
 */
public class NasaApodViewHolder extends RecyclerView.ViewHolder{

    //Para manejar el click en la foto
    private NasaApodAdapter.OnItemClickListener onItemListener;
    private Photo photo;

    //@BindView(R.id.item_apodImage)
    //ImageView itemApodImage;
    @BindView(R.id.item_apodImage)
    SimpleDraweeView itemApodImage;
    @BindView(R.id.item_apodTitle)
    TextView itemApodTitle;

    public NasaApodViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    //Para manejar el click en la foto
    public void setItemClick(Photo photo, NasaApodAdapter.OnItemClickListener onItemListener){
        this.photo = photo;
        this.onItemListener = onItemListener;
    }

    //Para manejar el click en la foto
    @OnClick(R.id.item_apodImage)
    public void onViewClick(View view){
        if(onItemListener != null)
            onItemListener.onItemClick(photo);
    }

}
