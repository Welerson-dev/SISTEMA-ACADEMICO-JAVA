# Sistema Acadêmico em Java Swing

Projeto desenvolvido para a atividade acadêmica N2, implementado estritamente em **Java puro** utilizando a biblioteca **Swing** para a interface gráfica e arquivos de texto para persistência de dados.

O projeto adota boas práticas de Engenharia de Software (Clean Code, DRY, Orientação a Objetos com Polimorfismo e Generics) para eliminar duplicação de código mantendo a separação clara de responsabilidades.

---

## 🏛️ Arquitetura e Estrutura de Classes

Para cada entidade, o sistema implementa o trio clássico exigido:
- **Entidade**: encapsula os atributos privados, fornece getters/setters e os métodos `toLinha()` e `fromLinha()`. Implementa a interface `Persistivel`.
- **Persistência (`*Arquivo`)**: classe responsável pelo I/O em arquivo texto. Herda de `ArquivoBase<T>` que implementa leitura completa (`listarTodos`) e sobrescrita integral (`salvarTodos`).
- **Interface Gráfica (`Tela*`)**: herda de `TelaCrudBase<T>`, centralizando comportamento de tabela não editável, formulário, botões de ação e listeners.

### Diagrama Estrutural

```
                 ┌──────────────────┐
                 │    Persistivel   │ (Interface)
                 └─────────▲────────┘
                           │ implements
   ┌──────────────┬────────┴─────┬──────────────┐
   │              │              │              │
┌──────┐    ┌───────────┐  ┌───────────┐   ┌─────────┐
│Aluno │    │ Professor │  │Disciplina │   │  Turma  │
└──────┘    └───────────┘  └───────────┘   └─────────┘

                 ┌───────────────────────────┐
                 │     ArquivoBase<T>        │ (Persistência genérica DRY)
                 └─────────────▲─────────────┘
                               │ extends
   ┌──────────────┬────────────┴───┬──────────────┐
   │              │                │              │
┌──────────────┐ ┌────────────────┐ ┌─────────────────┐ ┌──────────────┐
│ AlunoArquivo │ │ProfessorArquivo│ │DisciplinaArquivo│ │ TurmaArquivo │
└──────────────┘ └────────────────┘ └─────────────────┘ └──────────────┘

                 ┌───────────────────────────┐
                 │     TelaCrudBase<T>       │ (Template UI & CRUD Swing)
                 └─────────────▲─────────────┘
                               │ extends
   ┌──────────────┬────────────┴───┬──────────────┐
   │              │                │              │
┌───────────┐ ┌───────────────┐ ┌──────────────┐ ┌───────────┐
│ TelaAluno │ │ TelaProfessor │ │TelaDisciplina│ │ TelaTurma │
└───────────┘ └───────────────┘ └──────────────┘ └───────────┘
```

---

## 📂 Organização do Repositório

```text
SISTEMA-ACADEMICO-JAVA/
├── src/
│   ├── Persistivel.java          # Interface que padroniza toLinha()
│   ├── ArquivoBase.java          # Persistência genérica (listarTodos / salvarTodos)
│   ├── TelaCrudBase.java         # Template genérico de tela CRUD em Swing
│   ├── Aluno.java                # Entidade Aluno (nome, matrícula, cidade)
│   ├── AlunoArquivo.java         # Persistência de alunos em alunos.txt
│   ├── TelaAluno.java            # Painel Swing da aba Alunos
│   ├── Professor.java            # Entidade Professor (nome, titulação, salário)
│   ├── ProfessorArquivo.java     # Persistência de professores em professores.txt
│   ├── TelaProfessor.java        # Painel Swing da aba Professores
│   ├── Disciplina.java           # Entidade Disciplina (nome, carga horária, período)
│   ├── DisciplinaArquivo.java    # Persistência de disciplinas em disciplinas.txt
│   ├── Turma.java                # Entidade Turma (código, curso, vagas) - Desafio
│   ├── TurmaArquivo.java         # Persistência de turmas em turmas.txt - Desafio
│   ├── TelaTurma.java            # Painel Swing da aba Turmas - Desafio
│   └── SistemaAcademico.java     # Classe principal com JFrame e JTabbedPane
├── alunos.txt                    # Dados persistidos de Alunos
├── professores.txt               # Dados persistidos de Professores
├── disciplinas.txt               # Dados persistidos de Disciplinas
├── turmas.txt                    # Dados persistidos de Turmas
├── .gitignore                    # Ignora binários compilados (*.class)
└── README.md                     # Documentação completa
```

---

## ⚙️ Compilação e Execução

### Pré-requisitos
- JDK 8 ou superior instalado.

