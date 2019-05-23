package com.walhalla.fwdumper;

import android.util.SparseArray;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.MobileAds;

import nl.walhalla.domain.repository.AdvertRepository;
import nl.walhalla.domain.repository.from_internet.AdvertAdmobRepository;
import nl.walhalla.domain.repository.from_internet.AdvertConfig;

public class Application extends eu.chainfire.libsuperuser.Application {


    public static final AdvertConfig M_AC = new AdvertConfig() {

        @Override
        public String application_id() {
            return "ca-app-pub-5111357348858303~1254826132";
        }

        @Override
        public SparseArray<String> banner_ad_unit_id() {
            SparseArray<String> array = new SparseArray<>();
            array.put(R.id.bottom_container, "ca-app-pub-5111357348858303/2564038379");//block-list-bottom-banner
            array.put(R.id.bottom_container02, "ca-app-pub-5111357348858303/5329802512");//cp-bottom-banner
            return array;
        }

        @Override
        public String interstitial_ad_unit_id() {
            return null;
        }
    };


    public AdvertRepository getmAdvertRepository() {
        if (mAdvertRepository == null) {
            mAdvertRepository = AdvertAdmobRepository.getInstance(M_AC);
        }
        return mAdvertRepository;
    }

    private static AdvertRepository mAdvertRepository;


    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //io.fabric.sdk.android.Fabric.with(this, new Crashlytics());
    }
}
