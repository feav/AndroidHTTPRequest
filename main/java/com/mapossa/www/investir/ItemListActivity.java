package com.mapossa.www.investir;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapossa.www.investir.dummy.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    private final int jsoncode = 1;
    private ArrayList<DummyItem> playersModelArrayList;
    ProgressDialog dialog;
    View recyclerView;
    static boolean list_professionnel = true;
    static String url ="";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    protected void updateInvestisseursList(int index){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    parseJson();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        final Context context = getApplicationContext();
        TabLayout tabs = (TabLayout) findViewById(R.id.tabBar);
        tabs.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener(){

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if(tab.getPosition()==0){
                            ItemListActivity.list_professionnel = true;
                            ItemListActivity.url ="REPLACE_BY_FIRST_URL_GET_JSON_DATA";
                        }else{
                            ItemListActivity.list_professionnel = false;
                            ItemListActivity.url ="REPLACE_BY_SECOND_URL_GET_JSON_DATA";

                        }
                        try {
                            parseJson();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                }
        );
        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        if(DummyItem.ITEM_MAP.size()==0){
            try {
                parseJson();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(DummyItem.ITEM_MAP.size()>0){
            ((RecyclerView) recyclerView).setAdapter(new SimpleItemRecyclerViewAdapter(this, playersModelArrayList, mTwoPane));
        }
    }


    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private  List<DummyItem> mValues = new ArrayList<DummyItem>();
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DummyItem item = (DummyItem) view.getTag();
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString("item_id", item.id);
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra("item_id", item.id);

                            context.startActivity(intent);
                        }
                    }
                };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view ;
            if(list_professionnel)
                view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            else
                view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_content_part, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getNom().trim().charAt(0)+"");
            holder.mContentView.setText(mValues.get(position).getNom());
            holder.mEmailView.setText(mValues.get(position).getEmail());
            holder.mVilleView.setText(mValues.get(position).getVille());
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            if(mValues==null)
                return 0;
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mEmailView;
            final TextView mVilleView;
            final FrameLayout roundView;
            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mEmailView = (TextView) view.findViewById(R.id.email);
                mVilleView = (TextView) view.findViewById(R.id.ville);
                roundView = (FrameLayout) view.findViewById(R.id.round_frame);

            }
        }
    }

    private void parseJson() throws IOException, JSONException {

        if (!AndyUtils.isNetworkAvailable(ItemListActivity.this)) {
            Toast.makeText(ItemListActivity.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(ItemListActivity.this);
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(ItemListActivity.url);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        CharSequence text = response.toString();

        AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
        if (serviceCode == jsoncode) {
            playersModelArrayList= new ArrayList<>();
            DummyItem.ITEM_MAP = new HashMap<String,DummyItem>();
            JSONArray dataArray = null;
            try {
                dataArray = new JSONArray(response);
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    DummyItem playersModel = new DummyItem();
                    playersModel.setId(dataobj.getString("id"));
                    playersModel.setNom(dataobj.getString("nom"));
                    playersModel.setEmail(dataobj.getString("email"));
                    playersModel.setVille(dataobj.getString("ville"));
                    playersModel.setPhone(dataobj.getString("telephone"));
                    playersModel.setAdresse(dataobj.getString("adresse"));

                    if(ItemListActivity.list_professionnel) {
                        playersModel.setProfessional(true);
                        playersModel.setNomPro(dataobj.getString("nom"));
                        playersModel.setNom(dataobj.getString("contribuable"));
                        playersModel.setEmail(dataobj.getString("code_postal"));
                        playersModel.setEmailPro(dataobj.getString("email"));
                    }else
                        playersModel.setProfessional(false);
                    playersModelArrayList.add(playersModel);
                    DummyItem.ITEM_MAP.put(playersModel.getId(),playersModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ((RecyclerView) recyclerView).setAdapter(new SimpleItemRecyclerViewAdapter(this, playersModelArrayList, mTwoPane));

        }else{
        }
    }
}
