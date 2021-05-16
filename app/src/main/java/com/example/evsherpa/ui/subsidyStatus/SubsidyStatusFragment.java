package com.example.evsherpa.ui.subsidyStatus;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class SubsidyStatusFragment extends Fragment {

    private SubsidyStatusViewModel subsidyStatusViewModel;

    private TableLayout tableLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subsidy_status, container, false);
        tableLayout=(TableLayout)view.findViewById(R.id.table_subsidy_status);
        TableRow tableRow=new TableRow(getActivity());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        for(int i=0;i<3;i++){
            TextView textView=new TextView(getActivity());
            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.LEFT);
            textView.setPadding(15,2,15,2);
            textView.setTextSize(20);
            tableRow.addView(textView);

        }
        tableLayout.addView(tableRow);

        //this is for test..
        //TODO:일반 java 파일에서 jsoup 파싱 제대로 맞춰 온담에 적용하자..
        //걍 넣으니 터져버림
        Webpage webpage=new Webpage();
        String []str=webpage.getWebpage();

        for(int i=0;i<str.length;i++){
            TextView textView=new TextView(getActivity());
            textView.setText(str[i]);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(20);
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);



        return view;
    }

    class Webpage extends ArrayList<Element> {

        String[] getWebpage() {
            String url="https://www.ev.or.kr/portal/buyersGuide/incenTive";
            String []str=new String[3];
            try{
                Document document= (Document) Jsoup.connect(url).get();
                Elements tableList=document.select(".d102_blit > tbody");
                for(Element tr:tableList){



                    str[0]="1";
                    str[1]=tr.select("td").get(0).html();
                    str[2]=tr.select("td").get(1).html();

                    break;

                }


            }catch (IOException ioe){
                ioe.printStackTrace();


            }

            return str;
        }
    }
}