package com.ltp.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



// sacn beans
@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

	// @Autowired
	// private GradeController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// assertNotNull(controller);
		assertNotNull(mockMvc);
	}

	@Test
	public void testShowGradeForm() throws Exception{
		// build a mock request
		RequestBuilder request = MockMvcRequestBuilders.get("/?id=123");

		// perform the request
		mockMvc.perform(request)
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("form"))
			.andExpect(model().attributeExists("grade"));

	}

	@Test
	public void testSuccessfulSubmission() throws Exception {
		// build a mock request
		RequestBuilder request = (MockMvcRequestBuilders.post("/handleSubmit")
					.param("name", "Harry")
					.param("subject", "potion")
					.param("score", "C"));

		mockMvc.perform(request)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/grades"));
	}

	@Test
	public void testUnsuccessfulSubmission() throws Exception {
		RequestBuilder request = (MockMvcRequestBuilders.post("handleSubmit"))
				.param("name", "  ")
				.param("subject", "5200")
				.param("score", "100");

		mockMvc.perform(request)
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("form"));

	}

	@Test
	public void testGetGrades() throws Exception {
		RequestBuilder request = (MockMvcRequestBuilders.get("/grades"));
		mockMvc.perform(request)
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("grades"))
			.andExpect(model().attributeExists("grades"));
	}

}
