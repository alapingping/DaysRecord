package com.example.daysrecord.logic.model

data class Message(
    @Transient
    val id: Int = 0,
    val author: String,
    val content: String,
    val time: String,
    val comments: List<Comment>? = null) {
    override fun equals(other: Any?): Boolean {
        if (other is Message) {
            return this.author == other.author &&
                    this.content == other.content &&
                    this.time == other.time &&
                    this.comments == other.comments
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + author.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + comments.hashCode()
        return result
    }
}
