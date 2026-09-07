package com.example.cafe.demo.repository

import com.example.cafe.demo.model.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long> {
    // O Spring gera o SQL automaticamente para procurar apenas produtos ativos e de uma categoria
    fun findByCategoriaIdAndDisponivelTrue(categoriaId: Long): List<Produto>
}