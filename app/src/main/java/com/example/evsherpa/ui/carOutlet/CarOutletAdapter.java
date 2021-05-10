package com.example.evsherpa.ui.carOutlet;

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

public class CarOutletAdapter extends RecyclerView.Adapter<CarOutletAdapter.CarOutletViewHolder> {

    private ArrayList<CarOutletData> arrayList;

    public CarOutletAdapter(ArrayList<CarOutletData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CarOutletAdapter.CarOutletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.outlet_list,parent,false);
        CarOutletViewHolder holder=new CarOutletViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarOutletAdapter.CarOutletViewHolder holder, int position) {
        //holder 데이터 초기화하고 action 설정 가능
        holder.outlet_profile.setImageResource(arrayList.get(position).getOutlet_profile());
        holder.txt_connector_name.setText(arrayList.get(position).getTxt_connector_name());
        holder.txt_charge_current.setText(arrayList.get(position).getTxt_charge_current());
        holder.txt_charge_power.setText(arrayList.get(position).getTxt_charge_power());
        holder.txt_charge_level.setText(arrayList.get(position).getTxt_charge_level());
        holder.txt_available_car.setText(arrayList.get(position).getTxt_available_car());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //필요한 기능은 아닌데 제대로 눌리는지 확인하는 용도.
                String outlet_Name=holder.txt_connector_name.getText().toString();
                Toast.makeText(v.getContext(),outlet_Name,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return (arrayList !=null ? arrayList.size():0);
    }

    public class CarOutletViewHolder extends RecyclerView.ViewHolder{

        protected ImageView outlet_profile;
        protected TextView txt_connector_name;
        protected TextView txt_charge_current;
        protected TextView txt_charge_power;
        protected TextView txt_charge_level;
        protected TextView txt_available_car;
        public CarOutletViewHolder(@NonNull View itemView) {
            super(itemView);
            this.outlet_profile=(ImageView)itemView.findViewById(R.id.outlet_profile);
            this.txt_connector_name=(TextView)itemView.findViewById(R.id.txt_connector_name);
            this.txt_charge_current=(TextView)itemView.findViewById(R.id.txt_charge_current);
            this.txt_charge_power=(TextView)itemView.findViewById(R.id.txt_charge_power);
            this.txt_charge_level=(TextView)itemView.findViewById(R.id.txt_charge_level);
            this.txt_available_car=(TextView)itemView.findViewById(R.id.txt_available_car);
        }
    }
}
