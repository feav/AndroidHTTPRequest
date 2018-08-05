package com.mapossa.www.investir;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapossa.www.investir.dummy.DummyItem;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
//            DummyItem item = ItemDetailActivity;
//            arguments.putString("item_id",
//                    getIntent().getStringExtra("item_id"));
            final DummyItem mItem = DummyItem.ITEM_MAP.get(getIntent().getStringExtra("item_id"));

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNom());
            }
            View.OnClickListener call_action = new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+mItem.getPhone()));
                    if (ActivityCompat.checkSelfPermission(ItemDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
            };
            ((ImageButton) findViewById(R.id.callPerso)).setOnClickListener(call_action );
            ((ImageButton) findViewById(R.id.callPro)).setOnClickListener(call_action );
            if(mItem.isProfessional()){
                ((TextView) findViewById(R.id.nom)).setText(mItem.getNom());//contribuable
                ((TextView) findViewById(R.id.email)).setText(mItem.getEmail());//code_postal
                ((TextView) findViewById(R.id.ville)).setText(mItem.getVille());
                ((TextView) findViewById(R.id.phone)).setText(mItem.getPhone());
                ((TextView) findViewById(R.id.emailPro)).setText(mItem.getEmailPro());
                ((TextView) findViewById(R.id.nomPro)).setText(mItem.getNomPro());//nom
                ((TextView) findViewById(R.id.adresse)).setText(mItem.getAdresse());//adresse
            }else{
                ((LinearLayout)findViewById(R.id.list_pro)).setVisibility(LinearLayout.INVISIBLE);
                ((TextView) findViewById(R.id.nom)).setText(mItem.getNom());//contribuable
                ((TextView) findViewById(R.id.email)).setText(mItem.getEmail());//code_postal
                ((TextView) findViewById(R.id.ville)).setText(mItem.getVille());
                ((TextView) findViewById(R.id.phonePro)).setText(mItem.getPhone());
                ((TextView) findViewById(R.id.adresse)).setText(mItem.getAdresse());//adresse
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
