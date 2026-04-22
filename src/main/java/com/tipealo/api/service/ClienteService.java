package com.tipealo.api.service;

import com.tipealo.api.dto.cliente.ClienteRequest;
import com.tipealo.api.dto.cliente.ClienteResponse;
import com.tipealo.api.entity.Cliente;
import com.tipealo.api.entity.User;
import com.tipealo.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserService userService;

    public List<ClienteResponse> getAll() {
        User user = userService.getCurrentUser();
        return clienteRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse getById(Long id) {
        User user = userService.getCurrentUser();
        Cliente cliente = clienteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return toResponse(cliente);
    }

    public ClienteResponse create(ClienteRequest request) {
        User user = userService.getCurrentUser();
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setNotas(request.getNotas());
        cliente.setUser(user);
        return toResponse(clienteRepository.save(cliente));
    }

    public ClienteResponse update(Long id, ClienteRequest request) {
        User user = userService.getCurrentUser();
        Cliente cliente = clienteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(request.getNombre());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setNotas(request.getNotas());
        return toResponse(clienteRepository.save(cliente));
    }

    public void delete(Long id) {
        User user = userService.getCurrentUser();
        Cliente cliente = clienteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setTelefono(cliente.getTelefono());
        response.setDireccion(cliente.getDireccion());
        response.setNotas(cliente.getNotas());
        response.setCreatedAt(cliente.getCreatedAt());
        return response;
    }
}
