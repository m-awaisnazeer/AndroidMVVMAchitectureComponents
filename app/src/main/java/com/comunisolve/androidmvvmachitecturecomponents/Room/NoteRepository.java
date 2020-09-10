package com.comunisolve.androidmvvmachitecturecomponents.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
// Repository is just a werehouse of our data that is fetch from room Database or from RESTFULL APIs Services, VIEWMODEL doesn't know where the data comes from? it just access from ViewModel

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    //construcor to intialize these values
    public NoteRepository(Application application) { //application is subpart of context
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao(); // return instance of our db
        allNotes = noteDao.getAllNotes();
    }

    /* these four methods run of main thread, Room doesn't allow us to run on Main Thread.
    It will freee our app.
    To avoid this. we will create different Asynctask for each Method.
     */
    public void insert(Note note) {
        new InserNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() { //LiveData always run on Background Thread
        return allNotes;
    }

    private static class InserNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InserNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
