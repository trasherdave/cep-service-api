/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manoelcampos.cepservice;

import javax.enterprise.context.Dependent;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author David
 */
@Dependent
public final class WidenetService extends AbstractCepService {

    private static final String DOMINIO = "https://apps.widenet.com.br/";

//URL Absoluta:https://ws.apicep.com/cep/[cepCode].[format]
//Exemplo:https://ws.apicep.com/cep/06233-030.json
    
//URL Relativa:https://ws.apicep.com/cep.[format]?code=[cepCode]
//Exemplo:https://ws.apicep.com/cep.json?code=06233-030
    
    public WidenetService() {
        super(DOMINIO);
    }

    @Override
    protected WebTarget buildPath(final String cep) {
        return super.buildPath("busca-cep/api/cep.json?code=" + cep);
    }

}
