package com.example.byron.sga;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SearchViewCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Jsonable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ciclos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ciclos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ciclos extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String url_login = "http://10.251.33.70:8084/cicloServlet";
    JSONObject json;
    RuntimeTypeAdapterFactory<Jsonable> rta;
    Gson gson;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private MenuItem myActionMenuItem;
    private SearchView searchView;
    private Button b_anyoCiclo, b_nuevoCiclo;
    private OnFragmentInteractionListener mListener;
    private TableLayout ll;
    private TableRow r_parent;
    private TableRow row;
    private TextView code, name, cre, ho;
    public Ciclos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ciclos.
     */
    // TODO: Rename and change types and number of parameters
    public static Ciclos newInstance(String param1, String param2) {
        Ciclos fragment = new Ciclos();
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
                .registerSubtype(Ciclo.class, "Ciclos").registerSubtype(Curso.class, "Cursos");
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
                imprimeTablaAnyo(query);
                //searchView.setIconified(true);
                //System.out.println(query);
                return true;
            }

            public void callSearch(String query) {
                System.out.println(query);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void imprimeTablaAnyo(String q){
        HttpClient client = new DefaultHttpClient();
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data",
                    "bAnyo"));
            nameValuePairs.add(new BasicNameValuePair("anyo",
                    q));
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,System.getProperty("http.agent"));
            String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
            HttpGet httpGet = new HttpGet(url_login + "?" + paramsString);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result= null;
            try {
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    result = convertStreamToString(instream).toString();
                    // now you have the string representation of the HTML request

                    if (!("-1\n".equals(result))){
                        org.json.JSONArray arr = new org.json.JSONArray(result);

                        String[] strs = new String[arr.length()];
                        for (int i = 0; i < arr.length(); i++) {
                            strs[i] = arr.getString(i);
                        }
                        if (arr.length() <= 0) {
                            instream.close();
                            //Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_LONG);

                        }else {
                            int count = ll.getChildCount();
                            for (int i = 1; i < count; i++) {
                                View child = ll.getChildAt(i);
                                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                            }
                            for (int i=0; i < arr.length(); i++){
                                JSONObject myObject1 = new JSONObject(strs[i]);

                                String _c, _n, _cr, _h;
                                _c = myObject1.getString("codigo");
                                _n = myObject1.getString("nombre");
                                _cr = myObject1.getString("creditos");
                                _h = myObject1.getString("horasSemanles");
                                //Curso curso = gson.fromJson(myObject1.toString(), Curso.class);

                                code = new TextView(getActivity());
                                name = new TextView(getActivity());
                                cre = new TextView(getActivity());
                                ho = new TextView(getActivity());
                                row = new TableRow(getActivity());

                                ViewGroup.LayoutParams lp = r_parent.getLayoutParams();
                                row.setLayoutParams(lp);
                                code.setText(_c);
                                name.setText(_n);
                                cre.setText(_cr);
                                ho.setText(_h);
                                row.addView(code);
                                row.addView(name);
                                row.addView(cre);
                                row.addView(ho);
                                ll.addView(row,i+1);
                            }
                            ll.setVisibility(View.VISIBLE);
                        }
                    } else {

                        //startActivity(new Intent(context,LoginActivity.class));

                    }

                } else {

                    //startActivity(new Intent(context,LoginActivity.class));

                }
            } catch (JSONException e){
                System.out.println(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public void close(){
        searchView.setIconified(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ciclos, container, false);
        row = new TableRow(getActivity());

        b_anyoCiclo = (Button) view.findViewById(R.id.b_anyoCiclo);
        b_nuevoCiclo = (Button) view.findViewById(R.id.b_nuevoCiclo);
        b_anyoCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display a message by using a Toast
                searchView.setVisibility(View.VISIBLE);
                myActionMenuItem.setVisible(true);
                searchView.setIconified(false);
            }
        });
        b_nuevoCiclo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ll.setVisibility(View.INVISIBLE);
            }
        });
        ll = (TableLayout) view.findViewById(R.id.t_anyoCiclo);
        r_parent = (TableRow) view.findViewById(R.id.r_parent);
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
