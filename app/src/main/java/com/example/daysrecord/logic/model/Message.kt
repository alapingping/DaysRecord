package com.example.daysrecord.logic.model

data class Message(val id: Int, val author: String, val content: String, val time: String, val comments: List<Comment>) {
    override fun equals(other: Any?): Boolean {
        if (other is Message) {
            return this.author == other.author &&
                    this.content == other.content &&
                    this.time == other.time &&
                    this.comments == other.comments
        }
        return false
    }
}
