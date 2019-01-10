package notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.DataBase;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.AutenticacaoGoogle.MainActivity;
import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.CustomListView.AdpaterAlunosPersonalizados;
import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.CustomListView.MainActivityCustomListView;
import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.R;

public class MainActivityDataBase extends AppCompatActivity  {

    FirebaseDatabase database;
    DatabaseReference myRef;
    final String TAG = "OK";
    ListView listView;
    List<Aluno> arrayOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_data_base);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        listView =  findViewById(R.id.listviewId);

        // LISTENER DO LISTVIEW
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivityDataBase.this)
                        .setTitle("Deletando Registro")
                        .setMessage("Tem certeza que deseja deletar esse registro?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {

                                    //MÉTODO QUE É ACIONADO COM O CLIQUE EM UM REGISTRO DO LISTVIEW
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Aluno alunoSelecionado = arrayOfUsers.get(position);

                                        DatabaseReference remove = FirebaseDatabase.getInstance().getReference("Alunos");
                                        remove.child(String.valueOf(alunoSelecionado.getId())).removeValue();
                                        //Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("não", null)
                        .show();
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {

            // MÉTODO QUE É ACIONADO QUANDO HÁ UMA CONEXÃO COM O
            // BANCO OU QUANDO HÁ UMA ALTERAÇÃO DE ALGUM DADO
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayOfUsers = new ArrayList<Aluno>();
                for (DataSnapshot snapshot : dataSnapshot.child("Alunos").getChildren()) {
                    Aluno aluno = snapshot.getValue(Aluno.class);
                    arrayOfUsers.add(aluno);
                }

                ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(MainActivityDataBase.this,
                        android.R.layout.activity_list_item, arrayOfUsers);


                AdpaterAlunosPersonalizados adapterAlunos = new AdpaterAlunosPersonalizados(arrayOfUsers,MainActivityDataBase.this);

                listView.setAdapter(adapterAlunos);



                Toast.makeText(getApplicationContext(), "alterou", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // MÉTODO QUE É SALVA OS DADOS NO BANCO NO FIREBASE
    public void salvarDatabase(View view) {
        Random gerador = new Random();
        int id = gerador.nextInt(100);
        EditText nome = (EditText) findViewById(R.id.editTextNomeAluno);

        if(!(nome.getText().toString().trim().isEmpty())) {
            Aluno aluno = new Aluno(id, nome.getText().toString(), "Artes", "3107383422");
            myRef.child("Alunos").child(String.valueOf(id))
                    .setValue(aluno);
            nome.setText("");

        }else{
            Toast.makeText(getApplicationContext(), "É necessário preencher o campo ", Toast.LENGTH_SHORT).show();
        }
    }


}
