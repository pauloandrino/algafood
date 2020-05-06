package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.di.model.Cliente;
import com.algaworks.algafoodapi.di.service.AtivacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {

    @Autowired
    private AtivacaoClienteService ativacaoClienteService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        Cliente joao = new Cliente("João", "email@email.com", "33221144");
        ativacaoClienteService.ativar(joao);

        return "hello";
    }
}
