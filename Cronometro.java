package APS;

import APS.InfCronometro;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

// A classe Vector implementa um array redimensionável de objetos. Assim como um array, objetos da //classe Vector contém elementos que podem ser acessados via índices.

public class Cronometro extends WindowAdapter implements ActionListener {
    //Declaracao WindowAdapter-manipulador de eventos de janela, junto com ActionListener ()
    // windowOpened(),windowClosing(),windowClosed(), etc...
    private Frame janela, janelaRelat;
    private Panel painelBotoes, painelEndereco;
    private Label lNomEQP1, lNomPilot, lCronometro, lVoltasEQP1, lVoltaRapida1, lVoltaRapida2;
    private Label lRank, lPosicao, lNomEquipe, lTempoEquipe, lPosicoes, lNomeEquipes, lTempEquipes, lVoltPorEqp, lEQP1Relat, lEQP2Relat, lPilotoRelat, lPilotos;
    private Thread threadCronometro;
    private TextField tNomEQP1, tNomEQP2;
    private TextArea tVoltasEQP1;
    public static TextArea tVoltaRapida1 = new TextArea();
    private TextArea tVoltaRapida2;
    private Button bNovo, bRelatorio, bIniciar, bVolta, bParar, bAdicionarDados, bEditarDados, bCronometro; // Adicionado o botão bAdicionarDados
    private Vector vCronometro, vRelat;
    private int posicao;
    private boolean rodando = false;


    Font fonteCronometro, fonteNome, fonteLabel, RankFonte, VoltPorEqpFonte, fonteEQPRelat, fontetVoltasEQP1, fontetVoltaRapida;
    int minutos = 0;
    int segundos = 0;
    int milissegundos = 0;
    int voltas = 0;
    int voltasv = 0;
    Vector<String> volta = new Vector<>();
    public static String tempoVolta;
    public static String numVolta;


