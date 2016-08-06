package elsuper.david.com.spacetravel.data;

import elsuper.david.com.spacetravel.model.APOD;
import elsuper.david.com.spacetravel.model.MarsRovertResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alumno on 30/07/2016.
 */
public interface ApodService {

    @GET("planetary/apod?api_key=J0U8OnXkzemf1OF32OotEIYYrdOfWyUsdGKnxjaj")
    Call<APOD> getTodayApod();

    @GET("planetary/apod")
    Call<APOD> getTodayApodWithQuery(@Query("api_key") String apiKey);

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    Call<MarsRovertResponse> getTodayMarsRovertWithQuery(@Query("sol") int sol, @Query("api_key") String apiKey);
}
