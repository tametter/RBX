package ch.talionis.rbx.screen.levels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.levels.LevelGroup;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.views.CircleAnimalView;
import ch.talionis.rbx.views.LevelsView;

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
        private CircleAnimalView circleImageView;
        private LevelsView levelsView;

        LevelsViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pentagon_number_view);
            levelsView = itemView.findViewById(R.id.levels_view);
        }

        public void setLevelGroup(LevelGroup levelGroup) {
            circleImageView.setImageResId(levelGroup.getGroupIconRes());
            levelsView.setLevelGroup(levelGroup);
        }
    }
}
