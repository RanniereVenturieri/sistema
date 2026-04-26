package com.cafeteria.sistema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafeteria.sistema.entidades.Produtos;
import com.cafeteria.sistema.repositories.ProdutosRepositories;

@Service
public class ProdutosServices {

    @Autowired
    private ProdutosRepositories repository;
    
    public List<Produtos> procurarTodos(){
        return repository.findAll();
    }
    public Produtos procurarId(Integer id){
        return repository.findById(id).get();
    }
    public String adicionarProdutos(Produtos produto){
        repository.save(produto);
        return "Produto adicionado com sucesso!";
    }

    public String atualizarProdutos(Integer id, Produtos produto){
        Produtos response = repository.findById(id).get();
        response.setNome(produto.getNome());
        response.setPreco(produto.getPreco());
        response.setDescricao(produto.getDescricao());
        repository.save(response);
        return "Produto atualizado com sucesso!";
    }

    public void deletarProdutos(Integer id) {
        // Aqui usamos o repositório que já está injetado no seu Service
        // para apagar o registro no banco de dados.
        repository.deleteById(id); 
    }

    public void excluirProdutos(Integer id) {
        // Verifica se o ID realmente existe no banco antes de deletar
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            // Em vez de estourar erro 500, você pode apenas não fazer nada
            // ou lançar uma mensagem personalizada
            System.out.println("Tentativa de excluir ID inexistente: " + id);
        }
    }
}
