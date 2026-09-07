package com.example.cafe.demo.model

import jakarta.persistence.*

@Entity
class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    var nome: String = "",
    var ordemExibicao: Int = 0
)