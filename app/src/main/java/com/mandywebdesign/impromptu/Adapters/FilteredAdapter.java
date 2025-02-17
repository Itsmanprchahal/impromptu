package com.mandywebdesign.impromptu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mandywebdesign.impromptu.Filter.FilteredActivity;
import com.mandywebdesign.impromptu.Home_Screen_Fragments.Home;
import com.mandywebdesign.impromptu.Utils.Constants;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.ui.BookEventActivity;

public class FilteredAdapter extends RecyclerView.Adapter<FilteredAdapter.ViewHolder> {

    Context context;
    CardAdapterHelper cardAdapterHelper= new CardAdapterHelper();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public FilteredAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.custom_events,viewGroup,false);

        return new FilteredAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
//        cardAdapterHelper.onBindViewHolder(viewHolder.itemView,i,getItemCount());
        sharedPreferences = context.getSharedPreferences("ItemPosition", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        viewHolder.eventName.setText(FilteredActivity.title.get(i));
        viewHolder.evetPrice.setText(FilteredActivity.prices.get(i));
        viewHolder.eventAddress.setText(FilteredActivity.addres.get(i));
        viewHolder.category.setText(FilteredActivity.categois.get(i));
        Glide.with(context).load(FilteredActivity.images.get(i)).into(viewHolder.eventImage);
        viewHolder.date.setText(FilteredActivity.event_time.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String value = FilteredActivity.event_id.get(i);

                Intent intent = new Intent(context, BookEventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("event_id",value);
                intent.putExtra("fav_id","0");
                intent.putExtra("lat", Home.lat);
                intent.putExtra("lng",Home.lng);
                intent.putExtra("hostname",FilteredActivity.name1.get(i));
                editor.putString(Constants.itemPosition, String.valueOf(i));
                editor.commit();
                context.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return FilteredActivity.name1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView eventImage;
        TextView eventName,eventAddress,date;
        TextView evetPrice,category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            evetPrice = itemView.findViewById(R.id.event_price);
            eventAddress = itemView.findViewById(R.id.custom_text1);
            category = itemView.findViewById(R.id.custom_category_name);
            date = itemView.findViewById(R.id.date);
        }
    }
}
