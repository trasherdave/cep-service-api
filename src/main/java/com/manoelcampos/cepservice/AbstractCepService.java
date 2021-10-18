package com.manoelcampos.cepservice;

import com.manoelcampos.model.Endereco;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Implementação abstrata para serviços de consulta de CEP.
 * @author Manoel Campos da Silva Filho
 */
public abstract class AbstractCepService implements CepService {
    private final static Logger LOGGER = Logger.getLogger(CepService.class.getSimpleName());

    /**
     * Domínio do serviço a ser acessado,
     * definido pelas subclasses.
     */
    private final String dominio;
    private final Client client;

    /**
     * Instancia o serviço de CEP, definindo a URL base para acesso.
     * O construtor é protegido, pois, não queremos que quem for utilizar
     * as subclasses desta, possa indicar o domínio.
     * Classes filhas como {@link ViaCepService} são utilizadas para acesso
     * a um serviço específico, cujo domínio é definida pela própria subclasse.
     * Quem for utilizar tal subclasse não deve ter permissão para alterar o domínio.
     *
     * @param dominio domínio para acesso ao serviço
     * @return
     */
    protected AbstractCepService(final String dominio) {
        this.dominio = insertTrailingSlash(Objects.requireNonNull(dominio));
        this.client = ClientBuilder.newClient();
    }

    /**
     * Insere uma barra no final de um endereço, caso ainda não tenha.
     * @param url endereço a ser adicionada a barra no final
     * @return
     */
    private String insertTrailingSlash(final String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    @Override
    public Endereco buscaEndereco(final String cep) {
        Objects.requireNonNull(cep, "CEP não pode ser nulo");
        LOGGER.info("Enviando requisição para " + getFullPath(cep));

        /**
         * Usa o objeto target para enviar uma requisição HTTP
         * para o serviço de CEP, passando um cep específico por parâmetro.
         * O objeto target já foi previamente associado ao domínio do serviço.
         * Utilizando o método path(), passamos o resultado do método
         * {@link #buildPath(String)} para ele, que representa o restante
         * do endereço para consultar um determinado CEP.
         * Após usar o path para definir o endereço completo
         * que desejamos acessar, a requisição HTTP é construída por meio do método
         * request(). Em seguida, enviamos a requisição usando o método get
         * do protocolo HTTP (pois os serviços de CEP utilizados todos requerem
         * que tal método seja usado).
         *
         * Como estamos usando a API JAX-RS para consumir o serviço de CEP,
         * ela assumi por padrão que a resposta retornada pelo serviço
         * é em formato JSON, que é o caso para os serviços de CEP.
         * Passando a classe {@link Endereco} por parâmetro para o método get,
         * estamos indicando que o objeto JSON recebido deve ser automaticamente
         * convertido para um objeto java da classe {@link Endereco}.
         */
        return buildPath(cep).request().get(Endereco.class);
    }

    /**
     * Obtém a URI completa de uma requisição para buscar um determinado endereço a partir
     * de um CEP. O método é utilizado apenas para exibir tal URI no terminal sempre que uma requisição
     * é enviada.
     * @param cep CEP que deseja-se consultar
     * @return a URI completa da requisição
     */
    private String getFullPath(final String cep){
        return dominio + buildPath(cep);
    }

    /**
     * Constroi o caminho absoluto, a partir da {@link #dominio}, para acesso ao endpoint
     * do serviço de busca de endereço.
     *
     * @param caminho path relativo (sem o domínio) para buscar um endereço a partir de um CEP (que já deve estar no incluso em tal caminho)
     * @return objeto utilizado para enviar requisição HTTP para uma determinada URI.
     */
    protected WebTarget buildPath(String caminho) {
        return client.target(dominio + caminho);
    }
}
