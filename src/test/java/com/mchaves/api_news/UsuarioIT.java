package com.mchaves.api_news;

import com.mchaves.api_news.web.dto.UsuarioCreateDto;
import com.mchaves.api_news.web.dto.UsuarioResponseDto;
import com.mchaves.api_news.web.exception.ErroMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-create-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-drop-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class UsuarioIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void createUsuario_ComNameUsarnamePaswordValidos_RetornarUsuarioCriadoComStatus201(){

      UsuarioResponseDto usuarioResponseDto = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("mauricio","email01@email.com","123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

      assertThat(usuarioResponseDto).isNotNull();
      assertThat(usuarioResponseDto.getId()).isNotNull();
      assertThat(usuarioResponseDto.getName()).isNotNull();
      assertThat(usuarioResponseDto.getUsername()).isNotNull();
      assertThat(usuarioResponseDto.getName()).isEqualTo("mauricio");
      assertThat(usuarioResponseDto.getUsername()).isEqualTo("email01@email.com");
      assertThat(usuarioResponseDto.getRole()).isEqualTo("USUARIO");

    }

    @Test
    public void createUsuario_ComNamePaswordValidosEUsernameDuplicado_RetornarErrorMessage409(){

        ErroMessage erroMessage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("mauricio","teste01@email.com","123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        assertThat(erroMessage).isNotNull();
        assertThat(erroMessage.getStatus()).isEqualTo(409);



    }

    @Test
    public void createUsuario_ComNamePaswordValidosEUsernameInvalido_RetornarErrorMessage422(){

        ErroMessage erroMessage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("mauricio","teste01email.com","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        assertThat(erroMessage).isNotNull();
        assertThat(erroMessage.getStatus()).isEqualTo(422);



    }

    @Test
    public void createUsuario_ComNameNullUsernameEPaswordValidos_RetornarErrorMessage422(){

        ErroMessage erroMessage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("","email01@email.com","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        assertThat(erroMessage).isNotNull();
        assertThat(erroMessage.getStatus()).isEqualTo(422);



    }
}

