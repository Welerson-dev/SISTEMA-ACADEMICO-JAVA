/**
 * Interface que define o contrato para entidades que podem ser persistidas
 * em arquivo de texto formatado (colunas separadas por ponto e vírgula).
 */
public interface Persistivel {
    /**
     * Converte o estado da entidade em uma linha de texto.
     * @return Linha formatada com os atributos separados por ponto e vírgula.
     */
    String toLinha();
}
