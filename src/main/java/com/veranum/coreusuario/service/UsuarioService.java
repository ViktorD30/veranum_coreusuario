package com.veranum.coreusuario.service;

import com.veranum.coreusuario.dto.RolResponse;
import com.veranum.coreusuario.dto.UsuarioRequest;
import com.veranum.coreusuario.dto.UsuarioResponse;
import com.veranum.coreusuario.exception.BusinessException;
import com.veranum.coreusuario.exception.ResourceNotFoundException;
import com.veranum.coreusuario.model.Rol;
import com.veranum.coreusuario.model.Usuario;
import com.veranum.coreusuario.repository.RolRepository;
import com.veranum.coreusuario.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    private static final Logger log =
            LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public UsuarioResponse crearUsuario(UsuarioRequest request) {

        log.info(
                "Iniciando creación de usuario con RUT: {}",
                request.getRut()
        );

        validarRutYEmailDuplicados(
                request.getRut(),
                request.getEmail(),
                null
        );

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El rol especificado no existe"
                        )
                );

        Usuario usuario = new Usuario();

        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(rol);
        usuario.setBloqueado(false);
        usuario.setIntentosFallidos(0);

        Usuario guardado = usuarioRepository.save(usuario);

        log.info(
                "Usuario creado exitosamente con ID: {}",
                guardado.getId()
        );

        return convertirAResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> obtenerTodos() {

        log.info("Listando todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {

        log.info("Buscando usuario con ID: {}", id);

        Usuario usuario = obtenerEntidadPorId(id);

        return convertirAResponse(usuario);
    }

    public UsuarioResponse actualizarUsuario(
            Long id,
            UsuarioRequest request) {

        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = obtenerEntidadPorId(id);

        validarRutYEmailDuplicados(
                request.getRut(),
                request.getEmail(),
                id
        );

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El rol especificado no existe"
                        )
                );

        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(rol);

        Usuario actualizado = usuarioRepository.save(usuario);

        log.info(
                "Usuario actualizado correctamente con ID: {}",
                actualizado.getId()
        );

        return convertirAResponse(actualizado);
    }

    public void eliminarUsuario(Long id) {

        log.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = obtenerEntidadPorId(id);

        usuarioRepository.delete(usuario);

        log.info(
                "Usuario eliminado correctamente con ID: {}",
                id
        );
    }

    public UsuarioResponse bloquearUsuario(Long id) {

        log.info("Bloqueando usuario con ID: {}", id);

        Usuario usuario = obtenerEntidadPorId(id);

        usuario.setBloqueado(true);

        Usuario actualizado = usuarioRepository.save(usuario);

        log.info(
                "Usuario con ID {} ha sido bloqueado",
                id
        );

        return convertirAResponse(actualizado);
    }

    public UsuarioResponse desbloquearUsuario(Long id) {

        log.info("Desbloqueando usuario con ID: {}", id);

        Usuario usuario = obtenerEntidadPorId(id);

        usuario.setBloqueado(false);

        Usuario actualizado = usuarioRepository.save(usuario);

        log.info(
                "Usuario con ID {} ha sido desbloqueado",
                id
        );

        return convertirAResponse(actualizado);
    }

    @Transactional(readOnly = true)
    public boolean existePorRut(String rut) {

        log.info(
                "Verificando existencia de usuario con RUT: {}",
                rut
        );

        return usuarioRepository.existsByRut(rut);
    }

    @Transactional(readOnly = true)
    public Boolean validarUsuarioParaReserva(String rut) {

        log.info(
                "Validando usuario para reserva con RUT: {}",
                rut
        );

        Usuario usuario = usuarioRepository.findByRut(rut)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe usuario con RUT: " + rut
                        )
                );

        if (usuario.isBloqueado()) {

            throw new BusinessException(
                    "El usuario está bloqueado y no puede realizar reservas"
            );
        }

        if (!"CLIENTE".equalsIgnoreCase(
                usuario.getRol().getNombre())) {

            throw new BusinessException(
                    "Solo los usuarios con rol CLIENTE pueden realizar reservas"
            );
        }

        log.info(
                "Usuario habilitado para realizar reservas con RUT: {}",
                rut
        );

        return true;
    }

    @Transactional(readOnly = true)
    public List<RolResponse> listarRoles() {

        log.info("Listando roles del sistema");

        return rolRepository.findAll()
                .stream()
                .map(rol ->
                        new RolResponse(
                                rol.getId(),
                                rol.getNombre()
                        )
                )
                .collect(Collectors.toList());
    }

    private Usuario obtenerEntidadPorId(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con ID: " + id
                        )
                );
    }

    private void validarRutYEmailDuplicados(
            String rut,
            String email,
            Long idActual) {

        usuarioRepository.findByRut(rut)
                .ifPresent(usuario -> {

                    if (idActual == null
                            || !usuario.getId().equals(idActual)) {

                        throw new BusinessException(
                                "El RUT ya se encuentra registrado"
                        );
                    }
                });

        usuarioRepository.findByEmail(email)
                .ifPresent(usuario -> {

                    if (idActual == null
                            || !usuario.getId().equals(idActual)) {

                        throw new BusinessException(
                                "El email ya se encuentra registrado"
                        );
                    }
                });
    }

    private UsuarioResponse convertirAResponse(
            Usuario usuario) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getRut(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre(),
                usuario.isBloqueado(),
                usuario.getIntentosFallidos()
        );
    }
}
