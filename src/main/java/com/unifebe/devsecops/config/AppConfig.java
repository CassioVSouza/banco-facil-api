package com.unifebe.devsecops.config;

/**
 * Configuracao de credenciais da aplicacao.
 *
 * As credenciais NAO ficam mais no codigo-fonte: sao lidas de variaveis de
 * ambiente em tempo de execucao. Em producao, essas variaveis sao injetadas
 * no container a partir de um cofre de segredos (HashiCorp Vault, AWS Secrets
 * Manager, Azure Key Vault etc.), nunca gravadas na imagem nem no repositorio.
 *
 * Observacao: o GITHUB_TOKEN da pipeline e um segredo de CI/build (usado para
 * publicar a imagem no GHCR) e nao deve ser usado como segredo de runtime.
 */
public final class AppConfig {

    private AppConfig() {
    }

    public static String getDbPassword() {
        return System.getenv("DB_PASSWORD");
    }

    public static String getAwsAccessKeyId() {
        return System.getenv("AWS_ACCESS_KEY_ID");
    }

    public static String getAwsSecretAccessKey() {
        return System.getenv("AWS_SECRET_ACCESS_KEY");
    }

    public static String getPaymentGatewayApiKey() {
        return System.getenv("PAYMENT_GATEWAY_API_KEY");
    }
}
