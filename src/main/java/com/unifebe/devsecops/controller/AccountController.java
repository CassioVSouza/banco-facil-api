package com.unifebe.devsecops.controller;

import com.unifebe.devsecops.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class AccountController {

    private final PaymentService paymentService;

    public AccountController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/discount")
    public double getDiscount(@RequestParam double price, @RequestParam int percent) {
        return paymentService.applyDiscount(price, percent);
    }

    @GetMapping("/conta")
    public String buscarConta(@RequestParam String id) throws SQLException {
        // Corrigido (SQL Injection): o parametro "id" e passado como parametro
        // do PreparedStatement ("?"), e nao concatenado na string SQL. O driver
        // o trata sempre como dado, nunca como parte do comando SQL.
        // try-with-resources garante o fechamento da conexao e dos recursos.
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
             PreparedStatement stmt = conn.prepareStatement("SELECT nome FROM contas WHERE id = ?")) {
            stmt.setString(1, id);

            StringBuilder resultado = new StringBuilder();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultado.append(rs.getString("nome")).append(" ");
                }
            }
            return resultado.toString();
        }
    }


    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
