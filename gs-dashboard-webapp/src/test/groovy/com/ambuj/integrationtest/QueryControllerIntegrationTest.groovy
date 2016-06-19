package com.ambuj.integrationtest

import com.ambuj.domain.*
import com.ambuj.util.TestUtil
import groovy.json.JsonSlurper
import org.openspaces.core.GigaSpace
import org.openspaces.core.GigaSpaceConfigurer
import org.openspaces.core.space.UrlSpaceConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.integration.support.MutableMessage
import org.springframework.integration.transformer.ObjectToMapTransformer
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@ContextConfiguration(locations = ['classpath:/sandbox-servlet-test.xml'])
@WebAppConfiguration
class QueryControllerIntegrationTest extends Specification {

    @Autowired
    private WebApplicationContext webApplicationContext

    private MockMvc mockMvc

    private GigaSpace gigaSpace

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/processorSpace");
        gigaSpace = new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }

    def 'should return list of space document in when rest request is posted for getAllDocumentTypesForSpace'() {
        given: 'env name'
        String gridName = 'Grid-A'
        String spaceName = 'processorSpace'

        gigaSpace.write(TestDataCreator.getSampleData())

        when: "post rest request for query/getAllDocumentTypesForSpace"
        MockHttpServletResponse response =
                mockMvc.perform(
                        get("http://localhost:8080/query/getAllDocumentTypesForSpace")
                                .param("gridName", gridName)
                                .param('spaceName', spaceName))
                        .andReturn().getResponse()

        def content = new JsonSlurper().parseText(response.getContentAsString())

        then: "status is 200 and response is received in json format"
        response.status == HttpStatus.OK.value()
        content.sort() == ['java.lang.Object', 'Product', 'com.ambuj.domain.Person'].sort()
    }

    @Unroll
    def 'should get data from space with headers and spaceIdFieldName filtered by criteria [#criteria]'() {
        given: 'typename gridname and criteria'
        DataRequestForTypeName dataRequestForTypeName = new DataRequestForTypeName()
        dataRequestForTypeName.gridName = 'Grid-A'
        dataRequestForTypeName.spaceName = 'processorSpace'
        dataRequestForTypeName.dataType = Person.class.name
        dataRequestForTypeName.criteria = criteria

        and: 'publish test data'
        gigaSpace.writeMultiple(TestDataCreator.getSampleData(3) as Object[])

        when: "post rest request for query/getAllDocumentTypesForSpace"
        MockHttpServletResponse response =
                mockMvc.perform(post("http://localhost:8080/query/getDataFromSpaceForType")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(dataRequestForTypeName)))
                        .andReturn().getResponse()

        def content = new JsonSlurper().parseText(response.getContentAsString())

        then: "status is 200 and response is received in json format"
        response.status == HttpStatus.OK.value()
        content.spaceIdFieldName == 'name'
        content.fieldNames.sort() == ['address', 'fathersName', 'nickName', 'mothersName', 'name', 'company', 'class', 'age'].sort()
        content.dataPerField.size() == noOfExpectedObjects

        where:
        criteria    || noOfExpectedObjects
        ''          || 3
        "name='p0'" || 1
    }

    def 'should return detailed data in a map'() {
        given: 'env name'
        String spaceId = 'p0'
        String dataType = Person.class.name
        String gridName = 'Grid-A'
        String spaceName = 'processorSpace'

        gigaSpace.write(TestDataCreator.getSampleData())

        when: "post rest request for query/getAllDocumentTypesForSpace"
        MockHttpServletResponse response =
                mockMvc.perform(
                        get("http://localhost:8080/query/getDataFromSpaceForTypeForSpaceId")
                                .param("gridName", gridName)
                                .param('dataType', dataType)
                                .param('spaceId', spaceId)
                                .param('spaceName', spaceName)
                ).andReturn().getResponse()

        def content = new JsonSlurper().parseText(response.getContentAsString())

        then: "status is 200 and detailed object proeprties are retrieved"
        response.status == HttpStatus.OK.value()
        content
    }

    def 'should update the data in space when edited from detailed view'() {
        given: 'typename gridname and criteria'
        Person sampleData = TestDataCreator.getSampleData();

        DetailedDataUpdateDto detailedDataUpdateDto = new DetailedDataUpdateDto()
        detailedDataUpdateDto.gridName = 'Grid-A'
        detailedDataUpdateDto.dataTypeName = Person.class.name
        detailedDataUpdateDto.spaceIdName = 'name'
        detailedDataUpdateDto.spaceName='processorSpace'

        and: 'publish test data'
        gigaSpace.write(sampleData)
        sampleData.age = 220

        ObjectToMapTransformer transformer = new ObjectToMapTransformer()
        Map<String, Object> fieldValueMap = transformer.doTransform(new MutableMessage<Object>(sampleData)) as Map<String, Object>;
        DetailedDataEntry[] detailedDataEntries = fieldValueMap.collect { String key, Object value ->
            DetailedDataEntry dataEntry = new DetailedDataEntry()
            dataEntry.key = key
            dataEntry.value = value

            dataEntry
        } as DetailedDataEntry[]

        detailedDataUpdateDto.detailedDataEntry = detailedDataEntries

        when: "post rest request for query/updateDataInSpaceForTypeForSpaceId"

        mockMvc.perform(post("http://localhost:8080/query/updateDataInSpaceForTypeForSpaceId")
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(detailedDataUpdateDto)))

        Person expectedUpdated = gigaSpace.readById(Person.class, 'p0')

        then: "status is 200 and response is received in json format"
        expectedUpdated.age == 220

    }

    def cleanup() {
        gigaSpace.clear(null)
    }
}