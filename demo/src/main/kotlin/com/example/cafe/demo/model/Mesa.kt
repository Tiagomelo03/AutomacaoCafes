package com.example.cafe.demo.model

import jakarta.persistence.*
import java.util.UUID

@Entity
class Mesa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var numero: Int = 0,
    
    // O código dinâmico gerado para o QR Code
    var uuidSessao: String = UUID.randomUUID().toString() 
)