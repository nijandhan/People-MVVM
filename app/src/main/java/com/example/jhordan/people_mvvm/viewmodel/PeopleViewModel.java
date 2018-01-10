/**
 * Copyright 2016 Erik Jhordan Rey. <p/> Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at <p/> http://www.apache.org/licenses/LICENSE-2.0 <p/> Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.example.jhordan.people_mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jhordan.people_mvvm.PeopleApplication;
import com.example.jhordan.people_mvvm.base.presenter.BasePresenter;
import com.example.jhordan.people_mvvm.data.ErrorResponse;
import com.example.jhordan.people_mvvm.data.PeopleFactory;
import com.example.jhordan.people_mvvm.data.PeopleResponse;
import com.example.jhordan.people_mvvm.data.PeopleService;
import com.example.jhordan.people_mvvm.model.People;
import com.example.jhordan.people_mvvm.view.PeopleView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PeopleViewModel extends ViewModel {

  private MutableLiveData<List<People>> peoples;

  private List<People> peopleList;
  private Context context;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public PeopleViewModel(@NonNull Context context) {

    this.context = context;
    this.peopleList = new ArrayList<>();
  }

  public LiveData<List<People>> getPeoplesList(){
      if(peoples == null){
        peoples = new MutableLiveData<>();
        fetchPeopleList();
      }

      return peoples;
  }

  public void fetchPeopleList() {

    PeopleApplication peopleApplication = PeopleApplication.create(context);
    PeopleService peopleService = peopleApplication.getPeopleService();

    Disposable disposable = peopleService.fetchPeople(PeopleFactory.RANDOM_USER_URL)
        .subscribeOn(peopleApplication.subscribeScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<PeopleResponse>() {
          @Override public void accept(PeopleResponse peopleResponse) throws Exception {
            peoples.setValue(peopleResponse.getPeopleList());
            //changePeopleDataSet(peopleResponse.getPeopleList());
            //if(getMvpView() != null) getMvpView().onPeopleListSuccess();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            //if(getMvpView() != null) getMvpView().onPeopleListFailure(((ErrorResponse)throwable).getErrorMessage());
          }
        });

    compositeDisposable.add(disposable);
  }

  private void changePeopleDataSet(List<People> peoples) {
    peopleList.addAll(peoples);
  }

  public List<People> getPeopleList() {
    return peopleList;
  }
}
