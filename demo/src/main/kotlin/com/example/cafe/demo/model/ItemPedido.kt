package com.example.cafe.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "itens_pedido")
class ItemPedido(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JsonIgnore
    var pedido: Pedido? = null,

    @ManyToOne
    @JoinColumn(name = "produto_id")
    var produto: Produto? = null,

    var quantidade: Int = 1,
    var precoUnitario: BigDecimal = BigDecimal.ZERO
)