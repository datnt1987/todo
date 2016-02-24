package androidprj.duke.vn.todotask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidprj.duke.vn.todotask.R;
import androidprj.duke.vn.todotask.database.DBHelper;

/**
 * Created by datnt on 2/22/2016.
 */
public class DlgFrgNewTask extends DialogFragment implements View.OnClickListener {
    private final int SAVE = 1;
    private final int UPDATE = 2;
    private final int DELETE = 3;

    private EditText etName;
    private Button btnSave, btnUpdate, btnDelete;
    private Spinner spPriority;
    private LinearLayout llNew, llUpdate;
    // Variables;
    private String strPriority, id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dlg_new_task, container);

        etName = (EditText) view.findViewById(R.id.dlg_new_task_et_name);
        btnSave = (Button) view.findViewById(R.id.dlg_new_task_btn_add);
        btnUpdate = (Button) view.findViewById(R.id.dlg_new_task_btn_update);
        btnDelete = (Button) view.findViewById(R.id.dlg_new_task_btn_delete);
        spPriority = (Spinner) view.findViewById(R.id.dlg_new_task_sp_priority);
        llNew = (LinearLayout) view.findViewById(R.id.dlg_new_task_ll_create_new);
        llUpdate = (LinearLayout) view.findViewById(R.id.dlg_new_task_ll_update);

        btnSave.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("High");
        categories.add("Medium");
        categories.add("Low");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spPriority.setAdapter(dataAdapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPriority = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getDialog().setTitle("New task");

        // get data
        Bundle receivedData = new Bundle();
        receivedData = getArguments();
        if(receivedData != null) {
            etName.setText(receivedData.getString("Name"));
            id = receivedData.getString("Id");
            llNew.setVisibility(View.GONE);
            llUpdate.setVisibility(View.VISIBLE);
        } else {
            id = "";
            llNew.setVisibility(View.VISIBLE);
            llUpdate.setVisibility(View.GONE);
        }

        return view;
    }

    private int getPriority(String priorityValue) {
        if ("High".equals(priorityValue)) {
            return 1;
        } else if ("Medium".equals(priorityValue)) {
            return 2;
        } else if ("Low".equals(priorityValue)) {
            return 3;
        } else {
            return -1;
        }
    }

    private void handleData(int status) {
        // Passing data
        Intent dataIntent = new Intent();
        Bundle dataBundle = new Bundle();
        String name = etName.getText().toString();
        if(!TextUtils.isEmpty(name)) {
            dataBundle.putString("Name", name);
            dataBundle.putInt("Priority", getPriority(strPriority));
            dataBundle.putString("Id", id);
            dataBundle.putInt("Status", status);
            dataIntent.putExtras(dataBundle);

            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(dataIntent);

            dismiss();
        }
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(Intent intentData);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSave) {
            handleData(SAVE);
        } else if (view == btnUpdate) {
            handleData(UPDATE);
        } else if (view == btnDelete) {
            handleData(DELETE);
        }
    }
}
