package com.example.evsherpa.ui.carInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evsherpa.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CarInfoAdapter extends RecyclerView.Adapter<CarInfoAdapter.CarInfoViewHolder> {

    private ArrayList<CarInfoData> arrayList;

    public CarInfoAdapter(ArrayList<CarInfoData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CarInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_info_list,parent,false);
        CarInfoViewHolder holder= new CarInfoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarInfoViewHolder holder, int position) {
        holder.car_profile.setImageResource(arrayList.get(position).getCar_profile());
        holder.txt_car_name.setText("차량명: ".concat(arrayList.get(position).getTxt_car_name()));
        holder.txt_car_maker.setText("제조사: ".concat(arrayList.get(position).getTxt_car_maker()));
        holder.txt_car_capacity.setText("승차인원: ".concat(arrayList.get(position).getTxt_car_capacity()));
        holder.txt_max_speed.setText("최고속도출력: ".concat(arrayList.get(position).getTxt_max_speed()));
        holder.txt_max_distance.setText("1회충전주행거리: ".concat(arrayList.get(position).getTxt_max_distance()));
        holder.txt_battery.setText("배터리: ".concat(arrayList.get(position).getTxt_battery()));
        holder.txt_subsidy.setText("국고보조금: ".concat(arrayList.get(position).getTxt_subsidy()));
        holder.txt_maker_phone_num.setText("제조사번호: ".concat(arrayList.get(position).getTxt_maker_phone_num()));


        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 필요기능 생성후 toast는 삭제
                String car_name=holder.txt_car_name.getText().toString();
                Toast.makeText(v.getContext(),car_name,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);
    }

    public static class CarInfoViewHolder extends RecyclerView.ViewHolder {

        protected ImageView car_profile;
        protected TextView txt_car_name;
        protected TextView txt_car_maker;
        protected TextView txt_car_capacity;
        protected TextView txt_max_speed;
        protected TextView txt_max_distance;
        protected TextView txt_battery;
        protected TextView txt_subsidy;
        protected TextView txt_maker_phone_num;

        public CarInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.car_profile = (ImageView)itemView.findViewById(R.id.car_profile);
            this.txt_car_name = (TextView) itemView.findViewById(R.id.txt_car_name);
            this.txt_car_maker = (TextView) itemView.findViewById(R.id.txt_car_maker);
            this.txt_car_capacity =(TextView) itemView.findViewById(R.id.txt_car_capacity);
            this.txt_max_speed =(TextView)itemView.findViewById(R.id.txt_max_speed);
            this.txt_max_distance = (TextView)itemView.findViewById(R.id.txt_max_distance);
            this.txt_battery = (TextView)itemView.findViewById(R.id.txt_battery);
            this.txt_subsidy =(TextView)itemView.findViewById(R.id.txt_subsidy) ;
            this.txt_maker_phone_num = (TextView)itemView.findViewById(R.id.txt_maker_phone_num);
        }


    }
}
