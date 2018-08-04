package com.medexpertz.medexpertzdoctor.shankar.subtabs;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 12/10/2015.
 */
public class SubTabDetails {
    private String title;
    private String id;
    private Fragment fragmentClass;

    public SubTabDetails(String title, String id, Fragment fragmentClass) {
        this.title = title;
        this.fragmentClass = fragmentClass;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Fragment getFragment() {
        return fragmentClass;

    }
}


