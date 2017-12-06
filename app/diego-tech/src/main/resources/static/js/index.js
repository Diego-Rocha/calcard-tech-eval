String.prototype.format = function () {
	var formatted = this;
	for (var i = 0; i < arguments.length; i++) {
		var regexp = new RegExp('\\{' + i + '\\}', 'gi');
		formatted = formatted.replace(regexp, arguments[i]);
	}
	return formatted;
};
$(function () {

	$.notify.defaults({
		autoHide: false,
		position: 'bottom right'
	})

	$('#inputCPF').mask('000.000.000-00', {reverse: true}).on('change', function () {
		var cpf = $(this).val().replace(/\D/g, '');
		if (cpf.length == 11) {
			var requestCpfJaCadastrado = $.ajax({
				type: 'GET',
				url: '/api/v1/public/pessoas/cpf/{0}/exists'.format(cpf),
				dataType: 'json'
			});
			requestCpfJaCadastrado.done(function (data) {
				$('#menuPrincipal').notify('CPF já cadastrado!', 'error');
			});
		}
	});
	$('#inputSearchCPF').mask('000.000.000-00', {reverse: true});

	var requestComboEstadosCivis = $.ajax({
		type: 'GET',
		url: '/api/v1/public/estados-civis',
		dataType: 'json'
	});
	requestComboEstadosCivis.done(function (data) {
		var optionHtmlTmpl = '<option value="{0}">{1}<\/option>';
		var optionsOutput = '';

		$.each(data, function (index, value) {
			optionsOutput += optionHtmlTmpl.format(value.id, value.descricao);
		});

		$('#inputEstadoCivil').append(optionsOutput);
	});
	requestComboEstadosCivis.fail(function (jqXHR) {
		console.error(jqXHR);
		$('#menuPrincipal').notify('Falha ao carregar lista de estados civis do servidor', 'error');
	});

	var requestComboEstados = $.ajax({
		type: 'GET',
		url: '/api/v1/public/estados',
		dataType: 'json'
	});
	requestComboEstados.done(function (data) {
		var optionHtmlTmpl = '<option value="{0}">{0}<\/option>';
		var optionsOutput = '';

		$.each(data, function (index, value) {
			optionsOutput += optionHtmlTmpl.format(value);
		});

		$('#inputEstado').append(optionsOutput);
	});
	requestComboEstados.fail(function (jqXHR) {
		console.error(jqXHR);
		$('#menuPrincipal').notify('Falha ao carregar lista de estados do servidor', 'error');
	});

	$('#formCadastroPessoa').on('submit', function (evt) {
		evt.preventDefault();
		request = $.ajax({
			method: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			url: '/api/v1/public/pessoas',
			cache: false,
			data: JSON.stringify({
				'cpf': $('#inputCPF').val().replace(/\D/g, ''),
				'nome': $('#inputNome').val(),
				'idade': $('#inputIdade').val(),
				'sexo': $('#inputSexoContainer input[name=sexo]:checked').val(),
				'estadoCivilId': $('#inputEstadoCivil').val(),
				'estado': $('#inputEstado').val(),
				'dependentes': $('#inputDependente').val(),
				'renda': $('#inputRenda').val()
			})
		});
		request.done(function (data) {
			$('#menuPrincipal').notify('Usuário Cadastrado com Sucesso!\n'
				+ 'Dados de Retorno:\n'
				+ JSON.stringify(data, null, 2), 'success');

			$('#inputCPF').val('');
			$('#inputNome').val('');
			$('#inputIdade').val('');
			$('#inputSexoContainer input[name=sexo]:eq(0)').prop('checked', true);
			$('#inputEstadoCivil').val('');
			$('#inputEstado').val('');
			$('#inputDependente').val('');
			$('#inputRenda').val('');

		});
		request.fail(function (jqXHR) {
			console.error('Erro ao Gravar Pessoa', jqXHR);
			if (jqXHR.status == 409) {
				$('#menuPrincipal').notify('Pessoa já cadastrada!', 'error');
				return;
			}
			if (jqXHR.hasOwnProperty('responseJSON')) {
				if (jqXHR.responseJSON.hasOwnProperty('errors')) {
					var errorCode = jqXHR.responseJSON.errors[0].code;
					if (errorCode == 'error.pessoa.cpf.invalid') {
						$('#menuPrincipal').notify('CPF Inválido!', 'error');
						return;
					}
					if (errorCode == 'error.pessoa.idade.outOfRange') {
						$('#menuPrincipal').notify('Idade Inválida!', 'error');
						return;
					}
					if (errorCode == 'error.pessoa.sexo.invalid') {
						$('#menuPrincipal').notify('Sexo Inválido!', 'error');
						return;
					}
					var erroCodeMatch = errorCode.match('error\.pessoa\.(.*)\.required');
					if (erroCodeMatch = +null) {
						$('#menuPrincipal').notify('Campo "{0}" não informado!'.format(erroCodeMatch[1]), 'error');
						return;
					}
					var erroCodeMatch = errorCode.match('error\.pessoaRestCadastroAnaliseCreditoDTO\.(.*)\.required');
					if (erroCodeMatch = +null) {
						$('#menuPrincipal').notify('Campo "{0}" não informado!'.format(erroCodeMatch[1]), 'error');
						return;
					}
					$('#menuPrincipal').notify('Falha ao gravar dados da pessoa no servidor!\n'
						+ 'Código do Erro:\n'
						+ errorCode + '\n'
						+ 'Para mais informações, visualize o console de seu navegador!', 'error');
					return;
				}
				if (jqXHR.responseJSON.hasOwnProperty('message')) {
					$('#menuPrincipal').notify('Falha ao gravar dados da pessoa no servidor!\n' +
						'Erro inesperado!\n' +
						'Dados de Erro:\n'
						+ jqXHR.responseJSON.message.replace(/(.{100})/g,"$1\n")
						+ '\nPara mais informações, visualize o console de seu navegador!', 'error');
					return;
				}
			}
			$('#menuPrincipal').notify('Falha ao gravar dados da pessoa no servidor!\n' +
				'Erro não tratado!\n' +
				'Codigo HTTP:' + jqXHR.status.replace(/(.{100})/g,"$1\n")
				+ '\nPara mais informações, visualize o console de seu navegador!', 'error');
		});
	});

	$('#searchForm').on('submit', function (evt) {
		evt.preventDefault();
		request = $.ajax({
			method: 'GET',
			contentType: 'application/json',
			dataType: 'json',
			url: '/api/v1/public/pessoas/cpf/' + $('#inputSearchCPF').val().replace(/\D/g, ''),
			cache: false
		});
		request.done(function (data) {
			$('#menuPrincipal').notify('Usuário encontrado!\nDados de Retorno: \n' + JSON.stringify(data, null, 2), 'success');
		});
		request.fail(function (jqXHR) {
			console.error('Pessoa nao encontrada pelo CPF', jqXHR);
			$('#menuPrincipal').notify('Pessoa não encontrada pelo CPF!', 'error');
		});
	});
});