    //Método Construtor Criacao de vetor, frame
    public Cronometro() {
        vCronometro = new Vector();
        janela = new Frame();
        janela.setTitle("Cronômetro");
        janela.setSize(700, 615);
        janela.setBackground(new Color(46, 46, 46));
        janela.setLayout(null);
        janela.addWindowListener(this);

        //Método Construtor Criacao de Painel (backgroud principal)
        painelEndereco = new Panel();
        painelEndereco.setBackground(new Color(46, 46, 46));
        painelEndereco.setSize(700, 500);
        painelEndereco.setLocation(8, 28);
        painelEndereco.setLayout(null);

        //Método Construtor Criacao de Painel(backgroud dos botoes de salvar)
        painelBotoes = new Panel();
        painelBotoes.setBackground(new Color(46, 46, 46));
        painelBotoes.setSize(700, 33);
        painelBotoes.setLocation(00, 550);
        painelBotoes.setLayout(new FlowLayout());


        //Método Construtor Criacao de Labels
        //Nome Equipe 1
        lNomEQP1 = new Label("Nome da Equipe :");
        lNomEQP1.setForeground(new Color(255, 255, 255));
        fonteNome = new Font("Arial", Font.BOLD, 20);
        lNomEQP1.setFont(fonteNome);
        //Nome Piloto
        lNomPilot = new Label("Piloto :");
        lNomPilot.setForeground(new Color(255, 255, 255));
        fonteNome = new Font("Arial", Font.BOLD, 20);
        lNomPilot.setFont(fonteNome);
        //Campo voltas Equipe 1
        lVoltasEQP1 = new Label("Voltas:");
        lVoltasEQP1.setForeground(new Color(255, 255, 255));
        fonteNome = new Font("Arial", Font.BOLD, 25);
        lVoltasEQP1.setFont(fonteNome);
        //Campo voltas Equipe 1

        fonteNome = new Font("Arial", Font.BOLD, 30);
        //Contador cronometro
        lCronometro = new Label("00:00:00");
        lCronometro.setBounds(200, 180, 260, 60);
        lCronometro.setForeground(Color.white);
        fonteCronometro = new Font("Arial", Font.BOLD, 64);
        lCronometro.setFont(fonteCronometro);
        //Qual Equipe ira correr


        //Método Construtor Criacao de TextFields
        tNomEQP1 = new TextField(20);
        fonteLabel = new Font("Arial", Font.CENTER_BASELINE, 17);
        tNomEQP1.setFont(fonteLabel);

        tNomEQP2 = new TextField(20);
        fonteLabel = new Font("Arial", Font.CENTER_BASELINE, 17);
        tNomEQP2.setFont(fonteLabel);

        //Método Construtor Criação do Botão Adicionar Dados
        bAdicionarDados = new Button("Adicionar");
        bAdicionarDados.addActionListener(this);
        bAdicionarDados.setForeground(new Color(7, 84, 92));
        bAdicionarDados.setBackground(new Color(148, 222, 234)); // Cor verde escura

        bEditarDados = new Button("Editar");
        bEditarDados.addActionListener(this);
        bEditarDados.setForeground(new Color(7, 84, 92));
        bEditarDados.setBackground(new Color(148, 222, 234));

        //Substituicao do item pelo compomente especificado, na posicao indicada
        lNomEQP1.setBounds(10, 25, 180, 20);
        tNomEQP1.setBounds(200, 25, 200, 20); // Reduzindo a largura do TextField
        lNomPilot.setBounds(10, 55, 180, 20);
        tNomEQP2.setBounds(200, 55, 200, 20); // Reduzindo a largura do TextField
        bAdicionarDados.setBounds(410, 25, 90, 50);
        bEditarDados.setBounds(410, 80, 90, 20);// Posicionando o botão ao lado dos TextFields
        lVoltasEQP1.setBounds(10, 170, 250, 300);


        //Criacao de CheckboxGroup para definir a equipe a cronometrar a volta


        //Método Construtor Criacao de de TextArea Equipe 1
        tVoltasEQP1 = new TextArea("", 15, 90, TextArea.SCROLLBARS_BOTH);
        tVoltasEQP1.setBounds(120, 300, 400, 200); // Reduzindo a altura para o botão ficar visível
        fontetVoltasEQP1 = new Font("Arial", Font.BOLD, 20);
        tVoltasEQP1.setFont(fontetVoltasEQP1);

        //Método Construtor Criacao de de TextArea 2


        //Adiciona no frame painelEndereco os componentes criados
        painelEndereco.add(lNomEQP1);
        painelEndereco.add(tNomEQP1);
        painelEndereco.add(lNomPilot);
        painelEndereco.add(tNomEQP2);
        painelEndereco.add(bAdicionarDados);
        painelEndereco.add(bEditarDados);// Adicionando o botão ao painelEndereco
        painelEndereco.add(tVoltasEQP1);
        painelEndereco.add(lVoltasEQP1);


        //botoes
        bNovo = new Button("Novo");
        bNovo.addActionListener(this);
        bNovo.setBounds(180, 550, 100, 30);
        bNovo.setForeground(Color.white);
        bNovo.setBackground(new Color(217, 79, 79));
        janela.add(bNovo);

        bRelatorio = new Button("Relatório");
        bRelatorio.addActionListener(this);
        bRelatorio.setBounds(360, 550, 100, 30);
        bRelatorio.setForeground(Color.white);
        bRelatorio.setBackground(new Color(217, 79, 79));
        janela.add(bRelatorio);

        //botao iniciar
        bIniciar = new Button("Iniciar");
        bIniciar.setBounds(570, 120, 100, 30);
        bIniciar.setForeground(Color.white);
        bIniciar.setBackground(new Color(0, 121, 140));
        bIniciar.addActionListener(this);
        janela.add(bIniciar); //adicionar diretamente à janela, não no painel
        janela.add(lCronometro);
        //Botao volta
        bVolta = new Button("Volta");
        bVolta.setBounds(570, 160, 100, 30);
        bVolta.setForeground(Color.white);
        bVolta.setBackground(new Color(0, 121, 140));
        bVolta.addActionListener(this);
        janela.add(bVolta);
        bVolta.setEnabled(false);

        //Botao parar
        bParar = new Button("Parar");
        bParar.setBounds(570, 200, 100, 30);
        bParar.setForeground(Color.white);
        bParar.setBackground(new Color(0, 121, 140));
        bParar.addActionListener(this);
        janela.add(bParar);


        //adiciona na Janela os frames
        janela.add(painelEndereco);
        janela.add(painelBotoes);

    }

