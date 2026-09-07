package com.example.cafe.demo.controller

import com.example.cafe.demo.model.Categoria
import com.example.cafe.demo.model.Mesa
import com.example.cafe.demo.model.Produto
import com.example.cafe.demo.repository.CategoriaRepository
import com.example.cafe.demo.repository.MesaRepository
import com.example.cafe.demo.repository.ProdutoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class MenuController(
    private val categoriaRepo: CategoriaRepository,
    private val produtoRepo: ProdutoRepository,
    private val mesaRepo: MesaRepository
) {

    // 1. Endpoint para ir buscar todas as categorias
    @GetMapping("/categorias")
    fun listarCategorias(): List<Categoria> {
        return categoriaRepo.findAll()
    }

    // 2. Endpoint para ir buscar os produtos de uma categoria específica
    @GetMapping("/categorias/{id}/produtos")
    fun listarProdutosPorCategoria(@PathVariable id: Long): List<Produto> {
        return produtoRepo.findByCategoriaIdAndDisponivelTrue(id)
    }

    // 3. Endpoint para validar o QR Code da mesa através do UUID da sessão
    @GetMapping("/mesa/{uuid}")
    fun validarMesa(@PathVariable uuid: String): ResponseEntity<Mesa> {
        val mesa = mesaRepo.findByUuidSessao(uuid)
        return if (mesa.isPresent) {
            ResponseEntity.ok(mesa.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }
}