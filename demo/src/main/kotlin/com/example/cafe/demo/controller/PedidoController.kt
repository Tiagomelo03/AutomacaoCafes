package com.example.cafe.demo.controller

import com.example.cafe.demo.model.ItemPedido
import com.example.cafe.demo.model.Pedido
import com.example.cafe.demo.repository.MesaRepository
import com.example.cafe.demo.repository.PedidoRepository
import com.example.cafe.demo.repository.ProdutoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/pedidos")
class PedidoController(
    private val pedidoRepo: PedidoRepository,
    private val produtoRepo: ProdutoRepository,
    private val mesaRepo: MesaRepository,
    private val messagingTemplate: SimpMessagingTemplate // 1. Injetar o template do WebSocket
) {

    data class ItemPedidoRequest(val produtoId: Long, val quantidade: Int)
    data class CriarPedidoRequest(val mesaId: Long, val itens: List<ItemPedidoRequest>)

    @PostMapping
    fun criarPedido(@RequestBody request: CriarPedidoRequest): ResponseEntity<Any> {
        val mesaOpt = mesaRepo.findById(request.mesaId)
        if (mesaOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("erro" to "Mesa não encontrada"))
        }

        val pedido = Pedido().apply {
            mesa = mesaOpt.get()
            estado = "PENDENTE"
        }

        var total = BigDecimal.ZERO
        val itensPedido = mutableListOf<ItemPedido>()

        for (itemReq in request.itens) {
            val produtoOpt = produtoRepo.findById(itemReq.produtoId)
            if (produtoOpt.isPresent) {
                val produto = produtoOpt.get()
                val item = ItemPedido().apply {
                    this.pedido = pedido
                    this.produto = produto
                    this.quantidade = itemReq.quantidade
                    this.precoUnitario = produto.preco
                }
                total = total.add(produto.preco.multiply(BigDecimal(itemReq.quantidade)))
                itensPedido.add(item)
            }
        }

        if (itensPedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("erro" to "O carrinho está vazio ou contém produtos inválidos"))
        }

        pedido.itens = itensPedido
        pedido.valorTotal = total

        val pedidoSalvo = pedidoRepo.save(pedido)

        messagingTemplate.convertAndSend("/topic/pedidos", pedidoSalvo)

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo)
    }

    @GetMapping
    fun listarPedidos(): List<Pedido> {
        return pedidoRepo.findAll()
    }
}