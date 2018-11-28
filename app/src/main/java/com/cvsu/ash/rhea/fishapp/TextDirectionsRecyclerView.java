package com.cvsu.ash.rhea.fishapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ralph_lirio on 11/22/2016.
 */

public class TextDirectionsRecyclerView extends RecyclerView.Adapter<TextDirectionsRecyclerView.TextDirectionsViewHolder> {
    ArrayList<Directions> dList;
    Context mContext;
    View rootView;
    String fName, sName;
    String ins = "";
    public TextDirectionsRecyclerView(Context context, ArrayList<Directions> list, String facilityName, String streetName) {
        this.dList = list;
        this.mContext = context;
        this.fName = facilityName;
        this.sName = streetName;

    }

    @Override
    public TextDirectionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_directions, parent, false);
        TextDirectionsViewHolder viewHolder = new TextDirectionsViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TextDirectionsViewHolder holder, final int position) {

        String value = dList.get(position).getDirection();

        final double lat = dList.get(position).getLat();
        final double lng = dList.get(position).getLng();

        if(value.contains("Continue to")) {
            String getContinue = value.substring(value.indexOf("Continue"), value.length());
            String cont = Html.fromHtml(getContinue).toString();
            String noHtml = Html.fromHtml(value).toString();
            String d = noHtml.substring(0, noHtml.indexOf(cont));
            holder.tvContinue.setText(cont);
            holder.tvDirections.setText(d.trim());
            ins = d.trim();
        } else if(value.contains("Destination will be")) {
            String getDes = value.substring(value.indexOf("Destination"), value.length());
            String des = Html.fromHtml(getDes).toString();
            String noHtml = Html.fromHtml(value).toString();
            String d = noHtml.substring(0, noHtml.indexOf(des));
            holder.tvContinue.setText(des);
            holder.tvDirections.setText(d.trim());
            ins = d.trim();

        } else if(value.contains("Toll road") || value.contains("Partial toll road")) {
            if(value.contains("Toll road")) {
                String getDes = value.substring(value.indexOf("Toll"), value.length());
                String des = Html.fromHtml(getDes).toString();
                String noHtml = Html.fromHtml(value).toString();
                String d = noHtml.substring(0, noHtml.indexOf(des));
                holder.tvContinue.setText(des);
                holder.tvDirections.setText(d.trim());
                ins = d.trim();
            } else if (value.contains("Partial toll road")) {
                String getDes = value.substring(value.indexOf("Partial"), value.length());
                String des = Html.fromHtml(getDes).toString();
                String noHtml = Html.fromHtml(value).toString();
                String d = noHtml.substring(0, noHtml.indexOf(des));
                holder.tvContinue.setText(des);
                holder.tvDirections.setText(d.trim());
                ins = d.trim();
            }
        } else {
            holder.tvContinue.setVisibility(View.GONE);
            holder.tvDirections.setText(Html.fromHtml(value).toString());
        }

        if (dList.get(position).getManeuver() == null) {
            holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_up));
            holder.tvContinue.setVisibility(View.GONE);
        } else {

            if (dList.get(position).getManeuver().equalsIgnoreCase("straight")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_up));
            } else if (dList.get(position).getManeuver().equalsIgnoreCase("turn-right")) {
                // holder.ivDirections.setImageResource(R.drawable.ic_turn_right);
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_turn_right));
            } else if(dList.get(position).getManeuver().equalsIgnoreCase("turn-slight-right")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_slight_right));
            } else if (dList.get(position).getManeuver().equalsIgnoreCase("turn-left")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_turn_left));
            } else if (dList.get(position).getManeuver().equalsIgnoreCase("turn-slight-left")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_slight_left));
            }  else if (dList.get(position).getManeuver().equalsIgnoreCase("roundabout-right") || dList.get(position).getManeuver().equalsIgnoreCase("roundabout-left")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_roundabout));
            } else if (dList.get(position).getManeuver().equalsIgnoreCase("ferry")) {
                holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_ferryboat));
            }
        }

        if (value.equalsIgnoreCase("")) {
            holder.tvDirections.setText(fName);
            if(sName.length() < 1 || sName == null) {
                holder.tvContinue.setVisibility(View.GONE);
            } else {
                holder.tvContinue.setText(sName);
            }

            holder.ivDirections.setBackground(mContext.getResources().getDrawable(R.drawable.ic_marker));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVariables.points >= 20000) {
                    GlobalFunction f = new GlobalFunction();
                    f.showDialog(mContext, "", "Destination too far away.");
                } else {
                    ((MapsActivity)mContext).animateToLocationFromRecyclerView(lat, lng, ins, rootView);
                }


            }
        });

    }

    @Override
    public int getItemCount() {

        return dList.size();
    }

    public class TextDirectionsViewHolder extends RecyclerView.ViewHolder {
        TextView tvDirections, tvContinue;
        ImageView ivDirections;
        LinearLayout llContainer;


        TextDirectionsViewHolder(View itemView) {
            super(itemView);
            llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
            tvDirections = (TextView) itemView.findViewById(R.id.tvDirectionText);
            ivDirections = (ImageView) itemView.findViewById(R.id.ivDirectionIcon);
            tvContinue = (TextView) itemView.findViewById(R.id.tvContinueTo);
        }
    }
}
