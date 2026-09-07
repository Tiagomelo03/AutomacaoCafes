package com.example.cafe.demo.repository

import com.example.cafe.demo.model.Mesa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MesaRepository : JpaRepository<Mesa, Long> {
    // Essencial para validar se o link do QR Code (Sessão) ainda é válido
    fun findByUuidSessao(uuidSessao: String): Optional<Mesa>
}