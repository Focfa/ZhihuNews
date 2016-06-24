package com.example.zhihunews.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Lxq on 2016/5/27.
 */
public class DB {

    public static void saveOrUpdate(Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
    }

    public static void saveOrUpdateAsync(Realm realm, final RealmObject realmObject) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmObject);
            }
        });
    }

    public static <T extends RealmObject> void saveList(Realm realm, List<T> realmObjects) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObjects);
        realm.commitTransaction();
    }

    public static void save(Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.copyToRealm(realmObject);
        realm.commitTransaction();
    }

    public static <T extends RealmObject> T getById(Realm realm, int id, Class<T> realmObjectClass) {
        return realm.where(realmObjectClass).equalTo("id", id).findFirst();
    }

    public static <T extends RealmObject> T getByUrl(Realm realm, String url, Class<T> realmObjectClass) {
        return realm.where(realmObjectClass).equalTo(Constants.URL, url).findFirst();
    }

    public static <T extends RealmObject> boolean isUrlExisted(Realm realm, String url, Class<T> realmObjectClass) {
        return getByUrl(realm, url, realmObjectClass) != null;
    }


    public static <T extends RealmObject> RealmResults<T> findAll(Realm realm, Class<T> realmObjectClass) {
        return realm.where(realmObjectClass).findAll();
    }

    public static <T extends RealmObject> RealmResults<T> findAllDateSorted(Realm realm, Class<T> realmObjectClass) {
        RealmResults<T> results = findAll(realm, realmObjectClass);
        results.sort(Constants.DATE, Sort.DESCENDING);
        return results;
    }

    public static <T extends RealmObject> RealmResults<T> findAllPrefixSorted(Realm realm, Class<T> realmObjectClass) {
        RealmResults<T> results = findAll(realm, realmObjectClass);
        results.sort(Constants.GA_PREFIX, Sort.DESCENDING);
        return results;
    }

    public static <T extends RealmObject> void deleteAllFromRealm(Realm realm, Class<T> realmObjectClass) {
        realm.beginTransaction();
        findAll(realm, realmObjectClass).deleteAllFromRealm();
        realm.commitTransaction();
    }
}
