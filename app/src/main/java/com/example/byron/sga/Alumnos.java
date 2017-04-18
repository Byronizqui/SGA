package com.example.byron.sga;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import org.json.JSONObject;

import LogicaNegocio.Alumno;
import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Jsonable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Alumnos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Alumnos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alumnos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private View view;
    private String mParam1;
    private String mParam2;
    private String rol;
    JSONObject json;
    RuntimeTypeAdapterFactory<Jsonable> rta;
    Gson gson;
    private Intent intent;
    private Bundle bundle;
    private MenuItem myActionMenuItem;
    private SearchView searchView;
    private Button b_historialAlumno, b_editarAlumno, b_matricularAlumno, b_carreraAlumno,
            b_nombreAlumno, b_agregarAlumno, b_cedulaAlumno;
    private OnFragmentInteractionListener mListener;

    public Alumnos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alumnos.
     */
    // TODO: Rename and change types and number of parameters
    public static Alumnos newInstance(String param1, String param2) {
        Alumnos fragment = new Alumnos();
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        rta = RuntimeTypeAdapterFactory.of(Jsonable.class, "_class")
                .registerSubtype(Ciclo.class, "Ciclos").registerSubtype(Curso.class, "Cursos")
        .registerSubtype(Alumno.class, "Alumnos");
        gson = new GsonBuilder().registerTypeAdapterFactory(rta).setDateFormat("dd/MM/yyyy").create();
        StrictMode.setThreadPolicy(policy);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        myActionMenuItem = menu.findItem( R.id.action_search);

        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                //textView.setText(newText);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                //textView.setText(query);
                //callSearch(query);
                //searchView.setVisibility(View.VISIBLE);

                searchView.setVisibility(View.INVISIBLE);
                searchView.setVisibility(View.VISIBLE);
                myActionMenuItem.setVisible(false);
                //imprimeTablaAnyo(query);
                //searchView.setIconified(true);
                //System.out.println(query);
                return true;
            }

            public void callSearch(String query) {
                System.out.println(query);
            }
        });
        this.rol = bundle.getString("rol");
        switch (rol){
            case "3":{
                this.b_agregarAlumno.setVisibility(View.INVISIBLE);
                this.b_carreraAlumno.setVisibility(View.INVISIBLE);
                this.b_cedulaAlumno.setVisibility(View.INVISIBLE);
                this.b_editarAlumno.setVisibility(View.INVISIBLE);
                this.b_historialAlumno.setVisibility(View.INVISIBLE);
                this.b_matricularAlumno.setVisibility(View.INVISIBLE);
                this.b_nombreAlumno.setVisibility(View.INVISIBLE);
            }
            break;
            default:{

            }
            break;
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alumnos, container, false);
        intent = getActivity().getIntent();
        bundle = intent.getExtras();

        this.b_agregarAlumno = (Button) view.findViewById(R.id.b_agregarAlumno);
        this.b_carreraAlumno = (Button) view.findViewById(R.id.b_carreraAlumno);
        this.b_cedulaAlumno = (Button) view.findViewById(R.id.b_cedulaAlumno);
        this.b_editarAlumno = (Button) view.findViewById(R.id.b_editarAlumno);
        this.b_historialAlumno = (Button) view.findViewById(R.id.b_historialAlumno);
        this.b_matricularAlumno = (Button) view.findViewById(R.id.b_matricularAlumno);
        this.b_nombreAlumno = (Button) view.findViewById(R.id.b_nombreAlumno);
        this.b_nombreAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display a message by using a Toast
                searchView.setVisibility(View.VISIBLE);
                myActionMenuItem.setVisible(true);
                searchView.setIconified(false);
            }
        });
        this.b_cedulaAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display a message by using a Toast
                searchView.setVisibility(View.VISIBLE);
                myActionMenuItem.setVisible(true);
                searchView.setIconified(false);
            }
        });
        this.b_carreraAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display a message by using a Toast
                searchView.setVisibility(View.VISIBLE);
                myActionMenuItem.setVisible(true);
                searchView.setIconified(false);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
