package com.cafeteria.sistema.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cafeteria.sistema.entidades.Pedido;
import com.cafeteria.sistema.entidades.Produtos;
import com.cafeteria.sistema.repositories.PedidoRepository;
import com.cafeteria.sistema.repositories.ProdutosRepositories;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
private ProdutosRepositories produtosRepository; // Note o 'p' minúsculo aqui!

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @PostMapping
public ResponseEntity<Pedido> criarPedido(@RequestBody Map<String, Object> payload) {
    Pedido novoPedido = new Pedido();
    
    // Pegando os dados do JSON que você mandou
    novoPedido.setCliente((String) payload.get("cliente"));
    novoPedido.setStatus((String) payload.get("status"));

    // Buscando os produtos pelos IDs [6, 5] que estão no seu JSON
    List<Integer> ids = (List<Integer>) payload.get("itensIds");
    List<Produtos> produtosEncontrados = produtosRepository.findAllById(ids);
    
    novoPedido.setItens(produtosEncontrados);

    Pedido salvo = pedidoRepository.save(novoPedido);
    return ResponseEntity.status(201).body(salvo);
}

// --- ATUALIZAR PEDIDO (PUT) CORRIGIDO ---
@PutMapping("/{id}")
public ResponseEntity<Pedido> atualizarPedido(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
    
    return pedidoRepository.findById(id).map(pedidoExistente -> {
        
        // 1. Atualiza os textos (Cliente e Status)
        if (payload.containsKey("cliente")) {
            pedidoExistente.setCliente((String) payload.get("cliente"));
        }
        if (payload.containsKey("status")) {
            pedidoExistente.setStatus((String) payload.get("status"));
        }

        // 2. A MÁGICA DOS ITENS: Busca os novos produtos no banco usando os IDs do JSON
        if (payload.containsKey("itensIds")) {
            @SuppressWarnings("unchecked")
            List<Integer> ids = (List<Integer>) payload.get("itensIds");
            List<Produtos> produtosEncontrados = produtosRepository.findAllById(ids);
            
            // Troca a lista velha pela lista nova
            pedidoExistente.setItens(produtosEncontrados);
        }
        
        // 3. Salva por cima e retorna os dados atualizados
        Pedido atualizado = pedidoRepository.save(pedidoExistente);
        return ResponseEntity.ok(atualizado);

    }).orElse(ResponseEntity.notFound().build()); // Retorna 404 se o pedido não existir
}

// --- DELETAR PEDIDO (DELETE) ---
@DeleteMapping("/{id}")
public ResponseEntity<Void> deletarPedido(@PathVariable Integer id) {
    if (!pedidoRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    
    pedidoRepository.deleteById(id);
    return ResponseEntity.noContent().build(); // Retorna 204
}

    @PostMapping("/{id}/comprovante")
    public ResponseEntity<String> uploadComprovante(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

            String nomeArquivo = "pedido_" + id + "_comprovante_" + file.getOriginalFilename();
            Path caminho = Paths.get("./comprovante/" + nomeArquivo);

            Files.createDirectories(caminho.getParent());
            Files.copy(file.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

            pedido.setComprovanteUrl(nomeArquivo);
            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Comprovante enviado!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro no arquivo");
        }
    }
}