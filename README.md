# Sistema Acadêmico em Java Swing

Projeto desenvolvido para a atividade acadêmica N2, implementado em **Java básico/intermediário** utilizando exclusivamente a biblioteca **Swing** para as telas e arquivos de texto para persistência de dados.

O projeto segue a arquitetura solicitada pelo professor: para cada entidade, repete-se o trio clássico de classes:
- **Entidade (`Aluno`, `Professor`, `Disciplina`)**: guarda os dados encapsulados com atributos privados, getters/setters e métodos `toLinha()` e `fromLinha()`.
- **Persistência (`AlunoArquivo`, `ProfessorArquivo`, `DisciplinaArquivo`)**: única classe que acessa o arquivo de texto correspondente, com `listarTodos()` e `salvarTodos()`.
- **Tela (`TelaAluno`, `TelaProfessor`, `TelaDisciplina`)**: painel da aba no `JTabbedPane` com `JTable` não editável, campos de formulário e botões Adicionar, Alterar e Excluir.

---

## 📂 Organização do Projeto (10 Arquivos .java)

```text
SISTEMA-ACADEMICO-JAVA/
├── src/
│   ├── Aluno.java                # Entidade Aluno (nome, matrícula, cidade)
│   ├── AlunoArquivo.java         # Persistência de alunos em alunos.txt
│   ├── TelaAluno.java            # Painel Swing da aba Alunos
│   ├── Professor.java            # Entidade Professor (nome, titulação, salário)
│   ├── ProfessorArquivo.java     # Persistência de professores em professores.txt
│   ├── TelaProfessor.java        # Painel Swing da aba Professores
│   ├── Disciplina.java           # Entidade Disciplina (nome, carga horária, período)
│   ├── DisciplinaArquivo.java    # Persistência de disciplinas em disciplinas.txt
│   ├── TelaDisciplina.java       # Painel Swing da aba Disciplinas
│   └── SistemaAcademico.java     # Janela principal com JTabbedPane e 3 abas
├── alunos.txt                    # Dados persistidos de Alunos
├── professores.txt               # Dados persistidos de Professores
├── disciplinas.txt               # Dados persistidos de Disciplinas
├── .gitignore                    # Ignora binários compilados (*.class)
└── README.md                     # Documentação
```

---

## ⚙️ Compilação e Execução

### Comandos pelo Terminal
A partir da raiz do projeto:

1. **Compilar os arquivos:**
   ```bash
   javac src/*.java
   ```

2. **Executar a aplicação:**
   ```bash
   java -cp src SistemaAcademico
   ```

---

## 📊 Tabela de Conferência dos Dados (Seção 6)

| Aba | Registros Cadastrados | Critério de Conferência | Valor Esperado |
| :--- | :--- | :--- | :--- |
| **Alunos** | 5 registros | Alunos de Rio Verde | **3 alunos** |
| **Professores** | 4 registros | Soma dos salários | **R$ 28.600,50** |
| **Disciplinas** | 4 registros | Carga horária somada | **240h** |

### Questão Teórica (Professores):
> **Por que o salário no arquivo `professores.txt` é salvo com ponto (ex: `6200.5`) e na tela é digitado com vírgula (`6200,50`)?**
>
> **Resposta:** No Brasil, a convenção para digitação e exibição ao usuário final utiliza vírgula como separador decimal. No entanto, na linguagem Java, métodos de conversão como `Double.parseDouble` exigem obrigatoriamente a notação internacional com ponto. Dessa forma, a aplicação substitui a vírgula por ponto antes da conversão para double e salva no arquivo em formato padrão do Java, garantindo robustez e compatibilidade.

---

## 🛡️ Tratamento de Erros e Exceções

1. **Campos Obrigatórios Vazios:**
   - Nenhum campo pode ficar em branco. Ao tentar adicionar ou alterar com campo vazio, exibe alerta via `JOptionPane.WARNING_MESSAGE` e não grava.
2. **Guarda de Seleção (Alterar / Excluir):**
   - Verifica `tabela.getSelectedRow() < 0`. Se nenhuma linha estiver selecionada, avisa com `JOptionPane.WARNING_MESSAGE` e não quebra a aplicação.
3. **Tratamento de `NumberFormatException`:**
   - Em **Professores** (salário) e **Disciplinas** (carga horária), a conversão é protegida por bloco `try/catch (NumberFormatException)`. Se for digitado texto inválido, um alerta com `JOptionPane.ERROR_MESSAGE` é exibido sem gravar.
4. **Células Não Editáveis:**
   - `DefaultTableModel.isCellEditable(row, column)` retorna `false`. As modificações devem ser feitas exclusivamente pelos campos e confirmadas pelo botão "Alterar".

---

## 🎥 Roteiro para o Vídeo de Apresentação (6 a 10 minutos)

1. **Abertura e Navegação pelas 3 Abas (1 a 2 min):**
   - Rodar o sistema (`java -cp src SistemaAcademico`).
   - Mostrar as três abas: **Alunos**, **Professores** e **Disciplinas**, comprovando que cada uma carrega os dados do respectivo arquivo texto ao abrir.
2. **Demonstração do Ciclo CRUD (2 a 3 min):**
   - Em uma das abas (ex: Alunos):
     - Inserir um novo registro e clicar em **Adicionar**.
     - Clicar na linha da tabela para carregar os dados nos campos, editar um valor e clicar em **Alterar**.
     - Selecionar a linha e clicar em **Excluir**.
   - Abrir o arquivo `.txt` correspondente num editor de texto para mostrar que o arquivo foi atualizado.
3. **Demonstração dos Erros Tratados (2 min):**
   - Clicar em **Alterar** ou **Excluir** sem selecionar linha da tabela (mostrar o aviso do `JOptionPane`). Apontar no código o `if (linha < 0)`.
   - Digitar letras no campo Salário ou Carga Horária e clicar em **Adicionar** (mostrar a mensagem de erro). Apontar no código o bloco `try/catch (NumberFormatException)`.
4. **Comparação de Código Lado a Lado (1 a 2 min):**
   - Abrir `TelaAluno.java` e `TelaProfessor.java` lado a lado.
   - Apontar que a estrutura é rigorosamente a mesma (JTable, DefaultTableModel, ListSelectionListener, botões Adicionar/Alterar/Excluir, persistência). O que muda de uma para outra são os campos de entrada, o tipo dos dados e o tratamento de exceção numérica.
