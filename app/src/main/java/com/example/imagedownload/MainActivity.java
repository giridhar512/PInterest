package com.example.imagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mImageUrlsToDisplay = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSomeUrlsToList();
        ICache cache = new LRUCache(3);
        DownloadImage imageDownloader = new DownloadImage(getApplicationContext(), cache);

        Button nextButton = (Button)findViewById(R.id.Next);
        nextButton.setText("Click here to view Image feed");
        Iterator imageIterator = mImageUrlsToDisplay.iterator();

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String cachePath = null;
                if (imageIterator.hasNext()) {
                    try {
                        cachePath = imageDownloader.downloadImage((String)imageIterator.next());
                    } catch (Exception e) {
                    }

                    if(cachePath != null){
                        Bitmap myBitmap = BitmapFactory.decodeFile(cachePath);
                        ImageView myImage = (ImageView) findViewById(R.id.imageView2);
                        myImage.setImageBitmap(myBitmap);
                        nextButton.setText("Next Image");
                    }
                } else {
                    Button button = (Button)findViewById(R.id.Next);
                    button.setText("You Viewed all Images !");
                }
            }
        });
    }

    private void addSomeUrlsToList() {
        mImageUrlsToDisplay.add("https://images.unsplash.com/profile-fb-1464533812-a91a557e" +
                "646d.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=b9ff3604ece655dfc27052e941443856");
        mImageUrlsToDisplay.add("https://images.unsplash.com/placeholder-avatars/extra-la" +
                "rge.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=46caf91cf1f90b8b5ab6621512f102a8");
        mImageUrlsToDisplay.add("https://images.unsplash.com/placeholder-avatars/extra-la" +
                "rge.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=46caf91cf1f90b8b5ab6621512f102a8");
        mImageUrlsToDisplay.add("https://images.unsplash.com/profile-fb-1464533812-a91a557e" +
                "646d.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=b9ff3604ece655dfc27052e941443856");
        mImageUrlsToDisplay.add("https://images.unsplash.com/profile-fb-1464533812-a91a557e" +
                "646d.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=b9ff3604ece655dfc27052e941443856");
        mImageUrlsToDisplay.add("https://images.unsplash.com/profile-fb-1464533812-a91a557e" +
                "646d.jpg?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=" +
                "crop\\u0026h=32\\u0026w=32\\u0026s=b9ff3604ece655dfc27052e941443856");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Do nothing
        return false;
    }
}