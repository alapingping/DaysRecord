package com.example.daysrecord.logic.model

data class Comment(val id: Int, val messageId: Int, val author: String, val content: String, val time: String) {
    override fun equals(other: Any?): Boolean {
        if (other is Comment) {
            return this.author == other.author &&
                    this.content == other.content &&
                    this.time == other.time &&
                    this.messageId == other.messageId
        }
        return false
    }
}
