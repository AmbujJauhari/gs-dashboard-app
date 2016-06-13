package com.ambuj.controller

import com.ambuj.domain.SpaceLookUpDetails
import com.ambuj.service.SpaceLookUpService
import org.hamcrest.Matchers
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class DashboardControllerTest extends Specification {
    private DashboardController dashboardController = new DashboardController()
    private SpaceLookUpService lookUpService = Mock(SpaceLookUpService)

    private MockMvc mockMvc

    def setup() {
        dashboardController.spaceLookUpService = lookUpService
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build()
    }

    @Ignore
    def 'request to index page returns the list of gigaspace configured grids'() {
        when: 'request to index page'
        lookUpService.gsLookUpDetails() >> [
                {
                    SpaceLookUpDetails gridADetails = new SpaceLookUpDetails()
                    gridADetails.envName = 'Grid-A'
                    gridADetails.url = false

                    gridADetails
                }, {
                    SpaceLookUpDetails gridBDetails = new SpaceLookUpDetails()
                    gridBDetails.envName = 'Grid-A'
                    gridBDetails.url = false

                    gridBDetails
                }]

        ResultActions resultActions =
                mockMvc.perform(
                        get("http://localhost:8080/index"))

        then:
        resultActions.andReturn().response.status == HttpStatus.OK.value()
        resultActions.andExpect(view().name("dashboard"))
                .andExpect(MockMvcResultMatchers.model().attribute('gsEnvList', Matchers.notNullValue()))

    }

    @Ignore
    def 'request to home page redirects to index page'() {
        when:'request to homepage'
        ResultActions resultActions =
                mockMvc.perform(
                        get("http://localhost:8080/"))

        then:'requet is redirected to index'
        resultActions.andReturn().response.status == HttpStatus.FOUND.value()
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl('/index'))
    }
}
