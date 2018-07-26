package unit.br.com.bandtec.boletimapi.repository

import br.com.bandtec.boletimapi.entity.Boletim
import br.com.bandtec.boletimapi.repository.BoletimRepository
import spock.lang.Specification

class BoletimRepositoryTest extends Specification {

    def 'deveria criar mocks'() {
        given:
        // criando um Mock da interface BoletimRepository
        def repository = Mock(BoletimRepository)

        // ensinar os comportamentos para o mock
        repository.findByIdAndToken(1, 'a') >> new Boletim(id:1, aluno: 'Zé')
        repository.findByIdAndToken(22, 'a') >> new Boletim(id:22, aluno: 'Lady')
        // em parâmetros cujo valor é irrelevante para o teste,
        // podemos usar '_'
        repository.findByIdAndToken(666, _) >> new Boletim(id:666, aluno: 'Capiroto')

        when:
        def boletim1 = repository.findByIdAndToken(1, 'a')

        then:
        boletim1.id == 1
        boletim1.aluno == 'Zé'

        when:
        // executando um método não treinado no mock
        def boletim3 = repository.findByIdAndToken(333, 'c')

        then:
        // deve retornar 'null'
        boletim3 == null

        when:
        // no segundo parâmetro, poderiamos usar qualquer valor
        def boletim666 = repository.findByIdAndToken(666, 'Jojo')

        then:
        boletim666.id == 666
        boletim666.aluno == 'Capiroto'
    }
}
