package com.mchaves.api_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mchaves.api_news.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
