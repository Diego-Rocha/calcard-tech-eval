package business;

import business.base.BaseTest;
import io.diego.tech.business.PessoaBusiness;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.CreditoEnum;
import io.diego.tech.enums.EstadoBrasilEnum;
import io.diego.tech.enums.EstadoCivilEnum;
import io.diego.tech.model.Pessoa;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PessoaBusinessTest extends BaseTest<PessoaBusiness> {

	private static Map<Pessoa, CreditoEnum> tabularData = new LinkedHashMap<>();

	static {
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Lucas", 28, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 0, new BigDecimal("2500"))), CreditoEnum.ENTRE_500_1000);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Ana", 17, 'F', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("1000"))), CreditoEnum.ENTRE_100_500);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Pedro", 68, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.SC, 3, new BigDecimal("8000"))), CreditoEnum.ENTRE_1500_2000);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Paula", 61, 'F', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.RJ, 3, new BigDecimal("5000"))), CreditoEnum.ENTRE_1000_1500);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("João", 56, 'M', EstadoCivilEnum.DIVORCIADO.getId(), EstadoBrasilEnum.RJ, 2, new BigDecimal("2000"))), CreditoEnum.REPROVADO);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Maria", 45, 'F', EstadoCivilEnum.DIVORCIADO.getId(), EstadoBrasilEnum.SP, 1, new BigDecimal("2000"))), CreditoEnum.REPROVADO);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("José", 30, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.MA, 2, new BigDecimal("8000"))), CreditoEnum.SUPERIOR_2000);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Dinae", 33, 'F', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.SP, 1, new BigDecimal("10000"))), CreditoEnum.SUPERIOR_2000);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Marcos", 19, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 1, new BigDecimal("400"))), CreditoEnum.RENDA_BAIXA);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Suzan", 63, 'F', EstadoCivilEnum.VIUVA.getId(), EstadoBrasilEnum.MA, 3, new BigDecimal("1500"))), CreditoEnum.REPROVADO);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Luci", 28, 'F', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SC, 2, new BigDecimal("2500"))), CreditoEnum.ENTRE_100_500);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Roberto", 16, 'M', EstadoCivilEnum.SOLTEIRO.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("500"))), CreditoEnum.RENDA_BAIXA);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Bruno", 30, 'M', EstadoCivilEnum.CASADO.getId(), EstadoBrasilEnum.MA, 5, new BigDecimal("8000"))), CreditoEnum.ENTRE_1000_1500);
		tabularData.put(PessoaBusiness.convert(new PessoaRestCadastroAnaliseCreditoDTO("Ariel", 33, 'F', EstadoCivilEnum.VIUVA.getId(), EstadoBrasilEnum.SP, 0, new BigDecimal("10000"))), CreditoEnum.SUPERIOR_2000);
	}

	// @Test
	public void testeConvertRest() {

		PessoaRestCadastroAnaliseCreditoDTO dto = new PessoaRestCadastroAnaliseCreditoDTO();
		dto.setNome("Nome");
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
		for (Map.Entry<Pessoa, CreditoEnum> item : tabularData.entrySet()) {
			Pessoa pessoa = PessoaBusiness.calcularCredito(item.getKey());
			Assert.assertEquals(String.format("%s - Falha na pessoa: '%s - %s'", i, item.getKey().getNome(), item.getKey().getLimiteCredito()), CreditoEnum.getById(item.getValue().getId()), CreditoEnum.getById(pessoa.getCredito().getId()));
			i++;
		}
	}

}
