package com.example.noted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// this file needs some work to be done.

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    int lastPos = -1;
    private ArrayList<IndNote> indNoteArrayList;
    private Context context;
    private NoteCLickInterface noteClickInterface;

    public NotesAdapter(ArrayList<IndNote> indNoteArrayList, Context context, NoteCLickInterface noteClickInterface) {
        this.indNoteArrayList = indNoteArrayList;
        this.context = context;
        this.noteClickInterface = noteClickInterface;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        IndNote indNote = indNoteArrayList.get(position);
        holder.note_content.setText(indNote.getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteClickInterface.onNoteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView note_content;
        private ImageView note_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note_content = itemView.findViewById(R.id.note_string);
            note_img = itemView.findViewById(R.id.note_img);
        }
    }
    public interface NoteCLickInterface {
        void onNoteClick(int position);
    }
}
