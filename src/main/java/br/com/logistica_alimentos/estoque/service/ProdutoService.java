package br.com.logistica_alimentos.estoque.service;

import br.com.logistica_alimentos.estoque.api.dto.*;
import br.com.logistica_alimentos.estoque.config.Enum.MotivoDescarte;
import br.com.logistica_alimentos.estoque.config.anotacao.LogSucesso;
import br.com.logistica_alimentos.estoque.exception.BusinessException;
import br.com.logistica_alimentos.estoque.mapper.*;
import br.com.logistica_alimentos.estoque.model.Catalogo;
import br.com.logistica_alimentos.estoque.model.Descarte;
import br.com.logistica_alimentos.estoque.model.Produto;
import br.com.logistica_alimentos.estoque.repository.CatalogoRepository;
import br.com.logistica_alimentos.estoque.repository.DescarteRepository;
import br.com.logistica_alimentos.estoque.repository.ProdutoRepository;
import com.mongodb.DuplicateKeyException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CatalogoRepository catalogoRepository;
    private final DescarteRepository descarteRepository;
    private final CadastrarProdutoMapper cadastrarProdutoMapper;
    private final EntradaProdutoMapper entradaProdutoMapper;
    private final SaidaProdutoMapper saidaProdutoMapper;
    private final DescarteProdutoMapper descarteProdutoMapper;
    private final ProdutoMapper produtoMapper;
    private final AjusteManualMapper ajusteManualMapper;

    @Value("${estoque.alerta-validade-dias}")
    private long diasParaAviso;

    @LogSucesso(mensagem = "Produto cadastrado com sucesso")
    public ApiResponse<CadastrarProdutoResponse> cadastrarProduto(CadastrarProdutoRequest request) {

        Catalogo produto = cadastrarProdutoMapper.toEntity(request);

        if (catalogoRepository.existsByNome(request.nome())) {
            throw new IllegalArgumentException("Produto já cadastrado");
        }
        try {
            catalogoRepository.save(produto);

            CadastrarProdutoResponse dataResponse = cadastrarProdutoMapper.toResponse(produto);

            return ApiResponse.success("Produto cadastrado com sucesso", dataResponse);

        } catch (DuplicateKeyException ex) {
            throw new BusinessException("PRODUTO_DUPLICADO", "Produto já existente");
        }
    }

    @Transactional
    @LogSucesso(mensagem = "Entrada de produto registrada com sucesso")
    public ApiResponse<EntradaProdutoResponse> registrarEntrada(EntradaProdutoRequest request) {

        verificarCadastro(request.nome());

        Produto produto = atualizarQuantidade(true, request.nome(), request.validade(), request.origem(), request.quantidade());

        EntradaProdutoResponse dataResponse = entradaProdutoMapper.toResponse(produto);

        return ApiResponse.success("Entrada registrada com sucesso", dataResponse);
    }

    @Transactional
    @LogSucesso(mensagem = "Saida de produto registrada com sucesso")
    public ApiResponse<SaidaProdutoResponse> registrarSaida(SaidaProdutoRequest request) {

        verificarCadastro(request.nome());

        Produto produto = atualizarQuantidade(false, request.nome(), request.validade(), request.origem(), request.quantidade());

        SaidaProdutoResponse dataResponse = saidaProdutoMapper.toResponse(produto);

        return ApiResponse.success("Saida registrada com sucesso", dataResponse);
    }

    @Transactional
    @LogSucesso(mensagem = "Produto descartado com sucesso")
    public ApiResponse<DescarteProdutoResponse> descartarProduto(DescarteProdutoRequest request) {

        verificarCadastro(request.nome());

        atualizarQuantidade(false, request.nome(), request.validade(), request.origem(), request.quantidade());

        Descarte produtoDescartado = criarProdutoDescartado(request.nome(), request.quantidade(), request.validade(), request.origem(), request.motivo());

        DescarteProdutoResponse dataResponse = descarteProdutoMapper.toResponse(produtoDescartado);

        return ApiResponse.success("Descarte registrado com sucesso", dataResponse);
    }

    @Transactional(readOnly = true)
    @LogSucesso(mensagem = "Contagem de produtos realizada com sucesso")
    public ApiResponse<List<ProdutoResponse>> contagemProdutos() {

        List<Produto> listaProdutos = produtoRepository.findAll();

        if (listaProdutos.isEmpty()) {
            return ApiResponse.error("Nenhum produto encontrado", "NAO_ENCONTRADO");
        }

        List<ProdutoResponse> dataResponse = listaProdutos.stream()
                .map(produtoMapper::toResponse)
                .toList();

        return ApiResponse.success("Contagem realizada com sucesso", dataResponse);
    }

    @Transactional(readOnly = true)
    @LogSucesso(mensagem = "Verificação de validade realizada com sucesso")
    public ApiResponse<List<ProdutoResponse>> verificarValidade() {

        if (diasParaAviso <= 0) {
            throw new BusinessException("CONFIG_INVALIDA", "Parâmetro de dias para aviso inválido.");
        }

        List<Produto> listaProdutos = produtoRepository.findAll();

        if (listaProdutos.isEmpty()) {
            return ApiResponse.error("Nenhum produto encontrado", "NAO_ENCONTRADO");
        }

        LocalDate hoje = LocalDate.now();

        List<ProdutoResponse> dataResponse = listaProdutos.stream()
                .filter(p -> {
                    LocalDate validade = p.getValidade();
                    return validade != null
                            && !validade.isBefore(hoje)
                            && ChronoUnit.DAYS.between(hoje, validade) <= diasParaAviso;
                })
                .map(produtoMapper::toResponse)
                .toList();

        if(dataResponse.isEmpty()){
            return ApiResponse.success("Nenhum produto perto do vencimento", null);
        }

        return ApiResponse.success("Verificação realizada com sucesso", dataResponse);
    }

    @LogSucesso(mensagem = "Ajuste manual realizado com sucesso")
    public ApiResponse<AjusteManualResponse> ajusteManual(ProdutoRequest request) {

        verificarCadastro(request.nome());

        Produto produto = atualizarQuantidade(request.nome(), request.validade(), request.origem(), request.quantidade());

        AjusteManualResponse dataResponse = ajusteManualMapper.toResponse(produto, request.motivo());

        return ApiResponse.success("Ajuste realizado com sucesso", dataResponse);
    }

    private void verificarCadastro(String nome) {
        if(!catalogoRepository.existsByNome(nome)){
            throw new BusinessException("NAO_CADASTRADO", "Produto não cadastrado");
        }
    }

    @LogSucesso(mensagem = "Produto atualizado com sucesso")
    private Produto atualizarQuantidade(boolean entrada, String nome, LocalDate validade, String origem, BigDecimal quantidade) {

        boolean sucesso = false;
        int tentativas = 0;
        final int maximoTentativas = 3;

        Produto produto = null;

        while (!sucesso && tentativas < maximoTentativas) {

            if (produtoRepository.existsByNomeAndValidadeAndOrigem(nome, validade, origem)) {
                produto = produtoRepository.findByNomeAndValidadeAndOrigem(nome, validade, origem)
                        .orElseThrow(() -> new BusinessException("NAO_ENCONTRADO", "Produto cadastrado não encontrado"));
            }else{
                produto = new Produto();

                produto.setNome(nome);
                produto.setValidade(validade);
                produto.setOrigem(origem);
            }

            if (entrada) {
                produto.addQuantidade(quantidade);
            } else {
                produto.subtractQuantidade(quantidade);
            }

            try {
                produtoRepository.save(produto);
                sucesso = true;

            } catch (OptimisticLockingFailureException ex) {

                log.debug("Tentativa {} de atualizar quantidade do produto {}", tentativas + 1, nome);

                tentativas++;

                if (tentativas == maximoTentativas) {
                    throw new BusinessException("CONCORRENCIA", "Falha ao atualizar quantidade do produto, tente novamente.");
                }
            }
        }

        return produto;
    }

    @LogSucesso(mensagem = "Produto ajustado com sucesso")
    private Produto atualizarQuantidade(String nome, LocalDate validade, String origem, BigDecimal quantidade) {

        boolean sucesso = false;
        int tentativas = 0;
        final int maximoTentativas = 3;

        Produto produto = null;

        if(quantidade.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        while (!sucesso && tentativas < maximoTentativas) {

            if (produtoRepository.existsByNomeAndValidadeAndOrigem(nome, validade, origem)) {
                produto = produtoRepository.findByNomeAndValidadeAndOrigem(nome, validade, origem)
                        .orElseThrow(() -> new BusinessException("NAO_ENCONTRADO", "Produto cadastrado não encontrado"));
            }else{
                produto = new Produto();

                produto.setNome(nome);
                produto.setValidade(validade);
                produto.setOrigem(origem);
            }

            produto.setQuantidade(quantidade);

            try {
                produtoRepository.save(produto);
                sucesso = true;

            } catch (OptimisticLockingFailureException ex) {

                log.debug("Tentativa {} de ajustar quantidade do produto {}", tentativas + 1, nome);

                tentativas++;

                if (tentativas == maximoTentativas) {
                    throw new BusinessException("CONCORRENCIA", "Falha ao ajustar quantidade do produto, tente novamente.");
                }
            }
        }

        return produto;
    }

    @LogSucesso(mensagem = "Produto descartado com sucesso")
    private Descarte criarProdutoDescartado(String nome, BigDecimal quantidade, LocalDate validade, String origem, MotivoDescarte motivoDescarte) {

        boolean sucesso = false;
        int tentativas = 0;
        final int maximoTentativas = 3;

        Descarte produtoDescartado = null;

        while (!sucesso && tentativas < maximoTentativas) {

            if (descarteRepository.existsByNomeAndValidadeAndOrigemAndMotivoDescarte(nome, validade, origem, motivoDescarte)) {
                produtoDescartado = descarteRepository.findByNomeAndValidadeAndOrigemAndMotivoDescarte(nome, validade, origem, motivoDescarte)
                        .orElseThrow(() -> new BusinessException("NAO_ENCONTRADO", "Produto cadastrado não encontrado"));
            }else{
                produtoDescartado = new Descarte();

                produtoDescartado.setNome(nome);
                produtoDescartado.setValidade(validade);
                produtoDescartado.setOrigem(origem);
                produtoDescartado.setMotivoDescarte(motivoDescarte);
            }

            produtoDescartado.addQuantidade(quantidade);

            try {
                descarteRepository.save(produtoDescartado);
                sucesso = true;

            } catch (OptimisticLockingFailureException ex) {

                log.debug("Tentativa {} de descartar produto {}", tentativas + 1, produtoDescartado.getNome());

                tentativas++;

                if (tentativas == maximoTentativas) {
                    throw new BusinessException("CONCORRENCIA", "Falha ao descartar produto, tente novamente.");
                }
            }
        }
        return produtoDescartado;
    }

}
