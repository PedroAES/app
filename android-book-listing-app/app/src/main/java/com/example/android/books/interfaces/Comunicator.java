package com.example.android.books.interfaces;

import android.support.v4.app.Fragment;

public interface Comunicator {
    void openFragment(int id, Fragment fragment);

    void replaceFragment(int id, Fragment fragment);

    void removeFragment(int id);
}
