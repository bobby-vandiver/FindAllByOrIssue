package findallbyorissue

class AuthoredBook {

    Book book
    Author author

    static constraints = {
        book nullable: false
        author nullable: false
    }
}
