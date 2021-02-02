package ch.talionis.rbx.screen.levels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.views.PentagonNumberView;

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
        holder.setPentagonNumber(position + 1);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    static class LevelsViewHolder extends RecyclerView.ViewHolder {
        private PentagonNumberView pentagonNumberView;

        LevelsViewHolder(@NonNull View itemView) {
            super(itemView);
            pentagonNumberView = itemView.findViewById(R.id.pentagon_number_view);
        }

        public void setPentagonNumber(int number) {
            pentagonNumberView.setText(number + "");
        }
    }
}
