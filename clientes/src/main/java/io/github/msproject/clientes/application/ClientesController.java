package io.github.msproject.clientes.application;


import io.github.msproject.clientes.application.representation.ClienteSaveRequest;
import io.github.msproject.clientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClienteService clienteService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente cliente = request.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .query("cpf={cpf}")
                            .buildAndExpand(cliente.getCpf())
                            .toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping("cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = clienteService.getByCPF(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }


}
