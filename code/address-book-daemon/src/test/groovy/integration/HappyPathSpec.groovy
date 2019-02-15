package integration

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared

@Testcontainers
class HappyPathSpec extends BaseSpec {

    @Shared
    GenericContainer container = newAddressBookApiContainer()

    @Shared
    CloseableHttpClient client = HttpClients.createDefault()

    def 'obtains the oldest contact'() {
        when: 'sending request to get the oldest contact'
        def response = client.execute new HttpGet(baseUrl(container).concat(Path.OLDEST.value()))

        and: 'parsing entity'
        def message = EntityUtils.toString response.entity

        then: 'message should contain contact'
        message == 'The oldest contact is Wes Jackson who is 44 years old'
    }

    def 'counts males'() {
        when: 'sending request to count males'
        def response = client.execute new HttpGet(baseUrl(container).concat(Path.COUNT_MALES.value()))

        and: 'parsing entity'
        def message = EntityUtils.toString response.entity

        then: 'get expected males count'
        message as int == 3
    }

    def 'tells how many years contact is older than other'() {
        given: 'Bill'
        final billNamePattern = 'Bill'

        and: 'Paul'
        final paulNamePattern = 'Robinson'

        when: 'sending request to compare contacts'
        def response = client.execute new HttpGet(baseUrl(container).concat(Path.COMPARE.value(billNamePattern, paulNamePattern)))

        and: 'parsing entity'
        def message = EntityUtils.toString response.entity

        then: 'message has expected days value'
        message == 'Bill oldest person is Wes Jackson who is 44 years old'
    }
}

