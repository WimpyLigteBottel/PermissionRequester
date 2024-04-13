package nel.marco.message

data class MessageRequest(val who: String, val to: String, val requestMessage: String)


interface MessageService {
    fun send(messageRequest: MessageRequest)
}