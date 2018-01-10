package com.example.jhordan.people_mvvm.view;

import com.example.jhordan.people_mvvm.base.view.MvpView;
import com.example.jhordan.people_mvvm.model.People;

import java.util.List;

/**
 * Created by nijandhanl on 1/9/18.
 */

public interface PeopleView extends MvpView{
    void onPeopleListSuccess();
    void onPeopleListFailure(String errorMessage);
}
