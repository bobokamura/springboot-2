package med.voll.api.domain.medico;

public record DadosListaMedicos(Long id, String nome, String email , Especialidade especialidade, String crm, boolean ativo) {

    public DadosListaMedicos(Medico entity) {
        this(entity.getId(), entity.getNome(), entity.getEmail(), entity.getEspecialidade(), entity.getCrm(), entity.isAtivo());
    }
}
