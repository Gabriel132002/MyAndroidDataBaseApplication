package com.example.mydatabaseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewShowData;
    EditText editTextName, editTextEmail, editTextCellPhone, editTextId;
    Button btnSave, btnDelete, btnClear;
    UserDAO userDAO = new UserDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayListNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.custom_list_view, arrayListNames);

        listViewShowData = findViewById(R.id.mainActivity_listView);
        editTextName = findViewById(R.id.mainActivity_editText_name);
        editTextEmail = findViewById(R.id.mainActivity_editText_email);
        editTextCellPhone = findViewById(R.id.mainActivity_editText_cellPhone);
        editTextId = findViewById(R.id.mainActivity_editText_id);
        btnSave = findViewById(R.id.linearLayoutButton_save);
        btnDelete = findViewById(R.id.linearLayoutButton_delete);
        btnClear = findViewById(R.id.linearLayoutButton_clear);

        listViewShowData.setAdapter(adapter);
        listUsers();

        listViewShowData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                String[] parts = item.split("-");
                String cod = parts[0].trim();
                User user = userDAO.getUser(Integer.parseInt(cod));
                editTextId.setText(String.valueOf(user.getCod()));
                editTextName.setText(String.valueOf(user.getName()));
                editTextEmail.setText(String.valueOf(user.getEmail()));
                editTextCellPhone.setText(String.valueOf(user.getCellPhone()));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cod = editTextId.getText().toString();
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextCellPhone.getText().toString();
                if (name.isEmpty()) {
                    editTextName.setError("Campo obrigatório");
                } else if (email.isEmpty()) {
                    editTextEmail.setError("Campo obrigatório");
                } else if (phone.isEmpty()) {
                    editTextCellPhone.setError("Campo obrigatório");
                } else if (cod.isEmpty()) {
                    userDAO.addUser(new User(name, email, phone));
                    Toast.makeText(MainActivity.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    clearFields();
                    listUsers();
                } else {
                    userDAO.updateUser(new User(Integer.parseInt(cod), name, phone, email));
                    Toast.makeText(MainActivity.this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    clearFields();
                    listUsers();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cod = editTextId.getText().toString();
                if (cod.isEmpty()) {
                    editTextName.setError("Nenhuma pessoa encontrada");
                } else {
                    User user = new User();
                    user.setCod(Integer.parseInt(cod));
                    userDAO.deleteUser(user);
                    Toast.makeText(MainActivity.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                    clearFields();
                    listUsers();
                }
            }
        });

    }

    private void listUsers() {
        ArrayList<String> arrayListNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.custom_list_view, arrayListNames);

        List<User> users = userDAO.getUsers();
        arrayListNames = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.custom_list_view, arrayListNames);
        listViewShowData.setAdapter(adapter);

        for (User u :
                users) {
            arrayListNames.add(u.getCod() + " - " + u.getName());
            adapter.notifyDataSetChanged();
        }
    }

    private void clearFields() {
        editTextId.setText("");
        editTextName.setText("");
        editTextEmail.setText("");
        editTextCellPhone.setText("");
    }
}