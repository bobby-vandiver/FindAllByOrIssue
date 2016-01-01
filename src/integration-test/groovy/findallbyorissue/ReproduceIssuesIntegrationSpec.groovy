package findallbyorissue

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ReproduceIssuesIntegrationSpec extends Specification {

    Author author1
    Author author2

    Book book1
    Book book2

    void setup() {
        author1 = new Author(name: "Stephen King").save()
        author2 = new Author(name: "Kim Stanley Robinson").save()

        book1 = new Book(title: "The Gunslinger").save()
        book2 = new Book(title: "Red Mars").save()

        new AuthoredBook(author: author1, book: book1).save()
        new AuthoredBook(author: author2, book: book2).save()
    }

    void "find all by author or book -- dynamic finder"() {
        expect:
        AuthoredBook.findAllByAuthorOrBook(author2, book1).size() == 2
    }

    void "find all by author or book -- criteria"() {
        expect:
        AuthoredBook.withCriteria {
            or {
                eq("author", author2)
                eq("book", book1)
            }
        }.size() == 2
    }

    void "find all by author and book"() {
        expect:
        AuthoredBook.findAllByAuthorAndBook(author1, book1).size() == 1
    }
}
