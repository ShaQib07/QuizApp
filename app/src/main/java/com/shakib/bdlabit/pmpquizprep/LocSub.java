package com.shakib.bdlabit.pmpquizprep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shakib.bdlabit.pmpquizprep.Adapter.RecyclerAdapter;
import com.shakib.bdlabit.pmpquizprep.Utils.FirebaseEndPoint;
import com.shakib.bdlabit.pmpquizprep.Utils.MyApplication;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.shakib.bdlabit.pmpquizprep.database.SubjectDB;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class LocSub extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerAdapter mAdapter;

    Realm realm;
    DBRepo dbRepo;

    List<String> subNameList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_sub);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        recyclerView = findViewById(R.id.sub_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);





        getAllDataFromFirebase() ;

       // loadSubjectList();


    }

    private void loadSubjectList() {
        TextView chooser = findViewById(R.id.chooser);
        chooser.setVisibility(View.VISIBLE);

        subNameList = dbRepo.getAllSubjectName();
        mAdapter = new RecyclerAdapter(subNameList);
        mAdapter.setOnItemClickListener((position, v) -> {
            SharePreferenceSingleton.getInstance(getApplicationContext()).saveString("subject", mAdapter.getItem(position));
            startActivity(new Intent(LocSub.this, Dashboard.class));
            finish();
        });


        recyclerView.setAdapter(mAdapter);


    }

    int i = 0;

    public DatabaseReference getAllDataFromFirebase() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Setting things up, please wait...");
        progressDialog.show();


        realm.executeTransaction(realm -> {
            realm.where(SubjectDB.class).findAll().deleteAllFromRealm();
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//.child("items");
        ref.keepSynced(true);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (DataSnapshot ds : dataSnapshot1.getChildren()) {
                    String name = ds.getKey();

                    Log.d("name", name);


                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(name).child(FirebaseEndPoint.QUESTION);
                    ref1.keepSynced(true);
                    ref1.addValueEventListener(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                                      if (dataSnapshot.getValue() != null) {
                                                          final RealmList<QuestionDB> questionDBRealmList = new RealmList<>();
                                                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                              QuestionDB recentSearchModelFirebase = dataSnapshot1.getValue(QuestionDB.class);
                                                              questionDBRealmList.add(recentSearchModelFirebase);
                                                          }
                                                          SubjectDB subjectDB = new SubjectDB();
                                                          subjectDB.setSubName(name);
                                                          subjectDB.setQuestionDBRealmList(questionDBRealmList);


                                                          realm.executeTransaction(realm -> {
                                                              realm.insert(subjectDB);
                                                              i++;
                                                          });

                                                          Log.d("namenhfgvjksd", dataSnapshot.getChildrenCount()+"  "+i);

                                                          if (i == dataSnapshot1.getChildrenCount()) {

                                                              loadSubjectList();
                                                              progressDialog.dismiss();
                                                              i=0;
                                                          }

                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(DatabaseError databaseError) {
                                                      Toast.makeText(MyApplication.getInstance(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                  }
                                              }

                    );
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

        return ref;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
