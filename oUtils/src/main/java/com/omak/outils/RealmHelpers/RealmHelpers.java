package com.omak.outils.RealmHelpers;

import android.content.Context;

import com.omak.outils.HelperFunctions;
import com.omak.outils.firebaseNotification.RealmNotificationModel;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class RealmHelpers {

    Context context;
    Realm realm;
    public Integer nextNotificationId;

    public RealmHelpers(Context context) {
        this.context = context;
        realm = HelperFunctions.getRealm("messages", context);
    }

    public boolean getBooleanFlag(String flagName) {
        RealmQuery<RealmFlags> query = realm.where(RealmFlags.class).equalTo("key", flagName);
        RealmFlags realmFlags = query.findFirst();

        if (realmFlags != null) {
            return realmFlags.getBooleanValue();
        }

        return false;
    }

    public void setBooleanFlag(final String flagName, final boolean value) {
        RealmQuery<RealmFlags> query = realm.where(RealmFlags.class).equalTo("key", flagName);
        final RealmFlags isUpdatedFlag = query.findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (isUpdatedFlag != null) {
                    isUpdatedFlag.setBooleanValue(value);
                } else {
                    final RealmFlags realmFlags = new RealmFlags();
                    realmFlags.setKey(flagName);
                    realmFlags.setBooleanValue(value);
                    realm.insertOrUpdate(realmFlags);
                }
            }
        });
    }

    public int getNotificationId(HashMap<String, String> notiData) {
        RealmNotificationModel.createAndInsert(context, notiData);
        realm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number currentIdNum = realm.where(RealmNotificationModel.class).max("id");
                        nextNotificationId = (currentIdNum == null) ? 1 : currentIdNum.intValue();
                    }
                });

        return nextNotificationId;
    }

    public <T extends RealmObject> RealmResults<T> getUniqueFieldValuesFromRealm(String field, Class<T> clazz) {
        RealmQuery uniqueFieldQuery = realm.where(clazz).distinct(field);
        uniqueFieldQuery.notEqualTo(field, "");
        RealmResults<T> uniqueValueProjects = uniqueFieldQuery.findAll();
        return uniqueValueProjects;
    }


    public <T extends RealmObject> RealmResults<T> getFromRealm(String field, String value, Class<T> clazz) {
        return realm.where(clazz).equalTo(field, value).findAll();
    }

    public <T extends RealmObject> RealmResults<T> getFromRealm(String field, Integer value, Class<T> clazz) {
        return realm.where(clazz).equalTo(field, value).findAll();
    }

    public <T extends RealmObject> RealmResults<T> getFromRealm(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public <T extends RealmObject> T getSingleFromRealm(String field, String value, Class<T> clazz) {
        return realm.where(clazz).equalTo(field, value).findFirst();
    }

    public <T extends RealmObject> T getSingleFromRealm(String field, Integer value, Class<T> clazz) {
        return realm.where(clazz).equalTo(field, value).findFirst();
    }

    public <T extends RealmObject> void deleteFromRealm(String field, Integer value, Class<T> clazz) {
        final RealmResults<T> foundRows = realm.where(clazz).equalTo(field, value).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    foundRows.deleteAllFromRealm();
                } catch (RealmPrimaryKeyConstraintException e) {
                    HelperFunctions.tost(context, "Local DB Failure!");
                }
            }
        });
    }

    public <T extends RealmObject> void deleteFromRealm(String field, String value, Class<T> clazz) {
        final RealmResults<T> foundRows = realm.where(clazz).equalTo(field, value).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    foundRows.deleteAllFromRealm();
                } catch (RealmPrimaryKeyConstraintException e) {
                    HelperFunctions.tost(context, "Local DB Failure!");
                }
            }
        });
    }

}
