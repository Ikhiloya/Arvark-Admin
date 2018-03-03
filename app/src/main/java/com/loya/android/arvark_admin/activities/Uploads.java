package com.loya.android.arvark_admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.loya.android.arvark_admin.R;
import com.loya.android.arvark_admin.adapters.EquipmentAdapter;
import com.loya.android.arvark_admin.models.Equipment;

import java.util.ArrayList;

public class Uploads extends AppCompatActivity {


    private RecyclerView mRecycler;
    private EquipmentAdapter mAdapter;
    private ArrayList<Equipment> equipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        equipments = new ArrayList<>();
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_a, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_b, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_c, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_d, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_e, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));
        equipments.add(new Equipment("Tripper", "Buldozer", R.drawable.cat_a, "$1500", "$800", "500kg", "Tripper Machine is a very good machine..."));

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mAdapter = new EquipmentAdapter(this, equipments);
        mRecycler.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Uploads.this, Post.class));
            }
        });
    }

}
