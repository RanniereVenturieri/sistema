package com.cafeteria.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafeteria.sistema.entidades.Pedido;
import com.cafeteria.sistema.entidades.Produtos;
import com.cafeteria.sistema.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    // Ponto 2 e 3: Buscar todos os pedidos para o relatório da cafeteria
    public List<Pedido> procurarTodos() {
        return repository.findAll();
    }

    // Buscar um pedido específico (útil para ver o status)
    public Pedido procurarId(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElse(null); // Retorna o pedido ou nulo se não encontrar
    }

    // Ponto 4: Adicionar pedido com uma mensagem de confirmação
    public String adicionarPedido(Pedido pedido) {
        repository.save(pedido);
        return "Pedido realizado com sucesso! ID: " + pedido.getId();
    }

  public Pedido atualizarPedido(Integer id, String novoStatus, List<Produtos> novosItens) {
    // Busca o pedido original ou dá erro se não existir (o 404 que vimos antes)
    Pedido pedidoExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    // Atualiza apenas o que mudou
    pedidoExistente.setStatus(novoStatus);
    pedidoExistente.setItens(novosItens);

    return repository.save(pedidoExistente);
}


// Ajuste para o DELETE
public boolean excluirPedido(Integer id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return true;
    }
    return false;
}
}