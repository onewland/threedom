package com.oliverco

class VoteRecordingService(val threeGramDao: ThreeGramDao) {
    fun recordVote(ngramId: Int) {
        threeGramDao.upvote(ngramId)
    }
}