    //Adiciona o conteudo no vetor(setText)
    public void setNomEQP1(String NomEQP1) {
        tNomEQP1.setText(NomEQP1);
    }

    public void setNomEQP2(String NomEQP2) {
        tNomEQP2.setText(NomEQP2);
    }

    public void setVoltasEQP1(String VoltasEQP1) {
        tVoltasEQP1.setText(VoltasEQP1);
    }

    public void setVoltaRapida1(String VoltaRapida1) {
        tVoltaRapida1.setText(VoltaRapida1);
    }

    public void setVoltaRapida2(String VoltaRapida2) {
        tVoltaRapida2.setText(VoltaRapida2);
    }


    //Retorna o conteudo que esta no vetor
    public String getNomEQP1() {
        return tNomEQP1.getText();
    }

    public String getNomEQP2() {
        return tNomEQP2.getText();
    }

    public String getVoltasEQP1() {
        return tVoltasEQP1.getText();
    }

    public String getVoltaRapida1() {
        return tVoltaRapida1.getText();
    }

    public String getVoltaRapida2() {
        return tVoltaRapida2.getText();
    }


    //Verificacao de qual dos botoes foi acionado para chamar os procedimentos devidos
    public void actionPerformed(ActionEvent e) {
        Button b = (Button) e.getSource();
        if (b == bNovo) this.botaoNovo();
        else if (b == bIniciar) this.botaoIniciar();
        else if (b == bRelatorio) this.botaoRelatorio();
        else if (b == bIniciar) this.botaoIniciar();
        else if (b == bParar) this.botaoParar();
        else if (b == bVolta) this.botaoVolta();
        else if (b == bAdicionarDados) this.botaoAdicionarDados();
        else if (b == bEditarDados) this.botaoEditarDados();
        else if (b == bCronometro) this.botaoCronometro();
    }

    public static String nomeEquipe = "";
    public static String nomePiloto = "";
    //Acao do botao Adicionar Dados
    int b = 0;

