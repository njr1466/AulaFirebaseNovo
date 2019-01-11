package notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.DataBase;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.DataBase.Aluno;
import notificacao.nilsonjunior.example.com.ne.aulafirebasenovo.R;

/**
 * Created by nilsonjunior on 10/01/19.
 */

public class AdapterAlunosPersonalizados extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Activity act;

    public AdapterAlunosPersonalizados(List<Aluno> cursos, Activity act) {
        this.alunos = cursos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater()
                .inflate(R.layout.activity_main_custom_list_view, parent, false);


        Aluno aluno = alunos.get(position);

        TextView id = (TextView)
                view.findViewById(R.id.textViewId);
        TextView nome = (TextView)
                view.findViewById(R.id.textViewNome);

        nome.setText(aluno.getNome());
        id.setText(""+aluno.getId());


        return view;
    }
}
