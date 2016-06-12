package com.ambuj.controller

import com.ambuj.domain.DataRequestForTypeName
import com.ambuj.domain.Person
import com.ambuj.domain.SpaceLookUpDetails
import com.ambuj.domain.TestDataCreator
import com.ambuj.service.SpaceAccessorService
import org.hamcrest.core.Is
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static com.ambuj.util.TestUtil.convertObjectToJsonBytes
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class QueryControllerTest extends Specification {
    private QueryController mockMvcQueryContoller = new QueryController()
    private SpaceAccessorService accessorService = Mock(SpaceAccessorService)

    private MockMvc mockMvc

    def setup() {
        mockMvcQueryContoller.spaceAccessorService = accessorService
        mockMvc = MockMvcBuilders.standaloneSetup(mockMvcQueryContoller).build()
    }

    def 'should return list of space document in json format when rest request is posted for getAllDocumentTypesForSpace'() {
        given: 'env name'
        String envName = 'GRID-A'

        when: "post rest request for query/getAllDocumentTypesForSpace"
        accessorService.getAllDataTypesForSpace(_ as SpaceLookUpDetails) >> ['Product', 'com.ambuj.domain.Person']

        ResultActions resultActions =
                mockMvc.perform(
                        get("http://localhost:8080/query/getAllDocumentTypesForSpace").param("envName", envName))

        then: "status is 200 and response is received in json format"
        resultActions.andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)))
    }

    def 'should return all the documents of a typename when rest request is posted for query/getDataFromSpaceForType'() {
        given: 'env details'
        DataRequestForTypeName dataRequestForTypeName = new DataRequestForTypeName();
        dataRequestForTypeName.setCriteria("")
        dataRequestForTypeName.setDataType(Person.class.simpleName)
        dataRequestForTypeName.setGridName('Grid-A')

        Object[] sampleData = TestDataCreator.getSampleData(2) as Object[]

        when: 'post rest request for query/getDataFromSpaceForType'
        accessorService.getAllObjectsFromSpaceForTypeName('Grid-A', Person.class.simpleName, "") >> sampleData
        accessorService.getSpaceIdFieldNameForType('Grid-A', Person.class.simpleName) >> 'name'

        ResultActions resultActions = mockMvc.perform(post('http://localhost:8080/query/getDataFromSpaceForType')
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dataRequestForTypeName)))

        then: 'status is 200 and response is json document with headers and data'
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.spaceIdFieldName', Is.is('name')))
    }

}
