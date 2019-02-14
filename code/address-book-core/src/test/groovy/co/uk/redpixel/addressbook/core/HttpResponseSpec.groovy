package co.uk.redpixel.addressbook.core

import co.uk.redpixel.addressbook.core.config.Endpoint
import co.uk.redpixel.addressbook.core.config.Routes
import spock.lang.Specification
import spock.lang.Unroll

import static co.uk.redpixel.addressbook.core.HttpResponse.ok
import static co.uk.redpixel.addressbook.core.HttpResponse.notFound
import static co.uk.redpixel.addressbook.core.HttpResponse.badRequest

class HttpResponseSpec extends Specification {

    @Unroll
    def 'should correctly translate request object to string representation: #representation'() {
        when: 'response is formatted to string'
        def actual = response.toString()

        then: 'should have expected format'
        actual =~ representation

        where: 'data'
        response             | representation
        notFound()           | 'HTTP/1.1 404 Not Found\r\nDate: .* GMT\r\nContent-Type: text/plain; charset=UTF-8\r\nContent-Length: 0\r\n'
        badRequest('Test')   | 'HTTP/1.1 400 Bad Request\r\nDate: .* GMT\r\nContent-Type: text/plain; charset=UTF-8\r\nContent-Length: 4\r\n\r\nTest'
        ok(100)              | 'HTTP/1.1 200 OK\r\nDate: .* GMT\r\nContent-Type: text/plain; charset=UTF-8\r\nContent-Length: 3\r\n\r\n100'
    }

    def 'should successfully match request path'() {
        given: 'a request'
        def request = HttpRequest.of 'GET /some/1/path HTTP/1.1'

        and: 'routes'
        def routes = Routes.of Endpoint.<String>get("/some/[0-9]*/path\$", { HttpRequest r -> ok("Great success!") })

        when: 'response object is created'
        def response = HttpResponse.of request with routes build()

        then: 'matches expectations'
        response == ok("Great success!")
    }

    def 'should create NOT FOUND response'() {
        given: 'a request'
        def request = HttpRequest.of 'POST /login HTTP/1.1'

        when: 'path does not exist'
        def response = HttpResponse.of request build()

        then: 'should respond with NOT FOUND'
        response == notFound()
    }
}
