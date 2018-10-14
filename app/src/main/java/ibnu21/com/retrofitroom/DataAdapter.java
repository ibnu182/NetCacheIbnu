package ibnu21.com.retrofitroom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ibnu21.com.netcache.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    List<DataResponse> dataResponse = new ArrayList<>();
    Context context;


    public DataAdapter(List<DataResponse> dataResponses, Context context) {
        this.dataResponse = dataResponses;
        this.context = context;
    }

    public DataAdapter(DataResponse response) {
        this.dataResponse = dataResponse;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.title, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        DataResponse model = dataResponse.get(position);

        holder.txtTitle.setText(model.getTitle());

    }

    @Override
    public int getItemCount() {
        if (dataResponse == null)return 0;
        return dataResponse.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title)
        TextView txtTitle;

        public DataHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
