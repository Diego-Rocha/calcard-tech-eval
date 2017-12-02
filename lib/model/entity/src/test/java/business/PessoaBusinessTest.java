package business;

import business.base.BaseTest;
import io.diego.tech.business.PessoaBusiness;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.EstadoBrasilEnum;
import io.diego.tech.model.Pessoa;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PessoaBusinessTest extends BaseTest<PessoaBusiness> {

	@Test
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

}
