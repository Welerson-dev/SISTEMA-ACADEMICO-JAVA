import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SistemaAcademico {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }

                JFrame janela = new JFrame("Sistema Acadêmico");
                janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janela.setSize(750, 520);
                janela.setLocationRelativeTo(null);

                JTabbedPane abas = new JTabbedPane();

                abas.addTab("Alunos", new TelaAluno());
                abas.addTab("Professores", new TelaProfessor());
                abas.addTab("Disciplinas", new TelaDisciplina());
                abas.addTab("Turmas", new TelaTurma());

                janela.add(abas);
                janela.setVisible(true);
            }
        });
    }
}
