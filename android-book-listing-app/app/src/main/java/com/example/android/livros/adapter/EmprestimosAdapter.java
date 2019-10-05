package com.example.android.livros.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.livros.R;
import com.example.android.livros.model.Emprestimo;

import java.util.List;

public class EmprestimosAdapter extends RecyclerView.Adapter<EmprestimosAdapter.EmprestimoViewHolder>{

    List<Emprestimo> emprestimos;
    private Spinner spinner;
    private ArrayAdapter<String> dataAdapter;
    Context context;

    public EmprestimosAdapter(Context context,List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
        this.context = context;
    }

    @Override
    public EmprestimosAdapter.EmprestimoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.items_emprestimos, parent, false);

        return new EmprestimosAdapter.EmprestimoViewHolder(itemView);
    }

    @Override public int getItemCount() {
        return emprestimos.size();
    }

    @Override
    public void onBindViewHolder(EmprestimosAdapter.EmprestimoViewHolder holder , int position) {
        holder.codigo.setText(emprestimos.get(position).getCodigo());
        holder.dataInicial.setText(emprestimos.get(position).getData_emprestimo().split( "T" )[0]);
        holder.dataFinal.setText(emprestimos.get(position).getDataDevolucao().split( "T" )[0]);
        dataAdapter = new ArrayAdapter<>( context,R.layout.support_simple_spinner_dropdown_item, emprestimos.get( position ).getEmprestimos());
        holder.tituloLivro.setAdapter( dataAdapter );
    }

    public class EmprestimoViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo;
        public Spinner tituloLivro;
        public TextView dataInicial;
        public TextView dataFinal;

        public EmprestimoViewHolder(View view) {
            super(view);
            codigo = view.findViewById(R.id.cod_emprestimo);
            tituloLivro = view.findViewById(R.id.spinner_livros);
            dataInicial = view.findViewById(R.id.data_inicial);
            dataFinal = view.findViewById(R.id.data_final);
        }
    }
}
