package ch.talionis.rbx.screen.levels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.levels.LevelGroup;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.views.PentagonView;

import static ch.talionis.rbx.activities.ApplicationUtils.getLevelManager;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelsViewHolder> {
    private final LayoutInflater layoutInflater;
    private final LevelManager levelManager;

    public LevelsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        levelManager = getLevelManager(context);
    }

    @NonNull
    @Override
    public LevelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LevelsViewHolder(layoutInflater.inflate(R.layout.view_levels, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelsViewHolder holder, int position) {
        holder.setLevelGroup(levelManager.getGroup(position));
    }

    @Override
    public int getItemCount() {
        return levelManager.getNumberOfGroups();
    }

    static class LevelsViewHolder extends RecyclerView.ViewHolder {
        private PentagonView pentagonView;
        private ImageView animalView;

        LevelsViewHolder(@NonNull View itemView) {
            super(itemView);
            pentagonView = itemView.findViewById(R.id.pentagon_number_view);
            animalView = itemView.findViewById(R.id.animal_view);
        }

        public void setLevelGroup(LevelGroup levelGroup) {
            animalView.setImageResource(levelGroup.getGroupIconRes());
        }
    }
}
