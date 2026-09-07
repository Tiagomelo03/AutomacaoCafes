package com.example.cafe.demo.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var nome: String = "",
    var descricao: String = "",
    var preco: BigDecimal = BigDecimal.ZERO,
    var disponivel: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    var categoria: Categoria? = null
)