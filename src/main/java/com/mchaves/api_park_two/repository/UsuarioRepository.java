package com.mchaves.api_park_two.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mchaves.api_park_two.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
