package br.com.dvaltrick.cities;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dvaltrick.cities.TexoCitiesApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,
				classes=TexoCitiesApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class ApiTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void ApiControllerTests() throws Exception{
		//Testa se todas as capitais retornam corretamente e em ordem alfabética
		mockMvc.perform(get("/api/states/capitals")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(27)))
				.andExpect(jsonPath("$[0].noAccents", Matchers.equalToIgnoringCase("Aracaju")))
				.andExpect(jsonPath("$[4].noAccents", Matchers.equalToIgnoringCase("Brasilia")))
				.andExpect(jsonPath("$[26].noAccents", Matchers.equalToIgnoringCase("Vitoria")));
		
		//Testa se antes das alterações na base de dados os resultado de estados com mais e menos cidades é respeitado
		mockMvc.perform(get("/api/cities/moreandless")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.more", Matchers.notNullValue()))
				.andExpect(jsonPath("$.more.uf", Matchers.equalToIgnoringCase("MG")))
				.andExpect(jsonPath("$.more.quantidade", Matchers.equalTo(853)))
				.andExpect(jsonPath("$.less", Matchers.notNullValue()))
				.andExpect(jsonPath("$.less.uf", Matchers.equalToIgnoringCase("DF")))
				.andExpect(jsonPath("$.less.quantidade", Matchers.equalTo(1)));
		
		//Testa se a paginação está trazendo a quantidade pré-definida de resultados na lista
		mockMvc.perform(get("/api/cities/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(50)));
		
		mockMvc.perform(get("/api/cities/20")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(50)));
		
		//Testa se quando selecionado um index que não existe é retornado BadRequest
		mockMvc.perform(get("/api/cities/0")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		//Testa se os filtros estão funcionando corretamente
		mockMvc.perform(get("/api/cities?uf=SC")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(293)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(293)));
		
		mockMvc.perform(get("/api/cities?microregion=Joinville")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(11)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(11)));
		
		mockMvc.perform(get("/api/cities?mesoregion=Norte Catarinense")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(26)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(26)));
		
		mockMvc.perform(get("/api/cities?ibge_id=4201307")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.lista[0].name", Matchers.equalToIgnoringCase("Araquari")))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(1)));
				
		//Teste de deleção de Joinville
		mockMvc.perform(delete("/api/cities/4447")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		
		//Teste de deleção de Brasilia
		mockMvc.perform(delete("/api/cities/5565")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());

		//Teste de deleção de não existente
		mockMvc.perform(delete("/api/cities/999999")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		//Testa se todas as capitais retornam corretamente e em ordem alfabética após excluri Brasilia
		mockMvc.perform(get("/api/states/capitals")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(26)))
				.andExpect(jsonPath("$[0].noAccents", Matchers.equalToIgnoringCase("Aracaju")))
				.andExpect(jsonPath("$[4].noAccents", Matchers.equalToIgnoringCase("Campo Grande")))
				.andExpect(jsonPath("$[25].noAccents", Matchers.equalToIgnoringCase("Vitoria")));
		
		//Testa se os filtros estão funcionando após a remoção de Joinville
		mockMvc.perform(get("/api/cities?uf=SC")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(292)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(292)));
		
		mockMvc.perform(get("/api/cities?microregion=Joinville")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(10)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(10)));
		
		mockMvc.perform(get("/api/cities?mesoregion=Norte Catarinense")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(25)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(25)));
		
		mockMvc.perform(get("/api/cities?ibge_id=4209102")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista.*", Matchers.hasSize(0)))
				.andExpect(jsonPath("$.quantidade", Matchers.equalTo(0)));
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
}
