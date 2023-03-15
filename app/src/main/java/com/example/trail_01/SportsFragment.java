package com.example.trail_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SportsFragment#} factory method to
 * create an instance of this fragment.
 */
public class SportsFragment extends Fragment {

    ProgressBar prg;
    RecyclerView recyclerViewSports;


    Handler dataHanler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<com.example.cs310newstrail.News> data = (List<com.example.cs310newstrail.News>) message.obj;
            NewsAdapter adp = new NewsAdapter(getContext(),data);
            recyclerViewSports.setAdapter(adp);
            recyclerViewSports.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public EconomicsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment EconomicsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static EconomicsFragment newInstance(String param1, String param2) {
//        EconomicsFragment fragment = new EconomicsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//
//
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sports, container, false);

        recyclerViewSports = v.findViewById(R.id.recSports);
        prg = v.findViewById(R.id.progressBarList);
        recyclerViewSports.setLayoutManager(new LinearLayoutManager(getContext()));
        prg.setVisibility(View.VISIBLE);
        recyclerViewSports.setVisibility(View.INVISIBLE);


        findNews();



        return v;
    }

    public void findNews(){

        NewsRepository repo = new NewsRepository();
        repo.GetNewsByCategoryId(((NewsApplication)getActivity().getApplication()).srv, dataHanler, 2);
        //repo.getAllData(((NewsApplication)getActivity().getApplication()).srv, dataHanler);
    }
}