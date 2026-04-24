package com.cafeteria.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafeteria.sistema.entidades.Produtos;

@Repository
public interface  ProdutosRepositories extends JpaRepository<Produtos, Integer> {
    
}
