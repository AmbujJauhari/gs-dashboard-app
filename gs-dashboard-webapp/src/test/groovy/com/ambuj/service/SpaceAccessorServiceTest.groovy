package com.ambuj.service

import com.ambuj.domain.DetailedDataEntry
import com.ambuj.domain.Person
import com.ambuj.domain.TestDataCreator
import com.gigaspaces.query.IdQuery
import com.j_spaces.core.client.SQLQuery
import org.openspaces.core.GigaSpace
import org.springframework.integration.support.MutableMessage
import org.springframework.integration.transformer.ObjectToMapTransformer
import spock.lang.Specification

class SpaceAccessorServiceTest extends Specification {
    SpaceLookUpService spaceLookUpService = Mock()
    SpaceAccessorService accessorService

    GigaSpace gsProxy = Mock()

    def setup() {
        spaceLookUpService.getSpace(_ as String) >> gsProxy
        accessorService = new SpaceAccessorService(spaceLookUpService: spaceLookUpService)
    }

    def 'should call readmultiple function on gigaspace proxy to get all objects from space for typename'() {
        given: 'environment name'
        String envName = 'Grid-A'
        String dataType = 'data'
        String criteria = 'some-criteria'

        when: 'call space accessor service to get all objects from space for typename'
        accessorService.getAllObjectsFromSpaceForTypeName(envName, dataType, criteria)

        then: 'readMultiple method is invoked on gigapsace proxy'
        1 * gsProxy.readMultiple(_ as SQLQuery)
    }

    def 'should return map of field name and value when requested for detailed details for any typename'() {
        given: 'environment details and sample person object'
        String envName = 'Grid-A'
        String dataType = Person.class.simpleName
        String spaceId = 'some-criteria'

        TestDataCreator testDataCreator = new TestDataCreator()
        Person samplePersonDataForTest = testDataCreator.getSampleData()
        ObjectToMapTransformer mapTransformer = new ObjectToMapTransformer();

        when: 'request all object for typename'
        gsProxy.readById(_ as IdQuery) >> samplePersonDataForTest
        Map<String, Object> detailedFieldValueMap =
                accessorService.getDetailedDataFromSpaceForTypeNameWithSpaceId(envName, dataType, spaceId)

        then: 'should return flattened object as a key value pair'
        mapTransformer.doTransform(new MutableMessage<Object>(samplePersonDataForTest)) == detailedFieldValueMap
    }

    def 'should call gigaspace write method when updating an object'() {
        given: 'environment details, sample person object and updated object'
        String envName = 'Grid-A'
        String dataType = Person.class.simpleName
        String spaceId = 'name'

        TestDataCreator testDataCreator = new TestDataCreator()
        Person samplePersonDataForTest = testDataCreator.getSampleData()
        ObjectToMapTransformer mapTransformer = new ObjectToMapTransformer();
        Map<String, Object> fieldNameValueMap = mapTransformer.doTransform(new MutableMessage<Object>(samplePersonDataForTest))
        DetailedDataEntry[] detailedDataEntries = fieldNameValueMap.collect { Map.Entry<String, Object> entry ->
            DetailedDataEntry detailedDataEntry = new DetailedDataEntry()
            if (detailedDataEntry.key == 'age') {
                detailedDataEntry.setKey(entry.key)
                detailedDataEntry.setValue(92)
            } else {
                detailedDataEntry.setKey(entry.key)
                detailedDataEntry.setValue(entry.value)
            }
            detailedDataEntry
        } as DetailedDataEntry[]

        when: 'request for updating the object'
        gsProxy.takeById(_ as IdQuery) >> samplePersonDataForTest
        accessorService.updateDataForTypeNameWithSpaceId(envName, dataType, spaceId, detailedDataEntries)

        then: 'calls to gigaspace write method'
        1 * gsProxy.write(_ as Person)
    }
}