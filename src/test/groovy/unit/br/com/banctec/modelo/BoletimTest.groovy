package unit.br.com.banctec.modelo

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

    def 'não deveria aceitar frequência >100 nem <0'() {
        given:
        Boletim b1 = new Boletim(nota1: 10, nota2: 10, frequencia: -1)
        Boletim b2 = new Boletim(nota1: 10, nota2: 10, frequencia: 101)

        when:
        def res1 = b1.getResultado()
        def res2 = b2.getResultado()

        then:
        res1 == "Valor de frequência inválido (${b1.frequencia})"
        res2 == "Valor de frequência inválido (${b2.frequencia})"
    }

    // alterar a classe Boletim para que, caso a nota1 ou nota2
    // sejam menores que 0 ou maiores que 10,
    // o resultado seja sempre "Nota X inválida: Y".
    // Onde X é 1 ou 2 e Y é o valor da nota inválida
    // exs: "Nota 1 inválida: 999"
    //      "Nota 2 inválida: -722"

    // criem um cenário de teste que valide essa nova regra

}
