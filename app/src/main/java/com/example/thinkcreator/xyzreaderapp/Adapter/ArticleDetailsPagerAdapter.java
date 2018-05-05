package com.example.thinkcreator.xyzreaderapp.Adapter;

import android.database.Cursor;

import com.example.thinkcreator.xyzreaderapp.DataLoader.ArticleLoader;
import com.example.thinkcreator.xyzreaderapp.Fragment.ArticleTextFragment;

import java.lang.ref.WeakReference;

public class ArticleDetailsPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
private WeakReference<Cursor> mCursorWeakReference;

public ArticleDetailsPagerAdapter(android.support.v4.app.FragmentManager fm, Cursor cursor) {
        super(fm);
        mCursorWeakReference = new WeakReference<Cursor>(cursor);
        }

@Override
public android.support.v4.app.Fragment getItem(int position) {
        mCursorWeakReference.get().moveToPosition(position);
        return ArticleTextFragment.newInstance(mCursorWeakReference.get().getString(ArticleLoader.Query.BODY));
        }

@Override
public int getCount() {
        return mCursorWeakReference.get().getCount();
        }

}
