package unit.br.com.banctec.modelo

import br.com.bandtec.excecoes.FrequenciaInvalidaException
import br.com.bandtec.excecoes.NotaInvalidaException
import br.com.bandtec.modelo.Boletim
import spock.lang.Specification

class BoletimTest extends Specification {

    def 'deveria ser aprovado'() {
        given:
        Boletim boletim = new Boletim(nota1: 5, nota2: 7, frequencia: 75)

        when:
        def resultado = boletim.getResultado()

        then:
        resultado == 'Aprovado'
    }

    def 'deveria ser reprovado por nota'() {
        given:
        Boletim boletim = new Boletim(nota1: 4.99, nota2: 4.99, frequencia: 75)

        when:
        def res = boletim.getResultado()

        then:
        res == 'Reprovado por nota'
    }

    def 'deveria ser reprovado por frequência'() {

    }

    def 'deveria ser reprovado geral'() {

    }

    def 'deveria lançar uma exceção p/ frequência inválida'() {
        given:
        Boletim boletim = new Boletim(frequencia: -0.01)

        when:
        boletim.getResultado()

        // verificando se uma determinada exceção ocorreu
        // durante a execução do teste
        then:
        thrown(FrequenciaInvalidaException)
    }

    def 'deveria lançar uma exceção p/ nota inválida'() {
        given:
        Boletim bnota1menor = new Boletim(nota1: -0.01)
        Boletim bnota1maior = new Boletim(nota1: 10.01)
        Boletim bnota2menor = new Boletim(nota2: -0.01)
        Boletim bnota2maior = new Boletim(nota2: 10.01)

        when:
        bnota1menor.getResultado()

        then:
        thrown(NotaInvalidaException)


        when:
        bnota1maior.getResultado()

        then:
        thrown(NotaInvalidaException)


        when:
        bnota2menor.getResultado()

        then:
        thrown(NotaInvalidaException)


        when:
        bnota2maior.getResultado()

        then:
        thrown(NotaInvalidaException)
    }

    def 'deveria trazer a mensagem de erro p/ nota'() {
        given:
        Boletim bnota1 = new Boletim(nota1: 10.01)
        Boletim bnota2 = new Boletim(nota2: -0.01)

        when:
        bnota1.getResultado()

        then:
        def erro = thrown(NotaInvalidaException)
        erro.getMessage() == "Nota deve estar entre 0 e 10. Chegou ${bnota1.nota1}"


        when:
        bnota2.getResultado()

        then:
        erro = thrown(NotaInvalidaException)
        erro.getMessage() == "Nota deve estar entre 0 e 10. Chegou ${bnota2.nota2}"

    }


}
