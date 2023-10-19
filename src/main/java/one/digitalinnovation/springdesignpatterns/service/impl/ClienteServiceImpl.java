package one.digitalinnovation.springdesignpatterns.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.digitalinnovation.springdesignpatterns.model.Cliente;
import one.digitalinnovation.springdesignpatterns.model.ClienteRepository;
import one.digitalinnovation.springdesignpatterns.model.Endereco;
import one.digitalinnovation.springdesignpatterns.model.EnderecoRepository;
import one.digitalinnovation.springdesignpatterns.service.ClienteService;
import one.digitalinnovation.springdesignpatterns.service.ViaCepService;

@Component
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (!cliente.isPresent()) {
            throw new Error("Cliente inexistente");
        }
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBD = clienteRepository.findById(id);
        if (!clienteBD.isPresent()) {
            throw new Error("Cliente inexistente");
        }
        salvarClienteComCep(cliente);

    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
