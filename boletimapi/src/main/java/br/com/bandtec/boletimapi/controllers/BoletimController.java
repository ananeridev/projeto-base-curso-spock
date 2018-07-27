package br.com.bandtec.boletimapi.controllers;

import br.com.bandtec.boletimapi.entity.Boletim;
import br.com.bandtec.boletimapi.repository.BoletimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author José Yoshiriro
 */
@RestController
@RequestMapping("/boletinsruim")
public class BoletimController {

    @Autowired
    private BoletimRepository repository;

    @GetMapping
    public ResponseEntity getVarios(
            @RequestParam(value = "aluno", required = false) String aluno,
            @RequestHeader("token") String token) {
        List<Boletim> lista;
        if (aluno == null) {
            lista = repository.findByTokenOrderByAluno(token);

        } else {
            lista = repository.findByAlunoLikeAndTokenOrderByAluno(aluno, token);
        }

        if (lista!=null) {
            return ResponseEntity.ok(lista);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUm(@PathVariable("id") Long id, @RequestHeader("token") String token) {
        Boletim boletim = repository.findByIdAndToken(id, token);
        if (boletim != null) {
            return ResponseEntity.ok(boletim);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody Boletim boletim, @RequestHeader("token") String token) {
        boletim.setToken(token);
        try {
            repository.save(boletim);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getCause().getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id, @RequestHeader("token") String token) {
        repository.deleteByIdAndToken(id, token);
        return ResponseEntity.ok().build();
    }
}
