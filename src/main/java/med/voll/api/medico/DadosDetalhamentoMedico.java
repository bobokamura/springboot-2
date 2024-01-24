package med.voll.api.medico;
import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoMedico(String nome, String email, String telefone, String crm, Especialidade especialidade, Endereco endereco, boolean ativo) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco(), medico.isAtivo());
    }
}
