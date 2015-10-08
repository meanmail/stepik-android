package org.stepic.droid.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.stepic.droid.R;
import org.stepic.droid.base.MainApplication;
import org.stepic.droid.core.IScreenManager;
import org.stepic.droid.model.Section;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> implements View.OnClickListener {
    private final static String SECTION_TITLE_DELIMETER = ". ";

    @Inject
    IScreenManager mScreenManager;

    private List<Section> mSections;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public SectionAdapter(List<Section> sections, Context mContext) {
        this.mSections = sections;
        this.mContext = mContext;

        MainApplication.component().inject(this);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;

    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.section_item, parent, false);
        v.setOnClickListener(this);
        return new SectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        Section section = mSections.get(position);

        String title = section.getTitle();
        int positionOfSection = section.getPosition();
        title = positionOfSection + SECTION_TITLE_DELIMETER + title;
        holder.sectionTitle.setText(title);

        String formattedBeginDate = section.getFormattedBeginDate();
        holder.startDate.setText(formattedBeginDate == "" ? "" :
                holder.beginDateString + " " + formattedBeginDate);

        String formattedSoftDeadline = section.getFormattedSoftDeadline();
        holder.softDeadline.setText(formattedSoftDeadline == "" ? "" :
                holder.softDeadlineString + " " + formattedSoftDeadline);

        String formattedHardDeadline = section.getFormattedHardDeadline();
        holder.hardDeadline.setText(formattedHardDeadline == "" ? "" :
                holder.hardDeadlineString + " " + formattedHardDeadline);
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = mRecyclerView.indexOfChild(v);
        if (itemPosition >= 0 && itemPosition < mSections.size()) {
            mScreenManager.showUnitsForSection(mContext, mSections.get(itemPosition));
        }
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.section_title)
        TextView sectionTitle;

        @Bind(R.id.start_date)
        TextView startDate;

        @Bind(R.id.soft_deadline)
        TextView softDeadline;

        @Bind(R.id.hard_deadline)
        TextView hardDeadline;

        @BindString(R.string.hard_deadline_section)
        String hardDeadlineString;
        @BindString(R.string.soft_deadline_section)
        String softDeadlineString;
        @BindString(R.string.begin_date_section)
        String beginDateString;


        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}