package androidprj.duke.vn.todotask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import androidprj.duke.vn.todotask.model.ModelTodo;
import androidprj.duke.vn.todotask.R;

/**
 * Created by datnt on 2/22/2016.
 */
public class AdapterTodo extends BaseAdapter {
    private Context mContext;
    private List<ModelTodo> mListTodo;
    Holder holder;

    public AdapterTodo(Context context, List<ModelTodo> listTodo) {
        mContext = context;
        mListTodo = listTodo;
    }

    @Override
    public int getCount() {
        return mListTodo != null && mListTodo.size() > 0 ? mListTodo.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mListTodo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null || view.getTag() == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_todo, viewGroup, false);

            holder = new Holder();

            holder.tvName = (TextView) view.findViewById(R.id.item_todo_tv_name);
            holder.tvPriority = (TextView) view.findViewById(R.id.item_todo_tv_priority);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        ModelTodo modelTodo = mListTodo.get(i);
        if(modelTodo != null) {
            holder.tvName.setText(modelTodo.getName());
            holder.tvPriority.setText(setPriorityValue(modelTodo.getPriority()));
        }

        return view;
    }

    private String setPriorityValue(int priority) {
        String value = "";
        switch(priority) {
            case 1:
                value = "High";
                break;
            case 2:
                value = "Medium";
                break;
            case 3:
                value = "Low";
                break;
            default:
                value = "Unknown";
                break;
        }

        return value;
    }

    public void updateAdapter(List<ModelTodo> listupdate) {
        mListTodo = listupdate;
        notifyDataSetChanged();
    }

    private static class Holder {
        TextView tvName, tvPriority;
    }
}
