package com.bricboys.zxapp;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static void shareImage(Context context, ImageView imageView, String title) {
        if (imageView.getDrawable() == null) {
            Toast.makeText(context, "Aguarde o carregamento da imagem", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        try {
            File cachePath = new File(context.getCacheDir(), "shared_images");
            cachePath.mkdirs();
            File imageFile = new File(cachePath, "shared_image.jpg");
            FileOutputStream stream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();

            Uri contentUri = FileProvider.getUriForFile(context, "com.bricboys.zxapp.fileprovider", imageFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/jpeg");
                context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        } catch (IOException e) {
            Toast.makeText(context, "Erro ao compartilhar imagem", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void setWallpaper(Context context, ImageView imageView) {
        if (imageView.getDrawable() == null) {
            Toast.makeText(context, "Aguarde o carregamento da imagem", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        WallpaperManager wm = WallpaperManager.getInstance(context);
        try {
            wm.setBitmap(bitmap);
            Toast.makeText(context, "Papel de parede definido!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Erro ao definir papel de parede", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
