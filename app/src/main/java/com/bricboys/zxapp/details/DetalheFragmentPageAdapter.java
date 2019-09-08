package com.bricboys.zxapp.details;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.bricboys.zxapp.R;


public class DetalheFragmentPageAdapter extends FragmentPagerAdapter {
    private String mTabNames[] = new String [3];
    private String param;
    private String nome;

    public DetalheFragmentPageAdapter(FragmentManager fm, Context context, String parametro, String nome) {
        super(fm);
        this.param = parametro;
        this.nome = nome;
        //Log.e("Construtor", "Cheguei aqui: " + param);
        mTabNames[0] = context.getString(R.string.images);
        mTabNames[1] = context.getString(R.string.downloads);
        mTabNames[2] = context.getString(R.string.moreinfo);
    }

    @Override
    public Fragment getItem(int position) {
        //Log.e("getItem", "Cheguei aqui: " + param);
        if(position == 0){
            Bundle bundle = new Bundle();
            bundle.putString("parm", param);
            ImagesFragment i = new ImagesFragment();
            i.setArguments(bundle);
            return i;
        } else if(position==1) {
            Bundle bundle = new Bundle();
            bundle.putString("parm", param);
            bundle.putString("nome", nome);
            DownloadFragment d = new DownloadFragment();
            d.setArguments(bundle);
            return d;
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("parm", param);
            bundle.putString("nome", nome);
            MoreInfoFragment e = new MoreInfoFragment();
            e.setArguments(bundle);
            return e;
        }
    }

    @Override
    public int getCount() {
        //Log.e("getCount", "Cheguei aqui");
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Log.e("getPageTitle", "Cheguei aqui");
        return mTabNames[position];
    }
}
