package findallbyorissue

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ReproduceIssuesIntegrationSpec extends Specification {

    Author author1
    Author author2
    Author author3

    Book book1
    Book book2
    Book book3

    void setup() {
        author1 = new Author(name: "Stephen King").save()
        author2 = new Author(name: "Kim Stanley Robinson").save()
        author3 = new Author(name: "Unknown").save()

        book1 = new Book(title: "The Gunslinger").save()
        book2 = new Book(title: "Red Mars").save()
        book3 = new Book(title: "I Ching").save()

        new AuthoredBook(author: author1, book: book1).save()
        new AuthoredBook(author: author2, book: book2).save()
    }

    void "find by author or book -- dynamic finder"() {
        expect:
        AuthoredBook.findByAuthorOrBook(author3, book3) == null
    }

    void "find by author or book -- criteria"() {
        expect:
        AuthoredBook.createCriteria().get {
            or {
                eq("author", author3)
                eq("book", book3)
            }
        } == null
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
