package com.rujirakongsomran.roomdatabasenoteapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rujirakongsomran.roomdatabasenoteapp.databinding.ListRowMainBinding;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // Initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    private static final int CELL_TYPE_REGULAR_ITEM = 0;
    private static final int CELL_TYPE_DIALOG = 1;

    // Create Constructor

    public MainAdapter(List<MainData> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        return new ViewHolder(ListRowMainBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {
        // Initialize main data
        MainData data = dataList.get(position);
        // Initialize database
        database = RoomDB.getInstance(context);
        // Set text on text view
        holder.listRowMainBinding.tvText.setText(data.getText());

        holder.listRowMainBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());

                // Get Id
                final int sId = d.getId();

                // Get Text
                String sText = d.getText();

                // Create dialog
                final Dialog dialog = new Dialog(context);

                // Set content view
                dialog.setContentView(R.layout.dialog_update);

                // Initial width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Initial height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Set Layout
                dialog.getWindow().setLayout(width, height);
                // Show dialog
                dialog.show();

                // Initialize and assign variable
                final EditText etText = dialog.findViewById(R.id.etText);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

                // Set text on edit text
                etText.setText(sText);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Dismiss Dialog
                        dialog.dismiss();

                        // Get updated text from edit text
                        String uText = etText.getText().toString().trim();
                        // Update text in database
                        database.mainDao().update(sId, uText);
                        // Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();

                    }
                });

            }
        });


        holder.listRowMainBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());

                // Delete text from database
                database.mainDao().delete(d);

                // Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ListRowMainBinding listRowMainBinding;

        public ViewHolder(ListRowMainBinding binding) {
            super(binding.getRoot());
            this.listRowMainBinding = binding;
        }
    }
}
