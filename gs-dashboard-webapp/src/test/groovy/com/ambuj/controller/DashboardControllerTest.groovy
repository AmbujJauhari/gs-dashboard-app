package com.ambuj.controller

import com.ambuj.domain.SpaceLookUpDetails
import com.ambuj.domain.SpaceLookUpDto
import com.ambuj.exception.ConfigNotFoundException
import com.ambuj.service.SpaceLookUpService
import org.hamcrest.Matchers
import org.hamcrest.core.Is
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class DashboardControllerTest extends Specification {
    private DashboardController dashboardController = new DashboardController()
    private SpaceLookUpService lookUpService = Mock(SpaceLookUpService)

    private MockMvc mockMvc

    def setup() {
        dashboardController.spaceLookUpService = lookUpService
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build()
    }

    def 'request to getEnvList returns the list of gigaspace configured grids and Exceptions'() {
        given: 'sample grid names and exceptions'
        SpaceLookUpDto spaceLookUpDto = new SpaceLookUpDto()
        spaceLookUpDto.with {
            gridNames = ['grid-A', 'grid-B'] as Set
            exceptions = new ConfigNotFoundException()

            spaceLookUpDto
        }

        when: 'request posted to getEnvList'
        lookUpService.gsLookUpDetails() >> spaceLookUpDto

        ResultActions resultActions =
                mockMvc.perform(
                        get("/getEnvList"))

        then:
        resultActions.andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.gridNames', hasSize(2)))
                .andExpect(jsonPath('$.exceptions', Is.is(ConfigNotFoundException.class.name)))

    }

}
