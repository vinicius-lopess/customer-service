package com.unbox.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unbox.customer.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class CustomerServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private String customerJson(@NotNull @Size(min = 3, max = 100) String nome,
			@NotNull @Size(min = 3, max = 100) String sobrenome, @NotNull String numeroCpf,
			@NotNull Date dataNascimento, @NotNull String genero, @NotNull String endereco, @NotNull String cidade,
			@NotNull String uf) throws Exception {

		Customer anObject = new Customer(nome, sobrenome, numeroCpf, dataNascimento, genero, endereco, cidade, uf);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(anObject);
	}

	private Customer[] getCustomersFromJson(String json) throws Exception {
		return (Customer[]) (new ObjectMapper()).readValue(json, Customer[].class);
	}

	@Test
	@Order(1)
	public void inserirCustomerUm() throws Exception {
		mockMvc.perform(post("/api/customer/salvar").contentType(APPLICATION_JSON_UTF8)
				.content(customerJson("Leandro Vinicius", "Silva Lopes", "82883890153", new Date(), "M",
						"Q 208 LT 11 APT 1302", "Águas Claras", "DF")))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	public void inserirCustomerDois() throws Exception {
		mockMvc.perform(post("/api/customer/salvar").contentType(APPLICATION_JSON_UTF8).content(customerJson(
				"Vinicius", "Lopes", "82883890152", new Date(), "M", "Q 208 LT 11 APT 1302", "Águas Claras", "DF")))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(3)
	public void inserirCustomerTres() throws Exception {
		mockMvc.perform(post("/api/customer/salvar").contentType(APPLICATION_JSON_UTF8).content(customerJson(
				"Vinicius", "Silva", "82883890151", new Date(), "M", "Q 208 LT 11 APT 1302", "Águas Claras", "DF")))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(4)
	public void inserirCustomerQuatro() throws Exception {
		mockMvc.perform(post("/api/customer/salvar").contentType(APPLICATION_JSON_UTF8).content(customerJson(
				"Vinicius", "Silva Lopes", "82883890150", new Date(), "M", "Q 208 LT 11 APT 1302", "Águas Claras", "DF")))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(5)
	public void inserirCustomerCincoComPropsNullDeveFalar() throws Exception {
		mockMvc.perform(post("/api/customer/salvar").contentType(APPLICATION_JSON_UTF8)
				.content(customerJson(null, null, null, new Date(), "M", "Q 208 LT 11 APT 1302", "Águas Claras", "DF")))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@Order(6)
	public void deveRetornarTodosOsCostumersCadastrados() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/api/customer/listar/todos")).andDo(print())
				.andExpect(status().isOk()).andReturn();
		Customer[] customers = getCustomersFromJson(result.getResponse().getContentAsString());
		assertThat(customers[0].getNome()).isEqualTo("Leandro Vinicius");
		assertThat(customers.length).isEqualTo(4);
	}

}