    void botaoAdicionarDados() {
        nomeEquipe = getNomEQP1();
        nomePiloto = getNomEQP2();
        Nome.add(b, nomeEquipe);
        competidor.add(b, nomePiloto);



        if (!nomeEquipe.isEmpty() && !nomePiloto.isEmpty()) {
            // Aqui você pode armazenar esses dados onde for necessário
            System.out.println("Equipe: " + nomeEquipe + ", Piloto: " + nomePiloto);
            // Opcional: Limpar os campos após adicionar


            tNomEQP1.setEnabled(false);
            tNomEQP2.setEnabled(false);
            bEditarDados.setEnabled(true);
            bAdicionarDados.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(janela, "Por favor, preencha o nome da equipe e o nome do piloto.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
        }
    }

    void botaoEditarDados() {
        tNomEQP1.setEnabled(true);
        tNomEQP2.setEnabled(true);
        bEditarDados.setEnabled(false);
        bAdicionarDados.setEnabled(true);
        tNomEQP1.requestFocus();
    }

    //Acao do botao Novo
    StringBuilder txt = new StringBuilder();
    void botaoNovo() {

        //Faz a conexão com o BD quando clica no botão para puxar a segunda equipe
        try {
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/corrida", "root", "");


            String sqlSelect = "SELECT equipe, competidor, numero_volta, tempo_volta FROM voltas WHERE equipe = ? ORDER BY codigo desc";
            PreparedStatement consulta = conexao.prepareStatement(sqlSelect);
            consulta.setString(1, nomeEquipe); // Filtra pela mesma equipe

            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                txt.append("Equipe: ").append(rs.getString("equipe"))
                        .append(" | ").append(rs.getString("numero_volta"))
                        .append(" | Tempo: ").append(rs.getString("tempo_volta"))
                        .append("\n");
            }

            conexao.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        this.limpaDados();
        bRelatorio.setEnabled(false);
        bIniciar.setEnabled(true);
        bVolta.setEnabled(true);
        tNomEQP1.setEnabled(true);
        tNomEQP2.setEnabled(true);
        bEditarDados.setEnabled(false);
        bAdicionarDados.setEnabled(true);
        bParar.setEnabled(true);
        bRelatorio.setEnabled(true);
        codVolta.clear();



        //Contagem para salvar posi volta no ranking.
        i += 2;
        //Contagem para salvar nome no ranking.
        b++;
        if (rodando) {
            rodando = false;

        } else {

            minutos = 0;
            segundos = 0;
            milissegundos = 0;
            lCronometro.setText(formattempo(milissegundos));
            voltas = 0;
            volta.clear(); // Limpa as voltas ao iniciar novo
            tVoltasEQP1.setText("");
        }
        tNomEQP1.requestFocus();
    }

    //Acao do botao salva (estava com problema)
    public void botaoSalvar() {
        //Pega os valores dos campos de texto
        vCronometro.addElement(new InfCronometro(getNomEQP1(), getNomEQP2(), getVoltasEQP1(), null));
        bRelatorio.setEnabled(true);
    }

    private String formattempo(int totsegundos) {
        return String.format("%02d:%02d:%02d", minutos, segundos, milissegundos);
    }


    //Limpa Campos
    public void limpaDados() {
        this.setNomEQP1("");
        this.setNomEQP2("");
        this.setVoltasEQP1("");
        lCronometro.setText("00:00:00");
        voltas = 0;
        volta.clear();
        tVoltasEQP1.setText("");

    }



    //fecha janela
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void mostraCronometro() {
        janela.setVisible(true);
    }

    Vector TotalVolta = new Vector();
    Vector Nome = new Vector(2);
    Vector competidor = new Vector(2);

    void botaoIniciar() {
        bVolta.setEnabled(true);
        if (rodando) return;

        String nomeEquipe = getNomEQP1();
        String nomePiloto = getNomEQP2();

        if (nomeEquipe.isEmpty() || nomePiloto.isEmpty()) {
            JOptionPane.showMessageDialog(janela, "Por favor, preencha o nome da equipe e o nome do piloto antes de iniciar.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método sem iniciar o cronômetro
        }

        bIniciar.setEnabled(false);
        bVolta.setEnabled(true);
        bEditarDados.setEnabled(false);
        bAdicionarDados.setEnabled(false);

        rodando = true;
        threadCronometro = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (rodando) {
                        Thread.sleep(10);  // A contagem vai de 10 em 10 milissegundos

                        milissegundos++;  // Aumenta milissegundos

                        if (milissegundos >= 100) {
                            milissegundos = 0;
                            segundos++;  // Aumenta segundos a cada 100 milissegundos
                        }

                        if (segundos >= 60) {
                            segundos = 0;
                            minutos++;  // Aumenta minutos a cada 60 segundos
                        }

                        lCronometro.setText(formattempo(milissegundos));


                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadCronometro.start();
    }

    void botaoRelatorio() {
        vRelat = new Vector();
        janelaRelat = new Frame();
        janelaRelat.setTitle("Relatório");
        janelaRelat.setSize(700, 615);
        janelaRelat.setBackground(new Color(46, 46, 46));
        janelaRelat.setLayout(null);

        // WindowListener para fechar a janela
        janelaRelat.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                janelaRelat.dispose();
            }
        });

        janelaRelat.setVisible(true);

        lRank = new Label("RANKING");
        RankFonte = new Font("Arial", Font.CENTER_BASELINE, 60);
        lRank.setFont(RankFonte);

        lPosicao = new Label("Coloc.");
        lPosicao.setFont(fonteNome);

        lPilotoRelat = new Label("Piloto");
        lPilotoRelat.setFont(fonteNome);

        lVoltPorEqp = new Label("VOLTAS MAIS RAPIDAS POR EQUIPE");
        VoltPorEqpFonte = new Font("Arial", Font.CENTER_BASELINE, 25);
        lVoltPorEqp.setFont(VoltPorEqpFonte);

        lNomEquipe = new Label("Equipe");
        lNomEquipe.setFont(fonteNome);

        lTempoEquipe = new Label("Tempo Total");
        lTempoEquipe.setFont(fonteNome);

        lVoltaRapida1 = new Label("ABCDEF");
        lVoltaRapida2 = new Label("FEDCBA");

        lEQP1Relat= new Label("1º Colocado:");
        lEQP1Relat.setForeground(new Color(255, 255, 255));
        fonteEQPRelat = new Font("Arial", Font.BOLD, 15);
        lEQP1Relat.setFont(fonteEQPRelat);

        lEQP2Relat= new Label("2º Colocado:");
        lEQP2Relat.setForeground(new Color(255, 255, 255));
        lEQP2Relat.setFont(fonteEQPRelat);

        //Posições das labels
        lRank.setBounds(210, 60, 280, 60);
        lPosicao.setBounds(40, 140, 120, 40);
        lNomEquipe.setBounds(180, 140, 110, 40);
        lPilotoRelat.setBounds(350, 140, 100, 40);
        lTempoEquipe.setBounds(490, 140, 210, 40);

        lVoltPorEqp.setBounds(120, 350, 460, 20);

        lEQP1Relat.setBounds(40, 375, 200, 30);
        tVoltaRapida1 = new TextArea("", 15, 90, TextArea.SCROLLBARS_BOTH);
        tVoltaRapida1.setBounds(40, 400, 300, 150);
        fontetVoltaRapida = new Font("Arial", Font.BOLD, 13);
        tVoltaRapida1.setFont(fontetVoltaRapida);

        lEQP2Relat.setBounds(360, 375, 200, 30);
        tVoltaRapida2 = new TextArea("", 15, 90, TextArea.SCROLLBARS_BOTH);
        tVoltaRapida2.setBounds(360, 400, 300, 150);
        tVoltaRapida2.setFont(fontetVoltaRapida);

        bCronometro = new Button("Cronômetro");
        bCronometro.addActionListener(this);
        bCronometro.setBounds(300, 565, 100, 30);
        bCronometro.setForeground(Color.white);
        bCronometro.setBackground(new Color(217, 79, 79));
        janelaRelat.add(bCronometro);


        //CHAMA O BD PRA PUXAR OS DADOS PARA O RELATORIO           //existe tambem no botãonovo
        StringBuilder texto = new StringBuilder();
        try {
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/corrida", "root", "");

            String sqlSelect = "SELECT equipe, competidor, numero_volta, tempo_volta FROM voltas WHERE equipe = ? ORDER BY codigo desc";
            PreparedStatement consulta = conexao.prepareStatement(sqlSelect);
            consulta.setString(1, nomeEquipe); // Filtra pela mesma equipe

            ResultSet rs = consulta.executeQuery();


            while (rs.next()) {
                texto.append("Equipe: ").append(rs.getString("equipe"))
                        .append(" | ").append(rs.getString("numero_volta"))
                        .append(" | Tempo: ").append(rs.getString("tempo_volta"))
                        .append("\n");
            }

            //Aparece no setText2
            //tVoltaRapida2.setText(texto.toString());

            conexao.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //VoltaRapida do botãoNovo
        //tVoltaRapida1.setText(txt.toString());


        lRank.setForeground(Color.white);
        lVoltPorEqp.setForeground(Color.white);
        lPosicao.setForeground(Color.white);
        lPilotoRelat.setForeground(Color.white);
        lNomEquipe.setForeground(Color.white);
        lTempoEquipe.setForeground(Color.white);
        janelaRelat.add(lRank);
        janelaRelat.add(lVoltPorEqp);
        janelaRelat.add(lPosicao);
        janelaRelat.add(lNomEquipe);
        janelaRelat.add(lPilotoRelat);
        janelaRelat.add(lTempoEquipe);
        janelaRelat.add(tVoltaRapida1);
        janelaRelat.add(tVoltaRapida2);
        janelaRelat.add(lEQP1Relat);
        janelaRelat.add(lEQP2Relat);



        //CRIANDO ARRAY PARA RECEBER AS INFORMAÇÕES DO BANCO DE DADOS
        String[] equipes = new String[2];
        String[] pilotos = new String[2];
        String[] tempos = new String[2];

        //FAZ A CONVERSAO DE OBJETO/STRING PARA INTEIRO E JOGA EM UMA INT.
        int num1 = Integer.parseInt((String) TotalVolta.get(0));
        int num2 = Integer.parseInt((String) TotalVolta.get(2));

        //CONDICIONAL PARA DECIDIR A MELHOR CORRIDA
        if (num1 > num2) {
            tempos[0] = TotalVolta.get(1).toString();
            tempos[1] = TotalVolta.get(3).toString();
            equipes[0] = (String) Nome.get(0);
            equipes[1] = (String) Nome.get(1);
            pilotos[0] = competidor.get(0).toString();
            pilotos[1] = competidor.get(1).toString();

            tVoltaRapida1.setText(txt.toString());
            tVoltaRapida2.setText(texto.toString());



        } else {
            tempos[0] = TotalVolta.get(3).toString();
            tempos[1] = TotalVolta.get(1).toString();
            equipes[0] = (String) Nome.get(1);
            equipes[1] = (String) Nome.get(0);
            pilotos[0] = competidor.get(1).toString();
            pilotos[1] = competidor.get(0).toString();

            tVoltaRapida1.setText(texto.toString());
            tVoltaRapida2.setText(txt.toString());
        }


        for (int i = 0; i <= 1; i++) {
            lPosicoes = new Label((i + 1) + "º");
            lPilotos = new Label(pilotos[i]);
            lNomeEquipes = new Label(equipes[i]);
            lTempEquipes = new Label(tempos[i]);

            lPosicoes.setBounds(70, 190 + i * 60, 50, 40);
            lNomeEquipes.setBounds(160, 190 + i * 60, 180, 40);
            lPilotos.setBounds(350, 190 + i * 60, 200 , 40);
            lTempEquipes.setBounds(520, 190 + i * 60, 2000, 40);

            lPosicoes.setFont(fonteNome);
            lPosicoes.setForeground(Color.yellow);
            lPilotos.setFont(fonteNome);
            lPilotos.setForeground(Color.yellow);
            lNomeEquipes.setFont(fonteNome);
            lNomeEquipes.setForeground(Color.yellow);
            lTempEquipes.setFont(fonteNome);
            lTempEquipes.setForeground(Color.yellow);

            janelaRelat.add(lPosicoes);
            janelaRelat.add(lNomeEquipes);
            janelaRelat.add(lTempEquipes);
            janelaRelat.add(lPilotos);
        }
    }

    void botaoCronometro() {
        if (janelaRelat != null) {
            janelaRelat.dispose(); // Fecha a janela de relatório
        }

        janela.setVisible(true); // Mostra a janela principal novamente
        this.limpaDados();
        bRelatorio.setEnabled(false);
        bIniciar.setEnabled(true);
        bVolta.setEnabled(true);
        tNomEQP1.setEnabled(true);
        tNomEQP2.setEnabled(true);
        bEditarDados.setEnabled(false);
        bAdicionarDados.setEnabled(true);
        bParar.setEnabled(true);
        bRelatorio.setEnabled(true);
        codVolta.clear();
        tNomEQP1.requestFocus();
        minutos = 0;
        segundos = 0;
        milissegundos = 0;
    }

    int i = 0;

    void botaoParar(){
        bIniciar.setEnabled(true);
        bVolta.setEnabled(false);


        if (rodando) {
            rodando = false;
            String pergunta = JOptionPane.showInputDialog("Deseja parar mesmo? (Digite 'sim' ou 'não') ");

            if (pergunta.equals("sim") || pergunta.equals("Sim") || pergunta.equals("SIM")) {
                bIniciar.setEnabled(false);
                bParar.setEnabled(false);
                long tempoAtualMillis = (long) minutos * 60 * 1000 + segundos * 1000 + milissegundos * 10;
                long tempoDessaVoltaMillis;

                //Salva o tempo na TotalVolta na posi 0 e depois soma mais 2, e salva na posi 2, para comparar em um IF.
                TotalVolta.add(0 + i, minutos + "" + segundos + "" + milissegundos);
                //Salva o tempo formatado na posi 1, depois soma mais 2 e salva dnv na posi 3, para mostrar no relatorio.
                TotalVolta.add(1 + i, casas.format(minutos) + ":" + casas.format(segundos) + ":" + casas.format(milissegundos));


                // Se for a primeira volta, o tempo da volta é o tempo atual
                if (voltas == 0) {
                    tempoDessaVoltaMillis = tempoAtualMillis;


                } else {
                    // Para as voltas subsequentes, calcula a diferença entre o tempo atual e o tempo da volta anterior
                    tempoDessaVoltaMillis = tempoAtualMillis - tempoVoltaAnteriorMillis;
                }

                // Converte o tempo da volta (em milissegundos) de volta para o formato MM:SS:MS
                long minutosVolta = (tempoDessaVoltaMillis / (60 * 1000));
                tempoDessaVoltaMillis %= (60 * 1000);
                long segundosVolta = (tempoDessaVoltaMillis / 1000);
                tempoDessaVoltaMillis %= 1000;
                long milissegundosVolta = (tempoDessaVoltaMillis / 10);

                // Formata o tempo da volta para exibição
                String tempoDessaVolta = casas.format(minutosVolta) + ":" + casas.format(segundosVolta) + ":" + casas.format(milissegundosVolta);
                //Salva a volta em um vector para depois virar int no BD.
                codVolta.add(minutosVolta + "" + segundosVolta + "" + milissegundosVolta);
                cVolta = codVolta.get(1).toString();
                // Adiciona o tempo da volta ao vetor de voltas
                volta.add(tempoDessaVolta);
                // Incrementa o contador de voltas
                voltas++;
                // Define o número da volta para salvar no banco de dados
                numVolta = "Volta " + voltas;
                // Atualiza a variável estática tempoVolta para o banco de dados
                tempoVolta = tempoDessaVolta;
                // Atualiza o tempo da volta anterior para o cálculo da próxima volta
                tempoVoltaAnteriorMillis = tempoAtualMillis;

                // Atualiza a TextArea com todos os tempos de volta registrados
                String tempoVolta2 = "";
                for (int b = 0; b < volta.size(); b++) {
                    tempoVolta2 += "Volta " + (b + 1) + " -- " + volta.get(b) + "\n";
                }
                tVoltasEQP1.setText(tempoVolta2);
                // Tenta salvar os dados da volta no banco de dados
                try {
                    bancodedados();
                } catch (SQLException e) {
                    // Se ocorrer um erro ao acessar o banco de dados, lança uma exceção em tempo de execução
                    throw new RuntimeException(e);
                }

            } else {
                if (pergunta.equals("Não") || pergunta.equals("Nao") || pergunta.equals("nao") || pergunta.equals("não") || pergunta.equals("NÃO") || pergunta.equals("NAO")) {
                    rodando = false;

                } else {

                    JOptionPane.showMessageDialog(null, "Escreva uma resposta válida!");
                    pergunta = JOptionPane.showInputDialog("Deseja parar mesmo? (Digite 'sim' ou 'não') ");


                }
            }
        } else {
            rodando = false;
            bVolta.setEnabled(false);
            bIniciar.setEnabled(true);
        }


    }


    DecimalFormat casas = new DecimalFormat("00");
    Vector codVolta = new Vector(2);
    public static String cVolta;
    long tempoVoltaAnteriorMillis = 0;

    public void botaoVolta() {

        if (!rodando) {
            return; // Sai da função se o cronômetro não estiver ativo
        }

        // Calcula o tempo atual em milissegundos
        long tempoAtualMillis = (long) minutos * 60 * 1000 + segundos * 1000 + milissegundos * 10;
        long tempoDessaVoltaMillis;

        // Se for a primeira volta, o tempo da volta é o tempo atual
        if (voltas == 0) {
            tempoDessaVoltaMillis = tempoAtualMillis;
            bVolta.setEnabled(false);

        } else {
            // Para as voltas subsequentes, calcula a diferença entre o tempo atual e o tempo da volta anterior
            tempoDessaVoltaMillis = tempoAtualMillis - tempoVoltaAnteriorMillis;
        }

        // Converte o tempo da volta (em milissegundos) de volta para o formato MM:SS:MS
        long minutosVolta = (tempoDessaVoltaMillis / (60 * 1000));
        tempoDessaVoltaMillis %= (60 * 1000);
        long segundosVolta = (tempoDessaVoltaMillis / 1000);
        tempoDessaVoltaMillis %= 1000;
        long milissegundosVolta = (tempoDessaVoltaMillis / 10);

        //Salva a volta em um vector para depois virar int no BD.
        codVolta.add(minutosVolta + "" + segundosVolta + "" + milissegundosVolta);
        cVolta = codVolta.get(0).toString();

        // Formata o tempo da volta para exibição

        String tempoDessaVolta = casas.format(minutosVolta) + ":" + casas.format(segundosVolta) + ":" + casas.format(milissegundosVolta);

        // Adiciona o tempo da volta ao vetor de voltas
        volta.add(tempoDessaVolta);
        // Incrementa o contador de voltas
        voltas++;
        // Define o número da volta para salvar no banco de dados
        numVolta = "Volta " + voltas;
        // Atualiza a variável estática tempoVolta para o banco de dados
        tempoVolta = tempoDessaVolta;
        // Atualiza o tempo da volta anterior para o cálculo da próxima volta
        tempoVoltaAnteriorMillis = tempoAtualMillis;

        // Atualiza a TextArea com todos os tempos de volta registrados
        String tempoVolta2 = "";
        for (int b = 0; b < volta.size(); b++) {
            tempoVolta2 += "Volta " + (b + 1) + " -- " + volta.get(b) + "\n";
        }
        tVoltasEQP1.setText(tempoVolta2);


        // Tenta salvar os dados da volta no banco de dados
        try {
            bancodedados();
        } catch (SQLException e) {
            // Se ocorrer um erro ao acessar o banco de dados, lança uma exceção em tempo de execução
            throw new RuntimeException(e);
        }
    }

    public void bancodedados() throws SQLException {
        Connection conexao = null;
        String url = "jdbc:mysql://localhost:3306/corrida";
        String user = "root";
        String password = "";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado com sucesso.");

            //Adiciona as voltas no BD
            String sql = "insert into voltas (equipe, competidor, numero_volta,tempo_volta, codigo) values (?, ?, ?, ?, ?) ";
            PreparedStatement insercao = conexao.prepareStatement(sql);
            insercao.setString(1, nomeEquipe);
            insercao.setString(2, nomePiloto);
            insercao.setString(3, numVolta);
            insercao.setString(4, tempoVolta);
            insercao.setString(5, cVolta);



            int linhasafetadas = insercao.executeUpdate();
            System.out.println("Row Affected " + linhasafetadas);

        } catch (ClassNotFoundException e) {
            System.out.println("Driver do BD não localizado.");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao acessar o BD: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
    }



    public static void main(String[] args) throws SQLException {

        Cronometro Cronometro = new Cronometro();
        Cronometro.mostraCronometro();
        Cronometro.bancodedados();


    }
}