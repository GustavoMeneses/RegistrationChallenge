package com.registration.rest.erro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@ApiModel(description = "Um erro de aplicação. Seguindo a especificação do RFC 7870 - https://tools.ietf.org/html/rfc7807")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro implements ETaged{
	
	@NonNull
	@JsonIgnore
	@Builder.Default
	private HttpStatus status = Erro.exception("status");

	@Getter(lazy = true)
	@ApiModelProperty(
		value = 
			"Classificação do tipo de erro. "
			+ "Observar que essa URL pode ser relativa. "
			+ "Seguindo o template '/refs/erros/{CODIGO}' https://tools.ietf.org/html/rfc7807#section-3.1"
	)
	private final String type = String.format("/refs/erros/%s", this.codigo);
	
	@Builder.Default
	@ApiModelProperty(value = "Título do erro encontrando na aplicação")
	private String title = Erro.exception("title");

	@ApiModelProperty(value = "Detalhe do erro encontrado na aplicação")
	private String detail;

	@ApiModelProperty(value = "Mensagem de erro para o usuáro")
	private String mensagem;

	@ApiModelProperty(value = "Lista de mensagens de validação")
	private List<String> mensagens;
	
	@NonNull
	@Builder.Default
	@JsonProperty(value = "CODIGO")
	@ApiModelProperty(value = "Código interno do erro encontrando na aplicação")
	private String codigo = Erro.exception("codigo");


	@ApiModelProperty(value = "Instância do erro encontrado na aplicação")
	private String instance;
	
	@JsonProperty(value = "Status")
	@ApiModelProperty(value = "Código do erro encontrado")
	private int getStatusCode() {
		return this.status.value();
	}

	@ApiModelProperty(value = "Detalhes extras")
	private Map<String, Object> extra;

	public static final <T> T exception(String atributo) throws IllegalStateException {
		throw new IllegalStateException(String.format("O atributo '%s' nunca foi informado", atributo));
	}
	
}
