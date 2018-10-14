package ibnu21.com.retrofitroom;

import android.content.Intent;
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

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.Viewholder>{


    private List<DataResponse> dataResponse;
    private List<DataResponse> dataResponseFilter;

    public TitleAdapter(List<DataResponse> dataResponse) {
        this.dataResponse = dataResponse;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.title, parent, false));
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        DataResponse model = dataResponse.get(position);
        holder.txtTitle.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataResponse.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;

        public Viewholder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toDetailTitle = new Intent(itemView.getContext(), DetailActivity.class);
                    toDetailTitle.putExtra("title", dataResponse.get(getAdapterPosition()).getTitle());
                    toDetailTitle.putExtra("body", dataResponse.get(getAdapterPosition()).getBody());
                    itemView.getContext().startActivity(toDetailTitle);
                }
            });
        }
    }

    public void filter(ArrayList<DataResponse>newList)
    {
        dataResponse=new ArrayList<>();
        dataResponse.addAll(newList);
        notifyDataSetChanged();
    }


}
