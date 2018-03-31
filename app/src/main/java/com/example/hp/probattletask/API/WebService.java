package com.example.hp.probattletask.API;

import com.example.hp.probattletask.Wrappers.CryptoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by hp on 3/31/2018.
 */

public interface WebService {
    @GET("v1/ticker")
    Call<List<CryptoList>> getAllCrypto();

}
