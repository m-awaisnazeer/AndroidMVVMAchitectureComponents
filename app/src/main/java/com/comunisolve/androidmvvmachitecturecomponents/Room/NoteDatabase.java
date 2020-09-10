package com.comunisolve.androidmvvmachitecturecomponents.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //create instance of database by using singleton Method.
    public static synchronized NoteDatabase getInstance(Context context) {
        //synchronized = avoid multiple instance in multithreading

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() // if we update database version version in future , our app will not be crash and database will be create from scrach.
                    .addCallback(roomCallback) // this will added some nodes in our database when its created;
                    .build();

        }
        return instance;

    }

    // we want to popualte our Databas when created so we add some node in it.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        // called only one time when database is created.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));
            noteDao.insert(new Note("Title 4","Description 4",4));

            return null;
        }
    }
}
