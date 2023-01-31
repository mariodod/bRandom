package com.example.brandom;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.brandom.databinding.FragmentFirstBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ImageView imageView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        imageView = binding.imageView;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(view1 -> galeryRandom());
        binding.buttonFirst2.setOnClickListener(view1 -> externalRandom());
        binding.buttonFirst3.setOnClickListener(view1 -> internalRandom());
    }

    private void internalRandom() {
        String[] imageProjection = new String[]{
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media._ID};
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageProjection,
                null,
                null,
                null
        );
        int total = cursor.getCount();

        if (total > 0) {
            Random random = new Random();
            int rndInt = random.nextInt(total);
            cursor.moveToPosition(rndInt);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int id = cursor.getInt(columnIndex);
            Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
            imageView.setImageURI(imageUri);
        } else {
            Toast.makeText(getContext(), "No photos found in the gallery.", Toast.LENGTH_SHORT).show();
        }
    }

    private void externalRandom() {
        String[] imageProjection = new String[]{
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media._ID};
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                imageProjection,
                null,
                null,
                null
        );
        int total = cursor.getCount();

        if (total > 0) {
            Random random = new Random();
            int rndInt = random.nextInt(total);
            cursor.moveToPosition(rndInt);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int id = cursor.getInt(columnIndex);
            Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
            imageView.setImageURI(imageUri);
        } else {
            Toast.makeText(getContext(), "No photos found in the gallery.", Toast.LENGTH_SHORT).show();
        }
    }

    private void galeryRandom() {
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/DCIM/Camera";
        List<String> images=new ArrayList<>();
        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();
        if(files == null) {
            Toast.makeText(getContext(), "No photos found in the gallery.", Toast.LENGTH_SHORT).show();
            return;
        }
        for (File file : files) {

            images.add(file.getAbsolutePath());
        }
        //Show image
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}