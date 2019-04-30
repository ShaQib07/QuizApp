package com.shakib.bdlabit.pmpquizprep.database;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shakib.bdlabit.pmpquizprep.Utils.FirebaseEndPoint;
import com.shakib.bdlabit.pmpquizprep.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DBRepo {

    Realm realm;

    public DBRepo(Realm realm) {
        this.realm = realm;
    }


    public RealmList<String> getAllPreviousMockName(String subName){
        RealmList<String> subjectName = new RealmList<>();
        RealmResults<MockDB> subjectDBS = realm.where(MockDB.class).equalTo("subjectName", subName).findAll();
        for (MockDB mockDB : subjectDBS) {
            subjectName.add(mockDB.getMockupName());
        }
        return subjectName;
    }

    public RealmList<MockDB> getAllPrevMock(String subName){
        RealmList<MockDB> mockList = new RealmList<>();

        RealmResults<MockDB> mockDBS = realm.where(MockDB.class).equalTo("subjectName", subName).findAll();
        mockList.addAll(mockDBS);

        return mockList;

    }


    public RealmResults<SubjectDB> getAllQuestion() {
        return realm.where(SubjectDB.class).findAllAsync();
    }

    public RealmList<String> getAllSubjectName() {
        RealmList<String> subjectName = new RealmList<>();
        RealmResults<SubjectDB> subjectDBS = getAllQuestion();
        for (SubjectDB subjectDB : subjectDBS) {
            subjectName.add(subjectDB.getSubName());
        }
        return subjectName;
    }



    public RealmList<QuestionDB> getSubjectWiseAllQuestion(String subjectName) {

        return realm.where(SubjectDB.class).equalTo("subName", subjectName).findFirst().getQuestionDBRealmList();
    }

    public RealmList<QuestionDB> getSubjectWiseRandomQuestion(String subjectName, int quantity) {
        List<Integer> selectedQuestionNumberList = new ArrayList<>();
        RealmList<QuestionDB> selectedQuestion = new RealmList<>();

        RealmList<QuestionDB> subjectWiseAllQuestion = getSubjectWiseAllQuestion(subjectName);

        if (subjectWiseAllQuestion.size() < quantity) {
            Toast.makeText(MyApplication.getInstance(), "Not enough question", Toast.LENGTH_LONG).show();
        } else if (subjectWiseAllQuestion.size() == quantity) {
            return selectedQuestion;
        }

        for (int i = 0; i < quantity; i++) {

            int x = new Random().nextInt((subjectWiseAllQuestion.size() - 1) + 1) + 1;
            if (!selectedQuestionNumberList.contains(x)) {
                selectedQuestionNumberList.add(x);
                selectedQuestion.add(subjectWiseAllQuestion.get(x));
            }

        }

        return selectedQuestion;
    }

    public DatabaseReference getAllDataFromFirebase() {

        realm.executeTransaction(realm -> {
            realm.where(SubjectDB.class).findAll().deleteAllFromRealm();
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//.child("items");
        ref.keepSynced(true);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();

                    Log.d("name", name);
                    SubjectDB subjectDB = new SubjectDB();
                    subjectDB.setSubName(name);
                    subjectDB.setQuestionDBRealmList(getQuestions(name));

                    realm.executeTransaction(realm -> {
                        realm.insert(subjectDB);
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

        return ref;

    }

    private RealmList<QuestionDB> getQuestions(String name) {

        final RealmList<QuestionDB> questionDBRealmList = new RealmList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(name).child(FirebaseEndPoint.QUESTION);
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          if (dataSnapshot.getValue() != null) {
                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                  QuestionDB recentSearchModelFirebase = dataSnapshot1.getValue(QuestionDB.class);
                                                  questionDBRealmList.add(recentSearchModelFirebase);
                                              }
                                          }
                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {
                                          Toast.makeText(MyApplication.getInstance(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                      }
                                  }

        );
        return questionDBRealmList;
    }

}
