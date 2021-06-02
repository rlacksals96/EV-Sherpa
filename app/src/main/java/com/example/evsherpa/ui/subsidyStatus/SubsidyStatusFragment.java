package com.example.evsherpa.ui.subsidyStatus;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evsherpa.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SubsidyStatusFragment extends Fragment {

    private ImageView img;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_subsidy_status, container, false);
        img=view.findViewById(R.id.img_view_subsidy_status);
        img.setImageResource(R.drawable.subsidy_status);
        return view;
    }



    /*
    public void updateTable(SubsidyStatus subsidyStatus){
        electric_sedan=subsidyStatus.getElectric_Sedan();
        for(Car car:electric_sedan){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_electric_sedan.addView(tableRow);
        }
        electoric_cargo=subsidyStatus.getElectoric_cargo();
        for(Car car:electoric_cargo){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView type=new TextView(getActivity());
            type.setText(car.getCar_type_name());
            type.setGravity(Gravity.LEFT);
            type.setTextSize(20);
            tableRow.addView(type);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_electric_cargo.addView(tableRow);
        }
        electoric_van=subsidyStatus.getElectoric_van();
        for(Car car:electoric_van){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView type=new TextView(getActivity());
            type.setText(car.getCar_type_name());
            type.setGravity(Gravity.LEFT);
            type.setTextSize(20);
            tableRow.addView(type);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_electric_van.addView(tableRow);
        }
        electric_bike=subsidyStatus.getElectric_bike();
        for(Car car:electric_bike){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_electric_bike.addView(tableRow);
        }
        hydro_sedan=subsidyStatus.getHydro_sedan();
        for(Car car:hydro_sedan){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_hydro_sedan.addView(tableRow);
        }
        hydro_van=subsidyStatus.getHydro_van();
        for(Car car:hydro_van){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_hydro_van.addView(tableRow);
        }
        construction_vehicle=subsidyStatus.getConstruction_vehicle();
        for(Car car:construction_vehicle){
            TableRow tableRow=new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView division=new TextView(getActivity());
            division.setText(car.getDivision_name());
            division.setGravity(Gravity.LEFT);
            division.setTextSize(20);
            tableRow.addView(division);

            TextView maker=new TextView(getActivity());
            maker.setText(car.getMaker_name());
            maker.setGravity(Gravity.LEFT);
            maker.setTextSize(20);
            tableRow.addView(maker);

            TextView carName=new TextView(getActivity());
            carName.setText(car.getCar_name());
            carName.setGravity(Gravity.LEFT);
            carName.setTextSize(20);
            tableRow.addView(carName);

            TextView subsidy=new TextView(getActivity());
            subsidy.setText(car.getSubsidy_amount());
            subsidy.setGravity(Gravity.LEFT);
            subsidy.setTextSize(20);
            tableRow.addView(subsidy);

            table_electric_construction_vehicle.addView(tableRow);
        }
    }
    public void initTableLayout(View view){
        table_electric_sedan=(TableLayout)view.findViewById(R.id.table_electric_sedan);
        table_electric_cargo=(TableLayout)view.findViewById(R.id.table_electric_cargo);
        table_electric_van=(TableLayout)view.findViewById(R.id.table_electric_van);
        table_electric_bike=(TableLayout)view.findViewById(R.id.table_electric_bike);
        table_hydro_sedan=(TableLayout)view.findViewById(R.id.table_hydro_sedan);
        table_hydro_van=(TableLayout)view.findViewById(R.id.table_hydro_van);
        table_electric_construction_vehicle=(TableLayout)view.findViewById(R.id.table_electric_construction_vehicle);


    }
    static class SubsidyStatus extends ArrayList<Element> {
        String url;
        Document doc;
        Elements table;
        ArrayList<Car> electric_sedan;//승용 및 초소형 전기자동차(승용, 초소형)
        ArrayList<Car> electoric_cargo;//전기화물차(화물)
        ArrayList<Car> electoric_van;//전기 승합차(승합)
        ArrayList<Car> electric_bike;//전기이륜차(경형, 소형, 대형*기타형)
        ArrayList<Car> hydro_sedan;//수소 승용(수소 승용)
        ArrayList<Car> hydro_van;//수소 승합(대형)
        ArrayList<Car> construction_vehicle;//(굴착소형)

        String division_name;
        String subsidy_amount;
        String maker_name;
        String car_type_name;
        String car_name;
        SubsidyStatus() {
            url="https://www.ev.or.kr/portal/buyersGuide/incenTive";
            electric_sedan=new ArrayList<>();
            electoric_cargo=new ArrayList<>();
            electoric_van=new ArrayList<>();
            electric_bike=new ArrayList<>();
            hydro_sedan=new ArrayList<>();
            hydro_van=new ArrayList<>();
            construction_vehicle=new ArrayList<>();

            division_name="";
            subsidy_amount="";
            maker_name="";
            car_type_name="";
            car_name="";

        }
        void connect(){
            try{
                doc=(Document) Jsoup.connect(url).get();
                table=doc.select("table.table_02_2_1").select("tbody").select("tr");
            }catch (IOException ioe){
                ioe.printStackTrace();
            }

        }
        void updateSubsidyStatus(){
            for(Element tr:table){
                Elements tds=tr.select("td");
                Element[] arr;
                String result="";
                switch (tds.size()){

                    //구분,종류(중형,대형),제조사,차량명,보조금
                    case 5:
                        int idx=0;
                        arr=new Element[5];
                        for(Element e:tds)
                            arr[idx++]=e;
                        //구분
                        division_name=arr[0].html();
                        //종류
                        car_type_name=arr[1].html();

//                        maker_cnt=Integer.parseInt(arr[2].attr("rowspan"));
                        maker_name=arr[2].html();

                        car_name=arr[3].html();
                        subsidy_amount=arr[4].html();


                        break;
                    //구분(승용), 제조사, 차령명, 보조금
                    case 4:
                        idx=0;
                        arr=new Element[4];
                        for(Element e:tds)
                            arr[idx++]=e;
                        //구분
                        division_name=arr[0].html();

//                        maker_cnt=Integer.parseInt(arr[1].attr("rowspan"));
                        maker_name=arr[1].html();

                        car_name=arr[2].html();

                        subsidy_amount=arr[3].html();

                        // 값이 5개인 경우 종류가 있지만 나머진 없어서 결과값에서 null이 나와야함.
                        car_type_name="";
                        break;
                    case 3:
                        idx=0;
                        arr=new Element[3];
                        for(Element e:tds)
                            arr[idx++]=e;

                        maker_name=arr[0].html();

                        car_name=arr[1].html();

                        subsidy_amount=arr[2].html();
                        break;
                    case 2:
                        idx=0;
                        arr=new Element[2];
                        for(Element e:tds)
                            arr[idx++]=e;
                        car_name=arr[0].html();

                        subsidy_amount=arr[1].html();
                        break;

                    default:
                        break;
                }

                if(car_type_name.isEmpty()){
                    if(division_name.equals("화물")){
                        electoric_cargo.add(new Car(division_name,subsidy_amount,maker_name,car_type_name,car_name));
                    }
                    else if(division_name.equals("승합")){
                        electoric_van.add(new Car(division_name,subsidy_amount,maker_name,car_type_name,car_name));
                    }
               }else{
                    switch (division_name) {
                        case "승용":
                        case "초소형":
                            electric_sedan.add(new Car(division_name, subsidy_amount, maker_name, car_name));
                            break;
                        case "경형":
                        case "소형":
                        case "대형·기타형":
                            electric_bike.add(new Car(division_name, subsidy_amount, maker_name, car_name));
                            break;
                        case "수소 승용":
                            hydro_sedan.add(new Car(division_name, subsidy_amount, maker_name, car_name));
                            break;
                        case "수소 승합(대형)":
                            hydro_van.add(new Car(division_name, subsidy_amount, maker_name, car_name));
                            break;
                        case "굴착 소형":
                            construction_vehicle.add(new Car(division_name, subsidy_amount, maker_name, car_name));
                            break;
                    }
                }
            }
        }
        public ArrayList<Car> getElectric_Sedan() {
            return electric_sedan;
        }

        public ArrayList<Car> getElectoric_cargo() {
            return electoric_cargo;
        }

        public ArrayList<Car> getElectoric_van() {
            return electoric_van;
        }

        public ArrayList<Car> getElectric_bike() {
            return electric_bike;
        }

        public ArrayList<Car> getHydro_sedan() {
            return hydro_sedan;
        }

        public ArrayList<Car> getHydro_van() {
            return hydro_van;
        }

        public ArrayList<Car> getConstruction_vehicle() {
            return construction_vehicle;
        }
    }
    static class Car{
        String division_name;
        String subsidy_amount;
        String maker_name;
        String car_type_name;
        String car_name;

        public Car(String division_name, String subsidy_amount, String maker_name, String car_type_name, String car_name) {
            this.division_name = division_name;
            this.subsidy_amount = subsidy_amount;
            this.maker_name = maker_name;
            this.car_type_name = car_type_name;
            this.car_name = car_name;
        }

        public Car(String division_name, String subsidy_amount, String maker_name, String car_name) {
            this.division_name = division_name;
            this.subsidy_amount = subsidy_amount;
            this.maker_name = maker_name;
            this.car_name = car_name;
        }

        public String getDivision_name() {
            return division_name;
        }

        public String getSubsidy_amount() {
            return subsidy_amount;
        }

        public String getMaker_name() {
            return maker_name;
        }

        public String getCar_type_name() {
            return car_type_name;
        }

        public String getCar_name() {
            return car_name;
        }
    }


     */
}