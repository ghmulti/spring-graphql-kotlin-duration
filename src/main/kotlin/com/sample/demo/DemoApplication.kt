package com.sample.demo

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Configuration
class GraphQlConfig {

	@Bean
	fun runtimeWiringConfigurer() = RuntimeWiringConfigurer {
		it.scalar(graphqlDurationType)
			.build()
	}

	val graphqlDurationType: GraphQLScalarType = GraphQLScalarType.newScalar()
		.name("Duration")
		.description("Using Duration human readable standard formatting. \n Example: 1d 1h 30m or 1d 90m. Represents a duration of 1 day and 90 minutes.")
		.coercing(DurationCoercing)
		.build()

}

data class Sample(
	val duration: Duration
)

object DurationCoercing : Coercing<Duration, String> {
	override fun serialize(dataFetcherResult: Any): String {
		return ((dataFetcherResult as Long) shr 1).toDuration(DurationUnit.NANOSECONDS).toString()
	}

	override fun parseValue(input: Any): Duration {
		return Duration.parse(input as String)
	}

	override fun parseLiteral(input: Any): Duration {
		val durationString = (input as? StringValue)?.value ?: throw RuntimeException("Duration could not be cast to StringValue")
		return Duration.parse(durationString)
	}
}

