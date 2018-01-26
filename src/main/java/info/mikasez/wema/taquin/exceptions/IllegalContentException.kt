package info.mikasez.wema.taquin.exceptions

class IllegalContentException : IllegalArgumentException {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}