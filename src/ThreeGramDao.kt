package com.oliverco

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object NgramsTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val n1: Column<String> = varchar("n1",50)
    val n2: Column<String> = varchar("n2",50)
    val n3: Column<String> = varchar("n3",50)
    val votes: Column<Int> = integer("votes")
}

class ThreeGramDao(databaseUrl: String, databaseUser: String, databasePassword: String) {
    init {
        Database.connect(
            databaseUrl, driver = "org.postgresql.Driver",
            user = databaseUser, password = databasePassword
        )
    }

    fun getThreeGram(position: Int): ThreeGram {
        return transaction {
            val select = NgramsTable.select { NgramsTable.id eq position }.limit(1)
            select.map {
                ThreeGram(it[NgramsTable.id],
                    it[NgramsTable.n1],
                    it[NgramsTable.n2],
                    it[NgramsTable.n3]
                )
            }.first()
        }
    }

    fun upvote(ngramId: Int) {
        transaction {
            NgramsTable.update({ NgramsTable.id eq ngramId }) {
                with(SqlExpressionBuilder) {
                    it.update(votes, votes + 1)
                }
            }
        }
    }
}