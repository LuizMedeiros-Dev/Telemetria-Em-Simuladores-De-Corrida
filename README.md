# 🏎️ Cronômetro de Telemetria – Sistema para Simuladores de Corrida  
### *DESENVOLVIMENTO DE SISTEMA PARA TELEMETRIA EM SIMULADORES DE CORRIDA COM CARROS ELÉTRICOS USANDO LINGUAGEM DE PROGRAMAÇÃO ORIENTADA A OBJETOS*

---

## 📌 Sobre o Projeto

Este repositório contém o desenvolvimento de um sistema completo de telemetria para simulações de corrida utilizando **Java**, **AWT/Swing** e **MySQL**.

O objetivo é registrar o tempo de voltas de veículos elétricos (carros ou drones de controle remoto ou autônomos), permitindo análise, armazenamento e geração de relatórios digitais.

O sistema foi desenvolvido como parte das **Atividades Práticas Supervisionadas (APS)** do curso de Ciência da Computação.

---

## 🎯 Objetivos da APS (adaptados da proposta oficial)

1. **Cronometragem das voltas**
   - O veículo percorre 2 ou mais voltas.
   - O sistema registra:
     - Tempo da 1ª volta
     - Tempo total
     - Tempos subsequentes
     - Tempo total acumulado
   - Os tempos são exibidos em tela e armazenados.

2. **Utilização de conceitos de LPOO e Banco de Dados**
   - Implementação de classes e objetos
   - Encapsulamento e modularização
   - Persistência de dados com MySQL
   - Entrada de dados interna e externa

3. **Geração de Relatórios Digitais**
   - Exibição dos tempos registrados
   - Sistema ambientalmente correto (sem impressão física)

4. **Linguagens e Tecnologias exigidas**
   - Java
   - AWT/Swing
   - MySQL
   - JDBC

---

## 🖥️ Funcionalidades do Sistema

✔️ Cadastro de Equipe e Competidor  
✔️ Cronômetro com milissegundos  
✔️ Registro automático das voltas  
✔️ Cálculo:
- Tempo da volta
- Diferença entre voltas
- Tempo total da corrida

✔️ Salvamento dos registros no banco de dados  
✔️ Geração de relatório digital na interface  
✔️ Recriação do cronômetro sem perder dados já gravados  

---

## 🏗️ Arquitetura do Sistema

### **1. `InfCronometro`**
Classe responsável por armazenar informações essenciais da corrida:
- Nome das equipes
- Quantidade de voltas
- Dados gerais do usuário

### **2. `Cronometro`**
Classe principal responsável por:
- Interface gráfica (AWT/Swing)
- Controle do cronômetro em tempo real
- Registro de voltas
- Atualização do relatório
- Comunicação com MySQL via JDBC

---

## 🛢️ Banco de Dados

## Estrutura da tabela utilizada:

```sql
CREATE TABLE voltas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipe VARCHAR(255),
    competidor VARCHAR(255),
    numero_volta VARCHAR(20),
    tempo_volta VARCHAR(20),
    codigo VARCHAR(20)
);
```

## 🧰 Tecnologias Utilizadas

| Tecnologia      | Finalidade                          |
|-----------------|--------------------------------------|
| Java 17+        | Linguagem de programação principal   |
| AWT/Swing       | Interface gráfica                    |
| MySQL           | Armazenamento de dados               |
| JDBC            | Comunicação com o banco de dados     |
| Programação OO  | Estruturação e divisão modular       |


## 👨‍💻 Autor

**Luiz Fellipe Silva Medeiros**  
**Linkedin:** www.linkedin.com/in/luiz-fellipe-medeiros


