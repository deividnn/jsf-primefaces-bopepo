/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bopepo;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

/**
 * Exemplo de código para geração de um boleto simples.
 *
 * <p>
 * Utiliza o Banco Bradesco como exemplo, já que possui um implementação
 * simples.
 * </p>
 *
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @since 0.2
 *
 * @version 0.2
 */
public class MeuPrimeiroBoleto {

    /**
     * Executa o exemplo.
     *
     * @param args
     */
    public static void main(String[] args) {

        new MeuPrimeiroBoleto().exemplo();
    }

    /**
     * Um simples exemplo de como gerar um boleto.
     */
     final void exemplo() {

        /*
         * Para gerar um boleto é preciso dos dados do boleto e de um
         * visualizador de boletos.
         */
        /*
         * Primeiro crie os dados do boleto.
         */
        Boleto boleto = crieUmBoleto();

        /*
         * Em seguida, basta criar um visualizador para o boleto.
         */
        BoletoViewer boletoViewer = new BoletoViewer(boleto);

        /*
         * Depois, gere um arquivo, byte array ou stream. Nesse exemplo, um
         * arquivo será salvo na mesma pasta do seu "projeto"
         */
        // Exemplos de uso:
        // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
        // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
        File arquivoPdf = boletoViewer.getPdfAsFile(System.getProperty("user.home")+File.separator+"MeuPrimeiroBoleto.pdf");

        // Agora veja o arquivo gerado na tela.
        mostreBoletoNaTela(arquivoPdf);

    }

    /**
     * Cria um boleto, em passos distintos, com os dados necessários para a
     * visualização.
     *
     * @return boleto com dados
     */
    Boleto crieUmBoleto() {

        /*
         * PASSO 1: Você precisa dos dados de uma conta bancária habilitada para
         * emissão de boletos.
         */
        ContaBancaria contaBancaria = crieUmaContaBancaria();

        /*
         * PASSO 2: Informe os dados do cedente.
         */
        Cedente cedente = crieUmCedente();

        /*
         * PASSO 3: Informe os dados do Sacado.
         */
        Sacado sacado = crieUmSacado();

        /*
         * PASSO 4: Crie um novo título/cobrança e informe os dados.
         */
        Titulo titulo = crieOsDadosDoNovoTitulo(new Titulo(contaBancaria, sacado, cedente));

        /*
         * PASSO 5: Crie o boleto e informe os dados necessários.
         */
        Boleto boleto = crieOsDadosDoNovoBoleto(new Boleto(titulo));
        boleto.addTextosExtras("txtRsNossoNumero", "24" + titulo.getNossoNumero().substring(2) +"-"+ titulo.getDigitoDoNossoNumero());
        boleto.addTextosExtras("txtFcNossoNumero", "24" + titulo.getNossoNumero().substring(2)  +"-"+ titulo.getDigitoDoNossoNumero());

        return boleto;
    }

    /**
     * Preenche os principais dados do boleto.
     *
     * @param boleto
     * @return boleto com os dados necssários
     */
    final Boleto crieOsDadosDoNovoBoleto(Boleto boleto) {

        boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
        boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado não é o esperado, aproveite o DESCONTÃO!");
        boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
        boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
        boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
        boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
        boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
        boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
        boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
        boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
        return boleto;
    }

    /**
     * Preenche os principais dados do título.
     *
     * @param titulo
     *
     * @return título com os dados necssários
     */
    final Titulo crieOsDadosDoNovoTitulo(Titulo titulo) {

        /*
         * DADOS BÁSICOS.
         */
        titulo.setNumeroDoDocumento("12345");
        titulo.setNossoNumero("00234569812345");
        titulo.setDigitoDoNossoNumero("5");
        titulo.setValor(BigDecimal.valueOf(0.23));

        // Para informar a data de maneira simples você pode utilizar as 
        // classes utilitárias do JRimum. Abaixo temos alguns exemplos:
        // (1) titulo.setDataDoVencimento(  DateFormat.DDMMYYYY_B.parse("11/03/2011")  );
        // (2) titulo.setDataDoVencimento(  Dates.parse("11/03/2011", "dd/MM/yyyy")  );		
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(new Date());

        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.A);
        titulo.setDesconto(new BigDecimal(0.05));
        titulo.setDeducao(BigDecimal.ZERO);
        titulo.setMora(BigDecimal.ZERO);
        titulo.setAcrecimo(BigDecimal.ZERO);
        titulo.setValorCobrado(BigDecimal.ZERO);

        return titulo;
    }

    /**
     * Cria uma instância de sacado com os principais dados para o boleto.
     *
     * @return sacado com os dados necssários
     */
    final Sacado crieUmSacado() {

        Sacado sacado = new Sacado("Java Developer Pronto Para Férias", "222.222.222-22");

        // Informando o endereço do sacado.
        Endereco enderecoSac = new Endereco();
        enderecoSac.setUF(UnidadeFederativa.RN);
        enderecoSac.setLocalidade("Natal");
        enderecoSac.setCep(new CEP("59064-120"));
        enderecoSac.setBairro("Grande Centro");
        enderecoSac.setLogradouro("Rua poeta dos programas");
        enderecoSac.setNumero("1");
        sacado.addEndereco(enderecoSac);

        return sacado;
    }

    /**
     * Cria uma instância de cedente com os principais dados para o boleto.
     *
     * @return cedente com os dados necssários
     */
    final Cedente crieUmCedente() {

        return new Cedente("Projeto JRimum", "00.000.208/0001-00");
    }

    /**
     * Cria uma instância de conta bancária com os principais dados para o
     * boleto em questão (Banco Bradesco).
     *
     * @return conta com os dados necssários
     */
    final ContaBancaria crieUmaContaBancaria() {

        ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.CAIXA_ECONOMICA_FEDERAL.create());
        contaBancaria.setNumeroDaConta(new NumeroDaConta(12345, "0"));
        contaBancaria.setCarteira(new Carteira(8));
        contaBancaria.setAgencia(new Agencia(1234, "1"));

        return contaBancaria;
    }

    /**
     * Exibe o arquivo na tela.
     *
     * @param arquivoBoleto
     */
    final void mostreBoletoNaTela(File arquivoBoleto) {

        try {
        // Descomente se estiver usando java 6 ou superior
         java.awt.Desktop.getDesktop().open(arquivoBoleto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
