# Guia de uso:
Por favor colocar suas sugestes abaixo, na aréa destinada a isso, a seguir vamos discutir as ideias e ento começar a codar :-)

# CrackerBuild
tool for generating micro-services from a monolithic project

## Purpose
O propósito é criar uma anotação java que será interceptada durante o ciclo de construção do código fonte.

A ferramenta deverá extrair o método do código fonte original e criar um novo projeto, apenas com o método extraido, embarcando tecnologia no novo jar gerado.

Esse novo jar deve ser completamente autonomo e auto-sulficiente e deve atender a chamadas remotas.

## Example:

	package example;

	import com.jimsp.crackerbuild.annotation.MicroserviceFunction;

	import br.com.exemplo.logic.InternalDependency;
	import br.com.exemplo.logic.CallAResponse;

	import com.extrenalservice.client.IntegrationClient;
	import com.extrenalservice.dto.ExernalData;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	@Service
	public class MonoliticSourceCode {

		@Autowired
		private InternalDependency internalDependency;

		@Autowired
		private IntegrationClient integrationClient;

		@MicroserviceFunction
		public FunctionResponse function(final FunctionRequest functionRequest){
			final CallAResponse callAResponse = internalDependency.callA(functionRequest);
			final ExernalData externalData = integrationClient.execute(callAResponse);
			return internalDependency.logic(functionRequest, externalData);
		}
	}

## Expected
A jar file executable with an address to execute the annotated method.



# Sugestões:

Ale:
utilizar JSR-269 Annotation Processing para interceptar a anotação.
utilizar ByteBuddy (ou cglib ou javaassist?) para construção do "novo projeto".
embarcar spring-boot com Hystrix ou Hazelcast ou JGroup para execução distribuida. 
