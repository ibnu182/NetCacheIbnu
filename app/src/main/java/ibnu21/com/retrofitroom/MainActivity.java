package ibnu21.com.retrofitroom;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ibnu21.com.netcache.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public static final String DATABASE_DATA = "dataresponse.db";

    DataDatabase database;

    List<DataResponse> datas = new ArrayList<>();
    List<DataProjectEntity> datasEntities = new ArrayList<>();
    List<DataProjectEntity> results = new ArrayList<>();
    TitleAdapter adapter;

    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.progres_bar)
    ProgressBar progresBar;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDatabase();

        initRecyclerView();

        if (!isNetworkConnected()){
            progresBar.setVisibility(View.GONE);
            if (database.getDaoAccess().allDatas() == null || database.getDaoAccess().allDatas().size() == 0) {
                Log.d(TAG, "run: No Internet");
            }else{

                for (DataProjectEntity item: database.getDaoAccess().allDatas()){
                    datas.add(new DataResponse(item.getUserId(), item.getId(), item.getTitle(), item.getBody()));
                }
                adapter.notifyDataSetChanged();

            }

            Log.d(TAG, "onCreate:"+datas);

            final Snackbar snackbar = Snackbar.make(rootLayout, "Tidak ada koneksi internet, periksa kembali koneksi anda", Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isNetworkConnected()){

                    }else{
                        snackbar.dismiss();
                        fetchData();
                    }

                }

            });

            snackbar.show();

        }else{

            fetchData();

        }

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result  = String.valueOf(charSequence).toLowerCase();
                ArrayList<DataResponse>newlist=new ArrayList<>();
                for(DataResponse name:datas)
                {
                    String getName=name.getTitle().toLowerCase();
                    if(getName.contains(result)){
                        newlist.add(name);

                    }
                }
                adapter.filter(newlist);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), DataDatabase.class, DATABASE_DATA)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(linearLayoutManager);
        adapter = new TitleAdapter(datas);
        rvData.setAdapter(adapter);
    }

    private void fetchData() {
        progresBar.setVisibility(View.VISIBLE);
        Api apiStrore = ServiceGenerator.getRetrofit().create(Api.class);
        Call<List<DataResponse>> call = apiStrore.getData();
        call.enqueue(new Callback<List<DataResponse>>() {
            @Override
            public void onResponse(Call<List<DataResponse>> call, final Response<List<DataResponse>> response) {
                progresBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    datas.clear();

                    Collections.sort(response.body(), new Comparator<DataResponse>() {
                        @Override
                        public int compare(DataResponse dataResponse, DataResponse toa) {
                            return dataResponse.title.compareTo(toa.title);
                        }
                    });

                    datas.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    for (DataResponse item: datas){
                        datasEntities.add(new DataProjectEntity(item.id, item.userId, item.title, item.body));
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if (database.getDaoAccess().allDatas() == null || database.getDaoAccess().allDatas().size() == 0) {
                                database.getDaoAccess().insertAllDatas(datasEntities);
                            }else{
                                database.getDaoAccess().deleteDatas();
                                database.getDaoAccess().insertAllDatas(datasEntities);
                            }

                        }
                    }).start();

                } else {
                    Log.d(TAG, "onResponse: Gagal");
                }
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {
                progresBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "error nih" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
