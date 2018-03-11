# Guia de uso:
Por favor colocar suas sugestes abaixo, na aréa destinada a isso, a seguir vamos discutir as ideias e então começar a codar :-)

# CrackerBuild
tool for generating micro-services from a monolithic project

## Purpose
The purpose is to create a java annotation that will be intercepted during the source code build cycle.

The tool should extract the original source code method and create a new project, only with the extracted method, by loading technology into the new generated jar.

This new jar should be completely self-contained and self-sufficient and should handle remote calls.

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
   -utilizar JSR-269 Annotation Processing para interceptar a anotação.
   
   -utilizar ByteBuddy (ou cglib ou javaassist?) para construção do "novo projeto".
   
   -embarcar spring-boot com Hystrix ou Hazelcast ou JGroup para execução distribuida. 
