package ch.talionis.rbx.screen.levels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.talionis.rbx.R;
import ch.talionis.rbx.levels.LevelGroup;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.views.CircleAnimalView;
import ch.talionis.rbx.views.StarRow;

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
        private StarRow starRow;
        private List<View> levelViews;

        LevelsViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pentagon_number_view);
            starRow = itemView.findViewById(R.id.star_row);

            levelViews = new ArrayList<>();
            levelViews.add(itemView.findViewById(R.id.level_one));
            levelViews.add(itemView.findViewById(R.id.level_two));
            levelViews.add(itemView.findViewById(R.id.level_three));
            levelViews.add(itemView.findViewById(R.id.level_four));
            levelViews.add(itemView.findViewById(R.id.level_five));
            levelViews.add(itemView.findViewById(R.id.level_six));
            levelViews.add(itemView.findViewById(R.id.level_seven));
            levelViews.add(itemView.findViewById(R.id.level_eight));
            levelViews.add(itemView.findViewById(R.id.level_nine));
            levelViews.add(itemView.findViewById(R.id.level_ten));
        }

        public void setLevelGroup(LevelGroup levelGroup) {
            circleImageView.setImageResId(levelGroup.getGroupIconRes());
            starRow.setSelected(levelGroup.getStars());

            for (int i = 0; i < levelViews.size(); i++) {
                levelViews.get(i).setVisibility(levelGroup.getNumberOfLevels() > i ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
}
