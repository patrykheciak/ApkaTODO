package pl.patrykheciak.apkatodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.patrykheciak.apkatodo.db.AppDatabase;
import pl.patrykheciak.apkatodo.db.Task;

public class SublistFragment extends Fragment {
    public static final String ARG_ID_SUBLIST = "id_sublist";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sublist, container, false);
        mRecyclerView = v.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        Bundle args = getArguments();
        long sublistId = args.getLong(ARG_ID_SUBLIST);

        AppDatabase dbInstance = ((TodoApplication) getActivity().getApplication()).getDbInstance();
        List<Task> tasks = dbInstance.taskDao().tasksOfTasksubist(sublistId);

        mAdapter = new MyAdapter(tasks);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                launchEditTaskActivity(mAdapter.mDataset.get(position).getId_task());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return v;
    }

    private void launchEditTaskActivity(long taskId) {

        Intent i = new Intent(getContext(), NewTaskActivity.class);
        int id = (int) taskId;
        i.putExtra(NewTaskActivity.EXTRA_TASK_ID, id);
//        i.putExtra(
//                NewTaskActivity.EXTRA_SUBLIST_ID,
//                visibleSublistId);
        startActivityForResult(i, 2);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Task> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView taskName;
            public TextView taskNote;

            public ViewHolder(View v) {
                super(v);
                taskName = v.findViewById(R.id.task_name);
                taskNote = v.findViewById(R.id.task_note);
            }
        }

        public MyAdapter(List<Task> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_task, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.taskName.setText(mDataset.get(position).getName());
            holder.taskNote.setText(mDataset.get(position).getNote());

            // if priority then different background
            if (mDataset.get(position).getPrioritiy())
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}

