package com.example.android.books;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.books.model.Emprestimo;

import java.util.List;

public class EmprestimosAdapter extends RecyclerView.Adapter<EmprestimosAdapter.EmprestimoViewHolder>{

    List<Emprestimo> emprestimos;

    public EmprestimosAdapter(List emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public EmprestimosAdapter.EmprestimoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_emprestimos, parent, false);

        return new EmprestimosAdapter.EmprestimoViewHolder(itemView);
    }

    @Override public int getItemCount() {
        return emprestimos.size();
    }

    @Override
    public void onBindViewHolder(EmprestimosAdapter.EmprestimoViewHolder holder , int position) {
        holder.codigo.setText(emprestimos.get(position).getCodigo());
        holder.tituloLivro.setText(emprestimos.get(position).getEmprestimos().toString());
        holder.dataInicial.setText(emprestimos.get(position).getData_emprestimo());
        holder.dataFinal.setText(emprestimos.get(position).getDataDevolucao() );
    }

    public class EmprestimoViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo;
        public TextView tituloLivro;
        public TextView dataInicial;
        public TextView dataFinal;

        public EmprestimoViewHolder(View view) {
            super(view);
            codigo = view.findViewById(R.id.cod_emprestimo);
            tituloLivro = view.findViewById(R.id.titulo_emprestimo);
            dataInicial = view.findViewById(R.id.data_inicial);
            dataFinal = view.findViewById(R.id.data_final);
        }
    }
}
