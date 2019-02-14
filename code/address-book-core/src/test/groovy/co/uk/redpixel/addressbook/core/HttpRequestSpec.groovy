package co.uk.redpixel.addressbook.core

import spock.lang.Specification

import static co.uk.redpixel.addressbook.core.http.HttpMethod.POST
import static java.util.Collections.emptyMap

class HttpRequestSpec extends Specification {

    def 'should successfully parse request'() {
        given: 'test data'
        def content = 'POST /submit HTTP/1.1\r\n'

        and: 'and expectations'
        def expected = new HttpRequest(new URI('/submit'), 'HTTP/1.1', POST, emptyMap(), '')

        when: 'request created'
        def actual = HttpRequest.of content

        then: 'should match'
        actual == expected
    }

    def 'should successfully parse request with headers'() {
        given: 'test data'
        def content = 'POST /submit HTTP/1.1\r\n' +
                      'Host: localhost:8080\r\n' +
                      'content-length: 0\r\n'

        def headers = ['Host': 'localhost:8080', 'content-length': '0']
        def expected = new HttpRequest(new URI('/submit'), 'HTTP/1.1', POST, headers, '')

        when: 'request created'
        def actual = HttpRequest.of content

        then: 'should have expected fields'
        actual == expected
    }

    def 'should successfully parse request with body'() {
        given: 'test data'
        def content = 'POST /some/path HTTP/1.1\r\n' +
                      'Authorization: Bearer 1327fc78b00445139a89f5b16c320e81\r\n' +
                      'cache-control: no-cache\r\n' +
                      'Content-Type: text/plain\r\n' +
                      'Accept: */*\r\n' +
                      'Host: localhost:8080\r\n' +
                      'content-length: 1\r\n' +
                      '\r\n' +
                      '1'

        and: 'expectations'
        def headers = [
                'Authorization': 'Bearer 1327fc78b00445139a89f5b16c320e81',
                'cache-control': 'no-cache',
                'Content-Type': 'text/plain',
                'Accept': '*/*',
                'Host': 'localhost:8080',
                'content-length': '1'
        ]

        def expected = new HttpRequest(new URI('/some/path'), 'HTTP/1.1', POST, headers, '1')

        when: 'request created'
        def actual = HttpRequest.of content

        then: 'should have expected fields'
        actual == expected
    }
}
