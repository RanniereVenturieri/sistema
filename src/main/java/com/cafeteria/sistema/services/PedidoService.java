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

    public List<Pedido> procurarTodos() {
        return repository.findAll();
    }

    public Pedido procurarId(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElse(null); 
    }

    public String adicionarPedido(Pedido pedido) {
        repository.save(pedido);
        return "Pedido realizado com sucesso! ID: " + pedido.getId();
    }

  public Pedido atualizarPedido(Integer id, String novoStatus, List<Produtos> novosItens) {
 
    Pedido pedidoExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

 
    pedidoExistente.setStatus(novoStatus);
    pedidoExistente.setItens(novosItens);

    return repository.save(pedidoExistente);
}

public boolean excluirPedido(Integer id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return true;
    }
    return false;
}
}