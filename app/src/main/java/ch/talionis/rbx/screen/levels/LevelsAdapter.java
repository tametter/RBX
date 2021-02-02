package ch.talionis.rbx.screen.levels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelsViewHolder> {
    private final LayoutInflater layoutInflater;

    public LevelsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LevelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LevelsViewHolder(layoutInflater.inflate(R.layout.view_levels, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class LevelsViewHolder extends RecyclerView.ViewHolder {
        public LevelsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
