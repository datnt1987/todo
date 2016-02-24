package androidprj.duke.vn.todotask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import androidprj.duke.vn.todotask.adapter.AdapterTodo;
import androidprj.duke.vn.todotask.database.DBHelper;
import androidprj.duke.vn.todotask.model.ModelTodo;
import androidprj.duke.vn.todotask.view.DlgFrgNewTask;

public class MainActivity extends FragmentActivity implements DlgFrgNewTask.EditNameDialogListener {
    private ImageView btnAdd;
    private ListView lvTodo;
    // Variables
    private FragmentManager fm;
    private List<ModelTodo> listTodo;
    private AdapterTodo adapterTodo;
    private int indexSelectedItem;
    // Database
    DBHelper todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initView();
    }

    private void initVariables() {
        todoDB = new DBHelper(this);
        listTodo = todoDB.getAllTodos();
        adapterTodo = new AdapterTodo(this, listTodo);
        fm = getSupportFragmentManager();
        indexSelectedItem = -1;
    }

    private void initView() {
        btnAdd = (ImageView) findViewById(R.id.iv_add);
        lvTodo = (ListView) findViewById(R.id.lv_todo);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        lvTodo.setAdapter(adapterTodo);
        lvTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                indexSelectedItem = i;
                if (indexSelectedItem >= 0 && indexSelectedItem < listTodo.size()) {
                    ModelTodo modelTodo = listTodo.get(indexSelectedItem);
                    Bundle dataToDialog = new Bundle();
                    dataToDialog.putString("Id", modelTodo.getId());
                    dataToDialog.putString("Name", modelTodo.getName());
                    dataToDialog.putInt("Priority", modelTodo.getPriority());
                    DlgFrgNewTask dlg = new DlgFrgNewTask();
                    dlg.setArguments(dataToDialog);
                    dlg.show(fm, "TODOLIST");
                }
                return false;
            }
        });
    }

    private void addTask() {
        DlgFrgNewTask dlg = new DlgFrgNewTask();
        dlg.show(fm, "ALO");
    }

    @Override
    public void onFinishEditDialog(Intent intentData) {
        if (intentData != null) {
            String index = intentData.getExtras().getString("Id");
            if (index == "") {
                ModelTodo modelTodo = new ModelTodo();
                modelTodo.setName(intentData.getExtras().getString("Name"));
                modelTodo.setPriority(intentData.getExtras().getInt("Priority"));
                listTodo.add(modelTodo);
                todoDB.insertTodo(modelTodo.getId(), modelTodo.getName(), modelTodo.getPriority());

            } else {
                int status = intentData.getExtras().getInt("Status");
                if (status == 2) {
                    ModelTodo updateTodo = listTodo.get(indexSelectedItem);
                    updateTodo.setName(intentData.getExtras().getString("Name"));
                    updateTodo.setPriority(intentData.getExtras().getInt("Priority"));
                    todoDB.updateTodo(updateTodo.getId(), updateTodo.getName(), updateTodo.getPriority());
                } else if (status == 3) {
                    ModelTodo deleteTodo = listTodo.get(indexSelectedItem);
                    todoDB.deleteTodo(deleteTodo.getId());
                    listTodo.remove(indexSelectedItem);
                }
            }

            adapterTodo.updateAdapter(listTodo);
        }
    }
}
