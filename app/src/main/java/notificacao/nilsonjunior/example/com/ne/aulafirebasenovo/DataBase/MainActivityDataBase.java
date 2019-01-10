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
        listView = (ListView) findViewById(R.id.listviewId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivityDataBase.this)
                        .setTitle("Deletando Registro")
                        .setMessage("Tem certeza que deseja deletar esse registro?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
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

    public void salvarDatabase(View view) {
        Random gerador = new Random();
        int id = gerador.nextInt(100);
        Aluno aluno = new Aluno(id, "Marcio Jr", "Artes", "3107383422");
        myRef.child("Alunos").child(String.valueOf(id))
                .setValue(aluno);


    }


}
