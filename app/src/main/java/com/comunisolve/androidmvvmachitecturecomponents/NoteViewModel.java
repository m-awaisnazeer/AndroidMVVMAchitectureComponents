package com.comunisolve.androidmvvmachitecturecomponents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.comunisolve.androidmvvmachitecturecomponents.Room.Note;
import com.comunisolve.androidmvvmachitecturecomponents.Room.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    // AndroidViewModel is subclass of ViewModel, we can handle Context(application) to this
    // application is sub part of getApplicatonContext()
    //By using ViewModel we need to handle context to activity, we have reference to already destroed activity that is memory leakage.
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }
    // we need to create methods for Database operation, beacuse our activity does nat have directly access to Repository;
    // we are going to create raper methods.
    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAll(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
