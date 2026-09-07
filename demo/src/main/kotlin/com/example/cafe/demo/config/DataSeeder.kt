package com.example.cafe.demo.config

import com.example.cafe.demo.model.Categoria
import com.example.cafe.demo.model.Mesa
import com.example.cafe.demo.model.Produto
import com.example.cafe.demo.repository.CategoriaRepository
import com.example.cafe.demo.repository.MesaRepository
import com.example.cafe.demo.repository.ProdutoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
class DataSeeder {

    @Bean
    fun carregarDados(
        categoriaRepo: CategoriaRepository,
        produtoRepo: ProdutoRepository,
        mesaRepo: MesaRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            // Só insere se a base de dados estiver vazia (evita duplicados ao reiniciar o servidor)
            if (categoriaRepo.count() == 0L) {
                
                // 1. Criar Categorias
                val bebidas = categoriaRepo.save(Categoria(nome = "Bebidas", ordemExibicao = 1))
                val snacks = categoriaRepo.save(Categoria(nome = "Snacks", ordemExibicao = 2))

                // 2. Criar Produtos associados às categorias
                produtoRepo.save(Produto(nome = "Café Expresso", descricao = "Lote premium", preco = BigDecimal("1.00"), categoria = bebidas))
                produtoRepo.save(Produto(nome = "Fino (Super Bock)", descricao = "Cerveja de pressão 20cl", preco = BigDecimal("1.50"), categoria = bebidas))
                produtoRepo.save(Produto(nome = "Tosta Mista", descricao = "Pão saloio com fiambre e queijo", preco = BigDecimal("3.50"), categoria = snacks))

                // 3. Criar Mesas para teste
                mesaRepo.save(Mesa(numero = 1))
                mesaRepo.save(Mesa(numero = 2))
                mesaRepo.save(Mesa(numero = 3))

            }
        }
    }
}