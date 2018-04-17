package co.com.miguelfor.multimedia.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import co.com.miguelfor.multimedia.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioFragment extends Fragment {


    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    boolean verificacion = true;
    private TextView tvRuta;
    private Button btnplay;
    private Button btnGrabar;
    private Button btnAbrir;
    private static String nombreAudio = null;
    private MediaRecorder mediaRecorder = null;

    ///PLAY
    private String globalUrl = null;
    int aux = 0;
    boolean a = true;
    private MediaPlayer audio;
    static final int Pick_song = 1;

    private void detenerGrabacion() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        Toast.makeText(getContext(), "Se ha guardado el audio en:\n" + Environment.getExternalStorageDirectory() +
                "/audio.3gp", Toast.LENGTH_LONG).show();
        globalUrl = Environment.getExternalStorageDirectory() +
                "/audio.3gp";
        btnplay.setVisibility(View.VISIBLE);
        tvRuta.setText("RUTA: " + globalUrl);
        a = true;
        audio = null;
        aux = 0;
    }

    private void grabando(boolean comenzado) {
        if (comenzado) {
            comenzarGrabacion();
        } else {
            detenerGrabacion();
        }

    }


    private void comenzarGrabacion() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nombreAudio);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Toast.makeText(getContext(), "No se guardo correctamente", Toast.LENGTH_SHORT).show();
        }

        mediaRecorder.start();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioFragment newInstance(String param1, String param2) {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);
        btnGrabar = rootView.findViewById(R.id.btnGrabar);

        int leer = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int leer1 = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int leer2 = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.RECORD_AUDIO);
        if (leer == PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED || leer1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this.getActivity(), PERMISOS, REQUEST_CODE);
        }

        nombreAudio = Environment.getExternalStorageDirectory() + "/audio.3gp";


        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabando(verificacion);

                if (verificacion) {
                    btnGrabar.setText("Detener Grabacion");
                } else {
                    btnGrabar.setText("Iniciar Grabacion");
                }
                verificacion = !verificacion;

            }
        });
        btnplay = rootView.findViewById(R.id.btnPlay);
        tvRuta = rootView.findViewById(R.id.tvRuta);
        btnplay.setVisibility(View.INVISIBLE);

        btnAbrir = rootView.findViewById(R.id.btnAbrir);
        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione un audio"), Pick_song);
                //audio desde la app
                /*  if(a){
                    audio = MediaPlayer.create(getContext(),R.raw.cancion);
                    audio.setLooping(false);
                    audio.seekTo(aux);
                    audio.start();
                    a=false;
                }else{
                    audio.pause();
                    aux=audio.getCurrentPosition();
                    a=true;

                }*/
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int flag1;
                int flag2;
                int flag3;
                if (a) {
                    flag1 = aux;
                    audio = MediaPlayer.create(getContext(), Uri.parse(globalUrl));
                    flag2 = audio.getCurrentPosition();
                    flag3 = audio.getDuration();
                    audio.setLooping(false);
                    audio.seekTo(aux);
                    audio.start();
                    a = false;
                } else {
                    flag1 = aux;
                    flag2 = audio.getCurrentPosition();
                    flag3 = audio.getDuration();
                    boolean mike = audio.isPlaying();
                    if (!mike) {
                        aux = 0;
                        audio.seekTo(aux);
                        audio.start();
                        a = false;
                    } else {
                        audio.stop();
                        //audio.pause();
                        aux = audio.getCurrentPosition();
                        a = true;
                    }


                }

            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Pick_song:
                if (resultCode == Activity.RESULT_OK) {
                    String patch = data.getDataString();
                    globalUrl = (patch);
                    btnplay.setVisibility(View.VISIBLE);
                    tvRuta.setText("RUTA: " + globalUrl);
                    a = true;
                    audio = null;
                    aux = 0;

                }


        }
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
