package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;
import med.voll.api.exceptions.IntegrityViolationException;
import med.voll.api.exceptions.ResourceNotFoundException;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        try {
            var medico = medicoRepository.save(new Medico(dados));
            return ResponseEntity.created(UriComponentsBuilder
                            .fromUriString("medicos/{id}")
                            .buildAndExpand(medico.getId())
                            .toUri())
                    .body(new DadosDetalhamentoMedico(medico));
        } catch (Exception e) {
            throw new IntegrityViolationException(messageSource.getMessage(
                    "message.medico.integrity-error",
                    null,
                    LocaleContextHolder.getLocale()
            ));
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateMedico(@PathVariable Long id, @RequestBody @Valid DadosAtualizaMedico dados) {
        var medico = medicoRepository.getReferenceById(id);
        medico.atualizaMedico(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/buscamedico/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> findById(@PathVariable Long id) {
        var medico = medicoRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
                "message.medico.not-found",
                null,
                LocaleContextHolder.getLocale()
        )));
        return ResponseEntity.ok().body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<List<Medico>> consultarMedicos() {
        return ResponseEntity.ok().body(medicoRepository.findAll());
    }

    @GetMapping("/paginada")
    public ResponseEntity<Page<DadosListaMedicos>> consultarMedicosPaginada(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(medicoRepository.findAllMedicosPage(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            medicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/exclusaoLogica/{id}")
    public ResponseEntity<Void> exclusaoLogica(@PathVariable Long id) {
        try {
            var medico = medicoRepository.getReferenceById(id);
            medico.excluir();
            medicoRepository.save(medico);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }


}
