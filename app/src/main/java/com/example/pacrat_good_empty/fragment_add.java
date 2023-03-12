package com.example.pacrat_good_empty;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_add extends Fragment {
    private CollectionDatabase database;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_add.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_add newInstance(String param1, String param2) {
        fragment_add fragment = new fragment_add();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view2 = inflater.inflate(R.layout.fragment_add,container,false);

        Button btn = (Button) view2.findViewById(R.id.upload_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) view2.findViewById(R.id.databaseName);
                String str = "";

                str = name.getText().toString();

                Log.d("hello", "onClick: HEKEKKEKKEKEKKEKK   " + name.getText().toString());
                database = new CollectionDatabase(getContext());
                database.TABLE_NAME = str;
                database.createTable();
                Toast.makeText(getContext(), "Collection Table has been updated", Toast.LENGTH_SHORT).show();
                onDestroyView();
                getActivity().getFragmentManager().popBackStack();



            }
        });

        return view2;
    }


}