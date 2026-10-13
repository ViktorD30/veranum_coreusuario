package com.veranum.coreusuario.controller;

import com.veranum.coreusuario.dto.ApiResponse;
import com.veranum.coreusuario.dto.UsuarioRequest;
import com.veranum.coreusuario.dto.UsuarioResponse;
import com.veranum.coreusuario.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> crearUsuario(
            @Valid @RequestBody UsuarioRequest request) {

        UsuarioResponse usuario =
                usuarioService.crearUsuario(request);

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Usuario creado con éxito",
                        false,
                        usuario
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> obtenerTodos() {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Lista de usuarios obtenida correctamente",
                        false,
                        usuarioService.obtenerTodos()
                );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> obtenerPorId(
            @PathVariable Long id) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Detalle del usuario",
                        false,
                        usuarioService.obtenerPorId(id)
                );

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Usuario actualizado correctamente",
                        false,
                        usuarioService.actualizarUsuario(id, request)
                );

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminarUsuario(
            @PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Usuario eliminado correctamente",
                        false,
                        null
                );

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}/bloquear")
    public ResponseEntity<ApiResponse<Object>> bloquearUsuario(
            @PathVariable Long id) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Usuario bloqueado correctamente",
                        false,
                        usuarioService.bloquearUsuario(id)
                );

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}/desbloquear")
    public ResponseEntity<ApiResponse<Object>> desbloquearUsuario(
            @PathVariable Long id) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Usuario desbloqueado correctamente",
                        false,
                        usuarioService.desbloquearUsuario(id)
                );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/existe/{rut}")
    public ResponseEntity<ApiResponse<Object>> verificarSiExisteRut(
            @PathVariable String rut) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Validación de existencia realizada",
                        false,
                        usuarioService.existePorRut(rut)
                );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/validar-reserva/{rut}")
    public ResponseEntity<ApiResponse<Object>> validarUsuarioParaReserva(
            @PathVariable String rut) {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Usuario habilitado para realizar reservas",
                        false,
                        usuarioService.validarUsuarioParaReserva(rut)
                );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<Object>> listarRoles() {

        ApiResponse<Object> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Lista de roles obtenida correctamente",
                        false,
                        usuarioService.listarRoles()
                );

        return ResponseEntity.ok(respuesta);
    }
}
