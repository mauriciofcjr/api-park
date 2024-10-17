package com.mchaves.api_park_two.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mchaves.api_park_two.entity.Usuario;
import com.mchaves.api_park_two.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {       
        return usuarioRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
            );
    }

    @Transactional
    public void editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = findById(id);
        if(!senhaAtual.equals(user.getPassword())){
            throw new RuntimeException("Senhas não conferem!");
        }
        user.setPassword(novaSenha);        
    }

}
