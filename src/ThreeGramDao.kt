package com.oliverco

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object ngramsTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val n1: Column<String> = varchar("n1",50)
    val n2: Column<String> = varchar("n2",50)
    val n3: Column<String> = varchar("n3",50)
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
            val select = ngramsTable.select { ngramsTable.id eq position }.limit(1)
            select.map {
                ThreeGram(it[ngramsTable.id],
                    it[ngramsTable.n1],
                    it[ngramsTable.n2],
                    it[ngramsTable.n3]
                )
            }.first()
        }
    }
}