package com.cafeteria.sistema.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cafeteria.sistema.entidades.Produtos;
import com.cafeteria.sistema.services.ProdutosServices;

@RestController
@RequestMapping("/produtos")
public class ProdutosControllers {

    @Autowired
    private ProdutosServices service;

    @GetMapping
    public ResponseEntity<List<Produtos>> procurarTodos() {
        return ResponseEntity.ok(service.procurarTodos());
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadFoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Produtos produto = service.procurarId(id);
            if (produto == null) return ResponseEntity.notFound().build();

            String nomeArquivo = "produto_" + id + "_" + file.getOriginalFilename();
            Path caminho = Paths.get("./uploads/" + nomeArquivo);
            
            Files.createDirectories(caminho.getParent());
            Files.copy(file.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
            
            produto.setImagemUrl(nomeArquivo);
            service.atualizarProdutos(id, produto);

            return ResponseEntity.ok("Imagem enviada com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao salvar imagem");
        }
    }
}