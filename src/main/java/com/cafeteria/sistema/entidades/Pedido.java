package com.cafeteria.sistema.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Pedido {

    private String comprovanteUrl;
    @Column(nullable = true)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cliente;

    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(nullable = false)
    private String status; 

    @ManyToMany
    @JoinTable(
        name = "pedido_produtos", 
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produtos> itens = new ArrayList<>();

    public Pedido() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Produtos> getItens() { return itens; }
    public void setItens(List<Produtos> itens) { this.itens = itens; }
    public String getComprovanteUrl() { return comprovanteUrl; }
    public void setComprovanteUrl(String comprovanteUrl) { this.comprovanteUrl = comprovanteUrl; }
}