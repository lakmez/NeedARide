package com.ucm.cis.android.myproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucm.cis.android.db.Offer;
import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.db.nRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LekshmiPriya on 4/23/2015.
 */
public class CustomAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    nOffer data[];
    Offer data0[];
    nRequest data1[];

    public CustomAdapter(Context context, int resource, nOffer[] data) {
        super(context, resource, data);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = data;
    }
    public CustomAdapter(Context context, int resource, Offer[] data0) {
        super(context, resource, data0);
        this.layoutResourceId = resource;
        this.context = context;
        this.data0 = data0;
    }
    public CustomAdapter(Context context, int resource, nRequest[] data1) {
        super(context, resource, data1);
        this.layoutResourceId = resource;
        this.context = context;
        this.data1 = data1;
    }

    @Override
    public View getView(int position, View convView, ViewGroup parent) {
        View row = convView;
        DataHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DataHolder();
            holder.strtpt = (TextView) row.findViewById(R.id.strtpt);
            holder.endpt = (TextView) row.findViewById(R.id.endpt);
            holder.startdt = (TextView) row.findViewById(R.id.startdt);
            holder.strttime = (TextView) row.findViewById(R.id.strttime);
            holder.poolSeats = (TextView) row.findViewById(R.id.poolSeats);
            holder.distance = (TextView) row.findViewById(R.id.distance);
            row.setTag(holder);
        } else {
            holder = (DataHolder) row.getTag();
        }

        if (null != data0) {
            Offer offer = data0[position];
            holder.strtpt.setText(trimAddress(offer.getSource()));
            holder.endpt.setText(trimAddress(offer.getDestination()));
            String dat = offer.getDate();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yy/mm/dd");
            try {
                Date d1 = sdf.parse(dat);// ride date
                if (d.equals(d1)) {
                    dat = "Today";
                }
            } catch (Exception e) {
                dat = offer.getDate();
                e.printStackTrace();
            }
            holder.startdt.setText(dat);
            holder.strttime.setText(offer.getTime());
            holder.poolSeats.setText("Seats :" + offer.getSeats());
            if (offer.getDistance() != 0.0)
                holder.distance.setText(String.format("%.2f", offer.getDistance()) + " km away from here");
        }
        if (null != data) {
            nOffer offer = data[position];
            holder.strtpt.setText(trimAddress(offer.getSource().getAddress()));
            holder.endpt.setText(trimAddress(offer.getDestination().getAddress()));
            Date d = null;
            d= offer.getDatetime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String dat=null;
            if(null!=d){
                dat = sdf.format(d);
            }
            else{
                dat = sdf.format(new Date());
            }
            //Date d = new Date();

            //String dat = offer.getDatetime();//sdf.format(d);
            try {

            } catch (Exception e) {
               // dat = offer.getDate();
                e.printStackTrace();
            }
            holder.startdt.setText(dat);
           // holder.strttime.setText(offer.getTime());
            holder.poolSeats.setText("Seats :" + offer.getSeats());
            if (offer.getDistance() != 0.0)
                holder.distance.setText(String.format("%.2f", offer.getDistance()) + " km away from here");
        }
        if (null != data1) {
            nRequest offer = data1[position];
            holder.strtpt.setText(trimAddress(offer.getSource().getAddress()));
            holder.endpt.setText(trimAddress(offer.getDestination().getAddress()));
            Date d = null;
            d= offer.getDatetime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String dat=null;
            if(null!=d){
                dat = sdf.format(d);
            }
            else{
                dat = sdf.format(new Date());
            }
            try {
                //Date d1 = sdf.parse(dat);// ride date
              /*  if (d.equals(d1)) {
                    dat = "Today";
                }*/
            } catch (Exception e) {
               // dat = offer.getDate();
                e.printStackTrace();
            }
            holder.startdt.setText(dat);
            //holder.strttime.setText(offer.getTime());
           /* holder.poolSeats.setText("Seats :" + offer.getSeats());
            if (offer.getDistance() != 0.0)
                holder.distance.setText(String.format("%.2f", offer.getDistance()) + " km away from here");
*/
        }


        return row;
    }

    private String trimAddress(String address) {
        if(address.length()>15){
            address = address.substring(0,12)+"...";
        }
        return address;
    }

    static class DataHolder {
        TextView strtpt;
        TextView endpt;
        TextView startdt;
        TextView strttime;
        TextView poolSeats;
        TextView distance;
    }
}
