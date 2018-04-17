package co.com.miguelfor.multimedia.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.miguelfor.multimedia.BuildConfig;
import co.com.miguelfor.multimedia.R;

import static android.app.Activity.RESULT_OK;


public class ImagenFragment extends Fragment {


    private TextView tvRuta;

    Bitmap bitmap = null;
    ImageView iv;
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_CAMARA = 2;
    private static final String[] PERMISOS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String mCurrentPhotoPath;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ImagenFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ImagenFragment newInstance(String param1, String param2) {
        ImagenFragment fragment = new ImagenFragment();
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

        int leer = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int leer1 = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (leer == PackageManager.PERMISSION_DENIED || leer1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this.getActivity(), PERMISOS, REQUEST_CODE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imagen, container, false);
        // Inflate the layout for this fragment
        iv = rootView.findViewById(R.id.iv);
        tvRuta = rootView.findViewById(R.id.tvRuta);

        Button button = rootView.findViewById(R.id.abrir);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);


            }
        });


        Button button2 = rootView.findViewById(R.id.capturar);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagenFolder = new File(Environment.getExternalStorageDirectory(), "CamaraFolder");
                imagenFolder.mkdirs();
                File imagen = new File(imagenFolder, "foto.jpg");
                Uri uriImagen = null;
                try {
                    uriImagen = FileProvider.getUriForFile(getContext()
                            , BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Uri uriImagen = Uri.fromFile(imagen);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagen);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAMARA);
            }
        });
        return rootView;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String imageFileName = "JPEG_" + timeStamp + "_";
        String imageFileName = "foto";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {

        super.onStart();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != Data) {
            Uri imagenseleccionada = Data.getData();
            String[] path = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(imagenseleccionada, path, null, null, null);
            cursor.moveToFirst();
            int columna = cursor.getColumnIndex(path[0]);
            String pathimagen = cursor.getString(columna);
            cursor.close();
            bitmap = BitmapFactory.decodeFile(pathimagen);
            BitmapFactory.Options options = new BitmapFactory.Options();
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            float scaleA = ((float) (width / 2)) / width;
            float scaleB = ((float) (height / 2)) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleA, scaleB);
            Bitmap nuevaimagen = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            iv.setImageBitmap(nuevaimagen);
            tvRuta.setText("RUTA: " + pathimagen);


        }

        if (requestCode == REQUEST_CODE_CAMARA && resultCode == RESULT_OK) {

            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                iv.setImageBitmap(BitmapFactory.decodeStream(ims));
                tvRuta.setText("RUTA: " + imageUri.getPath());
            } catch (FileNotFoundException e) {
                return;
            }

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
             /*
              Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ "Camera/foto.jpg"  );

            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            float scaleA = ((float)(width/2))/width;
            float scaleB = ((float)(height/2))/height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleA,scaleB);

            Bitmap nuevaImagen = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

            iv.setImageBitmap(nuevaImagen);*/

        } else if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), "No se guardó correctamente la imagen en el dispositivo", Toast.LENGTH_LONG).show();
        }

    }

}