### Comandos pelo Terminal
A partir da raiz do repositório:

1. **Compilar todos os fontes:**
   ```bash
   javac src/*.java
   ```

2. **Executar a aplicação:**
   ```bash
   java -cp src SistemaAcademico
   ```

---

## 📊 Tabela de Conferência dos Dados de Teste (Seções 6 e 8)

| Aba | Registros Cadastrados | Critério de Conferência | Valor Esperado |
| :--- | :--- | :--- | :--- |
| **Alunos** | 5 registros | Alunos de Rio Verde | **3 alunos** |
| **Professores** | 4 registros | Soma dos salários | **R$ 28.600,50** |
| **Disciplinas** | 4 registros | Carga horária somada | **240h** |
| **Turmas** (Desafio) | 3 registros | Total de vagas somadas | **135 vagas** |

### Questão Teórica (Professores):
> **Por que o salário no arquivo `professores.txt` é salvo com ponto (ex: `6200.5`) e na tela é digitado com vírgula (`6200,50`)?**
>
> **Resposta:** No padrão brasileiro de formatação, a vírgula é o separador decimal oficial utilizado pelo usuário final na interface gráfica. Entretanto, a especificação técnica da linguagem Java e o método `Double.parseDouble` exigem a notação internacional (ponto decimal). Para proporcionar uma experiência de uso natural sem comprometer o processamento nem a interoperabilidade do arquivo texto, a tela substitui a vírgula por ponto antes da conversão para `double`, e formata de volta para vírgula ao exibir.

---

## 🛡️ Tratamento de Erros e Exceções

1. **Campos Obrigatórios Vazios:**
   - Detectado por `validarCampos()` / `validarCamposPreenchidos(...)`.
   - Exibe alerta via `JOptionPane.WARNING_MESSAGE` e aborta a operação sem gravar.
2. **Guarda de Seleção (Alterar / Excluir sem linha selecionada):**
   - Verificação de índice `tabela.getSelectedRow() < 0`.
   - Exibe alerta amigável via `JOptionPane.WARNING_MESSAGE` impedindo `IndexOutOfBoundsException`.
3. **Conversão Numérica (`NumberFormatException`):**
   - Campos de Salário (Professor), Carga Horária (Disciplina) e Vagas (Turma) possuem blocos `try/catch`.
   - Captura `NumberFormatException` e exibe mensagem descritiva com exemplo de formato aceito via `JOptionPane.ERROR_MESSAGE`.
4. **Células Não Editáveis:**
   - `DefaultTableModel.isCellEditable(row, column)` retorna `false`, obrigando toda alteração a ser realizada pelos campos e confirmada pelo botão "Alterar".

---

## 🎥 Roteiro para Gravação do Vídeo de Defesa (6 a 10 minutos)

1. **Apresentação Geral e Navegação pelas Abas (1 a 2 min):**
   - Abrir o sistema (`java -cp src SistemaAcademico`).
   - Navegar pelas abas: **Alunos**, **Professores**, **Disciplinas** e **Turmas**.
   - Mostrar que cada tabela já inicia populada com os dados do respectivo arquivo `.txt`.
2. **Demonstração do Ciclo CRUD em uma Aba (2 a 3 min):**
   - Na aba Alunos (ou outra de sua escolha):
     - Cadastrar um novo registro com o botão **Adicionar**.
     - Clicar na linha da tabela para carregá-la nos campos, modificar um dado e clicar em **Alterar**.
     - Selecionar o registro e clicar em **Excluir**.
   - Abrir o arquivo `.txt` correspondente num editor de texto para comprovar que o arquivo foi atualizado.
3. **Demonstração dos Erros Tratados (2 min):**
   - Clicar em **Alterar** ou **Excluir** sem selecionar nenhuma linha: mostrar o aviso do `JOptionPane`. Apontar no código a verificação `if (linha < 0)` em `TelaCrudBase.java`.
   - Na aba Professores ou Disciplinas, digitar letras (ex: `abc`) no campo numérico e clicar em **Adicionar**: mostrar o diálogo de erro. Apontar no código o bloco `try/catch (NumberFormatException ex)` em `TelaCrudBase.java` e a regra em `TelaProfessor.java`.
4. **Comparação de Código Lado a Lado (1 a 2 min):**
   - Colocar `TelaAluno.java` e `TelaProfessor.java` lado a lado.
   - Explicar como ambas herdam de `TelaCrudBase<T>`, mantendo a mesma estrutura visual e operacional. Destacar o que muda: apenas os campos do formulário, as colunas da tabela e a conversão com tratamento de `NumberFormatException`.
