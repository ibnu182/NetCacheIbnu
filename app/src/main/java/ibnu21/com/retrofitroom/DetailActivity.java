package ibnu21.com.retrofitroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ibnu21.com.netcache.R;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_judul)
    TextView tvJudul;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    String title, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getIntentExtras();

        tvJudul.setText(title);
        tvDetail.setText(body);
    }

    private void getIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            body = extras.getString("body");
        }
    }
}
