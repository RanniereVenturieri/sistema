package com.cafeteria.sistema; 

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cafeteria.sistema.entidades.Produtos; 
import com.cafeteria.sistema.repositories.ProdutosRepositories;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProdutosRepositories produtoRepository;

    public DataSeeder(ProdutosRepositories produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
 @Override
    public void run(String... args) throws Exception {

        if (produtoRepository.count() == 0) {
            System.out.println("🌱 Banco vazio! Semeando o cardápio oficial...");


            Produtos p1 = new Produtos();
            p1.setNome("Café Expresso");
            p1.setDescricao("Curto e intenso");
            p1.setPreco(4.5);

            Produtos p2 = new Produtos();
            p2.setNome("Cappuccino");
            p2.setDescricao("Café, leite e canela");
            p2.setPreco(7.0);

            Produtos p3 = new Produtos();
            p3.setNome("Pão de Queijo");
            p3.setDescricao("Quentinho e mineiro");
            p3.setPreco(1.5);

            Produtos p4 = new Produtos();
            p4.setNome("Bolo de Cenoura");
            p4.setDescricao("Com calda de chocolate");
            p4.setPreco(12.0);

            Produtos p5 = new Produtos();
            p5.setNome("Pão com ovo");
            p5.setDescricao("Ovinho no ponto e pão fres"); 
            p5.setPreco(3.5);

            Produtos p6 = new Produtos();
            p6.setNome("Suco de Laranja");
            p6.setDescricao("Natural da fruta");
            p6.setPreco(5.0);

            produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

            System.out.println("✅ Cardápio cadastrado com sucesso!");
        } else {
            System.out.println("☕ O cardápio já está no banco de dados. Tudo pronto!");
        }
    }
}