package co.com.miguelfor.multimedia.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;


public class VideoVFragment extends Fragment {
    private Button btnVideo;
    private VideoView videoView;

/*
    public class VideoFragment extends Fragment {
        private Button btnVideo;
        private VideoView videoView;

        private Uri videoUri;

        private static int REQUEST_CODE = 1;

        private static final String[] PERMISOS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int leer = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (leer == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISOS, REQUEST_CODE);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_video, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            btnVideo = (Button) view.findViewById(R.id.botonAbrir);
            videoView = (VideoView) view.findViewById(R.id.videoView);

            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                    File videosFolder = new File(Environment.getExternalStorageDirectory(), "VideosNextU");

                    videosFolder.mkdirs();

                    File video = new File(videosFolder, "video.mp4");

                    videoUri = Uri.fromFile(video);

                    videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

                    startActivityForResult(videoIntent, REQUEST_CODE);
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "Se guardó el video en:\n" + Environment.getExternalStorageDirectory() + "/VideosNextU/video.mp4", Toast.LENGTH_LONG).show();

                MediaController mediaController = new MediaController(getActivity());

                videoView.setMediaController(mediaController);

                videoView.setVideoURI(videoUri);
                videoView.start();

                mediaController.setAnchorView(videoView);
            } else {
                Toast.makeText(getActivity(), "Ha ocurrido un error al guardar el video", Toast.LENGTH_SHORT).show();
            }
        }

    }*/

}


