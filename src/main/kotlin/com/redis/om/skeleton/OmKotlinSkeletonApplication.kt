package com.redis.om.skeleton

import com.redis.om.skeleton.models.Address
import com.redis.om.skeleton.models.Person
import com.redis.om.skeleton.repositories.PeopleRepository
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories
import com.redis.om.spring.search.stream.EntityStream
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.geo.Point

@SpringBootApplication
@EnableRedisDocumentRepositories("com.redis.om.skeleton.*")
class OmKotlinDemoApplication {
	var logger: Logger = LoggerFactory.getLogger(OmKotlinDemoApplication::class.java)

	@Bean
	fun loadTestData(
		repo: PeopleRepository,
		entityStream: EntityStream
	): CommandLineRunner {
		return CommandLineRunner { _ ->
			repo.deleteAll()
			val thorSays = "The Rabbit Is Correct, And Clearly The Smartest One Among You."
			val ironmanSays = "Doth mother know you weareth her drapes?"
			val blackWidowSays =
				"Hey, fellas. Either one of you know where the Smithsonian is? I'm here to pick up a fossil."
			val wandaMaximoffSays = "You Guys Know I Can Move Things With My Mind, Right?"
			val gamoraSays = "I Am Going To Die Surrounded By The Biggest Idiots In The Galaxy."
			val nickFurySays = "Sir, I'm Gonna Have To Ask You To Exit The Donut"

			// Serendipity, 248 Seven Mile Beach Rd, Broken Head NSW 2481, Australia
			val thorsAddress =
				Address("248", "Seven Mile Beach Rd", "Broken Head", "NSW", "2481", "Australia")

			// 11 Commerce Dr, Riverhead, NY 11901
			val ironmansAddress = Address("11", "Commerce Dr", "Riverhead", "NY", "11901", "US")

			// 605 W 48th St, New York, NY 10019
			val blackWidowAddress = Address("605", "48th St", "New York", "NY", "10019", "US")

			// 20 W 34th St, New York, NY 10001
			val wandaMaximoffsAddress = Address("20", "W 34th St", "New York", "NY", "10001", "US")

			// 107 S Beverly Glen Blvd, Los Angeles, CA 90024
			val gamorasAddress = Address("107", "S Beverly Glen Blvd", "Los Angeles", "CA", "90024", "US")

			// 11461 Sunset Blvd, Los Angeles, CA 90049
			val nickFuryAddress = Address("11461", "Sunset Blvd", "Los Angeles", "CA", "90049", "US")
			val thor = Person(
				null, "Chris", "Hemsworth", 38, thorSays, Point(153.616667, -28.716667), thorsAddress,
				setOf("hammer", "biceps", "hair", "heart")
			)
			val ironman = Person(
				null, "Robert", "Downey", 56, ironmanSays, Point(40.9190747, -72.5371874),
				ironmansAddress, setOf("tech", "money", "one-liners", "intelligence", "resources")
			)
			val blackWidow = Person(
				null, "Scarlett", "Johansson", 37, blackWidowSays, Point(40.7215259, -74.0129994),
				blackWidowAddress, setOf("deception", "martial_arts")
			)
			val wandaMaximoff = Person(
				null, "Elizabeth", "Olsen", 32, wandaMaximoffSays, Point(40.6976701, -74.2598641),
				wandaMaximoffsAddress, setOf("magic", "loyalty")
			)
			val gamora = Person(
				null, "Zoe", "Saldana", 43, gamoraSays, Point(-118.399968, 34.073087), gamorasAddress,
				setOf("skills", "martial_arts")
			)
			val nickFury = Person(
				null, "Samuel L.", "Jackson", 73, nickFurySays, Point(-118.4345534, 34.082615),
				nickFuryAddress, setOf("planning", "deception", "resources")
			)
			repo.saveAll(listOf(thor, ironman, blackWidow, wandaMaximoff, gamora, nickFury))
			repo.findAll().forEach { p -> logger.info("🦸 Name: {} {}", p.firstName, p.lastName) }
		}
	}

	@Bean
	fun apiInfo(): OpenAPI {
		return OpenAPI().info(Info().title("Redis OM Spring Skeleton").version("1.0.0"))
	}

	@Bean
	fun httpApi(): GroupedOpenApi {
		return GroupedOpenApi.builder()
			.group("http")
			.pathsToMatch("/**")
			.build()
	}
}

fun main(args: Array<String>) {
	runApplication<OmKotlinDemoApplication>(*args)
}


