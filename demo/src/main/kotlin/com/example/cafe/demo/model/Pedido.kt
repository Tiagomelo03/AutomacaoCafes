package com.example.cafe.demo.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
class Pedido(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    var mesa: Mesa? = null,

    var estado: String = "PENDENTE", // PENDENTE, PREparacao, ENTREGUE, CANCELADO
    var valorTotal: BigDecimal = BigDecimal.ZERO,
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL])
    var itens: List<ItemPedido> = mutableListOf()
)