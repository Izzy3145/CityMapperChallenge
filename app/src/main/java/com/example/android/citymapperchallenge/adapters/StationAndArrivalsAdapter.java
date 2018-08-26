package com.example.android.citymapperchallenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.citymapperchallenge.R;
import com.example.android.citymapperchallenge.model.ArrivalLineTime;
import com.example.android.citymapperchallenge.model.NearbyStationDetails;
import com.example.android.citymapperchallenge.nearbyStations.StopPoint;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationAndArrivalsAdapter extends RecyclerView.Adapter<StationAndArrivalsAdapter.ViewHolder>{

    private ArrayList<NearbyStationDetails> mNearbyDetails;
    private Context mContext;
    private DetailsAdapterListener mClickHandler;

    public StationAndArrivalsAdapter(Context c, ArrayList<NearbyStationDetails> nearbyDetails,
                                     DetailsAdapterListener clickHandler) {
        mNearbyDetails = nearbyDetails;
        mContext = c;
        mClickHandler = clickHandler;
    }

    @Override
    public StationAndArrivalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arrival_list_item, parent,
                false);
        //pass the view to the ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StationAndArrivalsAdapter.ViewHolder holder, int position) {
        NearbyStationDetails nearbyStationDetails = mNearbyDetails.get(position);
        String stationName = nearbyStationDetails.getStation();
        ArrayList<ArrivalLineTime> threeArrivals = nearbyStationDetails.getArrivals();
        String firstArrival;
        String secondArrival;
        String thirdArrival;
        if(threeArrivals != null) {
            firstArrival = arrivalToString(threeArrivals.get(0));
            secondArrival = arrivalToString(threeArrivals.get(1));
            thirdArrival = arrivalToString(threeArrivals.get(2));
        } else {
            firstArrival = "";
            secondArrival = "";
            thirdArrival = "";
        }

        holder.mStationTv.setText(stationName);
        holder.mFirstArrivalTv.setText(firstArrival);
        holder.mSecondArrivalTv.setText(secondArrival);
        holder.mThirdArrivalTv.setText(thirdArrival);

    }

    @Override
    public int getItemCount() {
        return mNearbyDetails.size();
    }

    public void setStationsToAdapter(ArrayList<NearbyStationDetails> nearbyStations){
        mNearbyDetails = nearbyStations;
        notifyDataSetChanged();
    }

    //create onClickListener interface
    public interface DetailsAdapterListener {
        void onFirstArrivalClick(View v, int position);
        void onSecondArrivalClick(View v, int position);
        void onThirdArrivalClick(View v, int position);
    }

    //create viewholder class
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stationListTv)
        TextView mStationTv;
        @BindView(R.id.firstArrivalTv)
        TextView mFirstArrivalTv;
        @BindView(R.id.secondArrivalTv)
        TextView mSecondArrivalTv;
        @BindView(R.id.thirdArrivalTv)
        TextView mThirdArrivalTv;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mFirstArrivalTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickHandler.onFirstArrivalClick(v, getAdapterPosition());
                }
            });

            mSecondArrivalTv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mClickHandler.onSecondArrivalClick(v, getAdapterPosition());
                }
            });

            mThirdArrivalTv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mClickHandler.onThirdArrivalClick(v, getAdapterPosition());
                }
            });
        }

    }

    private String arrivalToString(ArrivalLineTime arr){
        String lineName = arr.getLineName();
        String arrivalTime = String.valueOf(arr.getTime());
        return lineName + " in: " + arrivalTime;
    }
}
