package unit.br.com.bandtec.boletimapi.controller

import br.com.bandtec.boletimapi.controllers.BoletimController
import br.com.bandtec.boletimapi.entity.Boletim
import br.com.bandtec.boletimapi.repository.BoletimRepository
import spock.lang.Specification

class BoletimControllerTest extends Specification {

    def 'deveria trazer um Boletim ou 404 no get'() {
        given:
        BoletimController controller = new BoletimController()

        // criando e configurando o mock
        BoletimRepository repositoryMock = Mock(BoletimRepository)
        repositoryMock.findByIdAndToken(1, _) >> new Boletim()

        controller.repository = repositoryMock

        when:
        def encontrado = controller.getUm(1, 'achei')

        then:
        encontrado.statusCodeValue == 200
        encontrado.body instanceof Boletim

        when:
        def naoencontrado = controller.getUm(0, 'não achei')

        then:
        naoencontrado.statusCodeValue == 404
        naoencontrado.body == null
    }

    def 'deveria criar ou retornar 400'() {
        given:
        def bvalido = new Boletim(id:1)
        def binvalido = new Boletim(id:-80)
        def msgErro = 'Deu ruim, código negativo não rola!'

        BoletimController controller = new BoletimController()

        BoletimRepository repositoryMock = Mock(BoletimRepository)
        repositoryMock.save(binvalido) >> { throw new Exception(msgErro) }

        controller.repository = repositoryMock

        when:
        def resultado = controller.criar(bvalido, 'valido')

        then:
        resultado.statusCodeValue == 201

        when:
        def resultado2 = controller.criar(binvalido, 'embaçado')

        then:
        resultado2.statusCodeValue == 400
        resultado2.body == msgErro

    }

}
