package unit.br.com.banctec.modelo

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import spock.lang.Specification

class BoletimApiTest extends Specification {

    def urlBase = 'http://localhost:8080/boletins'

    def 'deveria trazer um Boletim valido'() {
        given:
        RESTClient cliente = new RESTClient("${urlBase}/2", ContentType.JSON)

        when:
        HttpResponse resposta = cliente.get(headers: ['token': 'yoshi'])

        then:
        // verificando o status da resposta
        resposta.status == 200

        and:
        // verificando o corpo da resposta
        resposta.data.id == 2
        new BoletimModelo(resposta.data)
        /*resposta.data.aluno instanceof String
        resposta.data.nota1 instanceof Number
        resposta.data.nota2 instanceof Number
        resposta.data.media instanceof Number
        resposta.data.resultado instanceof String*/
    }

    def 'deveria trazer 404 para id inválido'() {
        given:
        RESTClient cliente = new RESTClient("${urlBase}/0", ContentType.JSON)

        when:
        HttpResponse resposta = cliente.get(headers: ['token': 'yoshi'])

        then:
        // caso a resposta seja 4xx ou 5xx, é lançada uma HttpResponseException
        def ex = thrown(HttpResponseException)
        ex.statusCode == 404
    }

    // 2 cenários de teste:
    // 1. excluir um registro existente (200)
    // 2. tentar excluir um registro inexistente (404)

    def 'deveria excluir um Boletim'() {
        given:
        RESTClient cliente = new RESTClient("${urlBase}/4")

        when:
        HttpResponse resposta = cliente.delete(headers: ['token': 'yoshi'])

        then:
        resposta.status == 200
    }

    def 'deveria retornar 404 ao tentar excluir inválido'() {
        given:
        RESTClient cliente = new RESTClient("${urlBase}/0")

        when:
        HttpResponse resposta = cliente.delete(headers: ['token': 'yoshi'])

        then:
        def ex = thrown(HttpResponseException)
        ex.statusCode == 404

        // verificando o corpo da resposta de erro
        ex.response.data.text == 'Boletim 0 não encontrado ou é de outro dono'
    }
}