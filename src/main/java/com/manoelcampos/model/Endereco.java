package com.manoelcampos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Manoel Campos da Silva Filho
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {
    @JsonAlias({"logradouro", "address"})
    private String logradouro;
    private String complemento;

    @JsonAlias({"bairro", "district"})
    private String bairro;

    @JsonAlias({"localidade", "city"})
    private String localidade;

    @JsonAlias({"uf", "state"})
    private String uf;

    @JsonAlias({"cep", "code"})
    private String cep;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(final String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(final String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Logradouro: ").append(logradouro);
        if(complemento != null && !complemento.isEmpty()) {
            builder.append(" - Complemento: ").append(complemento);
        }
        builder.append(" - Bairro: ").append(bairro);
        builder.append(" - Localidade: ").append(localidade);
        builder.append(" - UF: ").append(uf);
        builder.append(" - CEP: ").append(cep);

        return builder.toString();
    }
}
