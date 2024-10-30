package com.mchaves.api_news.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mchaves.api_news.entity.Usuario;
import com.mchaves.api_news.service.UsuarioService;
import com.mchaves.api_news.web.dto.UsuarioCreateDto;
import com.mchaves.api_news.web.dto.UsuarioResponseDto;
import com.mchaves.api_news.web.dto.UsuarioSenhaDto;
import com.mchaves.api_news.web.dto.mapper.UsuarioMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioContoller {

    private final UsuarioService usuarioService;


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id) {
        UsuarioResponseDto user = UsuarioMapper.toDto(usuarioService.findById(id));
        return ResponseEntity.ok(user);

    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create( @Valid @RequestBody  UsuarioCreateDto usuario) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto){
        usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<UsuarioResponseDto> usuarios = UsuarioMapper.toListDto(usuarioService.buscarTodos());
        return ResponseEntity.ok(usuarios);
    }

}
