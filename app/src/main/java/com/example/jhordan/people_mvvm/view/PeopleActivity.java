/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jhordan.people_mvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jhordan.people_mvvm.R;
import com.example.jhordan.people_mvvm.data.PeopleFactory;
import com.example.jhordan.people_mvvm.model.People;
import com.example.jhordan.people_mvvm.viewmodel.PeopleViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleActivity extends AppCompatActivity {

  private PeopleViewModel peopleViewModel;
  private PeopleAdapter mPeopleAdapter;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_people)
  RecyclerView listPeople;
  @BindView(R.id.progress_people)
  ProgressBar progress_people;
  @BindView(R.id.label_status)
  TextView label_status;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.people_activity);
    ButterKnife.bind(this);
    peopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
    peopleViewModel.getPeoplesList().observe(this, new Observer<List<People>>() {
      @Override
      public void onChanged(@Nullable List<People> people) {
        mPeopleAdapter.setPeopleList(people);
        progress_people.setVisibility(View.GONE);
        listPeople.setVisibility(View.VISIBLE);
        label_status.setText(getString(R.string.default_loading_people));
      }
    });
    setSupportActionBar(toolbar);
    setupListPeopleView();
  }

  private void setupListPeopleView() {
    mPeopleAdapter = new PeopleAdapter();
    listPeople.setAdapter(mPeopleAdapter);
    listPeople.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    //peopleViewModel.detachView();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_github) {
      startActivityActionView();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void startActivityActionView() {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PeopleFactory.PROJECT_URL)));
  }

/*  @Override
  public void onPeopleListSuccess() {

  }

  @Override
  public void onPeopleListFailure(String errorMessage) {
    progress_people.setVisibility(View.GONE);
    listPeople.setVisibility(View.GONE);
    label_status.setVisibility(View.VISIBLE);
    label_status.setText(getString(R.string.error_loading_people));
  }*/

  public void onClickFabLoad(View view) {
    initializeViews();
    peopleViewModel.fetchPeopleList();
  }

  public void initializeViews() {
    label_status.setVisibility(View.GONE);
    listPeople.setVisibility(View.GONE);
    progress_people.setVisibility(View.VISIBLE);
  }

/*  @Override
  public Context getContext() {
    return this;
  }*/

}
