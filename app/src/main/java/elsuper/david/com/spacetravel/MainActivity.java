package elsuper.david.com.spacetravel;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsuper.david.com.spacetravel.data.ApodService;
import elsuper.david.com.spacetravel.data.Data;
import elsuper.david.com.spacetravel.model.APOD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    /*private ImageView imageView;
    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvExplanation;
    private TextView tvCopyright;*/
    @BindView(R.id.apodImg) ImageView imageView;//2016-08-05
    @BindView(R.id.tvDate) TextView tvDate;//2016-08-05
    @BindView(R.id.tvTitle) TextView tvTitle;//2016-08-05
    @BindView(R.id.tvExplanation) TextView tvExplanation;//2016-08-05
    @BindView(R.id.tvCopyright) TextView tvCopyright;//2016-08-05
    @BindView(R.id.main_btnGoList) Button btnGoToList;//2016-08-05

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("BuildConfig", BuildConfig.URL);
        //Acceedemos a los controles
        /*imageView = (ImageView)findViewById(R.id.apodImg);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvExplanation = (TextView)findViewById(R.id.tvExplanation);
        tvCopyright = (TextView)findViewById(R.id.tvCopyright);*/
        ButterKnife.bind(this);//2016-08-05


        //Utilizando Retrofit
        ApodService apodService = Data.getRetrofitInstance().create(ApodService.class);

        //Call<APOD> callApodService = apodService.getTodayApod();
        Call<APOD> callApodService = apodService.getTodayApodWithQuery("J0U8OnXkzemf1OF32OotEIYYrdOfWyUsdGKnxjaj");

        callApodService.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                Log.d("APOD", response.body().getTitle());

                //Asignando valores
                Picasso.with(getApplicationContext()).load(response.body().getHdurl()).into(imageView);
                tvDate.setText(response.body().getDate());
                tvTitle.setText(response.body().getTitle());
                tvExplanation.setText(response.body().getExplanation());
                tvCopyright.setText("Copyright: " + response.body().getCopyright());
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {

            }
        });

        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
            }
        });
    }
}
