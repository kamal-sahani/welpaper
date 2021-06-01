  package com.example.logcatdemo.welpaper;

          import android.content.DialogInterface;
          import android.os.Bundle;
          import android.view.Menu;
          import android.view.MenuItem;
          import android.view.View;
          import android.widget.AbsListView;
          import android.widget.EditText;

          import androidx.annotation.NonNull;
          import androidx.appcompat.app.AlertDialog;
          import androidx.appcompat.app.AppCompatActivity;
               import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

          import com.android.volley.AuthFailureError;
          import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
          import java.util.HashMap;
          import java.util.List;
          import java.util.Map;

  public class MainActivity extends AppCompatActivity {

      private   RecyclerView recyclerView;
      private List<wellpaperModel>wellpaperModelList;
      private wellpaperAdapter wellpaperAdapter;
      int pageNumber = 1;
      Boolean isScroling = false;
      int crountItems,totalItems,scrolloutItems;
      String url = "https://api.pexels.com/v1/curated/?page="+pageNumber+"&per_page=80";

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate (savedInstanceState);
          setContentView (R.layout.activity_main);

          recyclerView = findViewById (R.id.recylerview);
          wellpaperModelList = new ArrayList<> ();
          wellpaperAdapter= new wellpaperAdapter (this, wellpaperModelList);
          recyclerView.setAdapter (wellpaperAdapter);
          final GridLayoutManager gridLayoutManager = new GridLayoutManager (this,2);
          recyclerView.setLayoutManager (gridLayoutManager);



          recyclerView.addOnScrollListener (new RecyclerView.OnScrollListener () {

              @Override
              public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                  super.onScrollStateChanged (recyclerView, newState);


                  if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                      isScroling = true;
                  }
              }



              @Override
              public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                  super.onScrolled (recyclerView, dx, dy);

                  crountItems= gridLayoutManager.getChildCount ();
                  totalItems = gridLayoutManager.getItemCount ();
                  scrolloutItems =gridLayoutManager.findFirstVisibleItemPosition ();

                  if (isScroling&&(crountItems+scrolloutItems==totalItems)){
                      isScroling = false;
                      fatchwelpaper ();

                  }
              }
          });

          fatchwelpaper ();


      }

      private void fatchwelpaper(){
          StringRequest request = new StringRequest (Request.Method.GET,url,
                  new Response.Listener<String> () {
                      @Override
                      public void onResponse(String response) {

                          try {
                              JSONObject jsonObject = new JSONObject (response);
                              JSONArray jsonArray =  jsonObject.getJSONArray ("photos");
                              int langth = jsonArray.length ();

                              for (int i=0; i<langth;i++){
                                  JSONObject object = jsonArray.getJSONObject (i);

                                  int id = object.getInt ("id");
                                  JSONObject objectimage = object.getJSONObject ("src");
                                  String origanalUrl = objectimage.getString ("original");
                                  String mediumUrl = objectimage.getString ("medium");

                                  wellpaperModel wellpaperModel = new wellpaperModel (id,origanalUrl,mediumUrl);
                                  wellpaperModelList.add (wellpaperModel);
                              }
                              wellpaperAdapter.notifyDataSetChanged ();
                              pageNumber++;



                          }catch (JSONException e){



                          }

                      }
                  }, new Response.ErrorListener () {
              @Override
              public void onErrorResponse(VolleyError error) {

              }
          }){

              @Override
              public Map<String, String> getHeaders() throws AuthFailureError {

                  Map<String,String> parmas = new HashMap<> ();
                  parmas.put ("Authorization","563492ad6f917000010000014bd732451a1e402495c4ce159491d2d5");
                  return parmas;
              }
          };



          RequestQueue requestQueue = Volley.newRequestQueue (getApplicationContext ());

          requestQueue.add (request);

      }


      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater ().inflate (R.menu.search,menu);
          return super.onCreateOptionsMenu (menu);
      }

      @Override
      public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          if (item.getItemId ()==R.id.search){
              AlertDialog.Builder atrt =new  AlertDialog.Builder(this);
              final EditText editText = new EditText (this);
              editText.setTextAlignment (View.TEXT_ALIGNMENT_CENTER);

             atrt.setMessage ("Enter Category e.g.Nature");
             atrt.setMessage ("Search Wallpaper");

             atrt.setView (editText);

             atrt.setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     String query  = editText.getText ().toString ().toLowerCase ();
                     url ="https://api.pexels.com/v1/search/?page="+pageNumber+"&per_page=80&query="+query;
                     wellpaperModelList.clear ();
                     fatchwelpaper ();

                 }
             });


             atrt.setNegativeButton ("No", new DialogInterface.OnClickListener () {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });

             atrt.show ();
          }
          return super.onOptionsItemSelected (item);
      }
  }