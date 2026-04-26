package com.cafeteria.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cafeteria.sistema.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
}