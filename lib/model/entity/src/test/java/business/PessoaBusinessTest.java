package business;

import business.base.BaseTest;
import io.diego.tech.business.PessoaBusiness;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.CreditoEnum;
import io.diego.tech.enums.EstadoBrasilEnum;
import io.diego.tech.enums.EstadoCivilEnum;
import io.diego.tech.model.Pessoa;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class PessoaBusinessTest extends BaseTest<PessoaBusiness> {

	private static Map<Pessoa, CreditoEnum> tabularTestData = new LinkedHashMap<>();

	static {
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("53998266070", "Lucas", 28, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 0, new BigDecimal("2500"))), CreditoEnum.ENTRE_500_1000);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("01741673003", "Ana", 17, 'F', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("1000"))), CreditoEnum.ENTRE_100_500);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("82414048085", "Pedro", 68, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.SC, 3, new BigDecimal("8000"))), CreditoEnum.ENTRE_1500_2000);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("37326384053", "Paula", 61, 'F', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.RJ, 3, new BigDecimal("5000"))), CreditoEnum.ENTRE_1000_1500);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("43673987062", "João", 56, 'M', EstadoCivilEnum.DIVORCIADO.getId(), EstadoBrasilEnum.RJ, 2, new BigDecimal("2000"))), CreditoEnum.REPROVADO);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("14375030046", "Maria", 45, 'F', EstadoCivilEnum.DIVORCIADO.getId(), EstadoBrasilEnum.SP, 1, new BigDecimal("2000"))), CreditoEnum.REPROVADO);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("49663203056", "José", 30, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.MA, 2, new BigDecimal("8000"))), CreditoEnum.SUPERIOR_2000);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("21139285076", "Dinae", 33, 'F', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.SP, 1, new BigDecimal("10000"))), CreditoEnum.SUPERIOR_2000);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("56258968015", "Marcos", 19, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 1, new BigDecimal("400"))), CreditoEnum.RENDA_BAIXA);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("36434378033", "Suzan", 63, 'F', EstadoCivilEnum.VIUVA.getId(), EstadoBrasilEnum.MA, 3, new BigDecimal("1500"))), CreditoEnum.REPROVADO);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("69414782095", "Luci", 28, 'F', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 2, new BigDecimal("2500"))), CreditoEnum.ENTRE_100_500);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("03894632054", "Roberto", 16, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("500"))), CreditoEnum.RENDA_BAIXA);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("69075758090", "Bruno", 30, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.MA, 5, new BigDecimal("8000"))), CreditoEnum.ENTRE_1000_1500);
		tabularTestData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("95099832091", "Ariel", 33, 'F', EstadoCivilEnum.VIUVA.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("10000"))), CreditoEnum.SUPERIOR_2000);
	}

	@Test
	public void testeConvertRest() {

		PessoaRestCadastroAnaliseCreditoDTO dto = new PessoaRestCadastroAnaliseCreditoDTO();
		dto.setNome("Nome");
		dto.setCpf("21691702005");
		dto.setIdade(18);
		dto.setSexo('M');
		dto.setEstadoCivilId(1L);
		dto.setEstado(EstadoBrasilEnum.SC);
		dto.setDependentes(1);
		dto.setRenda(new BigDecimal("1000.00"));

		Pessoa entity = PessoaBusiness.convert(dto);

		Assert.assertEquals(dto.getNome(), entity.getNome());
		Assert.assertEquals(dto.getIdade(), entity.getIdade());
		Assert.assertEquals(dto.getSexo(), entity.getSexo());
		Assert.assertNotNull(entity.getEstadoCivil());
		Assert.assertEquals(dto.getEstadoCivilId(), entity.getEstadoCivil().getId());
		Assert.assertEquals(dto.getEstado(), entity.getEstado());
		Assert.assertEquals(dto.getDependentes(), entity.getDependentes());
		Assert.assertEquals(dto.getRenda(), entity.getRenda());

	}

	@Test
	public void testeConvertEmptyRest() {
		PessoaRestCadastroAnaliseCreditoDTO dto = new PessoaRestCadastroAnaliseCreditoDTO();
		Pessoa entity = PessoaBusiness.convert(dto);
		Assert.assertEquals(new Pessoa(), entity);
	}

	@Test
	public void testeCalculoCredito() {
		int i = 1;
		for (Map.Entry<Pessoa, CreditoEnum> item : tabularTestData.entrySet()) {
			Pessoa pessoa = PessoaBusiness.calcularCredito(item.getKey());
			CreditoEnum esperado = CreditoEnum.getById(item.getValue().getId());
			CreditoEnum atual = CreditoEnum.getById(pessoa.getCredito().getId());
			String mensagem = String.format("%s - Falha na pessoa: '%s - %s'", i, item.getKey().getNome(), item.getKey().getLimiteCredito());
			Assert.assertEquals(mensagem, esperado, atual);
			i++;
		}
	}

}
