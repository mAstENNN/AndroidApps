package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Movie> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        arrayList.add(new Movie(R.drawable.stranger_l, R.drawable.stranger_h, "Will Ferrell, Emma Thompson, Maggie Gyllenhaal, Dustin Hoffman", "Stranger than Fiction", "https://www.sonypictures.com/movies/strangerthanfiction", "https://en.wikipedia.org/wiki/Stranger_than_Fiction_(2006_film)","https://www.amazon.com/Stranger-than-Fiction-Will-Ferrell/dp/B000NTHT6G"));
        arrayList.add(new Movie(R.drawable.matrix_l, R.drawable.matrix_h, "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "Matrix", "https://www.imdb.com/title/tt0133093/", "https://en.wikipedia.org/wiki/The_Matrix","https://www.amazon.com/gp/video/detail/amzn1.dv.gti.ed68472f-1421-417d-800b-0cfdb21db71c?autoplay=0&ref_=atv_cf_strg_wb"));
        arrayList.add(new Movie(R.drawable.maverick_h, R.drawable.maverick_l, "Tom Cruise, Jennifer Connelly, Miles Teller", "Top Gun: Maverick", "https://www.imdb.com/title/tt1745960/", "https://en.wikipedia.org/wiki/Top_Gun:_Maverick","https://www.amazon.com/gp/video/detail/amzn1.dv.gti.e6867686-3989-4e51-ad7b-19ff5edea2f4?autoplay=0&ref_=atv_cf_strg_wb"));
        arrayList.add(new Movie(R.drawable.dune_h, R.drawable.dune_l, "Timothée Chalamet, Rebecca Ferguson, Zendaya", "Dune", "https://www.imdb.com/title/tt1160419/", "https://en.wikipedia.org/wiki/Dune_(2021_film)","https://www.amazon.com/Dune-Timoth%C3%A9e-Chalamet/dp/B09LJXY4PH"));
        arrayList.add(new Movie(R.drawable.fastx_h, R.drawable.fastx_l, "Vin Diesel, Michelle Rodriguez, Jason Statham", "Fast X", "https://www.imdb.com/title/tt5433140/", "https://en.wikipedia.org/wiki/Fast_X","https://www.amazon.com/gp/video/detail/amzn1.dv.gti.e4bc6fae-9603-4bad-8431-de39c93fdd6d?autoplay=0&ref_=atv_cf_strg_wb"));
        arrayList.add(new Movie(R.drawable.deadpool_h, R.drawable.deadpool_l, "Ryan Reynolds, Morena Baccarin, T.J. Miller", "Deadpool", "https://www.imdb.com/title/tt1431045/", "https://en.wikipedia.org/wiki/Deadpool","https://www.disneyplus.com/movies/deadpool/3Kh13Lrb0Pnv"));

        MovieAdapter movieAdapter = new MovieAdapter(this, R.layout.list_row, arrayList);
        listView.setAdapter(movieAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = arrayList.get(position);
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", selectedMovie.getDetailsUrl());
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Movie selectedMovie = arrayList.get(position);
        int itemId = item.getItemId();

        if (itemId == R.id.menu_full_poster) {
            Intent intentPoster = new Intent(MainActivity.this, FullPosterActivity.class);
            intentPoster.putExtra("ImageResourceId", selectedMovie.getHighResImageId());
            intentPoster.putExtra("url", selectedMovie.getDetailsUrl());
            startActivity(intentPoster);
            return true;

        } else if (itemId == R.id.menu_wikipedia) {
            Intent intentWiki = new Intent(MainActivity.this, WikipediaActivity.class);
            intentWiki.putExtra("wikiUrl", selectedMovie.getWikipediaUrl());
            startActivity(intentWiki);
            return true;

        } else if (itemId == R.id.menu_streaming) {
            Intent intentServices = new Intent(MainActivity.this, StreamingServicesActivity.class);
            intentServices.putExtra("streamUrl", selectedMovie.getStreamUrl());
            startActivity(intentServices);
            return true;

        } else {  return super.onContextItemSelected(item);}

    }

}
