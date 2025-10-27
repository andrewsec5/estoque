package br.com.logistica_alimentos.estoque.api.controller;

import br.com.logistica_alimentos.estoque.api.dto.*;
import br.com.logistica_alimentos.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping("/cadastrar")
    public ApiResponse<CadastrarProdutoResponse> cadastrarProduto(@RequestBody @Valid CadastrarProdutoRequest request){
        return service.cadastrarProduto(request);
    }

    @PostMapping("/entrada")
    public ApiResponse<EntradaProdutoResponse> registrarEntrada(@RequestBody @Valid EntradaProdutoRequest request){
        return service.registrarEntrada(request);
    }

    @PostMapping("/saida")
    public ApiResponse<SaidaProdutoResponse> registrarSaida(@RequestBody @Valid SaidaProdutoRequest request){
        return service.registrarSaida(request);
    }

    @PostMapping("/descartar")
    public ApiResponse<DescarteProdutoResponse> descartarProduto(@RequestBody @Valid DescarteProdutoRequest request){
        return service.descartarProduto(request);
    }

    @GetMapping
    public ApiResponse<List<ProdutoResponse>> contagemProdutos(){
        return service.contagemProdutos();
    }

    @GetMapping("/vencimento")
    public ApiResponse<List<ProdutoResponse>> verificarValidade(){
        return service.verificarValidade();
    }

    @PostMapping("/ajustar")
    public ApiResponse<AjusteManualResponse> ajusteManual(@RequestBody @Valid ProdutoRequest request){
        return service.ajusteManual(request);
    }
}
